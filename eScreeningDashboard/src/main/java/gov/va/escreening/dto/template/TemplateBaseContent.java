package gov.va.escreening.dto.template;

import gov.va.escreening.dto.ae.ErrorBuilder;
import gov.va.escreening.exception.EscreeningDataValidationException;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import static gov.va.escreening.constants.AssessmentConstants.*;

@JsonInclude(Include.NON_NULL)
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
@JsonSubTypes({ @Type(value = TemplateTextContent.class, name = "text"), 
	@Type(value = TemplateVariableContent.class, name = "var")
	})
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class TemplateBaseContent {
	
	public static String translate(String operator, TemplateBaseContent inLeft, TemplateBaseContent right, Set<Integer> ids)
	{
		
		
		if (inLeft instanceof TemplateTextContent)
		{
			try
			{
				Double.parseDouble(((TemplateTextContent)inLeft).getContent());
				return ((TemplateTextContent)inLeft).getContent();
			}
			catch(Exception e)
			{}
			return "\""+((TemplateTextContent)inLeft).getContent()+"\"";
		}
		
		TemplateVariableContent leftContent = (TemplateVariableContent) inLeft;
		
		TemplateAssessmentVariableDTO left = leftContent.getContent();
		
		String inStr = "var" + left.getId();
		
		ids.add(left.getId());
		
		String translatedVar = inStr;
		
		// we are going to apply transformation of variable.
		if (left.getTransformations() != null && !left.getTransformations().isEmpty()) {
			for (VariableTransformationDTO transformation : left.getTransformations()) {
				StringBuilder s = new StringBuilder(transformation.getName());
				s.append("(").append(translatedVar);

				if (transformation.getParams() != null) {
					for (String param : transformation.getParams()){
						String trimmed = param.trim();
						//check to see if we need to quote the parameter value
						if((trimmed.startsWith("[") && trimmed.endsWith("]"))
							|| (trimmed.startsWith("{") && trimmed.endsWith("}"))){
							//we don't quote when it is an array or map
							s.append(",").append(param);
						}
						//this is done separately from the above statement because we must lower case booleans
						else if(trimmed.equalsIgnoreCase("true") 
							  || trimmed.equalsIgnoreCase("false")){
							s.append(",").append(param.toLowerCase());
						}
						else{
						    
						    try{//if the param is a number don't put quotes around it
				                Double.parseDouble(param);
				                s.append(",").append(param);
				            }
						    catch(Exception e){
						        s.append(",\"").append(param).append("\"");
						    }
						}
					}
				}
				//replace translatedVar with transformed variable
				translatedVar = s.append(")").toString();
			}
		}
		String beforeOperator = translatedVar;
		
		if (operator == null && (left.getTransformations() == null || left.getTransformations().isEmpty()))
		{//Don't pull value out if transformations were applied
			if (left.measureTypeIn(MEASURE_TYPE_FREE_TEXT, MEASURE_TYPE_SELECT_ONE, MEASURE_TYPE_SELECT_MULTI))
			{
				translatedVar = "getResponse("+inStr+", "+left.getMeasureTypeId()+")";
				
			}
			else if (left.typeIs(ASSESSMENT_VARIABLE_TYPE_FORMULA))
			{
				translatedVar =  "getFormulaValue("+inStr+")";
			}
			else if (left.typeIs(ASSESSMENT_VARIABLE_TYPE_CUSTOM))
			{
				translatedVar =  "getCustomValue("+inStr+")";
			}
		}
		else if ("eq".equals(operator) || "neq".equals(operator) || 
				"lt".equals(operator) || "gt".equals(operator) || "lte".equals(operator) 
				|| "gte".equals(operator))
		{
			if (left.measureTypeIs(MEASURE_TYPE_FREE_TEXT))			
			{
				if (right instanceof TemplateTextContent)
				{
					try
					{
						Double.parseDouble(((TemplateTextContent)right).getContent());
						translatedVar = "asNumber("+inStr+", "+left.getMeasureTypeId()+")?string != DEFAULT_VALUE && asNumber("+inStr+", "+left.getMeasureTypeId()+")";
					}
					catch(Exception e)
					{
						// right is not a number;
						translatedVar = "getResponse("+inStr+", "+left.getMeasureTypeId()+")";
					}
				}
				else
					translatedVar =  "getResponse("+inStr+")";
			}
			else if (left.typeIs(ASSESSMENT_VARIABLE_TYPE_CUSTOM))
			{
				translatedVar =  "asNumber(getCustomValue("+inStr+"))?string != \"notset\" && asNumber(getCustomValue("+inStr+"))";
			}
			else if (left.typeIs(ASSESSMENT_VARIABLE_TYPE_FORMULA))
			{
				translatedVar = "getFormulaValue("+inStr+")?string != \"notset\" && getFormulaValue("+inStr+")";
			}
		}
		else if ("answered".equals(operator))
		{
			if (left.measureTypeIn(
					MEASURE_TYPE_FREE_TEXT, 
					MEASURE_TYPE_SELECT_ONE, 
					MEASURE_TYPE_SELECT_MULTI, 
					MEASURE_TYPE_TABLE))
			{
				translatedVar= "wasAnswered("+inStr+")";
			}
		}
		else if ("nanswered".equals(operator))
		{
			if (left.measureTypeIn(
					MEASURE_TYPE_FREE_TEXT,
					MEASURE_TYPE_SELECT_ONE, 
					MEASURE_TYPE_SELECT_MULTI, 
					MEASURE_TYPE_TABLE))
			{
				translatedVar= "wasntAnswered("+inStr+")";
			}
		}
		else if ("result".equals(operator))
		{
			if (left.typeIs(ASSESSMENT_VARIABLE_TYPE_FORMULA))
			{
				translatedVar= "formulaHasResult("+inStr+")";
			}
			else if (left.typeIs(ASSESSMENT_VARIABLE_TYPE_CUSTOM))
			{
				translatedVar= "customHasResult("+inStr+")";
			}
			else if (left.typeIn(
					ASSESSMENT_VARIABLE_TYPE_MEASURE, 
					MEASURE_TYPE_SELECT_ONE_MATRIX, 
					MEASURE_TYPE_SELECT_MULTI_MATRIX))
			{
				translatedVar= "matrixHasResult("+inStr+")";
			}
			
		}
		else if ("nresult".equals(operator))
		{
			if (left.typeIs(ASSESSMENT_VARIABLE_TYPE_FORMULA))
			{
				translatedVar= "formulaHasNoResult("+inStr+")";
			}
			else if (left.typeIs(ASSESSMENT_VARIABLE_TYPE_CUSTOM))
			{
				translatedVar= "customHasNoResult("+inStr+")";
			}
			else if (left.measureTypeIn(
					MEASURE_TYPE_SELECT_ONE_MATRIX, 
					MEASURE_TYPE_SELECT_MULTI_MATRIX))
			{
				translatedVar= "matrixHasNoResult("+inStr+")";
			}
		}
		else if ("response".equals(operator))
		{
			if (left.measureTypeIn(
					MEASURE_TYPE_SELECT_ONE, 
					MEASURE_TYPE_SELECT_MULTI))
			{
				translatedVar= "responseIs("+inStr+", "+(translate(null, right, null, ids))+"," +left.getMeasureTypeId()+")";
			}
		}
		else if ("nresponse".equals(operator))
		{
			if (left.measureTypeIn(
					MEASURE_TYPE_SELECT_ONE, 
					MEASURE_TYPE_SELECT_MULTI))
			{
				translatedVar= "responseIsnt("+inStr+", "+(translate(null, right, null, ids))+"," +left.getMeasureTypeId()+")";
			}
		}
		else if ("none".equals(operator))
		{
			if (left.measureTypeIn( 
					MEASURE_TYPE_SELECT_ONE,
					MEASURE_TYPE_SELECT_MULTI,
					MEASURE_TYPE_TABLE))
			{
				translatedVar= "wasAnswerNone("+inStr+")";
			}
			 
		}
		else if ("nnone".equals(operator))
		{
			if (left.measureTypeIn(
					MEASURE_TYPE_SELECT_ONE, 
					MEASURE_TYPE_SELECT_MULTI,
					MEASURE_TYPE_TABLE))
			{
				translatedVar= "wasntAnswerNone("+inStr+")";
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
