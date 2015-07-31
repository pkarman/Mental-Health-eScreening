package gov.va.escreening.repository;

import org.springframework.stereotype.Repository;

import gov.va.escreening.entity.EventType;

@Repository
public class EventTypeRepositoryImpl extends AbstractHibernateRepository<EventType> implements EventTypeRepository {

    public EventTypeRepositoryImpl(){
        super();
        setClazz(EventType.class);
    }
}
