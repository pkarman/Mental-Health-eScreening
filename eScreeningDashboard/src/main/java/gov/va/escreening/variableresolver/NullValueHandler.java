package gov.va.escreening.variableresolver;

import gov.va.escreening.entity.MeasureAnswer;

/**
 * Facility for Template and DataExport to utilize the core AssessmentVariable Engine with a slight difference in behavior where necessary
 * Created by munnoo on 1/21/15.
 */
public interface NullValueHandler {
    /**
     * a call back to handle MeasureAnswerAssessmentVariableResolver#resolveCalculationValue. There are two separate implementations, one for Template and other for Data Export
     *
     * @param veteranAssessmentId
     * @param measureAnswer
     * @return
     */
    String handleMeasureAnswer(Integer veteranAssessmentId, MeasureAnswer measureAnswer);

    /**
     * a call back injected in MeasureAssessmentVariableResolver#resolveCalculationValue.
     *
     * @param measureAssessmentVariableResolver
     * @param measureId
     * @param veteranAssessmentId
     * @return
     */
    String handleMeasure(MeasureAssessmentVariableResolver measureAssessmentVariableResolver, Integer measureId, Integer veteranAssessmentId);

    String resolveCalculationDefaultValue(MeasureAssessmentVariableResolverImpl measureAssessmentVariableResolver, Integer measureId, Integer veteranAssessmentId);

}
