package gov.va.escreening.dto.editors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import gov.va.escreening.entity.MeasureBaseProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pouncilt on 8/5/14.
 */
@JsonRootName("question")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"measureId", "measureText", "measureType", "isRequired", "isVisible", "isPPI"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionInfo implements MeasureBaseProperties {
    private Integer id;
    private String text;
    private String type;
    private Integer displayOrder;
    private Boolean required;
    private Boolean visible;
    private String vistaText;
    private String variableName;
    private Boolean ppi;
    private Boolean mha;
    private List<AnswerInfo> answers = new ArrayList<AnswerInfo>();
    private List<ValidationInfo> validations = new ArrayList<ValidationInfo>();
    private List<QuestionInfo> childQuestions = new ArrayList<QuestionInfo>();
    private List<List<AnswerInfo>> tableAnswers = new ArrayList<List<AnswerInfo>>();

    public QuestionInfo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Integer getMeasureId() {
        return getId();
    }

    @Override
    public void setMeasureId(Integer measureId) {
        setId(measureId);
    }

    @Override
    public String getMeasureText() {
        return getText();
    }

    @Override
    public void setMeasureText(String measureText) {
        setText(measureText);
    }

    @Override
    public String getMeasureType() {
        return getType();
    }

    @Override
    public void setMeasureType(String measureType) {
        setType(measureType);
    }

    @Override
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    @Override
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    @Override
    public Boolean getIsRequired() {
        return isRequired();
    }

    @Override
    public void setIsRequired(Boolean isRequired) {
        setRequired(isRequired);
    }

    @Override
    public Boolean getIsVisible() {
        return isVisible();
    }

    @Override
    public void setIsVisible(Boolean isVisible) {
        setVisible(isVisible);
    }

    public Boolean isRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public List<AnswerInfo> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerInfo> answers) {
        this.answers = answers;
    }

    public String getVistaText() {
        return vistaText;
    }

    public void setVistaText(String vistaText) {
        this.vistaText = vistaText;
    }

    @Override
    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public Boolean getIsPPI() {
        return isPpi();
    }

    @Override
    public void setIsPPI(Boolean isPPI) {
        setPpi(isPPI);
    }

    public Boolean isPpi() {
        return ppi;
    }

    public void setPpi(Boolean ppi) {
        this.ppi = ppi;
    }

    @Override
    public Boolean getIsMha() {
        return isMha();
    }

    @Override
    public void setIsMha(Boolean isMha) {
        setMha(isMha);
    }

    public Boolean isMha() {
        return mha;
    }

    public void setMha(Boolean mha) {
        this.mha = mha;
    }

    public List<ValidationInfo> getValidations() {
        return validations;
    }

    public void setValidations(List<ValidationInfo> validations) {
        this.validations = validations;
    }

    public List<QuestionInfo> getChildQuestions() {
        return childQuestions;
    }

    public void setChildQuestions(List<QuestionInfo> childQuestions) {
        this.childQuestions = childQuestions;
    }

    public List<List<AnswerInfo>> getTableAnswers() {
        return tableAnswers;
    }

    public void setTableAnswers(List<List<AnswerInfo>> tableAnswers) {
        this.tableAnswers = tableAnswers;
    }
}
