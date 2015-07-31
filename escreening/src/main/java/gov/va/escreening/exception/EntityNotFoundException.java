package gov.va.escreening.exception;

import gov.va.escreening.dto.ae.ErrorResponse;

/**
 * When an ID is given for a REST call which does not exist on the system.
 * 
 * @author Robin Carnow
 * 
 */
public class EntityNotFoundException extends ErrorResponseRuntimeException {
	private static final long serialVersionUID = 1L;

	//needed for AopUtils; DO NOT USE
	@Deprecated
	public EntityNotFoundException() {}

	public EntityNotFoundException(ErrorResponse error) {
		super(error);
	}

	public EntityNotFoundException(String error) {
		super(error);
	}
}
