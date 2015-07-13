package gov.va.escreening.service;

import gov.va.escreening.domain.BatteryDto;
import gov.va.escreening.dto.DropDownObject;
import gov.va.escreening.dto.editors.BatteryInfo;
import gov.va.escreening.entity.Program;

import java.util.List;
import java.util.Set;

public interface BatteryService {

    /**
     * Retrieves a dropdown list of all the battery in the system.
     * @return
     */
    List<DropDownObject> getBatteryList();


    BatteryInfo create(BatteryInfo batteryInfo);
    List<BatteryInfo> getBatteryItemList();
    BatteryInfo getBattery(int batteryId);
    void update(BatteryInfo batteryInfo);

	Set<BatteryDto> getBatteryDtoSet();
	List<Program> getProgramsForBattery(int batteryId);


	void delete(Integer batteryId);
}
