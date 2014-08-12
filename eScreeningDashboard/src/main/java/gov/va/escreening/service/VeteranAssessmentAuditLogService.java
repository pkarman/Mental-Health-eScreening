package gov.va.escreening.service;

import gov.va.escreening.dto.dashboard.AssessmentAuditLogReport;
import gov.va.escreening.entity.VeteranAssessmentAuditLog;

import java.util.List;

public interface VeteranAssessmentAuditLogService {
	List<AssessmentAuditLogReport> getAssessmentAuditLogByAssessmentId (Integer assessmentId);
	
	void addLogEntry(VeteranAssessmentAuditLog auditLogEntry);
}
