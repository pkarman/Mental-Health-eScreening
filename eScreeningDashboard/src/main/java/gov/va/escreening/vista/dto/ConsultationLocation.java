package gov.va.escreening.vista.dto;

/**
 * Created by pouncilt on 6/11/14.
 */
public class ConsultationLocation implements ConsultationUrgencyInfo<ConsultationLocation> {
    private String header = null;
    private String choicePrefix = null;
    private String name = null;
    private String namePrefix = null;

    public ConsultationLocation(String header, String choicePrefix, String name, String namePrefix) {
        this.header = header;
        this.choicePrefix = choicePrefix;
        this.name = name;
        this.namePrefix = namePrefix;
    }

    public String getChoicePrefix() {
        return choicePrefix;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    @Override
    public String getHeader() {
        return header;
    }

    public Boolean isDefaultChoice() {
        return this.choicePrefix.startsWith("d");
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
        return true;
    }

    @Override
    public Boolean hasUrgencyInfo() {
        return false;
    }

    @Override
    public ConsultationLocation getConsultationInfoObject() {
        return this;
    }
}
