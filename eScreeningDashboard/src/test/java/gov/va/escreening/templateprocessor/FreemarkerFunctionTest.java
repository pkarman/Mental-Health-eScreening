package gov.va.escreening.templateprocessor;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import freemarker.template.TemplateException;
import gov.va.escreening.test.AssessmentVariableBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class FreemarkerFunctionTest {
	private static final Logger logger = LoggerFactory.getLogger(FreemarkerFunctionTest.class);
	private static TemplateProcessorServiceImpl templateService = new TemplateProcessorServiceImpl();
	
	private AssessmentVariableBuilder avBuilder;
	
    @Before
    public void setUp() {
    	avBuilder = new AssessmentVariableBuilder();
    }
	
	@Test
	public void testGetResponseForFreeText() throws Exception{
		assertEquals("Bill", 
				render(avBuilder.addFreeTextAV(1, "name", "Bill"), 
						"${getResponse(var1, 1)}"));
	}

	private String render(AssessmentVariableBuilder avBuilder, String templateText) throws IOException, TemplateException{
		return templateService.processTemplate(
				"<#include \"clinicalnotefunctions\">" + templateText, 
				avBuilder.getDTOs(), 1);
	}
	
}
