package gov.va.escreening.vista.request;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pouncilt on 6/12/14.
 */
public class VistaLinkResponseException extends VistaLinkRequestException {
    private static final long serialVersionUID = 3600864986650679823L;
    private static final Logger logger = LoggerFactory.getLogger(VistaLinkResponseException.class);


    public VistaLinkResponseException(String message) {
        super(message);
        logger.error(message);
    }

    public VistaLinkResponseException(String message, List<Object> requestParams, String response) {
        super(message, requestParams);

        logger.error(response);
    }

    public VistaLinkResponseException(String message, List<Object> requestParams, String response, LogTypes logType) {
        super(message, requestParams, logType);
        
        if(logType.equals(LogTypes.ERROR)) logger.error(response);
        if(logType.equals(LogTypes.WARNING)) logger.warn(response);
        if(logType.equals(LogTypes.DEBUG)) logger.debug(response);
        if(logType.equals(LogTypes.INFO)) logger.info(response);
    }

    public VistaLinkResponseException(String message, List requestParams, String response, Throwable throwable) {
        super(message, requestParams, throwable);
        logger.error("RPC Response: " + response);
    }

    public VistaLinkResponseException(String message, List requestParams, String response, LogTypes logType, Throwable throwable) {
        super(message, requestParams, logType, throwable);
        logger.error("RPC Response: " + response);

        if(logType.equals(LogTypes.ERROR)) logger.error(response);
        if(logType.equals(LogTypes.WARNING)) logger.warn(response);
        if(logType.equals(LogTypes.DEBUG)) logger.debug(response);
        if(logType.equals(LogTypes.INFO)) logger.info(response);
    }
}
