package gov.va.escreening.variableresolver;

import static com.google.common.base.Preconditions.checkNotNull;
import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.dto.ae.Answer;
import gov.va.escreening.entity.AssessmentVarChildren;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.exception.CouldNotResolveVariableException;
import gov.va.escreening.exception.CouldNotResolveVariableValueException;
import gov.va.escreening.exception.ErrorResponseRuntimeException;
import gov.va.escreening.exception.ReferencedFormulaMissingException;
import gov.va.escreening.exception.ReferencedVariableMissingException;
import gov.va.escreening.expressionevaluator.ExpressionEvaluatorService;
import gov.va.escreening.expressionevaluator.ExpressionExtentionUtil;
import gov.va.escreening.expressionevaluator.FormulaDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class FormulaAssessmentVariableResolverImpl implements
		FormulaAssessmentVariableResolver {

	//Please add to the constructor and do not use field based @Autowired	
	private final ExpressionEvaluatorService expressionEvaluatorService;
	private final MeasureAnswerAssessmentVariableResolver measureAnswerVariableResolver;
	private final MeasureAssessmentVariableResolver measureVariableResolver;
    private final CustomAssessmentVariableResolver customVariableResolver;

	private static final Logger logger = LoggerFactory
			.getLogger(FormulaAssessmentVariableResolverImpl.class);
	private static final ExpressionExtentionUtil expressionExtender = new ExpressionExtentionUtil();

	@Autowired
	public FormulaAssessmentVariableResolverImpl(
			ExpressionEvaluatorService ees,
			MeasureAnswerAssessmentVariableResolver mavr,
			MeasureAssessmentVariableResolver mvr,
			CustomAssessmentVariableResolver cvr){
		
		expressionEvaluatorService = checkNotNull(ees);
		measureAnswerVariableResolver = checkNotNull(mavr);
		measureVariableResolver = checkNotNull(mvr);
		customVariableResolver = checkNotNull(cvr);
	}
	
	@Override
	public AssessmentVariableDto resolveAssessmentVariable(
			AssessmentVariable assessmentVariable, ResolverParameters params) {

	    logger.debug("\n\nResolving formula {} \nWith expression: {}", 
	            assessmentVariable.getAssessmentVariableId(), 
	            assessmentVariable.getFormulaTemplate());
	    
	    Integer avId = assessmentVariable.getAssessmentVariableId();
        params.checkUnresolvable(avId);
        AssessmentVariableDto variableDto = params.getResolvedVariable(avId);
        
        if(variableDto != null)
            return variableDto;
        
	    try{
	    
	        Integer veteranAssessmentId = params.getAssessmentId();
    
    		try {
    		    
    			if (assessmentVariable.getFormulaTemplate() == null
    					|| assessmentVariable.getFormulaTemplate().isEmpty())
    				throw new CouldNotResolveVariableException(
    						String.format(
    								"Variable of type formula did not contain the required formulatempalte field"
    										+ " assessmentVariableId: %s, assessmentId: %s",
    								assessmentVariable.getAssessmentVariableId(),
    								veteranAssessmentId));
    			
    			List<AssessmentVariable> formulaTypeList = new ArrayList<AssessmentVariable>();
    			
    			Set<AssessmentVariable> allformulaChildVars = 
    			        resolveDependencies(assessmentVariable.getAssessmentVarChildrenList(), 
    			                            params, formulaTypeList);
                
        
                FormulaDto rootFormula = new FormulaDto();
    			rootFormula.setExpressionTemplate(assessmentVariable.getFormulaTemplate());
    			Map<Integer, AssessmentVariableDto> avMap = params.getResolvedVariableMap();
    			rootFormula.setAvMap(avMap);
    			rootFormula.setVariableValueMap(createResolvedVarValueMap(allformulaChildVars, params));
    			
    			// iterate the list of formulas and add them to the object
    			for (AssessmentVariable formulaVariable : formulaTypeList) {
    				Integer id = formulaVariable.getAssessmentVariableId();
    				String template = formulaVariable.getFormulaTemplate();
    				rootFormula.getChildFormulaMap().put(id, template);
    			}
    
    			String result = expressionEvaluatorService.evaluateFormula(rootFormula);
    
    			variableDto = new AssessmentVariableDto(assessmentVariable); 
    			variableDto.setResponse(result);
    		}
    		// warnings, these exceptions typically mean that a question was not
    		// answered
    		catch (CouldNotResolveVariableValueException cnrvve) {
    			throw new CouldNotResolveVariableException(
    					getCouldNotResolveMessage(
    							assessmentVariable.getAssessmentVariableId(),
    							veteranAssessmentId, cnrvve.getMessage()));
    		} catch (CouldNotResolveVariableException cnrve) {
    			throw new CouldNotResolveVariableException(
    					getCouldNotResolveMessage(
    							assessmentVariable.getAssessmentVariableId(),
    							veteranAssessmentId, cnrve.getMessage()));
    		}
    		// These are errors of either a missing data or logic nature. Missing
    		// expected questions, answers, formulas, or mapping logic missing,
    		// ect...
    		catch (NoSuchMethodException nsme) {
    			logger.error("NoSuchMethodException: " + nsme.getMessage());
    			throw new CouldNotResolveVariableException(
    					getCouldNotResolveMessage(
    							assessmentVariable.getAssessmentVariableId(),
    							veteranAssessmentId, nsme.getMessage()));
    		} catch (ReferencedVariableMissingException rvme) {
    			logger.error("ReferencedVariableMissingException: "
    					+ rvme.getMessage());
    			throw new CouldNotResolveVariableException(
    					getCouldNotResolveMessage(
    							assessmentVariable.getAssessmentVariableId(),
    							veteranAssessmentId, rvme.getMessage()));
    		} catch (ReferencedFormulaMissingException rfme) {
    			logger.error("ReferencedFormulaMissingException: "
    					+ rfme.getMessage());
    			throw new CouldNotResolveVariableException(
    					getCouldNotResolveMessage(
    							assessmentVariable.getAssessmentVariableId(),
    							veteranAssessmentId, rfme.getMessage()));
    		} catch (ErrorResponseRuntimeException erre) {
    			logger.error(erre.getErrorResponse().getLogMessage());
    			throw new CouldNotResolveVariableException(
    					getCouldNotResolveMessage(
    							assessmentVariable.getAssessmentVariableId(),
    							veteranAssessmentId, erre.getErrorResponse()
    									.getDeveloperMessage()));
    		} catch (UnsupportedOperationException uoe) {
    			logger.error("UnsupportedOperationException: " + uoe.getMessage());
    			throw new CouldNotResolveVariableException(
    					getCouldNotResolveMessage(
    							assessmentVariable.getAssessmentVariableId(),
    							veteranAssessmentId, uoe.getMessage()));
    		} catch (Exception e) {
    			logger.error("Exception: ", e);
    			throw new CouldNotResolveVariableException(
    					getCouldNotResolveMessage(
    							assessmentVariable.getAssessmentVariableId(),
    							veteranAssessmentId, e.getMessage()));
    		}
    		
    		//add resolved Dto to the cache
    		params.addResolvedVariable(variableDto);
    	    return variableDto;
	    }
	    catch(Exception e){
	        params.addUnresolvableVariable(assessmentVariable.getAssessmentVariableId());
            throw e;
	    }
	}

	private Map<Integer, String> createResolvedVarValueMap(
	        Collection<AssessmentVariable> avChildSet, 
	        ResolverParameters params) {
	    Map<Integer, String> valueMap = Maps.newHashMapWithExpectedSize(avChildSet.size());
	    
	    for(AssessmentVariable childAv : avChildSet){
	        Integer avId = childAv.getAssessmentVariableId();
	        AssessmentVariableDto variable = params.getResolvedVariable(avId);
	        
	        String value = null;
	        if(variable != null){	            
    	        if(variable.getType().equals("list")){
    	            value = expressionExtender.getListVariableAsNumber(variable);
    	        }
    	        else{
    	            value = expressionExtender.getVariableValue(variable);
    	        }
	        }
	        else{  //variable was not resolved
                List<Answer> responses = params.getVariableAnswers(avId);
                if(!responses.isEmpty()){
                    value = responses.get(0).getAnswerResponse();
                }
	        }
	        
	        if(value != null && value != ExpressionExtentionUtil.DEFAULT_VALUE){
	            valueMap.put(avId, value);
	            logger.debug("Value of variable {} is {}", avId, value);
	        }
	        else{
	            logger.debug("Variable DTO {} could not be resolved to a value", avId);
	        }
	    }
	    return valueMap;
	}



    private String getCouldNotResolveMessage(int assessmentVariableId,
			int assessmentId, String originalExceptionMsg) {
		String errorMsg = String
				.format("Could not resolve all of the required criteria for the formula.  AssessmentVariableId: %s, assessmentid: %s, message: %s",
						assessmentVariableId, assessmentId,
						originalExceptionMsg);
		return errorMsg;
	}

	/**
	 * Resolves each child (dependency) and collects the resolved values in the params 
	 * object.  Any sub-formula are also resolved and their dependencies are included
	 * in the returned list. 
	 * @param formulaDependencies
	 * @param params
	 * @param formulaTypeList
	 * @return list of all AssessmentVariable needed from this formula and any sub-formula
	 */
	private Set<AssessmentVariable>  resolveDependencies(
	        List<AssessmentVarChildren> formulaDependencies,
			ResolverParameters params,
			List<AssessmentVariable> formulaTypeList) {

	    Set<AssessmentVariable> allDependencies = Sets.newHashSet();
		for (AssessmentVarChildren dependencyAssociation : formulaDependencies) {
			AssessmentVariable child = dependencyAssociation.getVariableChild();
			allDependencies.add(child);
			switch (child.getAssessmentVariableTypeId()
					.getAssessmentVariableTypeId()) {
			case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE:
				measureVariableResolver.resolveAssessmentVariable(child, params);
				break;
			case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE_ANSWER:
			    //here we lack the context to set the answer's exact row, this is OK because 
			    //we don't support formulas using table answers.
			    try{
			        measureAnswerVariableResolver.resolveAssessmentVariable(child, params);
			    }
			    catch(CouldNotResolveVariableException e){
			        //ignore
			    }
				break;
			case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_CUSTOM:
			    customVariableResolver.resolveAssessmentVariable(child, params);
				break;
			case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_FORMULA:
				formulaTypeList.add(child);
                allDependencies.addAll(resolveDependencies(
						child.getAssessmentVarChildrenList(), params, formulaTypeList));
				resolveAssessmentVariable(child, params);
                
				break;
			default:
				throw new UnsupportedOperationException(String.format(
						"Assessment variable of type id: %s is not supported.",
						child.getAssessmentVariableTypeId()
								.getAssessmentVariableTypeId()));
			}
		}
		return allDependencies;
	}

//	@Override
//	public Optional<String> resolveAssessmentVariable(
//			List<AssessmentVarChildren> formulaDependencies,
//			FormulaDto rootFormula,
//			Map<Integer, Pair<Measure, gov.va.escreening.dto.ae.Measure>> responseMap,
//			Map<Integer, AssessmentVariable> measureAnswerHash) {
//		List<AssessmentVariable> answerTypeList = new ArrayList<AssessmentVariable>();
//		List<AssessmentVariable> measureTypeList = new ArrayList<AssessmentVariable>();
//		List<AssessmentVariable> customTypeList = new ArrayList<AssessmentVariable>();
//		List<AssessmentVariable> formulaTypeList = new ArrayList<AssessmentVariable>();
//		sortAssessmentVariablesByType(formulaDependencies, answerTypeList,
//				measureTypeList, customTypeList, formulaTypeList);
//
//		String result = null;
//
//		for (AssessmentVariable answerVariable : answerTypeList) {
//			Pair<Measure, gov.va.escreening.dto.ae.Measure> answer = responseMap
//					.get(answerVariable.getMeasureAnswer().getMeasure().getMeasureId());
//			if ((answer == null)) {
//				return Optional.absent();
//			}
//			String value = measureAnswerVariableResolver
//					.resolveCalculationValue(answerVariable, answer);
//			rootFormula.getVariableValueMap().put(
//					answerVariable.getAssessmentVariableId(), value);
//		}
//
//		for (AssessmentVariable measureVariable : measureTypeList) {
//			String value = "999";
//			Pair<Measure, gov.va.escreening.dto.ae.Measure> answer = responseMap
//					.get(measureVariable.getMeasure().getMeasureId());
//			if ((answer != null)) {
//
//				value = measureVariableResolver.resolveCalculationValue(
//						measureVariable, answer, measureAnswerHash);
//				
//				if(value == null)
//				{
//					value = "999";
//				}
//			}
//
//			
//			rootFormula.getVariableValueMap().put(
//					measureVariable.getAssessmentVariableId(), value);
//
//		}
//
//		// iterate the list of formulas and add them to the object
//		for (AssessmentVariable formulaVariable : formulaTypeList) {
//			int id = formulaVariable.getAssessmentVariableId();
//			String template = formulaVariable.getFormulaTemplate();
//			rootFormula.getChildFormulaMap().put(id, template);
//		}
//
//		try {
//			result = expressionEvaluatorService.evaluateFormula(rootFormula);
//		} catch (NoSuchMethodException | SecurityException e) {
//			// throw new
//			// CouldNotResolveVariableException(getCouldNotResolveMessage(-1,
//			// -1, e.getMessage()));
//		}
//
//		return Optional.fromNullable(result);
//	}

}
