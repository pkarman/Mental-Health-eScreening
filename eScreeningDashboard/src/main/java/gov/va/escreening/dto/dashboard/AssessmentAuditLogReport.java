package gov.va.escreening.dto.dashboard;

import java.io.Serializable;
import java.util.Date;

public class AssessmentAuditLogReport implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer eventId;
    private String updatedBy = "";
    private String assessmentState = "";
    private String assessmentEvent = "";
    private Date eventDate;
    
	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	public String getAssessmentState() {
		return assessmentState;
	}
	public void setAssessmentState(String assessmentState) {
		this.assessmentState = assessmentState;
	}
	
	public String getAssessmentEvent() {
		return assessmentEvent;
	}
	public void setAssessmentEvent(String assessmentEvent) {
		this.assessmentEvent = assessmentEvent;
	}
	
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
}