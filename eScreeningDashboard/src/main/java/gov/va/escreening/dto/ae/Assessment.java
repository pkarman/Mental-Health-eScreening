package gov.va.escreening.dto.ae;

import gov.va.escreening.entity.SurveyPage;

import java.io.Serializable;

public class Assessment implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer pageId;
    private Integer currentSurveyId;
    private Integer currentSurveySection;
    private String currentSurveyTitle;
    private Integer currentPage;
    private Boolean isFirstPage;
    private Boolean isLastPage;
    private Boolean isComplete;

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public Integer getCurrentSurveyId() {
        return currentSurveyId;
    }

    public void setCurrentSurveyId(Integer currentSurveyId) {
        this.currentSurveyId = currentSurveyId;
    }

    public Integer getCurrentSurveySection() {
        return currentSurveySection;
    }

    public void setCurrentSurveySection(Integer currentSurveySection) {
        this.currentSurveySection = currentSurveySection;
    }

    public String getCurrentSurveyTitle() {
        return currentSurveyTitle;
    }

    public void setCurrentSurveyTitle(String title) {
        this.currentSurveyTitle = title;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Boolean getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(Boolean isComplete) {
        this.isComplete = isComplete;
    }

    public Boolean getIsFirstPage(){
        return isFirstPage;
    }
    
    public Boolean getIsLastPage(){
        return isLastPage;
    }
    
    public Assessment(SurveyPage surveyPage, boolean isFirstPage, boolean isLastPage, boolean isComplete) {
        this.pageId  = surveyPage.getSurveyPageId();
        this.currentSurveyId = surveyPage.getSurvey().getSurveyId();
        if(surveyPage.getSurvey().getSurveySection() != null)
        {
            this.currentSurveySection = surveyPage.getSurvey().getSurveySection().getSurveySectionId();
        }
        this.currentPage = surveyPage.getPageNumber();
        this.isFirstPage = isFirstPage;
        this.isLastPage = isLastPage;
        this.isComplete = isComplete;
     
    }

    @Override
    public String toString() {
        return "Assessment [pageId=" + pageId + ", currentSurveyId="
                + currentSurveyId + ", currentSurveyTitle=" + currentSurveyTitle + ", currentPage=" + currentPage
                + ", isComplete=" + isComplete + "]";
    }

}
