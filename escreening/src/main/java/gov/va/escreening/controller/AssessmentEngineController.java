package gov.va.escreening.controller;

import gov.va.escreening.context.VeteranAssessmentSmrList;
import gov.va.escreening.delegate.AssessmentDelegate;
import gov.va.escreening.domain.AssessmentStatusEnum;
import gov.va.escreening.dto.ae.AssessmentRequest;
import gov.va.escreening.dto.ae.AssessmentResponse;
import gov.va.escreening.dto.ae.ErrorResponse;
import gov.va.escreening.exception.AssessmentEngineDataValidationException;
import gov.va.escreening.exception.EntityNotFoundException;
import gov.va.escreening.exception.IllegalSystemStateException;
import gov.va.escreening.exception.InvalidAssessmentContextException;
import gov.va.escreening.service.AssessmentEngineService;

import java.util.Map;

import javax.annotation.Resource;
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

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;

@Controller
@RequestMapping(value = "/assessment")
public class AssessmentEngineController {

	private static final Logger logger = LoggerFactory.getLogger(AssessmentEngineController.class);

	@Resource(type=VeteranAssessmentSmrList.class)
	VeteranAssessmentSmrList smrLister;
	
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
			assessmentDelegate.prepareAssessmentContext();
			
			assessmentEngineService.transitionAssessmentStatusTo(assessmentId, AssessmentStatusEnum.INCOMPLETE);
			
			model.addAttribute("veteranAssessmentId", assessmentId);
			model.addAttribute("sections", assessmentDelegate.getAssessmentSections());
			model.addAttribute("veteranFullName", assessmentDelegate.getVeteranFullName());

