package gov.va.escreening.service;

import gov.va.escreening.dto.MeasureAnswerDTO;
import gov.va.escreening.dto.MeasureValidationSimpleDTO;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.MeasureType;

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

	List<MeasureType> loadAllMeasureTypes();

	gov.va.escreening.dto.ae.Measure findMeasure(Integer measureId);

	List<MeasureAnswerDTO> getMeasureAnswerValues(Integer measureId);

	List<MeasureValidationSimpleDTO> getMeasureValidations(Integer measureId);
	
	/**
	 * Retrieves the measure answer for the given ID.
	 * @param measureAnswerId
	 * @throws EntityNotFoundException if the ID given does not exist.
	 * @return
	 */
	MeasureAnswerDTO getMeasureAnswer(Integer measureAnswerId);
}
