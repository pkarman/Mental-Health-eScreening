package gov.va.escreening.controller.dashboard;

import gov.va.escreening.delegate.ReportDelegate;
import gov.va.escreening.domain.ClinicDto;
import gov.va.escreening.domain.SurveyDto;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.util.ReportsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/dashboard")
public class ReportsController {

    @Resource(type = ReportDelegate.class)
    ReportDelegate rd;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/reports", method = RequestMethod.GET)
    public String setUpPageListView(Model model) {
        return "reports";
    }

    @RequestMapping(value = "/listSurveys", method = RequestMethod.GET)
    @ResponseBody
    public List<SurveyDto> getAllSurveys() {
        return rd.getSurveyList();
    }

    @RequestMapping(value = "/listClinics", method = RequestMethod.GET)
    @ResponseBody
    public List<ClinicDto> getAllClinics() {
        return rd.getClinicDtoList();
    }

    // ticket 601 related
    @RequestMapping(value = "/individualStatisticsReports", method = RequestMethod.GET)
    public ModelAndView getIindividualStatisticReports() {
        return new ModelAndView("individualStatisticsReports");
    }

    @RequestMapping(value = "/individualStatisticsGraphicAndNumber", method = RequestMethod.POST)
    public ModelAndView genIndividualStatisticsGraphicAndNumber(@RequestBody Map<String, Object> requestData, @CurrentUser EscreenUser escreenUser) {
        return getIndividualStaticsGraphicPDF(requestData, escreenUser, "individualStatisticsGraphNumberReport");
    }

    /**
     * REST endpoint will receive an list of svg objects plus any other data required to render a graphical report
     * This will simply prepare the required data + svg objects for the target Jasper report and forward
     *
     * @param requestData
     * @param escreenUser
     * @return
     */
    @RequestMapping(value = "/individualStatisticsGraphic", method = RequestMethod.POST)
    public ModelAndView genIndividualStatisticsGraphic(@RequestBody Map<String, Object> requestData, @CurrentUser EscreenUser escreenUser) {

        return getIndividualStaticsGraphicPDF(requestData, escreenUser, "individualStatisticsGraphReport");

    }

    @RequestMapping(value = "/averageScoresForPatientsByClinic", method = RequestMethod.GET)
    public ModelAndView getAverageScoresForPatientsByClinic() {
        return new ModelAndView("averageScoresForPatientsByClinic");
    }

    @RequestMapping(value = "/avgScoresVetByClinicGraphicNumeric", method = RequestMethod.POST)
    public ModelAndView genAvgScoresVetByClinicGraphicNumeric(@RequestBody Map<String, Object> requestData, @CurrentUser EscreenUser escreenUser) {
        return genIndividualStatisticsGraphic(requestData, escreenUser);
    }

    @RequestMapping(value = "/avgScoresVetByClinicGraphic", method = RequestMethod.POST)
    public ModelAndView genAvgScoresVetByClinicGraphic(@RequestBody Map<String, Object> requestData, @CurrentUser EscreenUser escreenUser) {

        // ticket 600 entry point graph chart
        if ("groupData".equals(requestData.get(ReportsUtil.DISPLAY_OPTION))) {
            //Group Chart
            return getAveScoresByClinicGraphOrNumeric(requestData, escreenUser, false);
        } else {
            // individual chart
            return getAvgScoresVetByClinicGraphReport(requestData, escreenUser);
        }
    }

    // for avg individual
    private ModelAndView getAvgScoresVetByClinicGraphReport(Map<String, Object> requestData, EscreenUser escreenUser) {

        return new ModelAndView("avgVetClinicGraphReport", rd.getAvgScoresVetByClinicGraphReport(requestData, escreenUser));
    }

    @RequestMapping(value = "/avgScoresVetByClinicGraphicNumber", method = RequestMethod.POST)
    public ModelAndView genAvgScoresVetByClinicGraphicNumber(@RequestBody Map<String, Object> requestData, @CurrentUser EscreenUser escreenUser) {

        // ticket 600 entry point after getting graph and numeric report
        return getAveScoresByClinicGraphOrNumeric(requestData, escreenUser, true);
    }


    // FOR GROUP
    private ModelAndView getAveScoresByClinicGraphOrNumeric(Map<String, Object> requestData, EscreenUser escreenUser, boolean includeCount) {
        return new ModelAndView("avgClinicGraphReport", rd.getAveScoresByClinicGraphOrNumeric(requestData, escreenUser, includeCount));
    }

    @RequestMapping(value = "/avgScoresVetByClinicNumeric", method = RequestMethod.POST)
    public ModelAndView genAvgScoresVetByClinicNumeric(@RequestBody HashMap<String, Object> requestData, @CurrentUser EscreenUser escreenUser) {
        return new ModelAndView("", rd.genAvgScoresVetByClinicNumeric(requestData, escreenUser));
    }

    private ModelAndView getIndividualStaticsGraphicPDF(Map<String, Object> requestData, EscreenUser escreenUser, String viewName) {
        return new ModelAndView(viewName, rd.getIndividualStaticsGraphicPDF(requestData, escreenUser));
    }

    @RequestMapping(value = "/requestChartableData", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> requestChartableData(@RequestBody Map<String, Object> requestData, @CurrentUser EscreenUser escreenUser) {

        if ("avgScoresForPatientsByClinic".equals(requestData.get(ReportsUtil.REPORT_TYPE))) {
            if ("groupData".equals(requestData.get(ReportsUtil.DISPLAY_OPTION))) {
                return createChartableDataFor601Clinic(requestData);
            } else {
                return createChartableDataFor601VeteranClinic(requestData);
            }
        }

        return createChartableDataForIndividualStats(requestData);
    }

    private List<Map<String, Object>> createChartableDataFor601VeteranClinic(Map<String, Object> requestData) {
        return rd.createChartableDataFor601VeteranClinic(requestData);
    }

    private List<Map<String, Object>> createChartableDataFor601Clinic(Map<String, Object> requestData) {
        return rd.createChartableDataFor601Clinic(requestData);
    }

    private List<Map<String, Object>> createChartableDataForIndividualStats(Map<String, Object> requestData) {
        return rd.createChartableDataForIndividualStats(requestData);
    }


    @RequestMapping(value = "/individualStatisticsNumeric", method = RequestMethod.POST)
    public ModelAndView genIndividualStatisticsNumeric(@RequestBody Map<String, Object> requestData, @CurrentUser EscreenUser escreenUser) {
        return new ModelAndView("IndividualStatisticsReportsNumericOnlyReport", rd.genIndividualStatisticsNumeric(requestData, escreenUser));
    }

    @RequestMapping(value = "/clinicStatisticReportsPartVDemographicsReport", method = RequestMethod.GET)
    public ModelAndView getClinicStatisticReportsPartVDemographicsReport() {
        return new ModelAndView("clinicStatisticReportsPartVDemographicsReport");
    }

    @RequestMapping(value = "/clinicStatisticReportsPartIVAverageTimePerModuleReport", method = RequestMethod.GET)
    public ModelAndView getClinicStatisticReportsPartIVAverageTimePerModuleReport() {
        return new ModelAndView("clinicStatisticReportsPartIVAverageTimePerModuleReport");
    }

    @RequestMapping(value = "/clinicStatisticReportsPart1eScreeningBatteriesReport", method = RequestMethod.GET)
    public ModelAndView getClinicStatisticReportsPart1eScreeningBatteriesReport() {
        return new ModelAndView("clinicStatisticReportsPart1eScreeningBatteriesReport");
    }

}
