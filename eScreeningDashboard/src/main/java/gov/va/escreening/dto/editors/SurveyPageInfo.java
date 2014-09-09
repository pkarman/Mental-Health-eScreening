package gov.va.escreening.dto.editors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gov.va.escreening.serializer.JsonDateSerializer;

import java.util.Date;
import java.util.List;

/**
 * Created by pouncilt on 9/9/14.
 */
public class SurveyPageInfo {
    private Integer id;
    private String description;
    private int pageNumber;
    private String title;
    private Date dateCreated;
    private List<QuestionInfo> questions;

    public SurveyPageInfo() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    @JsonSerialize(using=JsonDateSerializer.class)
    @JsonProperty("createdDate")
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<QuestionInfo> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionInfo> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "SurveyPageInfo [id=" + id + ", description=" + description + ", pageNumber=" + pageNumber +
                ", title=" + title + ", dateCreated=" + dateCreated + ", questions=" + questions + "]";
    }
}
