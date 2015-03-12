package gov.va.escreening.controller.dashboard;

import static com.google.common.base.Preconditions.checkNotNull;
import gov.va.escreening.controller.RestController;
import gov.va.escreening.delegate.EditorsViewDelegate;
import gov.va.escreening.dto.EventDto;
import gov.va.escreening.webservice.Response;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value = "/dashboard/services")
public class RuleRestController extends RestController {

    private static final Logger logger = LoggerFactory
            .getLogger(RuleRestController.class);

    private final EditorsViewDelegate editorDelegate;

    @Autowired
    RuleRestController(EditorsViewDelegate editorDelegate) {
        this.editorDelegate = checkNotNull(editorDelegate);
    }

    @RequestMapping(value = "/events", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response<List<EventDto>> getEvents(HttpServletRequest request) {
        logRequest(logger, request);

        Integer typeId = null;
        String typeString = request.getParameter("type");
        if(typeString != null){
            typeId = Integer.valueOf(typeString);
        }
        
        return successResponse(editorDelegate.getEvents(typeId));
    }

}
