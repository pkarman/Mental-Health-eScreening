package gov.va.escreening.controller.dashboard;

import gov.va.escreening.delegate.EditorsViewDelegate;
import gov.va.escreening.domain.ErrorCodeEnum;
import gov.va.escreening.dto.ae.ErrorResponse;
import gov.va.escreening.dto.ae.Measure;
import gov.va.escreening.dto.ae.Page;
import gov.va.escreening.dto.editors.*;
import gov.va.escreening.exception.AssessmentEngineDataValidationException;
import gov.va.escreening.repository.MeasureRepository;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;

import java.util.*;

import gov.va.escreening.transformer.EditorsQuestionViewTransformer;
import gov.va.escreening.webservice.Response;
import gov.va.escreening.webservice.ResponseStatus;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.NotFoundException;

@Controller
@RequestMapping(value = "/dashboard")
public class EditorRestController {

    private static final Logger logger = LoggerFactory.getLogger(EditorRestController.class);

    private EditorsViewDelegate editorsViewDelegate;
    @Autowired
    private MeasureRepository measureRepo;

    @Autowired
    public void setEditorsViewDelegate(EditorsViewDelegate editorsViewDelegate) {
        this.editorsViewDelegate = editorsViewDelegate;
    }

    /*
      ============= /services/surveys/{surveyId}/pages =============
     */
    @RequestMapping(value = "/services/surveys/{surveyId}/pages/{pageId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Response<String> createSurveyPage(@PathVariable("surveyId") Integer surveyId, @RequestBody Page surveyPage, @CurrentUser EscreenUser escreenUser) {

        editorsViewDelegate.createSurveyPage(surveyId, surveyPage);

        return new Response<>(new ResponseStatus(ResponseStatus.Request.Succeeded), "The data is created successfully.");
    }

