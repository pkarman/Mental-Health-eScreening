package gov.va.escreening.vista.request;

/**
 * Created by pouncilt on 5/5/14.
 */
public class TIU_LOCK_RECORD_RequestParameters extends VistaLinkRequestParameters{
    private Long progressNoteIEN = null;

    public TIU_LOCK_RECORD_RequestParameters(Long progressNoteIEN) {
        this.progressNoteIEN = progressNoteIEN;

        checkRequiredParameters();
    }

    @Override
    protected void checkRequiredParameters() {
        super.checkRequestParameterLong("progressNoteIEN", progressNoteIEN, true);
    }

    public Long getProgressNoteIEN() {
        return progressNoteIEN;
    }
}
