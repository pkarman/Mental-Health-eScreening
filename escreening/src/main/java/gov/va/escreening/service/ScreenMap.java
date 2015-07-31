package gov.va.escreening.service;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by munnoo on 4/25/15.
 */
@Component("poitiveScreenMap")
public class ScreenMap {
    @Resource(name = "modulesForPositiveScreeningMap")
    Map<String, String> modulesForPositiveScreeningMap;
    private Map<String, List<Map>> avMap;

    @PostConstruct
    private void constructPosNegScreenScoreRulesMap() {
        Gson gson = new GsonBuilder().create();
        this.avMap = Maps.newHashMap();
        for (String moduleName : modulesForPositiveScreeningMap.keySet()) {
            String avsAsJson = modulesForPositiveScreeningMap.get(moduleName);
            List<Map> avMapLst = gson.fromJson(avsAsJson, List.class);
            this.avMap.put(moduleName, avMapLst);
        }
    }

    public Map<String, List<Map>> getAvMap() {
        return avMap;
    }

}
