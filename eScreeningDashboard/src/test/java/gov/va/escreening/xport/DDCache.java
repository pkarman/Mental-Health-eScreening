package gov.va.escreening.xport;

import gov.va.escreening.service.export.DataDictionaryService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Created by munnoo on 1/25/15.
 */
@Component("ddCache")
public class DDCache {
    @Resource(type = DataDictionaryService.class)
    DataDictionaryService dds;

    Object dd;

    @PostConstruct
    private void init() {
        dd = dds.createDataDictionary();
    }

    public Object getDDCache() {
        return this.dd;
    }
}
