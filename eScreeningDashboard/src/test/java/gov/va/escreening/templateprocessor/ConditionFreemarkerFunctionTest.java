package gov.va.escreening.templateprocessor;

import static org.junit.Assert.*;

import java.util.Date;

import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.test.IntegrationTest;
import gov.va.escreening.test.TestAssessmentVariableBuilder.SelectAvBuilder;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import gov.va.escreening.variableresolver.CustomAssessmentVariableResolver;

/**
 * This class is concerned with the testing of freemarker functions used by template conditions.<br/>
 * This is an integration test.<br/>
 * 
 * @author Robin Carnow
 *
 */
@Category(IntegrationTest.class)
@RunWith(JUnit4.class)
public class ConditionFreemarkerFunctionTest extends FreeMarkerFunctionTest{

    @Test
    public void testMatrixHasResultConditionNull() throws Exception{
        //if null return false
        assertEquals("false",
                render("${matrixHasResult(var123)?c}", avBuilder));
    }

    @Test
    public void testMatrixHasResultConditionEmptyResponse() throws Exception{
        //if empty return false
        assertEquals("false",
                render("${matrixHasResult(var123)?c}",
                        avBuilder
                        .addSelectOneMatrixAv(123, "test matrix question")
                            .addChildWithAvId(111, "q1")
                            .addChildWithAvId(222, "q2")
                            .addChildWithAvId(333, "q3")
                            .addColumn(null, null, null, null, null)
                            .addColumn(null, null, null, null, null)
                            .addColumn(null, null, null, null, null)));
    }

    @Test
    public void testMatrixHasResultConditionValueAllEmpty() throws Exception{
        //if all options are set to false then not response (returns false)
        assertEquals("false",
                render("${matrixHasResult(var123)?c}",
                        avBuilder
                        .addSelectOneMatrixAv(123, "test matrix question")
                            .addChildWithAvId(111, "q1")
                            .addChildWithAvId(222, "q2")
                            .addChildWithAvId(333, "q3")
                            .addColumn(null, null, null, Lists.<Boolean>newArrayList(null, null, null), null)
                            .addColumn(null, null, null, Lists.<Boolean>newArrayList(null, null, null), null)
                            .addColumn(null, null, null, Lists.<Boolean>newArrayList(null, null, null), null)));
    }

    @Test
    public void testMatrixHasResultConditionValueAllFalse() throws Exception{
        //if all options are set to false then not response (returns false)
        assertEquals("false",
                render("${matrixHasResult(var123)?c}",
                        avBuilder
                        .addSelectOneMatrixAv(123, "test matrix question")
                            .addChildWithAvId(111, "q1")
                            .addChildWithAvId(222, "q2")
                            .addChildWithAvId(333, "q3")
                            .addColumn(null, null, null, ImmutableList.of(false, false, false), null)
                            .addColumn(null, null, null, ImmutableList.of(false, false, false), null)
                            .addColumn(null, null, null, ImmutableList.of(false, false, false), null)));
    }

    @Test
    public void testMatrixHasResultConditionValue() throws Exception{
        //if has at least one option selected then true
        assertEquals("true",
                render("${matrixHasResult(var123)?c}",
                        avBuilder
                        .addSelectOneMatrixAv(123, "test matrix question")
                            .addChildWithAvId(111, "q1")
                            .addChildWithAvId(222, "q2")
                            .addChildWithAvId(333, "q3")
                            .addColumn(null, null, null, ImmutableList.of(false, false, false), null)
                            .addColumn(null, null, null, ImmutableList.of(false, true, false), null)
                            .addColumn(null, null, null, ImmutableList.of(false, false, false), null)));
    }

    @Test
    public void testMatrixHasNoResultConditionNull() throws Exception{
        //if null return true

        assertEquals("true",
                render("${matrixHasNoResult(var123)?c}", avBuilder));
    }

    @Test
    public void testMatrixHasNoResultConditionEmptyResponse() throws Exception{
        assertEquals("true",
                render("${matrixHasNoResult(var123)?c}",
                        avBuilder
                        .addSelectOneMatrixAv(123, "test matrix question")
                            .addChildWithAvId(111, "q1")
                            .addChildWithAvId(222, "q2")
                            .addChildWithAvId(333, "q3")
                            .addColumn(null, null, null, null, null)
                            .addColumn(null, null, null, null, null)
                            .addColumn(null, null, null, null, null)));
    }

