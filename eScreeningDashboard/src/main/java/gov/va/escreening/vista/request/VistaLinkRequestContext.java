package gov.va.escreening.vista.request;

import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.rpc.RpcRequest;

/**
 * Created by pouncilt on 4/9/14.
 */
public interface VistaLinkRequestContext<T extends VistaLinkRequestParameters> {
    RpcRequest getRpcRequest();

    VistaLinkConnection getVistaLinkConnection();

    T getRequestParameters();
}
