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
import java.util.LinkedHashMap;
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

    @RequestMapping(value = "/individualStatisticsGraphic", method = RequestMethod.POST)
    public ModelAndView genIndividualStatisticsGraphic(@RequestBody Map<String, Object> requestData, @CurrentUser EscreenUser escreenUser) {

        return getIndividualStaticsGraphicPDF(requestData, escreenUser, "individualStatisticsGraphReport");

    }

    @RequestMapping(value = "/individualStatisticsNumeric", method = RequestMethod.POST)
    public ModelAndView genIndividualStatisticsNumeric(@RequestBody Map<String, Object> requestData, @CurrentUser EscreenUser escreenUser) {
        return new ModelAndView("IndividualStatisticsReportsNumericOnlyReport", rd.genIndividualStatisticsNumeric(requestData, escreenUser));
    }

    // ticket 600 related
    @RequestMapping(value = "/averageScoresForPatientsByClinic", method = RequestMethod.GET)
    public ModelAndView getAverageScoresForPatientsByClinic() {
        return new ModelAndView("averageScoresForPatientsByClinic");
    }

    @RequestMapping(value = "/avgScoresVetByClinicGraphic", method = RequestMethod.POST)
    public ModelAndView genAvgScoresVetByClinicGraphic(@RequestBody Map<String, Object> requestData, @CurrentUser EscreenUser escreenUser) {

        // ticket 600 entry point graph chart
        if ("groupData".equals(((LinkedHashMap<String, Object>) requestData.get("userReqData")).get(ReportsUtil.DISPLAY_OPTION))) {
            //Group Chart
            return getAveScoresByClinicGraphOrNumeric(requestData, escreenUser, false);
        } else {
            // individual chart
            return getAvgScoresVetByClinicGraphReport(requestData, escreenUser);
        }
    }

    @RequestMapping(value = "/avgScoresVetByClinicGraphicNumber", method = RequestMethod.POST)
    public ModelAndView genAvgScoresVetByClinicGraphicNumber(@RequestBody Map<String, Object> requestData, @CurrentUser EscreenUser escreenUser) {

        // ticket 600 entry point after getting graph and numeric report
        return getAveScoresByClinicGraphOrNumeric(requestData, escreenUser, true);
    }

    @RequestMapping(value = "/avgScoresVetByClinicNumeric", method = RequestMethod.POST)
    public ModelAndView genAvgScoresVetByClinicNumeric(@RequestBody HashMap<String, Object> requestData, @CurrentUser EscreenUser escreenUser) {

        LinkedHashMap<String, Object> userData = new LinkedHashMap<>();

        for (String key : requestData.keySet()) {
            userData.put(key, requestData.get(key));
        }

        requestData.put("userReqData", userData);

        if ("groupData".equals((requestData).get(ReportsUtil.DISPLAY_OPTION))) {
            //Group report
            return getAveScoresByClinicNumericReport(requestData, escreenUser);
        } else {
            // individual report
            return getAvgScoresVetByClinicNumericReport(requestData, escreenUser);
        }
    }

    // ticket 597
    @RequestMapping(value = "/clinicStatisticReportsPartVDemographicsReport", method = RequestMethod.GET)
    public ModelAndView getClinicStatisticReportsPartVDemographicsReport() {
        return new ModelAndView("clinicStatisticReportsPartVDemographicsReport");
    }

    @RequestMapping(value = "/clinicStatisticReportsPartVDemographicsReport", method = RequestMethod.POST)
    public ModelAndView genClinicStatisticReportsPartVDemographicsReport(@RequestBody HashMap<String, Object> requestData, @CurrentUser EscreenUser escreenUser) {
        return new ModelAndView("demographicsReport", rd.genClinicStatisticReportsPartVDemographicsReport(requestData, escreenUser));
    }

    // ticket 596
    @RequestMapping(value = "/clinicStatisticReportsPartIVAverageTimePerModuleReport", method = RequestMethod.GET)
    public ModelAndView getClinicStatisticReportsPartIVAverageTimePerModuleReport() {
        return new ModelAndView("clinicStatisticReportsPartIVAverageTimePerModuleReport");
    }

    @RequestMapping(value = "/clinicStatisticReportsPartIVAverageTimePerModuleReport", method = RequestMethod.POST)
    public ModelAndView genClinicStatisticReportsPartIVAverageTimePerModuleReport(@RequestBody HashMap<String, Object> requestData, @CurrentUser EscreenUser escreenUser) {
        return new ModelAndView("averageTimePerModuleReport", rd.genClinicStatisticReportsPartIVAverageTimePerModuleReport(requestData, escreenUser));
    }


    // ticket 593
    @RequestMapping(value = "/clinicStatisticReportsPart1eScreeningBatteriesReport", method = RequestMethod.GET)
    public ModelAndView getClinicStatisticReportsPart1eScreeningBatteriesReport() {
        return new ModelAndView("clinicStatisticReportsPart1eScreeningBatteriesReport");
    }

    @RequestMapping(value = "/clinicStatisticReportsPart1eScreeningBatteriesReport", method = RequestMethod.POST)
    public ModelAndView genClinicStatisticReportsPart1eScreeningBatteriesReport(@RequestBody HashMap<String, Object> requestData, @CurrentUser EscreenUser escreenUser) {
        return new ModelAndView("batteriesReport", rd.genClinicStatisticReportsPart1eScreeningBatteriesReport(requestData, escreenUser));
    }


    // 601 numeric report
    private ModelAndView getAveScoresByClinicNumericReport(HashMap<String, Object> requestData, EscreenUser escreenUser) {

        return new ModelAndView("avgClinicNumericReport", rd.getAveScoresByClinicGraphOrNumeric(requestData, escreenUser, true));
    }

    private ModelAndView getAvgScoresVetByClinicNumericReport(HashMap<String, Object> requestData, EscreenUser escreenUser) {

        return new ModelAndView("avgVetClinicNumericReport", rd.getAvgScoresVetByClinicGraphReport(requestData, escreenUser));
    }


    // for avg individual
    private ModelAndView getAvgScoresVetByClinicGraphReport(Map<String, Object> requestData, EscreenUser escreenUser) {

        return new ModelAndView("avgVetClinicGraphReport", rd.getAvgScoresVetByClinicGraphReport(requestData, escreenUser));
    }

    // FOR GROUP
    private ModelAndView getAveScoresByClinicGraphOrNumeric(Map<String, Object> requestData, EscreenUser escreenUser, boolean includeCount) {
        return new ModelAndView("avgClinicGraphReport", rd.getAveScoresByClinicGraphOrNumeric(requestData, escreenUser, includeCount));
    }

    private ModelAndView getIndividualStaticsGraphicPDF(Map<String, Object> requestData, EscreenUser escreenUser, String viewName) {
        return new ModelAndView(viewName, rd.getIndividualStaticsGraphicPDF(requestData, escreenUser));
    }

    @RequestMapping(value = "/requestChartableData", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> requestChartableData(@RequestBody Map<String, Object> requestData, @CurrentUser EscreenUser escreenUser) {

        if ("avgScoresForPatientsByClinic".equals(requestData.get(ReportsUtil.REPORT_TYPE))) {
            if ("groupData".equals(requestData.get(ReportsUtil.DISPLAY_OPTION))) {
                return createGrpChartableDataForAvgScoresForPatientsByClinic(requestData);
            } else {
                return createIndivChartableDataForAvgScoresForPatientsByClinic(requestData);
            }
        }

        return createChartableDataForIndividualStats(requestData);
    }

    private List<Map<String, Object>> createIndivChartableDataForAvgScoresForPatientsByClinic(Map<String, Object> requestData) {
        return rd.createIndivChartableDataForAvgScoresForPatientsByClinic(requestData);
    }

    private List<Map<String, Object>> createGrpChartableDataForAvgScoresForPatientsByClinic(Map<String, Object> requestData) {
        return rd.createGrpChartableDataForAvgScoresForPatientsByClinic(requestData);
    }


    private List<Map<String, Object>> createChartableDataForIndividualStats(Map<String, Object> requestData) {
        return rd.createChartableDataForIndividualStats(requestData);
    }
}
