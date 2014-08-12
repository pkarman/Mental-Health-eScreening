package gov.va.escreening.controller;

import gov.va.escreening.domain.SurveyDto;
import gov.va.escreening.domain.VeteranAssessmentDto;
import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.dto.DropDownObject;
import gov.va.escreening.entity.Clinic;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.ClinicService;
import gov.va.escreening.service.SurveyService;
import gov.va.escreening.service.UserService;
import gov.va.escreening.service.VeteranAssessmentService;
import gov.va.escreening.service.VeteranService;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/assessAdmin")
public class AssessmentAdminRestController {

    private static final Logger logger = LoggerFactory.getLogger(AssessmentAdminRestController.class);

    @Autowired
    private ClinicService clinicService;
    @Autowired
    private UserService userService;
    @Autowired
    private VeteranService veteranService;
    @Autowired
    private SurveyService surveyService;
    @Autowired
    private VeteranAssessmentService veteranAssessmentService;

    /*
     * Description of getClinicsForDropdown() Returns a json list containing all clinic id and clinic names Example
     * output: [ {"stateId":"9","stateName":"Primary Care - Oceanside"},
     * "stateId":"8","stateName":"Primary Care - Mission Valley" }]
     */
    @RequestMapping(value = "/services/getclinicsForDropdown", method = RequestMethod.GET)
    @ResponseBody
    public List<DropDownObject> getClinicsForDropdown() {
        logger.debug("In getallclinicnames");

        List<Clinic> clinics = clinicService.getClinics();
        List<DropDownObject> dropDownList = new ArrayList<DropDownObject>();

        for (Clinic clinic : clinics) {
            DropDownObject dropDown = new DropDownObject(String.valueOf(clinic.getClinicId()), clinic.getName());
            dropDownList.add(dropDown);
        }

        return dropDownList;
    }

    /*
     * Description of getCliniciansByClinic() Returns a json list of users of type clinician for the passed in clinicid
     * Example output: [{"stateId":"Clinicianone","stateName":"Clinician, One"}]
     */
    // @RequestMapping(value = "/services/clinics/{clinicId}/clinicians", method = RequestMethod.GET)
    // @ResponseBody
    // public List<DropDownObject> getCliniciansByClinic(@PathVariable int clinicId) {
    // logger.debug("In getCliniciansByClinic");
    //
    // return userService.getClinicianDropDownObjects(clinicId);
    // }

    /*
     * Description of getVeteransByLastNameLastFour() Returns a json list of users of type Veteran by the Veteran's last
     * name and Veteran's last 4 of their SSN. Example output:
     * [{"loginId":"loginid","password":null,"firstName":"First",
     * "middleName":"Middle","lastName":"Last","emailAddress":"example@nowhere.com"
     * ,"emailAddress2":"example2@nowhere.com"
     * ,"phoneNumber":"0123456789","phoneNumber2":"0123456789","birthDate":"1980-01-01"
     * ,"userStatus":{"id":1,"description"
     * :"Active"},"clinics":[],"role":{"id":6,"roleName":"Veteran"},"lastFirst":"Last, First","ssn":"1111"}]
     */
    @RequestMapping(value = "/services/veterans/{lastName}/{lastfour}", method = RequestMethod.GET)
    @ResponseBody
    @Deprecated
    public List<VeteranDto> getVeteransByLastNameLastFour(@PathVariable String lastName, @PathVariable String lastfour) {
        logger.debug("In getVeteransByLastNameLastFour");

        VeteranDto veteran = new VeteranDto();

        veteran.setLastName(lastName);
        veteran.setSsnLastFour(lastfour);

        List<VeteranDto> veterans = veteranService.findVeterans(veteran);

        return veterans;
    }

    /*
     * Description for getAllSurveys() Returns a list of all versions of all surveys in json format Example output:
     * [{"surveyId":1,"version":1,"description" :"Demographics","order":1,"surveySection"
     * :{"surveySectionId":1,"description":"Demographics and Social Information" ,"order":1}}]
     */
    @RequestMapping(value = "/services/survey/getAllSurveys", method = RequestMethod.GET)
    @ResponseBody
    public List<SurveyDto> getAllSurveys() {
        logger.debug("In getAllSurveys");

        List<SurveyDto> surveys = surveyService.getAssignableSurveys();
        return surveys;
    }

    @RequestMapping(value = "/assessments", method = RequestMethod.GET)
    @ResponseBody
    @Deprecated
    public List<VeteranAssessmentDto> getAssessments(HttpServletRequest request, @CurrentUser EscreenUser escreenUser) {

        logger.debug("getAssessments()");

        List<VeteranAssessmentDto> veteranAssessments = veteranAssessmentService.getAssessmentListForProgramIdList(escreenUser.getProgramIdList());

        logger.debug("veteranAssessments.size(): " + veteranAssessments.size());

        return veteranAssessments;
    }
}
