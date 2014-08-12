package gov.va.escreening.repository;

import gov.va.escreening.entity.BatterySurvey;

import java.util.List;

public interface BatterySurveyRepository extends RepositoryInterface<BatterySurvey> {

    /**
     * Retrieves all surveys mapped to a battery.
     * @param batteryId
     * @return
     */
    List<BatterySurvey> findAllByBatteryId(int batteryId);

    /**
     * Retrieves all the battery survey mapping.
     * @return
     */
    List<BatterySurvey> getBatterySurveyList();
}
