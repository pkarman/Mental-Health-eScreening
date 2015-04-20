package gov.va.escreening.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by kliu on 2/21/15.
 */

@Entity
@Table(name = "veteran_assessment_survey_score")
public class VeteranAssessmentSurveyScore implements Serializable {

    private static final long serialVersionUID = 18947892345L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "assessment_survey_score_id")
    private Integer assessmentSurveyScoreId;

    @JoinColumn(name="veteran_assessment_id", referencedColumnName = "veteran_assessment_id")
    @ManyToOne
    private VeteranAssessment veteranAssessment;

    @JoinColumn(name="survey_id", referencedColumnName = "survey_id")
    @ManyToOne
    private Survey survey;

    @JoinColumn(name="assessment_var_id", referencedColumnName = "assessment_variable_id")
    @ManyToOne
    private AssessmentVariable assessmentVariable;

    @Column(name = "survey_score")
    private Integer score;

    @Column(name = "screen_number")
    private Integer screenNumber;

    @Column(name = "date_completed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCompleted;

    @JoinColumn(name = "veteran_id", referencedColumnName = "veteran_id")
    @ManyToOne(optional = false)
    private Veteran veteran;

    @JoinColumn(name = "clinic_id", referencedColumnName = "clinic_id")
    @ManyToOne(optional = false)
    private Clinic clinic;

    public VeteranAssessment getVeteranAssessment() {
        return veteranAssessment;
    }

    public void setVeteranAssessment(VeteranAssessment veteranAssessment) {
        this.veteranAssessment = veteranAssessment;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public Veteran getVeteran() {
        return veteran;
    }

    public void setVeteran(Veteran veteran) {
        this.veteran = veteran;
    }

    public Integer getAssessmentSurveyScoreId() {
        return assessmentSurveyScoreId;
    }

    public void setAssessmentSurveyScoreId(Integer assessmentSurveyScoreId) {
        this.assessmentSurveyScoreId = assessmentSurveyScoreId;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public Integer getScreenNumber() {
        return screenNumber;
    }

    public void setScreenNumber(Integer screenNumber) {
        this.screenNumber = screenNumber;
    }

    public AssessmentVariable getAssessmentVariable() {
        return assessmentVariable;
    }

    public void setAssessmentVariable(AssessmentVariable assessmentVariable) {
        this.assessmentVariable = assessmentVariable;
    }
}
