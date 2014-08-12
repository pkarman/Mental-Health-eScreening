package gov.va.escreening.vista.request;

import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.rpc.RpcRequest;

/**
 * Created by pouncilt on 5/17/14.
 */
public class ORQQPXRM_MENTAL_HEALTH_RESULTS_VistaLinkRequestContext<T extends VistaLinkRequestParameters> extends VistaLinkRequestBaseContext {
    public ORQQPXRM_MENTAL_HEALTH_RESULTS_VistaLinkRequestContext(RpcRequest request, VistaLinkConnection connection, VistaLinkRequestParameters requestParameters) {
        super(request, connection, requestParameters);
    }
}
