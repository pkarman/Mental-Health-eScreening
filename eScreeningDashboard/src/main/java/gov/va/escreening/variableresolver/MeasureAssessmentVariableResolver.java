package gov.va.escreening.variableresolver;

import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.Measure;

import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

//TODO: see if we still need this interface
public interface MeasureAssessmentVariableResolver extends VariableResolver{

	String resolveCalculationValue(AssessmentVariable assessmentVariable,
								   Integer veteranAssessmentId, Map<Integer, AssessmentVariable> measureAnswerHash, NullValueHandler smrNullHandler);

	String resolveCalculationValue(AssessmentVariable measureVariable,
			Pair<Measure, gov.va.escreening.dto.ae.Measure> answer,
			Map<Integer, AssessmentVariable> measureAnswerHash);
}
