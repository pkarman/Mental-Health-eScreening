package gov.va.escreening.repository;

import gov.va.escreening.entity.ProgramSurvey;

import java.util.List;

public interface ProgramSurveyRepository extends RepositoryInterface<ProgramSurvey> {

    /**
     * Retrieves all surveys mapped to a program.
     * @param programId
     * @return
     */
    List<ProgramSurvey> findAllByProgramId(int programId);
}
