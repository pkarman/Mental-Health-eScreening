package gov.va.escreening.vista.request;

import java.util.Arrays;

/**
 * 
 * @author khalid rizvi
 * 
 */
public class ORWDXM1_BLDQRSP_RequestParameters extends VistaLinkRequestParameters {
	private final Long quickOrderDialogIEN;
	private final Long partPatientIEN;
	private final Long partLocationIEN;
	private final Long partProviderIEN;
	private final Boolean partInpatient;
	private final String partSex;
	private final Integer partAge;
	private final Long partEventIEN;
	private final String partEventType;
	private final Long partTimeStamp;
	private final Long partEffective;
	private final Long partScPercentage;
	private final Boolean inpatientMedicationOnOutpatient;
	private final Long locationIEN;

	public ORWDXM1_BLDQRSP_RequestParameters(Long quickOrderDialogIEN, Long partPatientIEN, Long partLocationIEN, Long partProviderIEN, Boolean partInpatient, String partSex, Integer partAge, Long partEventIEN, String partEventType, Long partTimeStamp, Long partEffective, Long partScPercentage, Boolean inpatientMedicationOnOutpatient, Long locationIEN) {
		this.quickOrderDialogIEN = quickOrderDialogIEN;
		this.partPatientIEN = partPatientIEN;
		this.partLocationIEN = partLocationIEN;
		this.partProviderIEN = partProviderIEN;
		this.partInpatient = partInpatient;
		this.partSex = partSex;
		this.partAge = partAge;
		this.partEventIEN = partEventIEN;
		this.partEventType = partEventType;
		this.partTimeStamp = partTimeStamp;
		this.partEffective = partEffective;
		this.partScPercentage = partScPercentage;
		this.inpatientMedicationOnOutpatient = inpatientMedicationOnOutpatient;
		this.locationIEN = locationIEN;

		checkRequiredParameters();
	}

	@Override
	protected void checkRequiredParameters() {
		super.checkRequestParameterLong("quickOrderDialogIEN", quickOrderDialogIEN, true);
		super.checkRequestParameterLong("partPatientIEN", partPatientIEN, true);
		super.checkRequestParameterLong("partLocationIEN", partLocationIEN, true);
		super.checkRequestParameterLong("partProviderIEN", partProviderIEN, true);
		super.checkRequestParameterBoolean("partInpatient", partInpatient, true);
		super.checkRequestParameterString("partSex", partSex, true, Arrays.asList("M", "F"));
		super.checkRequestParameterInteger("partAge", partAge, true);
		super.checkRequestParameterLong("partEventIEN", partEventIEN, true, 0);
		super.checkRequestParameterString("partEventType", partEventType, true, Arrays.asList("C"));
		super.checkRequestParameterLong("partTimeStamp", partTimeStamp, true, 0);
		super.checkRequestParameterLong("partEffective", partEffective, true, 0);
		super.checkRequestParameterLong("partScPercentage", partScPercentage, true, 0);
		super.checkRequestParameterBoolean("inpatientMedicationOnOutpatient", inpatientMedicationOnOutpatient, true, false);
		super.checkRequestParameterLong("locationIEN", locationIEN, true);
	}

	public String get8PiecesData() {
		// 100687^228^10000000210^0^M^69^0;C;0;0^0
		return String.format("%s^%s^%s^%s^%s^%s^%s;%s;%s;%s^%s", partPatientIEN, partLocationIEN, partProviderIEN, partInpatient, partSex, partAge, partEventIEN, partEventType, partTimeStamp, partEffective, partScPercentage);
	}

	public Long getQuickOrderDialogIEN() {
		return quickOrderDialogIEN;
	}

	public int getInpatientMedicationOnOutpatient() {
		return inpatientMedicationOnOutpatient ? 1 : 0;
	}

	public Long getLocationIEN() {
		return locationIEN;
	}
}
