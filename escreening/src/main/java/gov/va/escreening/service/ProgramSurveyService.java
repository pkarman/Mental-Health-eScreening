package gov.va.escreening.service;

import gov.va.escreening.entity.ProgramSurvey;

import java.util.List;

public interface ProgramSurveyService {

    /**
     * Retrieves all the surveys mapped to a program.
     * @param programId
     * @return
     */
    List<ProgramSurvey> findAllByProgramId(int programId);
}
