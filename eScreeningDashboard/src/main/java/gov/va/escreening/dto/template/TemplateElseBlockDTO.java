package gov.va.escreening.dto.template;

public class TemplateElseBlockDTO extends TemplateBaseBlockDTO {
	
	public TemplateElseBlockDTO()
	{
		setType("else");
	}

	@Override
	public String toFreeMarkerFormat()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("<#else>");
		
		for(INode child : getChildren())
		{
			sb.append(child.toFreeMarkerFormat());
		}
		
		return sb.toString();
	}
}
