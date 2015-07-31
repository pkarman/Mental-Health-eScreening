package gov.va.escreening.vista.request;

/**
 * Created by pouncilt on 5/17/14.
 */
public class ORQQPXRM_MENTAL_HEALTH_RESULTS_RequestParameters extends VistaLinkRequestParameters {
    private Long reminderDialogIEN = null;
    private Long patientIEN = null;
    private String mentalHealthTestName = null;
    //TODO:  May possible need to use a wrapper to hold a date code of "T" or a Date Object; which should be converted into a Vista date time string.
    private String dateCode = null; // Acceptable value is "T" for todays date or a Date Object which should be converted into a Vista date time string.
    private String staffCode = null;
    private String mentalHealthTestAnswers = null;

    public ORQQPXRM_MENTAL_HEALTH_RESULTS_RequestParameters(Long reminderDialogIEN, Long patientIEN,
                                                            String mentalHealthTestName, String dateCode,
                                                            String staffCode, String mentalHealthTestAnswers) {
        this.reminderDialogIEN = reminderDialogIEN;
        this.patientIEN = patientIEN;
        this.mentalHealthTestName = mentalHealthTestName;
        this.dateCode = dateCode;
        this.staffCode = staffCode;
        this.mentalHealthTestAnswers = mentalHealthTestAnswers;

        checkRequiredParameters();
    }

    @Override
    protected void checkRequiredParameters() {
        super.checkRequestParameterLong("reminderDialogIEN", reminderDialogIEN, true);
        super.checkRequestParameterString("dateCode", dateCode, true);
        super.checkRequestParameterString("staffCode", staffCode, true);
        super.checkRequestParameterLong("patientIEN", patientIEN, true);
        super.checkRequestParameterString("mentalHealthTestName", mentalHealthTestName, true);
        super.checkRequestParameterString("mentalHealthTestAnswers", mentalHealthTestAnswers, true);
    }

    public Long getReminderDialogIEN() {
        return reminderDialogIEN;
    }

    public Long getPatientIEN() {
        return patientIEN;
    }

    public String getMentalHealthTestName() {
        return mentalHealthTestName;
    }

    public String getDateCode() {
        return dateCode;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public String getMentalHealthTestAnswers() {
        return mentalHealthTestAnswers;
    }
}
