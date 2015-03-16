package gov.va.escreening.controller.dashboard;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gov.va.escreening.delegate.AssessmentDelegate;
import gov.va.escreening.domain.ClinicDto;
import gov.va.escreening.domain.SurveyDto;
import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.dto.report.*;
import gov.va.escreening.entity.ReportType;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.*;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.FileResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
    private static final String SSN_LAST_FOUR = "ssnLastFour";
    private static final String SURVEY_ID_LIST = "surveysList";
    private static final String TODATE = "toDate";
    private static final String DISPLAY_OPTION = "displayOption";
    private static final String CLINIC_ID_LIST = "clinicsList";
    private static final String REPORT_TYPE = "reportType";

    private static final String SVG_HEADER = "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n";


    @Autowired
    private AssessmentDelegate assessmentDelegate;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private VeteranAssessmentSurveyScoreService scoreService;

    @Autowired
    private SurveyScoreIntervalService intervalService;

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

    // ticket 601 related
    @RequestMapping(value = "/individualStatisticsReports", method = RequestMethod.GET)
    public ModelAndView getIindividualStatisticReports() {
        return new ModelAndView("individualStatisticsReports");
    }

    @RequestMapping(value = "/individualStatisticsGraphicAndNumber", method = RequestMethod.POST)
    public ModelAndView genIndividualStatisticsGraphicAndNumber(HttpServletRequest request,
                                                                @RequestBody Map<String, Object> requestData,
                                                                @CurrentUser EscreenUser escreenUser) {
        return getIndividualStaticsGraphicPDF(requestData, escreenUser,
                "individualStatisticsGraphNumberReport");
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

        return getIndividualStaticsGraphicPDF(requestData, escreenUser,
                "individualStatisticsGraphReport");

    }

    @RequestMapping(value = "/averageScoresForPatientsByClinic", method = RequestMethod.GET)
    public ModelAndView getAverageScoresForPatientsByClinic() {
        return new ModelAndView("averageScoresForPatientsByClinic");
    }

    @RequestMapping(value = "/avgScoresVetByClinicGraphicNumeric", method = RequestMethod.POST)
    public ModelAndView genAvgScoresVetByClinicGraphicNumeric(HttpServletRequest request,
                                                              @RequestBody Map<String, Object> requestData,
                                                              @CurrentUser EscreenUser escreenUser) {
        return genIndividualStatisticsGraphic(request, requestData, escreenUser);
    }

    @RequestMapping(value = "/avgScoresVetByClinicGraphic", method = RequestMethod.POST)
    public ModelAndView genAvgScoresVetByClinicGraphic(HttpServletRequest request,
                                                       @RequestBody Map<String, Object> requestData,
                                                       @CurrentUser EscreenUser escreenUser) {

        // ticket 600 entry point graph chart

        //Group Chart
        return getAveScoresByClinicGraphOrNumeric(requestData, escreenUser, false);


    }

    @RequestMapping(value = "/avgScoresVetByClinicGraphicNumber", method = RequestMethod.POST)
    public ModelAndView genAvgScoresVetByClinicGraphicNumber(HttpServletRequest request,
                                                       @RequestBody Map<String, Object> requestData,
                                                       @CurrentUser EscreenUser escreenUser) {

        // ticket 600 entry point after getting graph and numeric report

        return getAveScoresByClinicGraphOrNumeric(requestData, escreenUser, true);
    }



    // FOR GROUP
    private ModelAndView getAveScoresByClinicGraphOrNumeric(Map<String, Object> requestData, EscreenUser escreenUser, boolean includeCount){


        ArrayList<String> svgObject = (ArrayList<String>) requestData.get("svgData");
        LinkedHashMap<String, Object> userReqData = (LinkedHashMap<String, Object>) requestData.get("userReqData");

        Map<String, Object> parameterMap = new HashMap<String, Object>();


        String fromDate = (String) userReqData.get(FROMDATE);
        String toDate = (String) userReqData.get(TODATE);
        ArrayList cClinicList = (ArrayList) userReqData.get(CLINIC_ID_LIST);
        ArrayList sSurveyList = (ArrayList) userReqData.get(SURVEY_ID_LIST);

        parameterMap.put("fromToDate", "From " + fromDate + " to " + toDate);

        List<Integer> surveyIds = new ArrayList<>(sSurveyList.size());

        for (Object s : sSurveyList) {
            surveyIds.add((Integer) s);
        }

        List<ClinicDTO> resultList = new ArrayList<>();

        int index = 0;

        for (Object c : cClinicList) {

            Integer clinicId = (Integer) c;

            ClinicDTO dto = new ClinicDTO();
            resultList.add(dto);
            dto.setClinicName(clinicService.getClinicNameById(clinicId));

            dto.setGraphReport(new ArrayList<ModuleGraphReportDTO>());

            for(Object s : sSurveyList) {
                dto.getGraphReport().add(scoreService.getGraphDataForClinicStatisticsGraph(clinicId, (Integer)s, fromDate, toDate, includeCount));

                for (ModuleGraphReportDTO moduleGraphReportDTO : dto.getGraphReport()) {
                    moduleGraphReportDTO.setImageInput(SVG_HEADER + svgObject.get(index++));
                }
            }

        }

        JRDataSource dataSource = new JRBeanCollectionDataSource(resultList);

        parameterMap.put("datasource", dataSource);
        parameterMap.put("REPORT_FILE_RESOLVER", fileResolver);

        return new ModelAndView("avgClinicGraphReport", parameterMap);
    }

    private ModelAndView getAvgScoresVetByClinicGraphicOrNumeric(Map<String, Object> requestData, EscreenUser escreenUser, boolean includeCount) {

        ArrayList<String> svgObject = (ArrayList<String>) requestData.get("svgData");
        LinkedHashMap<String, Object> userReqData = (LinkedHashMap<String, Object>) requestData.get("userReqData");

        Map<String, Object> parameterMap = new HashMap<String, Object>();

        String fromDate = (String) userReqData.get(FROMDATE);
        String toDate = (String) userReqData.get(TODATE);
        ArrayList cClinicList = (ArrayList) userReqData.get(CLINIC_ID_LIST);
        ArrayList sSurveyList = (ArrayList) userReqData.get(SURVEY_ID_LIST);

        parameterMap.put("fromToDate", "From " + fromDate + " to " + toDate);

        List<Integer> surveyIds = new ArrayList<>(sSurveyList.size());

        for (Object s : sSurveyList) {
            surveyIds.add((Integer) s);
        }

        List<ClinicVeteranDTO> resultList = new ArrayList<>();

        int index = 0;

        for (Object c : cClinicList) {

            Integer clinicId = (Integer) c;

            ClinicVeteranDTO dto = new ClinicVeteranDTO();
            dto.setClinicName(clinicService.getClinicNameById(clinicId));

            //dto.setVeteranModuleGraphReportDTOs(scoreService.getGraphDataForClinicStatisticsGraph(clinicId, surveyIds, fromDate, toDate));

            for (VeteranModuleGraphReportDTO veteranModuleGraphReportDTO : dto.getVeteranModuleGraphReportDTOs()) {
                for (ModuleGraphReportDTO moduleGraphReportDTO : veteranModuleGraphReportDTO.getModuleGraphs()) {
                    moduleGraphReportDTO.setImageInput(SVG_HEADER + svgObject.get(index++));
                }
            }
        }

        JRDataSource dataSource = new JRBeanCollectionDataSource(resultList);

        parameterMap.put("datasource", dataSource);
        parameterMap.put("REPORT_FILE_RESOLVER", fileResolver);

        return new ModelAndView("avgClinicGraphReport", parameterMap);
    }

    @RequestMapping(value = "/avgScoresVetByClinicNumeric", method = RequestMethod.POST)
    public ModelAndView genAvgScoresVetByClinicNumeric(HttpServletRequest request,
                                                       @RequestBody HashMap<String, Object> requestData,
                                                       @CurrentUser EscreenUser escreenUser) {
        logger.debug("getting avgScoresVetByClinicNumeric");

        String displayOption = (String) requestData.get(DISPLAY_OPTION);
        if ("individualData".equals(displayOption)) {

        }

        ArrayList idList = (ArrayList) requestData.get(CLINIC_ID_LIST);


        return null;
    }

    private ModelAndView getIndividualStaticsGraphicPDF(Map<String, Object> requestData, EscreenUser escreenUser,
                                                        String viewName) {

        ArrayList<String> svgObject = (ArrayList<String>) requestData.get("svgData");
        LinkedHashMap<String, Object> userReqData = (LinkedHashMap<String, Object>) requestData.get("userReqData");

        Map<String, Object> parameterMap = new HashMap<String, Object>();

        String lastName = (String) userReqData.get(LASTNAME);
        String last4SSN = (String) userReqData.get(SSN_LAST_FOUR);
        String fromDate = (String) userReqData.get(FROMDATE);
        String toDate = (String) userReqData.get(TODATE);
        ArrayList surveyIds = (ArrayList) userReqData.get(SURVEY_ID_LIST);

        parameterMap.put("fromToDate", "From " + fromDate + " to " + toDate);
        parameterMap.put("lastNameSSN", lastName + ", " + last4SSN);

        VeteranDto veteran = new VeteranDto();
        veteran.setLastName(lastName);
        veteran.setSsnLastFour(last4SSN);

        List<VeteranDto> veterans = assessmentDelegate.findVeterans(veteran);

        Integer veteranId = -1;

        if (veterans != null && !veterans.isEmpty()) {
            veteranId = veterans.get(0).getVeteranId();
        }

        List<ModuleGraphReportDTO> resultList = new ArrayList<>();

        for (int i = 0; i < surveyIds.size(); i++) {
            Integer surveyId = (Integer) surveyIds.get(i);
            ModuleGraphReportDTO moduleGraphReportDTO = scoreService.getGraphReportDTOForIndividual(surveyId, veteranId, fromDate, toDate);
            if (moduleGraphReportDTO.getHasData()) {
                moduleGraphReportDTO.setImageInput(
                        "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n" +
                                svgObject.get(i));

                moduleGraphReportDTO.setScoreHistoryTitle("Score History by VistA Clinic");
            }
            resultList.add(moduleGraphReportDTO);

        }

        JRDataSource dataSource = new JRBeanCollectionDataSource(resultList);

        parameterMap.put("datasource", dataSource);
        parameterMap.put("REPORT_FILE_RESOLVER", fileResolver);

        return new ModelAndView(viewName, parameterMap);
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

        if ("avgScoresForPatientsByClinic".equals(requestData.get(REPORT_TYPE))) {
            if ("groupData".equals(requestData.get(DISPLAY_OPTION))) {
                return createChartableDataFor601Clinic(requestData);
            } else {
                return createChartableDataFor601VeteranClinic(requestData);
            }
        }

        return createChartableDataForIndividualStats(requestData);
    }

    private List<Map<String, Object>> createChartableDataFor601VeteranClinic(Map<String, Object> requestData) {
        List<Map<String, Object>> chartableDataList = Lists.newArrayList();

        logger.debug("Generating the veteran clinic graph data ");

        String fromDate = (String) requestData.get(FROMDATE);
        String toDate = (String) requestData.get(TODATE);
        List cList = (ArrayList) requestData.get(CLINIC_ID_LIST);
        List sList = (ArrayList) requestData.get(SURVEY_ID_LIST);

        for (Object oClinicId : cList) {

            Integer clinicId = (Integer) oClinicId;

            for (Object oSurveyId : sList) {

                Integer surveyId = (Integer) oSurveyId;

                chartableDataList.add(createChartableDataFor601Clinic(clinicId, surveyId, fromDate, toDate));
            }
        }
        return chartableDataList;

    }

    private List<Map<String, Object>> createChartableDataFor601Clinic(Map<String, Object> requestData) {

        List<Map<String, Object>> chartableDataList = Lists.newArrayList();

        logger.debug("Generating the clinic graph data ");

        String fromDate = (String) requestData.get(FROMDATE);
        String toDate = (String) requestData.get(TODATE);
        List cList = (ArrayList) requestData.get(CLINIC_ID_LIST);
        List sList = (ArrayList) requestData.get(SURVEY_ID_LIST);

        for (Object oClinicId : cList) {

            Integer clinicId = (Integer) oClinicId;

            for (Object oSurveyId : sList) {

                Integer surveyId = (Integer) oSurveyId;

                chartableDataList.add(createChartableDataFor601Clinic(clinicId, surveyId, fromDate, toDate));
            }
        }
        return chartableDataList;
    }

    private List<Map<String, Object>> createChartableDataForIndividualStats(Map<String, Object> requestData) {

        List<Map<String, Object>> chartableDataList = Lists.newArrayList();

        logger.debug("Generating the individual statistics reports");

        String lastName = (String) requestData.get(LASTNAME);
        String last4SSN = (String) requestData.get(SSN_LAST_FOUR);
        String fromDate = (String) requestData.get(FROMDATE);
        String toDate = (String) requestData.get(TODATE);

        VeteranDto veteran = new VeteranDto();
        veteran.setLastName(lastName);
        veteran.setSsnLastFour(last4SSN);

        List<VeteranDto> veterans = assessmentDelegate.findVeterans(veteran);

        if (veterans == null || veterans.isEmpty()) {
            return chartableDataList;
        }

        Integer veteranId = veterans.get(0).getVeteranId();

        for (Object strSurveyId : (ArrayList) requestData.get(SURVEY_ID_LIST)) {

            Integer surveyId = (Integer) strSurveyId;

            chartableDataList.add(createChartableDataForIndividualStats(surveyId, veteranId, fromDate, toDate));
        }

        return chartableDataList;
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
                                                       @RequestBody Map<String, Object> requestData,
                                                       @CurrentUser EscreenUser escreenUser) {

        logger.debug("Generating the individual statistics reports numeric only.");

        String lastName = (String) requestData.get(LASTNAME);
        String last4SSN = (String) requestData.get(SSN_LAST_FOUR);
        String fromDate = (String) requestData.get(FROMDATE);
        String toDate = (String) requestData.get(TODATE);

        logger.debug(" lastName :" + lastName);
        logger.debug(" last4SSN :" + last4SSN);
        logger.debug(" From :" + fromDate + " TO: " + toDate);

        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("lastNameSSN", lastName + ", " + last4SSN);
        parameterMap.put("fromToDate", "From " + fromDate + " to " + toDate);

        VeteranDto veteran = new VeteranDto();
        veteran.setLastName(lastName);
        veteran.setSsnLastFour(last4SSN);

        List<VeteranDto> veterans = assessmentDelegate.findVeterans(veteran);

        JRDataSource dataSource = null;

        if (veterans == null || veterans.isEmpty()) {
            dataSource = new JREmptyDataSource();
        } else {


            List<TableReportDTO> resultList = new ArrayList<>();


            for (Object strSurveyId : (ArrayList) requestData.get(SURVEY_ID_LIST)) {

                Integer surveyId = (Integer) strSurveyId;

                TableReportDTO tableReportDTO = scoreService.getSurveyDataForIndividualStatisticsReport(surveyId, veterans.get(0).getVeteranId(), fromDate, toDate);
                if (tableReportDTO != null) {
                    resultList.add(tableReportDTO);
                }
            }

            if (resultList.isEmpty()) {
                dataSource = new JREmptyDataSource();
            } else {
                dataSource = new JRBeanCollectionDataSource(resultList);
            }
        }

        parameterMap.put("datasource", dataSource);
        parameterMap.put("REPORT_FILE_RESOLVER", fileResolver);

        return new ModelAndView("IndividualStatisticsReportsNumericOnlyReport", parameterMap);
    }

    private Map<String, Object> createChartableDataForIndividualStats(Integer surveyId, Integer veteranId, String fromDate, String toDate) {

        Map<String, Object> chartableDataMap = Maps.newHashMap();

        chartableDataMap.put("dataSet", scoreService.getSurveyDataForIndividualStatisticsGraph(surveyId, veteranId, fromDate, toDate));
        chartableDataMap.put("dataFormat", intervalService.generateMetadata(surveyId));

        return chartableDataMap;
    }

    private Map<String, Object> createChartableDataFor601VeteranClinic(Integer clinicId, Integer surveyId, Integer veteranId, String fromDate, String toDate) {
        Map<String, Object> chartableDataMap = Maps.newHashMap();

        chartableDataMap.put("dataFormat", intervalService.generateMetadata(surveyId));

        return chartableDataMap;

    }

    private Map<String, Object> createChartableDataFor601Clinic(Integer clinicId, Integer surveyId, String fromDate, String toDate) {

        Map<String, Object> chartableDataMap = Maps.newHashMap();

        chartableDataMap.put("dataSet", scoreService.getSurveyDataForClinicStatisticsGraph(clinicId, surveyId, fromDate, toDate));
        chartableDataMap.put("dataFormat", intervalService.generateMetadata(surveyId));

        return chartableDataMap;

    }


}
