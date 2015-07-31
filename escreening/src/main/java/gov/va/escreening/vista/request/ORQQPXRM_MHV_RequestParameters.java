package gov.va.escreening.vista.request;

/**
 * Created by pouncilt on 5/17/14.
 */
public class ORQQPXRM_MHV_RequestParameters extends VistaLinkRequestParameters {
    private Long patientIEN = null;
    private String mentalHealthTestName = null;
    private String mentalHealthTestAnswers = null;

    public ORQQPXRM_MHV_RequestParameters(Long patientIEN, String mentalHealthTestName, String mentalHealthTestAnswers) {
        this.patientIEN = patientIEN;
        this.mentalHealthTestName = mentalHealthTestName;
        this.mentalHealthTestAnswers = mentalHealthTestAnswers;

        checkRequiredParameters();
    }
    @Override
    protected void checkRequiredParameters() {
        if (patientIEN == null) throw new NullPointerException("Request Parameter \"patientIEN\" cannot be Null.");
        if (mentalHealthTestName == null) throw new NullPointerException("Request Parameter \"mentalHealthTestName\" cannot be Null.");
        if (mentalHealthTestAnswers == null) throw new NullPointerException("Request Parameter \"mentalHealthTestAnswers\" cannot be Null.");
    }

    public Long getPatientIEN() {
        return patientIEN;
    }

    public String getMentalHealthTestName() {
        return mentalHealthTestName;
    }

    public String getMentalHealthTestAnswers() {
        return mentalHealthTestAnswers;
    }
}
