package gov.va.escreening.vista.request;

import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.rpc.RpcRequest;

/**
 * Created by pouncilt on 6/9/14.
 */
public class ORWDX_SAVE_VistaLinkRequestContext<T extends VistaLinkRequestParameters> extends VistaLinkRequestBaseContext {
    public ORWDX_SAVE_VistaLinkRequestContext(RpcRequest request, VistaLinkConnection connection, VistaLinkRequestParameters requestParameters) {
        super(request, connection, requestParameters);
    }
}
