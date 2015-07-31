package gov.va.escreening.repository;

import gov.va.escreening.entity.ProgramBattery;

import java.util.List;

public interface ProgramBatteryRepository extends RepositoryInterface<ProgramBattery> {

	/**
	 * Retrieves all Programs mapped to a battery.
	 * 
	 * @param programId
	 * @return
	 */
	List<ProgramBattery> findAllByBatteryId(int batteryId);

	List<ProgramBattery> findAllByProgramId(int programId);
}
