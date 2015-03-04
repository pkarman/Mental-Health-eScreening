package gov.va.escreening.domain;

public class ClinicalReminderDto {
	private int id;
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int iD) {
		id = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public ClinicalReminderDto(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

}
