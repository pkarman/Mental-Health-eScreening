package gov.va.escreening.repository;

import gov.va.escreening.entity.ExportLogData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ExportLogDataRepositoryImpl extends AbstractHibernateRepository<ExportLogData> implements ExportLogDataRepository {

	private static final Logger logger = LoggerFactory.getLogger(ExportLogDataRepositoryImpl.class);

	public ExportLogDataRepositoryImpl() {
		setClazz(ExportLogData.class);
	}
}
