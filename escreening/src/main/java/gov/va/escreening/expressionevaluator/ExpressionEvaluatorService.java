package gov.va.escreening.expressionevaluator;

import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.formula.AvMapTypeEnum;
import gov.va.escreening.formula.FormulaHandler;
import gov.va.escreening.variableresolver.AssessmentVariableDto;

import java.util.List;
import java.util.Map;

public interface ExpressionEvaluatorService {
    Map extractInputsRecursively(String filteredExpTemplate);

    String evaluateFormula(Map<String, Object> tgtFormula);

    String evaluateFormula(FormulaDto formulaDto) throws NoSuchMethodException, SecurityException;

    String evaluateFormula(String formulaAsStr, Map<Integer, AssessmentVariableDto> variableMap);

    AssessmentVariable findAvById(Integer avId);

    void readAllFormulas(FormulaHandler formulaHandler);

    Map<String, Object> verifyExpressionTemplate(String expressionTemplate, AvMapTypeEnum avMap);

    Integer updateFormula(Map<String, Object> tgtFormula);

    String buildFormulaFromMin(List<String> tokens);

    Map<String,Object> fetchFormulaFromDbById(Integer formulaId);

    public enum key {
        verifiedIds, success, failed, reason, status;
    }
}
