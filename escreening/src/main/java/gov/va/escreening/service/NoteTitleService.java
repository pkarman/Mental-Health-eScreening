package gov.va.escreening.service;

import gov.va.escreening.dto.DropDownObject;
import gov.va.escreening.entity.NoteTitle;

import java.util.List;

public interface NoteTitleService {

    /**
     * Retrieves the note titles that are associated with 'programId'.
     * @param programId
     * @return
     */
    List<DropDownObject> getNoteTitleList(Integer programId);

    /**
     * Retrieves all the note titles.
     * @return
     */
    List<DropDownObject> getNoteTitleList();

    /**
     * Retrieves a read-only list of all the note titles in the database.
     * @return
     */
    List<NoteTitle> findAll();

    /**
     * Create a new Note Title and returns its 'noteTitleId'.
     * @param name
     * @param vistaIen
     * @return
     */
    Integer create(String name, String vistaIen);
}
