package gov.va.escreening.controller.dashboard;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.va.escreening.domain.ErrorCodeEnum;
import gov.va.escreening.dto.ae.ErrorBuilder;
import gov.va.escreening.dto.ae.ErrorResponse;
import gov.va.escreening.exception.EntityNotFoundException;
import gov.va.escreening.service.AssessmentVariableService;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.google.common.collect.Maps;
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

    @RequestMapping(value = "/services/assessmentVariables", params = "surveyId", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Map<String, Object>> getAssessmentVarsForSurvey(@RequestParam("surveyId") Integer surveyId) {

        if (surveyId == null || surveyId < 0) {
			ErrorBuilder.throwing(EntityNotFoundException.class).toUser("Invalid or missing module ID.  Please contact your system administrator.").toAdmin("The survey id passed in is 0 or null").setCode(ErrorCodeEnum.OBJECT_NOT_FOUND.getValue()).throwIt();
        }

        Table<String, String, Object> t = avs.getAssessmentVarsForSurvey(surveyId, true, false);

        if (t.isEmpty()) {
            ErrorBuilder
                    .throwing(EntityNotFoundException.class)
				.toUser("No variables were found for this module.  Please contact your system administrator.")
				.toAdmin(String.format("No assessment variables were found for Survey with an Id of %s", surveyId))
                    .setCode(ErrorCodeEnum.OBJECT_NOT_FOUND.getValue()).throwIt();
        }
        return avTableToList(t);
    }

    @RequestMapping(value = "/services/avs2MngFormulas", params = "moduleId", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Map<String, Object>> getAssessmentVarsToMngFormulas(@RequestParam("moduleId") Integer moduleId) {
        if (moduleId == null || moduleId < 0) {
            ErrorBuilder.throwing(EntityNotFoundException.class).toUser("Sorry, we are unable to process your request at this time.  If this continues, please contact your system administrator.").toAdmin("The survey id passed in is 0 or null").setCode(ErrorCodeEnum.OBJECT_NOT_FOUND.getValue()).throwIt();
        }

        Table<String, String, Object> t = avs.getAssessmentVarsForSurvey(moduleId, false, true);

        if (t.isEmpty()) {
            ErrorBuilder
                    .throwing(EntityNotFoundException.class)
                    .toUser("Sorry, we are unable to process your request at this time.  If this continues, please contact your system administrator.")
                    .toAdmin(String.format("No Measures were found to be available for Survey with an Id of %s", moduleId))
                    .setCode(ErrorCodeEnum.OBJECT_NOT_FOUND.getValue()).throwIt();
        }
        List<Map<String, Object>> avs = avTableToList(t);
        if (avs != null) {
            // remove Assessment Variables which are CUSTOM TYPE
//            for (Iterator<Map<String, Object>> mapIter = avs.iterator(); mapIter.hasNext(); ) {
//                Map<String, Object> avDataMap = mapIter.next();
//                if (avDataMap.get("typeId").equals(3)) {
//                    mapIter.remove();
//                }
//            }

            // assign a unique guid to allow duplicate use of assessment variables inside  formula
            for (Map<String, Object> m : avs) {
                int typeId = (int) m.get("typeId");
                m.put("type", typeId == 1 ? "Question" : typeId == 2 ? "Answer" : typeId == 3 ? "Custom" : typeId == 4 ? "Formula" : "Operator");
                m.put("guid", System.nanoTime());
            }
        }
        return avs;
    }

    @RequestMapping(value = "/services/assessmentVariables", params = "batteryId", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Map<String, Object>> getAssessmentVarsForBattery(@RequestParam("batteryId") Integer batteryId) {

        if (batteryId == null || batteryId < 0) {
            ErrorBuilder.throwing(EntityNotFoundException.class).toUser("Sorry, we are unable to process your request at this time.  If this continues, please contact your system administrator.").toAdmin("The survey id passed in is 0 or null").setCode(ErrorCodeEnum.OBJECT_NOT_FOUND.getValue()).throwIt();
        }

        Table<String, String, Object> t = avs.getAssessmentVarsForBattery(batteryId);

        if (t.isEmpty()) {
            ErrorBuilder
                    .throwing(EntityNotFoundException.class)
                    .toUser("Sorry, we are unable to process your request at this time.  If this continues, please contact your system administrator.")
                    .toAdmin(String.format("No Assessment Variables were found for Battery with an Id of %s", batteryId))
                    .setCode(ErrorCodeEnum.OBJECT_NOT_FOUND.getValue()).throwIt();
        }
        return avTableToList(t);
    }

    private List<Map<String, Object>> avTableToList(Table<String, String, Object> t) {

        List<Map<String, Object>> avs = Lists.newArrayList();

        for (String rowKey : t.rowKeySet()) {
            Map<String, Object> m = Maps.newHashMap(t.row(rowKey)); // need HashMap as it allows nulls as key or values

            // remove properties with 0 -- only send that data on the wire which makes sense
            for (Iterator<Entry<String, Object>> eIter = m.entrySet().iterator(); eIter.hasNext(); ) {
                Entry<String, Object> e = eIter.next();
                if (e.getValue().equals(0)) {
                    eIter.remove();
                }
            }
            avs.add(m);
        }

        return avs;
    }

}
