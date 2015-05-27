package gov.va.escreening.repository;

import gov.va.escreening.entity.HealthFactor;

/**
 * Repository for managing {@link HealthFactor} entities<br/>
 * <b>PLEASE NOTE:</b> if you update or create a health factor please call eventService.updateHealthFactorEvent(hf)<br/>
 * 
 * @author Robin Carnow
 *
 */
public interface HealthFactorRepository extends RepositoryInterface<HealthFactor>{

    //PLEASE NOTE: if you update or create a health factor please call eventService.updateHealthFactorEvent(hf) 
}
