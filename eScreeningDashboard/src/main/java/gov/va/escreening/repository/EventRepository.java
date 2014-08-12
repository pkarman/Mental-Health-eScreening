package gov.va.escreening.repository;

import gov.va.escreening.entity.Event;

import java.util.Collection;
import java.util.List;

public interface EventRepository extends RepositoryInterface<Event> {
    
    /**
     * Gets all events with the given event type and with a related object ID contained in the give collection.
     * @param eventType
     * @param objectIds
     * @return
     */
    public List<Event> getEventByTypeFilteredByObjectIds(int eventType, Collection<Integer> objectIds);
}
