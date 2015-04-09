package gov.va.escreening.delegate;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import gov.va.escreening.controller.dashboard.ReportsController;
import gov.va.escreening.domain.ClinicDto;
import gov.va.escreening.domain.SurveyDto;
import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.dto.report.*;
import gov.va.escreening.repository.UserProgramRepository;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.*;
import gov.va.escreening.util.ReportsUtil;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.FileResolver;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Resource(type = VeteranAssessmentSurveyService.class)
    private VeteranAssessmentSurveyService vasSrv;

    @Resource(type = SurveyScoreIntervalService.class)
    private SurveyScoreIntervalService intervalService;

    @Resource(type = VeteranService.class)
    private VeteranService veteranService;

    @Resource(type = VeteranAssessmentService.class)
    private VeteranAssessmentService veteranAssessmentService;

    @Resource(type = UserProgramRepository.class)
    private UserProgramRepository upr;

    @Resource(name = "selectedReportableScoresMap")
    Map<String, String> selectedReportableScoresMap;

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
        surveyNames.addAll(selectedReportableScoresMap.keySet());

        return surveyService.getSurveyListByNames(surveyNames);
    }

    @Override
    public List<ClinicDto> getClinicDtoList() {
        return clinicService.getClinicDtoList();
    }

    @Override
    public Map<String, Object> getAveScoresByClinicGraphOrNumeric(Map<String, Object> requestData, EscreenUser escreenUser, boolean includeCount, boolean isGraphOnly) {
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

                    if (isGraphOnly) {
                        moduleGraphReportDTO.setScoreHistoryTitle(null);
                    }
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
    public Map<String, Object> genClinicStatisticReportsPart1eScreeningBatteriesReport(Map<String, Object> requestData, EscreenUser escreenUser) {
        Map<String, Object> parameterMap = Maps.newHashMap();
        attachDates(parameterMap, requestData);
        attachClinics(parameterMap, requestData);

        parameterMap.put("datasource", new JREmptyDataSource());

        final DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy");
        final LocalDate fromDate = dtf.parseLocalDate(requestData.get(ReportsUtil.FROMDATE).toString());
        final LocalDate toDate = dtf.parseLocalDate(requestData.get(ReportsUtil.TODATE).toString());

        List<Integer> clinicIds = (List<Integer>) requestData.get(ReportsUtil.CLINIC_ID_LIST);
        String strFromDate = (String) requestData.get(ReportsUtil.FROMDATE);
        String strToDate = (String) requestData.get(ReportsUtil.TODATE);

        int totals = 0;
        Map<LocalDate, Integer> dateTotalMap = Maps.newHashMap();
        if (requestData.get("eachDay") != null && ((Boolean) requestData.get("eachDay"))) {
            parameterMap.put("showByDay", true);
            List<Report593ByDayDTO> data = veteranAssessmentService.getBatteriesByDay(strFromDate, strToDate, clinicIds);
            parameterMap.put("byDay", data);

            for (Report593ByDayDTO dto : data) {
                totals += Integer.parseInt(dto.getTotal().trim());
            }

            parameterMap.put("grandTotal", "" + totals);
        } else {
            parameterMap.put("showByDay", false);
            parameterMap.put("byDay", Lists.newArrayList());
        }

        int total2 = 0;

        if (requestData.get("timeOfDayWeek") != null && ((Boolean) requestData.get("timeOfDayWeek"))) {
            parameterMap.put("showByTime", true);
            List<Report593ByTimeDTO> data = veteranAssessmentService.getBatteriesByTime(strFromDate, strToDate, clinicIds);

            for (Report593ByTimeDTO dto : data) {
                total2 += Integer.parseInt(dto.getTotal().trim());
            }

            parameterMap.put("byTime", data);
            parameterMap.put("grandTotal", "" + total2);


        } else {
            parameterMap.put("showByTime", false);
            parameterMap.put("byTime", Lists.newArrayList());
        }

        List<KeyValueDTO> ks = Lists.newArrayList();


        KeyValueDTO keyValueDTO;

        boolean bCheckAll = false;


        if (requestData.get("numberOfUniqueVeteran") != null
                && (Boolean) requestData.get("numberOfUniqueVeteran")) {
            keyValueDTO = new KeyValueDTO();
            keyValueDTO.setKey("Number of Unique Veterans");

            keyValueDTO.setValue(veteranAssessmentService.getUniqueVeterns(clinicIds, strFromDate, strToDate));
            ks.add(keyValueDTO);
            bCheckAll = true;
        }

        if (requestData.get("numberOfeScreeningBatteries") != null
                && (Boolean) requestData.get("numberOfeScreeningBatteries")) {
            keyValueDTO = new KeyValueDTO();
            keyValueDTO.setValue(
                    veteranAssessmentService.getNumOfBatteries(clinicIds, strFromDate, strToDate)
            );
            keyValueDTO.setKey("Number of eScreening batteries completed");
            ks.add(keyValueDTO);
            bCheckAll = true;
        }

        if (requestData.get("averageTimePerAssessment") != null
                && (Boolean) requestData.get("averageTimePerAssessment")) {
            keyValueDTO = new KeyValueDTO();
            keyValueDTO.setValue(
                    veteranAssessmentService.getAverageTimePerAssessment(clinicIds, strFromDate, strToDate) + " min"
            );
            keyValueDTO.setKey("Average time per assessment");
            ks.add(keyValueDTO);
            bCheckAll = true;
        }

        if (requestData.get("numberOfAssessmentsPerClinician") != null &&
                (Boolean) requestData.get("numberOfAssessmentsPerClinician")) {
            keyValueDTO = new KeyValueDTO();
            keyValueDTO.setValue(veteranAssessmentService.calculateAvgAssessmentsPerClinician(clinicIds, strFromDate, strToDate));
            keyValueDTO.setKey("Average number of assessments per clinician in each clinic");
            ks.add(keyValueDTO);
            bCheckAll = true;
        }

        if (requestData.get("numberAndPercentVeteransWithMultiple") != null
                && (Boolean) requestData.get("numberAndPercentVeteransWithMultiple")) {
            keyValueDTO = new KeyValueDTO();
            keyValueDTO.setValue(veteranAssessmentService.getVeteranWithMultiple(clinicIds, strFromDate, strToDate));
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

        Map<Integer, Integer> surveyAvgTimeMap = vasSrv.calculateAvgTimePerSurvey(requestData);

        JRDataSource dataSource = null;

        List<SurveyTimeDTO> dtos = Lists.newArrayList();

        for (SurveyDto survey : surveyService.getSurveyList()) {
            Integer surveyAvgTime = surveyAvgTimeMap.get(survey.getSurveyId());
            if (surveyAvgTime != null) {
                SurveyTimeDTO surveyTimeDTO = new SurveyTimeDTO();
                surveyTimeDTO.setModuleName(survey.getName());
                surveyTimeDTO.setModuleTime(String.valueOf(surveyAvgTime));
                dtos.add(surveyTimeDTO);
            }
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


        List<Integer> clinicIds = (List<Integer>) requestData.get(ReportsUtil.CLINIC_ID_LIST);
        String fromDate = requestData.get(ReportsUtil.FROMDATE).toString();
        String toDate = requestData.get(ReportsUtil.TODATE).toString();

        List<Report595DTO> dtos = veteranAssessmentService.getTopSkippedQuestions(clinicIds, fromDate, toDate);

        if (dtos == null || dtos.isEmpty()) {
            dataSource = new JREmptyDataSource();
        } else {
            dataSource = new JRBeanCollectionDataSource(dtos);
        }

        parameterMap.put("datasource", dataSource);
        parameterMap.put("REPORT_FILE_RESOLVER", fileResolver);

        return parameterMap;
    }

    @Override
    public Map<String, Object> genClinicStatisticReportsPartIIMostCommonTypesOfAlertsPercentagesReport(Map<String, Object> requestData, EscreenUser escreenUser) {
        Map<String, Object> parameterMap = Maps.newHashMap();

        attachDates(parameterMap, requestData);
        attachClinics(parameterMap, requestData);

        String fromDate = (String) requestData.get(ReportsUtil.FROMDATE);
        String toDate = (String) requestData.get(ReportsUtil.TODATE);
        List<Integer> clinicIds = (List<Integer>) requestData.get(ReportsUtil.CLINIC_ID_LIST);

        int totalAssessment = veteranAssessmentService.findAssessmentCount(fromDate, toDate, clinicIds);

        List<Report594DTO> dtos = veteranAssessmentService.findAlertsCount(fromDate, toDate, clinicIds);

        JRDataSource dataSource = null;

        if (totalAssessment == 0 || dtos == null || dtos.size() == 0) {
            dataSource = new JREmptyDataSource();
        } else {
            for (Report594DTO report594DTO : dtos) {

                int numerator = Integer.parseInt(report594DTO.getModuleCount());
                report594DTO.setModuleCount(String.format("%s/%s", numerator, totalAssessment));
                report594DTO.setModulePercent(String.format("%5.2f%%", numerator * 100.0f / totalAssessment));
            }
            dataSource = new JRBeanCollectionDataSource(dtos);
        }

        parameterMap.put("datasource", dataSource);
        parameterMap.put("REPORT_FILE_RESOLVER", fileResolver);
        return parameterMap;
    }


    /**
     * for 599 report
     *
     * @param requestData
     * @param escreenUser
     * @return
     */
    @Override
    public Map<String, Object> genClinicStatisticReportsPartVIPositiveScreensReport
    (Map<String, Object> requestData, EscreenUser escreenUser) {

        Map<String, Object> parameterMap = Maps.newHashMap();
        attachDates(parameterMap, requestData);
        attachClinics(parameterMap, requestData);

        List<String> surveyNameList = new ArrayList<>();
        surveyNameList.addAll(selectedReportableScoresMap.keySet());

        String fromDate = (String) requestData.get(ReportsUtil.FROMDATE);
        String toDate = (String) requestData.get(ReportsUtil.TODATE);
        List<Integer> clinicIds = (List<Integer>) requestData.get(ReportsUtil.CLINIC_ID_LIST);

        JRDataSource dataSource = null;

        List<Report599DTO> dtos = scoreService.getClinicStatisticReportsPartVIPositiveScreensReport
                (fromDate, toDate, clinicIds, surveyNameList);

        if (dtos == null || dtos.isEmpty()) {
            dataSource = new JREmptyDataSource();
        } else {
            dataSource = new JRBeanCollectionDataSource(dtos);
        }

        parameterMap.put("datasource", dataSource);
        parameterMap.put("REPORT_FILE_RESOLVER", fileResolver);
        return parameterMap;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClinicDto> getClinicDtoList(EscreenUser escreenUser) {
        final List<ClinicDto> clinicDtoList = getClinicDtoList();

        final List<ClinicDto> allowedClinic = Lists.newArrayList();

        Integer userId = escreenUser == null ? 0 : escreenUser.getUserId();

        if (userId == 0) {
            logger.warn(String.format("User Id is 0, therefore no clinics will be returned. Please check the user id of the ecreen user logged in"));
        }

        // use this user Id and go an get try to get UserProgram using this id and each programId from clinic.
        // If found then that is a intersection and that clinic is allowed
        for (ClinicDto clinicDto : clinicDtoList) {
            Integer programId = clinicDto.getProgramId();

            if (upr.hasUserAndProgram(userId, programId)) {
                allowedClinic.add(clinicDto);
            }
        }

        return allowedClinic;
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
        if (sb.length() > 2) {
            sb.delete(sb.length() - 2, sb.length());
        }
        dataCollection.put("clinicNames", sb.toString());
    }

    private void attachDeployments(Map<String, Object> dataCollection, Map<String, Object> requestData) {
        String fromDate = (String) requestData.get(ReportsUtil.FROMDATE);
        String toDate = (String) requestData.get(ReportsUtil.TODATE);
        List<Integer> cList = (List<Integer>) requestData.get(ReportsUtil.CLINIC_ID_LIST);

        List<Number> result = veteranAssessmentService.getNumOfDeploymentStatistics(cList, fromDate, toDate);

        if (result == null || result.size() != 3) {
            dataCollection.put("numberofdeployments", "");
        } else {
            dataCollection.put("numberofdeployments",
                    String.format("Mean number of deployments = %2.1f and minimum Value = %d and Maximum Value = %d",
                            result.get(0).floatValue(),
                            result.get(1).intValue(),
                            result.get(2).intValue()
                    ));
        }
    }

    private void attachTobaccoUsage(Map<String, Object> dataCollection, Map<String, Object> requestData) {
        String fromDate = (String) requestData.get(ReportsUtil.FROMDATE);
        String toDate = (String) requestData.get(ReportsUtil.TODATE);
        List<Integer> cList = (List<Integer>) requestData.get(ReportsUtil.CLINIC_ID_LIST);

        List<Integer> result = veteranAssessmentService.getTobaccoCount(cList, fromDate, toDate);

        int missing = veteranAssessmentService.getTobaccoMissingCount(cList, fromDate, toDate);

        int total = 0;
        if (result != null && result.size() > 0) {
            for (Integer i : result) {
                total += i;
            }

            total += missing;
            if (total != 0) {
                dataCollection.put("tobacco_never_percentage", String.format("%d", result.get(0) * 100 / total) + "%");
                dataCollection.put("tobacco_never_count", result.get(0) + "/" + total);
                dataCollection.put("tobacco_no_percentage", String.format("%d", result.get(1) * 100 / total) + "%");
                dataCollection.put("tobacco_no_count", result.get(1) + "/" + total);
                dataCollection.put("tobacco_yes_percentage", String.format("%d", result.get(2) * 100 / total) + "%");
                dataCollection.put("tobacco_yes_count", result.get(2) + "/" + total);
                dataCollection.put("tobacco_miss_percentage", String.format("%d", missing * 100 / total) + "%");
                dataCollection.put("tobacco_miss_count", missing + "/" + total);
            }
        }

        if (total == 0) {
            dataCollection.put("tobacco_never_percentage", "0%");
            dataCollection.put("tobacco_never_count", "0/0");
            dataCollection.put("tobacco_no_percentage", "0%");
            dataCollection.put("tobacco_no_count", "0/0");
            dataCollection.put("tobacco_yes_percentage", "0%");
            dataCollection.put("tobacco_miss_percentage", "0%");
            dataCollection.put("tobacco_yes_count", "0/0");
            dataCollection.put("tobacco_miss_count", "0/0");
        }
    }

    private void attachMilitaryBranch(Map<String, Object> dataCollection, Map<String, Object> requestData) {
        String fromDate = (String) requestData.get(ReportsUtil.FROMDATE);
        String toDate = (String) requestData.get(ReportsUtil.TODATE);
        List<Integer> cList = (List<Integer>) requestData.get(ReportsUtil.CLINIC_ID_LIST);

        List<Integer> result = veteranAssessmentService.getBranchCount(cList, fromDate, toDate);

        int missing = veteranAssessmentService.getMissingBranchCount(cList, fromDate, toDate);

        int total = 0;
        if (result != null && result.size() > 0) {
            for (Integer i : result) {
                total += i;
            }
            total += missing;
            if (total != 0) {
                dataCollection.put("army_percentage", String.format("%d", result.get(0) * 100 / total) + "%");
                dataCollection.put("army_count", result.get(0) + "/" + total);
                dataCollection.put("airforce_percentage", String.format("%d", result.get(1) * 100 / total) + "%");
                dataCollection.put("airforce_count", result.get(1) + "/" + total);
                dataCollection.put("coast_percentage", String.format("%d", result.get(2) * 100 / total) + "%");
                dataCollection.put("coast_count", result.get(2) + "/" + total);
                dataCollection.put("marines_percentage", String.format("%d", result.get(3) * 100 / total) + "%");
                dataCollection.put("marines_count", result.get(3) + "/" + total);
                dataCollection.put("nationalguard_percentage", String.format("%d", result.get(4) * 100 / total) + "%");
                dataCollection.put("nationalguard_count", result.get(4) + "/" + total);
                dataCollection.put("navy_percentage", String.format("%d", result.get(5) * 100 / total) + "%");
                dataCollection.put("navy_count", result.get(5) + "/" + total);
                dataCollection.put("missingmilitary_percentage", String.format("%d", missing * 100 / total) + "%");
                dataCollection.put("missingmilitary_count", missing + "/" + total);
            }
        }

        if (total == 0) {
            dataCollection.put("army_percentage", "0%");
            dataCollection.put("army_count", "0/0");
            dataCollection.put("airforce_percentage", "0%");
            dataCollection.put("airforce_count", "0/0");
            dataCollection.put("coast_percentage", "0%");
            dataCollection.put("coast_count", "0/" + total);
            dataCollection.put("marines_percentage", "0%");
            dataCollection.put("marines_count", "0/" + total);
            dataCollection.put("nationalguard_percentage", "0%");
            dataCollection.put("nationalguard_count", "0/" + total);
            dataCollection.put("navy_percentage", "0%");
            dataCollection.put("navy_count", "0/" + total);
            dataCollection.put("missingmilitary_percentage", "0%");
            dataCollection.put("missingmilitary_count", "0/" + total);
        }
    }

    private void attachEmploymentStatus(Map<String, Object> dataCollection, Map<String, Object> requestData) {

        String fromDate = (String) requestData.get(ReportsUtil.FROMDATE);
        String toDate = (String) requestData.get(ReportsUtil.TODATE);
        List<Integer> cList = (List<Integer>) requestData.get(ReportsUtil.CLINIC_ID_LIST);

        List<Integer> result = veteranAssessmentService.getEmploymentCount(cList, fromDate, toDate);

        int missing = veteranAssessmentService.getMissingEmploymentCount(cList, fromDate, toDate);

        int total = 0;
        if (result != null && result.size() > 0) {
            for (Integer i : result) {
                total += i;
            }

            if (total != 0) {
                dataCollection.put("fulltime_percentage", String.format("%d", result.get(0) * 100 / total) + "%");
                dataCollection.put("fulltime_count", result.get(0) + "/" + total);
                dataCollection.put("parttime_percentage", String.format("%d", result.get(1) * 100 / total) + "%");
                dataCollection.put("parttime_count", result.get(1) + "/" + total);
                dataCollection.put("seasonal_percentage", String.format("%d", result.get(2) * 100 / total) + "%");
                dataCollection.put("seasonal_count", result.get(2) + "/" + total);
                dataCollection.put("daylabor_percentage", String.format("%d", result.get(3) * 100 / total) + "%");
                dataCollection.put("daylabor_count", result.get(3) + "/" + total);
                dataCollection.put("unemployed_percentage", String.format("%d", result.get(4) * 100 / total) + "%");
                dataCollection.put("unemployed_count", result.get(4) + "/" + total);
                //TODO
                dataCollection.put("missingemp_percentage", "0%");
                dataCollection.put("missingemp_count", "0/" + total);
            }
        }

        if (total == 0) {
            dataCollection.put("fulltime_percentage", "0%");
            dataCollection.put("fulltime_count", "0/0");
            dataCollection.put("parttime_percentage", "0%");
            dataCollection.put("parttime_count", "0/0");
            dataCollection.put("seasonal_percentage", "0%");
            dataCollection.put("seasonal_count", "0/0");
            dataCollection.put("daylabor_percentage", "0%");
            dataCollection.put("daylabor_count", "0/0");
            dataCollection.put("unemployed_percentage", "0%");
            dataCollection.put("unemployed_count", "0/0");
            dataCollection.put("missingemp_percentage", "0%");
            dataCollection.put("missingemp_count", "0/0");
        }
    }

    private void attachEducation(Map<String, Object> dataCollection, Map<String, Object> requestData) {

        String fromDate = (String) requestData.get(ReportsUtil.FROMDATE);
        String toDate = (String) requestData.get(ReportsUtil.TODATE);
        List<Integer> cList = (List<Integer>) requestData.get(ReportsUtil.CLINIC_ID_LIST);

        List<Integer> result = veteranAssessmentService.getEducationCount(cList, fromDate, toDate);

        Integer missingCount = veteranAssessmentService.getMissingEducationCount(cList, fromDate, toDate);

        int total = 0;
        if (result != null && result.size() > 0) {
            for (Integer i : result) {
                total += i;
            }

            total += missingCount;

            if (total != 0) {
                dataCollection.put("highschool_percentage", String.format("%d", result.get(0) * 100 / total) + "%");
                dataCollection.put("highschool_count", result.get(0) + "/" + total);
                dataCollection.put("ged_percentage", String.format("%d", result.get(1) * 100 / total) + "%");
                dataCollection.put("ged_count", result.get(1) + "/" + total);
                dataCollection.put("highschooldip_percentage", String.format("%d", result.get(2) * 100 / total) + "%");
                dataCollection.put("highschooldip_count", result.get(2) + "/" + total);
                dataCollection.put("somecollege_percentage", String.format("%d", result.get(3) * 100 / total) + "%");
                dataCollection.put("somecollege_count", result.get(3) + "/" + total);
                dataCollection.put("associate_percentage", String.format("%d", result.get(4) * 100 / total) + "%");
                dataCollection.put("associate_count", result.get(4) + "/" + total);
                dataCollection.put("college_percentage", String.format("%d", result.get(5) * 100 / total) + "%");
                dataCollection.put("college_count", result.get(5) + "/" + total);
                dataCollection.put("master_percentage", String.format("%d", result.get(6) * 100 / total) + "%");
                dataCollection.put("master_count", result.get(6) + "/" + total);
                dataCollection.put("dr_percentage", String.format("%d", result.get(7) * 100 / total) + "%");
                dataCollection.put("dr_count", result.get(7) + "/" + total);
                dataCollection.put("missingedu_percentage", String.format("%d", missingCount * 100 / total) + "%");
                dataCollection.put("missingedu_count", missingCount + "/" + total);
            }
        }

        if (total == 0) {

            dataCollection.put("highschool_percentage", "0%");
            dataCollection.put("highschool_count", "0/0");
            dataCollection.put("ged_percentage", "0%");
            dataCollection.put("ged_count", "0/0");
            dataCollection.put("highschooldip_percentage", "0%");
            dataCollection.put("highschooldip_count", "0/0");
            dataCollection.put("ged_percentage", "0%");
            dataCollection.put("ged_count", "0/0");
            dataCollection.put("somecollege_percentage", "0%");
            dataCollection.put("somecollege_count", "0/0");
            dataCollection.put("associate_percentage", "0%");
            dataCollection.put("associate_count", "0/0");
            dataCollection.put("college_percentage", "0%");
            dataCollection.put("college_count", "0/0");
            dataCollection.put("master_percentage", "0%");
            dataCollection.put("master_count", "0/0");
            dataCollection.put("dr_percentage", "0%");
            dataCollection.put("dr_count", "0/0");
            dataCollection.put("missingedu_percentage", "0%");
            dataCollection.put("missingedu_count", "0/0");
        }
    }

    private void attachAge(Map<String, Object> dataCollection, Map<String, Object> requestData) {
        String fromDate = (String) requestData.get(ReportsUtil.FROMDATE);
        String toDate = (String) requestData.get(ReportsUtil.TODATE);
        List<Integer> cList = (List<Integer>) requestData.get(ReportsUtil.CLINIC_ID_LIST);

        List<Number> result = veteranAssessmentService.getAgeStatistics(cList, fromDate, toDate);

        if (result == null || result.isEmpty() || result.size() != 3) {
            dataCollection.put("age", "");
        } else {

            dataCollection.put("age",
                    String.format("Mean Age %3.1f years Minimum Value = %d and Maximum value = %d",
                            result.get(0).floatValue(), result.get(1).intValue(), result.get(2).intValue()));
        }
    }

    private void attachEthnicity(Map<String, Object> dataCollection, Map<String, Object> requestData) {

        String fromDate = (String) requestData.get(ReportsUtil.FROMDATE);
        String toDate = (String) requestData.get(ReportsUtil.TODATE);
        List cList = (List) requestData.get(ReportsUtil.CLINIC_ID_LIST);

        List<Integer> result = veteranAssessmentService.getEthnicityCount(cList, fromDate, toDate);

        int total = 0;
        if (result != null && result.size() > 0) {
            total = result.get(0) + result.get(1) + result.get(2);

            if (total != 0) {
                dataCollection.put("hispanic_percentage", String.format("%d", result.get(0) * 100 / total) + "%");
                dataCollection.put("hispanic_count", result.get(0) + "/" + total);
                dataCollection.put("non_hispanic_percentage", String.format("%d", result.get(1) * 100 / total) + "%");
                dataCollection.put("non_hispanic_count", result.get(1) + "/" + total);
                dataCollection.put("missingethnicity_percentage", String.format("%d", result.get(2) * 100 / total) + "%");
                dataCollection.put("missingethnicity_count", result.get(2) + "/" + total);
            }
        }
        if (total == 0) {
            dataCollection.put("hispanic_percentage", "0%");
            dataCollection.put("hispanic_count", "0/0");
            dataCollection.put("non_hispanic_percentage", "0%");
            dataCollection.put("non_hispanic_count", "0/0");
            dataCollection.put("missingethnicity_percentage", "0%");
            dataCollection.put("missingethnicity_count", "0/0");
        }

        total = 0;
        result = veteranAssessmentService.getRaceCount(cList, fromDate, toDate);

        if (result != null && result.size() > 0) {
            for (Integer i : result) {
                total += i;
            }

            if (total != 0) {
                dataCollection.put("white_percentage", String.format("%d", result.get(0) * 100 / total) + "%");
                dataCollection.put("white_count", result.get(0) + "/" + total);
                dataCollection.put("black_percentage", String.format("%d", result.get(1) * 100 / total) + "%");
                dataCollection.put("black_count", result.get(1) + "/" + total);
                dataCollection.put("indian_percentage", String.format("%d", result.get(2) * 100 / total) + "%");
                dataCollection.put("indian_count", result.get(2) + "/" + total);
                dataCollection.put("asian_percentage", String.format("%d", result.get(3) * 100 / total) + "%");
                dataCollection.put("asian_count", result.get(3) + "/" + total);
                dataCollection.put("hawaiian_percentage", String.format("%d", result.get(4) * 100 / total) + "%");
                dataCollection.put("hawaiian_count", result.get(4) + "/" + total);
                dataCollection.put("otherrace_percentage", String.format("%d", result.get(5) * 100 / total) + "%");
                dataCollection.put("otherrace_count", result.get(5) + "/" + total);
                dataCollection.put("norace_percentage", String.format("%d", result.get(6) * 100 / total) + "%");
                dataCollection.put("norace_count", result.get(6) + "/" + total);
            }
        }

        if (total == 0) {

            dataCollection.put("white_percentage", "0%");
            dataCollection.put("white_count", "0/0");
            dataCollection.put("black_percentage", "0%");
            dataCollection.put("black_count", "0/0");
            dataCollection.put("indian_percentage", "0%");
            dataCollection.put("indian_count", "0/0");
            dataCollection.put("asian_percentage", "0%");
            dataCollection.put("asian_count", "0/0");
            dataCollection.put("hawaiian_percentage", "0%");
            dataCollection.put("hawaiian_count", "0/0");
            dataCollection.put("otherrace_percentage", "0%");
            dataCollection.put("otherrace_count", "0/0");
            dataCollection.put("norace_percentage", "0%");
            dataCollection.put("norace_count", "0/0");
        }
    }

    private void attachGender(Map<String, Object> dataCollection, Map<String, Object> requestData) {

        String fromDate = (String) requestData.get(ReportsUtil.FROMDATE);
        String toDate = (String) requestData.get(ReportsUtil.TODATE);
        List cList = (List) requestData.get(ReportsUtil.CLINIC_ID_LIST);

        List<Integer> result = veteranAssessmentService.getGenderCount(cList, fromDate, toDate);

        if (result != null) {

            int female = result.get(1);
            int male = result.get(0);
            int total = female + male;


            dataCollection.put("female_percentage", String.format("%d", female * 100 / total) + "%");
            dataCollection.put("female_count", female + "/" + total);
            dataCollection.put("male_percentage", String.format("%d", male * 100 / total) + "%");
            dataCollection.put("male_count", male + "/" + total);
        } else {
            dataCollection.put("female_percentage", "0%");
            dataCollection.put("female_count", "0/0");
            dataCollection.put("male_percentage", "0%");
            dataCollection.put("male_count", "0/0");
        }

    }

    private Map<String, Object> createChartableDataForIndividualStats(Integer surveyId, Integer veteranId, String fromDate, String toDate) {

        Map<String, Object> chartableDataMap = Maps.newHashMap();

        final Map<String, Object> surveyDataForIndividualStatisticsGraph = scoreService.getSurveyDataForIndividualStatisticsGraph(surveyId, veteranId, fromDate, toDate);

        final Map<String, Object> metaData = intervalService.generateMetadata(surveyId);
        if (metaData != null) {
            metaData.put("score", !surveyDataForIndividualStatisticsGraph.isEmpty() ? getAvgFromData(surveyDataForIndividualStatisticsGraph) : 0);
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
            metaData.put("score", !surveyDataForIndividualStatisticsGraph.isEmpty() ? getAvgFromData(surveyDataForIndividualStatisticsGraph) : 0);
        }
        chartableDataMap.put("dataSet", surveyDataForIndividualStatisticsGraph);
        chartableDataMap.put("dataFormat", metaData);

        return chartableDataMap;
    }

    private Float getAvgFromData(Map<String, Object> surveyDataForIndividualStatisticsGraph) {
        float avg = 0.0f;
        if (surveyDataForIndividualStatisticsGraph == null || surveyDataForIndividualStatisticsGraph.isEmpty()) {
            return avg;
        }
        for (Object d : surveyDataForIndividualStatisticsGraph.values()) {
            avg += Float.valueOf(d.toString());
        }
        return avg / surveyDataForIndividualStatisticsGraph.size();
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
