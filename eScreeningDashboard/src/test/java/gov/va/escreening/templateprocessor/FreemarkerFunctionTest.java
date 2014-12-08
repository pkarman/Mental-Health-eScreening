package gov.va.escreening.templateprocessor;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.ImmutableList;

import gov.va.escreening.variableresolver.AssessmentVariableDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class FreemarkerFunctionTest {

	private static TemplateProcessorServiceImpl templateService = new TemplateProcessorServiceImpl();
	
	@Test
	public void test() throws Exception{
		List<AssessmentVariableDto> assessmentVariables = ImmutableList.of(getExampleQuestionFactory(1, "test output"));
		
		String result = templateService.processTemplate("${var1}", assessmentVariables, 1);
		System.out.println("Result is: " + result);
	}
	
	//Below is an example factory which generates a AssessmentVariableDto.  What we need is an accurate factory for the following:
	// Assessment variable for freeText question (see MeasureAssessmentVariableResolverImpl for structure)
	// Assessment variable for select one question (see MeasureAssessmentVariableResolverImpl for structure)
	// Assessment variable for select multi question (see MeasureAssessmentVariableResolverImpl for structure)
	// Assessment variable for Custom variables (see CustomAssessmentVariableResolverImpl; most of them are similar but one has children)
	//
	// Then the thought is we will be able to use these factories to test the template editor freemarker functions (see the end of ClinicalNoteTemplateFunctions.ftl)
	
	private AssessmentVariableDto getExampleQuestionFactory(Integer id, String value){
		AssessmentVariableDto var = new AssessmentVariableDto();
		var.setKey("var"+id);
		var.setValue(value);
		return var;
	}
	
}
