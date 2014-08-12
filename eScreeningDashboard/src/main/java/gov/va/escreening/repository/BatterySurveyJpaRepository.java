package gov.va.escreening.repository;

import gov.va.escreening.entity.BatterySurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by pouncilt on 8/1/14.
 */

public interface BatterySurveyJpaRepository extends JpaRepository<BatterySurvey, Integer>{
    /**
     * Retrieves a Battery Survey based on the a Battery Id.
     *
     * @param batteryId
     * @return
     */
    @Query("SELECT bs FROM BatterySurvey bs JOIN bs.battery b JOIN bs.survey s WHERE bs.battery.batteryId = :batteryId ORDER BY b.name, s.name, bs.batterySurveyId")
    public List<BatterySurvey> find(@Param("batteryId") Object batteryId);
}
