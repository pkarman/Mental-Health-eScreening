package gov.va.escreening.vista.request;

import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.rpc.RpcRequest;

/**
 * Created by pouncilt on 4/10/14.
 */
public abstract class VistaLinkRequestBaseContext<T extends VistaLinkRequestParameters> implements VistaLinkRequestContext <T> {
    private RpcRequest request = null;
    private VistaLinkConnection connection = null;
    private T requestParameters = null;

    public VistaLinkRequestBaseContext(RpcRequest request, VistaLinkConnection connection, T requestParameters) {
        if (request == null) throw new NullPointerException("RPCRequest can not be null.");
        if (connection == null) throw new NullPointerException("VistaLinkConnection can not be null.");
        if (requestParameters == null) throw new NullPointerException("RequestParameters can not be null.");

        this.request = request;
        this.connection = connection;
        this.requestParameters = requestParameters;
    }

    public VistaLinkRequestBaseContext(RpcRequest request, VistaLinkConnection connection) {
        if (request == null) throw new NullPointerException("RPCRequest can not be null.");
        if (connection == null) throw new NullPointerException("VistaLinkConnection can not be null.");

        this.request = request;
        this.connection = connection;
    }

    @Override
    public RpcRequest getRpcRequest() {
        return request;
    }

    @Override
    public VistaLinkConnection getVistaLinkConnection() {
        return connection;
    }

    @Override
    public T getRequestParameters() {
        return requestParameters;
    }
}
