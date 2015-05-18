package gov.va.escreening.variableresolver;

import static com.google.common.base.Preconditions.checkNotNull;
import static gov.va.escreening.constants.AssessmentConstants.MEASURE_TYPE_FREE_TEXT;
import static gov.va.escreening.constants.AssessmentConstants.MEASURE_TYPE_SELECT_MULTI;
import static gov.va.escreening.constants.AssessmentConstants.MEASURE_TYPE_SELECT_ONE;
import static gov.va.escreening.expressionevaluator.ExpressionExtentionUtil.DEFAULT_VALUE;
import static gov.va.escreening.expressionevaluator.ExpressionExtentionUtil.NUMBER_FORMAT;
import gov.va.escreening.constants.AssessmentConstants;
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

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

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
    		    
    			if (Strings.isNullOrEmpty(assessmentVariable.getFormulaTemplate())){
    				throw new CouldNotResolveVariableException(
    						String.format(
    								"Variable of type formula did not contain the required formulatempalte field"
    										+ " assessmentVariableId: %s, assessmentId: %s",
    								assessmentVariable.getAssessmentVariableId(),
    								veteranAssessmentId));
    			}
//    			List<AssessmentVariable> formulaTypeList = new ArrayList<AssessmentVariable>();
    			
//    			Set<AssessmentVariable> allformulaChildVars = 
//    			        resolveDependencies(assessmentVariable.getAssessmentVarChildrenList(), 
//    			                            params, formulaTypeList);
//                
        
                FormulaDto rootFormula = new FormulaDto();
    			rootFormula.setExpressionTemplate(assessmentVariable.getFormulaTemplate());
    			rootFormula.setVariableValueMap(createResolvedVarValueMap(assessmentVariable, params));
    			Map<Integer, AssessmentVariableDto> avMap = params.getResolvedVariableMap();
    			rootFormula.setAvMap(avMap);
    			
    			// iterate the list of formulas and add them to the object
