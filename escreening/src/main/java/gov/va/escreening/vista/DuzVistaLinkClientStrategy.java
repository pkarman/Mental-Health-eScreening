package gov.va.escreening.vista;

import gov.va.med.vistalink.adapter.cci.VistaLinkDuzConnectionSpec;
import gov.va.med.vistalink.adapter.spi.VistaLinkManagedConnectionFactory;

/**
 * Created by pouncilt on 4/18/14.
 */
public class DuzVistaLinkClientStrategy implements VistaLinkClientStrategy {
    private VistaLinkClient client = null;

    public DuzVistaLinkClientStrategy(VistaLinkManagedConnectionFactory vistaLinkManagedConnectionFactory, VistaLinkDuzConnectionSpec duzConnectionSpec, String appProxyName, String rpcContext) throws VistaLinkClientException {
        this.client = new VistaLinkRPC_Client2(vistaLinkManagedConnectionFactory, duzConnectionSpec, appProxyName, rpcContext);
    }

    public DuzVistaLinkClientStrategy(VistaLinkManagedConnectionFactory vistaLinkManagedConnectionFactory, VistaLinkDuzConnectionSpec duzConnectionSpec, String appProxyName, String rpcContext, Boolean useProprietaryMessageFormat, int connectionTimeOut) throws VistaLinkClientException {
        this.client = new VistaLinkRPC_Client2(vistaLinkManagedConnectionFactory, duzConnectionSpec, appProxyName, rpcContext, useProprietaryMessageFormat, connectionTimeOut);
    }

    @Override
    public VistaLinkClient getClient() {
        return client;
    }
}
