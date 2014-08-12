package gov.va.escreening.controller.dashboard;

import gov.va.escreening.dto.ae.ErrorBuilder;
import gov.va.escreening.dto.ae.ErrorMessage;
import gov.va.escreening.dto.ae.ErrorResponse;
import gov.va.escreening.exception.EscreeningDataValidationException;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value = "/dashboard")
public abstract class BaseDashboardRestController {

	private static final Logger logger = LoggerFactory.getLogger(BaseDashboardRestController.class);
	public static final int MAX_PAGE_SIZE = 1000;

	@ExceptionHandler(EscreeningDataValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleException(EscreeningDataValidationException ex) {

		logger.debug("==>Data Validation Exception");

		ErrorResponse errorResponse = ex.getErrorResponse().setStatus(HttpStatus.BAD_REQUEST.value());

		logger.debug(ex.toString());
		logger.debug(ex.getErrorResponse().toString());

		return errorResponse;
	}

	protected void handleResultErrors(BindingResult result) throws EscreeningDataValidationException {
		if (result.hasErrors()) {
			ErrorBuilder<EscreeningDataValidationException> errorBuilder = ErrorBuilder.throwing(EscreeningDataValidationException.class);

			for (ObjectError objectError : result.getAllErrors()) {

				ErrorMessage errorMessage = new ErrorMessage();

				if (objectError instanceof FieldError) {
					errorMessage.setName(((FieldError) objectError).getField());
				} else {
					errorMessage.setName(objectError.getObjectName());
				}

				errorMessage.setDescription(objectError.getDefaultMessage());
				errorBuilder.toUser(errorMessage);
			}
			// set admin message and throw error
			errorBuilder.toAdmin("BaseDashboardRestController.handleResultErrors:  called with " + result.getAllErrors().size() + " errors").throwIt();
		}
	}

	/**
	 * this method will only do its thing when in debug mode
	 * 
	 * @param request
	 */
	protected void displaySearchParms(HttpServletRequest request) {
		if (logger.isDebugEnabled()) {
			// Print out all the parameters sent by the caller.
			@SuppressWarnings("rawtypes")
			Enumeration itr1 = request.getParameterNames();

			while (itr1.hasMoreElements()) {
				String current = (String) itr1.nextElement();
				logger.debug(current + " : " + request.getParameter(current));
			}
		}
	}
}
