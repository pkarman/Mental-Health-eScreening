package gov.va.escreening.service;

import gov.va.escreening.entity.DashboardAlert;
import gov.va.escreening.entity.HealthFactor;
import gov.va.escreening.entity.Measure;

/**
 * Service class used to manage Event entities
 * 
 * @author Robin Carnow
 *
 */
public interface EventService {
    
    /**
     * Updates the associated Event for the given {@link Measure}.
     * @param measure
     */
    public void updateMeasureEvent(Measure measure);
    
    /**
     * Updates the associated Event for the given {@link HealthFactor}
     * @param hf
     */
    public void updateHealthFactorEvent(HealthFactor hf);
    
    /**
     * Updates the associated Event for the given {@link DashboardAlert}
     * @param alert
     */
    public void updateDashboardAlertEvent(DashboardAlert alert);
}
