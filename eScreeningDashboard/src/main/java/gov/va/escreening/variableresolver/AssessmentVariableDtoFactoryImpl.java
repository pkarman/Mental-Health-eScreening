package gov.va.escreening.variableresolver;

import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.VariableTemplate;
import gov.va.escreening.exception.AssessmentVariableInvalidValueException;
import gov.va.escreening.exception.CouldNotResolveVariableException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

@Transactional(noRollbackFor = { CouldNotResolveVariableException.class, AssessmentVariableInvalidValueException.class, UnsupportedOperationException.class, Exception.class })
public class AssessmentVariableDtoFactoryImpl implements AssessmentVariableDtoFactory {
	@Autowired
	private CustomAssessmentVariableResolver customVariableResolver;
	@Autowired
	private FormulaAssessmentVariableResolver formulaAssessmentVariableResolver;
	@Autowired
	private MeasureAnswerAssessmentVariableResolver measureAnswerVariableResolver;
	@Autowired
	private MeasureAssessmentVariableResolver measureVariableResolver;

	@Override
	public AssessmentVariableDto createAssessmentVariableDto(
			AssessmentVariable assessmentVariable, Integer veteranAssessmentId,
			Map<Integer, AssessmentVariable> measureAnswerHash) {
		AssessmentVariableDto variableDto = null;
		//StopWatch sw = new StopWatch("AssessmentVariableDtoFactoryImpl");
		Integer type = assessmentVariable.getAssessmentVariableTypeId().getAssessmentVariableTypeId();
		switch (type) {
		case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE:
			//sw.start("ASSESSMENT_VARIABLE_TYPE_MEASURE");
			variableDto = measureVariableResolver.resolveAssessmentVariable(assessmentVariable, veteranAssessmentId, measureAnswerHash);
			//sw.stop();
			break;
		case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE_ANSWER:
			//sw.start("ASSESSMENT_VARIABLE_TYPE_MEASURE_ANSWER");
			variableDto = measureAnswerVariableResolver.resolveAssessmentVariable(assessmentVariable, veteranAssessmentId, measureAnswerHash);
			//sw.stop();
			break;
		case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_CUSTOM:
			//sw.start("ASSESSMENT_VARIABLE_TYPE_CUSTOM");
			variableDto = customVariableResolver.resolveAssessmentVariable(assessmentVariable, veteranAssessmentId);
			//sw.stop();
			break;
		case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_FORMULA:
			//sw.start("ASSESSMENT_VARIABLE_TYPE_FORMULA");

			int id=assessmentVariable.getAssessmentVariableId();
			variableDto = new AssessmentVariableDto(id, "var"+id, "string", "formula_"+id, "37.0", "37.0", null, null, 0);
			// variableDto = formulaAssessmentVariableResolver.resolveAssessmentVariable(assessmentVariable,
			// veteranAssessmentId, measureAnswerHash);
			//sw.stop();
			break;
		default:
			throw new UnsupportedOperationException(String.format("Assessment variable of type id: %s is not supported.", type));
		}

		//System.out.println(sw.prettyPrint());
		return variableDto;
	}

	@Override
	public Map<Integer, AssessmentVariable> createMeasureAnswerTypeHash(
			List<VariableTemplate> variableTemplates) {
		Map<Integer, AssessmentVariable> measureAnswerHash = new Hashtable<Integer, AssessmentVariable>();

		for (VariableTemplate template : variableTemplates) {
			if (template.getAssessmentVariableId().getAssessmentVariableTypeId().getAssessmentVariableTypeId() == AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE_ANSWER) {
				MeasureAnswer measureAnswer = template.getAssessmentVariableId().getMeasureAnswer();
				AssessmentVariable assessmentVariable = template.getAssessmentVariableId();
				if (measureAnswer != null && assessmentVariable != null)
					measureAnswerHash.put(measureAnswer.getMeasureAnswerId(), assessmentVariable);
			}
		}

		return measureAnswerHash;
	}

	@Override
	public Map<Integer, AssessmentVariable> createMeasureAnswerTypeHash(
			Iterable<AssessmentVariable> assessmentVariables) {
		Map<Integer, AssessmentVariable> measureAnswerHash = new HashMap<Integer, AssessmentVariable>();

		for (AssessmentVariable variable : assessmentVariables) {
			if (variable.getAssessmentVariableTypeId().getAssessmentVariableTypeId() == AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE_ANSWER) {
				MeasureAnswer measureAnswer = variable.getMeasureAnswer();
				if (measureAnswer != null)
					measureAnswerHash.put(measureAnswer.getMeasureAnswerId(), variable);
			}
		}

		return measureAnswerHash;
	}

}
