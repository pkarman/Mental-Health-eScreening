package gov.va.escreening.dto.rule;

import gov.va.escreening.entity.Event;


public class EventDto {
    
    Integer id;
    Integer type;
    String name;
    
    public EventDto(Event dbEvent){
        id = dbEvent.getEventId();
        type = dbEvent.getEventType().getEventTypeId();
        name = dbEvent.getName();
    }
    
    public Integer getId() {
        return id;
    }
    
    public Integer getType() {
        return type;
    }
   
    public String getName() {
        return name;
    }
    
    /**
     * Needed for json decoding. please use the other constructor 
     */
    @SuppressWarnings("unused")
    private EventDto(){}
}
