package gov.va.escreening.dto;

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
}