//    			for (AssessmentVariable formulaVariable : formulaTypeList) {
//    				Integer id = formulaVariable.getAssessmentVariableId();
//    				String template = formulaVariable.getFormulaTemplate();
//    				rootFormula.getChildFormulaMap().put(id, template);
//    			}
    
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
	        AssessmentVariable formulaAv, 
	        ResolverParameters params) {
	    Map<Integer, String> valueMap = Maps.newHashMapWithExpectedSize(
	            formulaAv.getAssessmentVarChildrenList() != null ? formulaAv.getAssessmentVarChildrenList().size() : 0);
	    
	    for(AssessmentVarChildren avChild : formulaAv.getAssessmentVarChildrenList()){
	        AssessmentVariable childAv = avChild.getVariableChild();
	        Integer avId = childAv.getAssessmentVariableId();
	        
	        AssessmentVariableDto variable = null;
	        
	        try{
	            variable = resolveDependency(childAv, params);
	        }
	        catch(CouldNotResolveVariableException resolverException){
	            logger.debug("Child variable could not be resolved:\n{}", childAv);
	        }
	        
	        String value = null;
	        if(variable != null){	            
    	        if(variable.getType().equals("list")){
    	            value = getListVariableAsNumber(variable);
    	        }
    	        else{
    	            value = getVariableValue(variable);
    	        }
	        }
	        else{  //variable was not resolved
	            //TODO: This is slowing down our evaluation times so we have to optimize it
	            value = params.handleUnresolableVariable(childAv);
	        }
	        
	        if(value != null && value != DEFAULT_VALUE){
	            valueMap.put(avId, value);
	            logger.debug("Value of variable {} is {}", avId, value);
	        }
	        else{
	            logger.debug("Variable DTO {} could not be resolved to a value", avId);
	        }
	    }
	    return valueMap;
	}
	
	private AssessmentVariableDto resolveDependency(AssessmentVariable child, 
	        ResolverParameters params){
	    
	    switch (child.getAssessmentVariableTypeId()
                .getAssessmentVariableTypeId()) {
        case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE:
            return measureVariableResolver.resolveAssessmentVariable(child, params);
        case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE_ANSWER:
            return measureAnswerVariableResolver.resolveAssessmentVariable(child, params);
        case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_CUSTOM:
            return customVariableResolver.resolveAssessmentVariable(child, params);
        case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_FORMULA:
            return resolveAssessmentVariable(child, params);
        default:
            throw new UnsupportedOperationException(String.format(
                    "Assessment variable of type id: %s is not supported.",
                    child.getAssessmentVariableTypeId()
                            .getAssessmentVariableTypeId()));
        }
	}

    /**
     * Gets the numerical value of the given list-typed variable.
     * For select measures this means the calculation vaule.
     * For text answers it means the veteran entered value
     * @param var
     * @return
     */
    private String getListVariableAsNumber(AssessmentVariableDto var){
        if(var == null){
            return DEFAULT_VALUE;
        }
        String result;
        
        if(var.getMeasureTypeId() != null){
            switch(var.getMeasureTypeId()){
                case MEASURE_TYPE_FREE_TEXT:
                     result = expressionExtender.getFreeTextAnswer(var, DEFAULT_VALUE);
                     if(result != null && result != DEFAULT_VALUE){
                         try{
                             Double.valueOf(result);
                             return String.format(NUMBER_FORMAT, result);
                         }
                         catch(Exception e){ return result; }
                     }
                     break;
                case MEASURE_TYPE_SELECT_ONE:
                    AssessmentVariableDto response = expressionExtender.getSelectOneAnswerVar(var);
                    if(response == null){
                        return DEFAULT_VALUE;
                    }
                    result = response.getCalculationValue();
                    if(result == null){
                        result = response.getOtherText();
                    }
                    if(result != null){
                        return String.format(NUMBER_FORMAT, result);
                    }
                    break;
                case MEASURE_TYPE_SELECT_MULTI:// we could sum the calculation values
                default: 
                    return "[Error: unsupported question type]";
            }
        }
        else{
            return "[Error: unsupported question type]";
        }
        
        return DEFAULT_VALUE;
    }
    
    private String getVariableValue(AssessmentVariableDto var){
        if(var == null || var.getValue() == null){
            return DEFAULT_VALUE;
        }
        if(var.getValue() == "true" || var.getValue() == "false" ){
            return var.getValue();
        }
        
        try{
            Double.valueOf(var.getValue());
            return String.format(NUMBER_FORMAT, var.getValue());
        }
        catch(Exception e){ /* ignore */}

        return var.getValue();
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
//	private Set<AssessmentVariable>  resolveDependencies(
//	        List<AssessmentVarChildren> formulaDependencies,
//			ResolverParameters params,
//			List<AssessmentVariable> formulaTypeList) {
//
//	    //TODO: it would be far more efficient if we resolved variables on demand instead of resolving all of the here. 
//	    Set<AssessmentVariable> allDependencies = Sets.newHashSet();
//		for (AssessmentVarChildren dependencyAssociation : formulaDependencies) {
//			AssessmentVariable child = dependencyAssociation.getVariableChild();
//			allDependencies.add(child);
//			switch (child.getAssessmentVariableTypeId()
//					.getAssessmentVariableTypeId()) {
//			case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE:
//				measureVariableResolver.resolveAssessmentVariable(child, params);
//				break;
//			case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE_ANSWER:
//			    //here we lack the context to set the answer's exact row, this is OK because 
//			    //we don't support formulas using table answers.
//			    try{
//			        measureAnswerVariableResolver.resolveAssessmentVariable(child, params);
//			    }
//			    catch(CouldNotResolveVariableException e){
//			        //ignore
//			    }
//				break;
//			case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_CUSTOM:
//			    customVariableResolver.resolveAssessmentVariable(child, params);
//				break;
//			case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_FORMULA:
//				formulaTypeList.add(child);
//                allDependencies.addAll(resolveDependencies(
//						child.getAssessmentVarChildrenList(), params, formulaTypeList));
//				resolveAssessmentVariable(child, params);
//                
//				break;
//			default:
//				throw new UnsupportedOperationException(String.format(
//						"Assessment variable of type id: %s is not supported.",
//						child.getAssessmentVariableTypeId()
//								.getAssessmentVariableTypeId()));
//			}
//		}
//		return allDependencies;
//	}
}
