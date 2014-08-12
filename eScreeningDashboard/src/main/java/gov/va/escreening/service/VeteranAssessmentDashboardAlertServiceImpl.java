package gov.va.escreening.service;

import gov.va.escreening.dto.AlertDto;
import gov.va.escreening.dto.dashboard.DashboardAlertItem;
import gov.va.escreening.dto.dashboard.NearingCompletionAlertItem;
import gov.va.escreening.dto.dashboard.SlowMovingAlertItem;
import gov.va.escreening.entity.VeteranAssessmentDashboardAlert;
import gov.va.escreening.repository.VeteranAssessmentDashboardAlertRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("escVetAssessmentDashboardAlertSrv")
public class VeteranAssessmentDashboardAlertServiceImpl implements VeteranAssessmentDashboardAlertService {

	private VeteranAssessmentDashboardAlertRepository veteranAssessmentDashboardAlertRepository;

	@Autowired
	public void setVeteranAssessmentDashboardAlertRepository(
			VeteranAssessmentDashboardAlertRepository veteranAssessmentDashboardAlertRepository) {
		this.veteranAssessmentDashboardAlertRepository = veteranAssessmentDashboardAlertRepository;
	}

	@Transactional(readOnly = true)
	@Override
	public List<AlertDto> findForVeteranAssessmentId(int veteranAssessmentId) {

		List<AlertDto> alertList = new ArrayList<AlertDto>();

		List<VeteranAssessmentDashboardAlert> resultList = veteranAssessmentDashboardAlertRepository.findByVeteranAssessmentId(veteranAssessmentId);

		for (VeteranAssessmentDashboardAlert alert : resultList) {
			AlertDto alertDto = new AlertDto();
			alertDto.setAlertId(alert.getDashboardAlert().getDashboardAlertId());
			alertDto.setAlertName(alert.getDashboardAlert().getName());
			alertList.add(alertDto);

		}

		return alertList;
	}

	String[] colors = { "#0c6197", "#daca61", "#CE2AEB", "#D1C87F", "#44B9B0", "#E65414" };

	@Transactional(readOnly = false)
	@Override
	public List<DashboardAlertItem> findVeteranAlertByProgram(int programId) {
		List resultList = veteranAssessmentDashboardAlertRepository.findAlertsByProgram(programId);
		List<DashboardAlertItem> alerts = createDashboardAlertResult(resultList);
		return alerts;
	}

	private List<DashboardAlertItem> createDashboardAlertResult(List resultList) {
		long totalRecs = getTotalRecs(resultList);

		List<DashboardAlertItem> alerts = new ArrayList<DashboardAlertItem>();
		int i = 0;
		for (Iterator resIter = resultList.iterator(); resIter.hasNext();) {
			Object[] res = (Object[]) resIter.next();
			String dashboardAlertName = (String) res[0];
			long dashboardAlertCnt = (Long) res[1];
			String percentage = String.format("%2.02f", (((float) dashboardAlertCnt / totalRecs) * 100));
			String vaid = String.valueOf(res[2]);

			alerts.add(new DashboardAlertItem(dashboardAlertCnt, colors[i++ % colors.length], Float.parseFloat(percentage), dashboardAlertName, vaid));
		}

		if (alerts.isEmpty()) {
			alerts.add(new DashboardAlertItem(0L, "#009900", 100.00f, "No Alerts", ""));
		}

		return alerts;
	}

	private long getTotalRecs(List resultList) {
		long r = 0;
		for (Iterator resIter = resultList.iterator(); resIter.hasNext();) {
			Object[] res = (Object[]) resIter.next();
			long dashboardAlertCnt = (Long) res[1];
			r += dashboardAlertCnt;
		}
		return r;
	}

	@Transactional(readOnly = true)
	@Override
	public List<SlowMovingAlertItem> findSlowMovingAssessmentsByProgram(
			int programId) {
		List<Map<String, Object>> result = veteranAssessmentDashboardAlertRepository.findSlowMovingAssessments(programId, 5);

		List<SlowMovingAlertItem> alerts = new ArrayList<SlowMovingAlertItem>();
		for (Map<String, Object> resMap : result) {
			String lastName = (String) resMap.get("lastName");
			String lst4Ssn = (String) resMap.get("lastFourSsn");

			int time = (Integer) resMap.get("duration");
			int alert = (Integer) resMap.get("noOfAlerts");
			String vaid = String.valueOf(resMap.get("vaid"));
			alert = alert == 0 ? 0 : 1;
			alerts.add(new SlowMovingAlertItem(time, alert == 0 ? "#009900" : "#990000", alert, lastName, lst4Ssn, vaid));
		}
		return alerts;
	}

	@Transactional(readOnly = true)
	@Override
	public List<NearingCompletionAlertItem> findNearingCompletionAssessmentsByProgram(
			int programId) {

		List<Map<String, Object>> result = veteranAssessmentDashboardAlertRepository.findNearingCompletionAssessments(programId, 5);

		List<NearingCompletionAlertItem> alerts = new ArrayList<NearingCompletionAlertItem>();
		for (Map<String, Object> resMap : result) {
			String lastName = (String) resMap.get("lastName");
			String lst4Ssn = (String) resMap.get("lastFourSsn");

			int perentage = (Integer) resMap.get("percentComplete");
			int alert = (Integer) resMap.get("noOfAlerts");
			String vaid = String.valueOf(resMap.get("vaid"));
			alert = alert == 0 ? 0 : 1;
			alerts.add(new NearingCompletionAlertItem(perentage, alert == 0 ? "#009900" : "#990000", alert, lastName, lst4Ssn, vaid));
		}
		return alerts;
	}

}
