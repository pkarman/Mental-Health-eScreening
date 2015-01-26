package gov.va.escreening.controller.dashboard;

import gov.va.escreening.service.export.DataDictionaryService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by munnoo on 1/25/15.
 */
@Component("sessionMgr")
public class SessionMgr {

    @Resource(type = DataDictionaryService.class)
    DataDictionaryService dds;

    public Object getDD(HttpServletRequest request) {
        Object dd = request.getSession().getAttribute("data_dictionary");
        if (dd == null) {
            dd = dds.createDataDictionary();
            request.getSession().setAttribute("data_dictionary", dd);
        }
        return dd;
    }
}
