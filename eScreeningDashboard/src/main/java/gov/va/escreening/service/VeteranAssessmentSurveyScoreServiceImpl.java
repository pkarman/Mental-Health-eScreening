package gov.va.escreening.service;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gov.va.escreening.delegate.ReportFunctionCommon;
import gov.va.escreening.dto.report.*;
import gov.va.escreening.entity.*;
import gov.va.escreening.repository.*;
import gov.va.escreening.variableresolver.AssessmentVariableDto;
import gov.va.escreening.variableresolver.VariableResolverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by krizvi.ctr on 3/6/15.
 */
@Service("veteranAssessmentSurveyScoreService")
public class VeteranAssessmentSurveyScoreServiceImpl implements VeteranAssessmentSurveyScoreService {
    @Resource(type = VariableResolverService.class)
    VariableResolverService vrSrv;

    @Resource(type = AssessmentVariableService.class)
    AssessmentVariableService avSrv;

    @Resource(type = VeteranAssessmentSurveyScoreRepository.class)
    VeteranAssessmentSurveyScoreRepository vassRepos;

    @Resource(type = ReportFunctionCommon.class)
    private ReportFunctionCommon reportsHelper;

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private SurveyScoreIntervalService intervalService;

    // map between surveyName and formulas that belong to that survey
    // this is defined in WEB-INF/spring/business-config.xml:107
    @Resource(name = "selectedReportableScoresMap")
    Map<String, String> selectedReportableScoresMap;
    Map<String, List<Map>> reportableModulesMap;

    @Resource(type = MeasureRepository.class)
    MeasureRepository measureRepository;

    @Resource(type = MeasureAnswerRepository.class)
    MeasureAnswerRepository measureAnswerRepository;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private DecimalFormat df = new DecimalFormat("###.##");
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    @Resource(name = "opFunctionMap")
    Map<String, OpFunction> opFunctionMap;


    @Resource(name = "selectedReportableScreensMap")
    Map<String, String> selectedReportableScreensMap;
    Map<String, List<Map>> posNegScreenScoreRulesMap;

    /**
     * build posNegScreenScoreRulesMap after dependency injection is done to perform any initialization
     */
    @PostConstruct
    private void constructPosNegScreenScoreRulesMap() {
        loadPosNegScreenScoresRulesMap();
        loadReportableModulesMap();
    }

    private void loadReportableModulesMap() {
        Gson gson = new GsonBuilder().create();
        this.reportableModulesMap = Maps.newHashMap();
        for (String moduleName : selectedReportableScoresMap.keySet()) {
            String avNames = selectedReportableScoresMap.get(moduleName);
            List<Map> avMaps = gson.fromJson(avNames, List.class);
            this.reportableModulesMap.put(moduleName, avMaps);
        }
    }

    private void loadPosNegScreenScoresRulesMap() {
        Gson gson = new GsonBuilder().create();
        this.posNegScreenScoreRulesMap = Maps.newHashMap();
        for (String moduleName : selectedReportableScreensMap.keySet()) {
            String modulePosNegJson = selectedReportableScreensMap.get(moduleName);
            List<Map> modulePosNegMap = gson.fromJson(modulePosNegJson, List.class);
            this.posNegScreenScoreRulesMap.put(moduleName, modulePosNegMap);
        }
    }

    @Override
    @Transactional
    public void recordAllReportableScores(VeteranAssessment veteranAssessment) {
        final List<VeteranAssessmentSurveyScore> selectedReportableScores = processSelectedReportableScores(veteranAssessment);
        final List<VeteranAssessmentSurveyScore> selectedReportableScreens = processSelectedReportableScreens(veteranAssessment);

        final List<VeteranAssessmentSurveyScore> vassLst = Lists.newArrayList();
        vassLst.addAll(selectedReportableScores);
        vassLst.addAll(selectedReportableScreens);

        for (VeteranAssessmentSurveyScore vass : vassLst) {
            vassRepos.update(vass);
        }
    }

    @Override
    public List<VeteranAssessmentSurveyScore> processSelectedReportableScreens(VeteranAssessment veteranAssessment) {
        List<VeteranAssessmentSurveyScore> vassLst = Lists.newArrayList();
        for (Survey s : veteranAssessment.getSurveys()) {
            final List<Map> posNegScreenModuleRules = this.posNegScreenScoreRulesMap.get(s.getName());
            if (posNegScreenModuleRules == null) {
                continue;
            }

            for (Map posNegScreenModuleRule : posNegScreenModuleRules) {

                String avName = posNegScreenModuleRule.get("var").toString();

                Collection<AssessmentVariable> reportableAvs = avSrv.findByDisplayNames(Arrays.asList(avName));
                // use assessment variables and veteran Assessment Id
                final Iterable<AssessmentVariableDto> reportableAvDtos = vrSrv.resolveVariablesFor(veteranAssessment.getVeteranAssessmentId(), reportableAvs);
                int vassLstSize = vassLst.size();
                for (AssessmentVariableDto avDto : reportableAvDtos) {
                    VeteranAssessmentSurveyScore vass = tryCreateVASS(s, avDto, veteranAssessment);
                    if (vass != null) {
                        decideScoreScreen(vass, (List<Map>) posNegScreenModuleRule.get("pos"), (List<Map>) posNegScreenModuleRule.get("neg"));
                        vassLst.add(vass);
                    }
                }
                // look for missing responses
                if (vassLst.size() == vassLstSize) {
                    vassLst.add(createVASS(veteranAssessment, -1, s, avName, 999));
                }
            }
        }
        return vassLst;
    }

