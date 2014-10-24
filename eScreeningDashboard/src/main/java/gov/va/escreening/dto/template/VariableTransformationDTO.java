package gov.va.escreening.dto.template;

import java.util.List;

public class VariableTransformationDTO {
	
	private String name;
	private List<String>  params;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getParams() {
		return params;
	}
	public void setParams(List<String> params) {
		this.params = params;
	}
	
	

}