			return "/assessment/standardTemplate";
		} catch (InvalidAssessmentContextException e) {
			return "redirect:/assessmentLogin";
		}
	}
	
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> getWelcomeMessage() throws IllegalSystemStateException	{
		assessmentDelegate.ensureValidAssessmentContext();
		String message = assessmentDelegate.getWelcomeMessage();
		return ImmutableMap.of("message", Strings.nullToEmpty(message));
	}

	@RequestMapping(value = "/end", method = RequestMethod.GET, headers = { "content-type=application/json; charset=utf-8" })
	@ResponseBody
	public Map<String,String> getCompletionData(HttpSession session) throws IllegalSystemStateException {
		logger.debug("In getCompletionData");
		assessmentDelegate.ensureValidAssessmentContext();
		assessmentDelegate.markAssessmentAsComplete();

		String message = assessmentDelegate.getCompletionMessage();
		
		// delete everything out of the session
		session.invalidate();

		return ImmutableMap.of("message", Strings.nullToEmpty(message));
	}

	@RequestMapping(value = "/services/assessments/active", method = RequestMethod.POST, headers = { "content-type=application/json; charset=utf-8" })
	@ResponseBody
	public AssessmentResponse processData(
			@RequestBody AssessmentRequest assessmentRequest,
			HttpSession session) {

		logger.debug("POST:/services/assessments/active");//\nIn processData() \n{}", assessmentRequest);

		assessmentDelegate.ensureValidAssessmentContext();

        startInstrumentation(assessmentRequest, session);

		smrLister.clearSmrFromCache();
		long startTime = System.nanoTime();
        AssessmentResponse assessmentResponse = assessmentDelegate.processPage(assessmentRequest);
        long endTime = System.nanoTime();
        logger.debug("processPage time: {}ms", (endTime - startTime)/1000000l);
        
		smrLister.clearSmrFromCache();

        finishInstrumentation(assessmentResponse, session);

		return assessmentResponse;
	}

    private void finishInstrumentation(AssessmentResponse assessmentResponse, HttpSession session) {
        Long now = System.currentTimeMillis();
        session.setAttribute("assessment_start_time", now);

        if (assessmentResponse.getAssessment().getPageId()!=null) {
            Integer moduleId = assessmentDelegate.getModuleId(assessmentResponse.getAssessment().getPageId());
            String moduleStartTimeKey = String.format("module_%s_start_time", moduleId);
            session.setAttribute(moduleStartTimeKey, now);
        }
    }

    private void startInstrumentation(AssessmentRequest assessmentRequest, HttpSession session) {
        Long now = System.currentTimeMillis();

        // get the module and seed its start time
        if (assessmentRequest.getPageId()!=null) {
            Integer moduleId = assessmentDelegate.getModuleId(assessmentRequest.getPageId());
            String moduleStartTimeKey = String.format("module_%s_start_time", moduleId);
            Long moduleStartTime = (Long) session.getAttribute(moduleStartTimeKey);
            if (moduleStartTime == null) {
                moduleStartTime = now;
            }
            assessmentRequest.setModuleStartTime(moduleId, moduleStartTime);
        }
        // set the time of session create time as this will be used to measure
        // the duration of this assessment
        Long assessmentStartTime=(Long)session.getAttribute("assessment_start_time");
        if (assessmentStartTime==null){
            assessmentStartTime=now;
        }
        assessmentRequest.setAssessmentStartTime(assessmentStartTime);
    }

    @RequestMapping(value = "/services/assessments/visibility", method = RequestMethod.POST, headers = { "content-type=application/json; charset=utf-8" })
	@ResponseBody
	public Map<Integer, Boolean> processSurveyPageMeasureVisibility(
			@RequestBody AssessmentRequest assessmentRequest) {
		logger.debug("updating survey page visibility");

		assessmentDelegate.ensureValidAssessmentContext();
		assessmentRequest.setAssessmentId(assessmentDelegate.getVeteranAssessmentId());
		
		//long start = System.currentTimeMillis();
		Map<Integer, Boolean> inMemory = assessmentEngineService.getUpdatedVisibilityInMemory(assessmentRequest);
		//long end1 = System.currentTimeMillis();
		
		/*** The following section are here for testing purpose only **********/
//		Map<Integer, Boolean> regular = assessmentEngineService.getUpdatedVisibility(assessmentRequest);
//		long end2 = System.currentTimeMillis();
		
//		logger.info("INMEMORY TIME: "+ (end1-start)+ "   REGULAR TIME: "+ (end2-end1)); 
//		for(Integer key : inMemory.keySet())
//		{
//			if(!regular.get(key).equals(inMemory.get(key)))
//			{
//				logger.warn("========= Don't match ==========  " + key);
//			}
//		}
		
		return inMemory;
	}

	@ExceptionHandler(AssessmentEngineDataValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleException(
			AssessmentEngineDataValidationException ex) {

		logger.error("==>Assessment Engine Validation Exception");
		logger.error(ex.toString());
		logger.error(ex.getErrorResponse().toString());

		// returns the error response which contains a list of error messages
		return ex.getErrorResponse().setStatus(HttpStatus.BAD_REQUEST.value());
	}

	@ExceptionHandler(InvalidAssessmentContextException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public ErrorResponse handleException(InvalidAssessmentContextException ex) {

		logger.error("==>Veteran Assessment Context  Exception");
		logger.error(ex.toString());
		logger.error(ex.getErrorResponse().toString());

		return ex.getErrorResponse().setStatus(HttpStatus.UNAUTHORIZED.value());
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleEntityNotFoundException(EntityNotFoundException enfe) {
		ErrorResponse er = enfe.getErrorResponse();
    	logger.error(er.getLogMessage());
        return er;
    }
	
	@ExceptionHandler(IllegalSystemStateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleIllegalSystemStateException(IllegalSystemStateException isse) {
		ErrorResponse er = isse.getErrorResponse();
    	logger.error(er.getLogMessage());
        return er;
    }
	
	@ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleException(Exception e) {
		logger.error("Unexpected error:", e);
		
		ErrorResponse er = new ErrorResponse();
		er.setDeveloperMessage("Unexpected error: " + e + "\nCheck system log for stack trace before the ID given from this message.");
		er.addMessage("An unexpected error has occurred. <br/>Please see the clerk.");
		logger.error(er.getLogMessage());
		
        return er;
    }
}
