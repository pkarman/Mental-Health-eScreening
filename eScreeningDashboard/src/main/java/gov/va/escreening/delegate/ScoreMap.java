package gov.va.escreening.delegate;

/**
 * Created by munnoo on 4/25/15.
 */

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component("scoreMap")
public class ScoreMap {
    @Resource(name = "modulesForScoringMap")
    private Map<String, String> modulesForScoringMap;
    private Map<String, List<Map>> avMap;

    @PostConstruct
    private void constructPosNegScreenScoreRulesMap() {
        Gson gson = new GsonBuilder().create();
        this.avMap = Maps.newHashMap();
        for (String moduleName : modulesForScoringMap.keySet()) {
            String avAsJson = modulesForScoringMap.get(moduleName);
            List<Map> avMapLst = gson.fromJson(avAsJson, List.class);
            this.avMap.put(moduleName, avMapLst);
        }
    }

    public Map<String, List<Map>> getAvMap() {
        return avMap;
    }
}
