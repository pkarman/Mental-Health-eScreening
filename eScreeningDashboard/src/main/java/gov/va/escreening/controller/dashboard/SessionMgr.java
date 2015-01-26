package gov.va.escreening.controller.dashboard;

import gov.va.escreening.service.export.DataDictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by munnoo on 1/25/15.
 */
@Component("sessionMgr")
public class SessionMgr {
    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Resource(type = DataDictionaryService.class)
    DataDictionaryService dds;

    public Object getDD(HttpServletRequest request) {
        Object dd = request.getSession().getAttribute("data_dictionary");
        if (dd == null) {
            logger.warn(">>>>>>[getDD]DD-CREATE");

            dd = dds.createDataDictionary();
            request.getSession().setAttribute("data_dictionary", dd);
        }
        return dd;
    }
}
