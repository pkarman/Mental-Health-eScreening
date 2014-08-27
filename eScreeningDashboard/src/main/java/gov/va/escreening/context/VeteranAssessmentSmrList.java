package gov.va.escreening.context;

import gov.va.escreening.entity.SurveyMeasureResponse;

import java.util.List;

public class VeteranAssessmentSmrList {
	private static ThreadLocal<List<SurveyMeasureResponse>> t = new ThreadLocal<List<SurveyMeasureResponse>>();

	public static List<SurveyMeasureResponse> get() {
		return t.get();
	}

	public static void save(List<SurveyMeasureResponse> smrList) {
		t.set(smrList);
	}
}
