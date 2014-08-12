package gov.va.escreening.entity;

import gov.va.escreening.constants.AssessmentConstants;

public class VeteranAssessmentAuditLogHelper {
	
    public static VeteranAssessmentAuditLog createAuditLogEntry(VeteranAssessment veteranAssessment, 
    		int eventId, int assessmentStatusId, int personTypeId) {
    	VeteranAssessmentAuditLog auditLogEntry = new VeteranAssessmentAuditLog();

    	auditLogEntry.setVeteranAssessmentEventId(new VeteranAssessmentEvent(eventId));
    	auditLogEntry.setVeteranAssessmentId(veteranAssessment.getVeteranAssessmentId());
    	auditLogEntry.setAssessmentStatusId(new AssessmentStatus(assessmentStatusId));

    	auditLogEntry.setPersonTypeId(new PersonType(personTypeId));
    	if(personTypeId == AssessmentConstants.PERSON_TYPE_USER) {
    		auditLogEntry.setPersonFirstName(veteranAssessment.getCreatedByUser().getFirstName());
    		auditLogEntry.setPersonLastName(veteranAssessment.getCreatedByUser().getLastName());
    		auditLogEntry.setPersonId(veteranAssessment.getCreatedByUser().getUserId());
    	}
    	else {
    		auditLogEntry.setPersonFirstName(veteranAssessment.getVeteran().getFirstName());
    		auditLogEntry.setPersonLastName(veteranAssessment.getVeteran().getLastName());
    		auditLogEntry.setPersonId(veteranAssessment.getVeteran().getVeteranId());
    	}
    	
    	return auditLogEntry;
    }
}
