package gov.va.escreening.vista.request;

/**
 * Created by pouncilt on 4/9/14.
 */
public interface VistaLinkRequest<T>{
    public T sendRequest() throws VistaLinkRequestException;
    public void store() throws VistaLinkRequestException;
    public void load() throws VistaLinkRequestException;
}
