package gov.va.escreening.vista.request;

/**
 * Created by pouncilt on 5/29/14.
 */
public class ORWPT_SELECT_RequestParameters extends VistaLinkRequestParameters {
    private Long patientIEN = null;

    public ORWPT_SELECT_RequestParameters(Long patientIEN){
        this.patientIEN = patientIEN;

        checkRequiredParameters();
    }

    @Override
    protected void checkRequiredParameters() {
        super.checkRequestParameterLong("patientIEN", patientIEN, true);
    }

    public Long getPatientIEN() {
        return patientIEN;
    }
}
