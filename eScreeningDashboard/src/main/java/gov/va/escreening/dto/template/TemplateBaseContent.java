package gov.va.escreening.dto.template;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonInclude(Include.NON_NULL)
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
@JsonSubTypes({ @Type(value = TemplateTextContent.class, name = "text"), 
	@Type(value = TemplateVariableContent.class, name = "var")
	})
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class TemplateBaseContent {
	
	public static String translate(String operand, TemplateBaseContent inLeft, TemplateBaseContent right, Set<Integer> ids)
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
		if (left.getTransformations() != null && left.getTransformations().size() > 0) {
			for (VariableTransformationDTO transformation : left.getTransformations()) {
				StringBuilder s = new StringBuilder(transformation.getName());
				s.append("(").append(translatedVar);

				if (transformation.getParams() != null
						&& transformation.getParams().size() > 0) {
					for (String param : transformation.getParams())
						s.append("," + param);
				}
				s.append(" ) ");

				translatedVar = s.toString();
			}
		}
		
		if (operand == null && (left.getTransformations() == null || left.getTransformations().isEmpty()))
		{//Don't pull value out if transformations were applied
			if (left.getTypeId()!=null && left.getTypeId() == 1 && (left.getMeasureTypeId() == 1 || left.getMeasureTypeId() == 2 || left.getMeasureTypeId() == 3))
			{
				translatedVar = "getResponse("+inStr+", "+left.getMeasureTypeId()+")";
				
			}
			else if (left.getTypeId()!=null && left.getTypeId() == 4 )
			{
				translatedVar =  "getFormulaValue("+inStr+")";
			}
			else if (left.getTypeId()!=null && left.getTypeId() == 3)
			{
				translatedVar =  "getCustomValue("+inStr+")";
			}
		}
		else if ("eq".equals(operand) || "neq".equals(operand) || 
				"lt".equals(operand) || "gt".equals(operand) || "lte".equals(operand) 
				|| "gte".equals(operand))
		{
			if (left.getMeasureId()!=null && left.getMeasureTypeId() == 1)			
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
			else if (left.getTypeId()!=null && left.getTypeId() == 3)
			{
				translatedVar =  "asNumber(getCustomValue("+inStr+"))?string != \"notset\" && asNumber(getCustomValue("+inStr+"))";
			}
			else if (left.getTypeId()!=null && left.getTypeId() == 4)
			{
				translatedVar = "getFormulaValue("+inStr+")?string != \"notset\" && getFormulaValue("+inStr+")";
			}
		}
		else if ("answered".equals(operand))
		{
			if (left.getMeasureTypeId() != null 
					&& (left.getMeasureTypeId() == 1 || left.getMeasureTypeId() == 2 || left.getMeasureTypeId() == 3 || left.getMeasureTypeId() == 4))
			{
				translatedVar= "wasAnswered("+inStr+")";
			}
		}
		else if ("nanswered".equals(operand))
		{
			if (left.getMeasureTypeId() != null 
					&& (left.getMeasureTypeId() == 1 || left.getMeasureTypeId() == 2 || left.getMeasureTypeId() == 3 || left.getMeasureTypeId() == 4))
			{
				translatedVar= "wasntAnswered("+inStr+")";
			}
		}
		else if ("result".equals(operand))
		{
			if (left.getTypeId()!=null && left.getTypeId() == 4)
			{
				translatedVar= "formulaHasResult("+inStr+")";
			}
			else if (left.getTypeId()!=null && left.getTypeId() == 3)
			{
				translatedVar= "customHasResult("+inStr+")";
			}
			else if (left.getTypeId()!=null && (left.getTypeId() == 6 || left.getTypeId() == 7))
			{
				translatedVar= "matrixHasResult("+inStr+")";
			}
			
		}
		else if ("nresult".equals(operand))
		{
			if (left.getTypeId()!=null && left.getTypeId() == 4)
			{
				translatedVar= "formulaHasNoResult("+inStr+")";
			}
			else if (left.getTypeId()!=null && left.getTypeId() == 3)
			{
				translatedVar= "customHasNoResult("+inStr+")";
			}
			else if (left.getTypeId()!=null && (left.getTypeId() == 6 || left.getTypeId() == 7))
			{
				translatedVar= "matrixHasNoResult("+inStr+")";
			}
		}
		else if ("response".equals(operand))
		{
			if (left.getMeasureTypeId() !=null && (left.getMeasureTypeId() == 2 || left.getMeasureTypeId() == 3))
			{
				translatedVar= "responseIs("+inStr+", "+(translate(null, right, null, ids))+"," +left.getMeasureTypeId()+")";
			}
		}
		else if ("nresponse".equals(operand))
		{
			if (left.getMeasureTypeId()!=null && (left.getMeasureTypeId() == 2 || left.getMeasureTypeId() == 3))
			{
				translatedVar= "responseIsnt("+inStr+", "+(translate(null, right, null, ids))+"," +left.getMeasureTypeId()+")";
			}
		}
		else if ("none".equals(operand))
		{
			if (left.getMeasureTypeId() != null 
					&& (left.getMeasureTypeId() == 2 || left.getMeasureTypeId() == 3|| left.getMeasureTypeId() == 4))
			{
				translatedVar= "wasAnswerNone("+inStr+")";
			}
			 
		}
		else if ("nnone".equals(operand))
		{
			if (left.getMeasureTypeId() != null 
					&& (left.getMeasureTypeId() == 2 || left.getMeasureTypeId() == 3|| left.getMeasureTypeId() == 4))
			{
				translatedVar= "wasntAnswerNone("+inStr+")";
			}
			 
		}
		
		return translatedVar;
	}
}
