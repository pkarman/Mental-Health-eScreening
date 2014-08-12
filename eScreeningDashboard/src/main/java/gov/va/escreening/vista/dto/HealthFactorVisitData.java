package gov.va.escreening.vista.dto;

/**
 * Created by pouncilt on 5/5/14.
 */
public interface HealthFactorVisitData {
    public String getDataElementName();
    public VisitTypeEnum getType();
    public String getData();
    public String getAdditionalData();
}
