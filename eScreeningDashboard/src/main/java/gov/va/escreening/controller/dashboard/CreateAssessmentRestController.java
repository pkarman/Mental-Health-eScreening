package gov.va.escreening.controller.dashboard;

import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.dto.DataTableResponse;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.VeteranService;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/dashboard")
public class CreateAssessmentRestController {

    private static final Logger logger = LoggerFactory.getLogger(CreateAssessmentRestController.class);

    @Autowired
    private VeteranService veteranService;

    @RequestMapping(value = "/veteranSearch/services/veterans/search2", method = RequestMethod.POST)
    @ResponseBody
    public DataTableResponse<VeteranDto> getVeteransByLastNameLastFour(HttpServletRequest request,
            @CurrentUser EscreenUser escreenUser) {

        logger.debug("In getVeteransByLastNameLastFour");

        String lastName = request.getParameter("lastName");
        String ssnLastFour = request.getParameter("ssnLastFour");

        List<VeteranDto> veterans;

        if (StringUtils.isBlank(lastName) || StringUtils.isBlank(ssnLastFour)) {
            veterans = new ArrayList<VeteranDto>();
        }
        else {
            VeteranDto veteran = new VeteranDto();
            veteran.setLastName(lastName);
            veteran.setSsnLastFour(ssnLastFour);

            veterans = veteranService.findVeterans(veteran);
        }

        DataTableResponse<VeteranDto> dataTableResponse = new DataTableResponse<VeteranDto>();
        dataTableResponse.setTotalRecords(veterans.size());
        dataTableResponse.setTotalDisplayRecords(veterans.size());

        dataTableResponse.setData(veterans);

        return dataTableResponse;
    }

    @RequestMapping(value = "/veteranSearch/services/veterans/search3", method = RequestMethod.POST)
    @ResponseBody
    public List<VeteranDto> getVeteransByLastNameLastFour3(@RequestBody VeteranDto veteranDto,
            @CurrentUser EscreenUser escreenUser) {

        logger.debug("In getVeteransByLastNameLastFour3");

        String lastName = veteranDto.getLastName();
        String ssnLastFour = veteranDto.getSsnLastFour();

        logger.debug(lastName + " " + ssnLastFour);

        List<VeteranDto> veterans;

        if (StringUtils.isBlank(lastName) || StringUtils.isBlank(ssnLastFour)) {
            veterans = new ArrayList<VeteranDto>();
        }
        else {
            VeteranDto veteran = new VeteranDto();
            veteran.setLastName(lastName);
            veteran.setSsnLastFour(ssnLastFour);

            veterans = veteranService.findVeterans(veteran);
        }

        return veterans;
    }

}
