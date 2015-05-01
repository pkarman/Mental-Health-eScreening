package gov.va.escreening.entity;

import java.util.Date;

/**
 * Created by pouncilt on 8/2/14.
 */
public interface SurveyBaseProperties {
    Integer getSurveyId();
    void setSurveyId(Integer surveyId);
    String getName();
    void setName(String name);
    String getDescription();
    void setDescription(String description);
    Integer getVersion();
    void setVersion(Integer version);
    boolean isMha();
    void setMha(boolean mha);
    String getMhaTestName();
    void setMhaTestName(String mhaTestName);
    Date getDateCreated();
    void setDateCreated(Date dateCreated);
    void setDisplayOrderForSection(Integer displayOrder);
    Integer getDisplayOrderForSection();
}
