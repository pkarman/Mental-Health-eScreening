package gov.va.escreening.controller.dashboard;

import gov.va.escreening.domain.ErrorCodeEnum;
import gov.va.escreening.dto.ae.ErrorBuilder;
import gov.va.escreening.dto.ae.ErrorResponse;
import gov.va.escreening.exception.EntityNotFoundException;
import gov.va.escreening.service.AssessmentVariableService;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.collect.Lists;
import com.google.common.collect.Table;

@Controller
@RequestMapping("/dashboard")
public class AssessmentVariableController {
	private static final Logger logger = LoggerFactory.getLogger(AssessmentVariableController.class);

	// TODO: We only to access service via a business delegate. Need to re-factor service class to not be injected; but
	// a business delegate instead.
	@Resource(name = "assessmentVariableService")
	AssessmentVariableService avs;

	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorResponse handleEntityNotFoundException(
			EntityNotFoundException enfe) {
		logger.debug(enfe.getMessage());
		return enfe.getErrorResponse();
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorResponse handleIllegalArgumentException(Exception iae) {
		logger.debug(iae.getMessage());
		ErrorResponse er = new ErrorResponse();

		er.setDeveloperMessage(iae.getMessage());
		er.addMessage("Sorry; but we are unable to process your request at this time.  If this continues, please contact your system administrator.");
		er.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return er;
	}

	@RequestMapping(value = "/services/assessmentVariables", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List getAssessmentVarsForSurvey(
			@RequestParam("surveyId") Integer surveyId) {

		if (surveyId == null || surveyId < 0) {
			ErrorBuilder.throwing(EntityNotFoundException.class).toUser("Sorry, we are unable to process your request at this time.  If this continues, please contact your system administrator.").toAdmin("The survey id passed in is 0 or null").setCode(ErrorCodeEnum.OBJECT_NOT_FOUND.getValue()).throwIt();
		}

		Table<String, String, Object> t = avs.getAssessmentVarsFor(surveyId);

		if (t.isEmpty()) {
			ErrorBuilder.throwing(EntityNotFoundException.class).toUser("Sorry, we are unable to process your request at this time.  If this continues, please contact your system administrator.").toAdmin(String.format("No Measures were found to be available for Survey with an Id of %s", surveyId)).setCode(ErrorCodeEnum.OBJECT_NOT_FOUND.getValue()).throwIt();
		}

		List<Map<String, Object>> avs = Lists.newArrayList();

		for (String rowKey : t.rowKeySet()) {
			avs.add(t.row(rowKey));
		}

		return avs;
	}

}
