package gov.va.escreening.controller.dashboard;

import gov.va.escreening.dto.dashboard.DashboardAlertItem;
import gov.va.escreening.dto.dashboard.NearingCompletionAlertItem;
import gov.va.escreening.dto.dashboard.SlowMovingAlertItem;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.VeteranAssessmentDashboardAlertService;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/dashboard")
public class DashboardAlertRestController {

	@Resource(name = "escVetAssessmentDashboardAlertSrv")
	VeteranAssessmentDashboardAlertService escVetAssessmentDashboardAlertSrv;

	private static final Logger logger = LoggerFactory.getLogger(DashboardAlertRestController.class);

	@RequestMapping(value = "/services/alerts/r/dashboardAlerts/{programId}", method = RequestMethod.GET, consumes = "*/*", produces = "application/json")
	@ResponseBody
	public List<DashboardAlertItem> getDashboardAlerts(
			@PathVariable int programId, @CurrentUser EscreenUser escreenUser) {

		logger.debug("E:getDashboardAlerts");
		List<DashboardAlertItem> alerts = escVetAssessmentDashboardAlertSrv.findVeteranAlertByProgram(programId);
		logger.debug("X:getDashboardAlerts");

		return alerts;
	}

	@RequestMapping(value = "/services/alerts/r/nearingCompletionAssessments/{programId}", method = RequestMethod.GET, consumes = "*/*", produces = "application/json")
	@ResponseBody
	public List<NearingCompletionAlertItem> getNearingCompletionAssessments(
			@PathVariable int programId, @CurrentUser EscreenUser escreenUser) {

		logger.debug("E:getNearingCompletionsAssessments");
		List<NearingCompletionAlertItem> alerts = escVetAssessmentDashboardAlertSrv.findNearingCompletionAssessmentsByProgram(programId);
		logger.debug("X:getNearingCompletionsAssessments");

		return alerts;
	}

	@RequestMapping(value = "/services/alerts/r/slowMovingAssessments/{programId}", method = RequestMethod.GET, consumes = "*/*", produces = "application/json")
	@ResponseBody
	public List<SlowMovingAlertItem> getSlowMovingAssessments(
			@PathVariable int programId, @CurrentUser EscreenUser escreenUser) {

		logger.debug("E:getSlowMovingAssessments");
		List<SlowMovingAlertItem> alerts = escVetAssessmentDashboardAlertSrv.findSlowMovingAssessmentsByProgram(programId);
		logger.debug("X:getSlowMovingAssessments");

		return alerts;
	}
}
