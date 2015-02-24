package gov.va.escreening.templateprocessor;

import gov.va.escreening.test.AssessmentVariableBuilder;

import org.junit.Before;

public class FreeMarkerFunctionTest {

	protected static final TemplateProcessorServiceImpl templateService = new TemplateProcessorServiceImpl();
	protected AssessmentVariableBuilder avBuilder;
	
    @Before
    public void setUp() {
    	avBuilder = new AssessmentVariableBuilder();
    }
	
}
