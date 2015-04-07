package gov.va.escreening.service;

import static org.junit.Assert.*;
import freemarker.template.TemplateException;
import static gov.va.escreening.constants.AssessmentConstants.*;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.test.AssessmentVariableBuilder;
import gov.va.escreening.test.IntegrationTest;
import gov.va.escreening.test.TestAssessmentVariableBuilder;
import gov.va.escreening.variableresolver.AssessmentVariableDto;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Category(IntegrationTest.class)
@RunWith(JUnit4.class)
public class RuleExpressionFunctionTest {
    private static final Logger logger = LoggerFactory.getLogger(RuleExpressionFunctionTest.class);
    
    protected TestAssessmentVariableBuilder avBuilder;
    protected AtomicInteger ruleAvId;
    
    @Before
    public void setUp() {
        avBuilder = new TestAssessmentVariableBuilder();
        ruleAvId = new AtomicInteger(0);
    }
    
    @Test
    public void testGetFreeTextAnswerVariableAreAvailableInExpression() throws Exception{
        Integer freeTextId = ruleAvId.getAndIncrement();
        assertTrue(
                evaluateRule("getFreeTextAnswer(variable(" + freeTextId + "), \"notset\")", 
                        avBuilder.addFreeTextAv(freeTextId, "test", "true")));
    }
    
    @Test
    public void testCalculateAge() throws Exception{
        DateTime date = new DateTime().minusYears(3);
        
        assertTrue(
                evaluateRule("calculateAge(\"" + date.toString(STANDARD_DATE_FORMAT) + "\") == \"3\"", avBuilder));
    }

    protected Boolean evaluateRule(String expression, AssessmentVariableBuilder avBuilder) throws IOException, TemplateException{
        Integer avId = ruleAvId.getAndIncrement();
        Collection<AssessmentVariable> expressionDependencies = avBuilder.getVariables();
        avBuilder
            .addFormulaAv(avId, expression)
            .addAvChildren(expressionDependencies);
        
        Map<Integer, AssessmentVariableDto> dtoMap = avBuilder.buildDtoMap();
        AssessmentVariableDto evaluatedRule = dtoMap.get(avId);
        if(evaluatedRule != null){
            return Boolean.parseBoolean(evaluatedRule.getValue());
        }
        return false;
    }
}
