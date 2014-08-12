package gov.va.escreening.repository;

import gov.va.escreening.entity.ClinicalReminder;

import org.springframework.stereotype.Repository;

@Repository
public class ClinicalReminderRepositoryImpl extends AbstractHibernateRepository<ClinicalReminder> implements
        ClinicalReminderRepository {

    public ClinicalReminderRepositoryImpl() {
        super();

        setClazz(ClinicalReminder.class);
    }

}
