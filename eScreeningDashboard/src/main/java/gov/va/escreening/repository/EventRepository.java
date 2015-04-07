package gov.va.escreening.repository;

import gov.va.escreening.entity.Event;

import java.util.Collection;
import java.util.List;

public interface EventRepository extends RepositoryInterface<Event> {
    
    /**
     * Gets all events with the given event type and with a related object ID contained in the give collection.
     * Note: Only events which are associated with a rule will be returned.
     * @param eventType
     * @param objectIds
     * @return
     */
    public List<Event> getEventByTypeFilteredByObjectIds(int eventType, Collection<Integer> objectIds);

    /**
     * Gets all events of a given type
     * @param eventTypeId
     * @return
     */
    List<Event> getEventByType(int eventTypeId);
}
