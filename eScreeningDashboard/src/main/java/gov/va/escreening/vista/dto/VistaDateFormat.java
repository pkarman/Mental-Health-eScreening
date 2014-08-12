package gov.va.escreening.vista.dto;

/**
 * Created by pouncilt on 6/2/14.
 */
public enum VistaDateFormat {
    MMddHHmmss("MMdd.HHmmss"),
    MMdd("MMdd");

    private final String format;

    public String getFormat() {
        return format;
    }

    VistaDateFormat(String format) {
        this.format = format;
    }
}
