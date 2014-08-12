package gov.va.escreening.repository;

import gov.va.escreening.entity.ClinicSurvey;

import java.util.List;

public interface ClinicSurveyRepository extends RepositoryInterface<ClinicSurvey> {

    /**
     * Retrieves all the surveys mapped to a clinic.
     * @param clinicId
     * @return
     */
    List<ClinicSurvey> findAllByClinicId(int clinicId);
}
