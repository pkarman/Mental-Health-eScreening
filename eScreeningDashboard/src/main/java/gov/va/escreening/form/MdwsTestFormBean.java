package gov.va.escreening.form;

public class MdwsTestFormBean {

	private String lastFourSsn;
	private String firstName;
	private String middleName;
	private String lastName;
	private String matchString;
	private String localPid;

	public String getLocalPid() {
		return localPid;
	}

	public void setLocalPid(String localPid) {
		this.localPid = localPid;
	}

	public String getLastFourSsn() {
		return lastFourSsn;
	}

	public void setLastFourSsn(String lastFourSsn) {
		this.lastFourSsn = lastFourSsn;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMatchString() {
		return matchString;
	}

	public void setMatchString(String matchString) {
		this.matchString = matchString;
	}

}
