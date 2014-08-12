package gov.va.escreening.repository;

import gov.va.escreening.entity.Clinic;

import java.util.List;

public interface ClinicRepository extends RepositoryInterface<Clinic> {

    /**
     * Retrieves all the clinics associated with 'programId'
     * @param programId
     * @return
     */
    List<Clinic> findByProgramId(int programId);
}
