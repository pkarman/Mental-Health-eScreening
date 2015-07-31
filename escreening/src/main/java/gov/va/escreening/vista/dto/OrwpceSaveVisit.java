package gov.va.escreening.vista.dto;

import gov.va.escreening.vista.VistaUtils;
import gov.va.escreening.vista.extractor.VistaRecord;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class OrwpceSaveVisit implements Serializable, VistaRecord {

    private static final long serialVersionUID = 1L;

    private static final String prefix = "VST";

    private VisitTypeEnum visitType;
    private Date visitDate;
    private String patientIen;
    private String locationIen;
    private VistaServiceCategoryEnum vistaServiceCategory;
    private String parentVisitIen;
    private String freeText;

    public VisitTypeEnum getVisitType() {
        return visitType;
    }

    public void setVisitType(VisitTypeEnum visitType) {
        this.visitType = visitType;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public String getPatientIen() {
        return patientIen;
    }

    public void setPatientIen(String patientIen) {
        this.patientIen = patientIen;
    }

    public String getLocationIen() {
        return locationIen;
    }

    public void setLocationIen(String locationIen) {
        this.locationIen = locationIen;
    }

    public VistaServiceCategoryEnum getVistaServiceCategory() {
        return vistaServiceCategory;
    }

    public void setVistaServiceCategory(VistaServiceCategoryEnum vistaServiceCategory) {
        this.vistaServiceCategory = vistaServiceCategory;
    }

    public String getParentVisitIen() {
        return parentVisitIen;
    }

    public void setParentVisitIen(String parentVisitIen) {
        this.parentVisitIen = parentVisitIen;
    }

    public String getFreeText() {
        return freeText;
    }

    public void setFreeText(String freeText) {
        this.freeText = freeText;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public static String getPrefix() {
        return prefix;
    }

    public String toVistaRecord() {

        String[] pieces = new String[3];

        if (visitType == VisitTypeEnum.OUTSIDE_LOCATION_HIST) {
            pieces = new String[4];
        }

        pieces[0] = prefix;
        pieces[1] = visitType.getCode();

        switch (visitType) {
        case ENCOUNTER_DATE:
            pieces[2] = VistaUtils.convertToVistaDateString(visitDate, VistaDateFormat.MMddHHmmss);
            break;
        case PATIENT:
            pieces[2] = patientIen;
            break;
        case ENCOUNTER_LOCATION:
            pieces[2] = locationIen;
            break;
        case ENCOUNTER_SERVICE_CATEGORY:
            pieces[2] = vistaServiceCategory.getCode();
            break;
        case PARENT_VISIT_IEN_HIST:
            pieces[2] = parentVisitIen;
            break;
        case OUTSIDE_LOCATION_HIST:
            pieces[2] = (freeText != null) ? "0" : locationIen;
            pieces[3] = freeText;
            break;
        }

        return StringUtils.join(pieces, "^");
    }

}
