package gov.va.escreening.repository;

import gov.va.escreening.entity.RuleEvent;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;


/**
 * Created by mzhu on 9/5/15.
 */
@Repository
@Transactional
public class RuleEventRepositoryImpl extends AbstractHibernateRepository<RuleEvent> implements RuleEventRepo {

    public RuleEventRepositoryImpl() {
        super();
        setClazz(RuleEvent.class);
    }

    @Override
    public void deleteRuleEventByEventId(int eventId) {
        String sql = "delete from RuleEvent where event.eventId = :eventId";
        Query q = entityManager.createQuery(sql).setParameter("eventId", eventId);
        q.executeUpdate();
    }
}
