package gov.va.escreening.expressionevaluator;

import gov.va.escreening.exception.ReferencedFormulaMissingException;
import gov.va.escreening.exception.ReferencedVariableMissingException;

import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class ExpressionEvaluatorServiceImpl implements ExpressionEvaluatorService {

    private static final Logger logger = LoggerFactory.getLogger(ExpressionEvaluatorServiceImpl.class);
	
    @Override
    public String evaluateFormula(FormulaDto formulaDto) throws NoSuchMethodException, SecurityException {
    	String workingTemplate = formulaDto.getExpressionTemplate();
    	String originalFormulaTemplate = workingTemplate;
    	workingTemplate = mergeChildFormulasIntoTemplate(workingTemplate, formulaDto.getChildFormulaMap(), originalFormulaTemplate);
    	workingTemplate = mergeVariablesToTemplate(workingTemplate, formulaDto.getVariableValueMap(), originalFormulaTemplate);
    	
    	ExpressionParser parser = new SpelExpressionParser();

    	String answer = null;

    	//Check to see if this requires some custom logic to evaulate the expression
    	if(workingTemplate.contains(CustomCalculations.CALCULATE_AGE)) {
    		//Register a static method as CUSTOM function then invoke it.
    		StandardEvaluationContext stdContext = new StandardEvaluationContext();
    		stdContext.registerFunction("calculateAge", CustomCalculations.class.getDeclaredMethod("calculateAge", new Class[] { String.class }));
    		answer = parser.parseExpression(workingTemplate).getValue(stdContext, String.class);
    	}
    	else {
    		answer = parser.parseExpression(workingTemplate).getValue(String.class);	
    		logger.debug("parsing expression {} returned {}", workingTemplate, answer);
    	}
    	
    	return answer;
    }
    
    private String mergeChildFormulasIntoTemplate(String workingTemplate, Map<Integer, String> childFormulas, String originalFormulaTemplate) {
    	if(childFormulas == null || childFormulas.size() == 0)
    		return workingTemplate;
    	
    	int safetyCounter = 0;
    	do {
	    	Iterator<Integer> keysIterator = childFormulas.keySet().iterator();
	    	while(keysIterator.hasNext()) {
	    		Integer key = keysIterator.next();
	    		String childFormula = childFormulas.get(key);
	    		String variablePattern = String.format("[$%s$]", key);
	    		workingTemplate = workingTemplate.replace(variablePattern, String.format(" ( %s ) ", childFormula));
	    	}
	    	
	    	safetyCounter = safetyCounter + 1;
	    	if(safetyCounter > 10)
	    		throw new ReferencedFormulaMissingException
	    			(String.format("A referenced formula was missing. The original template was: '%s'. The merged template was: '%s'.", 
	    					originalFormulaTemplate, workingTemplate));
	    	
    	} while(workingTemplate.contains("$"));
    	
    	return workingTemplate;
    }
     
    private String mergeVariablesToTemplate(String workingTemplate, Map<Integer, String> variableValueMap, String originalFormulaTemplate) {
    	if(variableValueMap == null || variableValueMap.size() == 0)
    		return workingTemplate;
    	
    	Iterator<Integer> keysIterator = variableValueMap.keySet().iterator();
    	while(keysIterator.hasNext()) {
    		Integer key = keysIterator.next();
    		String value = variableValueMap.get(key);
    		String variablePattern = String.format("[%s]", key);
    		workingTemplate = workingTemplate.replace(variablePattern, value);
    	}
    	
    	if(workingTemplate.contains("[") || workingTemplate.contains("]"))
    		throw new ReferencedVariableMissingException
    			(String.format("A referenced variable was missing. The original template was: '%s'. The merged template was: '%s'.", 
    					originalFormulaTemplate, workingTemplate));
    	
    	return workingTemplate;
    }
    
    public static void main(String[] args)
    {
        ExpressionParser parser = new SpelExpressionParser();
        
        String expression = "(((true||false||false)?1:0) + ((true||true||false||false||false)?1:0) + (true?1:0) + (true?1:0))";
        
        System.out.println(parser.parseExpression(expression).getValue());
    }
    
}