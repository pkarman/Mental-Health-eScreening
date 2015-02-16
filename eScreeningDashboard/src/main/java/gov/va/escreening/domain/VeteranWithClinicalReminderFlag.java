package gov.va.escreening.domain;

import java.beans.Beans;

public class VeteranWithClinicalReminderFlag extends VeteranDto {
	private boolean dueClinicalReminder;

	public boolean isDueClinicalReminder() {
		return dueClinicalReminder;
	}

	public void setDueClinicalReminder(boolean dueClinicalReminder) {
		this.dueClinicalReminder = dueClinicalReminder;
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
