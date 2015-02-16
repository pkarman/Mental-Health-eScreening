package gov.va.escreening.form;

import java.util.Date;

public class VeteranClinicApptSearchFormBean {
	
	private String selectedClinic;
	private Date startDate;
	private Date endDate;
	
	
	public String getSelectedClinic() {
		return selectedClinic;
	}
	public void setSelectedClinic(String selectedClinic) {
		this.selectedClinic = selectedClinic;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public VeteranClinicApptSearchFormBean() {
		super();
		
	}
	
	

}
