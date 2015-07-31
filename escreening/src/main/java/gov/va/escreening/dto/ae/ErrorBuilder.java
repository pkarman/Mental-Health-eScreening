package gov.va.escreening.dto.ae;

import static com.google.common.base.Preconditions.checkState;
import gov.va.escreening.exception.ErrorResponseException;


/**
 * Error builder used to instantiate the given ErrorResponseException subclass, then initializes the internal 
 * ResponseError so that we are sure to have a user error, an admin error, and 
 * an error ID (used to find corresponding log entries for errors seen in UI).<br/>
 * Note: You must call toUser and toAdmin at least once with a message or throwIt will error;
 * 
 * The expected way to use this class is:     <br/>
 *  ErrorBuilder  <br/>
 *      .throwing(IllegalSystemStateException.class)   <br/>
 *      .toUser("user friendly message")   <br/>
 *      .toAdmin("technical message") <br/>
 *      .throwIt();  <br/>
 * <br/>
 * 
 * @author Robin Carnow
 *
 */
public class ErrorBuilder<E extends Exception & ErrorResponseException > {
    
    public static <E extends Exception & ErrorResponseException>  ErrorBuilder<E> throwing(Class<E> exceptionClass){
        try {
            return new ErrorBuilder<E>(exceptionClass);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Security violation during build", e);
        }
    }
    
    private final ErrorResponseException exception;
    private final Class<E> exceptionClass;
    private ErrorBuilder(Class<E> exceptionClass){
        this.exceptionClass = exceptionClass;
        try {
            exception = exceptionClass.newInstance();
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Security violation during build", e);
        }
    }
    
    /**
     * Add a user error message
     * @param message
     * @return this builder so you can add an admin message
     */
    public ErrorBuilder<E> toUser(String message){
        exception.getErrorResponse().addMessage(message);
        return this;
    }
    
    public ErrorBuilder<E> toAdmin(String message){
        exception.getErrorResponse().addDeveloperMessage(message);
        return this;
    }
    
    /**
     * Add a user error message
     * @param message
     * @return this builder so you can add an admin message
     */
    public ErrorBuilder<E> toUser(ErrorMessage message){
        exception.getErrorResponse().addErrorMessage(message);
        return this;
    }
    
    /**
     * This is optional
     * @param code
     * @return
     */
    public ErrorBuilder<E> setCode(int code) {   //TODO: this should only accept an ErrorCodeEnum
        exception.getErrorResponse().setCode(code);
        return this;
    }
    
    /**
     * This is the last method call for the builder which throws the required exception 
     * @throws E the exception we have been building
     */
    public void throwIt() throws E{
        checkState(!exception.getErrorResponse().getDeveloperMessage().isEmpty()
                && !exception.getErrorResponse().getErrorMessages().isEmpty(), 
                "Both a user friendly message and a techinical message must be set before throwing");
        
        throw exceptionClass.cast(exception);
    }

   
}
