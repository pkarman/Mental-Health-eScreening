package gov.va.escreening.repository;

import java.util.List;

import gov.va.escreening.entity.VariableTemplate;

public interface VariableTemplateRepository extends RepositoryInterface<VariableTemplate> {

	List<VariableTemplate> findByIds(List<Integer> variableTemplateIds);

}
