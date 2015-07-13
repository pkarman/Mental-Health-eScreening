package gov.va.escreening.vista.request;

import gov.va.escreening.vista.VistaLinkClientException;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pouncilt on 4/9/14.
 */
public class VistaLinkRequestException extends VistaLinkClientException {
    private static final long serialVersionUID = -1851363056204203373L;
    private static final Logger logger = LoggerFactory.getLogger(VistaLinkRequestException.class);
    public static enum LogTypes {ERROR, WARNING, DEBUG, INFO}
    public VistaLinkRequestException() {
    }

    public VistaLinkRequestException(String message) {
        super(message);
        logger.error(message);
    }

    public VistaLinkRequestException(String message, LogTypes logType) {
        super(message);
        
        if(logType.equals(LogTypes.ERROR)) logger.error(message);
        if(logType.equals(LogTypes.WARNING)) logger.warn(message);
        if(logType.equals(LogTypes.DEBUG)) logger.debug(message);
        if(logType.equals(LogTypes.INFO)) logger.info(message);
        
    }

    public VistaLinkRequestException(String message, List requestParams) {
        super(message);
        
        if(message != null) logger.error(message);
        if(requestParams != null) logger.error(printRequestParams(requestParams));
    }

    public VistaLinkRequestException(String message, List requestParams, LogTypes logType) {
        super(message);

        if(logType.equals(LogTypes.ERROR)) logger.error(message);
        if(logType.equals(LogTypes.WARNING)) logger.warn(message);
        if(logType.equals(LogTypes.DEBUG)) logger.debug(message);
        if(logType.equals(LogTypes.INFO)) logger.info(message);
        
        if(logType.equals(LogTypes.ERROR)) logger.error(printRequestParams(requestParams));
        if(logType.equals(LogTypes.WARNING)) logger.warn(printRequestParams(requestParams));
        if(logType.equals(LogTypes.DEBUG)) logger.debug(printRequestParams(requestParams));
        if(logType.equals(LogTypes.INFO)) logger.info(printRequestParams(requestParams));
    }

    public VistaLinkRequestException(Throwable throwable) {
        super(throwable);
    }

    public VistaLinkRequestException(String message, Throwable throwable) {
        super(message, throwable);
        logger.error(message);
    }

    public VistaLinkRequestException(String message, List requestParams, Throwable throwable) {
        super(message, throwable);
        
        logger.error(message);
        logger.error(printRequestParams(requestParams));
    }

    public VistaLinkRequestException(String message, List requestParams, LogTypes logType, Throwable throwable) {
        super(message, throwable);

        if(logType.equals(LogTypes.ERROR)) logger.error(message);
        if(logType.equals(LogTypes.WARNING)) logger.warn(message);
        if(logType.equals(LogTypes.DEBUG)) logger.debug(message);
        if(logType.equals(LogTypes.INFO)) logger.info(message);
        
        if(logType.equals(LogTypes.ERROR)) logger.error(printRequestParams(requestParams));
        if(logType.equals(LogTypes.WARNING)) logger.warn(printRequestParams(requestParams));
        if(logType.equals(LogTypes.DEBUG)) logger.debug(printRequestParams(requestParams));
        if(logType.equals(LogTypes.INFO)) logger.info(printRequestParams(requestParams));
    }


    private String printRequestParams(List requestParams) {
        StringBuilder sb = new StringBuilder("RPC Request Parameters:\n");
        int rpcRequestParamsPosition = 1;
        for(Object param: requestParams) {
            sb.append((rpcRequestParamsPosition++) + ". " + param.toString() + "\n");
        }
        return sb.toString();
    }
}