    private void decideScoreScreen(VeteranAssessmentSurveyScore vass, List<Map> pos, List<Map> neg) {
        Integer vassScore = vass.getScore();
        if (vassScore == null) {
            vass.setScreenNumber(999);
        } else if (resolveExpression(vass, pos)) {
            vass.setScreenNumber(1);// if score is equal or more than the threshold than +ve screen is POSITIVEßß
        } else if (resolveExpression(vass, neg)) {
            vass.setScreenNumber(0);  // if score is less than the threshold than +ve screen is NEGATIVE
        }
    }

    private boolean resolveExpression(VeteranAssessmentSurveyScore vass, List<Map> exprRules) {
        boolean res = false;
        for (Map exprRule : exprRules) {
            String op = exprRule.get("op").toString();
            Double val = (Double) exprRule.get("val");
            OpFunction opFunc = this.opFunctionMap.get(op);
            res = res || opFunc.apply(vass.getScore(), val.intValue());
        }

        return res;
    }

    private List<VeteranAssessmentSurveyScore> processSelectedReportableScores(VeteranAssessment veteranAssessment) {
        List<VeteranAssessmentSurveyScore> vassLst = Lists.newArrayList();
        for (Survey s : veteranAssessment.getSurveys()) {
            // find reportable Assessment Variables for each Survey in this Veteran Assessment. Most of these Assessment Variables will be Formulas,
            // and also most of the Formulas would be Aggregate Formulas
            final Collection<AssessmentVariable> reportableAvs = getReportableAvsForSurvey(s, this.reportableModulesMap.get(s.getName()));

            // in case a survey does not have any Assessment Variable as reportable
            if (reportableAvs != null) {
                // use assessment variables and veteran Assessment Id
                final Iterable<AssessmentVariableDto> reportableAvDtos = vrSrv.resolveVariablesFor(veteranAssessment.getVeteranAssessmentId(), reportableAvs);
                for (AssessmentVariableDto avDto : reportableAvDtos) {
                    VeteranAssessmentSurveyScore vass = tryCreateVASS(s, avDto, veteranAssessment);
                    if (vass != null) {
                        vassLst.add(vass);
                    }
                }
            }
        }
        return vassLst;
    }

    @Override
    public Map<String, Object> getSurveyDataForIndividualStatisticsGraph(Integer surveyId, String avName, Integer veteranId, String fromDate, String toDate) {
        List<VeteranAssessmentSurveyScore> scores = vassRepos.getDataForIndividual(surveyId, avName, veteranId, fromDate, toDate);

        Map<String, Object> data = Maps.newLinkedHashMap();
        if (scores != null && !scores.isEmpty()) {
            for (VeteranAssessmentSurveyScore score : scores) {
                data.put(dateFormatter.format(score.getDateCompleted()), score.getScore());
            }
        }

        return data;
    }

    @Override
    public Map<String, Object> getSurveyDataForIndividualStatisticsGraph(Integer clinicId, Integer surveyId, String avName, Integer veteranId, String fromDate, String toDate) {
        List<VeteranAssessmentSurveyScore> scores = vassRepos.getDataForIndividual(clinicId, surveyId, avName, veteranId, fromDate, toDate);

        Map<String, Object> data = Maps.newLinkedHashMap();
        if (scores != null && !scores.isEmpty()) {
            for (VeteranAssessmentSurveyScore score : scores) {
                data.put(dateFormatter.format(score.getDateCompleted()), score.getScore());
            }
        }
        return data;

    }

