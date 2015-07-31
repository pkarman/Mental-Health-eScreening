package gov.va.escreening.vista.dto;

/**
 * Created by pouncilt on 5/17/14.
 */
public class MentalHealthAssessmentResult {
    private Long reminderDialogIEN = null;
    private Long patientIEN = null;
    private String mentalHealthTestName = null;
    private String dateCode = null;
    private String staffCode = null;
    private String mentalHealthTestAnswers = null;
    private String mentalHealthAssessmentResultDescription = null;

    public MentalHealthAssessmentResult(Long reminderDialogIEN, Long patientIEN, String mentalHealthTestName,
                                        String dateCode, String staffCode, String mentalHealthTestAnswers,
                                        String mentalHealthAssessmentResultDescription) {

        this.reminderDialogIEN = reminderDialogIEN;
        this.patientIEN = patientIEN;
        this.mentalHealthTestName = mentalHealthTestName;
        this.dateCode = dateCode;
        this.staffCode = staffCode;
        this.mentalHealthTestAnswers = mentalHealthTestAnswers;
        this.mentalHealthAssessmentResultDescription = mentalHealthAssessmentResultDescription;
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

    public String getMentalHealthAssessmentResultDescription() {
        return mentalHealthAssessmentResultDescription;
    }

    public boolean isNegative() {
        return this.mentalHealthAssessmentResultDescription.toLowerCase().contains("negative");
    }

    public boolean isPositive() {
        return this.mentalHealthAssessmentResultDescription.toLowerCase().contains("positive");
    }

    public Integer getScore() {
        Integer score = null;

        if(this.mentalHealthAssessmentResultDescription != null){
            if (this.mentalHealthAssessmentResultDescription.contains("score")) {
                if(this.mentalHealthAssessmentResultDescription.indexOf("score=") == 0) {
                    score = Integer.parseInt(this.mentalHealthAssessmentResultDescription.split("score=")[0]);
                } else {
                    score = Integer.parseInt(this.mentalHealthAssessmentResultDescription.split("score=")[1].substring(0,1));
                }
            }
        }

        return score;
    }
}
