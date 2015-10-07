package gov.va.escreening.vista.dto;

import java.io.Serializable;

public class VistaLocation implements Serializable {

    private static final long serialVersionUID = 1L;

    final private Long ien;
    final private String name;

    public VistaLocation(Long ien, String name) {
        this.ien = ien;
        this.name = name != null ? name.trim() : null;
    }

    public Long getIen() {
        return ien;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("ien=%s|name=%s", ien, name);
    }

}