    @Override
    public TableReportDTO getSurveyDataForIndividualStatisticsReport(Integer surveyId, String avName, Integer veteranId, String fromDate, String toDate) {

        Survey survey = surveyRepository.findOne(surveyId);

        TableReportDTO result = new TableReportDTO();
        result.setModuleName(avName == null ? reportsHelper.getModuleName(surveyId) : reportsHelper.getModuleName(surveyId, avName));
        result.setScreeningModuleName(survey.getDescription());

        List<VeteranAssessmentSurveyScore> scores = vassRepos.getDataForIndividual(surveyId, avName, veteranId, fromDate, toDate);


        if (scores != null && !scores.isEmpty()) {
            for (VeteranAssessmentSurveyScore score : scores) {
                if (result.getScore() == null) {
                    result.setScore(score.getScore() + " - " + intervalService.getScoreMeaning(surveyId, score.getScore()));
                } else {
                    result.setScore(result.getScore() + "\n" + score.getScore() + " - " + intervalService.getScoreMeaning(surveyId, score.getScore()));
                }

                if (result.getHistoryByClinic() == null) {
                    result.setHistoryByClinic(simpleDateFormat.format(score.getDateCompleted()) + " | " + score.getClinic().getName());
                } else {
                    result.setHistoryByClinic(result.getHistoryByClinic() + "\n" + simpleDateFormat.format(score.getDateCompleted()) + " | " + score.getClinic().getName());
                }

            }

            return result;
        }

        return null;
    }

    @Override
    public ModuleGraphReportDTO getGraphReportDTOForIndividual(Integer surveyId, String avName, Integer veteranId, String fromDate, String toDate) {

        Survey survey = surveyRepository.findOne(surveyId);

        ModuleGraphReportDTO result = new ModuleGraphReportDTO();
        result.setModuleName(reportsHelper.getModuleName(surveyId, avName));


        List<VeteranAssessmentSurveyScore> scores = vassRepos.getDataForIndividual(surveyId, avName, veteranId, fromDate, toDate);

        if (scores != null && !scores.isEmpty()) {
            result.setScore(Integer.toString(scores.get(0).getScore()));
            result.setScoreMeaning(intervalService.getScoreMeaning(surveyId, scores.get(0).getScore()));
            result.setScoreName("Last Score");

            List<ScoreHistoryDTO> history = new ArrayList<>();
            for (VeteranAssessmentSurveyScore score : scores) {
                ScoreHistoryDTO h = new ScoreHistoryDTO();
                history.add(h);
                h.setClinicName(score.getClinic().getName());
                h.setSecondLine(score.getScore() + " - " + intervalService.getScoreMeaning(surveyId, score.getScore()));
                h.setScreeningDate(simpleDateFormat.format(score.getDateCompleted()));
            }
            result.setScoreHistory(history);
        } else {
            result.setHasData(false);
        }

        return result;
    }

    @Override
    public Map<String, Object> getSurveyDataForClinicStatisticsGraph(Integer clinicId, Integer surveyId, String avName, String fromDate, String toDate) {
        List<ScoreDateDTO> scores = vassRepos.getDataForClicnic(clinicId, surveyId, avName, fromDate, toDate);

        Map<String, Object> data = Maps.newLinkedHashMap();
        if (scores != null && !scores.isEmpty()) {
            for (ScoreDateDTO score : scores) {
                data.put(dateFormatter.format(score.getDateCompleted()), df.format(score.getScore()));
            }
        }
        return data;
    }

    @Override
    public ModuleGraphReportDTO getGraphDataForClinicStatisticsGraph(Integer clinicId, Integer surveyId, String avName, String fromDate, String toDate, boolean containsCount) {
        List<ScoreDateDTO> scores = vassRepos.getDataForClicnic(clinicId, surveyId, avName, fromDate, toDate);

        ModuleGraphReportDTO result = new ModuleGraphReportDTO();

        Survey survey = surveyRepository.findOne(surveyId);
        Clinic clinic = clinicRepository.findOne(clinicId);

        result.setModuleName(survey.getName());
        result.setScoreName("Average " + survey.getName() + "Score");

        if (scores != null && !scores.isEmpty()) {
            double total = 0d;
            int totalCount = 0;
            result.setScoreHistory(new ArrayList<ScoreHistoryDTO>());
            for (ScoreDateDTO score : scores) {
                total += score.getCount() * score.getScore();
                totalCount += score.getCount();


                // format history
                ScoreHistoryDTO h = new ScoreHistoryDTO();
                result.getScoreHistory().add(h);
                h.setClinicName(clinic.getName());
                h.setSecondLine(df.format(score.getScore()) + " - " + intervalService.getScoreMeaning(surveyId, score.getScore()));
                if (containsCount) {
                    h.setSecondLine(h.getSecondLine() + ", N=" + score.getCount());
                }

                h.setScreeningDate(simpleDateFormat.format(score.getDateCompleted()));
            }
            result.setScore(df.format(total / totalCount));
            result.setScoreMeaning(intervalService.getScoreMeaning(surveyId, ((int) (total / totalCount))));

            result.setScoreHistoryTitle("Average Score History by VistA Clinic");

            result.setVeteranCount(" Number of Veterans, N=" + vassRepos.getVeteranCountForClinic(clinicId, surveyId, avName, fromDate, toDate));
            return result;
        }

        return null;
    }

