package gov.va.escreening.form;

import java.io.Serializable;

public class VeteranDetailFormBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer veteranId;
    private String veteranIen;
    private Boolean hasActiveAssessment;

    public Integer getVeteranId() {
        return veteranId;
    }

    public void setVeteranId(Integer veteranId) {
        this.veteranId = veteranId;
    }

    public String getVeteranIen() {
        return veteranIen;
    }

    public void setVeteranIen(String veteranIen) {
        this.veteranIen = veteranIen;
    }

    public Boolean getHasActiveAssessment() {
        return hasActiveAssessment;
    }

    public void setHasActiveAssessment(Boolean hasActiveAssessment) {
        this.hasActiveAssessment = hasActiveAssessment;
    }

    public VeteranDetailFormBean() {

    }
}
