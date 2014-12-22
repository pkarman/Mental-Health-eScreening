package gov.va.escreening.service;

import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.Survey;

import java.util.Collection;

import com.google.common.collect.Multimap;
import com.google.common.collect.Table;

public interface AssessmentVariableService {
	/**
	 * return all {@link AssessmentVariable} as a table of name value pair belonging to requested survey
	 * 
	 * {@link Table} Table<String, String, String> is defined as row, column name, and column value
	 * 
	 * @param surveyId
	 *            {@link AssessmentVariable} which belongs to this surveyId will be returned
	 * 
	 * @return
	 */
	Table<String, String, Object> getAssessmentVarsForSurvey(int surveyId);

	/**
	 * Retrieves all assessment variables contained in a battery which means that all measures of the relevant 
	 * type for each survey currently associated with the battery will be returned. 
	 * 
	 * @param batteryId
	 * 
	 * @return all {@link AssessmentVariable} as a table of name value pair belonging to requested battery. 
	 * {@link Table} Table<String, String, String> is defined as row, column name, and column value
	 */
	Table<String, String, Object> getAssessmentVarsForBattery(int batteryId);

	
	Multimap<Survey, Measure> buildSurveyMeasuresMap();

	Collection<AssessmentVariable> findAllFormulae();

	void filterBySurvey(Survey survey, AvBuilder avModelBldr,
			Collection<Measure> smList, Collection<AssessmentVariable> avList,
			boolean filterMeasures);

	boolean compareMeasure(AssessmentVariable av, Measure m);

	boolean compareMeasureAnswer(AssessmentVariable av, Measure m);
}
