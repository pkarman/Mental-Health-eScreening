package gov.va.escreening.dto;

import java.io.Serializable;

import gov.va.escreening.domain.VeteranDto;

/**
 * This class represents the batch battery create result
 * @author mzhu
 *
 */
public class BatchBatteryCreateResult implements Serializable{
	private VeteranDto vet;
	private boolean succeed;
	private String errorMsg;
	public VeteranDto getVet() {
		return vet;
	}
	public void setVet(VeteranDto vet) {
		this.vet = vet;
	}
	public boolean isSucceed() {
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
