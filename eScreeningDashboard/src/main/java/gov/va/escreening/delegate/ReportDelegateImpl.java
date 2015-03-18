package gov.va.escreening.delegate;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import gov.va.escreening.controller.dashboard.ReportsController;
import gov.va.escreening.domain.ClinicDto;
import gov.va.escreening.domain.SurveyDto;
import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.dto.report.*;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.*;
import gov.va.escreening.util.ReportsUtil;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.FileResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by munnoo on 3/16/15.
 */
@Service("reportDelegate")
public class ReportDelegateImpl implements ReportDelegate {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(type = AssessmentDelegate.class)
    private AssessmentDelegate assessmentDelegate;

    @Resource(type = SurveyService.class)
    private SurveyService surveyService;

    @Resource(type = ClinicService.class)
    private ClinicService clinicService;

    @Resource(type = VeteranAssessmentSurveyScoreService.class)
    private VeteranAssessmentSurveyScoreService scoreService;

    @Resource(type = SurveyScoreIntervalService.class)
    private SurveyScoreIntervalService intervalService;

    @Resource(type = VeteranService.class)
    private VeteranService veteranService;

    @Resource(name = "reportableFormulasMap")
    Map<String, String> reportableFormulasMap;

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

    @Override
    public Map<String, Object> getAvgScoresVetByClinicGraphReport(Map<String, Object> requestData, EscreenUser escreenUser) {
        ArrayList<String> svgObject = (ArrayList<String>) requestData.get("svgData");
        LinkedHashMap<String, Object> userReqData = (LinkedHashMap<String, Object>) requestData.get("userReqData");

        Map<String, Object> parameterMap = Maps.newHashMap();


        String fromDate = (String) userReqData.get(ReportsUtil.FROMDATE);
        String toDate = (String) userReqData.get(ReportsUtil.TODATE);
        ArrayList cClinicList = (ArrayList) userReqData.get(ReportsUtil.CLINIC_ID_LIST);
        ArrayList sSurveyList = (ArrayList) userReqData.get(ReportsUtil.SURVEY_ID_LIST);

        parameterMap.put("fromToDate", "From " + fromDate + " to " + toDate);

        List<ClinicVeteranDTO> resultList = new ArrayList<>();

        int index = 0;

        for (Object c : cClinicList) {
            Integer clinicId = (Integer) c;

            ClinicVeteranDTO cvDTO = new ClinicVeteranDTO();

            cvDTO.setClinicName(clinicService.getClinicNameById(clinicId));
            cvDTO.setVeteranModuleGraphReportDTOs(new ArrayList<VeteranModuleGraphReportDTO>());


            List<Integer> veterans = clinicService.getAllVeteranIds(clinicId);

            boolean hasData = false;

            for (Integer vId : veterans) {

                VeteranModuleGraphReportDTO veteranModuleGraphReportDTO = new VeteranModuleGraphReportDTO();
                veteranModuleGraphReportDTO.setModuleGraphs(new ArrayList<ModuleGraphReportDTO>());
                cvDTO.getVeteranModuleGraphReportDTOs().add(veteranModuleGraphReportDTO);

                VeteranDto vDto = veteranService.getByVeteranId(vId);

                veteranModuleGraphReportDTO.setLastNameAndSSN(vDto.getLastName() + ", " + vDto.getSsnLastFour());

                for (Object o : sSurveyList) {
                    ModuleGraphReportDTO moduleGraphReportDTO = scoreService.getSurveyDataForVetClinicReport(clinicId, (Integer) o, vId, fromDate, toDate);
                    if (moduleGraphReportDTO.getHasData()) {
                        moduleGraphReportDTO.setImageInput(ReportsUtil.SVG_HEADER + svgObject.get(index++));
                        moduleGraphReportDTO.setScoreHistoryTitle("Score History by VistA Clinic");
                        hasData = true;
                    }
                    veteranModuleGraphReportDTO.getModuleGraphs().add(moduleGraphReportDTO);


                }
            }
            if (hasData) {
                resultList.add(cvDTO);
            }

        }

        JRDataSource dataSource = new JRBeanCollectionDataSource(resultList);

        parameterMap.put("datasource", dataSource);
        parameterMap.put("REPORT_FILE_RESOLVER", fileResolver);
        return parameterMap;
    }

    @Override
    public List<SurveyDto> getSurveyList() {

        List<String> surveyNames = new ArrayList<>();
        surveyNames.addAll(reportableFormulasMap.keySet());

        return surveyService.getSurveyListByNames(surveyNames);
    }

