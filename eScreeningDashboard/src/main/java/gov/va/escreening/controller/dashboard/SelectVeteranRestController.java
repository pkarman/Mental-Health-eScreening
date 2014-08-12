package gov.va.escreening.controller.dashboard;

import gov.va.escreening.delegate.CreateAssessmentDelegate;
import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/dashboard")
public class SelectVeteranRestController extends BaseDashboardRestController {

    private static final Logger logger = LoggerFactory.getLogger(SelectVeteranRestController.class);

    private CreateAssessmentDelegate createAssessmentDelegate;

    @Autowired
    public void setCreateAssessmentDelegate(CreateAssessmentDelegate createAssessmentDelegate) {
        this.createAssessmentDelegate = createAssessmentDelegate;
    }

    @RequestMapping(value = "/selectVeteran/veterans", method = RequestMethod.POST)
    @ResponseBody
    public VeteranDto getVeteran(HttpServletRequest request, @CurrentUser EscreenUser escreenUser) {

        logger.debug("In VeteranSearchRestController searchVeterans");

        // Get the search criteria sent by the client.
        // Need to figure out how we can get Spring to serialize this instead of doing this ourselves.
        String veteranIdString = request.getParameter("vid");
        String veteranIen = request.getParameter("vien");

        Integer veteranId = null;

        if (StringUtils.isNotBlank(veteranIdString) && StringUtils.isNumeric(veteranIdString)) {
            veteranId = Integer.valueOf(veteranIdString);
        }

        VeteranDto veteranDto = createAssessmentDelegate.fetchVeteran(escreenUser, veteranId, veteranIen, false);

        return veteranDto;
    }
}
