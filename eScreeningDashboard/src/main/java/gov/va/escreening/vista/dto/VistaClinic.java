package gov.va.escreening.vista.dto;

import java.io.Serializable;

public class VistaClinic implements Serializable {

    private static final long serialVersionUID = 1L;

    private String clinicIen;
    private String clinicName;

    public String getClinicIen() {
        return clinicIen;
    }

    public void setClinicIen(String clinicIen) {
        this.clinicIen = clinicIen;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public VistaClinic() {

    }

    public VistaClinic(String clinicIen, String clinicName) {
        this.clinicIen = clinicIen;
        this.clinicName = clinicName;
    }

    @Override
    public String toString() {
        return "VistaClinic [clinicIen=" + clinicIen + ", clinicName=" + clinicName + "]";
    }

}
