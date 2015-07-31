package gov.va.escreening.vista.request;

/**
 * Created by pouncilt on 4/17/14.
 */
public class VistaLinkNoResponseException extends VistaLinkRequestException {
    private static final long serialVersionUID = 121974052321673265L;

    public VistaLinkNoResponseException() {
        super();
    }
    public VistaLinkNoResponseException(String message) {
        super(message);
    }
}
