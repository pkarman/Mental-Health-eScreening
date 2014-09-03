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

	public List<SurveyMeasureResponse> fetchCachedSmr() {
		return t.get();
	}

	public void loadSmrFromDb(int vetAssId) {
		t.set(smrr.findForVeteranAssessmentId(vetAssId));
	}
}
