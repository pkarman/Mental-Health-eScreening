package gov.va.escreening.repository;

import gov.va.escreening.entity.ExportLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ExportLogRepositoryImpl extends AbstractHibernateRepository<ExportLog> implements
	ExportLogRepository {

    private static final Logger logger = LoggerFactory.getLogger(ExportLogRepositoryImpl.class);
	
	public ExportLogRepositoryImpl() {
		super();
		setClazz(ExportLog.class);
	}
}
