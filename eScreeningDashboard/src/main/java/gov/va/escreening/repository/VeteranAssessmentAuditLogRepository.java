package gov.va.escreening.repository;

import gov.va.escreening.entity.VeteranAssessmentAuditLog;

import java.util.List;

public interface VeteranAssessmentAuditLogRepository extends RepositoryInterface<VeteranAssessmentAuditLog> {
	List<VeteranAssessmentAuditLog> getAssessmentAuditLogByAssessmentId (Integer assessmentId);
}
