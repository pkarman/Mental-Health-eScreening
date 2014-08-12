package gov.va.escreening.repository;

import gov.va.escreening.entity.Battery;

import java.util.List;

public interface BatteryRepository extends RepositoryInterface<Battery> {

    /**
     * Retrieves all the batteries in the system.
     * @return
     */
    List<Battery> getBatteryList();
}
