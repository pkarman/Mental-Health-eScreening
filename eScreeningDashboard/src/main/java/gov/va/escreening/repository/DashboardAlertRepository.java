package gov.va.escreening.repository;

import gov.va.escreening.entity.DashboardAlert;

/**
 * Repository for {@link DashboardAlert} entities<br/>
 * <b>PLEASE NOTE:</b> if you update or create a Dashboard Alert please call eventService.updateDashboardAlertEvent(alert)<br/>
 * 
 * @author Robin Carnow
 *
 */
public interface DashboardAlertRepository extends RepositoryInterface<DashboardAlert>{

    //PLEASE NOTE: if you update or create a Dashboard Alert please call eventService.updateDashboardAlertEvent(alert)
}
