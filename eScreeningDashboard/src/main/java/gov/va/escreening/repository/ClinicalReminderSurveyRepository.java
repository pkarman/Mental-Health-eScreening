package gov.va.escreening.repository;

import gov.va.escreening.entity.ClinicalReminderSurvey;

import java.util.List;

public interface ClinicalReminderSurveyRepository extends RepositoryInterface<ClinicalReminderSurvey> {

    /**
     * Retrieves all surveys mapped to a clinical note using 'vistaIen'.
     * @param vistaIen
     * @return
     */
    List<ClinicalReminderSurvey> findAllByVistaIen(String vistaIen);
    
    /**
     * remove clinical reminder survey mappings for the survey
     * @param surveyID
     */
    void removeSurveyMapping(int surveyID);
    
    void createClinicalReminderSurvey(int clinicalReminderId, int surveyId);
}
