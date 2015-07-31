package gov.va.escreening.vista.request;

/**
 * Created by pouncilt on 6/11/14.
 */
public class ORWDCN32_DEF_RequestParameters extends VistaLinkRequestParameters {
    //TODO: Consider using a Enum for Consult and Procedure values.
    private String orderType = null; // C for Consult or P for procedure

    public ORWDCN32_DEF_RequestParameters(String orderType) {
        this.orderType = orderType;

        checkRequiredParameters();
    }

    @Override
    protected void checkRequiredParameters() {
        super.checkRequestParameterString("orderType", orderType, true);
    }

    public String getOrderType() {
        return orderType;
    }
}
