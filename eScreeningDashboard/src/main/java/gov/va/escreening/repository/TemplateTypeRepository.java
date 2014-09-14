package gov.va.escreening.repository;

import java.util.List;

import gov.va.escreening.entity.TemplateType;

public interface TemplateTypeRepository extends RepositoryInterface<TemplateType>{

	List<TemplateType> findAllOrderByName();

}