    @Test
    public void testMatrixHasNoResultConditionValueAllEmpty() throws Exception{
        //if all options are have no results recorded return true
        assertEquals("true",
                render("${matrixHasNoResult(var123)?c}",
                        avBuilder
                        .addSelectOneMatrixAv(123, "test matrix question")
                            .addChildWithAvId(111, "q1")
                            .addChildWithAvId(222, "q2")
                            .addChildWithAvId(333, "q3")
                            .addColumn(null, null, null, Lists.<Boolean>newArrayList(null, null, null), null)
                            .addColumn(null, null, null, Lists.<Boolean>newArrayList(null, null, null), null)
                            .addColumn(null, null, null, Lists.<Boolean>newArrayList(null, null, null), null)));
    }

    @Test
    public void testMatrixHasNoResultConditionValueAllFalse() throws Exception{
        //if all options are set to false returns true
        assertEquals("true",
                render("${matrixHasNoResult(var123)?c}",
                        avBuilder
                        .addSelectOneMatrixAv(123, "test matrix question")
                            .addChildWithAvId(111, "q1")
                            .addChildWithAvId(222, "q2")
                            .addChildWithAvId(333, "q3")
                            .addColumn(null, null, null, ImmutableList.of(false, false, false), null)
                            .addColumn(null, null, null, ImmutableList.of(false, false, false), null)
                            .addColumn(null, null, null, ImmutableList.of(false, false, false), null)));
    }

    @Test
    public void testMatrixHasNoResultConditionValue() throws Exception{
        //if has at least one option selected then return false
        assertEquals("false",
                render("${matrixHasNoResult(var123)?c}",
                        avBuilder
                        .addSelectOneMatrixAv(123, "test matrix question")
                            .addChildWithAvId(111, "q1")
                            .addChildWithAvId(222, "q2")
                            .addChildWithAvId(333, "q3")
                            .addColumn(null, null, null, ImmutableList.of(false, false, false), null)
                            .addColumn(null, null, null, ImmutableList.of(false, true, false), null)
                            .addColumn(null, null, null, ImmutableList.of(false, false, false), null)));
    }

    @Test
    public void testTableWasAnswerNoneForTrueCase() throws Exception{
        //if None was selected return true
        assertEquals("true",
                render("${wasAnswerNone(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, true)
                            .addChildFreeText(null, "free text question", null)));
    }

    @Test
    public void testTableWasAnswerNoneForFalseCase() throws Exception{
        //if None wasn't selected returns false
        assertEquals("false",
                render("${wasAnswerNone(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, false)
                            .addChildFreeText(null, "free text question",  Lists.newArrayList("first"))));
    }

    @Test
    public void testTableWasAnswerNoneForNoAnswerCase() throws Exception{
        assertEquals("false",
                render("${wasAnswerNone(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, null)
                            .addChildFreeText(null, "free text question", null)));
    }
    
    @Test
    public void testTableWasntAnswerNoneForTrueCase() throws Exception{
        //if None was selected return false
        assertEquals("false",
                render("${wasntAnswerNone(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, true)
                            .addChildFreeText(null, "free text question", null)));
    }

    @Test
    public void testTableWasntAnswerNoneForFalseCase() throws Exception{
        //if None wasn't selected returns true
        assertEquals("true",
                render("${wasntAnswerNone(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, false)
                            .addChildFreeText(null, "free text question", ImmutableList.of("one", "two","three"))));
    }
    
    @Test
    public void testTableWasntAnswerNoneForNoAnswerCase() throws Exception{
        assertEquals("true",
                render("${wasntAnswerNone(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, null)
                            .addChildFreeText(null, "free text question", null)));
    }

    @Test
    public void testTableWasAnsweredConditionForNone() throws Exception{
        //if None was selected returns true
        assertEquals("true", 
                render("${wasAnswered(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, true)
                            .addChildFreeText(null, "free text question", null)
                            .addChildSelectOneAv(null, "select one question")
                                .addChildSelectAnswer("answer 1", null, null)
                                .addChildSelectAnswer("answer 2", null, null)
                            .addChildSelectMultiAv(null, "select multi question")
                                .addChildSelectAnswer(null, null, null)
                                .addChildSelectAnswer(null, null, null)));
    }

