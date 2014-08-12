package gov.va.escreening.vista;

import gov.va.med.exception.FoundationsException;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnectionFactory;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnectionSpec;
import gov.va.med.vistalink.adapter.spi.VistaLinkManagedConnectionFactory;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcRequestFactory;

import javax.resource.ResourceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by pouncilt on 4/10/14.
 */
public abstract class BaseVistaLinkRPC_Client{
    private static final Logger logger = LoggerFactory.getLogger(BaseVistaLinkRPC_Client.class);
    @Autowired
    private VistaLinkManagedConnectionFactory vistaLinkManagedConnectionFactory;
    private VistaLinkConnectionSpec connectionSpec = null;
    private String appProxyName = null;
    private String rpcContext = null;
    private boolean useProprietaryMessageFormat = true;
    private int connectionTimeOut = 1000;
    private RpcRequest request = null;
    private VistaLinkConnection connection = null;

    public BaseVistaLinkRPC_Client() {

    }

    public BaseVistaLinkRPC_Client(VistaLinkManagedConnectionFactory vistaLinkManagedConnectionFactory, VistaLinkConnectionSpec connectionSpec, String appProxyName, String rpcContext) throws VistaLinkClientException {
        this.vistaLinkManagedConnectionFactory = vistaLinkManagedConnectionFactory;
        this.connectionSpec = connectionSpec;
        this.appProxyName = appProxyName;
        this.rpcContext = rpcContext;
        initialize();
    }

    public BaseVistaLinkRPC_Client(VistaLinkManagedConnectionFactory vistaLinkManagedConnectionFactory, VistaLinkConnectionSpec connectionSpec, String appProxyName, String rpcContext, boolean useProprietaryMessageFormat, int connectionTimeOut) throws VistaLinkClientException {
        this.vistaLinkManagedConnectionFactory = vistaLinkManagedConnectionFactory;
        this.connectionSpec = connectionSpec;
        this.appProxyName = appProxyName;
        this.rpcContext = rpcContext;
        this.useProprietaryMessageFormat = useProprietaryMessageFormat;
        this.connectionTimeOut = connectionTimeOut;
        initialize();
    }

    protected void initialize() throws VistaLinkClientException {
        if(connection == null) {
            try {
                connection = createVistaLinkConnection(this.connectionSpec, this.connectionTimeOut);
                request = createRpcRequest(this.appProxyName + this.rpcContext, this.connection, this.useProprietaryMessageFormat);
            } catch (ResourceException re) {
                throw new VistaLinkClientException(re);
            } catch (FoundationsException fe) {
                throw new VistaLinkClientException(fe);
            }
        }
    }

    private RpcRequest createRpcRequest( String rpcContext, VistaLinkConnection vistaLinkConnection, boolean useProprietaryMessageFormat) throws FoundationsException {
        RpcRequest request = RpcRequestFactory.getRpcRequest();
        request.setTimeOut(vistaLinkConnection.getTimeOut() * 2);
        request.setUseProprietaryMessageFormat(useProprietaryMessageFormat);
        request.setRpcContext(rpcContext);
        return request;
    }

    private VistaLinkConnection createVistaLinkConnection(VistaLinkConnectionSpec connectionSpec, int connectionTimeOut) throws ResourceException {
        VistaLinkConnectionFactory vistaLinkConnectionFactory = new VistaLinkConnectionFactory(vistaLinkManagedConnectionFactory, null);
        VistaLinkConnection vistaLinkConnection = (VistaLinkConnection) vistaLinkConnectionFactory.getConnection(connectionSpec);
        vistaLinkConnection.setTimeOut(connectionTimeOut);
        return vistaLinkConnection;
    }

    protected RpcRequest getRequest() {
        return request;
    }

    protected VistaLinkConnection getConnection() {
        return connection;
    }

    public void closeConnection () {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (ResourceException e) {
                logger.error("Exception thrown while trying to close VistaLinkConnection: ", e);
            }
        }
    }
}
