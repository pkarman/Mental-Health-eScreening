package gov.va.escreening.repository;

import gov.va.escreening.entity.Program;

import java.util.List;

public interface ProgramRepository extends RepositoryInterface<Program> {

    /**
     * Retrieves all program.
     * @return
     */
    List<Program> getProgramList();

    /**
     * Retrieves all the active programs.
     * @return
     */
    List<Program> getActiveProgramList();

    /**
     * Retrieves all the programs for programIdList.
     * @param programIdList
     * @return
     */
    List<Program> findByProgramIdList(List<Integer> programIdList);
}