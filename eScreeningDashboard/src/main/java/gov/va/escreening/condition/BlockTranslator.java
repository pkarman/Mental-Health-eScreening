package gov.va.escreening.condition;

import static gov.va.escreening.constants.AssessmentConstants.*;
import gov.va.escreening.dto.ae.ErrorBuilder;
import gov.va.escreening.dto.template.TemplateAssessmentVariableDTO;
import gov.va.escreening.dto.template.TemplateBaseContent;
import gov.va.escreening.dto.template.TemplateTextContent;
import gov.va.escreening.dto.template.TemplateVariableContent;
import gov.va.escreening.exception.EscreeningDataValidationException;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;

/**
 * Base class for block translators.  Blocks are defined by the user using an editor and 
 * the objects that are defined by our UI are translated here into some templating language.
 *   
 * @author Robin Carnow
 *
 */
public abstract class BlockTranslator {
    private static final Logger logger = LoggerFactory.getLogger(BlockTranslator.class);
    
    /**
     * Creates the initial value for the translation.<br/>
     * Note: This probably should include the dereferencing of the inLeft variable and any transformations
     * applied to it.
     * @param variable the variable to dereference and apply transformations to.
     * @return StringBuilder containing initial content for the given variable
     */
    protected abstract StringBuilder createInitialContent(TemplateAssessmentVariableDTO variable); 
        
