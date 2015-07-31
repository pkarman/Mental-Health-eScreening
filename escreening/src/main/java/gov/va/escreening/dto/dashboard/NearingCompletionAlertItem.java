package gov.va.escreening.dto.dashboard;

public class NearingCompletionAlertItem {
	private final int percentages;
	private final String colors;
	private final int alert;
	private final String vLastName;
	private final String ssn;
	private final String vaid;
	private final String vLastNamePlusSsn;

	public NearingCompletionAlertItem(int percentages, String colors, int alert, String vLastName, String ssn, String vaid) {
		this.percentages = percentages;
		this.colors = colors;
		this.alert = alert;
		this.vLastName = vLastName;
		this.ssn = ssn;
		this.vaid = vaid;
		vLastNamePlusSsn = vLastName + " (" + ssn + ")";
	}

	public float getPercentages() {
		return (float) percentages / 100.00f;
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
