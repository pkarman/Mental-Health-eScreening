package gov.va.escreening.dto.ae;

import java.io.Serializable;
import java.util.List;

public class AssessmentRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer assessmentId;
    private Integer pageId;
    private String navigation;
    private Integer targetSection = null;
    private long startTime;
    private List<Measure> userAnswers;

    public Integer getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(Integer assessmentId) {
        this.assessmentId = assessmentId;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public String getNavigation() {
        return navigation;
    }

    public void setNavigation(String navigation) {
        this.navigation = navigation;
    }

    public List<Measure> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(List<Measure> userAnswers) {
        this.userAnswers = userAnswers;
    }
    
    public Integer getTargetSection() {
        return targetSection;
    }

    public void setTargetSection(Integer targetSection) {
        this.targetSection = targetSection;
    }
    
    public AssessmentRequest() {}

    @Override
    public String toString() {
        return "AssessmentRequest [assessmentId=" + assessmentId + ", pageId=" + pageId 
                + ", navigation=" + navigation + ", userAnswers=" + userAnswers 
                + ", targetSection=" + targetSection + "]";
    }

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

}
