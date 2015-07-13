package gov.va.escreening.service;

import gov.va.escreening.domain.BatterySurveyDto;
import gov.va.escreening.dto.SearchDTO;
import gov.va.escreening.entity.BatterySurvey;

import java.util.List;

public interface BatterySurveyService {

    /**
     * Retrieves all the surveys mapped to a battery.
     * @param batteryId
     * @return
     */
    List<BatterySurveyDto> findAllByBatteryId(int batteryId);

    /**
     * Retrieves all the battery survey mappings.
     * @return
     */
    List<BatterySurveyDto> getBatterySurveyList();


    /**
     * Searches persons by using the search criteria given as a parameter.
     * @param searchCriteria
     * @return  A list of battery survey matching with the search criteria. If no battery survey is found, this method
     *          returns an empty list.
     * @throws IllegalArgumentException if search type is not given.
     */
    public List<BatterySurvey> search(SearchDTO searchCriteria);
}
