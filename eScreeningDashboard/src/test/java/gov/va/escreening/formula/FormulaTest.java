package gov.va.escreening.formula;

import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.expressionevaluator.ExpressionEvaluatorService;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class FormulaTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource(type = ExpressionEvaluatorService.class)
    ExpressionEvaluatorService expressionEvaluator;

    @Test
    public void testValidRoundFormula() {
        String result = expressionEvaluator.evaluateFormula("T(Math).round(199/T(Math).pow( ( ( 6 * 12 ) + 0 ) ,2)*703)", null);
        Assert.isTrue(result.equals("27"));
    }
    @Test
    public void testValidFormula() {
        String result = expressionEvaluator.evaluateFormula("((2f)*(3f))", null);
        Assert.isTrue(result.equals("6.0"));
    }
    @Test
    public void mathRemoval() {
        String result = "T(Math).round([demo_weight])+T(Math).pow(T(Math).max([demo_weight],[demo_race_pacisl]),3)";
        String cleaned = result.replaceAll("[T][(]Math[)][.]","");
        Assert.isTrue(cleaned.equals("round([demo_weight])+pow(max([demo_weight],[demo_race_pacisl]),3)"));
    }

    @Test
    public void testValidMaxFormula() {
        String result = expressionEvaluator.evaluateFormula("T(Math).max(3.34,2.23)", null);
        Assert.isTrue(result.equals("3.34"));
    }

    @Test
    public void showFormulasWith_AvId_Plus_AvDisplayName_Plus_OrigFormulaTemplate_Plus_FormulasWithDisplayNames() {
        expressionEvaluator.readAllFormulas(new FormulaHandler() {
            @Override
            public void handleFormula(AssessmentVariable av, String formulaWithDisplayNames) {
                logger.info(String.format("avId:%s\nname:%s\norig-formula:%s\ndisplayNames:%s\n\n", av.getAssessmentVariableId(), av.getDisplayName(), av.getFormulaTemplate(), formulaWithDisplayNames));
            }
        });
    }
    @Test
    public void showDecmalFormatting() {
        String d=String.format("%03d", 4 * 100 / 47);
    }

    @Test
    public void showFormulasAsUserWouldEnter() {
        logger.info(generateFormulasAsUserWouldEnter().toString());
    }

    @Test
    public void verifyUserEnteredFormulas() {
        final Set<String> userEnteredFormulas = Sets.newHashSet();

        expressionEvaluator.readAllFormulas(new FormulaHandler() {
            @Override
            public void handleFormula(AssessmentVariable av, String formulaWithDisplayNames) {
                userEnteredFormulas.add(av.getFormulaTemplate());
            }
        });

        for (String uef : userEnteredFormulas) {
            Map<String, Object> verifedExpressionResult = expressionEvaluator.verifyExpressionTemplate(uef.replaceAll("[$]", ""), AvMapTypeEnum.ID2NAME);
            logger.info(verifedExpressionResult.toString());
        }
    }

    private List<String> generateFormulasAsUserWouldEnter() {
        final List<String> userEnteredFormulas = Lists.newArrayList();

        expressionEvaluator.readAllFormulas(new FormulaHandler() {
            @Override
            public void handleFormula(AssessmentVariable av, String formulaWithDisplayNames) {
                userEnteredFormulas.add(formulaWithDisplayNames);
            }
        });
        return userEnteredFormulas;
    }

}