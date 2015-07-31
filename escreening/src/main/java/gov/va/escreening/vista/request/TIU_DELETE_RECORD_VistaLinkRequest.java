package gov.va.escreening.vista.request;

import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcResponse;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pouncilt on 4/17/14.
 */
public class TIU_DELETE_RECORD_VistaLinkRequest extends VistaLinkBaseRequest implements VistaLinkRequest {
    private static final Logger logger = LoggerFactory.getLogger(TIU_DELETE_RECORD_VistaLinkRequest.class);
    private VistaLinkConnection connection = null;
    private RpcRequest request = null;
    private Long progressNoteIEN = null;

    public TIU_DELETE_RECORD_VistaLinkRequest(VistaLinkRequestContext<TIU_DELETE_RECORD_RequestParameters> requestContext) {
        this.request = requestContext.getRpcRequest();
        this.connection = requestContext.getVistaLinkConnection();
        this.progressNoteIEN = requestContext.getRequestParameters().getProgressNoteIEN();
    }

    @Override
    protected String[] parseRpcResponse(RpcResponse response) throws Exception {
        String [] parsedRpcResponse = super.parseRpcSimpleResponseWithCarrotDelimiter(response);
        logger.debug("Delete Progress Note Response: " + response.getResults());
        return parsedRpcResponse;
    }

    @Override
    public Boolean sendRequest() throws VistaLinkRequestException {
        Boolean progressNoteDeletedSuccessfully = false;

        try {
            progressNoteDeletedSuccessfully = this.deleteProgressNote();
        } catch (Exception e) {
            throw new VistaLinkRequestException(e);
        }

        return progressNoteDeletedSuccessfully;
    }

    private Boolean deleteProgressNote() throws Exception {
        Boolean progressNoteDeleted = false;
        List<String> requestParams = new ArrayList<String>();
        requestParams.add(progressNoteIEN.toString()); // IEN of progress note. Required
        logger.debug("Delete Progress Note IEN: " + progressNoteIEN);

        request.setRpcName("TIU DELETE RECORD");
        request.clearParams();
        request.setParams(requestParams);

        RpcResponse response = connection.executeRPC(request);

        try {
            String[] responseStatus = parseRpcResponse(response);
            if(responseStatus[0].equals("0")){
                progressNoteDeleted = true;
            } else {
                throw new VistaLinkRequestException(responseStatus[1]);
            }
        } catch (VistaLinkNoResponseException vlnre) {
            throw new VistaLinkResponseException("Expected a response; but found no response." , requestParams, response.getResults(), vlnre);
        }

        return progressNoteDeleted;
    }

    @Override
    public void store() throws VistaLinkRequestException {

    }

    @Override
    public void load() throws VistaLinkRequestException {

    }
}
