package gov.va.escreening.service;

import gov.va.escreening.entity.ClinicalReminderSurvey;

import java.util.List;

public interface ClinicalReminderSurveyService {

    List<ClinicalReminderSurvey> findAllByVistaIen(String vistaIen);
}
