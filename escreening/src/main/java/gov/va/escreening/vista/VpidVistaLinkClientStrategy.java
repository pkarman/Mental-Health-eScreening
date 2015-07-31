package gov.va.escreening.vista;

import gov.va.med.vistalink.adapter.cci.VistaLinkVpidConnectionSpec;
import gov.va.med.vistalink.adapter.spi.VistaLinkManagedConnectionFactory;

/**
 * Created by pouncilt on 4/18/14.
 */
public class VpidVistaLinkClientStrategy implements VistaLinkClientStrategy {
    private VistaLinkClient client = null;
    public VpidVistaLinkClientStrategy(VistaLinkManagedConnectionFactory vistaLinkManagedConnectionFactory, VistaLinkVpidConnectionSpec vpidConnectionSpec, String appProxyName, String rpcContext) throws VistaLinkClientException {
        this.client = new VistaLinkRPC_Client2(vistaLinkManagedConnectionFactory, vpidConnectionSpec, appProxyName, rpcContext);
    }

    public VpidVistaLinkClientStrategy(VistaLinkManagedConnectionFactory vistaLinkManagedConnectionFactory, VistaLinkVpidConnectionSpec vpidConnectionSpec, String appProxyName, String rpcContext, Boolean useProprietaryMessageFormat, int connectionTimeOut) throws VistaLinkClientException {
        this.client = new VistaLinkRPC_Client2(vistaLinkManagedConnectionFactory, vpidConnectionSpec, appProxyName, rpcContext, useProprietaryMessageFormat, connectionTimeOut);
    }

    @Override
    public VistaLinkClient getClient() {
        return client;
    }
}
