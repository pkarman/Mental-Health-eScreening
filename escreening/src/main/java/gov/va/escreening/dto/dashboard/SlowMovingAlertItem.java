package gov.va.escreening.dto.dashboard;

public class SlowMovingAlertItem {
	private final int time;
	private final String colors;
	private final int alert;
	private final String vLastName;
	private final String ssn;
	private final String vLastNamePlusSsn;
	private final String vaid;

	public SlowMovingAlertItem(int time, String colors, int alert, String vLastName, String ssn, String vaid) {
		this.time = time;
		this.colors = colors;
		this.alert = alert;
		this.vLastName = vLastName;
		this.ssn = ssn;
		this.vaid = vaid;
		vLastNamePlusSsn = vLastName + " (" + ssn + ")";
	}

	public int getTime() {
		return time;
	}

	public String getColors() {
		return colors;
	}

	public int getAlert() {
		return alert;
	}

	public String getvLastName() {
		return vLastName;
	}

	public String getSsn() {
		return ssn;
	}

	public String getvLastNamePlusSsn() {
		return vLastNamePlusSsn;
	}

	public String getVaid() {
		return vaid;
	}

}
