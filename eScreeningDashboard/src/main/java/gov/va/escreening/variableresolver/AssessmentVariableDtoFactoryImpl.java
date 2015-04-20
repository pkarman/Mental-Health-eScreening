package gov.va.escreening.variableresolver;

import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.entity.AssessmentVarChildren;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.VariableTemplate;
import gov.va.escreening.exception.AssessmentVariableInvalidValueException;
import gov.va.escreening.exception.CouldNotResolveVariableException;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import static com.google.common.base.Preconditions.*;

@Transactional(noRollbackFor = { CouldNotResolveVariableException.class, AssessmentVariableInvalidValueException.class, UnsupportedOperationException.class, Exception.class })
public class AssessmentVariableDtoFactoryImpl implements AssessmentVariableDtoFactory {
	
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
	public AssessmentVariableDto createAssessmentVariableDto(
			AssessmentVariable assessmentVariable, Integer veteranAssessmentId,
			Map<Integer, AssessmentVariable> measureAnswerHash, NullValueHandler smrNullHandler) {
		AssessmentVariableDto variableDto = null;
		Integer type = assessmentVariable.getAssessmentVariableTypeId().getAssessmentVariableTypeId();
		switch (type) {
		case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE:
			variableDto = measureVariableResolver.resolveAssessmentVariable(assessmentVariable, veteranAssessmentId, measureAnswerHash);
			break;
		case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE_ANSWER:
			variableDto = measureAnswerVariableResolver.resolveAssessmentVariable(assessmentVariable, veteranAssessmentId, measureAnswerHash);
			break;
		case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_CUSTOM:
			variableDto = customVariableResolver.resolveAssessmentVariable(assessmentVariable, veteranAssessmentId);
			break;
		case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_FORMULA:

			int id = assessmentVariable.getAssessmentVariableId();
			variableDto = formulaAssessmentVariableResolver.resolveAssessmentVariable(assessmentVariable, veteranAssessmentId, measureAnswerHash, smrNullHandler);
			break;
		default:
			throw new UnsupportedOperationException(String.format("Assessment variable of type id: %s is not supported.", type));
		}

		return variableDto;
	}

	@Override
	public Map<Integer, AssessmentVariable> createMeasureAnswerTypeHash(
			List<VariableTemplate> variableTemplates) {
	    
	    //This is a temporary fix until ticket 643 is complete. If there is a conflict with ticket 643 please remove these changes
		Set<AssessmentVariable> variables = Sets.newHashSetWithExpectedSize(variableTemplates.size());
		for (VariableTemplate variableTemplate : variableTemplates) {
		    variables.add(variableTemplate.getAssessmentVariableId());
		}
		return createMeasureAnswerTypeHash(variables);

	}

	@Override
	public Map<Integer, AssessmentVariable> createMeasureAnswerTypeHash(
			Iterable<AssessmentVariable> assessmentVariables) {
		Map<Integer, AssessmentVariable> measureAnswerHash = new HashMap<Integer, AssessmentVariable>();
		
		//This is a temporary fix until ticket 643 is complete. If there is a conflict with ticket 643 please remove these changes
		Set<AssessmentVariable> answerAvs = Sets.newHashSet(); 		
		
		for(AssessmentVariable av : assessmentVariables){
		    collectAnswerAvs(av, answerAvs);
		}
		measureAnswerHash = Maps.newHashMapWithExpectedSize(answerAvs.size());
		for (AssessmentVariable variable : answerAvs) {
		    MeasureAnswer measureAnswer = variable.getMeasureAnswer();
		    if (measureAnswer != null){
		        measureAnswerHash.put(measureAnswer.getMeasureAnswerId(), variable);
		    }
		} 
		return measureAnswerHash;
	}
	
	//This is a temporary fix until ticket 643 is complete. If there is a conflict with ticket 643 please remove these changes
	private Set<AssessmentVariable> collectAnswerAvs(AssessmentVariable av, Set<AssessmentVariable> answerAvs){
	    switch(av.getAssessmentVariableTypeId().getAssessmentVariableTypeId()){
	    case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_FORMULA:
	        for(AssessmentVarChildren child : av.getAssessmentVarChildrenList()){
	            collectAnswerAvs(child.getVariableChild(), answerAvs);
	        }
	        break;
	    case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE:
	        for(MeasureAnswer ma : av.getMeasure().getMeasureAnswerList()){
	            answerAvs.add(ma.getAssessmentVariable());
	        }
	        break;
	    case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE_ANSWER:
	        answerAvs.add(av);
	        break;
	    }
	    return answerAvs;
	}
}
