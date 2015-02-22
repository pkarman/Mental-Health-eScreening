package gov.va.escreening.formula;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import gov.va.escreening.entity.AssessmentFormula;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.expressionevaluator.ExpressionEvaluatorService;
import gov.va.escreening.repository.AssessmentVariableRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class FormulaLoaderTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource(type = ExpressionEvaluatorService.class)
    ExpressionEvaluatorService expressionEvaluator;

    @Resource(type = AssessmentVariableRepository.class)
    AssessmentVariableRepository avr;

    @Test
    public void loadFormulaTemplatesToAssessmentFormulas() {
        List<AssessmentVariable> formulas = avr.findAllFormulae();
        for (AssessmentVariable formulaVar : formulas) {


            List<AssessmentFormula> origTokens = formulaVar.getAssessmentFormulas();
            List<AssessmentFormula> tokensFromTemplate = formulaVar.buildTokensFromTemplate();
            //Assert.isTrue(origTokens.equals(tokensFromTemplate));

            //String origTemplate = formulaVar.getFormulaTemplate();
            //String templateFromTokens = formulaVar.buildTemplateFromTokens();
            //Assert.isTrue(origTemplate.equals(templateFromTokens));


            avr.update(formulaVar);
        }
    }
}