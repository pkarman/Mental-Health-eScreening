package gov.va.escreening.dto.editors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import gov.va.escreening.entity.MeasureAnswerBaseProperties;

/**
 * Created by pouncilt on 8/5/14.
 */
//@JsonIgnoreProperties(ignoreUnknown = true, value = {"answerId", "answerText", "answerType", "answerResponse", "otherAnswerResponse"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnswerInfo implements MeasureAnswerBaseProperties {
    private Integer id;
    private String text;
    private String type;
    private String response;
    private String vistaText;
    private String exportName;
    private String otherResponse;
    private Integer rowId;


    public AnswerInfo() {

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

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public Integer getAnswerId() {
        return getId();
    }

    @Override
    public void setAnswerId(Integer id) {
        setId(id);
    }

    @Override
    public String getAnswerText() {
        return getText();
    }

    @Override
    public void setAnswerText(String answerText) {
        setText(answerText);
    }

    @Override
    public String getAnswerType() {
        return getType();
    }

    @Override
    public void setAnswerType(String answerType) {
        setType(answerType);
    }

    @Override
    public String getAnswerResponse() {
        return getResponse();
    }

    @Override
    public void setAnswerResponse(String answerResponse) {
        setResponse(answerResponse);
    }

    public String getVistaText() {
        return vistaText;
    }

    public void setVistaText(String vistaText) {
        this.vistaText = vistaText;
    }

    public String getExportName() {
        return exportName;
    }

    public void setExportName(String exportName) {
        this.exportName = exportName;
    }

    @Override
    public String getOtherAnswerResponse() {
        return getOtherResponse();
    }

    @Override
    public void setOtherAnswerResponse(String otherAnswerResponse) {
        setOtherResponse(otherAnswerResponse);
    }

    public String getOtherResponse() {
        return otherResponse;
    }

    public void setOtherResponse(String otherResponse) {
        this.otherResponse = otherResponse;
    }

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }
}
