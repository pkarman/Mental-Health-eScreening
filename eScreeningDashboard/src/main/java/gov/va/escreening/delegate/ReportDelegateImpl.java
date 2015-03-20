package gov.va.escreening.delegate;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
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
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.util.FileResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Resource(type = AssessmentVariableService.class)
    AssessmentVariableService avs;

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
        List<String> svgObject = (List<String>) requestData.get("svgData");
        Map<String, Object> userReqData = (Map<String, Object>) requestData.get("userReqData");

        Map<String, Object> parameterMap = Maps.newHashMap();


        String fromDate = (String) userReqData.get(ReportsUtil.FROMDATE);
        String toDate = (String) userReqData.get(ReportsUtil.TODATE);
        List cClinicList = (List) userReqData.get(ReportsUtil.CLINIC_ID_LIST);
        List sSurveyList = (List) userReqData.get(ReportsUtil.SURVEY_ID_LIST);

        attachDates(parameterMap, userReqData);

        List<ClinicVeteranDTO> resultList = Lists.newArrayList();

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

                        if (svgObject != null && svgObject.size() > 0) {
                            moduleGraphReportDTO.setImageInput(ReportsUtil.SVG_HEADER + svgObject.get(index++));
                        }
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

        List<String> surveyNames = Lists.newArrayList();
        surveyNames.addAll(reportableFormulasMap.keySet());

        return surveyService.getSurveyListByNames(surveyNames);
    }

    @Override
    public List<ClinicDto> getClinicDtoList() {
        return clinicService.getClinicDtoList();
    }

    @Override
    public Map<String, Object> getAveScoresByClinicGraphOrNumeric(Map<String, Object> requestData, EscreenUser escreenUser, boolean includeCount) {
        List<String> svgObject = (List<String>) requestData.get("svgData");
        Map<String, Object> userReqData = (Map<String, Object>) requestData.get("userReqData");

        Map<String, Object> parameterMap = Maps.newHashMap();

        String fromDate = (String) userReqData.get(ReportsUtil.FROMDATE);
        String toDate = (String) userReqData.get(ReportsUtil.TODATE);
        List cClinicList = (List) userReqData.get(ReportsUtil.CLINIC_ID_LIST);
        List sSurveyList = (List) userReqData.get(ReportsUtil.SURVEY_ID_LIST);

        attachDates(parameterMap, userReqData);

        List<Integer> surveyIds = Lists.newArrayList();

        for (Object s : sSurveyList) {
            surveyIds.add((Integer) s);
        }

        List<ClinicDTO> resultList = Lists.newArrayList();

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

                if (svgObject != null && svgObject.size() > 0) {
                    for (ModuleGraphReportDTO moduleGraphReportDTO : dto.getGraphReport()) {
                        moduleGraphReportDTO.setImageInput(ReportsUtil.SVG_HEADER + svgObject.get(index++));
                    }
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
        List<String> svgObject = (List<String>) requestData.get("svgData");
        Map<String, Object> userReqData = (Map<String, Object>) requestData.get("userReqData");

        Map<String, Object> parameterMap = Maps.newHashMap();

        String fromDate = (String) userReqData.get(ReportsUtil.FROMDATE);
        String toDate = (String) userReqData.get(ReportsUtil.TODATE);
        List cClinicList = (List) userReqData.get(ReportsUtil.CLINIC_ID_LIST);
        List sSurveyList = (List) userReqData.get(ReportsUtil.SURVEY_ID_LIST);

        attachDates(parameterMap, userReqData);

        List<Integer> surveyIds = Lists.newArrayList();

        for (Object s : sSurveyList) {
            surveyIds.add((Integer) s);
        }

        List<ClinicVeteranDTO> resultList = Lists.newArrayList();

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
        List<String> svgObject = (List<String>) requestData.get("svgData");
        Map<String, Object> userReqData = (Map<String, Object>) requestData.get("userReqData");

        Map<String, Object> parameterMap = Maps.newHashMap();

        String lastName = (String) userReqData.get(ReportsUtil.LASTNAME);
        String last4SSN = (String) userReqData.get(ReportsUtil.SSN_LAST_FOUR);
        String fromDate = (String) userReqData.get(ReportsUtil.FROMDATE);
        String toDate = (String) userReqData.get(ReportsUtil.TODATE);
        List surveyIds = (List) userReqData.get(ReportsUtil.SURVEY_ID_LIST);

        attachDates(parameterMap, userReqData);
        parameterMap.put("lastNameSSN", lastName + ", " + last4SSN);

        VeteranDto veteran = new VeteranDto();
        veteran.setLastName(lastName);
        veteran.setSsnLastFour(last4SSN);

        List<VeteranDto> veterans = assessmentDelegate.findVeterans(veteran);

        Integer veteranId = -1;

        if (veterans != null && !veterans.isEmpty()) {
            veteranId = veterans.get(0).getVeteranId();
        }

        List<ModuleGraphReportDTO> resultList = Lists.newArrayList();

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
    public List<Map<String, Object>> createIndivChartableDataForAvgScoresForPatientsByClinic(Map<String, Object> requestData) {
        String fromDate = (String) requestData.get(ReportsUtil.FROMDATE);
        String toDate = (String) requestData.get(ReportsUtil.TODATE);
        List cList = (List) requestData.get(ReportsUtil.CLINIC_ID_LIST);
        List sList = (List) requestData.get(ReportsUtil.SURVEY_ID_LIST);

        List<Map<String, Object>> chartableDataList = Lists.newArrayList();
        for (Object oClinicId : cList) {
            Integer clinicId = (Integer) oClinicId;
            List<Integer> veterans = clinicService.getAllVeteranIds(clinicId);
            for (Integer vId : veterans) {
                for (Object oSurveyId : sList) {
                    Integer surveyId = (Integer) oSurveyId;
                    final Map<String, Object> chartableDataForIndividualStats = createChartableDataForIndivAvgScoresForPatientsByClinic(clinicId, surveyId, vId, fromDate, toDate);
                    if (!chartableDataForIndividualStats.isEmpty()) {
                        chartableDataList.add(chartableDataForIndividualStats);
                    }
                }
            }
        }
        return chartableDataList;
    }

    @Override
    public List<Map<String, Object>> createGrpChartableDataForAvgScoresForPatientsByClinic(Map<String, Object> requestData) {
        List<Map<String, Object>> chartableDataList = Lists.newArrayList();

        logger.debug("Generating the clinic graph data ");

        String fromDate = (String) requestData.get(ReportsUtil.FROMDATE);
        String toDate = (String) requestData.get(ReportsUtil.TODATE);
        List cList = (List) requestData.get(ReportsUtil.CLINIC_ID_LIST);
        List sList = (List) requestData.get(ReportsUtil.SURVEY_ID_LIST);

        for (Object oClinicId : cList) {

            Integer clinicId = (Integer) oClinicId;

            for (Object oSurveyId : sList) {

                Integer surveyId = (Integer) oSurveyId;
                final Map<String, Object> chartableDataForClinic = createChartableDataForGrpAvgScoresForPatientsByClinic(clinicId, surveyId, fromDate, toDate);
                if (!chartableDataForClinic.isEmpty()) {
                    chartableDataList.add(chartableDataForClinic);
                }
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

        for (Object strSurveyId : (List) requestData.get(ReportsUtil.SURVEY_ID_LIST)) {

            Integer surveyId = (Integer) strSurveyId;

            final Map<String, Object> chartableDataForIndividualStats = createChartableDataForIndividualStats(surveyId, veteranId, fromDate, toDate);
            if (!chartableDataForIndividualStats.isEmpty()) {
                chartableDataList.add(chartableDataForIndividualStats);
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
        attachDates(parameterMap, requestData);

        VeteranDto veteran = new VeteranDto();
        veteran.setLastName(lastName);
        veteran.setSsnLastFour(last4SSN);

        List<VeteranDto> veterans = assessmentDelegate.findVeterans(veteran);

        JRDataSource dataSource = null;

        if (veterans == null || veterans.isEmpty()) {
            dataSource = new JREmptyDataSource();
        } else {


            List<TableReportDTO> resultList = Lists.newArrayList();


            for (Object strSurveyId : (List) requestData.get(ReportsUtil.SURVEY_ID_LIST)) {

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
    public Map<String, Object> genAvgScoresVetByClinicNumeric(Map<String, Object> requestData, EscreenUser escreenUser) {
        Map<String, Object> parameterMap = Maps.newHashMap();
        String displayOption = (String) requestData.get(ReportsUtil.DISPLAY_OPTION);
        if ("individualData".equals(displayOption)) {

        }

        List idList = (List) requestData.get(ReportsUtil.CLINIC_ID_LIST);
        return parameterMap;
    }

    @Override
    public Map<String, Object> genClinicStatisticReportsPart1eScreeningBatteriesReport(Map<String, Object> requestData, EscreenUser escreenUser) {
        Map<String, Object> parameterMap = Maps.newHashMap();
        attachDates(parameterMap, requestData);
        attachClinics(parameterMap, requestData);

        parameterMap.put("datasource", new JREmptyDataSource());


        if (requestData.get("eachDay")!=null && ((Boolean)requestData.get("eachDay"))){
            parameterMap.put("showByDay", true);
            List<Report593ByDayDTO> data = new ArrayList<>();

            Report593ByDayDTO byDayDTO = new Report593ByDayDTO();
            //TODO: populate the data here
            byDayDTO.setDate("01/02/2015");
            byDayDTO.setDayOfWeek("Tuesday");
            byDayDTO.setTotal("15");
            data.add(byDayDTO);

            byDayDTO = new Report593ByDayDTO();
            byDayDTO.setDate("01/03/2015");
            byDayDTO.setDayOfWeek("Wednesday");
            byDayDTO.setTotal("25");
            data.add(byDayDTO);

            parameterMap.put("byDay", data);
            parameterMap.put("grandTotal", "40");

        }else{
            parameterMap.put("showByDay", false);
            parameterMap.put("byDay", Lists.newArrayList());
        }


        if (requestData.get("timeOfDayWeek")!=null && ((Boolean)requestData.get("timeOfDayWeek"))){
            parameterMap.put("showByTime", true);
            List<Report593ByTimeDTO> data = new ArrayList<>();

            Report593ByTimeDTO byTimeDTO = new Report593ByTimeDTO();
            //TODO: populate the data here
            byTimeDTO.setDayOfWeek("Tuesday");
            byTimeDTO.setTotal("20");
            byTimeDTO.setDate("01/02/2015");
            byTimeDTO.setEightToTen("1");
            byTimeDTO.setFourToSix("2");
            byTimeDTO.setSixToEight("3");
            byTimeDTO.setTenToTwelve("0");
            byTimeDTO.setTwelveToTwo("1");
            byTimeDTO.setTwoToFour("3");
            data.add(byTimeDTO);

            parameterMap.put("byTime", data);
            parameterMap.put("grandTotal", "100");


        }else{
            parameterMap.put("showByTime", false);
            parameterMap.put("byTime", Lists.newArrayList());
        }

        List<KeyValueDTO> ks = Lists.newArrayList();


        KeyValueDTO keyValueDTO;

        boolean bCheckAll = false;

        if (requestData.get("numberOfUniqueVeteran")!=null
                &&(Boolean)requestData.get("numberOfUniqueVeteran")) {
            keyValueDTO = new KeyValueDTO();
            keyValueDTO.setKey("Number of Unique Patients");
            keyValueDTO.setValue("100");
            ks.add(keyValueDTO);
            bCheckAll = true;
        }

        if (requestData.get("numberOfeScreeningBatteries")!=null
                && (Boolean)requestData.get("numberOfeScreeningBatteries")) {
            keyValueDTO = new KeyValueDTO();
            keyValueDTO.setValue("12");
            keyValueDTO.setKey("Number of eScreening batteries completed");
            ks.add(keyValueDTO);
            bCheckAll = true;
        }

        if (requestData.get("averageTimePerAssessment")!=null
                && (Boolean)requestData.get("averageTimePerAssessment")) {
            keyValueDTO = new KeyValueDTO();
            keyValueDTO.setValue("13 min");
            keyValueDTO.setKey("Average time per assessment");
            ks.add(keyValueDTO);
            bCheckAll = true;
        }

        if (requestData.get("numberOfAssessmentsPerClinician")!=null &&
                (Boolean)requestData.get("numberOfAssessmentsPerClinician")) {
            keyValueDTO = new KeyValueDTO();
            keyValueDTO.setValue("15");
            keyValueDTO.setKey("Number of assessments per clinician in each clinic");
            ks.add(keyValueDTO);
            bCheckAll = true;
        }

        if (requestData.get("numberAndPercentVeteransWithMultiple")!=null
                && (Boolean)requestData.get("numberAndPercentVeteransWithMultiple")) {
            keyValueDTO = new KeyValueDTO();
            keyValueDTO.setValue("16");
            keyValueDTO.setKey("Number and percent of veterans with multiple eScreening batteries");
            ks.add(keyValueDTO);
            bCheckAll = true;
        }

        parameterMap.put("checkAll", ks);
        parameterMap.put("showCheckAll", bCheckAll);

        return parameterMap;
    }

    @Override
    public Map<String, Object> genClinicStatisticReportsPartIVAverageTimePerModuleReport(Map<String, Object> requestData, EscreenUser escreenUser) {
        Map<String, Object> parameterMap = Maps.newHashMap();

        attachDates(parameterMap, requestData);
        attachClinics(parameterMap, requestData);

        JRDataSource dataSource = null;

        List<SurveyTimeDTO> dtos = Lists.newArrayList();

        Iterable<String> clinicNames = Splitter.on(",").trimResults().omitEmptyStrings().split(parameterMap.get("clinicNames").toString());
        for (String clinicName : clinicNames) {
            SurveyTimeDTO surveyTimeDTO = new SurveyTimeDTO();
            surveyTimeDTO.setModuleName(clinicName);
            surveyTimeDTO.setModuleTime(String.valueOf((int) (Math.random() * 100)));
            dtos.add(surveyTimeDTO);
        }

        dataSource = new JRBeanCollectionDataSource(dtos);
        //dataSource = new JREmptyDataSource();

        parameterMap.put("datasource", dataSource);
        parameterMap.put("REPORT_FILE_RESOLVER", fileResolver);

        return parameterMap;
    }

    @Override
    public Map<String, Object> genClinicStatisticReportsPartVDemographicsReport(Map<String, Object> requestData, EscreenUser escreenUser) {
        Map<String, Object> parameterMap = Maps.newHashMap();

        attachDates(parameterMap, requestData);
        attachClinics(parameterMap, requestData);
        attachGender(parameterMap, requestData);
        attachEthnicity(parameterMap, requestData);
        attachAge(parameterMap, requestData);
        attachEducation(parameterMap, requestData);
        attachEmploymentStatus(parameterMap, requestData);
        attachMilitaryBranch(parameterMap, requestData);
        attachTobaccoUsage(parameterMap, requestData);
        attachDeployments(parameterMap, requestData);

        parameterMap.put("datasource", new JREmptyDataSource());
        parameterMap.put("REPORT_FILE_RESOLVER", fileResolver);

        return parameterMap;
    }

    @Override
    public Map<String, Object> genClinicStatisticReportsPartIIIList20MostSkippedQuestionsReport(Map<String, Object> requestData, EscreenUser escreenUser) {
        Map<String, Object> parameterMap = Maps.newHashMap();
        attachDates(parameterMap, requestData);
        attachClinics(parameterMap, requestData);
        JRDataSource dataSource = null;

        List<Report595DTO> dtos = Lists.newArrayList();

        int i = 1;
        for (Integer surveyId : (List<Integer>) requestData.get(ReportsUtil.CLINIC_ID_LIST)) {
            final Table<String, String, Object> assessmentVarsForSurvey = avs.getAssessmentVarsForSurvey(surveyId, true, false);
            final Collection<Map<String, Object>> avCollection = assessmentVarsForSurvey.rowMap().values();

            for (Map<String, Object> av : avCollection) {
                if (i <= 20 && !Integer.valueOf(0).equals(av.get("measureId"))) {
                    Report595DTO report595DTO = new Report595DTO();
                    report595DTO.setOrder("" + i++);
                    report595DTO.setPercentage(String.format("%5.2f%%", Math.random() * 100));
                    report595DTO.setQuestions(av.get("displayName").toString());
                    report595DTO.setVariableName(av.get("name").toString());
                    dtos.add(report595DTO);
                }
                if (i > 20) {
                    break;
                }
            }
        }
        Collections.sort(dtos, new Comparator<Report595DTO>() {
            @Override
            public int compare(Report595DTO o1, Report595DTO o2) {
                return Float.valueOf(o2.getPercentage().replaceAll("%", "").trim()).compareTo(Float.valueOf(o1.getPercentage().replaceAll("%", "").trim()));
            }
        });
        dataSource = new JRBeanCollectionDataSource(dtos);

        parameterMap.put("datasource", dataSource);
        parameterMap.put("REPORT_FILE_RESOLVER", fileResolver);

        return parameterMap;
    }

    @Override
    public Map<String, Object> genClinicStatisticReportsPartIIMostCommonTypesOfAlertsPercentagesReport(Map<String, Object> requestData, EscreenUser escreenUser) {
        Map<String, Object> parameterMap = Maps.newHashMap();

        attachDates(parameterMap, requestData);
        attachClinics(parameterMap, requestData);

        JRDataSource dataSource = null;

        List<Report594DTO> dtos = Lists.newArrayList();

        Iterable<String> clinicNames = Splitter.on(",").trimResults().omitEmptyStrings().split(parameterMap.get("clinicNames").toString());

        int total =  0;

        for (String clinicName : clinicNames) {
            Report594DTO report594DTO = new Report594DTO();
            report594DTO.setModuleName(clinicName);
            int val = (int) (Math.random() * 100);
            total += val;
            report594DTO.setModuleCount(String.format("%s", val));
            report594DTO.setModulePercent(String.format("%5.2f%%", Math.random() * 100));
            dtos.add(report594DTO);
        }

        for(int i=0; i<dtos.size(); i++){

            int val = Integer.parseInt(dtos.get(i).getModuleCount());
            dtos.get(i).setModuleCount( val + "/"+total);
            dtos.get(i).setModulePercent(String.format("%5.2f%%", val* 100d/total));
        }

        dataSource = new JRBeanCollectionDataSource(dtos);

        parameterMap.put("datasource", dataSource);
        parameterMap.put("REPORT_FILE_RESOLVER", fileResolver);
        return parameterMap;
    }

    @Override
    public Map<String, Object> genClinicStatisticReportsPartVIPositiveScreensReport(Map<String, Object> requestData, EscreenUser escreenUser) {
        Map<String, Object> parameterMap = Maps.newHashMap();
        attachDates(parameterMap, requestData);
        attachClinics(parameterMap, requestData);

        JRDataSource dataSource = null;

        List<Report599DTO> dtos = Lists.newArrayList();

        Iterable<String> clinicNames = Splitter.on(",").trimResults().omitEmptyStrings().split(parameterMap.get("clinicNames").toString());
        for (String clinicName : clinicNames) {
            Report599DTO dto = new Report599DTO();
            dto.setMissingCount(String.format("%s", (int) (Math.random() * 100)));
            dto.setMissingPercent(String.format("%5.2f%%", Math.random() * 100));
            dto.setModuleName(clinicName);
            dto.setNegativeCount(String.format("%s", (int) (Math.random() * 100)));
            dto.setNegativePercent(String.format("%5.2f%%", Math.random() * 100));
            dto.setPositiveCount(String.format("%s", (int) (Math.random() * 100)));
            dto.setPositivePercent(String.format("%5.2f%%", Math.random() * 100));
            dtos.add(dto);
        }

        dataSource = new JRBeanCollectionDataSource(dtos);
        parameterMap.put("datasource", dataSource);
        parameterMap.put("REPORT_FILE_RESOLVER", fileResolver);
        return parameterMap;
    }

    private void attachDates(Map<String, Object> dataCollection, Map<String, Object> requestData) {
        String fromDate = requestData.get(ReportsUtil.FROMDATE).toString();
        String toDate = requestData.get(ReportsUtil.TODATE).toString();
        dataCollection.put("fromToDate", String.format("From %s -- %s", fromDate, toDate));
    }

    private void attachClinics(Map<String, Object> dataCollection, Map<String, Object> requestData) {
        StringBuilder sb = new StringBuilder();
        for (Integer clicnicId : (List<Integer>) requestData.get(ReportsUtil.CLINIC_ID_LIST)) {
            sb.append(clinicService.getClinicNameById(clicnicId)).append(", ");
        }
        dataCollection.put("clinicNames", sb.toString());
    }

    private void attachDeployments(Map<String, Object> dataCollection, Map<String, Object> requestData) {
        dataCollection.put("numberofdeployments", "Mean number of deployments = 2.5 and minimum Value = 6 and Maximum Value = 10");
    }

    private void attachTobaccoUsage(Map<String, Object> dataCollection, Map<String, Object> requestData) {
        dataCollection.put("tobacco_never_percentage", "37%");
        dataCollection.put("tobacco_never_count", "37/100");
        dataCollection.put("tobacco_no_percentage", "38%");
        dataCollection.put("tobacco_no_count", "38/100");
        dataCollection.put("tobacco_yes_percentage", "39%");
        dataCollection.put("tobacco_miss_percentage", "39/100");
        dataCollection.put("tobacco_yes_count", "40%");
        dataCollection.put("tobacco_miss_count", "40/100");
    }

    private void attachMilitaryBranch(Map<String, Object> dataCollection, Map<String, Object> requestData) {
        dataCollection.put("army_percentage", "30%");
        dataCollection.put("army_count", "30/100");
        dataCollection.put("airforce_percentage", "31%");
        dataCollection.put("airforce_count", "31/100");
        dataCollection.put("coast_percentage", "32%");
        dataCollection.put("coast_count", "32/100");
        dataCollection.put("marines_percentage", "33%");
        dataCollection.put("marines_count", "33/100");
        dataCollection.put("nationalguard_percentage", "34%");
        dataCollection.put("nationalguard_count", "34/100");
        dataCollection.put("navy_percentage", "35%");
        dataCollection.put("navy_count", "35/100");
        dataCollection.put("missingmilitary_percentage", "36%");
        dataCollection.put("missingmilitary_count", "36/100");
    }

    private void attachEmploymentStatus(Map<String, Object> dataCollection, Map<String, Object> requestData) {
        dataCollection.put("fulltime_percentage", "24%");
        dataCollection.put("fulltime_count", "24/100");
        dataCollection.put("parttime_percentage", "25%");
        dataCollection.put("parttime_count", "25/200");
        dataCollection.put("seasonal_percentage", "26%");
        dataCollection.put("seasonal_count", "26/100");
        dataCollection.put("daylabor_percentage", "27%");
        dataCollection.put("daylabor_count", "27/100");
        dataCollection.put("unemployed_percentage", "28%");
        dataCollection.put("unemployed_count", "28/100");
        dataCollection.put("missingemp_percentage", "29%");
        dataCollection.put("missingemp_count", "29/100");
    }

    private void attachEducation(Map<String, Object> dataCollection, Map<String, Object> requestData) {
        dataCollection.put("highschool_percentage", "13%");
        dataCollection.put("highschool_count", "13/100");
        dataCollection.put("ged_percentage", "14%");
        dataCollection.put("ged_count", "14/100");
        dataCollection.put("highschooldip_percentage", "15%");
        dataCollection.put("highschooldip_count", "15/100");
        dataCollection.put("ged_percentage", "16%");
        dataCollection.put("ged_count", "16/100");
        dataCollection.put("highschooldip_percentage", "17%");
        dataCollection.put("highschooldip_count", "17/100");
        dataCollection.put("somecollege_percentage", "18%");
        dataCollection.put("somecollege_count", "18/100");
        dataCollection.put("associate_percentage", "19%");
        dataCollection.put("associate_count", "19/100");
        dataCollection.put("college_percentage", "20%");
        dataCollection.put("college_count", "20/100");
        dataCollection.put("master_percentage", "21%");
        dataCollection.put("master_count", "21/100");
        dataCollection.put("dr_percentage", "22%");
        dataCollection.put("dr_count", "22/100");
        dataCollection.put("missingedu_percentage", "23%");
        dataCollection.put("missingedu_count", "23/100");
    }

    private void attachAge(Map<String, Object> dataCollection, Map<String, Object> requestData) {
        dataCollection.put("age", "Mean Age 3.3 years Minimum Value = 60 and Maximum value = 80");
    }

    private void attachEthnicity(Map<String, Object> dataCollection, Map<String, Object> requestData) {
        dataCollection.put("hispanic_percentage", "3%");
        dataCollection.put("hispanic_count", "3/100");
        dataCollection.put("non_hispanic_percentage", "4%");
        dataCollection.put("non_hispanic_count", "4/100");
        dataCollection.put("missingethnicity_percentage", "5%");
        dataCollection.put("missingethnicity_count", "5/100");
        dataCollection.put("white_percentage", "6%");
        dataCollection.put("white_count", "6/100");
        dataCollection.put("black_percentage", "7%");
        dataCollection.put("black_count", "7/100");
        dataCollection.put("indian_percentage", "8%");
        dataCollection.put("indian_count", "8/100");
        dataCollection.put("hawaiian_percentage", "9%");
        dataCollection.put("hawaiian_count", "9/100");
        dataCollection.put("asian_percentage", "10%");
        dataCollection.put("asian_count", "10/100");
        dataCollection.put("norace_percentage", "11%");
        dataCollection.put("norace_count", "11/100");
        dataCollection.put("otherrace_percentage", "12%");
        dataCollection.put("otherrace_count", "12/100");
    }

    private void attachGender(Map<String, Object> dataCollection, Map<String, Object> requestData) {
        dataCollection.put("female_percentage", "1%");
        dataCollection.put("female_count", "1/100");
        dataCollection.put("male_percentage", "2%");
        dataCollection.put("male_count", "2/100");
    }

    private Map<String, Object> createChartableDataForIndividualStats(Integer surveyId, Integer veteranId, String fromDate, String toDate) {

        Map<String, Object> chartableDataMap = Maps.newHashMap();

        final Map<String, Object> surveyDataForIndividualStatisticsGraph = scoreService.getSurveyDataForIndividualStatisticsGraph(surveyId, veteranId, fromDate, toDate);

        final Map<String, Object> metaData = intervalService.generateMetadata(surveyId);
        if (metaData != null) {
            metaData.put("score", !surveyDataForIndividualStatisticsGraph.isEmpty() ? surveyDataForIndividualStatisticsGraph.values().iterator().next() : 0);
        }
        chartableDataMap.put("dataSet", surveyDataForIndividualStatisticsGraph);
        chartableDataMap.put("dataFormat", metaData);

        return chartableDataMap;
    }

    private Map<String, Object> createChartableDataForIndivAvgScoresForPatientsByClinic(Integer clinicId, Integer surveyId, Integer veteranId, String fromDate, String toDate) {

        Map<String, Object> chartableDataMap = Maps.newHashMap();

        final Map<String, Object> surveyDataForIndividualStatisticsGraph = scoreService.getSurveyDataForIndividualStatisticsGraph(clinicId, surveyId, veteranId, fromDate, toDate);

        final Map<String, Object> metaData = intervalService.generateMetadata(surveyId);
        if (metaData != null) {
            metaData.put("score", !surveyDataForIndividualStatisticsGraph.isEmpty() ? surveyDataForIndividualStatisticsGraph.values().iterator().next() : 0);
        }
        chartableDataMap.put("dataSet", surveyDataForIndividualStatisticsGraph);
        chartableDataMap.put("dataFormat", metaData);

        return chartableDataMap;
    }

    private Map<String, Object> createChartableDataForGrpAvgScoresForPatientsByClinic(Integer clinicId, Integer surveyId, String fromDate, String toDate) {

        Map<String, Object> chartableDataMap = Maps.newHashMap();

        final Map<String, Object> surveyDataForClinicStatisticsGraph = scoreService.getSurveyDataForClinicStatisticsGraph(clinicId, surveyId, fromDate, toDate);

        final Map<String, Object> metaData = intervalService.generateMetadata(surveyId);
        if (metaData != null) {
            metaData.put("score", !surveyDataForClinicStatisticsGraph.isEmpty() ? surveyDataForClinicStatisticsGraph.values().iterator().next() : 0);
        }

        chartableDataMap.put("dataSet", surveyDataForClinicStatisticsGraph);
        chartableDataMap.put("dataFormat", metaData);

        return chartableDataMap;

    }

}
