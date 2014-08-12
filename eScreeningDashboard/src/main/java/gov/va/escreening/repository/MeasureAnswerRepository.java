package gov.va.escreening.repository;

import gov.va.escreening.entity.MeasureAnswer;

import java.util.List;


public interface MeasureAnswerRepository extends RepositoryInterface<MeasureAnswer> {
	
	/**
	 * Retrieve a list of answers for the specified measure
	 * @param measureId
	 * @return
	 */
	List<MeasureAnswer> getAnswersForMeasure(Integer measureId);
}
