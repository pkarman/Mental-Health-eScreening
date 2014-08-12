package gov.va.escreening.vista.request;

import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.rpc.RpcRequest;


/**
 * Created by pouncilt on 4/10/14.
 */
public class ORWPCE_GETSVC_VistaLinkRequestContext<T extends VistaLinkRequestParameters> extends VistaLinkRequestBaseContext {
    public ORWPCE_GETSVC_VistaLinkRequestContext(RpcRequest request, VistaLinkConnection connection, T requestParameters) {
        super (request, connection, requestParameters);
    }
}