    @Override
    public List<ClinicDto> getClinicDtoList() {
        return clinicService.getClinicDtoList();
    }

    @Override
    public Map<String, Object> getAveScoresByClinicGraphOrNumeric(Map<String, Object> requestData, EscreenUser escreenUser, boolean includeCount) {
        ArrayList<String> svgObject = (ArrayList<String>) requestData.get("svgData");
        LinkedHashMap<String, Object> userReqData = (LinkedHashMap<String, Object>) requestData.get("userReqData");

        Map<String, Object> parameterMap = Maps.newHashMap();

        String fromDate = (String) userReqData.get(ReportsUtil.FROMDATE);
        String toDate = (String) userReqData.get(ReportsUtil.TODATE);
        ArrayList cClinicList = (ArrayList) userReqData.get(ReportsUtil.CLINIC_ID_LIST);
        ArrayList sSurveyList = (ArrayList) userReqData.get(ReportsUtil.SURVEY_ID_LIST);

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

            dto.setClinicName(clinicService.getClinicNameById(clinicId));

            dto.setGraphReport(new ArrayList<ModuleGraphReportDTO>());

            boolean hasData = false;
            for (Object s : sSurveyList) {
                ModuleGraphReportDTO moduleGraphReportDTO = scoreService.getGraphDataForClinicStatisticsGraph(clinicId, (Integer) s, fromDate, toDate, includeCount);

                if (moduleGraphReportDTO != null) {
                    dto.getGraphReport().add(moduleGraphReportDTO);
                    hasData = true;
                }
            }

            if (hasData) {

                for (ModuleGraphReportDTO moduleGraphReportDTO : dto.getGraphReport()) {
                    moduleGraphReportDTO.setImageInput(ReportsUtil.SVG_HEADER + svgObject.get(index++));
                }

                resultList.add(dto);
            }

        }

        JRDataSource dataSource = new JRBeanCollectionDataSource(resultList);

