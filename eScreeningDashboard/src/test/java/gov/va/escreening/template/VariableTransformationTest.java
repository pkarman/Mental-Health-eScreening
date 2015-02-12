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
