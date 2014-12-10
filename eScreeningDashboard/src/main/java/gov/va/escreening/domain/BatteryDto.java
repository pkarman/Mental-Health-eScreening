package gov.va.escreening.domain;

import java.io.Serializable;

public class BatteryDto implements Comparable<BatteryDto>, Serializable {

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

	@Override
	public int compareTo(BatteryDto o) {
		return this.batteryName.compareTo(o.batteryName);
	}

	@Override
	public int hashCode() {
		return batteryId.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof BatteryDto)) {
			return false;
		}
		BatteryDto other = (BatteryDto) obj;
		if (batteryId == null) {
			if (other.batteryId != null) {
				return false;
			}
		} else if (!batteryId.equals(other.batteryId)) {
			return false;
		}
		return true;
	}

}
