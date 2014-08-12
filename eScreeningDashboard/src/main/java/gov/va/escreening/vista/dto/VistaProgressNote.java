package gov.va.escreening.vista.dto;

/**
 * Created by pouncilt on 4/16/14.
 */
public class VistaProgressNote {
    private Long ien;
    private String[] boilerPlateText;
    private String content;

    public VistaProgressNote(Long ien) {
        this.ien = ien;
    }

    public Long getIEN() {
        return ien;
    }

    public void setBoilerPlateText(String[] boilerPlateText) {
        this.boilerPlateText = boilerPlateText;
    }

    public String[] getBoilerPlateText() {
        return boilerPlateText;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
