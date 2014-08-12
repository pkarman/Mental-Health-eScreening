package gov.va.escreening.vista.request;

import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.rpc.RpcRequest;

/**
 * Created by pouncilt on 5/29/14.
 */
public class ORWPT_SELECT_VistaLinkRequestContext<T extends ORWPT_SELECT_RequestParameters> extends VistaLinkRequestBaseContext {

    public ORWPT_SELECT_VistaLinkRequestContext(RpcRequest request, VistaLinkConnection connection, T requestParameters) {
        super(request, connection, requestParameters);
    }
}
