package gov.va.escreening.vista.request;

/**
 * Created by pouncilt on 4/10/14.
 */
public class VistaLinkCommandGenericResponse<T> implements VistaLinkCommandResponse{
    private String errorMessage = null;
    private T dto = null;

    public VistaLinkCommandGenericResponse(T dto) {
        this.dto = dto;
    }

    public VistaLinkCommandGenericResponse(String errorMessage){
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public boolean requestWasSuccessful() {
        return (errorMessage == null) ? false: true;
    }

    @Override
    public T getDTO() {
        return dto;
    }
}
