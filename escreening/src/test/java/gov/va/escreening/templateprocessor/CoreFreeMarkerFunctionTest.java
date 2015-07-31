package gov.va.escreening.templateprocessor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import freemarker.template.TemplateException;
import gov.va.escreening.entity.User;
import gov.va.escreening.expressionevaluator.ExpressionExtentionUtil;
import gov.va.escreening.test.IntegrationTest;
import gov.va.escreening.variableresolver.AssessmentVariableDto;
import gov.va.escreening.variableresolver.CustomAssessmentVariableResolver;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.google.common.collect.ImmutableList;

/**
 * This class is concerned with the testing of core freemarker functions defined 
 * used by templates.<br/>
 * This is an integration test.<br/>
 * 
 * @author Robin Carnow
 */
@Category(IntegrationTest.class)
@RunWith(JUnit4.class)
public class CoreFreeMarkerFunctionTest extends FreeMarkerFunctionTest {
   
    @Test
    public void testGetFreeTextAnswer() throws Exception {
        assertEquals("Patagonia",
                render("${getFreeTextAnswer(var1)}",
                        avBuilder
                        .addFreeTextAv(1, "free text question", "Patagonia")));
        
        assertEquals(ExpressionExtentionUtil.DEFAULT_VALUE,
                render("${getFreeTextAnswer(var1)}",
                        avBuilder
                        .addFreeTextAv(1, "free text question", "")));
    }
  
    @Test
    public void testAsNumberForFreeText() throws Exception {
        assertEquals("12",
                render("${asNumber(var1)}",
                        avBuilder
                        .addFreeTextAv(1, "free text question", "12")));
    }
    
    @Test
    public void testAsNumberForSelectOne() throws Exception {
        assertEquals("4",
                render("${asNumber(var1)}",
                        avBuilder
                        .addSelectOneAv(1, null)
                        .addAnswer(null, null, "answer", null, 2d, false, null)
                        .addAnswer(null, null, "answer", null, 4d, true, null)
                        .addAnswer(null, null, "answer", null, 8d, false, null)));
    }
    
    @Test
    public void testAsNumberForSelectMulti() throws Exception {
        assertEquals("10",
                render("${asNumber(var1)}",
                        avBuilder
                        .addSelectMultiAv(1, null)
                        .addAnswer(null, null, "answer", null, 10d, false, null)
                        .addAnswer(null, null, "answer", null, 4d, true, null)
                        .addAnswer(null, null, "answer", null, 4d, true, null)
                        .addAnswer(null, null, "answer", null, 10d, false, null)
                        .addAnswer(null, null, "answer", null, 2d, true, null)));
    }
    
    @Test
    public void testGetResponseForFreeTextNoAnswer() throws Exception {
        assertEquals(ExpressionExtentionUtil.DEFAULT_VALUE,
                render("${getResponse(var1)}",
                        avBuilder
                        .addFreeTextAv(1, "free text question", null)));
    }
    
    @Test
    public void testGetResponseForFreeText() throws Exception {
        assertEquals("didactic",
                render("${getResponse(var2)}",
                        avBuilder
                        .addFreeTextAv(2, "free text question", "didactic")));
    }
    
    @Test
    public void testGetResponseForSelectOneNoAnswer() throws Exception {
        assertEquals(ExpressionExtentionUtil.DEFAULT_VALUE,
                render("${getResponse(var1)}",
                        avBuilder
                        .addSelectOneAv(1, null)
                        .addAnswer(null, null, "answer1", null, 2d, false, null)
                        .addAnswer(null, null, "answer2", null, 4d, false, null)
                        .addAnswer(null, null, "answer3", null, 8d, false, null)));
    }
    
    @Test
    public void testGetResponseForSelectOne() throws Exception {
        assertEquals("answer2",
                render("${getResponse(var1)}",
                        avBuilder
                        .addSelectOneAv(1, null)
                        .addAnswer(null, null, "answer1", null, 2d, false, null)
                        .addAnswer(null, null, "answer2", null, 4d, true, null)
                        .addAnswer(null, null, "answer3", null, 8d, false, null)));
    }
    
    @Test
    public void testGetResponseForSelectMultiNoAnswer() throws Exception {
        assertEquals(ExpressionExtentionUtil.DEFAULT_VALUE,
                render("${getResponse(var1)}",
                        avBuilder
                        .addSelectMultiAv(1, null)
                        .addAnswer(null, null, "answer1", null, 10d, false, null)
                        .addAnswer(null, null, "answer2", null, 4d, false, null)
                        .addAnswer(null, null, "answer3", null, 4d, false, null)));
    }
    
    @Test
    public void testGetResponseForSelectMultiTwoAnswers() throws Exception {
        //this should be a comma delimited list
        assertEquals("answer2, and answer4",
                render("${getResponse(var1)}",
                        avBuilder
                        .addSelectMultiAv(1, null)
                        .addAnswer(null, null, "answer1", null, 10d, false, null)
                        .addAnswer(null, null, "answer2", null, 4d, true, null)
                        .addAnswer(null, null, "answer3", null, 4d, false, null)
                        .addAnswer(null, null, "answer4", null, 10d, true, null)
                        .addAnswer(null, null, "answer5", null, 2d, false, null)));
    }
    
    @Test
    public void testGetResponseForSelectMultiTwoAnswersSingleAnswer() throws Exception {
        //should only show the one value, not commas or "and"
        assertEquals("answer4",
                render("${getResponse(var1)}",
                        avBuilder
                        .addSelectMultiAv(1, null)
                        .addAnswer(null, null, "answer1", null, 10d, false, null)
                        .addAnswer(null, null, "answer2", null, 4d, false, null)
                        .addAnswer(null, null, "answer3", null, 4d, false, null)
                        .addAnswer(null, null, "answer4", null, 10d, true, null)
                        .addAnswer(null, null, "answer5", null, 2d, false, null)));        
    }
    
    @Test
    public void testGetResponseForUnsupportedType() throws Exception {
        //should output [Error: unsupported question type]
        assertEquals("[Error: unsupported question type]",
                render("${getResponse(var1)}",
                        avBuilder
                        .addTableQuestionAv(1, null, true, true)
                            .addChildFreeText(null, "free text question", ImmutableList.of("vivacious"))));
    }

    @Test
    public void testGetCustomValue() throws Exception {
        assertEquals("00:00:20",
                render(String.format("${getCustomValue(var%d)}", CustomAssessmentVariableResolver.CUSTOM_ASSESSMENT_DURATION),
                        avBuilder
                        .addCustomAv(CustomAssessmentVariableResolver.CUSTOM_ASSESSMENT_DURATION)
                        .setAssessmentDuration(20)));
    }
    
    @Test
    public void testGetFormulaValue() throws Exception {
        assertEquals("4",
                render("${getFormulaValue(var1)}",
                        avBuilder
                        .addFormulaAv(1, "2 + 2")));
    }
}
