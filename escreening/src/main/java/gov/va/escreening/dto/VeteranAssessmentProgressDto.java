package gov.va.escreening.dto;

import java.io.Serializable;

public class VeteranAssessmentProgressDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer veteranAssessmentId;
    private Integer surveySectionId;
    private String surveySectionName;
    private Integer displayOrder;
    private Integer totalQuestionCount;
    private Integer totalResponseCount;
    private Integer percentComplete;

    public Integer getVeteranAssessmentId() {
        return veteranAssessmentId;
    }

    public void setVeteranAssessmentId(Integer veteranAssessmentId) {
        this.veteranAssessmentId = veteranAssessmentId;
    }

    public Integer getSurveySectionId() {
        return surveySectionId;
    }

    public void setSurveySectionId(Integer surveySectionId) {
        this.surveySectionId = surveySectionId;
    }

    public String getSurveySectionName() {
        return surveySectionName;
    }

    public void setSurveySectionName(String surveySectionName) {
        this.surveySectionName = surveySectionName;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Integer getTotalQuestionCount() {
        return totalQuestionCount;
    }

    public void setTotalQuestionCount(Integer totalQuestionCount) {
        this.totalQuestionCount = totalQuestionCount;
    }

    public Integer getTotalResponseCount() {
        return totalResponseCount;
    }

    public void setTotalResponseCount(Integer totalResponseCount) {
        this.totalResponseCount = totalResponseCount;
    }

    public Integer getPercentComplete() {
        return percentComplete;
    }

    public void setPercentComplete(Integer percentComplete) {
        this.percentComplete = percentComplete;
    }

    public VeteranAssessmentProgressDto() {
        // Default constructor
    }
}
