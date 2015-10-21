package gov.va.escreening.service;

import static com.google.common.base.Preconditions.*;
import static gov.va.escreening.constants.RuleConstants.*;

import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.entity.DashboardAlert;
import gov.va.escreening.entity.Event;
import gov.va.escreening.entity.EventType;
import gov.va.escreening.entity.HealthFactor;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.repository.EventRepository;
import gov.va.escreening.repository.EventTypeRepository;

import gov.va.escreening.repository.RuleEventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;

@Transactional
@Service
public class EventServiceImpl implements EventService {
    private static final int MAX_NAME_LENGTH = 50;
    
    private final EventRepository eventRepo;
    private final EventTypeRepository eventTypeRepo;

    private final RuleEventRepo ruleEventRepo;
    
    @Autowired
    EventServiceImpl(
            EventRepository eventRepo,
            EventTypeRepository eventTypeRepo, RuleEventRepo ruleEventRepo){
        
        this.eventRepo = checkNotNull(eventRepo);
        this.eventTypeRepo = checkNotNull(eventTypeRepo);
        this.ruleEventRepo = checkNotNull(ruleEventRepo);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateMeasureEvent(Measure measure){
        //Ticket 988 -- measure event should be created even if the measure is an instruction type.
        //if(measure.getMeasureType().getMeasureTypeId() != AssessmentConstants.MEASURE_TYPE_INSTRUCTION){
            updateEvent(measure.getMeasureId(), EVENT_TYPE_SHOW_QUESTION, measure.getVariableName(), measure.getMeasureText());
       // }
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateHealthFactorEvent(HealthFactor hf) {
        updateEvent(hf.getHealthFactorId(), EVENT_TYPE_HEALTH_FACTOR, hf.getName());
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateDashboardAlertEvent(DashboardAlert alert) {
        updateEvent(alert.getDashboardAlertId(), EVENT_TYPE_DASHBOARD_ALERT, alert.getName());
    }
    
    private void updateEvent(int relatedObjectId, int typeId, String... possibleNames){
        String name = truncateName(createName(typeId, relatedObjectId, possibleNames));
        
        Event event = eventRepo.getEventForObject(relatedObjectId, typeId);
        if(event == null){
            EventType type = eventTypeRepo.findOne(typeId);
            event = new Event(truncateName(name), relatedObjectId, type);
            eventRepo.create(event);
        }
        else{
            event.setName(truncateName(name));
            eventRepo.update(event);
        }
    }
    
    /**
     * Figures out the name of the event given the parameters passed in.
     * @param typeId EventType id
     * @param relatedObjectId 
     * @param possibleNames array of possible names to choose from in order
     * @return
     */
    private String createName(int typeId, int relatedObjectId, String[] possibleNames){
        for(String possibleName : possibleNames){
            if(!Strings.isNullOrEmpty(possibleName)){
                return possibleName;
            }
        }
        //If none of the possible names will work generate one
        switch(typeId){
        case EVENT_TYPE_SHOW_QUESTION:
            return "question_" + relatedObjectId;
        case EVENT_TYPE_HEALTH_FACTOR:
            return "health_factor_" + relatedObjectId;
        case EVENT_TYPE_DASHBOARD_ALERT:
            return "alert_" + relatedObjectId;
        case EVENT_TYPE_CONSULT:
            return "consult_" + relatedObjectId;
        }
        
        throw new IllegalArgumentException("Event with type ID " + typeId + " is not supported");
    }
    
    private String truncateName(String name){
        return name.substring(0, Math.min(name.length(), MAX_NAME_LENGTH));
    }

    @Transactional
    @Override
    public void deleteEventForHealthFactor(HealthFactor hf)
    {
        Event event = eventRepo.getEventForObject(hf.getHealthFactorId(), EVENT_TYPE_HEALTH_FACTOR);
        if(event != null)
        {
            ruleEventRepo.deleteRuleEventByEventId(event.getEventId());
            eventRepo.delete(event);
        }
    }
}
