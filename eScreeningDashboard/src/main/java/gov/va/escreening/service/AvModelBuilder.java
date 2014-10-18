package gov.va.escreening.service;

import gov.va.escreening.entity.AssessmentVarChildren;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.MeasureAnswer;

public interface AvModelBuilder {
	void buildFromMeasure(AssessmentVariable av, AssessmentVarChildren avc,
			Measure m);

	void buildFromMeasureAnswer(AssessmentVariable av, AssessmentVarChildren avc,
			Measure m, MeasureAnswer ma);

	Object getResult();

}
