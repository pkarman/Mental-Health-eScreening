package gov.va.escreening.xport;

import gov.va.escreening.service.export.DataDictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Created by munnoo on 1/25/15.
 */
@Component("ddCache")
public class DDCache {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(type = DataDictionaryService.class)
    DataDictionaryService dds;

    Object dd;

    @PostConstruct
    private void init() {
        logger.warn(">>>>>>[init]DD-CREATE");
        dd = dds.createDataDictionary();
    }

    public Object getDDCache() {
        return this.dd;
    }
}
