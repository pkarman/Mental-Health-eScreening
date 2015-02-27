package gov.va.escreening.templateprocessor;

import static org.junit.Assert.assertEquals;
import gov.va.escreening.test.IntegrationTest;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/**
 * This class is concerned with the testing of freemarker functions defined for all templates.<br/>
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
                        .addSelectOneMatrixAV(123, "test matrix question")
                            .addChildQuestion(111, "q1")
                            .addChildQuestion(222, "q2")
                            .addChildQuestion(333, "q3")
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
                        .addSelectOneMatrixAV(123, "test matrix question")
                            .addChildQuestion(111, "q1")
                            .addChildQuestion(222, "q2")
                            .addChildQuestion(333, "q3")
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
                        .addSelectOneMatrixAV(123, "test matrix question")
                            .addChildQuestion(111, "q1")
                            .addChildQuestion(222, "q2")
                            .addChildQuestion(333, "q3")
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
                        .addSelectOneMatrixAV(123, "test matrix question")
                            .addChildQuestion(111, "q1")
                            .addChildQuestion(222, "q2")
                            .addChildQuestion(333, "q3")
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
                        .addSelectOneMatrixAV(123, "test matrix question")
                            .addChildQuestion(111, "q1")
                            .addChildQuestion(222, "q2")
                            .addChildQuestion(333, "q3")
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
                        .addSelectOneMatrixAV(123, "test matrix question")
                            .addChildQuestion(111, "q1")
                            .addChildQuestion(222, "q2")
                            .addChildQuestion(333, "q3")
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
                        .addSelectOneMatrixAV(123, "test matrix question")
                            .addChildQuestion(111, "q1")
                            .addChildQuestion(222, "q2")
                            .addChildQuestion(333, "q3")
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
                        .addSelectOneMatrixAV(123, "test matrix question")
                            .addChildQuestion(111, "q1")
                            .addChildQuestion(222, "q2")
                            .addChildQuestion(333, "q3")
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
                            .addChildFreeText(null, "free text question", null)));

        assertEquals("true",
                render("${wasntAnswerNone(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, false)
                            .addChildFreeText(null, "free text question", ImmutableList.of("one", "two","three"))));
    }

    @Test
    public void testTableWasAnsweredConditionForNone() throws Exception{
        //if None was selected returns true
        assertEquals("true", 
                render("${wasAnswered(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, true)
                            .addChildFreeText(null, "free text question", null)
                            .addChildSelectOne(null, "select one question")
                                .addChildSelectAnswer(null, "answer 1", null, null, null, null)
                                .addChildSelectAnswer(null, "answer 2", null, null, null, null)
                            .addChildSelectMulti(null, "select multi question")
                                .addChildSelectAnswer(null, null, null, null, null, null)
                                .addChildSelectAnswer(null, null, null, null, null, null)));
    }

    @Test
    public void testTableWasAnsweredConditionForEntry() throws Exception{
        //if an entry was added returns true
        assertEquals("true", 
                render("${wasAnswered(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, true)
                            .addChildFreeText(null, "free text question", null)
                            .addChildSelectOne(null, "select one question")
                                .addChildSelectAnswer(null, "answer 1", null, null, Lists.<Boolean>newArrayList(null, null, null), null)
                                .addChildSelectAnswer(null, "answer 2", null, null, ImmutableList.of(false, true, false), null)
                            .addChildSelectMulti(null, "select multi question")
                                .addChildSelectAnswer(null, null, null, null, null, null)
                                .addChildSelectAnswer(null, null, null, null, null, null)));

        //test freetext
        assertEquals("true", 
                render("${wasAnswered(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, true)
                            .addChildFreeText(null, "free text question", Lists.newArrayList(null, "first", null))
                            .addChildSelectOne(null, "select one question")
                                .addChildSelectAnswer(null, "answer 1", null, null, null, null)
                                .addChildSelectAnswer(null, "answer 2", null, null, null, null)
                            .addChildSelectMulti(null, "select multi question")
                                .addChildSelectAnswer(null, null, null, null, null, null)
                                .addChildSelectAnswer(null, null, null, null, null, null)));
        //test select multi
        assertEquals("true", 
                render("${wasAnswered(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, true)
                            .addChildFreeText(null, "free text question", null)
                            .addChildSelectOne(null, "select one question")
                                .addChildSelectAnswer(null, "answer 1", null, null, null, null)
                                .addChildSelectAnswer(null, "answer 2", null, null, null, null)
                            .addChildSelectMulti(null, "select multi question")
                                .addChildSelectAnswer(null, null, null, null, ImmutableList.of(false, false, false), null)
                                .addChildSelectAnswer(null, null, null, null, ImmutableList.of(false, true, false), null)));
    }

    @Test
    public void testTableWasAnsweredConditionForEntrySelectHasAllFalse() throws Exception{
        //if all select answers are false then it was not answered
        assertEquals("false", 
                render("${wasAnswered(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, false)
                            .addChildFreeText(null, "free text question", null)
                            .addChildSelectOne(null, "select one question")
                                .addChildSelectAnswer(null, "answer 1", null, null, Lists.<Boolean>newArrayList(null, null, null), null)
                                .addChildSelectAnswer(null, "answer 2", null, null, ImmutableList.of(false, false, false), null)
                            .addChildSelectMulti(null, "select multi question")
                                .addChildSelectAnswer(null, null, null, null, null, null)
                                .addChildSelectAnswer(null, null, null, null, null, null)));

        //check select multi
        assertEquals("false", 
                render("${wasAnswered(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, false)
                            .addChildFreeText(null, "free text question", null)
                            .addChildSelectOne(null, "select one question")
                                .addChildSelectAnswer(null, "answer 1", null, null, null, null)
                                .addChildSelectAnswer(null, "answer 2", null, null, null, null)
                            .addChildSelectMulti(null, "select multi question")
                                .addChildSelectAnswer(null, null, null, null, ImmutableList.of(false, false, false), null)
                                .addChildSelectAnswer(null, null, null, null, ImmutableList.of(false, false, false), null)));
    }

    @Test
    public void testTableWasAnsweredConditionForFalse() throws Exception{
        //if no entries and no None answer was selected returns false
        assertEquals("false", 
                render("${wasAnswered(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, false)
                            .addChildFreeText(null, "free text question", null)
                            .addChildSelectOne(null, "select one question")
                                .addChildSelectAnswer(null, "answer 1", null, null, Lists.<Boolean>newArrayList(null, null, null), null)
                                .addChildSelectAnswer(null, "answer 2", null, null, Lists.<Boolean>newArrayList(null, null, null), null)
                            .addChildSelectMulti(null, "select multi question")
                                .addChildSelectAnswer(null, null, null, null, null, null)
                                .addChildSelectAnswer(null, null, null, null, null, null)));
    }		

    @Test
    public void testTableWasntAnsweredConditionForNone() throws Exception{
        //if None was selected returns false
        assertEquals("false", 
                render("${wasntAnswered(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, true)
                            .addChildFreeText(null, "free text question", null)
                            .addChildSelectOne(null, "select one question")
                                .addChildSelectAnswer(null, "answer 1", null, null, null, null)
                                .addChildSelectAnswer(null, "answer 2", null, null, null, null)
                            .addChildSelectMulti(null, "select multi question")
                                .addChildSelectAnswer(null, null, null, null, null, null)
                                .addChildSelectAnswer(null, null, null, null, null, null)));
    }

    @Test
    public void testTableWasntAnsweredConditionForEntry() throws Exception{
        //if an entry was added returns false
        assertEquals("false", 
                render("${wasntAnswered(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, true)
                            .addChildFreeText(null, "free text question", null)
                            .addChildSelectOne(null, "select one question")
                                .addChildSelectAnswer(null, "answer 1", null, null, Lists.<Boolean>newArrayList(null, null, null), null)
                                .addChildSelectAnswer(null, "answer 2", null, null, ImmutableList.of(false, true, false), null)
                            .addChildSelectMulti(null, "select multi question")
                                .addChildSelectAnswer(null, null, null, null, null, null)
                                .addChildSelectAnswer(null, null, null, null, null, null)));

        //test freetext
        assertEquals("false", 
                render("${wasntAnswered(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, true)
                            .addChildFreeText(null, "free text question", Lists.newArrayList(null, "first", null))
                            .addChildSelectOne(null, "select one question")
                                .addChildSelectAnswer(null, "answer 1", null, null, null, null)
                                .addChildSelectAnswer(null, "answer 2", null, null, null, null)
                            .addChildSelectMulti(null, "select multi question")
                                .addChildSelectAnswer(null, null, null, null, null, null)
                                .addChildSelectAnswer(null, null, null, null, null, null)));
        //test select multi
        assertEquals("false", 
                render("${wasntAnswered(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, true)
                            .addChildFreeText(null, "free text question", null)
                            .addChildSelectOne(null, "select one question")
                                .addChildSelectAnswer(null, "answer 1", null, null, null, null)
                                .addChildSelectAnswer(null, "answer 2", null, null, null, null)
                            .addChildSelectMulti(null, "select multi question")
                                .addChildSelectAnswer(null, null, null, null, ImmutableList.of(false, false, false), null)
                                .addChildSelectAnswer(null, null, null, null, ImmutableList.of(false, true, false), null)));
    }		

    @Test
    public void testTableWasntAnsweredConditionForFalse() throws Exception{
        //if no entries and no None answer was selected returns true
        assertEquals("true", 
                render("${wasntAnswered(var1)?c}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, false)
                            .addChildFreeText(null, "free text question", Lists.<String>newArrayList(null, null, null))
                            .addChildSelectOne(null, "select one question")
                                .addChildSelectAnswer(null, "answer 1", null, null, Lists.<Boolean>newArrayList(null, null, null), null)
                                .addChildSelectAnswer(null, "answer 2", null, null, Lists.<Boolean>newArrayList(null, null, null), null)
                            .addChildSelectMulti(null, "select multi question")
                                .addChildSelectAnswer(null, null, null, null, Lists.<Boolean>newArrayList(null, null, null), null)
                                .addChildSelectAnswer(null, null, null, null, Lists.<Boolean>newArrayList(null, null, null), null)));
    }
}
