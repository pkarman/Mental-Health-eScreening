package gov.va.escreening.variableresolver;

import static org.junit.Assert.*;

import java.util.Collection;

import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.test.AssessmentVariableBuilder;
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
        assertFormulaResult("[1] + [2]", 3d,  avBuilder
            .addSelectOneAv(1, "first-select-one-visible")
                .addAnswer(null, null, "select multi option 1", null, 1d, true, null)
            .addSelectOneAv(2, "second-select-one-visible")
                .addAnswer(null, null, "select one option 1", null, 2d, true, null));        
    }
    
    @Test
    public void testFormulaWithInvisibleQuestion() throws Exception{
        assertFormulaResult("[1] + [2]", 1d, avBuilder
            .addSelectOneAv(1, "first-select-one-visible")
                .addAnswer(null, null, "select multi option 1", null, 1d, true, null)
            .addSelectOneAv(2, "second-select-one-invisible")
                .addAnswer(null, null, "select one option 1", null, 2d, null, null)
                .setMeasureVisibility(false));
    }
    
    @Test
    public void testFormulaWithVisibleQuestion() throws Exception{
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
    
    @Test
    public void testFormulaWithAnswerOfInvisibleUnansweredQuestion() throws Exception{
        assertFormulaResult("[2]?1:0 + [4]?1:0", 1, avBuilder
                .addSelectOneAv(1, "first-select-one-visible")
                    .addAnswer(null, 2, "select multi option 1", null, 1d, true, null)
                .addSelectOneAv(3, "second-select-one-invisible")
                    .addAnswer(null, 4, "select one option 1", null, 2d, null, null)
                    .setMeasureVisibility(false));
    }
    
    @Test
    public void testFormulaWithAnswerOfVisibleUnansweredQuestion() throws Exception{
        Collection<AssessmentVariable> dependencies = avBuilder
                .addSelectOneAv(1, "first-select-one-visible")
                    .addAnswer(null, 2, "select multi option 1", null, 1d, null, null)
                .addSelectOneAv(3, "second-select-one-visible")
                    .addAnswer(null, 4, "select one option 1", null, 2d, true, null)
                .getVariables();
            
            avBuilder
                .addFormulaAv(5, "[2]?1:0 + [4]?1:0")
                .addAvChildren(dependencies);
                    
            Collection<AssessmentVariableDto> dtos = avBuilder.getDTOs();
            assertEquals(2, dtos.size());
            for(AssessmentVariableDto dto : dtos){
                assertFalse("The formula should not have been resolved to a value", dto.getVariableId().equals(5));
            }
    }
    
    @Test
    public void testMultiSelectWasAnsweredTrueCase() throws Exception{
        assertFormulaResult("wasAnswered(variable(1))", "true", avBuilder
            .addSelectMultiAv(1, "test select multi question")
                .addAnswer(null, null, "first", null, null, false, null)
                .addAnswer(null, null, "second", null, null, false, null)
                .addAnswer(null, null, "third", null, null, true, null)
                .addAnswer(null, null, "fourth", null, null, false, null));
    }
    
    @Test
    public void testMultiSelectWasAnsweredFalseCase() throws Exception{
        assertFormulaResult("wasAnswered(variable(1))", "false", avBuilder
            .addSelectMultiAv(1, "test select multi question")
                .addAnswer(null, null, "first", null, null, false, null)
                .addAnswer(null, null, "second", null, null, false, null)
                .addAnswer(null, null, "third", null, null, false, null)
                .addAnswer(null, null, "fourth", null, null, false, null));
    }
    
    
    
    private void assertFormulaResult(String formulaExpression,
            Double expectedResult,
            AssessmentVariableBuilder builder){
        assertFormulaResult(formulaExpression, expectedResult.toString(), builder);
    }
    
    private void assertFormulaResult(String formulaExpression,
            Integer expectedResult,
            AssessmentVariableBuilder builder){
        assertFormulaResult(formulaExpression, expectedResult.toString(), builder);
    }
    
    private void assertFormulaResult(String formulaExpression,
            String expectedResult,
            AssessmentVariableBuilder builder){
        
        Collection<AssessmentVariable> dependencies = builder.getVariables();
        Integer maxAvId = 0;
        for(AssessmentVariable av : dependencies){
            if(av.getAssessmentVariableId() > maxAvId){
                maxAvId = av.getAssessmentVariableId();
            }
        }
        int formulaId = maxAvId +1;
        
        builder
            .addFormulaAv(formulaId, formulaExpression)
            .addAvChildren(dependencies);
        
        Collection<AssessmentVariableDto> dtos = builder.getDTOs();
        for(AssessmentVariableDto dto : dtos){
            if(dto.getVariableId().equals(formulaId)){
                assertEquals(expectedResult, dto.getValue());
                return;
            }
        }
        throw new AssertionError("Formula was not resolved");
    }
}
