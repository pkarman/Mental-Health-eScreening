package gov.va.escreening.variableresolver;

import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.exception.CouldNotResolveVariableValueException;
import org.springframework.stereotype.Component;

/**
 * Created by munnoo on 1/21/15.
 */
@Component("templateSmrNullHandler")
public class NullValueHandlerTemplate implements NullValueHandler {

    @Override
    public String handleMeasureAnswer(Integer veteranAssessmentId, MeasureAnswer measureAnswer) {
        throw new CouldNotResolveVariableValueException(String.format("There was no MeasureAnswer response for MeasureAnswerId: %s, assessmentId: %s",
                measureAnswer.getMeasureAnswerId(), veteranAssessmentId));
    }

    @Override
    public String handleMeasure(MeasureAssessmentVariableResolver measureAssessmentVariableResolver, Integer measureId, Integer veteranAssessmentId) {
        throw new CouldNotResolveVariableValueException(
                String.format(
                        "There were not any measure responses for measureId: %s, assessmentId: %s",
                        measureId, veteranAssessmentId));
    }

    @Override
    public String resolveCalculationDefaultValue(MeasureAssessmentVariableResolverImpl measureAssessmentVariableResolver, Integer measureId, Integer veteranAssessmentId) {
        return "null";
    }
}
