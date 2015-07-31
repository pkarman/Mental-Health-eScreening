package gov.va.escreening.service;

import gov.va.escreening.entity.ClinicSurvey;

import java.util.List;

public interface ClinicSurveyService {

    /**
     * Retrieves all the surveys associated with the clinic.
     * @param clinicId
     * @return
     */
    List<ClinicSurvey> findAllByClinicId(int clinicId);
}
