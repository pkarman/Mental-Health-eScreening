package gov.va.escreening.service;

import java.util.Collection;

import gov.va.escreening.entity.AssessmentVarChildren;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.Survey;

public interface AvBuilder<T> {
	void buildFromMeasure(AssessmentVariable av, AssessmentVarChildren avc,
			Measure m);

	void buildFromMeasureAnswer(AssessmentVariable av,
			AssessmentVarChildren avc, Measure m, MeasureAnswer ma);

	void buildFormula(Survey survey, AssessmentVariable av,
			Collection<Measure> smList, Collection<AssessmentVariable> avList,
			boolean filterMeasures);

	T getResult();

}
