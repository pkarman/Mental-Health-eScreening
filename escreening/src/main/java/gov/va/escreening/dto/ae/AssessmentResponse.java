package gov.va.escreening.dto.ae;

import java.io.Serializable;
import java.util.List;

public class AssessmentResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer status;
    private Assessment assessment;
    private List<SurveyProgress> surveyProgresses;
    private Page page;
    /** Long value representing the number of milliseconds since 1 January 1970 00:00:00 UTC (Unix Epoch). */
    private Long dateModified;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public List<SurveyProgress> getSurveyProgresses() {
        return surveyProgresses;
    }

    public void setSurveyProgresses(List<SurveyProgress> progressStatuses) {
        this.surveyProgresses = progressStatuses;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
    
    public Long getDateModified() {
        return dateModified;
    }

    /**
     * @param dateModified Integer value representing the number of milliseconds since 1 January 1970 00:00:00 UTC (Unix Epoch).
     */
    public void setDateModified(Long dateModified) {
        this.dateModified = dateModified;
    }

    public AssessmentResponse() {

    }

    @Override
    public String toString() {
        return "AssessmentResponse [status=" + status + ", assessment=" + assessment + ", surveyProgresses="
                + surveyProgresses + ", page=" + page + "]";
    }    
}
