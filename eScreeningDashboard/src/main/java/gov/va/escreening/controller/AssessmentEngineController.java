package gov.va.escreening.controller;

import gov.va.escreening.delegate.AssessmentDelegate;
import gov.va.escreening.domain.AssessmentStatusEnum;
import gov.va.escreening.dto.ae.AssessmentRequest;
import gov.va.escreening.dto.ae.AssessmentResponse;
import gov.va.escreening.dto.ae.CompletionResponse;
import gov.va.escreening.dto.ae.ErrorResponse;
import gov.va.escreening.exception.AssessmentEngineDataValidationException;
import gov.va.escreening.exception.InvalidAssessmentContextException;
import gov.va.escreening.service.AssessmentEngineService;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value = "/assessment")
public class AssessmentEngineController {

	private static final Logger logger = LoggerFactory.getLogger(AssessmentEngineController.class);

	@Autowired
	private AssessmentDelegate assessmentDelegate;
	@Autowired
	private AssessmentEngineService assessmentEngineService;

	@RequestMapping(value = "/start", method = RequestMethod.GET)
	public String setupForm(Model model) {
		logger.debug("In setupForm (get to assessment/start)");
		try {
			assessmentDelegate.ensureValidAssessmentContext();

			Integer assessmentId = assessmentDelegate.getVeteranAssessmentId();

			assessmentEngineService.transitionAssessmentStatusTo(assessmentId, AssessmentStatusEnum.INCOMPLETE);
			model.addAttribute("veteranAssessmentId", assessmentId);
			model.addAttribute("sections", assessmentDelegate.getAssessmentSections());
			model.addAttribute("veteranFullName", assessmentDelegate.getVeteranFullName());

			return "/assessment/standardTemplate";
		} catch (InvalidAssessmentContextException e) {
			return "redirect:/assessmentLogin";
		}
	}

	@RequestMapping(value = "/services/assessments/active", method = RequestMethod.POST, headers = { "content-type=application/json; charset=utf-8" })
	@ResponseBody
	public AssessmentResponse processData(
			@RequestBody AssessmentRequest assessmentRequest,
			HttpSession session) {

		logger.debug("processData()::assessmentRequest \n{}", assessmentRequest);

		assessmentDelegate.ensureValidAssessmentContext();

		// set the time of session create time as this will be used to measure
		// the duration of this assessment
		Long startTime=(Long)session.getAttribute("start_time");
		if (startTime==null){
			startTime=System.currentTimeMillis();
		}

		assessmentRequest.setStartTime(startTime);
		AssessmentResponse assessmentResponse = assessmentDelegate.processPage(assessmentRequest);
		
		session.setAttribute("start_time", System.currentTimeMillis());

		logger.debug("processData()::assessmentResponse \n{}", assessmentResponse);

		return assessmentResponse;
	}

	@RequestMapping(value = "/services/assessments/end", method = RequestMethod.GET, headers = { "content-type=application/json; charset=utf-8" })
	@ResponseBody
	public CompletionResponse getCompletionData(HttpSession session) {
		logger.debug("In getCompletionData");
		assessmentDelegate.ensureValidAssessmentContext();
		assessmentDelegate.markAssessmentAsComplete();

		// delete everything out of the session
		session.invalidate();

		return assessmentDelegate.getCompletionResponse();
	}

	@RequestMapping(value = "/services/assessments/visibility", method = RequestMethod.POST, headers = { "content-type=application/json; charset=utf-8" })
	@ResponseBody
	public Map<Integer, Boolean> processSurveyPageMeasureVisibility(
			@RequestBody AssessmentRequest assessmentRequest) {
		logger.debug("updating survey page visibility");

		assessmentDelegate.ensureValidAssessmentContext();

		return assessmentEngineService.getUpdatedVisibility(assessmentRequest);
	}

	@ExceptionHandler(AssessmentEngineDataValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleException(
			AssessmentEngineDataValidationException ex) {

		logger.debug("==>Assessment Engine Validation Exception");
		logger.debug(ex.toString());
		logger.debug(ex.getErrorResponse().toString());

		// returns the error response which contains a list of error messages
		return ex.getErrorResponse().setStatus(HttpStatus.BAD_REQUEST.value());
	}

	@ExceptionHandler(InvalidAssessmentContextException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public ErrorResponse handleException(InvalidAssessmentContextException ex) {

		logger.debug("==>Veteran Assessment Context  Exception");

		logger.debug(ex.toString());
		logger.debug(ex.getErrorResponse().toString());

		return ex.getErrorResponse().setStatus(HttpStatus.UNAUTHORIZED.value());
	}
}
