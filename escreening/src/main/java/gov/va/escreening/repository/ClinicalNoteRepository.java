package gov.va.escreening.repository;

import gov.va.escreening.entity.ClinicalNote;

import java.util.List;

public interface ClinicalNoteRepository extends RepositoryInterface<ClinicalNote> {

    /**
     * Retrieves all the notes relevant for a program.
     * @param programId
     * @return
     */
    List<ClinicalNote> findAllForProgramId(int programId);
}
