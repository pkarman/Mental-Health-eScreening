package gov.va.escreening.service;

import gov.va.escreening.domain.ProgramDto;
import gov.va.escreening.dto.DropDownObject;
import gov.va.escreening.form.ProgramEditViewFormBean;

import java.util.List;

public interface ProgramService {

    /**
     * Retrieves all programs.
     * @return
     */
    List<ProgramDto> getProgramList();

    /**
     * Retrieves all programs.
     * @return
     */
    List<DropDownObject> getDropdownObjects();

    /**
     * Retrieves programs as a DropDownObject list.
     * @return
     */
    List<DropDownObject> getProgramDropDownObjects(List<Integer> programIdList);

    /**
     * Returns a program.
     * @param programId
     * @return
     */
    ProgramDto find(int programId);

    /**
     * Returns the form bean.
     * @param programId
     * @return
     */
    ProgramEditViewFormBean getProgramEditViewFormBean(int programId);

    /**
     * Creates a new program record and associates clinics and note titles.
     * @param name
     * @param isDisabled
     * @param clinicIdList
     * @param noteTitleIdList
     */
    Integer createProgram(String name, Boolean isDisabled, List<Integer> clinicIdList, List<Integer> noteTitleIdList);

    /**
     * Updates program with name and merges changes in the 'clinicIdList' and 'noteTitleIdList'.
     * @param programId
     * @param name
     * @param isDisabled
     * @param clinicIdList
     * @param noteTitleIdList
     */
    void updateProgram(int programId, String name, Boolean isDisabled, List<Integer> batteryIdList, List<Integer> clinicIdList, List<Integer> noteTitleIdList);

    /**
     * Updates the program status to 'isDisabled'
     * @param programId
     * @param isDisabled
     */
    void updatePrgoramStatus(int programId, boolean isDisabled);
}