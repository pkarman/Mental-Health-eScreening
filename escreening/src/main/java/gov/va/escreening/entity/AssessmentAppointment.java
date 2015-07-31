package gov.va.escreening.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "assessment_appoint")
public class AssessmentAppointment implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "vet_assessment_id")
	private int vetAssessmentId;
	
	@Column(name = "appointment_date")
    @Temporal(TemporalType.TIMESTAMP)
	private Date appointmentDate;
	
	@Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	public int getVetAssessmentId() {
		return vetAssessmentId;
	}

	public void setVetAssessmentId(int vetAssessment) {
		this.vetAssessmentId = vetAssessment;
	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	

}
