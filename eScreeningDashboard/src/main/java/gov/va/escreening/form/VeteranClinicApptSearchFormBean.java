package gov.va.escreening.form;

import gov.va.escreening.domain.ClinicDto;

import java.util.Date;
import java.util.List;

public class VeteranClinicApptSearchFormBean {
	
	private ClinicDto selectedClinic;
	private Date startDate;
	private Date endDate;
	
	
	public ClinicDto getSelectedClinic() {
		return selectedClinic;
	}
	public void setSelectedClinic(ClinicDto selectedClinic) {
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
