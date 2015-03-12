package gov.va.escreening.controller.dashboard;

import static com.google.common.base.Preconditions.checkNotNull;
import gov.va.escreening.controller.RestController;
import gov.va.escreening.delegate.EditorsDelegate;
import gov.va.escreening.dto.EventDto;
import gov.va.escreening.dto.RuleDto;
import gov.va.escreening.dto.template.TemplateFileDTO;
import gov.va.escreening.service.RuleService;
import gov.va.escreening.webservice.Response;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value = "/dashboard/services")
public class RuleRestController extends RestController {

    private static final Logger logger = LoggerFactory
            .getLogger(RuleRestController.class);

    private final RuleService ruleService;

    @Autowired
    RuleRestController(RuleService ruleService) {
        this.ruleService = checkNotNull(ruleService);
    }
    
    /*  RULES */
    
    @RequestMapping(value = "/rules", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response<List<RuleDto>> getRules(HttpServletRequest request) {
        logRequest(logger, request);

        return successResponse(ruleService.getRules());
    }
    
    @RequestMapping(value = "/rules", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Response<RuleDto> addRule(
            @RequestBody RuleDto rule,
            HttpServletRequest request) {
        logRequest(logger, request);

        return successResponse(ruleService.createRule(rule));
    }
    
    @RequestMapping(value = "/rules/{ruleId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response<RuleDto> getRule(
            @PathVariable("ruleId") Integer ruleId,
            HttpServletRequest request) {
        logRequest(logger, request);

        return successResponse(ruleService.getRule(ruleId));
    }
    
    @RequestMapping(value = "/rules/{ruleId}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response<RuleDto> updateRule(
            @PathVariable("ruleId") Integer ruleId,
            @RequestBody RuleDto rule,
            HttpServletRequest request) {
        logRequest(logger, request);

        return successResponse(ruleService.updateRule(ruleId, rule));
    }
    
    @RequestMapping(value = "/rules/{ruleId}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response<Integer> deleteRule(
            @PathVariable("ruleId") Integer ruleId,
            HttpServletRequest request) {
        logRequest(logger, request);

        ruleService.deleteRule(ruleId);
        return successResponse(ruleId);
    }
    
    /*  RULE'S EVENTS */
    
    @RequestMapping(value = "/rules/{ruleId}/events", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response<List<EventDto>> getRuleEvents(
            @PathVariable("ruleId") Integer ruleId,
            HttpServletRequest request) {
        logRequest(logger, request);

        return successResponse(ruleService.getRuleEvents(ruleId));
    }
    
    /**
     * Add an event to a rule
     * @param ruleId
     * @param event
     * @param request
     * @return the added event
     */
    @RequestMapping(value = "/rules/{ruleId}/events", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Response<EventDto> Rule(
            @PathVariable("ruleId") Integer ruleId,
            @RequestBody EventDto event,
            HttpServletRequest request) {
        logRequest(logger, request);

        return successResponse(ruleService.addEventToRule(ruleId, event));
    }
    
    @RequestMapping(value = "/rules/{ruleId}/events/{eventId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response<EventDto> getRuleEvent(
            @PathVariable("ruleId") Integer ruleId,
            @PathVariable("eventId") Integer eventId,
            HttpServletRequest request) {
        logRequest(logger, request);

        return successResponse(ruleService.getRuleEvent(ruleId, eventId));
    }
    
    @RequestMapping(value = "/rules/{ruleId}/events/{eventId}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response<Integer> deleteRuleEvent(
            @PathVariable("ruleId") Integer ruleId,
            @PathVariable("eventId") Integer eventId,
            HttpServletRequest request) {
        logRequest(logger, request);

        ruleService.deleteRuleEvent(ruleId, eventId);
        return successResponse(eventId);
    }
    

    /* EVENTS */
    
    @RequestMapping(value = "/events", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response<List<EventDto>> getEvents(HttpServletRequest request) {
        logRequest(logger, request);

        String typeString = request.getParameter("type");
        
        if (typeString != null) {
            Integer typeId = Integer.valueOf(typeString);
            return successResponse(ruleService.getEventsByType(typeId));
        }
        return successResponse(ruleService.getAllEvents());
    }
    
}
