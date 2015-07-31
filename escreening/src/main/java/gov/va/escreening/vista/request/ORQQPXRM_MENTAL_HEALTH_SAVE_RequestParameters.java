package gov.va.escreening.vista.request;

import java.util.Date;

/**
 * Created by pouncilt on 5/30/14.
 */
public class ORQQPXRM_MENTAL_HEALTH_SAVE_RequestParameters extends VistaLinkRequestParameters {
    private Long patientIEN = null;
    private String mentalHealthTestName = null;
    private Date date = null; // Acceptable value is "T" for todays date or a Date Object which should be converted into a Vista date time string.
    private String staffCode = null;
    private String mentalHealthTestAnswers = null;

    public ORQQPXRM_MENTAL_HEALTH_SAVE_RequestParameters(Long patientIEN,
                                                         String mentalHealthTestName, Date date,
                                                         String staffCode, String mentalHealthTestAnswers) {
        this.patientIEN = patientIEN;
        this.mentalHealthTestName = mentalHealthTestName;
        this.date = date;
        this.staffCode = staffCode;
        this.mentalHealthTestAnswers = mentalHealthTestAnswers;

        checkRequiredParameters();
    }
    @Override
    protected void checkRequiredParameters() {
        super.checkRequestParameterDate("date", date, true);
        super.checkRequestParameterString("staffCode", staffCode, true);
        super.checkRequestParameterLong("patientIEN", patientIEN, true);
        super.checkRequestParameterString("mentalHealthTestName", mentalHealthTestName, true);
        super.checkRequestParameterString("mentalHealthTestAnswers", mentalHealthTestAnswers, true);
    }

    public Long getPatientIEN() {
        return patientIEN;
    }

    public String getMentalHealthTestName() {
        return mentalHealthTestName;
    }

    public Date getDate() {
        return date;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public String getMentalHealthTestAnswers() {
        return mentalHealthTestAnswers;
    }
}