        parameterMap.put("datasource", dataSource);
        parameterMap.put("REPORT_FILE_RESOLVER", fileResolver);
        return parameterMap;
    }

    @Override
    public Map<String, Object> getAvgScoresVetByClinicGraphicOrNumeric(Map<String, Object> requestData, EscreenUser escreenUser) {
        ArrayList<String> svgObject = (ArrayList<String>) requestData.get("svgData");
        LinkedHashMap<String, Object> userReqData = (LinkedHashMap<String, Object>) requestData.get("userReqData");

        Map<String, Object> parameterMap = Maps.newHashMap();

        String fromDate = (String) userReqData.get(ReportsUtil.FROMDATE);
        String toDate = (String) userReqData.get(ReportsUtil.TODATE);
        ArrayList cClinicList = (ArrayList) userReqData.get(ReportsUtil.CLINIC_ID_LIST);
        ArrayList sSurveyList = (ArrayList) userReqData.get(ReportsUtil.SURVEY_ID_LIST);

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
                    moduleGraphReportDTO.setImageInput(ReportsUtil.SVG_HEADER + svgObject.get(index++));
                }
            }
        }

        JRDataSource dataSource = new JRBeanCollectionDataSource(resultList);

        parameterMap.put("datasource", dataSource);
        parameterMap.put("REPORT_FILE_RESOLVER", fileResolver);
        return parameterMap;
    }

    @Override
    public Map<String, Object> getIndividualStaticsGraphicPDF(Map<String, Object> requestData, EscreenUser escreenUser) {
        ArrayList<String> svgObject = (ArrayList<String>) requestData.get("svgData");
        LinkedHashMap<String, Object> userReqData = (LinkedHashMap<String, Object>) requestData.get("userReqData");

        Map<String, Object> parameterMap = Maps.newHashMap();

        String lastName = (String) userReqData.get(ReportsUtil.LASTNAME);
        String last4SSN = (String) userReqData.get(ReportsUtil.SSN_LAST_FOUR);
        String fromDate = (String) userReqData.get(ReportsUtil.FROMDATE);
        String toDate = (String) userReqData.get(ReportsUtil.TODATE);
        ArrayList surveyIds = (ArrayList) userReqData.get(ReportsUtil.SURVEY_ID_LIST);

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

        int index = 0;

        for (int i = 0; i < surveyIds.size(); i++) {
            Integer surveyId = (Integer) surveyIds.get(i);
            ModuleGraphReportDTO moduleGraphReportDTO = scoreService.getGraphReportDTOForIndividual(surveyId, veteranId, fromDate, toDate);
            if (moduleGraphReportDTO.getHasData()) {
                moduleGraphReportDTO.setImageInput(ReportsUtil.SVG_HEADER + svgObject.get(index++));

                moduleGraphReportDTO.setScoreHistoryTitle("Score History by VistA Clinic");
            }
            resultList.add(moduleGraphReportDTO);

        }

        JRDataSource dataSource = new JRBeanCollectionDataSource(resultList);

        parameterMap.put("datasource", dataSource);
        parameterMap.put("REPORT_FILE_RESOLVER", fileResolver);
        return parameterMap;
    }

    @Override
    public List<Map<String, Object>> createChartableDataFor601VeteranClinic(Map<String, Object> requestData) {
        String fromDate = (String) requestData.get(ReportsUtil.FROMDATE);
        String toDate = (String) requestData.get(ReportsUtil.TODATE);
        List cList = (ArrayList) requestData.get(ReportsUtil.CLINIC_ID_LIST);
        List sList = (ArrayList) requestData.get(ReportsUtil.SURVEY_ID_LIST);

        List<Map<String, Object>> chartableDataList = Lists.newArrayList();
        for (Object oClinicId : cList) {
            Integer clinicId = (Integer) oClinicId;
            List<Integer> veterans = clinicService.getAllVeteranIds(clinicId);
            for (Integer vId : veterans) {
                for (Object oSurveyId : sList) {
                    Integer surveyId = (Integer) oSurveyId;
                    chartableDataList.add(createChartableDataForIndividualStats(clinicId, surveyId, vId, fromDate, toDate));
                }
            }
        }
        return chartableDataList;
    }

    @Override
    public List<Map<String, Object>> createChartableDataFor601Clinic(Map<String, Object> requestData) {
        List<Map<String, Object>> chartableDataList = Lists.newArrayList();

        logger.debug("Generating the clinic graph data ");

        String fromDate = (String) requestData.get(ReportsUtil.FROMDATE);
        String toDate = (String) requestData.get(ReportsUtil.TODATE);
        List cList = (ArrayList) requestData.get(ReportsUtil.CLINIC_ID_LIST);
        List sList = (ArrayList) requestData.get(ReportsUtil.SURVEY_ID_LIST);

        for (Object oClinicId : cList) {

            Integer clinicId = (Integer) oClinicId;

            for (Object oSurveyId : sList) {

                Integer surveyId = (Integer) oSurveyId;

                chartableDataList.add(createChartableDataFor601Clinic(clinicId, surveyId, fromDate, toDate));
            }
        }
        return chartableDataList;
    }

    @Override
    public List<Map<String, Object>> createChartableDataForIndividualStats(Map<String, Object> requestData) {
        List<Map<String, Object>> chartableDataList = Lists.newArrayList();

        logger.debug("Generating the individual statistics reports");

        String lastName = (String) requestData.get(ReportsUtil.LASTNAME);
        String last4SSN = (String) requestData.get(ReportsUtil.SSN_LAST_FOUR);
        String fromDate = (String) requestData.get(ReportsUtil.FROMDATE);
        String toDate = (String) requestData.get(ReportsUtil.TODATE);

        VeteranDto veteran = new VeteranDto();
        veteran.setLastName(lastName);
        veteran.setSsnLastFour(last4SSN);

        List<VeteranDto> veterans = assessmentDelegate.findVeterans(veteran);

        if (veterans == null || veterans.isEmpty()) {
            return chartableDataList;
        }

        Integer veteranId = veterans.get(0).getVeteranId();

        for (Object strSurveyId : (ArrayList) requestData.get(ReportsUtil.SURVEY_ID_LIST)) {

            Integer surveyId = (Integer) strSurveyId;

            Map<String, Object> r = createChartableDataForIndividualStats(surveyId, veteranId, fromDate, toDate);

            if (r != null) {
                chartableDataList.add(r);
            }
        }

        return chartableDataList;

    }

    @Override
    public Map<String, Object> genIndividualStatisticsNumeric(Map<String, Object> requestData, EscreenUser escreenUser) {
        String lastName = (String) requestData.get(ReportsUtil.LASTNAME);
        String last4SSN = (String) requestData.get(ReportsUtil.SSN_LAST_FOUR);
        String fromDate = (String) requestData.get(ReportsUtil.FROMDATE);
        String toDate = (String) requestData.get(ReportsUtil.TODATE);

        logger.debug(" lastName :" + lastName);
        logger.debug(" last4SSN :" + last4SSN);
        logger.debug(" From :" + fromDate + " TO: " + toDate);

        Map<String, Object> parameterMap = Maps.newHashMap();
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


            for (Object strSurveyId : (ArrayList) requestData.get(ReportsUtil.SURVEY_ID_LIST)) {

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
        return parameterMap;
    }

    @Override
    public Map<String, Object> genAvgScoresVetByClinicNumeric(HashMap<String, Object> requestData, EscreenUser escreenUser) {
        Map<String, Object> parameterMap = Maps.newHashMap();
        String displayOption = (String) requestData.get(ReportsUtil.DISPLAY_OPTION);
        if ("individualData".equals(displayOption)) {

        }

        ArrayList idList = (ArrayList) requestData.get(ReportsUtil.CLINIC_ID_LIST);
        return parameterMap;
    }

    @Override
    public Map<String, Object> genClinicStatisticReportsPart1eScreeningBatteriesReport(HashMap<String, Object> requestData, EscreenUser escreenUser) {
        Map<String, Object> parameterMap = Maps.newHashMap();
        return parameterMap;
    }

    @Override
    public Map<String, Object> genClinicStatisticReportsPartIVAverageTimePerModuleReport(HashMap<String, Object> requestData, EscreenUser escreenUser) {
        Map<String, Object> parameterMap = Maps.newHashMap();

        parameterMap.put("fromToDate"," from 2/1/2015 to 2/3/2015");
        parameterMap.put("clinicNames","MAMMOGRAM");

        JRDataSource dataSource = null;

        List<SurveyTimeDTO> dtos = new ArrayList<>();

        SurveyTimeDTO surveyTimeDTO = new SurveyTimeDTO();

        surveyTimeDTO.setModuleName("AUDIT-C");
        surveyTimeDTO.setModuleTime("20");
        dtos.add(surveyTimeDTO);

        surveyTimeDTO = new SurveyTimeDTO();
        surveyTimeDTO.setModuleName("AUDIT-C2");
        surveyTimeDTO.setModuleTime("23");
        dtos.add(surveyTimeDTO);

        surveyTimeDTO = new SurveyTimeDTO();
        surveyTimeDTO.setModuleName("Goals");
        surveyTimeDTO.setModuleTime("24");
        dtos.add(surveyTimeDTO);

        surveyTimeDTO = new SurveyTimeDTO();
        surveyTimeDTO.setModuleName("Exposure");
        surveyTimeDTO.setModuleTime("27");
        dtos.add(surveyTimeDTO);

        dataSource = new JRBeanCollectionDataSource(dtos);


        parameterMap.put("datasource", dataSource);
        parameterMap.put("REPORT_FILE_RESOLVER", fileResolver);

        return parameterMap;
    }

    @Override
    public Map<String, Object> genClinicStatisticReportsPartVDemographicsReport(HashMap<String, Object> requestData, EscreenUser escreenUser) {
        Map<String, Object> parameterMap = Maps.newHashMap();

        parameterMap.put("fromToDate"," from 2/1/2015 to 2/3/2015");
        parameterMap.put("clinicNames","MAMMOGRAM");
        parameterMap.put("female_percentage","1%");
        parameterMap.put("female_count","1/100");
        parameterMap.put("male_percentage","2%");
        parameterMap.put("male_count","2/100");
        parameterMap.put("hispanic_percentage","3%");
        parameterMap.put("hispanic_count","3/100");
        parameterMap.put("non_hispanic_percentage","4%");
        parameterMap.put("non_hispanic_count","4/100");
        parameterMap.put("missingethnicity_percentage","5%");
        parameterMap.put("missingethnicity_count","5/100");
        parameterMap.put("white_percentage","6%");
        parameterMap.put("white_count","6/100");
        parameterMap.put("black_percentage","7%");
        parameterMap.put("black_count","7/100");
        parameterMap.put("indian_percentage","8%");
        parameterMap.put("indian_count","8/100");
        parameterMap.put("hawaiian_percentage","9%");
        parameterMap.put("hawaiian_count","9/100");
        parameterMap.put("asian_percentage","10%");
        parameterMap.put("asian_count","10/100");
        parameterMap.put("norace_percentage","11%");
        parameterMap.put("norace_count","11/100");
        parameterMap.put("otherrace_percentage","12%");
        parameterMap.put("otherrace_count","12/100");
        parameterMap.put("highschool_percentage","13%");
        parameterMap.put("highschool_count","13/100");
        parameterMap.put("ged_percentage","14%");
        parameterMap.put("ged_count","14/100");
        parameterMap.put("highschooldip_percentage","15%");
        parameterMap.put("highschooldip_count","15/100");
        parameterMap.put("ged_percentage","16%");
        parameterMap.put("ged_count","16/100");
        parameterMap.put("highschooldip_percentage","17%");
        parameterMap.put("highschooldip_count","17/100");
        parameterMap.put("somecollege_percentage","18%");
        parameterMap.put("somecollege_count","18/100");
        parameterMap.put("associate_percentage","19%");
        parameterMap.put("associate_count","19/100");
        parameterMap.put("college_percentage","20%");
        parameterMap.put("college_count","20/100");
        parameterMap.put("master_percentage","21%");
        parameterMap.put("master_count","21/100");
        parameterMap.put("dr_percentage","22%");
        parameterMap.put("dr_count","22/100");
        parameterMap.put("missingedu_percentage","23%");
        parameterMap.put("missingedu_count","23/100");
        parameterMap.put("fulltime_percentage","24%");
        parameterMap.put("fulltime_count","24/100");
        parameterMap.put("parttime_percentage","25%");
        parameterMap.put("parttime_count","25/200");
        parameterMap.put("seasonal_percentage","26%");
        parameterMap.put("seasonal_count","26/100");
        parameterMap.put("daylabor_percentage","27%");
        parameterMap.put("daylabor_count","27/100");
        parameterMap.put("unemployed_percentage","28%");
        parameterMap.put("unemployed_count","28/100");
        parameterMap.put("missingemp_percentage","29%");
        parameterMap.put("missingemp_count","29/100");
        parameterMap.put("army_percentage","30%");
        parameterMap.put("army_count","30/100");
        parameterMap.put("airforce_percentage","31%");
        parameterMap.put("airforce_count","31/100");
        parameterMap.put("coast_percentage","32%");
        parameterMap.put("coast_count","32/100");
        parameterMap.put("marines_percentage","33%");
        parameterMap.put("marines_count","33/100");
        parameterMap.put("nationalguard_percentage","34%");
        parameterMap.put("nationalguard_count","34/100");
        parameterMap.put("navy_percentage","35%");
        parameterMap.put("navy_count","35/100");
        parameterMap.put("missingmilitary_percentage","36%");
        parameterMap.put("missingmilitary_count","36/100");
        parameterMap.put("tobacco_never_percentage","37%");
        parameterMap.put("tobacco_never_count","37/100");
        parameterMap.put("tobacco_no_percentage","38%");
        parameterMap.put("tobacco_no_count","38/100");
        parameterMap.put("tobacco_yes_percentage","39%");
        parameterMap.put("tobacco_miss_percentage","39/100");
        parameterMap.put("tobacco_yes_count","40%");
        parameterMap.put("tobacco_miss_count","40/100");
        parameterMap.put("numberofdeployments","Mean number of deployments = 2.5 and minimum Value = 6 and Maximum Value = 10");
        parameterMap.put("age","Mean Age 3.3 years Minimum Value = 60 and Maximum value = 80");

        parameterMap.put("datasource", new JREmptyDataSource());
        parameterMap.put("REPORT_FILE_RESOLVER", fileResolver);

        return parameterMap;
    }

    private Map<String, Object> createChartableDataForIndividualStats(Integer surveyId, Integer veteranId, String fromDate, String toDate) {

        Map<String, Object> chartableDataMap = Maps.newHashMap();

        final Map<String, Object> surveyDataForIndividualStatisticsGraph = scoreService.getSurveyDataForIndividualStatisticsGraph(surveyId, veteranId, fromDate, toDate);

        if (surveyDataForIndividualStatisticsGraph == null) {
            return null;
        }

        final Map<String, Object> metaData = intervalService.generateMetadata(surveyId);
        metaData.put("score", surveyDataForIndividualStatisticsGraph.values().iterator().next());

        chartableDataMap.put("dataSet", surveyDataForIndividualStatisticsGraph);
        chartableDataMap.put("dataFormat", metaData);

        return chartableDataMap;
    }

    private Map<String, Object> createChartableDataForIndividualStats(Integer clinicId, Integer surveyId, Integer veteranId, String fromDate, String toDate) {

        Map<String, Object> chartableDataMap = Maps.newHashMap();

        chartableDataMap.put("dataSet", scoreService.getSurveyDataForIndividualStatisticsGraph(clinicId, surveyId, veteranId, fromDate, toDate));
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
