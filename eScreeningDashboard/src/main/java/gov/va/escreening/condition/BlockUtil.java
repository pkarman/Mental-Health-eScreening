package gov.va.escreening.condition;

import gov.va.escreening.dto.ae.ErrorBuilder;
import gov.va.escreening.dto.template.TemplateBaseContent;
import gov.va.escreening.exception.EscreeningDataValidationException;

import java.util.Set;

/**
 * Utility class to assist with Blocks (used in Templates and Rules)
 * 
 * @author Robin Carnow
 *
 */
//TODO: Move to making this an injected interface instead of a static utility class
public class BlockUtil {
	private static final FreeMarkerTranslator freeMarkerTranslator = new FreeMarkerTranslator();
	private static final SpringElTranslator springElTranslator = new SpringElTranslator();
	
	public static FreeMarkerTranslator getFreeMarkerTranslator(){
	    return freeMarkerTranslator;
	}
	
	public static SpringElTranslator getSpringElTranslator(){
	    return springElTranslator;
	}
	
	public static String toFreeMarker(TemplateBaseContent textContent, Set<Integer> avIds){
	    return freeMarkerTranslator.translateCondition(null, textContent, null, avIds);
	}
	
	public static String conditionToFreeMarker(String operator, TemplateBaseContent left,
			TemplateBaseContent right, Set<Integer> avIds) {
		return translateCondition(freeMarkerTranslator, operator, left, right, avIds);
	}
	
	public static String conditionToSpringEl(String operator, TemplateBaseContent left,
            TemplateBaseContent right, Set<Integer> avIds) {
        return translateCondition(springElTranslator, operator, left, right, avIds);
    }
	
	public static String translateCondition(BlockTranslator translator, String operator, TemplateBaseContent left,
            TemplateBaseContent right, Set<Integer> avIds){
	    
		switch (operator) {
		case "eq":
			return translator.translateCondition(operator, left, right, avIds) + " == " + translator.translateCondition(null, right, null, avIds);
		case "neq":
			return translator.translateCondition(operator, left, right, avIds) + " != " + translator.translateCondition(null, right, null, avIds);
		case "gt":
			return translator.translateCondition(operator, left, right, avIds) + " > " + translator.translateCondition(null, right, null, avIds);
		case "lt":
			return translator.translateCondition(operator, left, right, avIds) + " < " + translator.translateCondition(null, right, null, avIds);
		case "gte":
			return translator.translateCondition(operator, left, right, avIds) + " >= " + translator.translateCondition(null, right, null, avIds);
		case "lte":
			return translator.translateCondition(operator, left, right, avIds) + " <= " + translator.translateCondition(null, right, null, avIds);
		case "answered":
		case "nanswered":
		case "result":
		case "nresult":
		case "response":
		case "nresponse":
		case "none":
		case "nnone":
			return translator.translateCondition(operator, left, right, avIds);
		default:
		    ErrorBuilder.throwing(EscreeningDataValidationException.class)
            .toAdmin("Operator: '" + operator + "' is unsupported for variable")
            .toUser("An unsupported template operation was used.  Please call support")
            .throwIt();
		    return "";
		}
	}	
}