    @RequestMapping(value = "/services/surveys/{surveyId}/pages", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    @ResponseBody
    public Response<Map<String, List<SurveyPageInfo>>> updateSurveyPages(@PathVariable Integer surveyId, @RequestBody List<SurveyPageInfo> surveyPages, @CurrentUser EscreenUser escreenUser) {
        ErrorResponse errorResponse = new ErrorResponse();

        for (SurveyPageInfo surveyPage : surveyPages) {
            for (QuestionInfo q : surveyPage.getQuestions()) {
                if (q.getMeasureType() == null) {
                    // throw data validation exception
                    errorResponse.setCode(ErrorCodeEnum.DATA_VALIDATION.getValue()).reject("data", "Question Type", "Question Type is required.");

                } else if (q.getText() == null) {
                    // throw data validation exception
                    errorResponse.setCode(ErrorCodeEnum.DATA_VALIDATION.getValue()).reject("data", "Question Text", "Question Text is required.");

                }

            }
            if (errorResponse.getErrorMessages() != null && errorResponse.getErrorMessages().size() > 0) {
                throw new AssessmentEngineDataValidationException(errorResponse);
            }

        }
        editorsViewDelegate.updateSurveyPages(surveyId, surveyPages);
        Map<String, List<SurveyPageInfo>> surveyPageInfoItems = new HashMap<>();
        surveyPageInfoItems.put("surveyPages", surveyPages);

        return new Response<>(new ResponseStatus(ResponseStatus.Request.Succeeded, "All module changes have been saved successfully."), surveyPageInfoItems);
    }

    @RequestMapping(value = "/services/surveys/{surveyId}/pages", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseBody
    public Response<SurveyPageInfo> createSurveyPage(@PathVariable Integer surveyId, @RequestBody SurveyPageInfo surveyPage, @CurrentUser EscreenUser escreenUser) {
        // re-direct this call to the update version
        return updateSurveyPage(surveyId, 0, surveyPage, escreenUser);
    }


    @RequestMapping(value = "/services/surveys/{surveyId}/pages/{pageId}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    @ResponseBody
    public Response<SurveyPageInfo> updateSurveyPage(@PathVariable Integer surveyId, @PathVariable Integer pageId,@RequestBody SurveyPageInfo surveyPage, @CurrentUser EscreenUser escreenUser) {
        editorsViewDelegate.updateSurveyPages(surveyId, Arrays.asList(surveyPage));
        for(SurveyPageInfo spi : editorsViewDelegate.getSurveyPages(surveyId, surveyPage.getPageNumber())){
        	return new Response<>(new ResponseStatus(ResponseStatus.Request.Succeeded, "Survey Page saved successfully."), spi);
        }
        throw new IllegalStateException("The save of page " + pageId + " did not work");
    }

    @RequestMapping(value = "/services/surveys/{surveyId}/pages", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Response<Map<String,List<SurveyPageInfo>>> retrieveSurveyPages(@PathVariable("surveyId") Integer surveyId, @CurrentUser EscreenUser escreenUser) {
        // Call service class here instead of hard coding it.
        List<SurveyPageInfo> surveyPages = editorsViewDelegate.getSurveyPages(surveyId, -1);
        Map<String,List<SurveyPageInfo>> surveyPageInfoItems = new HashMap<>();
        surveyPageInfoItems.put("surveyPages", surveyPages);

        return new Response<>(new ResponseStatus(ResponseStatus.Request.Succeeded), surveyPageInfoItems);
    }

    @RequestMapping(value = "/services/surveys/{surveyId}/pages/{pageId}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public Response<Integer> retrieveSurveyPages(@PathVariable("surveyId") Integer surveyId, @PathVariable("pageId") Integer pageId, @CurrentUser EscreenUser escreenUser) {
        editorsViewDelegate.deleteSurveyPage(surveyId, pageId);

        return new Response<Integer>(new ResponseStatus(ResponseStatus.Request.Succeeded), pageId);
    }

    @RequestMapping(value = "/services/surveys/{surveyId}/pages/{pageId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Response<SurveyPageInfo> removeSurveyPages(@PathVariable("surveyId") Integer surveyId, @PathVariable("pageId") Integer pageId, @CurrentUser EscreenUser escreenUser) {
        SurveyPageInfo surveyPage = editorsViewDelegate.getSurveyPage(surveyId, pageId);

        return new Response<>(new ResponseStatus(ResponseStatus.Request.Succeeded), surveyPage);
    }

    /*
      ============= /services/questions/{questionId} =============
     */
    @RequestMapping(value = "/services/questions", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Response<Map<String, QuestionInfo>>  addQuestion(@RequestBody QuestionInfo question,
                                @CurrentUser EscreenUser escreenUser) {

        throw new IllegalStateException("Rest Service not implemented yet");
    }

    @RequestMapping(value = "/services/questions/{questionId}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Response<Map<String, QuestionInfo>> updateQuestion(
            @PathVariable("questionId") Integer questionId,
            @RequestBody QuestionInfo question,
            @CurrentUser EscreenUser escreenUser) {

        QuestionInfo updatedQuestionInfo = EditorsQuestionViewTransformer.transformQuestion(measureRepo.updateMeasure(EditorsQuestionViewTransformer.transformQuestionInfo(question)));

        Map<String, QuestionInfo> questionMap = new HashMap<>();
        questionMap.put("question", updatedQuestionInfo);

        return new Response<>(new ResponseStatus(ResponseStatus.Request.Succeeded), questionMap);
    }

    @RequestMapping(value = "/services/questions/{questionId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Response<QuestionInfo>  getQuestion(@PathVariable("questionId") Integer questionId,
                                @CurrentUser EscreenUser escreenUser) {
        // Call service class here instead of hard coding it.
        Measure measure = editorsViewDelegate.findMeasure(questionId);
        QuestionInfo question = EditorsQuestionViewTransformer.transformQuestion(measure);
        return new Response<>(new ResponseStatus(ResponseStatus.Request.Succeeded), question);
    }

    /*
      ============= /services/surveys/{surveyId}/questions =============
     */
    @RequestMapping(value = "/services/surveys/{surveyId}/questions", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Response<Map<String, List<QuestionInfo>>> getQuestionsBySurveyId(@PathVariable("surveyId") Integer surveyId,
                                           @CurrentUser EscreenUser escreenUser) {
        logger.debug("getQuestions");

        List<QuestionInfo> questions = EditorsQuestionViewTransformer.transformQuestions(measureRepo.getMeasureDtoBySurveyID(surveyId));

        Map<String, List<QuestionInfo>> questionInfoItems = new HashMap<>();
        questionInfoItems.put("questions", questions);

        return new Response<>(new ResponseStatus(ResponseStatus.Request.Succeeded), questionInfoItems);
    }

    @RequestMapping(value = "/services/surveys/{surveyId}/questions/{questionId}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public Response<String> deleteQuestion(
            @PathVariable("surveyId") Integer surveyId,
            @PathVariable("questionId") Integer questionId, @CurrentUser EscreenUser escreenUser) {


        editorsViewDelegate.removeQuestionFromSurvey(surveyId, questionId);

        return new Response<>(new ResponseStatus(ResponseStatus.Request.Succeeded), "The data is deleted successfully.");
    }

    @RequestMapping(value = "/services/surveys", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Response<SurveyInfo> addSurvey(@RequestBody SurveyInfo survey,
                              @CurrentUser EscreenUser escreenUser) {
        logger.debug("create new survey:" + survey);

        ErrorResponse errorResponse = new ErrorResponse();

        // Data validation.
        if (StringUtils.isBlank(survey.getName())) {
            // throw data validation exception
            errorResponse.setCode(ErrorCodeEnum.DATA_VALIDATION.getValue()).reject("data", "Module Title", "Module Name is required.");
        } else if (survey.getName().length() > 255) {
            // throw data validation exception
            errorResponse.setCode(ErrorCodeEnum.DATA_VALIDATION.getValue()).reject("data", "Module Title", "Module Title should be less than 255 characters.");
        } else if (survey.getDescription() != null && survey.getDescription().length() > 255) {
            errorResponse.setCode(ErrorCodeEnum.DATA_VALIDATION.getValue()).reject("data", "Module Description", "Description should be less than 255 characters.");
        } else if (survey.getSurveySectionInfo() == null || survey.getSurveySectionInfo().getSurveySectionId() == null) {
            errorResponse.setCode(ErrorCodeEnum.DATA_VALIDATION.getValue()).reject("data", "Survey Section", "Survey Section can not be empty");
        }

        if (errorResponse.getErrorMessages() != null && errorResponse.getErrorMessages().size() > 0) {
            throw new AssessmentEngineDataValidationException(errorResponse);
        }

        // Call service class here.
        survey = editorsViewDelegate.createSurvey(survey);

        return new Response<>(new ResponseStatus(ResponseStatus.Request.Succeeded), survey); 
    }

    @RequestMapping(value = "/services/surveys/{surveyId}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Response<Map<String, SurveyInfo>> updateSurvey(
            @PathVariable("surveyId") Integer surveyId,
            @RequestBody SurveyInfo survey,
            @CurrentUser EscreenUser escreenUser) {

        survey = editorsViewDelegate.updateSurvey(survey);
        Map<String, SurveyInfo> surveyMap = new HashMap<>();
        surveyMap.put("survey", survey);

        return new Response<>(new ResponseStatus(ResponseStatus.Request.Succeeded), surveyMap);
    }

    @RequestMapping(value = "/services/surveys/{surveyId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Response<SurveyInfo> getSurvey(@PathVariable("surveyId") Integer surveyId,
                              @CurrentUser EscreenUser escreenUser) {
        logger.debug("getSurvey");

        SurveyInfo surveyInfo = editorsViewDelegate.findSurvey(surveyId);
        return new Response<SurveyInfo>(new ResponseStatus(ResponseStatus.Request.Succeeded), surveyInfo);
    }

    @RequestMapping(value = "/services/surveys", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Response<Map<String, List<SurveyInfo>>> getSurveys(@CurrentUser EscreenUser escreenUser) {
        logger.debug("getSurveys");

        List<SurveyInfo> surveyInfoList = editorsViewDelegate.getSurveys();

        Map<String, List<SurveyInfo>> surveyInfoItems = new HashMap<>();
        surveyInfoItems.put("surveys", surveyInfoList);

        return new Response<>(new ResponseStatus(ResponseStatus.Request.Succeeded), surveyInfoItems);
    }

    @RequestMapping(value = "/services/surveys/{surveyId}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public Response<Object> deleteSurvey(@PathVariable("surveyId") Integer surveyId, @CurrentUser EscreenUser escreenUser) {
        //editorsViewDelegate.deleteBattery(surveyId);
        return new Response<>(new ResponseStatus(ResponseStatus.Request.Succeeded), null);
    }


    @RequestMapping(value = "/services/battery", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Map<String, Object> addBattery(@RequestBody BatteryInfo battery,
                          @CurrentUser EscreenUser escreenUser) {
        ErrorResponse errorResponse = new ErrorResponse();

        // Data validation.
        if (StringUtils.isBlank(battery.getName())) {
            errorResponse.setCode(ErrorCodeEnum.DATA_VALIDATION.getValue()).reject("data", "Battery Name", "Battery Name is required.");
        } else if (battery.getName().length() > 50) {
            errorResponse.setCode(ErrorCodeEnum.DATA_VALIDATION.getValue()).reject("data", "Battery Name", "Battery Name should be less than 50 characters.");
        }

        if (errorResponse.getErrorMessages() != null && errorResponse.getErrorMessages().size() > 0) {
            throw new AssessmentEngineDataValidationException(errorResponse);
        }

        // Call service class here.
        BatteryInfo batteryInfo = editorsViewDelegate.createBattery(battery);

        return createBatteryResponse(batteryInfo);
    }

    @RequestMapping(value = "/services/batteries/{batteryId}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Map<String, Object> updateBattery(
            @PathVariable("batteryId") Integer batteryId,
            @RequestBody BatteryInfo battery,
            @CurrentUser EscreenUser escreenUser) {

        ErrorResponse errorResponse = new ErrorResponse();

        // Data validation.
        if (StringUtils.isBlank(battery.getName())) {
            errorResponse.setCode(ErrorCodeEnum.DATA_VALIDATION.getValue()).reject("data", "Battery Name", "Battery Name is required.");
        } else if (battery.getName().length() > 50) {
            errorResponse.setCode(ErrorCodeEnum.DATA_VALIDATION.getValue()).reject("data", "Battery Name", "Battery Name should be less than 50 characters.");
        }

        if (errorResponse.getErrorMessages() != null && errorResponse.getErrorMessages().size() > 0) {
            throw new AssessmentEngineDataValidationException(errorResponse);
        }

        // Call service class here.
        editorsViewDelegate.updateBattery(battery);

        return createBatteryResponse(battery);
    }

    @RequestMapping(value = "/services/batteries/{batteryId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, Object> getBattery(@PathVariable("batteryId") Integer batteryId,
                          @CurrentUser EscreenUser escreenUser) {
        logger.debug("getBattery");

        // Call service class here instead of hard coding it.
        BatteryInfo batteryInfo = editorsViewDelegate.getBattery(batteryId);
        return createBatteryResponse(batteryInfo);
    }

    @RequestMapping(value = "/services/batteries", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, Object> getBatteries(@CurrentUser EscreenUser escreenUser) {
        logger.debug("getBatteries");

        List<BatteryInfo> batteryInfoList = editorsViewDelegate.getBatteries();

        return createBatteriesResponse(batteryInfoList);
    }

    @RequestMapping(value = "/services/batteries/{batteryId}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public Map<String, Map<String, String>> deleteBattery(@PathVariable("batteryId") Integer batteryId, @CurrentUser EscreenUser escreenUser) {
        editorsViewDelegate.deleteBattery(batteryId);
        return createDeleteBatterySuccessfulResponse();
    }

    @ExceptionHandler(AssessmentEngineDataValidationException.class)
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, Map<String, String>> handleException(
            AssessmentEngineDataValidationException ex) {

        logger.error(ex.getErrorResponse().getLogMessage(), ex);
        // returns the error response which contains a list of error messages
        //return ex.getErrorResponse().setStatus(HttpStatus.BAD_REQUEST.value());
        return createRequestFailureResponse(ex.getErrorResponse().getUserMessage("\n"));
    }

    @ExceptionHandler(NotFoundException.class)
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Map<String, Map<String, String>> handleException(NotFoundException e) {
        logger.error("Object not found:", e);

        ErrorResponse er = new ErrorResponse();
        er.setDeveloperMessage(e.getMessage());
        er.setStatus(HttpStatus.NOT_FOUND.value());
        // returns the error response which contains a list of error messages
        //return er;
        return createRequestFailureResponse(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, Map<String, String>> handleException(Exception e) {
        logger.error("Unexpected error:", e);

        ErrorResponse er = new ErrorResponse();
        er.setDeveloperMessage(e.getMessage());
        er.setStatus(HttpStatus.BAD_REQUEST.value());
        // returns the error response which contains a list of error messages
        //return er;
        return createRequestFailureResponse(e.getMessage());
    }

    @RequestMapping(value = "/services/surveySections", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, Object> getSections(@CurrentUser EscreenUser escreenUser) {
        logger.debug("getSections");

        List<SurveySectionInfo> surveySectionInfoList = editorsViewDelegate.getSections();

        return createSectionsResponse(surveySectionInfoList);
    }

    @RequestMapping(value = "/services/surveySections/{sectionId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, Object> getSection(
            @PathVariable("sectionId") Integer sectionId,
            @CurrentUser EscreenUser escreenUser) {
        logger.debug("getSection");

        // Call service class here instead of hard coding it.
        SurveySectionInfo surveySectionInfo = editorsViewDelegate.getSection(sectionId);

        return createSectionResponse(surveySectionInfo);
    }

    @RequestMapping(value = "/services/surveySection", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Map<String, Object> addSection(
            @RequestBody SurveySectionInfo surveySectionInfo,
            @CurrentUser EscreenUser escreenUser) {
        /*logger.debug("addSection");
        ErrorResponse errorResponse = new ErrorResponse();
        if (surveySectionItem != null) {
            logger.debug(surveySectionItem.toString());

            // Data validation.
            if (StringUtils.isBlank(surveySectionItem.getName())) {
                // throw data validation exception
                errorResponse.setCode(ErrorCodeEnum.DATA_VALIDATION.getValue()).reject("data", "Section Name", "Section Name is required.");
            } else if (surveySectionItem.getName().length() > 50) {
                // throw data validation exception
                errorResponse.setCode(ErrorCodeEnum.DATA_VALIDATION.getValue()).reject("data", "Section Name", "Secion Name should be less than 50 characters.");
            }

            // Call service class here.
            Integer surveySectionId = editorDelegate.createSection(surveySectionItem);
            logger.debug("surveySectionId: " + surveySectionId);
        } else {
            errorResponse.setCode(ErrorCodeEnum.DATA_VALIDATION.getValue()).reject("data", "Section Object", "Cannot be null.");
        }

        if (errorResponse.getErrorMessages() != null && errorResponse.getErrorMessages().size() > 0) {
            throw new AssessmentEngineDataValidationException(errorResponse);
        }*/

        return createSectionResponse(surveySectionInfo);
    }

    @RequestMapping(value = "/services/surveySections/{sectionId}", method = {RequestMethod.PUT}, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Map<String, Object> updateSection(
            @PathVariable("sectionId") Integer sectionId,
            @RequestBody SurveySectionInfo surveySectionInfo,
            @CurrentUser EscreenUser escreenUser) {
        logger.debug("updateSection");
        ErrorResponse errorResponse = new ErrorResponse();
        SurveySectionInfo updatedSurveySectionInfo = null;
        if (sectionId != null) {
            // Data validation.
            if (StringUtils.isBlank(surveySectionInfo.getName())) {
                // throw data validation exception
                errorResponse.setCode(ErrorCodeEnum.DATA_VALIDATION.getValue()).reject("data", "Survey Section Name", "Section Name is required.");
            } else if (surveySectionInfo.getName().length() > 50) {
                // throw data validation exception
                errorResponse.setCode(ErrorCodeEnum.DATA_VALIDATION.getValue()).reject("data", "Survey Section Name", "Section Name should be less than 50 characters.");
            }

            if (errorResponse.getErrorMessages() != null && errorResponse.getErrorMessages().size() > 0) {
                throw new AssessmentEngineDataValidationException(errorResponse);
            }

            // Call service class here.
            surveySectionInfo.setSurveySectionId(sectionId);
            updatedSurveySectionInfo = editorsViewDelegate.updateSection(surveySectionInfo);
        }

        return createSectionResponse(updatedSurveySectionInfo);
    }

    @RequestMapping(value = "/services/surveySections/{sectionId}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public Map<String, Map<String, String>> deleteSection(
            @PathVariable("sectionId") Integer sectionId,
            @CurrentUser EscreenUser escreenUser) {
        editorsViewDelegate.deleteSection(sectionId);
        return createDeleteSectionSuccessfulResponse();
    }

    private Map<String, Object> createSectionsResponse(List<SurveySectionInfo> surveySectionInfoList) {
    	Map<String, String> status = new HashMap<>();
        status.put("message", "The Quick Brown fox jumps over the lazy dog");
        status.put("status", surveySectionInfoList != null && !surveySectionInfoList.isEmpty() ? "succeeded" : "failed");

        Map<String, List<SurveySectionInfo>> surveySectionItems = new HashMap<>();
        surveySectionItems.put("surveySections", surveySectionInfoList);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", status);
        responseMap.put("payload", surveySectionItems);

        return responseMap;
    }

    private Map<String, Map<String, String>> createRequestFailureResponse(String message) {
    	Map<String, String> status = new HashMap<>();
        status.put("message", message);
        status.put("status", "failed");

        Map<String, Map<String, String>> responseMap = new HashMap<>();
        responseMap.put("status", status);
        responseMap.put("payload", new HashMap<String,String>());

        return responseMap;
    }

    private Map<String, Map<String, String>> createDeleteSectionSuccessfulResponse() {
    	Map<String, String> status = new HashMap<>();
        status.put("message", "Section deleted successfully.");
        status.put("status", "succeeded");

        Map<String, Map<String, String>> responseMap = new HashMap<>();
        responseMap.put("status", status);
        responseMap.put("payload", new HashMap<String, String>());

        return responseMap;
    }

    private Map<String, Map<String, String>>  createDeleteBatterySuccessfulResponse() {
        Map<String, String> status = new HashMap<>();
        status.put("message", "Battery deleted successfully.");
        status.put("status", "succeeded");

        Map<String, Map<String, String>> responseMap = new HashMap<>();
        responseMap.put("status", status);
        responseMap.put("payload", new HashMap<String, String>());

        return responseMap;
    }


    private Map<String, Object> createSectionResponse(SurveySectionInfo surveySection) {
    	Map<String,String> status = new HashMap<>();
        status.put("message", (surveySection != null && surveySection.getSurveySectionId() != null) ? "Section created successfully." : "There was a problem processing your request.  If problem continues to exist, please contact the system administrator.");
        status.put("status", (surveySection != null && surveySection.getSurveySectionId() != null) ? "succeeded" : "failed");

        Map<String, SurveySectionInfo> surveySectionItems = new HashMap<>();
        surveySectionItems.put("surveySection", surveySection);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", status);
        responseMap.put("payload", surveySectionItems);

        return responseMap;
    }

    private Map<String, Object> createBatteryResponse(BatteryInfo batteryInfo) {
        Map<String,String> status = new HashMap<>();
        status.put("message", (batteryInfo != null && batteryInfo.getBatteryId() != null) ? "Battery created successfully." : "There was a problem processing your request.  If problem continues to exist, please contact the system administrator.");
        status.put("status", (batteryInfo != null && batteryInfo.getBatteryId() != null) ? "succeeded" : "failed");

        Map<String, BatteryInfo> batteryMap = new HashMap<>();
        batteryMap.put("battery", batteryInfo);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", status);
        responseMap.put("payload", batteryMap);

        return responseMap;
    }

    private Map<String, Object> createBatteriesResponse(List<BatteryInfo> batteryInfoList) {
    	Map<String,String> status = new HashMap<>();
        status.put("message", batteryInfoList != null && !batteryInfoList.isEmpty() ? "Batteries created successfully." : "There was a problem processing your request.  If problem continues to exist, please contact the system administrator.");
        status.put("status", batteryInfoList != null && !batteryInfoList.isEmpty() ? "succeeded" : "failed");

        Map<String, List<BatteryInfo>> surveySectionItems = new HashMap<>();
        surveySectionItems.put("batteries", batteryInfoList);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", status);
        responseMap.put("payload", surveySectionItems);

        return responseMap;
    }
}
