package gov.va.escreening.vista.request;

import java.util.Map;

/**
 * Created by pouncilt on 6/9/14.
 */
public class ORWDX_SAVE_RequestParameters extends VistaLinkRequestParameters {
	private Long patientIEN;
	private Long providerIEN;
	private Long locationIEN;
	private String orderDialog;
	private Long dialogGroupIEN;
	private Long quickOrderDialogIEN;
	private Long orderIEN;
	private Map<String, Object> responseList;
	private String drugControlledSubstanceSchedule;
	private String appointment;
	private String orderSource;
	private Boolean isEvent;

	public ORWDX_SAVE_RequestParameters(Long patientIEN, Long providerIEN, Long locationIEN, String orderDialog, Long dialogGroupIEN, Long quickOrderDialogIEN, Long orderIEN, Map<String, Object> responseList, String drugControlledSubstanceSchedule, String appointment, String orderSource, Boolean isEvent) {

		this.patientIEN = patientIEN;
		this.providerIEN = providerIEN;
		this.locationIEN = locationIEN;
		this.orderDialog = orderDialog;
		this.dialogGroupIEN = dialogGroupIEN;
		this.quickOrderDialogIEN = quickOrderDialogIEN;
		this.orderIEN = orderIEN;
		this.responseList = responseList;
		this.drugControlledSubstanceSchedule = drugControlledSubstanceSchedule;
		this.appointment = appointment;
		this.orderSource = orderSource;
		this.isEvent = isEvent;

		checkRequiredParameters();
	}

	@Override
	protected void checkRequiredParameters() {
		super.checkRequestParameterLong("patientIEN", patientIEN, true);
		super.checkRequestParameterLong("providerIEN", providerIEN, true);
		super.checkRequestParameterLong("locationIEN", locationIEN, true);
		super.checkRequestParameterString("orderDialog", orderDialog, true);
		super.checkRequestParameterLong("quickOrderDialogIEN", quickOrderDialogIEN, true);
		super.checkRequestParameterLong("orderIEN", orderIEN, false);
		super.checkRequestParameterMap("responseList", responseList, true);
		super.checkRequestParameterString("drugControlledSubstanceSchedule", drugControlledSubstanceSchedule, false);
		super.checkRequestParameterString("appointment", appointment, false);
		super.checkRequestParameterString("orderSource", orderSource, false);
		super.checkRequestParameterBoolean("isEvent", isEvent, true);
	}

	public Long getPatientIEN() {
		return patientIEN;
	}

	public Long getProviderIEN() {
		return providerIEN;
	}

	public Long getLocationIEN() {
		return locationIEN;
	}

	public String getOrderDialog() {
		return orderDialog;
	}

	public Long getDialogGroupIEN() {
		return dialogGroupIEN;
	}

	public Long getQuickOrderDialogIEN() {
		return quickOrderDialogIEN;
	}

	public Long getOrderIEN() {
		return orderIEN;
	}

	public String getDrugControlledSubstanceSchedule() {
		return drugControlledSubstanceSchedule;
	}

	public String getAppointment() {
		return appointment;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public Boolean getIsEvent() {
		return isEvent;
	}

	public Map<String, Object> getResponseList() {
		return responseList;
	}
}