    @Test
    public void testTableWasAnsweredConditionForEntry() throws Exception{
        //if an entry was added returns true
        assertEquals("true", 
                render("${wasAnswered(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, true)
                            .addChildFreeText(null, "free text question", null)
                            .addChildSelectOneAv(null, "select one question")
                                .addChildSelectAnswer("answer 1", Lists.<Boolean>newArrayList(null, null, null), null)
                                .addChildSelectAnswer("answer 2", ImmutableList.of(false, true, false), null)
                            .addChildSelectMultiAv(null, "select multi question")
                                .addChildSelectAnswer(null, null, null)
                                .addChildSelectAnswer(null, null, null)));

        //test freetext
        assertEquals("true", 
                render("${wasAnswered(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, true)
                            .addChildFreeText(null, "free text question", Lists.newArrayList(null, "first", null))
                            .addChildSelectOneAv(null, "select one question")
                                .addChildSelectAnswer("answer 1", null, null)
                                .addChildSelectAnswer("answer 2", null, null)
                            .addChildSelectMultiAv(null, "select multi question")
                                .addChildSelectAnswer(null, null, null)
                                .addChildSelectAnswer(null, null, null)));
        //test select multi
        assertEquals("true", 
                render("${wasAnswered(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, true)
                            .addChildFreeText(null, "free text question", null)
                            .addChildSelectOneAv(null, "select one question")
                                .addChildSelectAnswer("answer 1", null, null)
                                .addChildSelectAnswer("answer 2", null, null)
                            .addChildSelectMultiAv(null, "select multi question")
                                .addChildSelectAnswer(null, ImmutableList.of(false, false, false), null)
                                .addChildSelectAnswer(null, ImmutableList.of(false, true, false), null)));
    }

    @Test
    public void testTableWasAnsweredConditionForEntrySelectHasAllFalse() throws Exception{
        //if all select answers are false then it was not answered
        assertEquals("false", 
                render("${wasAnswered(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, false)
                            .addChildFreeText(null, "free text question", null)
                            .addChildSelectOneAv(null, "select one question")
                                .addChildSelectAnswer("answer 1", Lists.<Boolean>newArrayList(null, null, null), null)
                                .addChildSelectAnswer("answer 2", ImmutableList.of(false, false, false), null)
                            .addChildSelectMultiAv(null, "select multi question")
                                .addChildSelectAnswer(null, null, null)
                                .addChildSelectAnswer(null, null, null)));

        //check select multi
        assertEquals("false", 
                render("${wasAnswered(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, false)
                            .addChildFreeText(null, "free text question", null)
                            .addChildSelectOneAv(null, "select one question")
                                .addChildSelectAnswer("answer 1", null, null)
                                .addChildSelectAnswer("answer 2", null, null)
                            .addChildSelectMultiAv(null, "select multi question")
                                .addChildSelectAnswer(null, ImmutableList.of(false, false, false), null)
                                .addChildSelectAnswer(null, ImmutableList.of(false, false, false), null)));
    }

    @Test
    public void testTableWasAnsweredConditionForFalse() throws Exception{
        //if no entries and no None answer was selected returns false
        assertEquals("false", 
                render("${wasAnswered(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, false)
                            .addChildFreeText(null, "free text question", null)
                            .addChildSelectOneAv(null, "select one question")
                                .addChildSelectAnswer("answer 1", Lists.<Boolean>newArrayList(null, null, null), null)
                                .addChildSelectAnswer("answer 2", Lists.<Boolean>newArrayList(null, null, null), null)
                            .addChildSelectMultiAv(null, "select multi question")
                                .addChildSelectAnswer(null, null, null)
                                .addChildSelectAnswer(null, null, null)));
    }		

    @Test
    public void testTableWasntAnsweredConditionForNone() throws Exception{
        //if None was selected returns false
        assertEquals("false", 
                render("${wasntAnswered(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, true)
                            .addChildFreeText(null, "free text question", null)
                            .addChildSelectOneAv(null, "select one question")
                                .addChildSelectAnswer("answer 1", null, null)
                                .addChildSelectAnswer("answer 2", null, null)
                            .addChildSelectMultiAv(null, "select multi question")
                                .addChildSelectAnswer(null, null, null)
                                .addChildSelectAnswer(null, null, null)));
    }

