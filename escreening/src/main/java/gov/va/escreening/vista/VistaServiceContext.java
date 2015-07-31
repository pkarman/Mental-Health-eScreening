package gov.va.escreening.vista;

/**
 * Created by pouncilt on 4/18/14.
 */
public class VistaServiceContext <T>{
    VistaLinkClientStrategy clientStrategy;
    T requestParameters;

    public VistaServiceContext(VistaLinkClientStrategy clientStrategy, T requestParameters) {
        this.clientStrategy = clientStrategy;
        this.requestParameters = requestParameters;
    }

    public VistaLinkClient getVistaClient(){
        return clientStrategy.getClient();
    }

    public T getRequestParameters(){
        return requestParameters;
    }
}
