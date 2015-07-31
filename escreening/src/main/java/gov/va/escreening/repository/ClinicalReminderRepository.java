package gov.va.escreening.repository;

import gov.va.escreening.entity.ClinicalReminder;

import java.util.List;
import java.util.Map;

public interface ClinicalReminderRepository extends RepositoryInterface<ClinicalReminder> {

    Map<String, ClinicalReminder> deriveAllowedClinicalReminders();
}
