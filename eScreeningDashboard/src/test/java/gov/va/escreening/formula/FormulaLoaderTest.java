package gov.va.escreening.formula;

import com.google.common.collect.Lists;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class FormulaLoaderTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource(type = ExpressionEvaluatorService.class)
    ExpressionEvaluatorService expressionEvaluator;

    @Resource(type = AssessmentVariableRepository.class)
    AssessmentVariableRepository avr;

    @Test
    public void doNothing() {

    }

    /**
     * this method will basically parse the assessment_variable's formula_template column data and break each operator
     * or brackets or constants or assessment_variable pks into tokens for assessment_formula table
     */
    @Test
    @Transactional
    public void loadFormulaTemplatesToAssessmentFormulas() throws Exception {

        StringBuilder inserts = new StringBuilder();

        List<AssessmentVariable> formulas = avr.findAllFormulae();
        for (AssessmentVariable formulaVar : formulas) {
            if (formulaVar.getAssessmentVariableId() == 12) {
                // ignore formula #calculateAge
                continue;
            }
            List<String> formulaTokensAsMin = buildFormulaTokensAsMin(formulaVar);
            String origTemplate = formulaVar.getFormulaTemplate().replaceAll("\\s", "");
            String templateFromTokens = expressionEvaluator.buildFormulaFromMin(formulaTokensAsMin);

            Assert.isTrue(origTemplate.equals(templateFromTokens));

            //Map<String, Object> asMap = Maps.newHashMap();
            //asMap.put("av", formulaVar.getAsFormulaVar());
            //asMap.put("tokens", tokensFromTemplate);

            addFormulaTokensAsDbInserts(inserts, formulaVar, formulaTokensAsMin);

            //expressionEvaluator.updateFormula(asMap);
        }

        saveDbInserts(inserts);
    }

    private void saveDbInserts(StringBuilder inserts) {
        String dbInserts = inserts.toString();
        // copy this string and save it in a file to run the script in mysql
    }

    private void addFormulaTokensAsDbInserts(StringBuilder inserts, AssessmentVariable av, List<String> tokensFromTemplate) {
        av.attachFormulaTokens(tokensFromTemplate);

        for (AssessmentFormula af : av.getAssessmentFormulas()) {
            String insertStatement = String.format("insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (%s, %s, '%s',%s);", af.getParentAssessment().getAssessmentVariableId(), af.getDisplayOrder(), af.getFormulaToken(), af.getUserDefined());
            inserts.append(insertStatement).append("\n");
        }
    }

    private List<String> buildFormulaTokensAsMin(AssessmentVariable formulaVar) throws Exception {
        String formulaTemplate = formulaVar.getFormulaTemplate().replaceAll("\\s", "");
        Reader formulaReader = new StringReader(formulaTemplate);

        List<String> tokens = Lists.newArrayList();
        int c = -1;

        boolean bracketSeen = false;
        String varId = "";
        int index = 0;
        boolean numberSeen = false;
        while ((c = formulaReader.read()) != -1) {
            if (c == '[') {
                bracketSeen = true;
                continue;
            } else if (c == ']') {
                bracketSeen = false;
                if (!varId.isEmpty()) {
                    tokens.add(buildToken(varId.replaceAll("[$]", ""), false));
                    varId = "";
                }
            } else if (bracketSeen) {
                varId += (char) c;
            } else if (!bracketSeen && c > 47 && c < 58) {
                numberSeen = true;
                varId += (char) c;
            } else {
                if (!varId.isEmpty()) {
                    // this will be a number entered by user as a CONSTANT
                    tokens.add(buildToken(varId, true));
                    varId = "";
                    numberSeen = false;
                }
                // should should be any other single character
                tokens.add(buildToken(String.valueOf((char) c),true));
            }
        }

        if (!bracketSeen && !varId.isEmpty()) {
            if (numberSeen) {
                tokens.add(buildToken(varId, true));
            } else {
                tokens.add(buildToken(varId, false));
            }
        }

        return tokens;

    }

    private String buildToken(String token, boolean userDefined) {
        return String.format("%s|%s", userDefined ? "t" : "f", token);
    }
}