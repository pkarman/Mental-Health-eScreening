package gov.va.escreening.service;

import gov.va.escreening.domain.AssessmentStatusEnum;
import gov.va.escreening.dto.ae.AssessmentRequest;
import gov.va.escreening.dto.ae.AssessmentResponse;

import java.util.Map;

public interface AssessmentEngineService {

	/**
	 * Process user data in survey and returns the next page with the next set
	 * of questions and an updated progress section.
	 * 
	 * @param assessmentRequest
	 * @return
	 */
	AssessmentResponse processPage(AssessmentRequest assessmentRequest);

	/**
	 * Saves the veteran's answers found in the request. Any Rules dealing with
	 * questions found on the Survey page are run and any measure visibility
	 * changes are updated.
	 * 
	 * @param assessmentRequest
	 * @return a map (from measure ID to visibility) containing measures found
	 *         on the survey page found in the request.
	 */
	Map<Integer, Boolean> getUpdatedVisibility(
			AssessmentRequest assessmentRequest);

	boolean transitionAssessmentStatusTo(Integer veteranAssessmentId,
			AssessmentStatusEnum requestedState);
}
