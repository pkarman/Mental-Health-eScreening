package gov.va.escreening.vista.request;

import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.rpc.RpcRequest;

/**
 * Created by pouncilt on 6/13/14.
 */
public class ORQQCN_SVC_WITH_SYNONYMS_VistaLinkRequestContext<T extends VistaLinkRequestParameters> extends VistaLinkRequestBaseContext {
    public ORQQCN_SVC_WITH_SYNONYMS_VistaLinkRequestContext(RpcRequest request, VistaLinkConnection connection, VistaLinkRequestParameters requestParameters) {
        super(request, connection, requestParameters);
    }
}