    @Test
    public void testTableWasntAnsweredConditionForEntry() throws Exception{
        //if an entry was added returns false
        assertEquals("false", 
                render("${wasntAnswered(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, true)
                            .addChildFreeText(null, "free text question", null)
                            .addChildSelectOneAv(null, "select one question")
                                .addChildSelectAnswer("answer 1", Lists.<Boolean>newArrayList(null, null, null), null)
                                .addChildSelectAnswer("answer 2", ImmutableList.of(false, true, false), null)
                            .addChildSelectMultiAv(null, "select multi question")
                                .addChildSelectAnswer(null, null, null)
                                .addChildSelectAnswer(null, null, null)));

        //test freetext
        assertEquals("false", 
                render("${wasntAnswered(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, true)
                            .addChildFreeText(null, "free text question", Lists.newArrayList(null, "first", null))
                            .addChildSelectOneAv(null, "select one question")
                                .addChildSelectAnswer("answer 1", null, null)
                                .addChildSelectAnswer("answer 2", null, null)
                            .addChildSelectMultiAv(null, "select multi question")
                                .addChildSelectAnswer(null, null, null)
                                .addChildSelectAnswer(null, null, null)));
        //test select multi
        assertEquals("false", 
                render("${wasntAnswered(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, true)
                            .addChildFreeText(null, "free text question", null)
                            .addChildSelectOneAv(null, "select one question")
                                .addChildSelectAnswer("answer 1", null, null)
                                .addChildSelectAnswer("answer 2", null, null)
                            .addChildSelectMultiAv(null, "select multi question")
                                .addChildSelectAnswer(null, ImmutableList.of(false, false, false), null)
                                .addChildSelectAnswer(null, ImmutableList.of(false, true, false), null)));
    }		

    @Test
    public void testTableWasntAnsweredConditionForFalse() throws Exception{
        //if no entries and no None answer was selected returns true
        assertEquals("true", 
                render("${wasntAnswered(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, false)
                            .addChildFreeText(null, "free text question", Lists.<String>newArrayList(null, null, null))
                            .addChildSelectOneAv(null, "select one question")
                                .addChildSelectAnswer("answer 1", Lists.<Boolean>newArrayList(null, null, null), null)
                                .addChildSelectAnswer("answer 2", Lists.<Boolean>newArrayList(null, null, null), null)
                            .addChildSelectMultiAv(null, "select multi question")
                                .addChildSelectAnswer(null, Lists.<Boolean>newArrayList(null, null, null), null)
                                .addChildSelectAnswer(null, Lists.<Boolean>newArrayList(null, null, null), null)));
    }

    @Test
    public void testIsDefinedForFreeText() throws Exception {
        assertEquals("true",
                render("${isDefined(var1)?c}",
                        avBuilder
                        .addFreeTextAv(1, "free text question", "answer")));
        
        assertEquals("false",
                render("${isDefined(var2)?c}",
                        avBuilder
                        .addFreeTextAv(2, "free text question", null)));
    }
    
    @Test
    public void testIsDefinedForSelectOne() throws Exception {
        assertEquals("true",
                render("${isDefined(var1)?c}",
                        avBuilder
                        .addSelectOneAv(1, null)
                        .addAnswer("answer1", true, null)));
        
        assertEquals("true",
                render("${isDefined(var2)?c}",
                        avBuilder
                        .addSelectOneAv(2, null)
                        .addAnswer("answer1", false, null)));
        
        assertEquals("false",
                render("${isDefined(var3)?c}",
                        avBuilder
                        .addSelectOneAv(3, null)
                        .addAnswer("answer1", null, null)));
    }
    
    @Test
    public void testIsDefinedForSelectMulti() throws Exception {
        assertEquals("true",
                render("${isDefined(var1)?c}",
                        avBuilder
                        .addSelectMultiAv(1, null)
                        .addAnswer("answer1", true, null)));
        
        assertEquals("true",
                render("${isDefined(var2)?c}",
                        avBuilder
                        .addSelectMultiAv(2, null)
                        .addAnswer("answer1", false, null)));
        
        assertEquals("false",
                render("${isDefined(var3)?c}",
                        avBuilder
                        .addSelectMultiAv(3, null)
                        .addAnswer("answer1", null, null)));
    }
    