    /**
     * Translates a condition into a string
     * @param operator 
     * @param inLeft the left operand
     * @param right the right operand
     * @param avIds the set of AssessmentVariable IDs which should be added to when an AV is found in the condition.
     * @return string representing the condition
     */
    public final String translateCondition(String operator, TemplateBaseContent inLeft, TemplateBaseContent right, Set<Integer> avIds){
        
        if (inLeft instanceof TemplateTextContent){
            try {
                Double.parseDouble(((TemplateTextContent)inLeft).getContent());
                return ((TemplateTextContent)inLeft).getContent();
            }
            catch(Exception e){}
            return "\""+((TemplateTextContent)inLeft).getContent()+"\"";
        }

        TemplateVariableContent leftContent = (TemplateVariableContent) inLeft;

        TemplateAssessmentVariableDTO left = leftContent.getContent();

        avIds.add(left.getId());
        
        String translatedVar = createInitialContent(left).toString();
        String beforeOperator = translatedVar;

        if (operator == null && (left.getTransformations() == null || left.getTransformations().isEmpty()))
        {//Don't pull value out if transformations were applied
            if (left.measureTypeIn(MEASURE_TYPE_FREE_TEXT, MEASURE_TYPE_READ_ONLY, 
                    MEASURE_TYPE_SELECT_ONE, MEASURE_TYPE_SELECT_MULTI))
            {
                translatedVar = "getResponse("+translatedVar+", "+left.getMeasureTypeId()+")";

            }
            else if (left.typeIs(ASSESSMENT_VARIABLE_TYPE_FORMULA))
            {
                translatedVar =  "getFormulaValue("+translatedVar+")";
            }
            else if (left.typeIs(ASSESSMENT_VARIABLE_TYPE_CUSTOM))
            {
                translatedVar =  "getCustomValue("+translatedVar+")";
            }
        }
        else if ("eq".equals(operator) || "neq".equals(operator) 
                || "lt".equals(operator) || "gt".equals(operator) 
                || "lte".equals(operator) || "gte".equals(operator))
        {
            if (left.measureTypeIn(MEASURE_TYPE_FREE_TEXT, MEASURE_TYPE_READ_ONLY))         
            {
                if (right instanceof TemplateTextContent)
                {
                    try
                    {
                        Double.parseDouble(((TemplateTextContent)right).getContent());
                        translatedVar = "isDefined(asNumber("+translatedVar+", "+left.getMeasureTypeId()+") && asNumber("+translatedVar+", "+left.getMeasureTypeId()+")";
                    }
                    catch(Exception e)
                    {
                        // right is not a number;
                        translatedVar = "getResponse("+translatedVar+", "+left.getMeasureTypeId()+")";
                    }
                }
                else
                    translatedVar =  "getResponse("+translatedVar+")";
            }
            if (left.measureTypeIs(MEASURE_TYPE_TABLE)) //at this point we only support numberOfEntries transformation compared with a number
            {
                if (right instanceof TemplateTextContent)
                {
                    String rightContent = ((TemplateTextContent)right).getContent();
                    try
                    {
                        Double.parseDouble(rightContent);
                        translatedVar =  "isDefined(" + translatedVar +") && " + translatedVar;
                    }
                    catch(Exception e){
                        logger.warn("Unsupported operation: Left operand: {} \nOperator: {}\n right operand: {}\n Full exception {}", 
                                new Object[]{translatedVar, operator, rightContent, Throwables.getRootCause(e).getLocalizedMessage()});
                    }
                }
            }
            else if (left.typeIs(ASSESSMENT_VARIABLE_TYPE_CUSTOM))
            {
                translatedVar =  "isDefined(asNumber(getCustomValue("+translatedVar+"))) && asNumber(getCustomValue("+translatedVar+"))";
            }
            else if (left.typeIs(ASSESSMENT_VARIABLE_TYPE_FORMULA))
            {
                translatedVar = "isDefined(getFormulaValue("+translatedVar+")) && getFormulaValue("+translatedVar+")";
            }
        }
        else if ("answered".equals(operator))
        {
            if (left.measureTypeIn(
                    MEASURE_TYPE_FREE_TEXT,
                    MEASURE_TYPE_READ_ONLY,
                    MEASURE_TYPE_SELECT_ONE, 
                    MEASURE_TYPE_SELECT_MULTI, 
                    MEASURE_TYPE_TABLE))
            {
                translatedVar= "wasAnswered("+translatedVar+")";
            }
        }
        else if ("nanswered".equals(operator))
        {
            if (left.measureTypeIn(
                    MEASURE_TYPE_FREE_TEXT,
                    MEASURE_TYPE_READ_ONLY,
                    MEASURE_TYPE_SELECT_ONE, 
                    MEASURE_TYPE_SELECT_MULTI, 
                    MEASURE_TYPE_TABLE))
            {
                translatedVar= "wasntAnswered("+translatedVar+")";
            }
        }
        else if ("result".equals(operator))
        {
            if (left.typeIs(ASSESSMENT_VARIABLE_TYPE_FORMULA))
            {
                translatedVar= "formulaHasResult("+translatedVar+")";
            }
            else if (left.typeIs(ASSESSMENT_VARIABLE_TYPE_CUSTOM))
            {
                translatedVar= "customHasResult("+translatedVar+")";
            }
            else if (left.typeIn(
                    ASSESSMENT_VARIABLE_TYPE_MEASURE, 
                    MEASURE_TYPE_SELECT_ONE_MATRIX, 
                    MEASURE_TYPE_SELECT_MULTI_MATRIX))
            {
                translatedVar= "matrixHasResult("+translatedVar+")";
            }

        }
        else if ("nresult".equals(operator))
        {
            if (left.typeIs(ASSESSMENT_VARIABLE_TYPE_FORMULA))
            {
                translatedVar= "formulaHasNoResult("+translatedVar+")";
            }
            else if (left.typeIs(ASSESSMENT_VARIABLE_TYPE_CUSTOM))
            {
                translatedVar= "customHasNoResult("+translatedVar+")";
            }
            else if (left.measureTypeIn(
                    MEASURE_TYPE_SELECT_ONE_MATRIX, 
                    MEASURE_TYPE_SELECT_MULTI_MATRIX))
            {
                translatedVar= "matrixHasNoResult("+translatedVar+")";
            }
        }
        else if ("response".equals(operator))
        {
            if (left.measureTypeIn(
                    MEASURE_TYPE_SELECT_ONE, 
                    MEASURE_TYPE_SELECT_MULTI))
            {
                translatedVar= "responseIs("+translatedVar+", "+(translateCondition(null, right, null, avIds))+"," +left.getMeasureTypeId()+")";
            }
        }
        else if ("nresponse".equals(operator))
        {
            if (left.measureTypeIn(
                    MEASURE_TYPE_SELECT_ONE, 
                    MEASURE_TYPE_SELECT_MULTI))
            {
                translatedVar= "responseIsnt("+translatedVar+", "+(translateCondition(null, right, null, avIds))+"," +left.getMeasureTypeId()+")";
            }
        }
        else if ("none".equals(operator))
        {
            if (left.measureTypeIn( 
                    MEASURE_TYPE_SELECT_ONE,
                    MEASURE_TYPE_SELECT_MULTI,
                    MEASURE_TYPE_TABLE))
            {
                translatedVar= "wasAnswerNone("+translatedVar+")";
            }

        }
        else if ("nnone".equals(operator))
        {
            if (left.measureTypeIn(
                    MEASURE_TYPE_SELECT_ONE, 
                    MEASURE_TYPE_SELECT_MULTI,
                    MEASURE_TYPE_TABLE))
            {
                translatedVar= "wasntAnswerNone("+translatedVar+")";
            }

        }

        if(translatedVar == beforeOperator && operator != null && !operator.isEmpty()){
            ErrorBuilder.throwing(EscreeningDataValidationException.class)
            .toAdmin("Operator: '" + operator + "' is unsupported for variable (with ID: " + left.getId() + ") of type: " + left.getTypeId())
            .toUser("An unsupported template operation was used.  Please call support")
            .throwIt();
        }

        return translatedVar;
    }
}
