package gov.va.escreening.formula;

import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.expressionevaluator.FormulaDto;

/**
 * Created by munnoo on 1/30/15.
 */
public interface FormulaHandler {
    void handleFormula(AssessmentVariable av, String formulaWithDisplayNames);
}
