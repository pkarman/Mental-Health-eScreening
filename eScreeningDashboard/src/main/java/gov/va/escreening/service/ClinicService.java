package gov.va.escreening.service;

import gov.va.escreening.domain.ClinicDto;
import gov.va.escreening.dto.DropDownObject;
import gov.va.escreening.entity.Clinic;

import java.util.List;

public interface ClinicService {

    /**
     * Retrieves all the clinics.
     * @return
     */
    List<Clinic> getClinics();

    /**
     * Retrieves all the clinic DTO
     * @return
     */
    List<ClinicDto> getClinicDtoList();

    /**
     * Retrieves all the clinics for 'programId'
     * @param programId
     * @return
     */
    List<DropDownObject> getDropDownObjectsByProgramId(int programId);

    /**
     * Creates a clinic and returns its 'clinicId'
     * @param name
     * @param vistaIen
     * @return
     */
    Integer create(String name, String vistaIen);
}
