package gov.va.escreening.context;

import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.repository.SurveyMeasureResponseRepository;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component("veteranAssessmentSmrList")
public class VeteranAssessmentSmrList {
	@Resource(type = SurveyMeasureResponseRepository.class)
	SurveyMeasureResponseRepository smrr;

	private ThreadLocal<List<SurveyMeasureResponse>> t = new ThreadLocal<List<SurveyMeasureResponse>>();

	public List<SurveyMeasureResponse> fetchCachedSmr(int vetAssId) {
		List<SurveyMeasureResponse> smrList = t.get();
		if (smrList == null) {
			t.set(smrr.findForVeteranAssessmentId(vetAssId));
			return fetchCachedSmr(vetAssId);
		} else {
			return smrList;
		}
	}

	/**
	 * clear the thread with its weight as we are in a thread pool
	 */
	public void clearSmrFromCache() {
		List<SurveyMeasureResponse> smrList = t.get();
		if (smrList != null && !smrList.isEmpty()) {
			smrList.clear();
		}

		t.remove();
	}
}
