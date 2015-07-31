package gov.va.escreening.vista.dto;

/**
 * Created by pouncilt on 5/6/14.
 */
public abstract class VisitBaseInfo implements HealthFactorVisitData{
    private String data = null;
    private String additionalData = "";

    public VisitBaseInfo(String data, String additionalData) {
        this.data = data;
        if(additionalData != null) this.additionalData = additionalData;
    }

    @Override
    public String getDataElementName() {
        return "VST";
    }

    @Override
    public String getData() {
        return data;
    }

    @Override
    public String getAdditionalData() {
        return (getType() == VisitTypeEnum.OUTSIDE_LOCATION_HIST && getData().equals("0"))? additionalData: "";
    }

    @Override
    public String toString(){
        return getDataElementName() + "^" + getType().getCode() + "^" + getData() + "^" + getAdditionalData();
    }
}
