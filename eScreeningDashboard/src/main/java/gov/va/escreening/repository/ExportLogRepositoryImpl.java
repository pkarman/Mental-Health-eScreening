package gov.va.escreening.repository;

import gov.va.escreening.entity.ExportLog;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Repository
public class ExportLogRepositoryImpl extends AbstractHibernateRepository<ExportLog> implements ExportLogRepository {

    private static final Logger logger = LoggerFactory.getLogger(ExportLogRepositoryImpl.class);

    public ExportLogRepositoryImpl() {
        setClazz(ExportLog.class);
    }

    @Override
    public List<ExportLog> findAllForDays(int noOfDays) {
        Date startDate = (new DateTime()).plusDays(noOfDays * -1).toDate();
        TypedQuery<ExportLog> query = entityManager.createQuery("from ExportLog where dateCreated >= :startDate", ExportLog.class);
        query.setParameter("startDate", startDate);
        return query.getResultList();
    }

    @Override
    protected List<ExportLog> _findAll() {
        TypedQuery<ExportLog> query = entityManager.createNamedQuery("ExportLog.findAllMinusBytes", ExportLog.class);
        List<ExportLog> result =query.getResultList();
        return result;

    }

    @Override
    public Date findLastSnapshotDate() {
        TypedQuery<Date> query = entityManager.createNamedQuery("ExportLog.findLastSnapshotDate", Date.class);
        Date date = query.getSingleResult();
        return date;
    }
}
