package gov.va.escreening.dto;

import gov.va.escreening.domain.VeteranWithClinicalReminderFlag;

import java.io.Serializable;

/**
 * This class represents the batch battery create result
 * @author mzhu
 *
 */
public class BatchBatteryCreateResult implements Serializable{
	private VeteranWithClinicalReminderFlag vet;
	private boolean succeed;
	private String errorMsg;

	public void setVet(VeteranWithClinicalReminderFlag vet) {
		this.vet = vet;
	}
	
	public VeteranWithClinicalReminderFlag getVet() {
		return vet;
	}
	public boolean getSucceed() {
		return succeed;
	}
	public void setSucceed(boolean succeed) {
		this.succeed = succeed;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
