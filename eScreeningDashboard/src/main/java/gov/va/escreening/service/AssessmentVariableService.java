package gov.va.escreening.service;

import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.exception.EntityNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Multimap;
import com.google.common.collect.Table;

public interface AssessmentVariableService {
    Collection<AssessmentVariable> findByDisplayNames(List<String> displayNames);

    /**
     * return all {@link AssessmentVariable} as a table of name value pair belonging to requested survey
     * <p/>
     * {@link Table} Table<String, String, String> is defined as row, column name, and column value
     *
     * @param surveyId {@link AssessmentVariable} which belongs to this surveyId will be returned
     * @return
     */
    Table<String, String, Object> getAssessmentVarsForSurvey(int surveyId, boolean ignoreAnswers, boolean includeFormulaTokens);

    /**
     * Retrieves all assessment variables contained in a battery which means that all measures of the relevant
     * type for each survey currently associated with the battery will be returned.
     *
     * @param batteryId
     * @return all {@link AssessmentVariable} as a table of name value pair belonging to requested battery.
     * {@link Table} Table<String, String, String> is defined as row, column name, and column value
     */
    Table<String, String, Object> getAssessmentVarsForBattery(int batteryId);


    Multimap<Survey, Measure> buildSurveyMeasuresMap();

    Collection<AssessmentVariable> findAllFormulas();

	void filterBySurvey(Survey survey, AvBuilder<?> avModelBldr,
                        Collection<Measure> smList, Collection<AssessmentVariable> avList,
                        boolean filterMeasures, boolean includeFormulaTokens);

    boolean compareMeasure(AssessmentVariable av, Measure m);

    boolean compareMeasureAnswer(AssessmentVariable av, Measure m);

	/**
	 * Retrieves all assessment variables associated with the given measure and its child measures if applicable. Does not include answer AVs.
	 * @param measureId 
	 * @return Map from AV ID to the AV object
	 */
	Map<Integer, AssessmentVariable> getAssessmentVarsForMeasure(Integer measureId);
	
	/**
	 * @param measureAnswerId measure answer to pull AV for. This method gives back a single 
	 * AV which is because the relationship should be one-to-one. This will be updated soon so 
	 * for now this will return only one.
	 * @return the AV associated with the given answer
	 * @throws EntityNotFoundException if no AV is found for the given answer
	 */
	AssessmentVariable getAssessmentVarsForAnswer(Integer measureAnswerId);
	
	List<Map<String, String>> askFormulasFor(Integer moduleId);

	String getPlainText(String htmlText);

    AssessmentVariable findById(Integer variableId);
}
