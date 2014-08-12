package gov.va.escreening.vista.dto;

/**
 * Created by pouncilt on 6/11/14.
 */
public class ConsultationUrgency implements ConsultationUrgencyInfo<ConsultationUrgency> {
    private String header = null;
    private String ien = null;
    private String name = null;
    private Long protocolIEN = null;

    public ConsultationUrgency(String header, String ien, String name, Long protocolIEN) {
        this.header = header;
        this.ien = ien;
        this.name = name;
        this.protocolIEN = protocolIEN;
    }

    public String getIen() {
        return ien;
    }

    @Override
    public String getName() {
        return name;
    }

    public Long getProtocolIEN() {
        return protocolIEN;
    }

    @Override
    public String getHeader() {
        return this.header;
    }

    public Boolean isDefaultChoice() {
        return this.ien.startsWith("d");
    }

    @Override
    public Boolean isInpatientInfo() {
        return this.header.contains("Inpt");
    }

    @Override
    public Boolean isOutpatientInfo() {
        return this.header.contains("Outpt");
    }

    @Override
    public Boolean hasLocationInfo() {
        return false;
    }

    @Override
    public Boolean hasUrgencyInfo() {
        return true;
    }

    @Override
    public ConsultationUrgency getConsultationInfoObject() {
        return this;
    }
}
