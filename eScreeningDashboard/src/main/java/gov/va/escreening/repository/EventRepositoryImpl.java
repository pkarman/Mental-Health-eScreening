package gov.va.escreening.repository;

import gov.va.escreening.entity.Event;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class EventRepositoryImpl extends AbstractHibernateRepository<Event> 
    implements EventRepository {

    private static final Logger logger = LoggerFactory.getLogger(EventRepositoryImpl.class);

    public EventRepositoryImpl() {
        super();

        setClazz(Event.class);
    }
    
    @Override
    public List<Event> getEventByTypeFilteredByObjectIds(int eventTypeId, Collection<Integer> objectIds){
        
        logger.debug("Getting events with type {} and filtered by {} related object IDs", eventTypeId, objectIds.size());
        
        if(objectIds.isEmpty())
            return Collections.emptyList();
        
        String sql = "SELECT e FROM Event e WHERE e.eventType.eventTypeId = :eventTypeId and e.relatedObjectId IN (:objectIds)";
        
        return entityManager
            .createQuery(sql, Event.class)
            .setParameter("eventTypeId", eventTypeId)
            .setParameter("objectIds", objectIds)
            .getResultList();
    }
}
