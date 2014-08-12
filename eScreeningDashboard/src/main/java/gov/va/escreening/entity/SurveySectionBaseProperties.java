package gov.va.escreening.entity;

import java.util.Date;

/**
 * Created by pouncilt on 8/2/14.
 */
public interface SurveySectionBaseProperties {
    Integer getSurveySectionId();
    void setSurveySectionId(Integer surveySectionId);
    String getName();
    void setName(String name);
    String getDescription();
    void setDescription(String name);
    Integer getDisplayOrder();
    void setDisplayOrder(Integer displayOrder);
    Date getDateCreated();
    void setDateCreated(Date dateCreated);
}
