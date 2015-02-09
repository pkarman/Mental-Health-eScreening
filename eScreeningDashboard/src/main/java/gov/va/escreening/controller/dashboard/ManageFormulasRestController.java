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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(value = "/services/formulas", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Map> persistFormula(HttpServletRequest request,
                                              @RequestBody Map<String, Object> tgtFormula,
                                              @CurrentUser EscreenUser escreenUser) {

        Map<String, String> m = Maps.newHashMap();
        try {
            Integer avId = expressionEvaluator.updateFormula(tgtFormula);
            m.put("data", String.valueOf(avId));
            // invalidate the session so cached DD is reflecting the correct data
            sessionMgr.invalidateDD(request);
            return new ResponseEntity<Map>(m, HttpStatus.OK);
        } catch (Exception e) {
            m.put("data", Throwables.getRootCause(e).getMessage());
            return new ResponseEntity<Map>(m, HttpStatus.BAD_REQUEST);
        }


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
    public ResponseEntity<Map> testFormula(@RequestBody Map<String, Object> tgtFormula) {
        Map<String, String> m = Maps.newHashMap();
        try {
            String result = expressionEvaluator.evaluateFormula(tgtFormula);
            m.put("data", result);
            return new ResponseEntity<Map>(m, HttpStatus.OK);
        } catch (Exception e) {
            m.put("data", Throwables.getRootCause(e).getMessage());
            return new ResponseEntity<Map>(m, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/services/formulas/verify", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Map> verifyFormula(@RequestBody Map<String, String> tgtFormula) {
        String formulaAsStr = tgtFormula.get("f2v");

        if (formulaAsStr == null) {
            Map result = Maps.newHashMap();
            result.put("status", "failed");
            result.put("reason", "missing formula");
            return new ResponseEntity<Map>(result, HttpStatus.BAD_REQUEST);
        } else {
            String filteredExpTemplate = formulaAsStr.replaceAll("[$]", "");
            Map result = expressionEvaluator.extractInputsRecursively(filteredExpTemplate);
            if (result.get(ExpressionEvaluatorService.key.status.name()).equals(ExpressionEvaluatorService.key.failed.name())) {
                return new ResponseEntity<Map>(result, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<Map>(result, HttpStatus.OK);
        }
    }
}
