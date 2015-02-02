package gov.va.escreening.expressionevaluator;

import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.formula.AvMapTypeEnum;
import gov.va.escreening.formula.FormulaHandler;

import java.util.List;
import java.util.Map;

public interface ExpressionEvaluatorService {
    String evaluateFormula(FormulaDto formulaDto) throws NoSuchMethodException, SecurityException;

    String evaluateFormulaTemplate(String formulaAsStr);

    void extractAllInputs(Integer avId, List<Integer> avIds);

    FormulaDto createFormulaDto(String expressionTemplate, Map<Integer, String> avDataMap);

    AssessmentVariable findAvById(Integer avId);

    void readAllFormulas(FormulaHandler formulaHandler);

    Map<String, String> verifyExpressionTemplate(String expressionTemplate, AvMapTypeEnum avMap);

    void updateFormula(Integer avId, String cleanedFormula);
}
