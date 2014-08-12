package gov.va.escreening.vista.dto;

import java.io.Serializable;

public class VistaLocation implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long ien;
    private String name;

    public Long getIen() {
        return ien;
    }

    public String getName() {
        return name;
    }

    public VistaLocation(Long ien, String name) {
        this.ien = ien;
        this.name = name;
    }

    @Override
    public String toString() {
        return "VistaLocation [ien=" + ien + ", name=" + name + "]";
    }

}
