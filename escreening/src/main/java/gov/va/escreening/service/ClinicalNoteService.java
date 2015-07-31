package gov.va.escreening.service;

import gov.va.escreening.domain.ClinicalNoteDto;

import java.util.List;

public interface ClinicalNoteService {

    /**
     * Retrieves all the clinical notes associated with 'programId'
     * @param programId
     * @return
     */
    List<ClinicalNoteDto> findAllForProgramId(int programId);
}
