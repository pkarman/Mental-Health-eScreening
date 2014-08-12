package gov.va.escreening.vista.dto;

import java.io.Serializable;

public class DialogPrompt implements Serializable {

    private static final long serialVersionUID = 1L;

    // 3: Resolution
    // 4: Additional Prompts
    // 5: Listbox Items
    // 6: Progress Note Text

    private DialogPromptTypeEnum dialogPromptType;
    private String dialogPromptTypeCode;
    private String dialogElementIen;
    private String lineId;
    private String findingTypeCode;
    private String piece05;
    private String itemIen;
    private String r3Code;
    private String displayText;
    private String r3Cat;
    private String piece10;
    private String piece11;
    private String alternativeText;
    private String cptPovIen;
    private String codeDescription;
    private String progressNoteText;
    private String record;

    public DialogPromptTypeEnum getDialogPromptType() {
        return dialogPromptType;
    }

    public void setDialogPromptType(DialogPromptTypeEnum dialogPromptType) {
        this.dialogPromptType = dialogPromptType;
    }

    public String getDialogPromptTypeCode() {
        return dialogPromptTypeCode;
    }

    public void setDialogPromptTypeCode(String dialogPromptTypeCode) {
        this.dialogPromptTypeCode = dialogPromptTypeCode;
    }

    public String getDialogElementIen() {
        return dialogElementIen;
    }

    public void setDialogElementIen(String dialogElementIen) {
        this.dialogElementIen = dialogElementIen;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getFindingTypeCode() {
        return findingTypeCode;
    }

    public void setFindingTypeCode(String findingTypeCode) {
        this.findingTypeCode = findingTypeCode;
    }

    public String getPiece05() {
        return piece05;
    }

    public void setPiece05(String piece05) {
        this.piece05 = piece05;
    }

    public String getItemIen() {
        return itemIen;
    }

    public void setItemIen(String itemIen) {
        this.itemIen = itemIen;
    }

    public String getR3Code() {
        return r3Code;
    }

    public void setR3Code(String r3Code) {
        this.r3Code = r3Code;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public String getR3Cat() {
        return r3Cat;
    }

    public void setR3Cat(String r3Cat) {
        this.r3Cat = r3Cat;
    }

    public String getPiece10() {
        return piece10;
    }

    public void setPiece10(String piece10) {
        this.piece10 = piece10;
    }

    public String getPiece11() {
        return piece11;
    }

    public void setPiece11(String piece11) {
        this.piece11 = piece11;
    }

    public String getAlternativeText() {
        return alternativeText;
    }

    public void setAlternativeText(String alternativeText) {
        this.alternativeText = alternativeText;
    }

    public String getCptPovIen() {
        return cptPovIen;
    }

    public void setCptPovIen(String cptPovIen) {
        this.cptPovIen = cptPovIen;
    }

    public String getCodeDescription() {
        return codeDescription;
    }

    public void setCodeDescription(String codeDescription) {
        this.codeDescription = codeDescription;
    }

    public String getProgressNoteText() {
        return progressNoteText;
    }

    public void setProgressNoteText(String progressNoteText) {
        this.progressNoteText = progressNoteText;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public DialogPrompt() {

    }

    @Override
    public String toString() {
        return "DialogPrompt [dialogPromptType=" + dialogPromptType + ", dialogPromptTypeCode=" + dialogPromptTypeCode
                + ", dialogElementIen=" + dialogElementIen + ", lineId=" + lineId + ", findingTypeCode="
                + findingTypeCode + ", piece05=" + piece05 + ", itemIen=" + itemIen + ", r3Code=" + r3Code
                + ", displayText=" + displayText + ", r3Cat=" + r3Cat + ", piece10=" + piece10 + ", piece11=" + piece11
                + ", alternativeText=" + alternativeText + ", cptPovIen=" + cptPovIen + ", codeDescription="
                + codeDescription + ", progressNoteText=" + progressNoteText + ", record=" + record + "]";
    }

}
