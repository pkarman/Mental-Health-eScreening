package gov.va.escreening.service;

import gov.va.escreening.dto.VeteranAssessmentNoteDto;

import java.util.List;

public interface VeteranAssessmentNoteService {

    /**
     * Retrieves all the clinical notes associated with veteran assessment.
     * @param veteranAssessmentId
     * @return
     */
    List<VeteranAssessmentNoteDto> findAllByVeteranAssessmentId(int veteranAssessmentId);
}
