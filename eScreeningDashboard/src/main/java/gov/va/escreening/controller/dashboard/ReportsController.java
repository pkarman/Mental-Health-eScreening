package gov.va.escreening.controller.dashboard;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gov.va.escreening.delegate.AssessmentDelegate;
import gov.va.escreening.domain.ClinicDto;
import gov.va.escreening.domain.SurveyDto;
import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.dto.report.ModuleGraphReportDTO;
import gov.va.escreening.dto.report.ScoreHistoryDTO;
import gov.va.escreening.dto.report.TableReportDTO;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.ClinicService;
import gov.va.escreening.service.ReportTypeService;
import gov.va.escreening.service.SurveyService;
import gov.va.escreening.service.VeteranAssessmentSurveyScoreService;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.FileResolver;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.LocalDataSourceJobStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Controller
@RequestMapping(value = "/dashboard")
public class ReportsController {

    private static final Logger logger = LoggerFactory.getLogger(ReportsController.class);

    private static final String LASTNAME = "lastName";
    private static final String FROMDATE = "fromDate";
    private static final String SSN_LAST_FOUR="ssnLastFour";
    private static final String SURVEY_ID_LIST="surveysList";
    private static final String TODATE="toDate";

    @Autowired
    private AssessmentDelegate assessmentDelegate;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private VeteranAssessmentSurveyScoreService scoreService;

    private FileResolver fileResolver = new FileResolver() {

        @Override
        public File resolveFile(String fileName) {
            URI uri;
            try {
                uri = new URI(ReportsController.class.getResource(fileName).getPath());
                return new File(uri.getPath());
            } catch (URISyntaxException e) {
                System.out.println(fileName);
                logger.error("Fail to load image file for jasper report " + fileName);
                return null;
            }
        }
    };

    /**
     * Initialize and setup page.
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/reports", method = RequestMethod.GET)
    public String setUpPageListView(Model model) {
        return "reports";
    }

    @RequestMapping(value = "/individualStatisticsReports", method = RequestMethod.GET)
    public ModelAndView getIindividualStatisticReports() {
        return new ModelAndView("individualStatisticsReports");
    }

    @RequestMapping(value = "/listSurveys", method = RequestMethod.GET)
    @ResponseBody
    public List<SurveyDto> getAllSurveys() {
        return surveyService.getSurveyList();
    }

    @RequestMapping(value = "/listClinics", method = RequestMethod.GET)
    @ResponseBody
    public List<ClinicDto> getAllClinics() {
        return clinicService.getClinicDtoList();
    }


    /**
     * REST endpoint will receive an list of svg objects plus any other data required to render a graphical report
     * This will simply prepare the required data + svg objects for the target Jasper report and forward
     *
     * @param request
     * @param requestData
     * @param escreenUser
     * @return
     */
    @RequestMapping(value = "/individualStatisticsGraphic", method = RequestMethod.POST)
    public ModelAndView genIndividualStatisticsGraphic(HttpServletRequest request,
                                                       @RequestBody Map<String, Object> requestData,
                                                       @CurrentUser EscreenUser escreenUser) {


        Map<String, Object> parameterMap = new HashMap<String, Object>();


        parameterMap.put("fromToDate", "From 02/01/2014 to 01/05/2015");
        parameterMap.put("lastNameSSN", "Veteran1123, 1234");

        List<ModuleGraphReportDTO> resultList = new ArrayList<>();

        ModuleGraphReportDTO moduleGraphReportDTO = new ModuleGraphReportDTO();

        moduleGraphReportDTO.setScore("20");
        moduleGraphReportDTO.setModuleName("PHQ-9");
        moduleGraphReportDTO.setScoreMeaning("Severe");
        moduleGraphReportDTO.setScoreName("Last Depression Score");
        moduleGraphReportDTO.setScoreHistoryTitle("Score History by VistA Clinic");

        List<ScoreHistoryDTO> history = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            ScoreHistoryDTO scoreHistoryDTO = new ScoreHistoryDTO();
            scoreHistoryDTO.setClinicName(" Test Clinic " + i);
            scoreHistoryDTO.setScreeningDate("01/" + i + "/2015");
            history.add(scoreHistoryDTO);
        }