    /**
     * for veteran clinc graphs
     *
     * @param clinicId
     * @param surveyId
     * @param veteranId
     * @param fromDate
     * @param toDate
     * @return
     */
    @Override
    public ModuleGraphReportDTO getSurveyDataForVetClinicReport(Integer clinicId, Integer surveyId, String avName, Integer veteranId, String fromDate, String toDate) {

        ModuleGraphReportDTO result = new ModuleGraphReportDTO();

        Survey s = surveyRepository.findOne(surveyId);
        result.setModuleName(reportsHelper.getModuleName(surveyId, avName));
        result.setScoreName("Average Score");


        List<VeteranAssessmentSurveyScore> scores = vassRepos.getDataForIndividual(clinicId, surveyId, avName, veteranId, fromDate, toDate);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

        if (scores != null && !scores.isEmpty()) {
            result.setHasData(true);
            result.setScore(getAvgOfScores(scores).toString());
            result.setScoreMeaning(intervalService.getScoreMeaning(surveyId, scores.get(0).getScore()));

            List<ScoreHistoryDTO> history = new ArrayList<>();
            for (VeteranAssessmentSurveyScore score : scores) {
                ScoreHistoryDTO h = new ScoreHistoryDTO();
                history.add(h);
                h.setClinicName(score.getClinic().getName());
                h.setSecondLine(score.getScore() + " - " + intervalService.getScoreMeaning(surveyId, score.getScore()));
                h.setScreeningDate(simpleDateFormat.format(score.getDateCompleted()));
            }
            result.setScoreHistory(history);
        } else {
            result.setHasData(false);
        }

        return result;
    }

    @Override
    public List<Report599DTO> getClinicStatisticReportsPartVIPositiveScreensReport(String fromDate, String toDate, List<Integer> clinicIds) {
        return vassRepos.getClinicStatisticReportsPartVIPositiveScreensReport(fromDate, toDate, clinicIds);
    }

    private Float getAvgOfScores(List<VeteranAssessmentSurveyScore> scores) {
        float avg = 0.0f;
        if (scores == null || scores.isEmpty()) {
            return avg;
        }
        for (VeteranAssessmentSurveyScore vass : scores) {
            avg += Float.valueOf(vass.getScore());
        }
        return avg / scores.size();
    }


    private Collection<AssessmentVariable> getReportableAvsForSurvey(Survey s, List<Map> avMap) {
        List<String> avDisplayNames = reportsHelper.getAllAvsFromModule(s.getName());
        if (avDisplayNames == null) {
            return null;
        }
        final Collection<AssessmentVariable> byDisplayNames = avSrv.findByDisplayNames(avDisplayNames);
        return byDisplayNames;
    }

    private List<String> getDisplayNamesForSurvey(Survey s, Map<String, String> map) {
        String avDisplayNames = map.get(s.getName());
        if (avDisplayNames == null) {
            return null;
        }
        final List<String> avDisplayNamesAsList = Lists.newArrayList(Splitter.on(',').omitEmptyStrings().trimResults().split(avDisplayNames));
        return avDisplayNamesAsList;
    }

    private VeteranAssessmentSurveyScore tryCreateVASS(Survey s, AssessmentVariableDto avDto, VeteranAssessment veteranAssessment) {
        String scoreAsStr = avDto.getDisplayText();
        if (!"false".equals(avDto.getValue()) && scoreAsStr != null && !scoreAsStr.trim().isEmpty()) {
            VeteranAssessmentSurveyScore vass = createVASS(veteranAssessment, tryCalcScore(scoreAsStr, avDto), s, avDto.getDisplayName(), null);
            return vass;
        }
        return null;
    }

    private VeteranAssessmentSurveyScore createVASS(VeteranAssessment veteranAssessment, Integer score, Survey survey, String avName, Integer screenNumber) {
        VeteranAssessmentSurveyScore vass = new VeteranAssessmentSurveyScore();
        vass.setClinic(veteranAssessment.getClinic());
        vass.setDateCompleted(new Date());

        vass.setScore(score);

        vass.setSurvey(survey);
        vass.setVeteran(veteranAssessment.getVeteran());
        vass.setVeteranAssessment(veteranAssessment);
        vass.setAvName(avName);
        vass.setScreenNumber(screenNumber);
        return vass;
    }

    private int tryCalcScore(String scoreAsStr, AssessmentVariableDto avDto) {
        try {
            return (int) Math.round(Double.parseDouble(scoreAsStr));
        } catch (NumberFormatException nfe) {
            Integer maId = avDto.getAnswerId();
            if (maId != null) {
                MeasureAnswer ma = measureAnswerRepository.findOne(maId);
                String measureCalcValue = ma.getCalculationValue();
                return (measureCalcValue == null) ? 0 : Integer.parseInt(ma.getCalculationValue());
            } else {
                return 0;
            }
        }
    }
}
