package gov.va.escreening.controller.dashboard;

import com.google.common.collect.Maps;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.service.SurveyMeasureResponseService;
import gov.va.escreening.service.SurveyService;
import gov.va.escreening.service.VeteranAssessmentService;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/dashboard")
public class AssessmentPreviewController {

    private static final Logger logger = LoggerFactory.getLogger(AssessmentPreviewController.class);

    @Autowired
    private VeteranAssessmentService veteranAssessmentService;
    @Autowired
    private SurveyMeasureResponseService surveyMeasureResponseService;
    @Autowired
    private SurveyService surveyService;

    @RequestMapping(value = "/assessmentPreview", method = RequestMethod.GET)
    public String setupPage(Model model, @RequestParam(value = "vaid", required = false) Integer veteranAssessmentId) {

        logger.trace("In AssessmentReportController::setupPageAssessmentReport");

        logger.trace("veteranId: " + veteranAssessmentId);

        // Get the veteran assessment.
        VeteranAssessment veteranAssessment = veteranAssessmentService.findByVeteranAssessmentId(veteranAssessmentId);
        model.addAttribute("veteranAssessment", veteranAssessment);

        List<Survey> surveyList = surveyService.findForVeteranAssessmentId(veteranAssessmentId);
        model.addAttribute("surveyList", surveyList);

        Map<Integer, Integer> measureQuestionMap = Maps.newHashMap();

        Hashtable<Integer, List<SurveyMeasureResponse>> surveyMeasureResponseMap = surveyMeasureResponseService
                .findForVeteranAssessmentId(veteranAssessmentId, measureQuestionMap);
        model.addAttribute("surveyMeasureResponseMap", surveyMeasureResponseMap);
        model.addAttribute("measureQuestionMap", measureQuestionMap);

        return "dashboard/assessmentPreview";
    }
}
