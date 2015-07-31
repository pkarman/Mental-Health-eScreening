package gov.va.escreening.vista.dto;

import gov.va.escreening.vista.extractor.VistaRecord;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class OrwpceSaveProvider implements Serializable, VistaRecord {

    private static final long serialVersionUID = 1L;

    private static final String prefix = "PRV";

    private String providerIen;
    private String providerName;
    private Boolean isPrimaryPhysician;

    public String getProviderIen() {
        return providerIen;
    }

    public void setProviderIen(String providerIen) {
        this.providerIen = providerIen;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public Boolean getIsPrimaryPhysician() {
        return isPrimaryPhysician;
    }

    public void setIsPrimaryPhysician(Boolean isPrimaryPhysician) {
        this.isPrimaryPhysician = isPrimaryPhysician;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public static String getPrefix() {
        return prefix;
    }

    public OrwpceSaveProvider() {

    }

    public String toVistaRecord() {

        String[] pieces = new String[6];

        pieces[0] = prefix;
        pieces[1] = providerIen;
        pieces[2] = "";
        pieces[3] = "";
        pieces[4] = providerName;
        pieces[5] = (isPrimaryPhysician) ? "1" : "0";

        return StringUtils.join(pieces, "^");
    }

}
