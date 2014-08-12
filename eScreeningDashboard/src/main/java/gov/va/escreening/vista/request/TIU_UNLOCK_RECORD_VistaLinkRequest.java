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
public class TIU_UNLOCK_RECORD_VistaLinkRequest extends VistaLinkBaseRequest implements VistaLinkRequest {
    private static final Logger logger = LoggerFactory.getLogger(TIU_UNLOCK_RECORD_VistaLinkRequest.class);
    private VistaLinkConnection connection = null;
    private RpcRequest request = null;
    private Long progressNoteIEN = null;

    public TIU_UNLOCK_RECORD_VistaLinkRequest(VistaLinkRequestContext<TIU_UNLOCK_RECORD_RequestParameters> requestContext) {
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
        Boolean unlockProgressNoteSuccessful = false;
        try {
            unlockProgressNoteSuccessful = this.unlockProgressNote();
        } catch (Exception e) {
            throw new VistaLinkRequestException(e);
        }
        return unlockProgressNoteSuccessful;
    }

    public boolean unlockProgressNote() throws Exception{
        boolean progressNoteUnLocked = false;
        List<String> requestParams = new ArrayList<String>();
        requestParams.add(this.progressNoteIEN.toString()); // IEN of progress note. Required

        request.setRpcName("TIU UNLOCK RECORD");
        request.clearParams();
        request.setParams(requestParams);

        RpcResponse response = connection.executeRPC(request);
        logger.debug("UnLock Progress Note Response: " + response.getResults());


        String[] responseArray = new String[0];
        try {
            responseArray = parseRpcResponse(response);
            if(responseArray.length == 1) {
                progressNoteUnLocked = responseArray[0].equals("0");
            } else if(responseArray.length == 2) {
                throw new VistaLinkFaultException(responseArray[1]);
            } else {
                throw new VistaLinkFaultException("Create Progress Note response did not return a valid documented response.");
            }
        } catch (VistaLinkNoResponseException vlnre) {
            throw new VistaLinkResponseException("Expected a response; but found no response." , requestParams, response.getResults(), vlnre);
        }

        return progressNoteUnLocked;
    }

    @Override
    public void store() throws VistaLinkRequestException {

    }

    @Override
    public void load() throws VistaLinkRequestException {

    }
}
