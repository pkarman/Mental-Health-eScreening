/**
 * The HttpRejectionProcessor handles an HTTP Rejection Message and returns a typed message specific to the HTTP 1.1
 * error code.
 * @author Bryan Henderson
 * @type   Static
 */

/**
 * Constructor empty, does nothing.  We're only interested in the static method.
 * @constructor
 */
function HttpRejectionProcessor(){};

/**
 * Static method takes rejection message, returns error type-specific string.
 * @param rejection
 * @returns {String}
 */
HttpRejectionProcessor.processRejection = function(rejection){
	/* We can ignore most non-200 status messages, but some,
    the egregious ones, we'll collect for display.*/
	var rejectMsg = "The server encountered an error processing your request.";
	 switch (rejection.status){
	 		case 500:
	 			// Server-side processing error.
	 			rejectMsg = "The server encountered an error processing your request. Please contact your administrator.";
	 			break;
	 		case 503:
	 			// Gateway Timeout.
	 			rejectMsg = "The request timed out contacting the server. Please check network connection.";
	 			break;
	 		case 403:
	 		case 404:
	 			/* This should be caught by the server, and the user shunted to the global errors page, so...
	 			just in case */
	 			rejectMsg = "The requested resource is unavailable, or you don't have permission to access it.  Please contact your administrator.";
	 			break;
	 		case 301:
	 			rejectMsg = "The requested resource has been permanently moved.  Please contact your administrator.";
	 			break;
	 		case 400:
	 			// Messed up request.  Our (developers) bad.
	 			rejectMsg = "The server refused the request. Please contact your administrator.";
	 			break;
	 		
	 }
	 return rejectMsg;
};