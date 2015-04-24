package gov.va.escreening.delegate;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.service.AssessmentVariableService;
import gov.va.escreening.service.SurveyScoreIntervalService;
import gov.va.escreening.service.SurveyService;
import gov.va.escreening.service.VeteranAssessmentSurveyScoreService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by munnoo on 4/23/15.
 */
@Component("reportsHelper")
public class ReportFunctionCommon {
    @Resource(name = "selectedReportableScoresMap")
    private Map<String, String> selectedReportableScoresMap;
    private Map<String, List<Map>> avMap;

    private Map<Integer, Survey> surveyCache;

    @Resource(type = SurveyService.class)
    SurveyService ss;

    @Resource(type = SurveyScoreIntervalService.class)
    SurveyScoreIntervalService intervalService;


    @Resource(type = VeteranAssessmentSurveyScoreService.class)
    protected VeteranAssessmentSurveyScoreService scoreService;

    @PostConstruct
    private void constructPosNegScreenScoreRulesMap() {
        Gson gson = new GsonBuilder().create();
        this.avMap = Maps.newHashMap();
        for (String moduleName : selectedReportableScoresMap.keySet()) {
            String modulePosNegJson = selectedReportableScoresMap.get(moduleName);
            List<Map> modulePosNegMap = gson.fromJson(modulePosNegJson, List.class);
            this.avMap.put(moduleName, modulePosNegMap);
        }

        this.surveyCache = Maps.newHashMap();
    }

    protected Map<String, List<Map>> getAvMap() {
        return avMap;
    }


    protected boolean isSplittableModule(Integer surveyId) {
        List<Map> avMaps = findAvMapsForModule(surveyId);
        for (Map avMap : avMaps) {
            Boolean splittable = (Boolean) avMap.get("split");
            if (splittable != null && splittable) {
                return true;
            }
        }
        return false;
    }

    private List<Map> findAvMapsForModule(Integer surveyId) {
        Survey s = getSurveyById(surveyId);
        return this.avMap.get(s.getName());
    }

    protected Float getAvgFromData(Map<String, Object> surveyDataForIndividualStatisticsGraph) {
        float avg = 0.0f;
        if (surveyDataForIndividualStatisticsGraph == null || surveyDataForIndividualStatisticsGraph.isEmpty()) {
            return avg;
        }
        for (Object d : surveyDataForIndividualStatisticsGraph.values()) {
            avg += Float.valueOf(d.toString());
        }
        return avg / surveyDataForIndividualStatisticsGraph.size();
    }


    private Survey getSurveyById(Integer surveyId) {
        Survey s = surveyCache.get(surveyId);
        if (s == null) {
            s = ss.findOne(surveyId);
            surveyCache.put(surveyId, s);
        }
        return s;
    }

    protected List<String> findSplittableAvNames(Integer surveyId) {
        if (!isSplittableModule(surveyId)) {
            return null;
        }
        List<Map> avMaps = findAvMapsForModule(surveyId);

        List<String> avNames = Lists.newArrayList();
        for (Map avMap : avMaps) {
            Boolean splittableAv = (Boolean) avMap.get("split");
            if (splittableAv != null && splittableAv) {
                avNames.add(avMap.get("var").toString());
            }
        }
        return avNames;
    }

    public String getModuleName(Integer moduleId, String avName) {
        if (avName == null) {
            return getModuleName(moduleId);
        }
        List<Map> avMaps = findAvMapsForModule(moduleId);
        for (Map m : avMaps) {
            String name = (String) m.get("var").toString();
            if (avName.equals(name)) {
                if (m.get("split") != null) {
                    return m.get("alias").toString();
                } else {
                    return surveyCache.get(moduleId).getName();
                }
            }
        }
        throw new IllegalStateException(String.format("No information found for module name with the combination of moduleId=%s and av name=%s", moduleId, avName));
    }

    public String getModuleName(Integer moduleId) {
        getSurveyById(moduleId);
        return surveyCache.get(moduleId).getName();
    }

    public List<String> getAllAvsFromModule(Integer surveyId) {
        Survey s = getSurveyById(surveyId);
        return getAllAvsFromModule(s.getName());
    }

    public List<String> getAllAvsFromModule(String name) {
        List<Map> avMaps = avMap.get(name);
        if (avMaps == null) {
            return null;
        }
        List<String> avNames = Lists.newArrayList();
        for (Map avMap : avMaps) {
            avNames.add(avMap.get("var").toString());
        }
        return avNames;
    }
}
