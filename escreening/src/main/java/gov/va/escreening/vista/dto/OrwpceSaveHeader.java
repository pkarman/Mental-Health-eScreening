package gov.va.escreening.vista.dto;

import gov.va.escreening.vista.VistaUtils;
import gov.va.escreening.vista.extractor.VistaRecord;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class OrwpceSaveHeader implements Serializable, VistaRecord {

    private static final long serialVersionUID = 1L;

    private static final String prefix = "HDR";

    private Boolean isInpatient;
    private String visitLocationIen;
    private Date visitDate;
    private VistaServiceCategoryEnum vistaServiceCategory;

    public Boolean getIsInpatient() {
        return isInpatient;
    }

    public void setIsInpatient(Boolean isInpatient) {
        this.isInpatient = isInpatient;
    }

    public String getVisitLocationIen() {
        return visitLocationIen;
    }

    public void setVisitLocationIen(String visitLocationIen) {
        this.visitLocationIen = visitLocationIen;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public VistaServiceCategoryEnum getVistaServiceCategory() {
        return vistaServiceCategory;
    }

    public void setVistaServiceCategory(VistaServiceCategoryEnum vistaServiceCategory) {
        this.vistaServiceCategory = vistaServiceCategory;
    }

    public String getPrefix() {
        return prefix;
    }

    public String toVistaRecord() {

        String[] pieces = new String[4];

        pieces[0] = prefix;
        pieces[1] = (isInpatient) ? "1" : "0";
        pieces[2] = "";
        pieces[3] = (visitLocationIen + ";" + VistaUtils.convertToVistaDateString(visitDate, VistaDateFormat.MMddHHmmss) + ";" + vistaServiceCategory
                .getCode());

        return StringUtils.join(pieces, "^");
    }

}
