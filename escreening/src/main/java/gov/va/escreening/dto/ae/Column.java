package gov.va.escreening.dto.ae;

import java.io.Serializable;
import java.util.List;

public class Column implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private Integer answerId;
	private List<Validation> validations;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Validation> getValidations() {
		return validations;
	}
	public void setValidations(List<Validation> validations) {
		this.validations = validations;
	}
	
    public Integer getAnswerId() {
		return answerId;
	}
	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}
}
