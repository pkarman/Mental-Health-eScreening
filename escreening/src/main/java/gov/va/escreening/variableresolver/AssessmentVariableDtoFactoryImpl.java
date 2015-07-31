package gov.va.escreening.variableresolver;

import static com.google.common.base.Preconditions.checkNotNull;
import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.exception.AssessmentVariableInvalidValueException;
import gov.va.escreening.exception.CouldNotResolveVariableException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional(noRollbackFor = { CouldNotResolveVariableException.class, AssessmentVariableInvalidValueException.class, UnsupportedOperationException.class, Exception.class })
public class AssessmentVariableDtoFactoryImpl implements AssessmentVariableDtoFactory {
    Logger logger = LoggerFactory.getLogger(AssessmentVariableDtoFactoryImpl.class);
    
	//Please add to the constructor and do not use field based @Autowired
	private final CustomAssessmentVariableResolver customVariableResolver;
	private final FormulaAssessmentVariableResolver formulaAssessmentVariableResolver;
	private final MeasureAnswerAssessmentVariableResolver measureAnswerVariableResolver;
	private final MeasureAssessmentVariableResolver measureVariableResolver;

	@Autowired
	public AssessmentVariableDtoFactoryImpl(
			CustomAssessmentVariableResolver cvr, 
			FormulaAssessmentVariableResolver favr,
			MeasureAnswerAssessmentVariableResolver mavr,
			MeasureAssessmentVariableResolver mvr){
		
		customVariableResolver = checkNotNull(cvr);
		formulaAssessmentVariableResolver = checkNotNull(favr);
		measureAnswerVariableResolver = checkNotNull(mavr);
		measureVariableResolver = checkNotNull(mvr);
	}
	
	@Override
	public AssessmentVariableDto resolveAssessmentVariable(
			AssessmentVariable assessmentVariable, 
			ResolverParameters params) {
	    
		AssessmentVariableDto variableDto = params.getResolvedVariable(assessmentVariable.getAssessmentVariableId());
		if(variableDto == null){
		    //logger.debug("Resolving variable with ID: {}", assessmentVariable.getAssessmentVariableId());
		    
    		Integer type = assessmentVariable.getAssessmentVariableTypeId().getAssessmentVariableTypeId();
    		switch (type) {
    		case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE:
    			variableDto = measureVariableResolver.resolveAssessmentVariable(assessmentVariable, params);
    			break;
    		case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE_ANSWER:
    		    //Some care should be taken here because there can be more than one response for an answer. This is 
    		    //due to the the fact that tables allow for more than one row of responses for a given question (and
    		    //therefore multiple AV DTO can be possible.
    			variableDto = measureAnswerVariableResolver.resolveAssessmentVariable(assessmentVariable, params);
    			break;
    		case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_CUSTOM:
    			variableDto = customVariableResolver.resolveAssessmentVariable(assessmentVariable, params);
    			break;
    		case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_FORMULA:
    			variableDto = formulaAssessmentVariableResolver.resolveAssessmentVariable(assessmentVariable, params);
    			break;
    		default:
    			throw new UnsupportedOperationException(String.format("Assessment variable of type id: %s is not supported.", type));
    		}
		}
		return variableDto;
	}
}
