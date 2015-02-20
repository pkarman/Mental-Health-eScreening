package gov.va.escreening.domain;


public class VeteranWithClinicalReminderFlag extends VeteranDto {
	private boolean dueClinicalReminders;
	private String apptDate;
	private String apptTime;

	public String getApptDate() {
		return apptDate;
	}

	public void setApptDate(String appDate) {
		this.apptDate = appDate;
	}

	public String getApptTime() {
		return apptTime;
	}

	public void setApptTime(String appTime) {
		this.apptTime = appTime;
	}

	public boolean getDueClinicalReminders() {
		return dueClinicalReminders;
	}

	public void setDueClinicalReminders(boolean dueClinicalReminder) {
		this.dueClinicalReminders = dueClinicalReminder;
	}

	public VeteranWithClinicalReminderFlag(VeteranDto dto) {
		setFirstName(dto.getFirstName());
		setLastName(dto.getLastName());
		setMiddleName(dto.getMiddleName());
		setIsSensitive(dto.getIsSensitive());
		setSsnLastFour(dto.getSsnLastFour());
		setBirthDate(dto.getBirthDate());
		setVeteranId(dto.getVeteranId());
		setVeteranIen(dto.getVeteranIen());
	}
	
}
