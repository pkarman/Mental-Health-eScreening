package gov.va.escreening.repository;

import gov.va.escreening.dto.SearchAttributes;
import gov.va.escreening.dto.dashboard.SearchResult;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.entity.VeteranAssessmentDashboardAlert;

import java.util.List;
import java.util.Map;

public interface VeteranAssessmentDashboardAlertRepository extends RepositoryInterface<VeteranAssessmentDashboardAlert> {

	/**
	 * Retrieves all the alerts for a veteranAssessmentId.
	 * 
	 * @param veteranAssessmentId
	 * @return
	 */
	List<VeteranAssessmentDashboardAlert> findByVeteranAssessmentId(
			int veteranAssessmentId);

	List findAlertsByProgram(int programId);

	List<Map<String, Object>> findNearingCompletionAssessments(int programId,
			int totalRequestedRecords);

	List<Map<String, Object>> findSlowMovingAssessments(int programId, int totalRequestedRecords);

	SearchResult<VeteranAssessment> searchVeteranAssessment(Integer programId,
			SearchAttributes searchAttributes);
}
