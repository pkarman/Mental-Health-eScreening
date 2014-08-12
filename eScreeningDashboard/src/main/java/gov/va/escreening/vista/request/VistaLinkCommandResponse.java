package gov.va.escreening.vista.request;

/**
 * Created by pouncilt on 4/9/14.
 */
public interface VistaLinkCommandResponse <T> {
    T getDTO();
    String getErrorMessage();
    boolean requestWasSuccessful();
}
