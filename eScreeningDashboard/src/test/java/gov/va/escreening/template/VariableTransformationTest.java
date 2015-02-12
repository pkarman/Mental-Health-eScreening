package gov.va.escreening.template;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import gov.va.escreening.dto.template.INode;
import gov.va.escreening.dto.template.TemplateAssessmentVariableDTO;
import gov.va.escreening.dto.template.TemplateBaseContent;
import gov.va.escreening.dto.template.TemplateFileDTO;
import gov.va.escreening.dto.template.TemplateTextContent;
import gov.va.escreening.dto.template.TemplateTextDTO;
import gov.va.escreening.dto.template.TemplateVariableContent;
import gov.va.escreening.dto.template.VariableTransformationDTO;
import gov.va.escreening.entity.Template;
import gov.va.escreening.repository.TemplateRepository;
import gov.va.escreening.variableresolver.AssessmentVariableDto;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class VariableTransformationTest {

		private static final Logger logger = LoggerFactory.getLogger(VariableTransformationTest.class);
		@Resource
		TemplateRepository templateRepository;
		
		
		/**     TESTS for delimit transformation used on each supported question type     **/
		
		@Test
		public void testDelimitTranslationForAppointments() throws Exception	{
					
			VariableTransformationDTO transformation = createTransformation("delimit", "p", "lp", "s", "true", "defaultVal");
			
			TemplateAssessmentVariableDTO variable = mock(TemplateAssessmentVariableDTO.class);
			when(variable.getTransformations()).thenReturn(Lists.newArrayList(transformation));
			
			TemplateVariableContent varContent = new TemplateVariableContent();
			varContent.setContent(variable);
			
			String translation = TemplateBaseContent.translate(null, varContent, null, new HashSet<Integer>());
			
			System.out.println(translation);
			
			//ObjectMapper om = new ObjectMapper();
			//logger.warn(om.writeValueAsString(fileDTO));
			//.readValue(fixture("fixtures/person.json"), Person.class)
		}
		
		@Test
		public void testDelimitTranslationForSelectMulti() throws Exception{
			
		}
		
		@Test
		public void testDelimitTranslationForMultiSelectWithOtherAnswer() throws Exception{
			
		}
		
		@Test
		public void testDelimitTranslationForSingleValueCase() throws Exception{
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
		
		
		/** TESTS condition translation into freemarker **/
		@Test
		public void testMatrixHasResultConditionFreemarkerTranslation() throws Exception{
			//should use: matrixHasResult
		}
		
		@Test
		public void testMatrixHasNoResultConditionFreemarkerTranslation() throws Exception{
			//should use: matrixHasNoResult
		}
		
		@Test
		public void testTableWasAnsweredConditionFreemarkerTranslation() throws Exception{
			//should use: wasAnswered
		}
		
		@Test
		public void testTableWasntAnsweredConditionFreemarkerTranslation() throws Exception{
			//should use: wasntAnswerd
		}
		
		@Test
		public void testTableWasAnswerNoneConditionFreemarkerTranslation() throws Exception{
			//should use: wasAnswerNone
		}
		
		@Test
		public void testWasntAnswerNoneConditionFreemarkerTranslation() throws Exception{
			//should use: wasntAnswerNone
		}
		
		
		/** TESTS condition logic **/
		@Test
		public void testMatrixHasResultConditionNull() throws Exception{
			//if null return false
			
		}
		
		@Test
		public void testMatrixHasResultConditionEmptyString() throws Exception{
			//if empty return false
		}
		
		@Test
		public void testMatrixHasResultConditionValue() throws Exception{
			//if has at least one char then true
		}
		
		@Test
		public void testMatrixHasNoResultConditionNull() throws Exception{
			//if null return true
			
		}
		
		@Test
		public void testMatrixHasNoResultConditionEmptyString() throws Exception{
			//if empty return true
		}
		
		@Test
		public void testMatrixHasNoResultConditionValue() throws Exception{
			//if has at least one char then false
		}
		
		@Test
		public void testTableWasAnsweredConditionForNone() throws Exception{
			//if None was selected returns true
		}
		
		@Test
		public void testTableWasAnsweredConditionForEntry() throws Exception{
			//if an entry was added returns true
		}		
		
		@Test
		public void testTableWasAnsweredConditionForFalse() throws Exception{
			//if no entries and no None answer was selected returns false
		}		
		
		@Test
		public void testTableWasntAnsweredConditionForNone() throws Exception{
			//if None was selected returns false
		}
		
		@Test
		public void testTableWasntAnsweredConditionForEntry() throws Exception{
			//if an entry was added returns false
		}		
		
		@Test
		public void testTableWasntAnsweredConditionForFalse() throws Exception{
			//if no entries and no None answer was selected returns true
		}	
		
		
		
		private VariableTransformationDTO createTransformation(String name, String... params){
			VariableTransformationDTO transformation = new VariableTransformationDTO();
			transformation.setName(name);
			transformation.setParams(ImmutableList.copyOf(params));
			return transformation;
		}
		
		private TemplateFileDTO createTemplateFile(TemplateVariableContent content){
			
			TemplateFileDTO fileDTO = new TemplateFileDTO();
			fileDTO.setId(40);
			fileDTO.setIsGraphical(false);
			fileDTO.setBlocks(new ArrayList<INode>());
			
			TemplateTextDTO text1 = new TemplateTextDTO();
			text1.setName("Depression Screening");
			text1.setSection("1.");
			text1.setContents(new ArrayList<TemplateBaseContent>());
			
			text1.getContents().add(content);
			fileDTO.getBlocks().add(text1);
			
			return fileDTO;
		}		
}
