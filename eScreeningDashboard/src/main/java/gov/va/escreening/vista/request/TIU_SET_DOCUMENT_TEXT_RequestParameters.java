package gov.va.escreening.vista.request;

/**
 * Created by pouncilt on 5/5/14.
 */
public class TIU_SET_DOCUMENT_TEXT_RequestParameters extends VistaLinkRequestParameters {
    private Long progressNoteIEN = null;
    private String[] progressNoteBoilerPlateText = null;
    private String progressNoteContent = null;
    private Boolean progressNoteAppendContent = true;
    private Boolean suppressEditBuffer = false;

    public TIU_SET_DOCUMENT_TEXT_RequestParameters(Long progressNoteIEN, String[] progressNoteBoilerPlateText,
                                                   String progressNoteContent, Boolean progressNoteAppendContent,
                                                   Boolean suppressEditBuffer) {
        this.progressNoteIEN = progressNoteIEN;
        this.progressNoteBoilerPlateText = progressNoteBoilerPlateText;
        this.progressNoteContent = progressNoteContent;
        if(progressNoteAppendContent != null) this.progressNoteAppendContent = progressNoteAppendContent;
        if(suppressEditBuffer != null) this.suppressEditBuffer = suppressEditBuffer;

        checkRequiredParameters();
    }

    public TIU_SET_DOCUMENT_TEXT_RequestParameters(Long progressNoteIEN, String[] progressNoteBoilerPlateText,
                                                   String progressNoteContent) {
        this.progressNoteIEN = progressNoteIEN;
        this.progressNoteBoilerPlateText = progressNoteBoilerPlateText;
        this.progressNoteContent = progressNoteContent;

        checkRequiredParameters();
    }

    @Override
    protected void checkRequiredParameters() {
        super.checkRequestParameterLong("progressNoteIEN", progressNoteIEN, true);
        super.checkRequestParameterArray("progressNoteBoilerPlateText", progressNoteBoilerPlateText, true);
        super.checkRequestParameterString("progressNoteContent", progressNoteContent, false);
        super.checkRequestParameterBoolean("progressNoteAppendContent", progressNoteAppendContent, true);
        super.checkRequestParameterBoolean("suppressEditBuffer", suppressEditBuffer, false);
    }

    public Long getProgressNoteIEN() {
        return progressNoteIEN;
    }

    public String[] getProgressNoteBoilerPlateText() {
        return progressNoteBoilerPlateText;
    }

    public String getProgressNoteContent() {
        return progressNoteContent;
    }

    public Boolean isProgressNoteAppendContent() {
        return progressNoteAppendContent;
    }

    public Boolean isSuppressEditBuffer() {
        return suppressEditBuffer;
    }
}
