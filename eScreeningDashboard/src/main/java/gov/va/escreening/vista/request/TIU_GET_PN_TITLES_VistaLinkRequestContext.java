package gov.va.escreening.vista.request;

import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.rpc.RpcRequest;

/**
 * Created by pouncilt on 4/10/14.
 */
public class TIU_GET_PN_TITLES_VistaLinkRequestContext<T extends VistaLinkRequestParameters> extends VistaLinkRequestBaseContext {
    public TIU_GET_PN_TITLES_VistaLinkRequestContext(RpcRequest request, VistaLinkConnection connection) {
        super(request, connection);
    }
}
