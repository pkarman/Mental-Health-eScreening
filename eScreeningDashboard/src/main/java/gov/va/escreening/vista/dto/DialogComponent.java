package gov.va.escreening.vista.dto;

import java.io.Serializable;
import java.util.List;

public class DialogComponent implements Serializable {

    private static final long serialVersionUID = 1L;

    // Reminder dialogs have dialog group or dialog element components.
    // Dialog groups have a combination of dialog element components.
    // Dialog elements have prompt and forced value components.

    // Dialog component types.
    // 'P' FOR prompt;
    // 'E' FOR dialog element;
    // 'R' FOR reminder dialog;
    // 'F' FOR forced value;
    // 'G' FOR dialog group;
    // 'S' FOR result group;
    // 'T' FOR result element;

    // Reminder Finding Type parameters:
    // CPT Procedure
    // ED Education Topic
    // EX Exam
    // HF Health Factor
    // IMM Immunization
    // OI Orderable Item
    // POV Diagnosis (ICD9)
    // ST Skin Test
    // VM Vital Measurement
    // WH WH Notification Purpose (e.g. PXRM Contraindicated is restricted to IMMUNIZATION, finding type IM)

    // Group Entry:
    // '1' FOR ONE SELECTION ONLY;
    // '2' FOR ONE OR MORE SELECTIONS;
    // '3' FOR NONE OR ONE SELECTION;
    // '4' FOR ALL SELECTIONS ARE REQUIRED;
    // '0' FOR NO SELECTION REQUIRED;

    private String mode;
    private String dialogComponentIen;
    private String sequenceString;
    private String inputType; // S or D or T
    private Boolean excludeFromProgressNote; // 1 exclude, 0 no exclusion
    private Integer indentSize; // Number of indents
    private String findingTypeString;
    private Boolean doneElsewhereHistorical; // 1 = done elsewhere (historical)
    private Boolean excludeMentalHealthTestFromProgressNote; // 1 = exclude
    private String resultGroupListString;
    private String dcount; // don't know what this is.
    private Boolean hideUntilChecked; // 1 = hide, 0 = show
    private Integer childrenIndentSize;
    private Boolean shareCommonPrompt; // 1 = yes
    private Integer groupEntry; // 1 = 1 only, 2 = 1+, 3 = 0-1, 4 = all
                                // required, 0 = non required.
    private Boolean drawBox; // Y or 1 = Draw Box
    private String caption; // 3 - 30 characters.
    private Boolean indentProgressNote; // 1 = indent
    private String displayText; // (for type T: The 2nd "2 mode" line will be T)
    private String dexc; // (for type T: ??)
    private String unused;
    private String cptPovType; // (for type T: CPT or POV)
    private Boolean historical; // (for type T: Historical/current flag)
    private String record;
    private List<DialogPrompt> dialogPromptList;
    private List<DialogComponent> childDialogComponents;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getDialogComponentIen() {
        return dialogComponentIen;
    }

    public void setDialogComponentIen(String dialogComponentIen) {
        this.dialogComponentIen = dialogComponentIen;
    }

    public String getSequenceString() {
        return sequenceString;
    }

