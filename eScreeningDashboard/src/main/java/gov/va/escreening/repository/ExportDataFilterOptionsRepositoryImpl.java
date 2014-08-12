package gov.va.escreening.repository;

import gov.va.escreening.entity.ExportdataFilterOptions;

import org.springframework.stereotype.Repository;

@Repository
public class ExportDataFilterOptionsRepositoryImpl extends AbstractHibernateRepository<ExportdataFilterOptions> 
	implements ExportDataFilterOptionsRepository {
	
    public ExportDataFilterOptionsRepositoryImpl() {
        super();

        setClazz(ExportdataFilterOptions.class);
    }
}