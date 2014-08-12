package gov.va.escreening.vista.dto;

import gov.va.escreening.vista.extractor.VistaRecord;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class OrwpceSaveHealthFactor implements Serializable, VistaRecord  {

    private static final long serialVersionUID = 1L;

    private static final String prefix = "HF";

    private Boolean removeHealthFactor;
    private String healthFactorIen;
    private String category;
    private String healthFactorName;
    private String level;
    private Integer sequenceNumber;
    private String gecRen;

    public Boolean getRemoveHealthFactor() {
        return removeHealthFactor;
    }

    public void setRemoveHealthFactor(Boolean removeHealthFactor) {
        this.removeHealthFactor = removeHealthFactor;
    }

    public String getHealthFactorIen() {
        return healthFactorIen;
    }

    public void setHealthFactorIen(String healthFactorIen) {
        this.healthFactorIen = healthFactorIen;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHealthFactorName() {
        return healthFactorName;
    }

    public void setHealthFactorName(String healthFactorName) {
        this.healthFactorName = healthFactorName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getGecRen() {
        return gecRen;
    }

    public void setGecRen(String gecRen) {
        this.gecRen = gecRen;
    }

    public String getPrefix() {
        return prefix;
    }

    public String toVistaRecord() {

        String[] pieces = new String[11];

        pieces[0] = prefix + ((removeHealthFactor == true) ? "-" : "+");
        pieces[1] = healthFactorIen;
        pieces[2] = category;
        pieces[3] = healthFactorName;
        pieces[4] = (level != null) ? level : "@";
        pieces[5] = "";
        pieces[6] = "";
        pieces[7] = "";
        pieces[8] = "";
        pieces[9] = (sequenceNumber != null) ? sequenceNumber.toString() : "";
        pieces[10] = gecRen;

        return StringUtils.join(pieces, "^");
    }
}
