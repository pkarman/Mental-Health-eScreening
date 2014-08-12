package gov.va.escreening.dto.dashboard;

public class DashboardAlertItem {
	private final String label;
	private final String color;
	private final long count;
	private final float value;
	private final String vaid;
	private final String labelPlusCnt;

	public DashboardAlertItem(long count, String color, float value, String label, String vaid) {
		this.label = label;
		this.color = color;
		this.count = count;
		this.value = value;
		this.vaid = vaid;
		this.labelPlusCnt = label + "-" + count;
	}

	public String getLabel() {
		return label;
	}

	public String getColor() {
		return color;
	}

	public long getCount() {
		return count;
	}

	public float getValue() {
		return value;
	}

	public String getLabelPlusCnt() {
		return labelPlusCnt;
	}

	public String getVaid() {
		return vaid;
	}

}
