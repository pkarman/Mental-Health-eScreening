package gov.va.escreening.service;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import gov.va.escreening.dto.report.*;
import gov.va.escreening.entity.*;
import gov.va.escreening.repository.ClinicRepository;
import gov.va.escreening.repository.SurveyRepository;
import gov.va.escreening.repository.VeteranAssessmentSurveyScoreRepository;
import gov.va.escreening.variableresolver.AssessmentVariableDto;
import gov.va.escreening.variableresolver.VariableResolverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Resource(name = "selectedReportableScreensMap")
    Map<String, String> selectedReportableScreensMap;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private DecimalFormat df = new DecimalFormat("###.##");
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    @Override
    @Transactional
    public void recordAllReportableScores(VeteranAssessment veteranAssessment) {
        final List<VeteranAssessmentSurveyScore> selectedReportableScores = processSelectedReportableScores(veteranAssessment, selectedReportableScoresMap);
        final List<VeteranAssessmentSurveyScore> selectedReportableScreens = processSelectedReportableScreens(veteranAssessment, selectedReportableScreensMap);

        final List<VeteranAssessmentSurveyScore> vassLst = Lists.newArrayList();
        vassLst.addAll(selectedReportableScores);
        vassLst.addAll(selectedReportableScreens);

        for (VeteranAssessmentSurveyScore vass : vassLst) {
            vassRepos.update(vass);
        }
    }

    private List<VeteranAssessmentSurveyScore> processSelectedReportableScreens(VeteranAssessment veteranAssessment, Map<String, String> map) {
        List<VeteranAssessmentSurveyScore> vassLst = Lists.newArrayList();
        for (Survey s : veteranAssessment.getSurveys()) {
            final String screenAvPlusThreshold = map.get(s.getName());
            if (screenAvPlusThreshold == null) {
                continue;
            }
            String[] screenAvPlusThresholdAry = screenAvPlusThreshold.split("[$]");

            Collection<AssessmentVariable> reportableAvs = avSrv.findByDisplayNames(Arrays.asList(screenAvPlusThresholdAry[0]));
            // use assessment variables and veteran Assessment Id
            final Iterable<AssessmentVariableDto> reportableAvDtos = vrSrv.resolveVariablesFor(veteranAssessment.getVeteranAssessmentId(), reportableAvs);
            for (AssessmentVariableDto avDto : reportableAvDtos) {
                VeteranAssessmentSurveyScore vass = tryCreateVASS(s, avDto, veteranAssessment);
                if (vass != null) {
                    // apply the score screen positive/negative, or missing rule
                    decideScoreScreen(vass, Integer.valueOf(screenAvPlusThresholdAry[1]));
                    vassLst.add(vass);
                }
            }
        }
        return vassLst;
    }

    private void decideScoreScreen(VeteranAssessmentSurveyScore vass, Integer scoreThreshold) {
        Integer vassScore = vass.getScore();
        if (vassScore == null) {
            vass.setScreenNumber(999);
        } else if (vassScore.compareTo(scoreThreshold) >= 0) {
            vass.setScreenNumber(1);// if score is equal or more than the threshold than +ve screen is POSITIVEßß
        } else {
            vass.setScreenNumber(0);  // if score is less than the threshold than +ve screen is NEGATIVE
        }
    }

    private List<VeteranAssessmentSurveyScore> processSelectedReportableScores(VeteranAssessment veteranAssessment, Map<String, String> map) {
        List<VeteranAssessmentSurveyScore> vassLst = Lists.newArrayList();
        for (Survey s : veteranAssessment.getSurveys()) {
            // find reportable Assessment Variables for each Survey in this Veteran Assessment. Most of these Assessment Variables will be Formulas,
            // and also most of the Formulas would be Aggregate Formulas
            final Collection<AssessmentVariable> reportableAvs = getReportableAvsForSurvey(s, map);

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
    public Map<String, Object> getSurveyDataForIndividualStatisticsGraph(Integer surveyId, Integer veteranId, String fromDate, String toDate) {
        List<VeteranAssessmentSurveyScore> scores = vassRepos.getDataForIndividual(surveyId, veteranId, fromDate, toDate);

        Map<String, Object> data = Maps.newLinkedHashMap();
        if (scores != null && !scores.isEmpty()) {
            for (VeteranAssessmentSurveyScore score : scores) {
                data.put(dateFormatter.format(score.getDateCompleted()), score.getScore());
            }
        }

        return data;
    }

    @Override
    public Map<String, Object> getSurveyDataForIndividualStatisticsGraph(Integer clinicId, Integer surveyId, Integer veteranId, String fromDate, String toDate) {
        List<VeteranAssessmentSurveyScore> scores = vassRepos.getDataForIndividual(clinicId, surveyId, veteranId, fromDate, toDate);

        Map<String, Object> data = Maps.newLinkedHashMap();
        if (scores != null && !scores.isEmpty()) {
            for (VeteranAssessmentSurveyScore score : scores) {
                data.put(dateFormatter.format(score.getDateCompleted()), score.getScore());
            }
        }
        return data;

    }

    @Override
    public TableReportDTO getSurveyDataForIndividualStatisticsReport(Integer surveyId, Integer veteranId, String fromDate, String toDate) {

        Survey survey = surveyRepository.findOne(surveyId);

        TableReportDTO result = new TableReportDTO();

        result.setModuleName(survey.getName());
        result.setScreeningModuleName(survey.getDescription());

        List<VeteranAssessmentSurveyScore> scores = vassRepos.getDataForIndividual(surveyId, veteranId, fromDate, toDate);


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
    public ModuleGraphReportDTO getGraphReportDTOForIndividual(Integer surveyId, Integer veteranId, String fromDate, String toDate) {

        Survey survey = surveyRepository.findOne(surveyId);

        ModuleGraphReportDTO result = new ModuleGraphReportDTO();
        result.setModuleName(survey.getName());
        //result.setScreeningModuleName(survey.getDescription());

        List<VeteranAssessmentSurveyScore> scores = vassRepos.getDataForIndividual(surveyId, veteranId, fromDate, toDate);

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
    public Map<String, Object> getSurveyDataForClinicStatisticsGraph(Integer clinicId, Integer surveyId, String fromDate, String toDate) {
        List<ScoreDateDTO> scores = vassRepos.getDataForClicnic(clinicId, surveyId, fromDate, toDate);

        Map<String, Object> data = Maps.newLinkedHashMap();
        if (scores != null && !scores.isEmpty()) {
            for (ScoreDateDTO score : scores) {
                data.put(dateFormatter.format(score.getDateCompleted()), score.getScore());
            }
        }
        return data;
    }

    @Override
    public ModuleGraphReportDTO getSurveyDataForVetClinicReport(Integer clinicId, Integer surveyIds, String fromDate, String toDate) {
        return null;
    }

    @Override
    public ModuleGraphReportDTO getGraphDataForClinicStatisticsGraph(Integer clinicId, Integer surveyId, String fromDate, String toDate, boolean containsCount) {
        List<ScoreDateDTO> scores = vassRepos.getDataForClicnic(clinicId, surveyId, fromDate, toDate);

        ModuleGraphReportDTO result = new ModuleGraphReportDTO();

        Survey survey = surveyRepository.findOne(surveyId);
        Clinic clinic = clinicRepository.findOne(clinicId);

        result.setModuleName(survey.getName());
        result.setHasData(!scores.isEmpty());
        result.setScoreName("Average " + survey.getName() + "Score");

        if (result.getHasData()) {
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

            result.setVeteranCount(" Number of Veterans, N=" + vassRepos.getVeteranCountForClinic(clinicId, surveyId, fromDate, toDate));
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
    public ModuleGraphReportDTO getSurveyDataForVetClinicReport(Integer clinicId, Integer surveyId, Integer veteranId, String fromDate, String toDate) {

        ModuleGraphReportDTO result = new ModuleGraphReportDTO();

        Survey s = surveyRepository.findOne(surveyId);

        result.setModuleName(s.getName());
        result.setScoreName("Average Score");


        List<VeteranAssessmentSurveyScore> scores = vassRepos.getDataForIndividual(clinicId, surveyId, veteranId, fromDate, toDate);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

        if (scores != null && !scores.isEmpty()) {
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
    public List<Report599DTO> getClinicStatisticReportsPartVIPositiveScreensReport(String fromDate, String toDate, List<Integer> clinicIds, List<String> surveyNameList) {
        return vassRepos.getClinicStatisticReportsPartVIPositiveScreensReport(fromDate, toDate, clinicIds, surveyNameList);
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


    private Collection<AssessmentVariable> getReportableAvsForSurvey(Survey s, Map<String, String> map) {
        List<String> avDisplayNames = getDisplayNamesForSurvey(s, map);
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
        if (scoreAsStr != null && !scoreAsStr.trim().isEmpty()) {
            VeteranAssessmentSurveyScore vass = new VeteranAssessmentSurveyScore();
            vass.setClinic(veteranAssessment.getClinic());
            vass.setDateCompleted(new Date());

            //get the string as an integer, round it to get best mathematical number
            int roundedScore = (int) Math.round(Double.parseDouble(scoreAsStr));
            vass.setScore(roundedScore);

            vass.setSurvey(s);
            vass.setVeteran(veteranAssessment.getVeteran());
            vass.setVeteranAssessment(veteranAssessment);
            return vass;
        }
        return null;
    }
}
