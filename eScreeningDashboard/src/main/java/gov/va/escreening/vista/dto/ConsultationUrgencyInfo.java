package gov.va.escreening.vista.dto;

/**
 * Created by pouncilt on 6/11/14.
 */
public interface ConsultationUrgencyInfo <T> {
    public String getHeader();
    public String getName();
    public Boolean isInpatientInfo();
    public Boolean isOutpatientInfo();
    public Boolean hasLocationInfo();
    public Boolean hasUrgencyInfo();
    public Boolean isDefaultChoice();
    public T getConsultationInfoObject();
}
