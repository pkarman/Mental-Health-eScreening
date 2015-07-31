package gov.va.escreening.vista.dto;

/**
 * Created by pouncilt on 5/6/14.
 */
public class VisitInfo_PR extends VisitBaseInfo implements HealthFactorVisitData {
    public VisitInfo_PR(String data) {
        super(data, null);
    }

    public VisitInfo_PR(String data, String additionalData) {
        super(data, additionalData);
    }

    @Override
    public VisitTypeEnum getType() {
        return VisitTypeEnum.PARENT_VISIT_IEN_HIST;
    }
}
