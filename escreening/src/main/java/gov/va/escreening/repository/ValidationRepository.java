package gov.va.escreening.repository;

import gov.va.escreening.entity.Validation;

public interface ValidationRepository extends RepositoryInterface<Validation> 
{
	public Validation findValidationByCode(String code);
}