    public void setSequenceString(String sequenceString) {
        this.sequenceString = sequenceString;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public Boolean getExcludeFromProgressNote() {
        return excludeFromProgressNote;
    }

    public void setExcludeFromProgressNote(Boolean excludeFromProgressNote) {
        this.excludeFromProgressNote = excludeFromProgressNote;
    }

    public Integer getIndentSize() {
        return indentSize;
    }

    public void setIndentSize(Integer indentSize) {
        this.indentSize = indentSize;
    }

    public String getFindingTypeString() {
        return findingTypeString;
    }

    public void setFindingTypeString(String findingTypeString) {
        this.findingTypeString = findingTypeString;
    }

    public Boolean getDoneElsewhereHistorical() {
        return doneElsewhereHistorical;
    }

    public void setDoneElsewhereHistorical(Boolean doneElsewhereHistorical) {
        this.doneElsewhereHistorical = doneElsewhereHistorical;
    }

    public Boolean getExcludeMentalHealthTestFromProgressNote() {
        return excludeMentalHealthTestFromProgressNote;
    }

    public void setExcludeMentalHealthTestFromProgressNote(
            Boolean excludeMentalHealthTestFromProgressNote) {
        this.excludeMentalHealthTestFromProgressNote = excludeMentalHealthTestFromProgressNote;
    }

    public String getResultGroupListString() {
        return resultGroupListString;
    }

    public void setResultGroupListString(String resultGroupListString) {
        this.resultGroupListString = resultGroupListString;
    }

    public String getDcount() {
        return dcount;
    }

    public void setDcount(String dcount) {
        this.dcount = dcount;
    }

    public Boolean getHideUntilChecked() {
        return hideUntilChecked;
    }

    public void setHideUntilChecked(Boolean hideUntilChecked) {
        this.hideUntilChecked = hideUntilChecked;
    }

    public Integer getChildrenIndentSize() {
        return childrenIndentSize;
    }

    public void setChildrenIndentSize(Integer childrenIndentSize) {
        this.childrenIndentSize = childrenIndentSize;
    }

    public Boolean getShareCommonPrompt() {
        return shareCommonPrompt;
    }

    public void setShareCommonPrompt(Boolean shareCommonPrompt) {
        this.shareCommonPrompt = shareCommonPrompt;
    }

    public Integer getGroupEntry() {
        return groupEntry;
    }

    public void setGroupEntry(Integer groupEntry) {
        this.groupEntry = groupEntry;
    }

    public Boolean getDrawBox() {
        return drawBox;
    }

    public void setDrawBox(Boolean drawBox) {
        this.drawBox = drawBox;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Boolean getIndentProgressNote() {
        return indentProgressNote;
    }

    public void setIndentProgressNote(Boolean indentProgressNote) {
        this.indentProgressNote = indentProgressNote;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public String getDexc() {
        return dexc;
    }

    public void setDexc(String dexc) {
        this.dexc = dexc;
    }

    public String getUnused() {
        return unused;
    }

    public void setUnused(String unused) {
        this.unused = unused;
    }

    public String getCptPovType() {
        return cptPovType;
    }

    public void setCptPovType(String cptPovType) {
        this.cptPovType = cptPovType;
    }

    public Boolean getHistorical() {
        return historical;
    }

    public void setHistorical(Boolean historical) {
        this.historical = historical;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public List<DialogPrompt> getDialogPromptList() {
        return dialogPromptList;
    }

    public void setDialogPromptList(List<DialogPrompt> dialogPromptList) {
        this.dialogPromptList = dialogPromptList;
    }

    public List<DialogComponent> getChildDialogComponents() {
        return childDialogComponents;
    }

    public void setChildDialogComponents(
            List<DialogComponent> childDialogComponents) {
        this.childDialogComponents = childDialogComponents;
    }

    public DialogComponent() {

    }

    @Override
    public String toString() {
        return "DialogComponent [mode=" + mode + ", dialogComponentIen=" + dialogComponentIen + ", sequenceString="
                + sequenceString + ", inputType=" + inputType + ", excludeFromProgressNote=" + excludeFromProgressNote
                + ", indentSize=" + indentSize + ", findingTypeString=" + findingTypeString
                + ", doneElsewhereHistorical=" + doneElsewhereHistorical + ", excludeMentalHealthTestFromProgressNote="
                + excludeMentalHealthTestFromProgressNote + ", resultGroupListString=" + resultGroupListString
                + ", dcount=" + dcount + ", hideUntilChecked=" + hideUntilChecked + ", childrenIndentSize="
                + childrenIndentSize + ", shareCommonPrompt=" + shareCommonPrompt + ", groupEntry=" + groupEntry
                + ", drawBox=" + drawBox + ", caption=" + caption + ", indentProgressNote=" + indentProgressNote
                + ", displayText=" + displayText + ", dexc=" + dexc + ", unused=" + unused + ", cptPovType="
                + cptPovType + ", historical=" + historical + ", record=" + record + ", dialogPromptList="
                + dialogPromptList + ", childDialogComponents=" + childDialogComponents + "]";
    }

}
