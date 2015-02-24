package gov.va.escreening.templateprocessor;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import freemarker.template.TemplateException;
import gov.va.escreening.test.AssessmentVariableBuilder;

/**
 * This class is concerned with testing Freemarker functions used for Assessment Variable transformations<br/>
 * This is an integration test.<br/>
 * 
 * @author Robin Carnow
 *
 */
@RunWith(JUnit4.class)
public class TransformationFreeMarkerFunctionTest extends FreeMarkerFunctionTest{
	private static final Logger logger = LoggerFactory.getLogger(TransformationFreeMarkerFunctionTest.class);
	
	@Test
	public void testGetResponseForFreeText() throws Exception{
		assertEquals("Bill", 
				render(avBuilder.addFreeTextAV(1, "name", "Bill"), 
						"${getResponse(var1, 1)}"));
	}
	
	@Test
	public void testDelimitTransformationForAppointments() throws Exception {
				
	}
	
	@Test
	public void testDelimitTransformationForSelectMulti() throws Exception{
		
	}
	
	@Test
	public void testDelimitTransformationForMultiSelectWithOtherAnswer() throws Exception{
		
	}
	
	@Test
	public void testDelimitTransformationForSingleValueCase() throws Exception{
		//when there is only one value the prefix, lastPrefix, and suffix should never be returned
		
	}
	
	/**    TESTS for delimit transformation settings    **/
	
	@Test
	public void testDelimitTranslationForPrefix() throws Exception{
		
	}
	
	@Test
	public void testDelimitTranslationForLastPrefix() throws Exception{
		
	}
	
	@Test
	public void testDelimitTranslationForSuffix() throws Exception{
		
	}
	
	@Test
	public void testDelimitTranslationForSuffixAfterLastEntry() throws Exception{
		
	}
	
	@Test
	public void testDelimitTranslationForDefaultValue() throws Exception{
		
	}
	
	/**    TESTS for yearsFromDate transformation  **/
	
	//Transformation takes value and emits the number of years between the veteran entered date and the assessment date
	
	@Test
	public void testYearsFromDateTranslation() throws Exception{
		//test before today's date, on today's date and after today's date 
	}
	
	@Test
	public void testYearsFromDateTranslationRendering() throws Exception{
		//we have to ensure that the correct date is being calculated
	}

	/** TESTS for delimitedMatrixQuestions translation Select-One and Select-Multi Matrix Questions  **/
	
	@Test
	public void testDelimitedMatrixQuestionsTranslationForSelectOneMatrix() throws Exception{
		
	}
	
	@Test
	public void testDelimitedMatrixQuestionsTranslationForSelectMultiMatrix() throws Exception{
		
	}
	
	@Test
	public void testDelimitedMatrixQuestionsTranslationForSingleValueOutputCase() throws Exception{
		
	}
	
	/** TESTS for numberOfEntries translation for table questions  **/
	
	@Test
	public void testNumberOfEntriesTranslation() throws Exception{
		
	}
	
	/** TESTS for delimitTableField translation for table questions  **/
	
	@Test
	public void testDelimitTableFieldTranslation() throws Exception{
		
	}
	
	//TODO: Add a test for each setting 
	
	
	private String render(AssessmentVariableBuilder avBuilder, String templateText) throws IOException, TemplateException{
		return templateService.processTemplate(
				"<#include \"clinicalnotefunctions\">" + templateText, 
				avBuilder.getDTOs(), 1);
	}
	
}
