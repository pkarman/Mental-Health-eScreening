package gov.va.escreening.vista.dto;

import gov.va.escreening.vista.extractor.VistaRecord;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class OrwpceSaveImmunization implements Serializable, VistaRecord  {

    private static final long serialVersionUID = 1L;

    private static final String prefix = "IMM";

    private Boolean removeImmunization;
    private String immunizationIen;
    private String immunizationName;
    private String series;
    private String reaction;
    private Boolean isContraindicated;
    private Integer sequenceNumber;

    public Boolean getRemoveImmunization() {
        return removeImmunization;
    }

    public void setRemoveImmunization(Boolean removeImmunization) {
        this.removeImmunization = removeImmunization;
    }

    public String getImmunizationIen() {
        return immunizationIen;
    }

    public void setImmunizationIen(String immunizationIen) {
        this.immunizationIen = immunizationIen;
    }

    public String getImmunizationName() {
        return immunizationName;
    }

    public void setImmunizationName(String immunizationName) {
        this.immunizationName = immunizationName;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public Boolean getIsContraindicated() {
        return isContraindicated;
    }

    public void setIsContraindicated(Boolean isContraindicated) {
        this.isContraindicated = isContraindicated;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public static String getPrefix() {
        return prefix;
    }

    public OrwpceSaveImmunization() {

    }

    public String toVistaRecord() {

        String[] pieces = new String[10];

        pieces[0] = prefix + ((removeImmunization == true) ? "-" : "+");
        pieces[1] = immunizationIen;
        pieces[2] = "";
        pieces[3] = immunizationName;
        pieces[4] = (series != null) ? series : "@";
        pieces[5] = "";
        pieces[6] = (reaction != null) ? reaction : "@";
        pieces[7] = (isContraindicated) ? "1" : "0";
        pieces[8] = "";
        pieces[9] = (sequenceNumber != null) ? sequenceNumber.toString() : "";

        return StringUtils.join(pieces, "^");
    }

}
