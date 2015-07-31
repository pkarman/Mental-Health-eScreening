package gov.va.escreening.vista.request;

import gov.va.escreening.vista.dto.VistaServiceCategoryEnum;

/**
 * Created by pouncilt on 5/2/14.
 */
public class ORWPCE_GETSVC_RequestParameters extends VistaLinkRequestParameters {
    private VistaServiceCategoryEnum initialServiceConnectionCategory = null;
    private Long locationIEN = null;
    private Boolean inpatientStatus = false;


    public ORWPCE_GETSVC_RequestParameters(VistaServiceCategoryEnum initialServiceConnectionCategory, Long locationIEN, Boolean inpatientStatus) {
        this.initialServiceConnectionCategory = initialServiceConnectionCategory;
        this.locationIEN = locationIEN;
        if (inpatientStatus!= null) this.inpatientStatus = inpatientStatus;

        checkRequiredParameters();
    }

    public ORWPCE_GETSVC_RequestParameters(VistaServiceCategoryEnum initialServiceConnectionCategory, Long locationIEN) {
        this.initialServiceConnectionCategory = initialServiceConnectionCategory;
        this.locationIEN = locationIEN;

        checkRequiredParameters();
    }

    protected void checkRequiredParameters() {
        super.checkRequestParameterString("initialServiceConnectionCategory", initialServiceConnectionCategory.getCode(),  true);
        super.checkRequestParameterLong("locationIEN", locationIEN, true);
        super.checkRequestParameterBoolean("inpatientStatus", inpatientStatus, false);
    }

    public String getInitialServiceConnectionCategory() {
        return initialServiceConnectionCategory.getCode();
    }

    public Long getLocationIEN() {
        return locationIEN;
    }

    public Boolean getInpatientStatus() {
        return inpatientStatus;
    }
}
