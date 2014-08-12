package gov.va.escreening.vista.request;

/**
 * Created by pouncilt on 5/5/14.
 */
public class TIU_LOAD_BOILERPLATE_TEXT_RequestParameters extends VistaLinkRequestParameters {
    private Long patientIEN = null;  // Required to create Progress Note.
    private Long titleIEN = null;   // Required to create Progress Note.
    private String visitString = null; // Required to create Progress Note.

    public TIU_LOAD_BOILERPLATE_TEXT_RequestParameters(Long patientIEN, Long titleIEN, String visitString) {
        this.patientIEN = patientIEN;
        this.titleIEN = titleIEN;
        this.visitString = visitString;

        checkRequiredParameters();
    }

    @Override
    protected void checkRequiredParameters() {
        super.checkRequestParameterLong("patientIEN", patientIEN, true);
        super.checkRequestParameterLong("titleIEN", titleIEN, true);
        super.checkRequestParameterString("visitString", visitString, true);
    }

    public Long getPatientIEN() {
        return patientIEN;
    }

    public Long getTitleIEN() {
        return titleIEN;
    }

    public String getVisitString() {
        return visitString;
    }
}