        moduleGraphReportDTO.setScoreHistory(history);

        resultList.add(moduleGraphReportDTO);

        moduleGraphReportDTO = new ModuleGraphReportDTO();

        moduleGraphReportDTO.setScore("24");
        moduleGraphReportDTO.setModuleName("PCL-C");
        moduleGraphReportDTO.setScoreMeaning("Moderate");
        moduleGraphReportDTO.setScoreName("Last Depression Score");
        moduleGraphReportDTO.setScoreHistoryTitle("Score History by VistA Clinic");

        history = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            ScoreHistoryDTO scoreHistoryDTO = new ScoreHistoryDTO();
            scoreHistoryDTO.setClinicName(" Test Clinic " + i);
            scoreHistoryDTO.setScreeningDate("02/" + i + "/2015");
            history.add(scoreHistoryDTO);
        }

        moduleGraphReportDTO.setScoreHistory(history);

        resultList.add(moduleGraphReportDTO);


        JRDataSource dataSource = new JRBeanCollectionDataSource(resultList);

        parameterMap.put("datasource", dataSource);
        parameterMap.put("REPORT_FILE_RESOLVER", fileResolver);

        System.out.println("aaaaaaacccsaa");
        return new ModelAndView("individualStatisticsGraphReport", parameterMap);
    }

    /**
     * REST enpoint point will receive search data (data entered bu the user in IndivStatsFormBean for numeric data report.
     * This should seek ehelp from service to grab the data form back end and forward it to the target JasperReport
     *
     * @param request
     * @param requestData
     * @param escreenUser
     * @return
     */
    @RequestMapping(value = "/requestChartableData", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> requestChartableData(HttpServletRequest request,
                                                          @RequestBody Map<String, Object> requestData,
                                                          @CurrentUser EscreenUser escreenUser) {

        List<Map<String, Object>> chartableDataList = Lists.newArrayList();

        //todo Kai to replace this test loop with the real code
        for (int i = 0; i < 2; i++) {
            chartableDataList.add(createTstChartableData(requestData));
        }
        return chartableDataList;
    }

    public Map<String, Object> createTstChartableData(Map<String, Object> requestData) {
        Map<String, Object> chartableDataMap = Maps.newHashMap();

        //first create the data
        String dataInJsonFormat = "{\"03/06/2015 08:59:38\": 16,\"01/23/2015 12:51:17\": 27,\"09/23/2014 12:36:48\": 5}";
        Gson gson = new GsonBuilder().create();
        Map dataSet = gson.fromJson(dataInJsonFormat, Map.class);

        String dataFormatInJsonFormat = "{\n" +
                "  \"ticks\": [\n" +
                "    0, \n" +
                "    1, \n" +
                "    5, \n" +
                "    10, \n" +
                "    15, \n" +
                "    20, \n" +
                "    27\n" +
                "  ], \n" +
                "  \"score\": 16, \n" +
                "  \"footer\": \"\", \n" +
                "  \"varId\": 1599, \n" +
                "  \"title\": \"My Depression Score\", \n" +
                "  \"intervals\": {\n" +
                "    \"None\": 0, \n" +
                "    \"Moderately Severe\": 15, \n" +
                "    \"Mild\": 5, \n" +
                "    \"Severe\": 20, \n" +
                "    \"Moderate\": 10, \n" +
                "    \"Minimal\": 1\n" +
                "  }, \n" +
                "  \"maxXPoint\": 27, \n" +
                "  \"numberOfMonths\": 12\n" +
                "}";
        Map dataFormat = gson.fromJson(dataFormatInJsonFormat, Map.class);

        chartableDataMap.put("dataSet", dataSet);
        chartableDataMap.put("dataFormat", dataFormat);

        return chartableDataMap;
    }

    /**
     * REST enpoint point will receive search data (data entered bu the user in IndivStatsFormBean for numeric data report.
     * This should seek ehelp from service to grab the data form back end and forward it to the target JasperReport
     *
     * @param request
     * @param requestData
     * @param escreenUser
     * @return
     */
    @RequestMapping(value = "/individualStatisticsNumeric", method = RequestMethod.POST)
    public ModelAndView genIndividualStatisticsNumeric(HttpServletRequest request,
                                                       @RequestBody HashMap<String, Object> requestData,
                                                       BindingResult result, ModelMap model,
                                                       @CurrentUser EscreenUser escreenUser) {

        logger.debug("Generating the individual statistics reports numeric only.");

        String lastName = (String)requestData.get(LASTNAME);
        String last4SSN = (String)requestData.get(SSN_LAST_FOUR);
        String fromDate = (String)requestData.get(FROMDATE);
        String toDate = (String)requestData.get(TODATE);

        logger.debug(" lastName :"+lastName);
        logger.debug(" last4SSN :"+ last4SSN);
        logger.debug( " From :"+fromDate+ " TO: "+toDate);

        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("lastNameSSN", lastName+", "+last4SSN);
        parameterMap.put("fromToDate", "From "+fromDate+" to "+toDate);

        VeteranDto veteran = new VeteranDto();
        veteran.setLastName(lastName);
        veteran.setSsnLastFour(last4SSN);

        List<VeteranDto> veterans = assessmentDelegate.findVeterans(veteran);

        JRDataSource dataSource = null;

        if (veterans == null || veterans.isEmpty()){
            dataSource = new JREmptyDataSource();
        }
        else {


            List<TableReportDTO> resultList = new ArrayList<>();


            for (Object strSurveyId : (ArrayList) requestData.get(SURVEY_ID_LIST)) {

                Integer surveyId = (Integer)strSurveyId;

                TableReportDTO tableReportDTO = scoreService.getSurveyDataForIndividualStatisticsReport(surveyId, veterans.get(0).getVeteranId(), fromDate, toDate);
                if (tableReportDTO != null){
                    resultList.add(tableReportDTO);
                }
            }

            if (resultList.isEmpty()) {
                dataSource = new JREmptyDataSource();
            }
            else{
                dataSource = new JRBeanCollectionDataSource(resultList);
            }
        }

        parameterMap.put("datasource", dataSource);
        parameterMap.put("REPORT_FILE_RESOLVER", fileResolver);



/*


        List<TableReportDTO> resultList = new ArrayList<>();

        TableReportDTO tb = new TableReportDTO();
        tb.setModuleName("PHQ-9");
        tb.setScreeningModuleName("Depression");
        tb.setScore("15 - Moderate\n" +
                "20 - Moderate Severe\n" +
                "27 - Severe");
        tb.setHistoryByClinic("02/01/2015  | LI SOC WK OEF ESCREENING\n" +
                "01/14/2015  | LI PRIM CARE HAGARICH\n" +
                "10/10/2014  | LI 2N MHAC URGENT CLINIC");
        resultList.add(tb);

        tb = new TableReportDTO();

        tb.setModuleName("PCL-C");
        tb.setScreeningModuleName("Post-traumatic Stress Disorder");
        tb.setScore("43 - Negative");
        tb.setHistoryByClinic("10/10/2014  |  LI SOC WK OEF ESCREENING");

        resultList.add(tb);


        parameterMap.put("cast", resultList);


        parameterMap.put("reportTitle", "PHQ-9");
        parameterMap.put("reportScore", "20");
        parameterMap.put("scoreMeaning", "Moderate Severe");

        parameterMap.put("history", "02/01/2015  | LI SOC WK OEF ESCREENING\n" +
                "01/14/2015  | LI PRIM CARE HAGARICH\n" +
                "10/10/2014  | LI 2N MHAC URGENT CLINIC");

*/
        return new ModelAndView("IndividualStatisticsReportsNumericOnlyReport", parameterMap);
    }
}
