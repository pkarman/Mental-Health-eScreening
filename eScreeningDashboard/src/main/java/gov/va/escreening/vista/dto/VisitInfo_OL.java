package gov.va.escreening.vista.dto;

/**
 * Created by pouncilt on 5/6/14.
 */
public class VisitInfo_OL extends VisitBaseInfo implements HealthFactorVisitData {
    public VisitInfo_OL(String data) {
        super(data, null);
    }

    public VisitInfo_OL(String data, String additionalData) {
        super(data, additionalData);
    }

    @Override
    public VisitTypeEnum getType() {
        return VisitTypeEnum.OUTSIDE_LOCATION_HIST;
    }
}
