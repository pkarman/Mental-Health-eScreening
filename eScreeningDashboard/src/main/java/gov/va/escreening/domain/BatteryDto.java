package gov.va.escreening.domain;

import java.io.Serializable;

public class BatteryDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer batteryId;
	private String batteryName;

	public Integer getBatteryId() {
		return batteryId;
	}

	public void setBatteryId(Integer batteryId) {
		this.batteryId = batteryId;
	}

	public String getBatteryName() {
		return batteryName;
	}

	public void setBatteryName(String batteryName) {
		this.batteryName = batteryName;
	}

	public BatteryDto() {

	}

	public BatteryDto(Integer batteryId, String batteryName) {
		this.batteryId = batteryId;
		this.batteryName = batteryName;
	}
}
