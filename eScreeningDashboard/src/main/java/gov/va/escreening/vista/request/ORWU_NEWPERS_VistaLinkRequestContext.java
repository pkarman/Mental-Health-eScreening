package gov.va.escreening.vista.request;

import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.rpc.RpcRequest;

/**
 * Created by pouncilt on 4/11/14.
 */
public class ORWU_NEWPERS_VistaLinkRequestContext<T extends VistaLinkRequestParameters> extends VistaLinkRequestBaseContext {
    public ORWU_NEWPERS_VistaLinkRequestContext(RpcRequest request, VistaLinkConnection connection,T requestParameters) {
        super(request, connection, requestParameters);
    }
}
