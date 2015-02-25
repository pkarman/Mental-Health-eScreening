package gov.va.escreening.templateprocessor;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import gov.va.escreening.test.TestAssessmentVariableBuilder;
import gov.va.escreening.variableresolver.CustomAssessmentVariableResolver;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private static DateTimeFormatter STANDARD_DATE_FORMAT = DateTimeFormat.forPattern("MM/dd/yyyy");
	
	@Test
	public void testGetResponseForFreeText() throws Exception{
		assertEquals("Bill", 
				render(avBuilder.addFreeTextAV(1, "name", "Bill"), 
						"${getResponse(var1, 1)}"));
	}
	
	@Test
	public void testDelimitTransformationForAppointments() throws Exception {
		Date firstApptTime = new DateTime().plusDays(5).toDate();
		Date secondApptTime = new DateTime().plusDays(6).toDate();
		
		assertEquals("*" + CustomAssessmentVariableResolver.APPOINTMENT_DATE_FORMAT.format(firstApptTime) + "Clinic 1-**" 
						+ CustomAssessmentVariableResolver.APPOINTMENT_DATE_FORMAT.format(secondApptTime) + "Clinic 2-", 
				render(avBuilder
						.addCustomAV(CustomAssessmentVariableResolver.CUSTOM_VETERAN_APPOINTMENTS)
						.addAppointment("Clinic 1", firstApptTime, "active")
						.addAppointment("Clinic 2", secondApptTime, "active"),
						"${delimit(var6,\"*\",\"**\",\"-\",true,\"defaultVal\")}"));
	}
	
	@Test
	public void testDelimitTransformationForAppointmentsNoResponseGiven() throws Exception {
		//test that transformation still works when no veteran response is available
		assertEquals("defaultVaLL", 
				render(avBuilder
						.addCustomAV(CustomAssessmentVariableResolver.CUSTOM_VETERAN_APPOINTMENTS),
						"${delimit(var6,\"*\",\"**\",\"-\",true,\"defaultVaLL\")}"));
	}
	
	@Test
	public void testDelimitTransformationForSelectMulti() throws Exception{
		assertEquals("*first-*third-**fourth-", 
				render(avBuilder
						.addSelectMultiAV(1, null)
						.addAnswer(null, "first", null, null, true, null)
						.addAnswer(null, "second", null, null, false, null)
						.addAnswer(null, "third", null, null, true, null)
						.addAnswer(null, "fourth", null, null, true, null),
						"${delimit(var1,\"*\",\"**\",\"-\",true,\"defaultVaLL\")}"));
	}
	
	@Test
	public void testDelimitTransformationForMultiSelectWithOtherAnswer() throws Exception{
		String otherResponse = "my other answer";
		
		assertEquals("*first-*"+ otherResponse + "-**fourth-", 
				render(avBuilder
						.addSelectMultiAV(1, null)
						.addAnswer(null, "first", null, null, true, null)
						.addAnswer(null, "second", null, null, false, null)
						.addAnswer(null, "third", "other", null, true, otherResponse)
						.addAnswer(null, "fourth", null, null, true, null),
						"${delimit(var1,\"*\",\"**\",\"-\",true,\"defaultVaLL\")}"));
	}
	
	@Test
	public void testDelimitTransformationForSelectMultiNoResponseGiven() throws Exception{
		//test that transformation still works when no veteran response is available
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
		//if no response is given default value should be used
		
	}
	
	/**    TESTS for yearsFromDate transformation  **/
	
	//Transformation takes value and emits the number of years between the veteran entered date and the assessment date
	
	@Test
	public void testYearsFromDateTranslationSameDay() throws Exception{
		DateTime date = new DateTime().minusYears(3);
		
		assertEquals("3", 
				render(avBuilder
						.addFreeTextAV(1, "dob", STANDARD_DATE_FORMAT.print(date))
						.setDataTypeValidation("date"), 
						"${yearsFromDate(var1)}"));
	}
	
	@Test
	public void testYearsFromDateTranslationDayBefore() throws Exception{
		//test before today's date which should count current year 
		DateTime date = new DateTime().minusYears(3).minusDays(1);
		
		assertEquals("3", 
				render(avBuilder
						.addFreeTextAV(1, "dob", STANDARD_DATE_FORMAT.print(date))
						.setDataTypeValidation("date"), 
						"${yearsFromDate(var1)}"));
	}
	
	@Test
	public void testYearsFromDateTranslationDayAfter() throws Exception{
		//after today's date which should not count last year as complete
		DateTime date = new DateTime().minusYears(3).plusDays(1);
		
		assertEquals("2", 
				render(avBuilder
						.addFreeTextAV(1, "dob", STANDARD_DATE_FORMAT.print(date))
						.setDataTypeValidation("date"), 
						"${yearsFromDate(var1)}"));
	}
	
	@Test
	public void testYearsFromDateTransformationNoResponseGiven() throws Exception{
		//the freemarker function should gracefully deal with not having an answer
		assertEquals(TestAssessmentVariableBuilder.DEFAULT_VALUE, 
				render(avBuilder
						.addFreeTextAV(1, "dob", null)
						.setDataTypeValidation("date"), 
						"${yearsFromDate(var1)}"));
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
	
	@Test
	public void testDelimitedMatrixQuestionsTranslationForSingleNoResponseGiven() throws Exception{
		//test that transformation still works when no veteran response is available
	}
	
	/** TESTS for numberOfEntries translation for table questions  **/
	
	@Test
	public void testNumberOfEntriesTranslation() throws Exception{
		
	}
	
	@Test
	public void testNumberOfEntriesTranslationNoResponseGiven() throws Exception{
		//test that transformation still works when no veteran response is available
	}
	
	/** TESTS for delimitTableField translation for table questions  **/
	
	@Test
	public void testDelimitTableFieldTranslation() throws Exception{
		
	}
	
	@Test
	public void testDelimitTableFieldTranslationNoResponseGiven() throws Exception{
		//test that transformation still works when no veteran response is available
	}
	
	
	//TODO: Add a test for each setting 
	
	
}
