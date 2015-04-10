package gov.va.escreening.dto.ae;

import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class AssessmentRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer assessmentId;
    private Integer pageId;
    private String navigation;
    private Integer targetSection;
    private long assessmentStartTime;
    private List<Measure> userAnswers;
    final private Map<Integer, Long> moduleStartTimeMap;

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
    
    public AssessmentRequest() {
        moduleStartTimeMap = Maps.newHashMap();
    }

    @Override
    public String toString() {
        return "AssessmentRequest [assessmentId=" + assessmentId + ", pageId=" + pageId 
                + ", navigation=" + navigation + ", userAnswers=" + userAnswers 
                + ", targetSection=" + targetSection + "]";
    }

	public long getAssessmentStartTime() {
		return assessmentStartTime;
	}

	public void setAssessmentStartTime(long assessmentStartTime) {
		this.assessmentStartTime = assessmentStartTime;
	}

    public void setModuleStartTime(Integer moduleId, Long startTime){
        this.moduleStartTimeMap.put(moduleId, startTime);
    }

    public Long getModuleStartTime(Integer moduleId){
        return this.moduleStartTimeMap.get(moduleId);
    }
}
