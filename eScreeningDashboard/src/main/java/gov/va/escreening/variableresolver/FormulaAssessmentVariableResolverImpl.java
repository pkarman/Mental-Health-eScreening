package gov.va.escreening.variableresolver;

import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.entity.AssessmentVarChildren;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.exception.CouldNotResolveVariableException;
import gov.va.escreening.exception.CouldNotResolveVariableValueException;
import gov.va.escreening.exception.ErrorResponseRuntimeException;
import gov.va.escreening.exception.ReferencedFormulaMissingException;
import gov.va.escreening.exception.ReferencedVariableMissingException;
import gov.va.escreening.expressionevaluator.ExpressionEvaluatorService;
import gov.va.escreening.expressionevaluator.FormulaDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class FormulaAssessmentVariableResolverImpl implements FormulaAssessmentVariableResolver {

	@Autowired
	private CustomAssessmentVariableResolver customVariableResolver;
    @Autowired
    private ExpressionEvaluatorService expressionEvaluatorService;
	@Autowired
	private MeasureAnswerAssessmentVariableResolver measureAnswerVariableResolver;
	@Autowired
	private MeasureAssessmentVariableResolver measureVariableResolver;
	
    private static final Logger logger = LoggerFactory.getLogger(FormulaAssessmentVariableResolverImpl.class);
    
    @Override
	public AssessmentVariableDto resolveAssessmentVariable(AssessmentVariable assessmentVariable,
			Integer veteranAssessmentId, Map<Integer, AssessmentVariable> measureAnswerHash) {
    	
    	List<AssessmentVariable> answerTypeList = new ArrayList<AssessmentVariable>();
    	List<AssessmentVariable> measureTypeList = new ArrayList<AssessmentVariable>();
    	List<AssessmentVariable> customTypeList = new ArrayList<AssessmentVariable>();
    	List<AssessmentVariable> formulaTypeList = new ArrayList<AssessmentVariable>();
    	sortAssessmentVariablesByType(assessmentVariable, answerTypeList, measureTypeList, customTypeList, formulaTypeList);
    	
    	FormulaDto rootFormula = new FormulaDto();
    	
    	AssessmentVariableDto variableDto = null;
    	try{
        	if(assessmentVariable.getFormulaTemplate() == null || assessmentVariable.getFormulaTemplate().isEmpty())
        		throw new CouldNotResolveVariableException(String.format("Variable of type formula did not contain the required formulatempalte field"
        				+ " assessmentVariableId: %s, assessmentId: %s", assessmentVariable.getAssessmentVariableId(), veteranAssessmentId));
        	rootFormula.setExpressionTemplate(assessmentVariable.getFormulaTemplate());
    		
        	for(AssessmentVariable answerVariable : answerTypeList) {
   	    		String value = measureAnswerVariableResolver.resolveCalculationValue(answerVariable, veteranAssessmentId);
   	    		rootFormula.getVariableValueMap().put(answerVariable.getAssessmentVariableId(), value);
        	}
        	
        	for(AssessmentVariable measureVariable : measureTypeList) {
        		String value = measureVariableResolver.resolveCalculationValue(measureVariable, veteranAssessmentId, measureAnswerHash);
    	    	rootFormula.getVariableValueMap().put(measureVariable.getAssessmentVariableId(), value);
        	}    	

        	if(customTypeList.size() > 0) 
        		throw new UnsupportedOperationException("Resolve calcuation value for variable type Custom has not been implemented.");
        	
        	//iterate the list of formulas and add them to the object
        	for(AssessmentVariable formulaVariable : formulaTypeList) {
        		int id = formulaVariable.getAssessmentVariableId();
        		String template = formulaVariable.getFormulaTemplate();
        		rootFormula.getChildFormulaMap().put(id, template);
        	} 
        	
        	String result = expressionEvaluatorService.evaluateFormula(rootFormula);
        	
        	variableDto = createAssessmentVariableDto(assessmentVariable.getAssessmentVariableId(), result);	
    	}
    	//warnings, these exceptions typically mean that a question was not answered
    	catch(CouldNotResolveVariableValueException cnrvve) { 
    		throw new CouldNotResolveVariableException(getCouldNotResolveMessage(assessmentVariable.getAssessmentVariableId(), veteranAssessmentId, cnrvve.getMessage()));
    	}
    	catch(CouldNotResolveVariableException cnrve) {
    		throw new CouldNotResolveVariableException(getCouldNotResolveMessage(assessmentVariable.getAssessmentVariableId(), veteranAssessmentId, cnrve.getMessage()));
    	}
    	//These are errors of either a missing data or logic nature. Missing expected questions, answers, formulas, or mapping logic missing, ect...
    	catch(NoSuchMethodException nsme) {
    		logger.error("NoSuchMethodException: " + nsme.getMessage());
    		throw new CouldNotResolveVariableException(getCouldNotResolveMessage(assessmentVariable.getAssessmentVariableId(), veteranAssessmentId, nsme.getMessage()));
    	}
    	catch(ReferencedVariableMissingException rvme){
    		logger.error("ReferencedVariableMissingException: " + rvme.getMessage());
    		throw new CouldNotResolveVariableException(getCouldNotResolveMessage(assessmentVariable.getAssessmentVariableId(), veteranAssessmentId, rvme.getMessage()));
    	}
    	catch(ReferencedFormulaMissingException rfme){
    		logger.error("ReferencedFormulaMissingException: " + rfme.getMessage());
    		throw new CouldNotResolveVariableException(getCouldNotResolveMessage(assessmentVariable.getAssessmentVariableId(), veteranAssessmentId, rfme.getMessage()));
    	}
    	catch(ErrorResponseRuntimeException erre){
    		logger.error(erre.getErrorResponse().getLogMessage());
    		throw new CouldNotResolveVariableException(getCouldNotResolveMessage(assessmentVariable.getAssessmentVariableId(), veteranAssessmentId, erre.getErrorResponse().getDeveloperMessage()));
    	}
    	catch(UnsupportedOperationException uoe){
    		logger.error("UnsupportedOperationException: " + uoe.getMessage());
    		throw new CouldNotResolveVariableException(getCouldNotResolveMessage(assessmentVariable.getAssessmentVariableId(), veteranAssessmentId, uoe.getMessage()));
    	}
    	catch(Exception e) {
    		logger.error("Exception: ", e);
    		throw new CouldNotResolveVariableException(getCouldNotResolveMessage(assessmentVariable.getAssessmentVariableId(), veteranAssessmentId, e.getMessage()));
    	}
    	return variableDto;
    }

	// new AssessmentVariable(10, "var10", "string", "formula_10", "25", "25", null, null)
    private AssessmentVariableDto createAssessmentVariableDto(int assessmentVariableId, String resolvedValue) {
    	Integer id = assessmentVariableId;
    	String key = String.format("var%s", id);
    	String type = "string";
    	String name = String.format("formula_%s", id);
    	AssessmentVariableDto variableDto = new AssessmentVariableDto(id, key, type, name, resolvedValue, resolvedValue, null, null, AssessmentConstants.ASSESSMENT_VARIABLE_DEFAULT_COLUMN);
    	return variableDto;
    }
    
    private String getCouldNotResolveMessage(int assessmentVariableId, int assessmentId, String originalExceptionMsg) {
    	String errorMsg = String.format("Could not resolve all of the required criteria for the formula.  AssessmentVariableId: %s, assessmentid: %s, message: %s", 
    		assessmentVariableId, assessmentId, originalExceptionMsg);
    	return errorMsg;
    }
    
    //recursive method to sort by variable type and get dependencies for child formulas
    private void sortAssessmentVariablesByType(AssessmentVariable assessmentVariable, List<AssessmentVariable> answerTypeList,  
    		List<AssessmentVariable> measureTypeList, List<AssessmentVariable> customTypeList, List<AssessmentVariable> formulaTypeList) {
    	
    	List<AssessmentVarChildren> formulaDependencies = assessmentVariable.getAssessmentVarChildrenList();
    	for(AssessmentVarChildren dependencyAssociation : formulaDependencies) {
    		AssessmentVariable child = dependencyAssociation.getVariableChild();
    		switch(child.getAssessmentVariableTypeId().getAssessmentVariableTypeId()) {
				case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE: 
					measureTypeList.add(child);
					break;
				case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE_ANSWER: 
					answerTypeList.add(child);
					break;
				case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_CUSTOM: 
					customTypeList.add(child);
					break;
				case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_FORMULA: 
					formulaTypeList.add(child);
					sortAssessmentVariablesByType(child, answerTypeList, measureTypeList, customTypeList, formulaTypeList);
					break;
				default:			
					throw new UnsupportedOperationException(String.format("Assessment variable of type id: %s is not supported.", child.getAssessmentVariableTypeId().getAssessmentVariableTypeId()));
    		}
    	}
    	
    }
    
}
