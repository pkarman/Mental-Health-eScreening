package gov.va.escreening.variableresolver;

import gov.va.escreening.entity.AssessmentVarChildren;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.expressionevaluator.FormulaDto;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

public interface FormulaAssessmentVariableResolver {
	AssessmentVariableDto resolveAssessmentVariable(AssessmentVariable assessmentVariable,  
			Integer veteranAssessmentId, Map<Integer, AssessmentVariable> measureAnswerHash);


	AssessmentVariableDto resolveAssessmentVariable(
			List<AssessmentVarChildren> createAssessmentVarChildrenList,
			FormulaDto formula,
			Map<Integer, Pair<Measure, gov.va.escreening.dto.ae.Measure>> responseMap,
			Map<Integer, AssessmentVariable> measureAnswerHash);
}
