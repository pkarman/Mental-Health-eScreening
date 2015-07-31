package gov.va.escreening.repository;

import com.google.common.collect.Maps;
import gov.va.escreening.entity.ClinicalReminder;

import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

@Repository
public class ClinicalReminderRepositoryImpl extends AbstractHibernateRepository<ClinicalReminder> implements
        ClinicalReminderRepository {

    public ClinicalReminderRepositoryImpl() {
        setClazz(ClinicalReminder.class);
    }

    @Override
    public Map<String, ClinicalReminder> deriveAllowedClinicalReminders() {
        String sql = "SELECT cr FROM ClinicalReminder cr WHERE cr.id in (select distinct clinicalReminder from ClinicalReminderSurvey)";

        TypedQuery<ClinicalReminder> query = entityManager.createQuery(sql, ClinicalReminder.class);
        List<ClinicalReminder> crLst = query.getResultList();

        Map<String, ClinicalReminder> crMap = Maps.newHashMap();
        for (ClinicalReminder cr : crLst) {
            crMap.put(cr.getVistaIen(), cr);
        }
        return crMap;
    }
}
