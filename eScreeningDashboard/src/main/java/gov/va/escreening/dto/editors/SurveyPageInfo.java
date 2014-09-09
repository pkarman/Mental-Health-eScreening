package gov.va.escreening.dto.editors;

import java.util.List;

/**
 * Created by pouncilt on 9/9/14.
 */
public class SurveyPageInfo {
    private String description;
    private int pageNumber;
    private String title;
    private List<QuestionInfo> questions;

    public SurveyPageInfo() {}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<QuestionInfo> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionInfo> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "SurveyPageInfo [description=" + description + ", pageNumber=" + pageNumber + ", title=" + title + ", questions=" + questions + "]";
    }
}
