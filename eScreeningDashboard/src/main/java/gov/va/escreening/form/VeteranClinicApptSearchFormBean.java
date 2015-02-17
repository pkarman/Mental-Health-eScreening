package gov.va.escreening.form;

import gov.va.escreening.vista.dto.VistaClinicAppointment;

import java.util.Date;
import java.util.List;

public class VeteranClinicApptSearchFormBean {
	
	private String selectedClinic;
	private Date startDate;
	private Date endDate;
	private List<VistaClinicAppointment> result;
	
	public List<VistaClinicAppointment> getResult() {
		return result;
	}
	public void setResult(List<VistaClinicAppointment> results) {
		this.result = results;
	}
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
