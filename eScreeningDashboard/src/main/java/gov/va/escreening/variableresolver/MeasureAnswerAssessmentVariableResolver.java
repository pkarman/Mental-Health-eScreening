package gov.va.escreening.variableresolver;

import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.SurveyMeasureResponse;

import java.util.Map;

public interface MeasureAnswerAssessmentVariableResolver {
	AssessmentVariableDto resolveAssessmentVariable(AssessmentVariable assessmentVariable, 
			Integer veteranAssessmentId, Map<Integer, AssessmentVariable> measureAnswerHash);
	
	AssessmentVariableDto resolveAssessmentVariable(AssessmentVariable parentAssessmentVariable, SurveyMeasureResponse response, 
			Integer veteranAssessmentId, Map<Integer, AssessmentVariable> measureAnswerHash);
	
	String resolveCalculationValue(AssessmentVariable assessmentVariable, Integer veteranAssessmentId, 
			SurveyMeasureResponse response);
	
	String resolveCalculationValue(AssessmentVariable assessmentVariable, Integer veteranAssessmentId);
}
