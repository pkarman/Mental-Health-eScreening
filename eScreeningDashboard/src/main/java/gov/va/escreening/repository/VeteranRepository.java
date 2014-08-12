package gov.va.escreening.repository;

import gov.va.escreening.dto.SearchAttributes;
import gov.va.escreening.dto.dashboard.SearchResult;
import gov.va.escreening.dto.dashboard.VeteranSearchResult;
import gov.va.escreening.entity.Veteran;

import java.util.List;

public interface VeteranRepository extends RepositoryInterface<Veteran> {
    /**
     * Uses wild card searches to search for veterans.
     * @param veteranId
     * @param lastName
     * @param ssnLastFour
     * @param clinicIdList
     * @param searchAttributes
     * @return
     */
    SearchResult<VeteranSearchResult> searchVeterans(Integer veteranId, String lastName, String ssnLastFour,
            List<Integer> clinicIdList, SearchAttributes searchAttributes);

    /**
     * Uses exact matching to search for veterans.
     * @param veteran
     * @return
     */
    List<Veteran> getVeterans(Veteran veteran);

    /**
     * 
     * @param lastName
     * @param ssnLastFour
     * @return
     */
    List<Veteran> searchVeterans(String lastName, String ssnLastFour);



}
