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
	 * @return
	 */
	public Table<String, String, Object> getAssessmentVarsFor(int surveyId);

	Multimap<Survey, Measure> buildSurveyMeasuresMap();

	Collection<AssessmentVariable> findAllFormulae();

	void filterBySurvey(Survey survey,
			AvModelBuilder dataExtractor,
			Collection<Measure> smList, Collection<AssessmentVariable> avList);
}
