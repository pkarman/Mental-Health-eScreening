package gov.va.escreening.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;

public abstract class RestController {
	
	
	protected void logRequest(Logger logger, HttpServletRequest request){
		logger.debug(request.getMethod() + ": "+ request.getRequestURL());
	}
	
}
