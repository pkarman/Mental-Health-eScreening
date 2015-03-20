/**
 * 
 */
package gov.va.escreening.repository;

import javax.persistence.Query;

import gov.va.escreening.entity.DashboardAlert;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default DashboardAlert repository
 * 
 * @author Robin Carnow
 *
 */
@Repository
@Transactional
public class DashboardAlertRepositoryImpl extends AbstractHibernateRepository<DashboardAlert> implements DashboardAlertRepository {
    public DashboardAlertRepositoryImpl() {
        super();

        setClazz(DashboardAlert.class);
    }
    
}
