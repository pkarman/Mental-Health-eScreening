package gov.va.escreening.variableresolver;

import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.exception.CouldNotResolveVariableValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by munnoo on 1/21/15.
 */
@Component("exportDataSmrNullHandler")
public class NullValueHandlerDataExport implements NullValueHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String handleMeasureAnswer(Integer veteranAssessmentId, MeasureAnswer measureAnswer) {
        logger.warn(String.format("There was no MeasureAnswer response for MeasureAnswerId: %s, assessmentId: %s",
                measureAnswer.getMeasureAnswerId(), veteranAssessmentId));
        return measureAnswer.getMeasure().getMeasureType().getMeasureTypeId() == 3 ? "false" : "0";
    }

    @Override
    public String handleMeasure(MeasureAssessmentVariableResolver mvr, Integer measureId, Integer veteranAssessmentId) {
        logger.warn(
                String.format(
                        "There were not any measure responses for measureId: %s, assessmentId: %s",
                        measureId, veteranAssessmentId));
        return "0";
    }

    @Override
    public String resolveCalculationDefaultValue(MeasureAssessmentVariableResolverImpl measureAssessmentVariableResolver, Integer measureId, Integer veteranAssessmentId) {
        return "0";
    }
}
