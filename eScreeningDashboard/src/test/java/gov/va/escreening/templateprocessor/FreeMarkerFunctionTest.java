package gov.va.escreening.templateprocessor;

import java.io.IOException;

import freemarker.template.TemplateException;
import gov.va.escreening.test.AssessmentVariableBuilder;
import gov.va.escreening.test.TestAssessmentVariableBuilder;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FreeMarkerFunctionTest {
	private static final Logger logger = LoggerFactory.getLogger(FreeMarkerFunctionTest.class);
	
	protected TestAssessmentVariableBuilder avBuilder;
	
    @Before
    public void setUp() {
    	avBuilder = new TestAssessmentVariableBuilder();
    }

	protected String render(String templateText, AssessmentVariableBuilder avBuilder) throws IOException, TemplateException{
		//creating a new processor every time to avoid caching of templates
		String result = new TemplateProcessorServiceImpl().processTemplate(
				"<#include \"clinicalnotefunctions\">" + templateText, 
				avBuilder.getDTOs(), 1);
		
		logger.debug("template rendered:\n" + result);
		
		return result;
	}
		
}
