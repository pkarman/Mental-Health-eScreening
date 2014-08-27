package gov.va.escreening.entity;

import gov.va.escreening.dto.ae.*;

import java.util.List;

/**
 * Created by pouncilt on 8/6/14.
 */
public interface MeasureBaseProperties {
    Integer getMeasureId();
    void setMeasureId(Integer measureId);
    String getMeasureText();
    void setMeasureText(String measureText);
    String getMeasureType();
    void setMeasureType(String measureType);
    Integer getDisplayOrder();
    void setDisplayOrder(Integer displayOrder);
    Boolean getIsRequired();
    void setIsRequired(Boolean isRequired);
    Boolean getIsVisible();
    void setIsVisible(Boolean isVisible);
    String getVistaText();
    void setVistaText(String vistaText);
    String getVariableName();
    void setVariableName(String variableName);
    Boolean getIsPPI();
    void setIsPPI(Boolean isPPI);
    Boolean getIsMha();
    void setIsMha(Boolean isMha);
}
