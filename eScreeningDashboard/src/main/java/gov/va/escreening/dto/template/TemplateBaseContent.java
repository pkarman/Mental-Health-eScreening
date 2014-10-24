package gov.va.escreening.dto.template;

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
public abstract class TemplateBaseContent {
	
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public static String translate(String operand, TemplateBaseContent inLeft, TemplateBaseContent right)
	{
		
		
		if (inLeft.getType().equals("text"))
		{
			return ((TemplateTextContent)inLeft).getContent();
		}
		
		TemplateVariableContent leftContent = (TemplateVariableContent) inLeft;
		
		TemplateAssessmentVariableDTO left = leftContent.getContent();
		
		String inStr = "var" + left.getId();
		
		String translatedVar = inStr;
		
		if (operand == null)
		{
			if (left.getTypeId() == 1 && (left.getMeasureTypeId() == 1 || left.getMeasureTypeId() == 2 || left.getMeasureTypeId() == 3))
				
			{
				if (right instanceof TemplateTextContent)
				{
					try
					{
						Double.parseDouble(((TemplateTextContent)right).getContent());
						translatedVar = "asNumber(getResponse("+inStr+", "+left.getMeasureTypeId()+"))";
					}
					catch(Exception e)
					{
						// right is not a number;
						translatedVar = "getResponse("+inStr+", "+left.getMeasureTypeId()+")";
					}
				}
				else
				{
					translatedVar = "getResponse("+inStr+", "+left.getMeasureTypeId()+")";
				}
				
				
			}
			else if (left.getTypeId() == 4 )
			{
				translatedVar =  "getFormulaValue("+inStr+")";
			}
			else if (left.getTypeId() == 3)
			{
				translatedVar =  "getCustomValue("+inStr+")";
			}
		}
		else if ("eq".equals(operand) || "neq".equals(operand) || 
				"lt".equals(operand) || "gt".equals(operand) || "lte".equals(operand) 
				|| "gte".equals(operand))
		{
			if (left.getMeasureTypeId() == 1)			
			{
				translatedVar =  "getResponse("+inStr+")";
			}
			else if (left.getTypeId() == 3)
			{
				translatedVar =  "asNumber(getCustomValue("+inStr+"))";
			}
			else if (left.getTypeId() == 4)
			{
				translatedVar = "getFormulaValue("+inStr+")";
			}
		}
		else if ("answered".equals(operand))
		{
			if (left.getMeasureTypeId() == 1 || left.getMeasureTypeId() == 2 || left.getMeasureTypeId() == 3)
			{
				translatedVar= "wasAnswered("+inStr+", "+left.getMeasureTypeId()+")";
			}
		}
		else if ("nanswered".equals(operand))
		{
			if (left.getMeasureTypeId() == 1 || left.getMeasureTypeId() == 2 || left.getMeasureTypeId() == 3)
			{
				translatedVar= "wasntAnswered("+inStr+", "+left.getMeasureTypeId()+")";
			}
			 
		}
		else if ("result".equals(operand))
		{
			if (left.getTypeId() == 4)
			{
				translatedVar= "formulaHasResult("+inStr+")";
			}
			else if (left.getTypeId() == 3)
			{
				translatedVar= "customHasResult("+inStr+")";
			}
		}
		else if ("nresult".equals(operand))
		{
			if (left.getTypeId() == 4)
			{
				translatedVar= "formulaHasNoResult("+inStr+")";
			}
			else if (left.getTypeId() == 3)
			{
				translatedVar= "customHasNoResult("+inStr+")";
			}
			
		}
		else if ("response".equals(operand))
		{
			if (left.getMeasureTypeId() == 2 || left.getMeasureTypeId() == 3)
			{
				translatedVar= "responseIs("+inStr+", "+(translate(null, right, null))+"," +left.getMeasureTypeId()+")";
			}
		}
		else if ("nresponse".equals(operand))
		{
			if (left.getMeasureTypeId() == 2 || left.getMeasureTypeId() == 3)
			{
				translatedVar= "responseIsnt("+inStr+", "+(translate(null, right, null))+"," +left.getMeasureTypeId()+")";
			}
		}
		
		// now we have the var.
		// we are going to apply transformation on top ofit.
		
		if (left.getTransformations() != null && left.getTransformations().size() > 0) {
			for (VariableTransformationDTO transformation : left.getTransformations()) {
				StringBuffer s = new StringBuffer(transformation.getName());
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
		return translatedVar;
	}
}
