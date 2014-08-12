package gov.va.escreening.vista;

import gov.va.med.vistalink.adapter.cci.VistaLinkAppProxyConnectionSpec;
import gov.va.med.vistalink.adapter.spi.VistaLinkManagedConnectionFactory;

/**
 * Created by pouncilt on 4/10/14.
 */
class VistaLinkRPCProxyClient extends BaseVistaLinkRPC_Client implements VistaLinkProxyClient {

    public VistaLinkRPCProxyClient(VistaLinkManagedConnectionFactory vistaLinkManagedConnectionFactory, VistaLinkAppProxyConnectionSpec proxyConnectionSpec, String appProxyName, String rpcContext) throws VistaLinkClientException {
        super(vistaLinkManagedConnectionFactory, proxyConnectionSpec, appProxyName, rpcContext);
    }

    public VistaLinkRPCProxyClient(VistaLinkManagedConnectionFactory vistaLinkManagedConnectionFactory, VistaLinkAppProxyConnectionSpec proxyConnectionSpec, String appProxyName, String rpcContext, boolean useProprietaryMessageFormat, int connectionTimeOut) throws VistaLinkClientException {
        super(vistaLinkManagedConnectionFactory, proxyConnectionSpec, appProxyName, rpcContext, useProprietaryMessageFormat, connectionTimeOut);
    }
}
