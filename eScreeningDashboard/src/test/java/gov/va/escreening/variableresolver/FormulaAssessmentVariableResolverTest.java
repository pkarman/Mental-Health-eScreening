package gov.va.escreening.variableresolver;

import static org.junit.Assert.*;

import java.util.Collection;

import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.expressionevaluator.ExpressionEvaluatorService;
import gov.va.escreening.test.TestAssessmentVariableBuilder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class FormulaAssessmentVariableResolverTest {
    private TestAssessmentVariableBuilder avBuilder;
    
    @Before
    public void setUp(){
        avBuilder = new TestAssessmentVariableBuilder();
    }
    
    @Test
    public void testFormulaWithAllQuestionsAnswer() throws Exception{
        Collection<AssessmentVariable> dependencies = avBuilder
            .addSelectOneAv(1, "first-select-one-visible")
                .addAnswer(null, null, "select multi option 1", null, 1d, true, null)
            .addSelectOneAv(2, "second-select-one-visible")
                .addAnswer(null, null, "select one option 1", null, 2d, true, null)
            .getVariables();
        
        avBuilder
            .addFormulaAv(3, "[1] + [2]")
            .addAvChildren(dependencies);
                
        Collection<AssessmentVariableDto> dtos = avBuilder.getDTOs();
        assertEquals(5, dtos.size());
        for(AssessmentVariableDto dto : dtos){
            if(dto.getVariableId().equals(3)){
                assertTrue(dto.getValue().equals("3.0"));
                return;
            }
        }
        throw new AssertionError("Formula was not resolved");
    }
    
    @Test
    public void testFormulaWithHiddenQuestionsSkipped() throws Exception{
        Collection<AssessmentVariable> dependencies = avBuilder
            .addSelectOneAv(1, "first-select-one-visible")
                .addAnswer(null, null, "select multi option 1", null, 1d, true, null)
            .addSelectOneAv(2, "second-select-one-visible")
                .addAnswer(null, null, "select one option 1", null, 2d, null, null)
                .setMeasureVisibility(false)
            .getVariables();
            
            avBuilder
                .addFormulaAv(3, "[1] + [2]")
                .addAvChildren(dependencies);
                    
            Collection<AssessmentVariableDto> dtos = avBuilder.getDTOs();
            assertEquals(3, dtos.size());
            for(AssessmentVariableDto dto : dtos){
                if(dto.getVariableId().equals(3)){
                    assertTrue(dto.getValue().equals("1.0"));
                    return;
                }
            }
            throw new AssertionError("Formula was not resolved");
    }
    
    @Test
    public void testFormulaWithShownQuestionsSkipped() throws Exception{
        Collection<AssessmentVariable> dependencies = avBuilder
                .addSelectOneAv(1, "first-select-one-visible")
                    .addAnswer(null, null, "select multi option 1", null, 1d, null, null)
                .addSelectOneAv(2, "second-select-one-visible")
                    .addAnswer(null, null, "select one option 1", null, 2d, true, null)
                .getVariables();
            
            avBuilder
                .addFormulaAv(3, "[1] + [2]")
                .addAvChildren(dependencies);
                    
            Collection<AssessmentVariableDto> dtos = avBuilder.getDTOs();
            assertEquals(2, dtos.size());
            for(AssessmentVariableDto dto : dtos){
                assertFalse("The formula should not have been resolved to a value", dto.getVariableId().equals(3));
            }
    }
}
