package gov.va.escreening.repository;

import gov.va.escreening.entity.VeteranAssessmentNote;

import java.util.List;

public interface VeteranAssessmentNoteRepository extends RepositoryInterface<VeteranAssessmentNote> {

    /**
     * Retrieves all the clinical notes associated with 'veteranAssessmentId'
     * @param veteranAssessmentId
     * @return
     */
    List<VeteranAssessmentNote> findAllByVeteranAssessmentId(int veteranAssessmentId);
}
