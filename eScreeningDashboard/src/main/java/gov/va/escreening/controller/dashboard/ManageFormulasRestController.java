package gov.va.escreening.controller.dashboard;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import gov.va.escreening.delegate.EditorsViewDelegate;
import gov.va.escreening.domain.ErrorCodeEnum;
import gov.va.escreening.dto.ae.ErrorResponse;
import gov.va.escreening.dto.editors.SurveyInfo;
import gov.va.escreening.dto.editors.SurveySectionInfo;
import gov.va.escreening.exception.AssessmentEngineDataValidationException;
import gov.va.escreening.expressionevaluator.FormulaDto;
import gov.va.escreening.repository.MeasureRepository;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.SurveyService;
import gov.va.escreening.service.export.DataDictionaryService;
import gov.va.escreening.variableresolver.FormulaAssessmentVariableResolver;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;
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
}
