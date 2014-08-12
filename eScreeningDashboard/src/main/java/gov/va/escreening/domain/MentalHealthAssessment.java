package gov.va.escreening.domain;

import java.io.Serializable;

/**
 * Created by pouncilt on 5/17/14.
 */
public class MentalHealthAssessment implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long surveyId;
    private String mentalHealthTestName;
    private String mentalHealthTestAnswers;
    private Long reminderDialogIEN;

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public String getMentalHealthTestName() {
        return mentalHealthTestName;
    }

    public void setMentalHealthTestName(String mentalHealthTestName) {
        this.mentalHealthTestName = mentalHealthTestName;
    }

    public String getMentalHealthTestAnswers() {
        return mentalHealthTestAnswers;
    }

    public void setMentalHealthTestAnswers(String mentalHealthTestAnswers) {
        this.mentalHealthTestAnswers = mentalHealthTestAnswers;
    }

    public Long getReminderDialogIEN() {
        return reminderDialogIEN;
    }

    public void setReminderDialogIEN(Long reminderDialogIEN) {
        this.reminderDialogIEN = reminderDialogIEN;
    }

    public MentalHealthAssessment() {
        // default constructor.
    }
}
