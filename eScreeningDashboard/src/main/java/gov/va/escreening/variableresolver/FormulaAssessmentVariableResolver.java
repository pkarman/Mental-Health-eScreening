package gov.va.escreening.variableresolver;

import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.expressionevaluator.FormulaDto;

import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

public interface FormulaAssessmentVariableResolver {
	AssessmentVariableDto resolveAssessmentVariable(AssessmentVariable assessmentVariable,  
			Integer veteranAssessmentId, Map<Integer, AssessmentVariable> measureAnswerHash);

	AssessmentVariableDto resolveAssessmentVariable(
			FormulaDto formula,
			Map<Integer, Pair<Measure, gov.va.escreening.dto.ae.Measure>> responseMap,
			Map<Integer, AssessmentVariable> measureAnswerHash);
}
