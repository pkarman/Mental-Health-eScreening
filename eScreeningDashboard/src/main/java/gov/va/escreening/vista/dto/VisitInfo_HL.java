package gov.va.escreening.vista.dto;

/**
 * Created by pouncilt on 5/6/14.
 */
public class VisitInfo_HL extends VisitBaseInfo implements HealthFactorVisitData {
    public VisitInfo_HL(String data){
        super(data, null);
    }

    public VisitInfo_HL(String data, String additionalData){
        super(data, additionalData);
    }

    @Override
    public VisitTypeEnum getType() {
        return VisitTypeEnum.ENCOUNTER_LOCATION;
    }
}
