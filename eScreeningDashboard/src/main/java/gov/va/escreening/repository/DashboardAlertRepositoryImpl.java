/**
 * 
 */
package gov.va.escreening.repository;

import gov.va.escreening.entity.DashboardAlert;

import org.springframework.stereotype.Repository;

/**
 * Default DashboardAlert repository
 * 
 * @author Robin Carnow
 *
 */
@Repository
public class DashboardAlertRepositoryImpl extends AbstractHibernateRepository<DashboardAlert> implements DashboardAlertRepository {
    public DashboardAlertRepositoryImpl() {
        super();

        setClazz(DashboardAlert.class);
    }
}
