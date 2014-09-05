package gov.va.escreening.assessments.test;

import gov.va.escreening.entity.AssessmentStatus;
import gov.va.escreening.entity.Battery;
import gov.va.escreening.entity.Clinic;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.entity.User;
import gov.va.escreening.entity.Veteran;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.repository.VeteranAssessmentRepository;

import java.util.Calendar;

import javax.annotation.Resource;

public abstract class AssessmentTestBase {
	protected static final int TEST_USER_ID = 5;
	protected static final int TEST_VET_ID = 18;

	@Resource
	protected VeteranAssessmentRepository vetAssessmentRepo;

	protected SurveyMeasureResponse createResponse(int measureId,
			int measureAnswerId, boolean value, int assessmentId, int surveyId) {
		SurveyMeasureResponse res = new SurveyMeasureResponse();
		res.setBooleanValue(value);
		res.setMeasure(new Measure(measureId));
		res.setMeasureAnswer(new MeasureAnswer(measureAnswerId));
		res.setVeteranAssessment(new VeteranAssessment(assessmentId));
		res.setSurvey(new Survey(surveyId));
		return res;
	}

	protected SurveyMeasureResponse createResponse(int measureId,
			int measureAnswerId, long value, int assessmentId, int surveyId) {
		SurveyMeasureResponse res = new SurveyMeasureResponse();
		res.setNumberValue(value);
		res.setMeasure(new Measure(measureId));
		res.setMeasureAnswer(new MeasureAnswer(measureAnswerId));
		res.setVeteranAssessment(new VeteranAssessment(assessmentId));
		res.setSurvey(new Survey(surveyId));
		return res;
	}

	protected SurveyMeasureResponse createResponse(int measureId,
			int measureAnswerId, String value, int assessmentId, int surveyId) {
		SurveyMeasureResponse res = new SurveyMeasureResponse();
		res.setTextValue(value);
		res.setMeasure(new Measure(measureId));
		res.setMeasureAnswer(new MeasureAnswer(measureAnswerId));
		res.setVeteranAssessment(new VeteranAssessment(assessmentId));
		res.setSurvey(new Survey(surveyId));
		return res;
	}

	protected VeteranAssessment createTestAssessment() {
		VeteranAssessment assessment = new VeteranAssessment();
		assessment.setBattery(new Battery(1));
		assessment.setClinic(new Clinic(20)); // Observation
		assessment.setClinician(new User(TEST_USER_ID));
		assessment.setCreatedByUser(new User(TEST_USER_ID));
		assessment.setVeteran(new Veteran(TEST_VET_ID));
		assessment.setDateCreated(Calendar.getInstance().getTime());
		assessment.setAssessmentStatus(new AssessmentStatus(2));

		vetAssessmentRepo.create(assessment);
		// vetAssessmentRepo.commit();

		assessment = vetAssessmentRepo.findByVeteranId(TEST_VET_ID).get(0);
		return assessment;
	}

}
