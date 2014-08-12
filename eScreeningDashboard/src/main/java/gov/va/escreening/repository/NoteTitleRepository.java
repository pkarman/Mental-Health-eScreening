package gov.va.escreening.repository;

import gov.va.escreening.entity.NoteTitle;

import java.util.List;

public interface NoteTitleRepository extends RepositoryInterface<NoteTitle> {

    /**
     * Retrieves the note titles that are associated with either the 'programId'.
     * @param programId
     * @return
     */
    List<NoteTitle> getNoteTitleList(Integer programId);

    /**
     * Retrieves all the note titles.
     * @return
     */
    List<NoteTitle> getNoteTitleList();
}
