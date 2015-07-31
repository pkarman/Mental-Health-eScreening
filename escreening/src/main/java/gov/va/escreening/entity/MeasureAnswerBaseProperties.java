package gov.va.escreening.entity;

/**
 * Created by pouncilt on 8/6/14.
 */
public interface MeasureAnswerBaseProperties {
    Integer getAnswerId();
    void setAnswerId(Integer id);
    String getAnswerText();
    void setAnswerText(String answerText);
    String getAnswerType();
    void setAnswerType(String answerType);
    String getAnswerResponse();
    void setAnswerResponse(String answerResponse);
    String getVistaText();
    void setVistaText(String vistaText);
    String getExportName();
    void setExportName(String exportName);
    String getOtherAnswerResponse();
    void setOtherAnswerResponse(String otherAnswerResponse);
    Integer getRowId();
    void setRowId(Integer rowId);
    
    public String getCalculationValue();
    public void setCalculationValue(String calculationValue);
    public Integer getDisplayOrder();
    public void setDisplayOrder(Integer displayOrder);
    public String getMhaValue();
    public void setMhaValue(String mhaValue);
}
