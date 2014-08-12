package gov.va.escreening.service;

import gov.va.escreening.entity.ClinicalReminder;

import java.util.List;

public interface ClinicalReminderService {

    /**
     * Retrieves all the Clinical Reminder as a read-only list.
     * @return
     */
    List<ClinicalReminder> findAll();

    /**
     * Creates a new Clinical Reminder and returns its 'clinicalReminderId'.
     * @param name
     * @param vistaIen
     * @param printName
     * @param clinicalReminderClassCode
     * @return
     */
    Integer create(String name, String vistaIen, String printName, String clinicalReminderClassCode);
}
