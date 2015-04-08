/**
 * 
 */
package gov.va.escreening.repository;

import gov.va.escreening.entity.DashboardAlert;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default DashboardAlert repository<br/>
 * <b>PLEASE NOTE:</b> if you update or create a Dashboard Alert please call eventService.updateDashboardAlertEvent(alert) 
 * 
 * @author Robin Carnow
 *
 */
@Repository
@Transactional
public class DashboardAlertRepositoryImpl extends AbstractHibernateRepository<DashboardAlert> implements DashboardAlertRepository {
    
    //PLEASE NOTE: if you update or create a Dashboard Alert please call eventService.updateDashboardAlertEvent(alert) 
    
    public DashboardAlertRepositoryImpl() {
        super();

        setClazz(DashboardAlert.class);
    }
    
}
