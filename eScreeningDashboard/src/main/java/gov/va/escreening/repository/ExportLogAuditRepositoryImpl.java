package gov.va.escreening.repository;

import gov.va.escreening.entity.ExportLog;
import gov.va.escreening.entity.ExportLogAudit;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Repository
public class ExportLogAuditRepositoryImpl extends AbstractHibernateRepository<ExportLogAudit> implements ExportLogAuditRepository {

	private static final Logger logger = LoggerFactory.getLogger(ExportLogAuditRepositoryImpl.class);

	public ExportLogAuditRepositoryImpl() {
		setClazz(ExportLogAudit.class);
	}

	@Override
	public List<ExportLogAudit> findAllForDays(int noOfDays) {
		//TODO implement this cotrrectly
		return findAll();
	}
}
