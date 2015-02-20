package gov.va.escreening.controller;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import gov.va.escreening.entity.AssessmentStatus;
import gov.va.escreening.entity.MeasureType;
import gov.va.escreening.service.AssessmentStatusService;
import gov.va.escreening.service.MeasureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/refdata")
public class RefDataRestController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(type = MeasureService.class)
    MeasureService ms;

    @RequestMapping(value = "/services/measureType", method = RequestMethod.GET)
    @ResponseBody
    public Collection<Map> getMeasureTypes() {
        List<MeasureType> mtLst = ms.loadAllMeasureTypes();

        return Collections2.transform(mtLst, new Function<MeasureType, Map>() {
            @Nullable
            @Override
            public Map apply(MeasureType input) {
                Map m = Maps.newHashMap();
                m.put("id", input.getMeasureTypeId());
                m.put("name", input.getName());
                return m;
            }

            ;
        });
    }
}
