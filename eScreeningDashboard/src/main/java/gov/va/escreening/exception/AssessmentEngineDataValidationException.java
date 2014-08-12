package gov.va.escreening.exception;

import gov.va.escreening.dto.ae.ErrorResponse;

public class AssessmentEngineDataValidationException extends ErrorResponseRuntimeException {

    private static final long serialVersionUID = 1L;
    
    //TODO: all usages of this should be replaced with an ErrorBuilder expression.
    public AssessmentEngineDataValidationException(ErrorResponse error){
        super(error);
    }
    
    //TODO: all usages of this should be replaced with an ErrorBuilder expression.
    public AssessmentEngineDataValidationException(String error){
        super(error);
    }
}
