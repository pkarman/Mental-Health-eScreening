package gov.va.escreening.controller;

import gov.va.escreening.exception.ErrorResponseCheckedException;
import gov.va.escreening.exception.ErrorResponseRuntimeException;
import gov.va.escreening.exception.ReferencedFormulaMissingException;
import gov.va.escreening.exception.ReferencedVariableMissingException;
import gov.va.escreening.expressionevaluator.ExpressionEvaluatorService;
import gov.va.escreening.expressionevaluator.FormulaDto;
import gov.va.escreening.templateprocessor.TemplateProcessorService;
import gov.va.escreening.variableresolver.VariableResolverService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/dashboard")
public class VariableResolverTestController {
    private static final Logger logger = LoggerFactory.getLogger(VariableResolverTestController.class);

    @Autowired
    private ExpressionEvaluatorService expressionEvaluatorService;
    @Autowired
    private VariableResolverService variableResolverService;
    @Autowired
    private TemplateProcessorService templateProcessorService; 
        
    //@RequestMapping(value = "/templatetest", method = RequestMethod.GET)
//    public String templateTestCall() {
//    	logger.debug("In templateTestCall");
//    	
//    	Integer veteranAssessmentId = 49;
//    	Integer templateId = 1;
//    	String output = null;
//    	try {
//    		output = templateProcessorService.processTemplate(templateId, veteranAssessmentId);
//    		logger.debug(String.format("Successfully applied templateid: %s for assessmentid: %s, the result was: %s", 
//    			templateId, veteranAssessmentId, output));
//    	}
//    	catch(ErrorResponseRuntimeException e) {
//    		logger.error(e.getErrorResponse().getLogMessage());
//    	}
//        catch (ErrorResponseCheckedException e) {
//            logger.error(e.getErrorResponse().getLogMessage());
//        }
//    	
//    	return "notesBackendTestPage";
//    }
    
    //@RequestMapping(value = "/variableresolvertest", method = RequestMethod.GET)
    public String variableResolverTestCall() {
    	logger.debug("In variableresolvertest");
    	
    	Integer veteranAssessmentId = 49;
    	Integer templateId = 1;
//    	List<AssessmentVariableDto> assessmentVariables = 
//    			variableResolverService.resolveVariablesForTemplateAssessment(veteranAssessmentId, templateId);
//    	
    	return "notesBackendTestPage";
    }
    
    //@RequestMapping(value = "/expressionTest", method = RequestMethod.GET)
    public String expressionTestCall() {
    	logger.debug("In expressionTestCall");

    	FormulaDto testFormula = new FormulaDto();
    	testFormula.setExpressionTemplate("( [$1$] ) + ( [$2$] ) + [1] + [2]");
    	testFormula.getVariableValueMap().put(1, "1");
    	testFormula.getVariableValueMap().put(2, "2");  
    	testFormula.getVariableValueMap().put(3, "3");    	
    	testFormula.getVariableValueMap().put(4, "4");
    	testFormula.getVariableValueMap().put(5, "5");
    	testFormula.getVariableValueMap().put(6, "6");  
    	testFormula.getVariableValueMap().put(7, "7");
    	testFormula.getVariableValueMap().put(8, "8");
    	testFormula.getChildFormulaMap().put(1, "( [$3$] ) + [5] + [6]");
    	testFormula.getChildFormulaMap().put(2, "[7] + [8]");
    	testFormula.getChildFormulaMap().put(3, "[3] + [4]");
    	//String resultOne = expressionEvaluatorService.evaluateFormula(testFormula);

    	try {
    		FormulaDto missingFormula = new FormulaDto();
    		missingFormula.setExpressionTemplate("( [$1$] ) + ( [$2$] ) + [1] + [2]");
    		missingFormula.getVariableValueMap().put(1, "1");
    		missingFormula.getVariableValueMap().put(2, "2");  
    		missingFormula.getVariableValueMap().put(3, "3");
    		missingFormula.getVariableValueMap().put(5, "5");
    		missingFormula.getVariableValueMap().put(6, "6");    		
    		missingFormula.getChildFormulaMap().put(1, "[5] + [6]");
    		//formula 2 is missing to test the exception being thrown
    		//String result = expressionEvaluatorService.evaluateFormula(missingFormula);
    	}
    	catch(ReferencedFormulaMissingException rfme) {
    		logger.error("Got expected exception of: " + rfme.getMessage());
    	}
    	
    	try {
    		FormulaDto missingVariable = new FormulaDto();
    		missingVariable.setExpressionTemplate(" [1] + [2]");
    		missingVariable.getVariableValueMap().put(2, "2");  
    		//variable 1 is missing to test the exception being thrown
    		//String result = expressionEvaluatorService.evaluateFormula(missingVariable);
    	}
    	catch(ReferencedVariableMissingException rvme) {
    		logger.error("Got expected exception of: " + rvme.getMessage());
    	}
    	
    	return "notesBackendTestPage";
    }
}
