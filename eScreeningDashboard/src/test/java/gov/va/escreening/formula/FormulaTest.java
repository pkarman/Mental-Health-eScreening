package gov.va.escreening.formula;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.expressionevaluator.ExpressionEvaluatorService;
import gov.va.escreening.expressionevaluator.FormulaDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class FormulaTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource(type = ExpressionEvaluatorService.class)
    ExpressionEvaluatorService expressionEvaluator;

    @Test
    public void testValidFormula() {
        String result = expressionEvaluator.evaluateFormulaTemplate("((2f)*(3f))");
        Assert.isTrue(result.equals("6.0"));
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
    public void showFormulasAsUserWouldEnter() {
        logger.info(generateFormulasAsUserWouldEnter().toString());
    }

    @Test
    public void createFormulaDto() {
        String formula = "([$10711$]+[$10712$]+([202]?1:0)+([214]?1:0))";
        Map<Integer, String> dataMap = newHashMap();
        dataMap.put(1536, "false");
        dataMap.put(202, "false");
        dataMap.put(1535, "false");
        dataMap.put(1534, "false");
        dataMap.put(1533, "false");
        dataMap.put(1532, "false");
        dataMap.put(214, "false");
        dataMap.put(1524, "false");
        dataMap.put(1523, "false");
        dataMap.put(1522, "false");

        FormulaDto fdto = expressionEvaluator.createFormulaDto(formula, dataMap);
        Assert.isTrue(fdto.getChildFormulaMap().size() == 2);
    }

    @Test
    public void updateFormula() {
        Integer avId=10713;
        String formula = "([$10711$]+[$10712$]+([202]?1:0)+([214]?1:0)+([250]?1:0)+([214]?1:0)+([214]?1:0)+([214]?1:0))";

        AssessmentVariable avB=expressionEvaluator.findAvById(avId);
        Assert.isTrue(!avB.getFormulaTemplate().equals(formula));
        expressionEvaluator.updateFormula(avId, formula);
        AssessmentVariable avC=expressionEvaluator.findAvById(avId);
        Assert.isTrue(avC.getFormulaTemplate().equals(formula));

    }

    @Test
    public void extractAllFormulaInputs() {
        List<Integer> avIds = Lists.newArrayList();
        expressionEvaluator.extractAllInputs(10713, avIds);
        List<Integer> expectedIds = Arrays.asList(1536, 202, 1535, 1534, 1533, 1532, 214, 1524, 1523, 1522);
        Assert.isTrue(expectedIds.size() == avIds.size());
        Assert.isTrue(expectedIds.containsAll(avIds));
        Assert.isTrue(avIds.containsAll(expectedIds));
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
            Map<String, String> verifedExpressionResult = expressionEvaluator.verifyExpressionTemplate(uef, AvMapTypeEnum.ID2NAME);
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