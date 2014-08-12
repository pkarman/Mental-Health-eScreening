package gov.va.escreening.service;

import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.dto.SearchAttributes;
import gov.va.escreening.dto.dashboard.SearchResult;
import gov.va.escreening.dto.dashboard.VeteranSearchResult;
import gov.va.escreening.form.CreateVeteranFormBean;

import java.util.List;

public interface VeteranService {

    /**
     * Create a new veteran record.
     * @param veteran
     * @return
     */
    VeteranDto add(VeteranDto veteran);

    /**
     * Create a new veteran record using lastName and ssnLastFour.
     * @param lastName
     * @param ssnLastFour
     * @return
     */
    VeteranDto add(String lastName, String ssnLastFour);

    /**
     * Searches for the veterans in the local db using exact matches to supplied parameters
     * @param veteran
     * @return
     */
    List<VeteranDto> findVeterans(VeteranDto veteran);

    /**
     * Searches for the veterans in the local db using 'like'
     * @param veteranId
     * @param lastName
     * @param ssnLastFour
     * @param programIdList
     * @param searchAttributes
     * @return
     */
    SearchResult<VeteranSearchResult> searchVeterans(Integer veteranId, String lastName, String ssnLastFour,
            List<Integer> programIdList, SearchAttributes searchAttributes);

    /**
     * Retrieves the veteran object by veteranId.
     * @param veteranId
     * @return
     */
    VeteranDto getByVeteranId(Integer veteranId);

    /**
     * Updates veteran record by veteranId.
     * @param veteran
     * @return
     */
    void updateVeteran(VeteranDto veteran);

    /**
     * Updates veteran demographics section.
     * @param veteranDto
     */
    void updateDemographicsData(VeteranDto veteranDto);

    /**
     * Creates a new Veteran
     * @param createVeteranFormBean
     * @return
     */
    Integer add(CreateVeteranFormBean createVeteranFormBean);

    /**
     * Last name is optional and ssnLastFour will search on whole string.
     * @param lastName
     * @param ssnLastFour
     * @return
     */
    List<VeteranDto> searchVeterans(String lastName, String ssnLastFour);

    /**
     * Updates the known VistA fields in the Veteran table with 'veteranDto'.
     * @param veteranDto
     */
    void updateMappedVistaFields(VeteranDto veteranDto);

    /**
     * Updates the veteranIen for veteran. A NULL veteranIen can be passed to un-map a veteran record to a VistA record.
     * @param veteranId
     * @param veteranIen
     */
    void updateVeteranIen(int veteranId, String veteranIen);

    /**
     * Copy veteran vista record and save it into the database.
     * @param veteranDto
     * @return System assigned unique identifier for the new veteran record.
     */
    Integer importVistaVeteranRecord(VeteranDto veteranDto);

}
