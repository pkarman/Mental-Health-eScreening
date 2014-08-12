package gov.va.escreening.service;

import gov.va.escreening.entity.Measure;

import java.util.List;

public interface MeasureService {
	/**
	 * Retrieve a list of measures of type table for the specified survey
	 * @param surveyId
	 * @return
	 */
	List<Measure> getMeasureIdsForTableTypeQuestions(Integer surveyId);
	
	/**
	 * Retrieves a list of measures for the specified survey
	 * @param surveyId
	 * @return
	 */
	List<Measure> getMeasuresBySurvey(Integer surveyId);
}