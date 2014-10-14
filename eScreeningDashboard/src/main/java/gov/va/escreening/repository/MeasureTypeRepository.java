package gov.va.escreening.repository;

import gov.va.escreening.entity.MeasureType;

public interface MeasureTypeRepository extends RepositoryInterface<MeasureType> {
	
	public MeasureType findMeasureTypeByName(String name);
}