    @Test
    public void testIsDefinedForSelectOneMatrixTrue() throws Exception {
        assertEquals("true",
                render("${isDefined(var1)?c}",
                        avBuilder
                        .addSelectOneMatrixAv(1, null)
                        .addChildWithAvId(null, null)
                        .addColumn("answer1", null, 1d, 
                                Lists.<Boolean>newArrayList(true), null)));
    
        assertEquals("true",
                render("${isDefined(var2)?c}",
                        avBuilder
                        .addSelectOneMatrixAv(2, null)
                        .addChildWithAvId(null, null)
                        .addColumn("answer1", null, 1d, 
                                Lists.<Boolean>newArrayList(false), null)));
    }
    
    @Test
    public void testIsDefinedFalseCase() throws Exception {
        //missing response list
        assertEquals("false",
                render("${isDefined(var1)?c}", avBuilder));
    }
    
    @Test
    public void testIsDefinedForSelectOneMatrixFalse() throws Exception {
        //missing response list
        assertEquals("false",
                render("${isDefined(var1)?c}",
                        avBuilder
                        .addSelectOneMatrixAv(1, null)));
    }
    
    @Test
    public void testIsDefinedForSelectMultiMatrixTrue() throws Exception {
        assertEquals("true",
                render("${isDefined(var1)?c}",
                        avBuilder
                        .addSelectMultiMatrixAv(1, null)
                        .addChildWithAvId(null, null)
                        .addColumn("answer1", null, 1d, 
                                Lists.<Boolean>newArrayList(true), null)));
    
        assertEquals("true",
                render("${isDefined(var2)?c}",
                        avBuilder
                        .addSelectMultiMatrixAv(2, null)
                        .addChildWithAvId(null, null)
                        .addColumn("answer1", null, 1d, 
                                Lists.<Boolean>newArrayList(false), null)));
    }
    
    @Test
    public void testIsDefinedForSelectMultiMatrixFalse() throws Exception {
        //missing response list
        assertEquals("false",
                render("${isDefined(var1)?c}",
                        avBuilder
                        .addSelectMultiMatrixAv(1, null)));
    }
    
    @Test
    public void testIsDefinedForTableTrue() throws Exception {
        assertEquals("true",
                render("${isDefined(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, true)
                            .addChildFreeText(null, "free text question", null)));
        assertEquals("true",
                render("${isDefined(var2)?c}",
                        avBuilder
                        .addTableQuestionAv(2, null, true, false)
                            .addChildFreeText(null, "free text question", null)));
        
        assertEquals("true",
                render("${isDefined(var3)?c}",
                        avBuilder
                        .addTableQuestionAv(3, null, true, null)
                            .addChildFreeText(null, "free text question", Lists.newArrayList(null, "first", null))));
    }
    
