package gov.va.escreening.repository;

import gov.va.escreening.entity.ExportType;

import org.springframework.stereotype.Repository;

@Repository
public class ExportTypeRepositoryImpl extends AbstractHibernateRepository<ExportType> implements
	ExportTypeRepository {
	
	public ExportTypeRepositoryImpl() {
		super();
		setClazz(ExportType.class);
	}
}