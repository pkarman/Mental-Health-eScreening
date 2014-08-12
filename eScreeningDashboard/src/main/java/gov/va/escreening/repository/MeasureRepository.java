package gov.va.escreening.repository;

import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.MeasureType;

import java.util.List;

public interface MeasureRepository extends RepositoryInterface<Measure> {
	/**
	 * Retrieves a list of measureids for the specified survey and measuretype 
	 * @param surveyId
	 * @param measureType
	 * @return
	 */
	List<Measure> getMeasureForTypeSurvey(Integer surveyId, MeasureType measureType);
	
	/**
	 * Get the child measures for a given parentMeasureId
	 * @param parentMeasureId
	 * @return
	 */
	List<Integer> getChildMeasureIds(Measure parentMeasureId);

	/**
	 * Get the child measures for a given parentMeasureId
	 * @param parentMeasureId
	 * @return
	 */
	List<Measure> getChildMeasures(Measure parentMeasure);
	
	/**
	 * Get all of the measures associated to the given survey
	 * @param surveyId
	 * @return
	 */
	List<Measure> getMeasuresBySurvey(Integer surveyId);

    public abstract List<gov.va.escreening.dto.ae.Measure> getMeasureDtoBySurveyID(int surveyID);

    public abstract void updateMeasure(gov.va.escreening.dto.ae.Measure measureDto);
}