    @Test
    public void testIsDefinedForTableFalse() throws Exception {
        assertEquals("false", 
                render("${isDefined(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, null)));
    }
    
    @Test
    public void testWasAnsweredForFreeText() throws Exception {
        assertEquals("true",
                render("${wasAnswered(var1)?c}",
                        avBuilder
                        .addFreeTextAv(1, "free text question", "answer")));
        
        assertEquals("false",
                render("${wasAnswered(var2)?c}",
                        avBuilder
                        .addFreeTextAv(2, "free text question", null)));
    }
    
    @Test
    public void testWasntAnsweredForFreeText() throws Exception {
        assertEquals("false",
                render("${wasntAnswered(var1)?c}",
                        avBuilder
                        .addFreeTextAv(1, "free text question", "answer")));
        
        assertEquals("true",
                render("${wasntAnswered(var2)?c}",
                        avBuilder
                        .addFreeTextAv(2, "free text question", null)));
    }
    
    @Test
    public void testWasAnsweredForSelectOne() throws Exception {
        assertEquals("true",
                render("${wasAnswered(var1)?c}",
                        avBuilder
                        .addSelectOneAv(1, null)
                        .addAnswer("answer1", true, null)));
        
        assertEquals("false",
                render("${wasAnswered(var2)?c}",
                        avBuilder
                        .addSelectOneAv(2, null)
                        .addAnswer("answer1", false, null)));
        
        assertEquals("false",
                render("${wasAnswered(var3)?c}",
                        avBuilder
                        .addSelectOneAv(3, null)
                        .addAnswer("answer1", null, null)));
    }
    
    @Test
    public void testWasntAnsweredForSelectOne() throws Exception {
        assertEquals("false",
                render("${wasntAnswered(var1)?c}",
                        avBuilder
                        .addSelectOneAv(1, null)
                        .addAnswer("answer1", true, null)));
        
        assertEquals("true",
                render("${wasntAnswered(var2)?c}",
                        avBuilder
                        .addSelectOneAv(2, null)
                        .addAnswer("answer1", false, null)));
        
        assertEquals("true",
                render("${wasntAnswered(var3)?c}",
                        avBuilder
                        .addSelectOneAv(3, null)
                        .addAnswer("answer1", null, null)));
    }
    
    @Test
    public void testWasAnsweredForSelectMulti() throws Exception {
        assertEquals("true",
                render("${wasAnswered(var1)?c}",
                        avBuilder
                        .addSelectMultiAv(1, null)
                        .addAnswer("answer1", true, null)));
        
        assertEquals("false",
                render("${wasAnswered(var2)?c}",
                        avBuilder
                        .addSelectMultiAv(2, null)
                        .addAnswer("answer1", false, null)));
        
        assertEquals("false",
                render("${wasAnswered(var3)?c}",
                        avBuilder
                        .addSelectMultiAv(3, null)
                        .addAnswer("answer1", null, null)));
    }
    
    @Test
    public void testWasntAnsweredForSelectMulti() throws Exception {
        assertEquals("false",
                render("${wasntAnswered(var1)?c}",
                        avBuilder
                        .addSelectMultiAv(1, null)
                        .addAnswer("answer1", true, null)));
        
        assertEquals("true",
                render("${wasntAnswered(var2)?c}",
                        avBuilder
                        .addSelectMultiAv(2, null)
                        .addAnswer("answer1", false, null)));
        
        assertEquals("true",
                render("${wasntAnswered(var3)?c}",
                        avBuilder
                        .addSelectMultiAv(3, null)
                        .addAnswer("answer1", null, null)));
    }
    
    //hasResult is the only available operation for both select one and select multi matrices 
    
    @Test
    public void testFormulaHasResult() throws Exception{
        assertEquals("true",
                render("${formulaHasResult(var1)?c}",
                        avBuilder
                        .addFormulaAv(1, "2 + 2")));
        
        assertEquals("false",
                render("${formulaHasResult(var2)?c}",
                        avBuilder
                        .addFormulaAv(3, "[100]")));
    }
    
    @Test
    public void testFormulaHasNoResult() throws Exception{
        assertEquals("false",
                render("${formulaHasNoResult(var1)?c}",
                        avBuilder
                        .addFormulaAv(1, "2 + 2")));
        
        assertEquals("true",
                render("${formulaHasNoResult(var2)?c}",
                        avBuilder
                        .addFormulaAv(3, "[100]")));
    }
    
    @Test
    public void testCustomHasResult() throws Exception{
        assertEquals("true",
                render(String.format("${customHasResult(var%d)?c}", CustomAssessmentVariableResolver.CUSTOM_ASSESSMENT_DURATION),
                        avBuilder
                        .addCustomAv(CustomAssessmentVariableResolver.CUSTOM_ASSESSMENT_DURATION)
                        .setAssessmentDuration(20)));
        
        assertEquals("false",
                render(String.format("${customHasResult(var%d)?c}", CustomAssessmentVariableResolver.CUSTOM_ASSESSMENT_LAST_MODIFIED),
                        avBuilder
                        .addCustomAv(CustomAssessmentVariableResolver.CUSTOM_ASSESSMENT_LAST_MODIFIED)));
    }
    
    @Test
    public void testCustomHasNoResult() throws Exception{
        assertEquals("false",
                render(String.format("${customHasNoResult(var%d)?c}", CustomAssessmentVariableResolver.CUSTOM_ASSESSMENT_DURATION),
                        avBuilder
                        .addCustomAv(CustomAssessmentVariableResolver.CUSTOM_ASSESSMENT_DURATION)
                        .setAssessmentDuration(20)));
        
        assertEquals("true",
                render(String.format("${customHasNoResult(var%d)?c}", CustomAssessmentVariableResolver.CUSTOM_ASSESSMENT_LAST_MODIFIED),
                        avBuilder
                        .addCustomAv(CustomAssessmentVariableResolver.CUSTOM_ASSESSMENT_LAST_MODIFIED)));
    }
    
    @Test
    public void testCustomHasResultForAppointmentsTrue() throws Exception{
        assertEquals("true",
                render(String.format("${customHasResult(delimit(var%d,\"*\",\"**\",\"-\",true,\"\"))?c}", 
                        CustomAssessmentVariableResolver.CUSTOM_VETERAN_APPOINTMENTS),
                        avBuilder
                        .addCustomAv(CustomAssessmentVariableResolver.CUSTOM_VETERAN_APPOINTMENTS)
                        .addAppointment("clinic1", new Date(), "scheduled")));
    }
    
    @Test
    public void testCustomHasResultForAppointmentsFalse() throws Exception{
        assertEquals("false",
                render(String.format("${customHasResult(delimit(var%d,\"*\",\"**\",\"-\",true,\"\"))?c}", 
                        CustomAssessmentVariableResolver.CUSTOM_VETERAN_APPOINTMENTS),
                        avBuilder
                        .addCustomAv(CustomAssessmentVariableResolver.CUSTOM_VETERAN_APPOINTMENTS)));
    }
    
    @Test
    public void testCustomHasNoResultForAppointmentsTrue() throws Exception{
        assertEquals("true",
                render(String.format("${customHasNoResult(delimit(var%d,\"*\",\"**\",\"-\",true,\"\"))?c}", 
                        CustomAssessmentVariableResolver.CUSTOM_VETERAN_APPOINTMENTS),
                        avBuilder
                        .addCustomAv(CustomAssessmentVariableResolver.CUSTOM_VETERAN_APPOINTMENTS)));
    }
    
    @Test
    public void testCustomHasNoResultForAppointmentsFalse() throws Exception{
        assertEquals("false",
                render(String.format("${customHasNoResult(delimit(var%d,\"*\",\"**\",\"-\",true,\"\"))?c}", 
                        CustomAssessmentVariableResolver.CUSTOM_VETERAN_APPOINTMENTS),
                        avBuilder
                        .addCustomAv(CustomAssessmentVariableResolver.CUSTOM_VETERAN_APPOINTMENTS)
                        .addAppointment("clinic1", new Date(), "scheduled")));
    }
    
    //TODO: With lambdas the following 8 tests could use a helper method to run the tests but after an evaluation the complexity of doing it here with java 7 was not going to make it easier to maintain

    @Test
    public void testResponseIsForSelectOneUsingVariable() throws Exception{
        //this also tests isSelectedAnswer
        SelectAvBuilder builder = avBuilder
                .addSelectOneAv(1, null)
                .addAnswer("answer1", true, null);
        
        AssessmentVariable answerAv = builder.getAnswerAvs().get(0);
        
        String varFormat = "${responseIs(var1, var%d)?c}";
        String template = String.format(varFormat, answerAv.getAssessmentVariableId());
        assertEquals("true", render(template, builder));
        
        template = String.format(varFormat, 100);
        assertEquals("false", render(template, builder));
    }
    
    @Test
    public void testResponseIsForSelectOneUsingAnswerId() throws Exception{
        SelectAvBuilder builder = avBuilder
                .addSelectOneAv(1, null)
                .addAnswer("answer1", true, null);
        
        AssessmentVariable answerAv = builder.getAnswerAvs().get(0);
        
        String answerIdFormat = "${responseIs(var1, %d)?c}";
        String template = String.format(answerIdFormat, answerAv.getMeasureAnswer().getMeasureAnswerId());
        assertEquals("true", render(template, builder));
        
        template = String.format(answerIdFormat, 100);
        assertEquals("false", render(template, builder));
    }
    
    @Test
    public void testResponseIsntForSelectOneUsingVariable() throws Exception{
      //this also tests isSelectedAnswer
        SelectAvBuilder builder = avBuilder
                .addSelectOneAv(1, null)
                .addAnswer("answer1", true, null);
        
        AssessmentVariable answerAv = builder.getAnswerAvs().get(0);
        
        String varFormat = "${responseIsnt(var1, var%d)?c}";
        String template = String.format(varFormat, answerAv.getAssessmentVariableId());
        assertEquals("false", render(template, builder));
        
        template = String.format(varFormat, 100);
        assertEquals("true", render(template, builder));
    }
    
    @Test
    public void testResponseIsntForSelectOneUsingAnswerId() throws Exception{
        SelectAvBuilder builder = avBuilder
                .addSelectOneAv(1, null)
                .addAnswer("answer1", true, null);
        
        AssessmentVariable answerAv = builder.getAnswerAvs().get(0);
        
        String answerIdFormat = "${responseIsnt(var1, %d)?c}";
        String template = String.format(answerIdFormat, answerAv.getMeasureAnswer().getMeasureAnswerId());
        assertEquals("false", render(template, builder));
        
        template = String.format(answerIdFormat, 100);
        assertEquals("true", render(template, builder));
    }
    
    @Test
    public void testResponseIsForSelectMultiUsingVariable() throws Exception{
      //this also tests isSelectedAnswer
        SelectAvBuilder builder = avBuilder
                .addSelectMultiAv(1, null)
                .addAnswer("answer1", false, null)
                .addAnswer("answer2", true, null)
                .addAnswer("answer3", false, null);
        
        AssessmentVariable answerAv2 = builder.getAnswerAvs().get(1);
        AssessmentVariable answerAv3 = builder.getAnswerAvs().get(2);
        
        String varFormat = "${responseIs(var1, var%d)?c}";
        String template = String.format(varFormat, answerAv2.getAssessmentVariableId());
        assertEquals("true", render(template, builder));
        
        template = String.format(varFormat, answerAv3.getAssessmentVariableId());
        assertEquals("false", render(template, builder));
        
        template = String.format(varFormat, 100);
        assertEquals("false", render(template, builder));
    }
    
    @Test
    public void testResponseIsForSelectMultiUsingAnswerId() throws Exception{
        SelectAvBuilder builder = avBuilder
                .addSelectMultiAv(1, null)
                .addAnswer("answer1", false, null)
                .addAnswer("answer2", true, null)
                .addAnswer("answer3", false, null);
        
        AssessmentVariable answerAv2 = builder.getAnswerAvs().get(1);
        AssessmentVariable answerAv3 = builder.getAnswerAvs().get(2);
        
        String answerIdFormat = "${responseIs(var1, %d)?c}";
        String template = String.format(answerIdFormat, answerAv2.getMeasureAnswer().getMeasureAnswerId());
        assertEquals("true", render(template, builder));
        
        template = String.format(answerIdFormat, answerAv3.getMeasureAnswer().getMeasureAnswerId());
        assertEquals("false", render(template, builder));
        
        template = String.format(answerIdFormat, 100);
        assertEquals("false", render(template, builder));
    }
    
    @Test
    public void testResponseIsntForSelectMultiUsingVariable() throws Exception{
      //this also tests isSelectedAnswer
        SelectAvBuilder builder = avBuilder
                .addSelectMultiAv(1, null)
                .addAnswer("answer1", false, null)
                .addAnswer("answer2", true, null)
                .addAnswer("answer3", false, null);
        
        AssessmentVariable answerAv2 = builder.getAnswerAvs().get(1);
        AssessmentVariable answerAv3 = builder.getAnswerAvs().get(2);
        
        String varFormat = "${responseIsnt(var1, var%d)?c}";
        String template = String.format(varFormat, answerAv2.getAssessmentVariableId());
        assertEquals("false", render(template, builder));
        
        template = String.format(varFormat, answerAv3.getAssessmentVariableId());
        assertEquals("true", render(template, builder));
        
        template = String.format(varFormat, 100);
        assertEquals("true", render(template, builder));
    }
    
    @Test
    public void testResponseIsntForSelectMultiUsingAnswerId() throws Exception{
        SelectAvBuilder builder = avBuilder
                .addSelectMultiAv(1, null)
                .addAnswer("answer1", false, null)
                .addAnswer("answer2", true, null)
                .addAnswer("answer3", false, null);
        
        AssessmentVariable answerAv2 = builder.getAnswerAvs().get(1);
        AssessmentVariable answerAv3 = builder.getAnswerAvs().get(2);
        
        String answerIdFormat = "${responseIsnt(var1, %d)?c}";
        String template = String.format(answerIdFormat, answerAv2.getMeasureAnswer().getMeasureAnswerId());
        assertEquals("false", render(template, builder));
        
        template = String.format(answerIdFormat, answerAv3.getMeasureAnswer().getMeasureAnswerId());
        assertEquals("true", render(template, builder));
        
        template = String.format(answerIdFormat, 100);
        assertEquals("true", render(template, builder));
    }    
}