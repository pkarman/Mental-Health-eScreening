package gov.va.escreening.delegate;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.service.SurveyScoreIntervalService;
import gov.va.escreening.service.SurveyService;
import gov.va.escreening.service.VeteranAssessmentSurveyScoreService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by munnoo on 4/23/15.
 */
@Component("reportsHelper")
public class ReportFunctionCommon {
    private Map<Integer, Survey> surveyCache;

    @Resource(type = SurveyService.class)
    SurveyService ss;

    @Resource(type = SurveyScoreIntervalService.class)
    SurveyScoreIntervalService intervalService;


    @Resource(type = VeteranAssessmentSurveyScoreService.class)
    protected VeteranAssessmentSurveyScoreService scoreService;

    @PostConstruct
    private void postConstructSafeInit() {
        this.surveyCache = Maps.newHashMap();
    }

    protected final boolean isSplittableModule(Integer surveyId, Map<String, List<Map>> m) {
        List<Map> avMaps = findAvMapsForModule(surveyId, m);
        for (Map avMap : avMaps) {
            if (isSplittableAv(avMap)) {
                return true;
            }
        }
        return false;
    }

    protected final boolean isSplittableAv(Map avMap) {
        Boolean splittable = (Boolean) avMap.get("split");
        if (splittable != null && splittable) {
            return true;
        }
        return false;
    }

    private List<Map> findAvMapsForModule(Integer surveyId, Map<String, List<Map>> m) {
        Survey s = getSurveyById(surveyId);
        return m.get(s.getName());
    }

    protected final Float getAvgFromData(Map<String, Object> surveyDataForIndividualStatisticsGraph) {
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

    protected final List<String> findSplittableAvNames(Integer surveyId, Map<String, List<Map>> m) {
        if (!isSplittableModule(surveyId, m)) {
            return null;
        }
        List<Map> avMaps = findAvMapsForModule(surveyId, m);

        List<String> avNames = Lists.newArrayList();
        for (Map avMap : avMaps) {
            if (isSplittableAv(avMap)) {
                avNames.add(getAvName(avMap));
            }
        }
        return avNames;
    }

    public String createChartId(Integer moduleId, String avName, Integer veteranId, Integer clinicId) {
        return String.format("%s_%s_%s_%s", moduleId, avName, veteranId, clinicId);
    }

    public String getModuleName(Integer moduleId, String avName, Map<String, List<Map>> m) {
        if (avName == null) {
            return getModuleName(moduleId);
        }
        List<Map> avMaps = findAvMapsForModule(moduleId, m);
        for (Map avMap : avMaps) {
            String tgtAvName = getAvName(avMap);
            if (avName.equals(tgtAvName)) {
                if (isSplittableAv(avMap)) {
                    return avMap.get("alias").toString();
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

    public List<String> getAllAvsFromModule(Integer surveyId, Map<String, List<Map>> m) {
        Survey s = getSurveyById(surveyId);
        return getAllAvsFromModule(s.getName(), m);
    }

    public List<String> getAllAvsFromModule(String name, Map<String, List<Map>> m) {
        List<Map> avMaps = m.get(name);
        if (avMaps == null) {
            return null;
        }
        List<String> avNames = Lists.newArrayList();
        for (Map avMap : avMaps) {
            avNames.add(getAvName(avMap));
        }
        return avNames;
    }

    public Map<String, List<String>> partitionAssessmentVariables(Integer surveyId, Map<String, List<Map>> m) {
        Map<String, List<String>> partitionMap = Maps.newHashMap();

        List<String> s = Lists.newArrayList();
        partitionMap.put("splittable", s);

        List<String> o = Lists.newArrayList();
        partitionMap.put("ordinary", o);

        for (Map avMap : findAvMapsForModule(surveyId, m)) {
            if (isSplittableAv(avMap)) {
                s.add(getAvName(avMap));
            } else {
                o.add(getAvName(avMap));
            }
        }

        return partitionMap;
    }

    public String getAvName(Map avMap) {
        return avMap.get("var").toString();
    }

    public Map<String, List<Map>> partitionAvMap(Integer surveyId, Map<String, List<Map>> m) {
        Map<String, List<Map>> partitionMap = Maps.newHashMap();

        List<Map> s = Lists.newArrayList();
        partitionMap.put("splittable", s);

        List<Map> o = Lists.newArrayList();
        partitionMap.put("ordinary", o);

        for (Map avMap : findAvMapsForModule(surveyId, m)) {
            if (isSplittableAv(avMap)) {
                s.add(avMap);
            } else {
                o.add(avMap);
            }
        }

        return partitionMap;
    }

    protected String extractSvgObject(Map<String, String> svgObject, Integer surveyId, String avName, Integer vId, Integer clinicId) {
        String svgObjAsStr= (svgObject != null && !svgObject.isEmpty()) ? svgObject.get(createChartId(surveyId, avName, vId, clinicId)) : null;
        if (svgObjAsStr==null){
            return null;
        }
        // firefox returns the scg header as <svg height="300" width="450" id="chart"> and jasper has a logic to look for
        // <svg id="chart" width="450" height="300">
        svgObjAsStr = svgObjAsStr.replace("<svg height=\"300\" width=\"450\" id=\"chart\">", "<svg id=\"chart\" width=\"450\" height=\"300\">");
        return svgObjAsStr;
    }
}
