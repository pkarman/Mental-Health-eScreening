package gov.va.escreening.vista.request;

import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.adapter.record.VistaLinkFaultException;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcResponse;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pouncilt on 4/16/14.
 */
public class TIU_LOCK_RECORD_VistaLinkRequest extends VistaLinkBaseRequest implements VistaLinkRequest {
    private static final Logger logger = LoggerFactory.getLogger(TIU_LOCK_RECORD_VistaLinkRequest.class);
    private VistaLinkConnection connection = null;
    private RpcRequest request = null;
    private Long progressNoteIEN = null;

    public TIU_LOCK_RECORD_VistaLinkRequest(VistaLinkRequestContext<TIU_LOCK_RECORD_RequestParameters> requestContext) {
        this.request = requestContext.getRpcRequest();
        this.connection = requestContext.getVistaLinkConnection();
        this.progressNoteIEN = requestContext.getRequestParameters().getProgressNoteIEN();
    }

    @Override
    protected String[] parseRpcResponse(RpcResponse response) throws Exception {
        return super.parseRpcSimpleResponseWithCarrotDelimiter(response);
    }

    @Override
    public Boolean sendRequest() throws VistaLinkRequestException {
        Boolean progressNoteLocked = false;

        try {
            progressNoteLocked = this.lockProgressNote();
        } catch (Exception e) {
            throw new VistaLinkRequestException(e);
        }

        return progressNoteLocked;
    }

    private Boolean lockProgressNote() throws Exception{
        boolean progressNoteLocked = false;
        List<String> requestParams = new ArrayList<String>();
        requestParams.add(progressNoteIEN.toString()); // IEN of progress note. Required

        request.setRpcName("TIU LOCK RECORD");
        request.clearParams();
        request.setParams(requestParams);

        RpcResponse response = connection.executeRPC(request);
        logger.debug("Lock Progress Note Response: " + response.getResults());


        try {
            String[] responseArray = parseRpcResponse(response);
            if(responseArray.length == 1) {
                progressNoteLocked = responseArray[0].equals("0");
                if(!progressNoteLocked) {
                    throw new VistaLinkFaultException(responseArray[1]);
                }
            } else {
                throw new VistaLinkFaultException("Create Progress Note response did not return a valid documented response.");
            }
        } catch (VistaLinkNoResponseException vlnre) {
            throw new VistaLinkResponseException("Expected a response; but found no response." , requestParams, response.getResults(), vlnre);
        }

        return progressNoteLocked;
    }

    @Override
    public void store() throws VistaLinkRequestException {

    }

    @Override
    public void load() throws VistaLinkRequestException {

    }
}
