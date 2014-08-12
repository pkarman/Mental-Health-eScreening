package gov.va.escreening.vista;

/**
 * Created by pouncilt on 4/10/14.
 */
public class VistaLinkClientException extends RuntimeException {
	private static final long serialVersionUID = 6304113837985844322L;

	public VistaLinkClientException() {
	}

	public VistaLinkClientException(String s) {
		super(s);
	}

	public VistaLinkClientException(Throwable throwable) {
		super(throwable);
	}

	public VistaLinkClientException(String s, Throwable throwable) {
		super(s, throwable);
	}
}
