package gov.va.escreening.service;

import gov.va.escreening.dto.AlertDto;
import gov.va.escreening.dto.dashboard.DashboardAlertItem;
import gov.va.escreening.dto.dashboard.NearingCompletionAlertItem;
import gov.va.escreening.dto.dashboard.SlowMovingAlertItem;

import java.util.List;

public interface VeteranAssessmentDashboardAlertService {

	/**
	 * Retrieves all the alerts associated with veteranAssessmentId
	 * 
	 * @param veteranAssessmentId
	 * @return
	 */
	List<AlertDto> findForVeteranAssessmentId(int veteranAssessmentId);

	List<DashboardAlertItem> findVeteranAlertByProgram(int locationId);

	List<SlowMovingAlertItem> findSlowMovingAssessmentsByProgram(
			int veteranAssessmentId);

	List<NearingCompletionAlertItem> findNearingCompletionAssessmentsByProgram(
			int veteranAssessmentId);
}
