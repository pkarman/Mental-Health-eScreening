package gov.va.escreening.repository;

import java.util.List;

import gov.va.escreening.entity.AssessmentVariable;

public interface AssessmentVariableRepository extends RepositoryInterface<AssessmentVariable> {

	List<AssessmentVariable> findAllFormulae();

}
