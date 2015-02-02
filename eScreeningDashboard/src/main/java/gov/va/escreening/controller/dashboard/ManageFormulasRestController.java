package gov.va.escreening.controller.dashboard;

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import gov.va.escreening.dto.editors.SurveyInfo;
import gov.va.escreening.expressionevaluator.ExpressionEvaluatorService;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.SurveyService;
import gov.va.escreening.service.export.DataDictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller("FormulasMgr")
@RequestMapping(value = "/dashboard")
public class ManageFormulasRestController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(type = DataDictionaryService.class)
    DataDictionaryService dds;

    @Resource(type = SurveyService.class)
    SurveyService ss;

    @Resource(name = "sessionMgr")
    SessionMgr sessionMgr;

    @Resource(type = ExpressionEvaluatorService.class)
    ExpressionEvaluatorService expressionEvaluator;


    @RequestMapping(value = "/services/formulas", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Map> getFormulasForModule(HttpServletRequest request,
                                          @RequestParam("moduleId") Integer moduleId,
                                          @CurrentUser EscreenUser escreenUser) {

        List<Map> formulas = dds.askFormulasFor(sessionMgr.getDD(request), moduleId);
        return formulas;
    }

    @RequestMapping(value = "/services/formulas/modules/{moduleId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public SurveyInfo getModuleById(@PathVariable("moduleId") Integer moduleId, @CurrentUser EscreenUser escreenUser) {
        List<SurveyInfo> surveyItemList = ss.getSurveyItemList();
        for (SurveyInfo si : surveyItemList) {
            if (moduleId.equals(si.getSurveyId())) {
                return si;
            }
        }
        return null;
    }

    @RequestMapping(value = "/services/formulas/test", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    public Map testFormula(@RequestBody Map<String, Object> tgtFormula) {
        String formulaAsStr = (String)tgtFormula.get("formulaToTest");
        Map result = Maps.newHashMap();
        result.put("status", "failed");

        if (formulaAsStr == null) {
            result.put("reason", "missing formula");
        } else {
            try {
                String testResult = expressionEvaluator.evaluateFormulaTemplate(formulaAsStr);
                result.put("status", "passed");
                result.put("result", testResult);
            } catch (Exception e) {
                result.put("reason", Throwables.getRootCause(e).getMessage());
            }
        }
        return result;
    }
}
