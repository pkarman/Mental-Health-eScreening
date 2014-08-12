package gov.va.escreening.vista.request;

import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.adapter.record.VistaLinkFaultException;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pouncilt on 4/16/14.
 */
public class TIU_SET_DOCUMENT_TEXT_VistaLinkRequest extends VistaLinkBaseRequest implements VistaLinkRequest {
    private static final Logger logger = LoggerFactory.getLogger(TIU_SET_DOCUMENT_TEXT_VistaLinkRequest.class);
    private VistaLinkConnection connection = null;
    private RpcRequest request = null;
    private Long progressNoteIEN = null;
    private String[] progressNoteBoilerPlateText = null;
    private String progressNoteContent = null;
    private boolean progressNoteAppendContent = true;
    private boolean suppressEditBuffer = false;


    public TIU_SET_DOCUMENT_TEXT_VistaLinkRequest(VistaLinkRequestContext<TIU_SET_DOCUMENT_TEXT_RequestParameters> requestContext) {
        this.request = requestContext.getRpcRequest();
        this.connection = requestContext.getVistaLinkConnection();
        this.progressNoteIEN = requestContext.getRequestParameters().getProgressNoteIEN();
        this.progressNoteBoilerPlateText = requestContext.getRequestParameters().getProgressNoteBoilerPlateText();
        this.progressNoteContent = requestContext.getRequestParameters().getProgressNoteContent(); // Required to create Progress Note.
        Boolean appendProgressNoteContent = requestContext.getRequestParameters().isProgressNoteAppendContent();
        this.progressNoteAppendContent = (appendProgressNoteContent == null) ? true : appendProgressNoteContent;   // Required to create Progress Note.
        Boolean suppressEditBuffer = requestContext.getRequestParameters().isSuppressEditBuffer();
        this.suppressEditBuffer = (suppressEditBuffer == null)? false: suppressEditBuffer;
    }

    @Override
    protected String[] parseRpcResponse(RpcResponse response) throws Exception {
        return super.parseRpcSimpleResponseWithCarrotDelimiter(response);
    }

    @Override
    public Boolean sendRequest() throws VistaLinkRequestException {
        Boolean updateSuccessful = false;
        try {
            updateSuccessful = this.updateProgressNote();
        } catch (Exception e) {
            throw new VistaLinkRequestException(e);
        }
        return updateSuccessful;
    }

    private Boolean updateProgressNote() throws Exception {
        Boolean updateSuccessful = false;
        HashMap<String, String> textHashMap = new HashMap<String, String>();
        int hashMapIndex = 1;
        if (this.progressNoteAppendContent) {
            for(String boilerPlateText: Arrays.asList(this.progressNoteBoilerPlateText)) {
                if(boilerPlateText != null && boilerPlateText.length() > 0) {
                    textHashMap.put(RpcRequest.buildMultipleMSubscriptKey("\"TEXT\"," + (hashMapIndex++) + ",0"), boilerPlateText);
                }
            }

            // One day we may need to template boilerplate test by implement 3c, 3c1, and 3c2.
            // Currently 3c, 3c1, and 3c2 are out of scope right now.
        }

        textHashMap.put(RpcRequest.buildMultipleMSubscriptKey("\"TEXT\"," + (hashMapIndex++) + ",0"), this.progressNoteContent);

        byte delimiterByte = 94;
        byte byte1 = 49;
        byte[] pageInfo = {
                byte1,
                delimiterByte,
                byte1
        };
        String pageInfoKey = "\"HDR\"";
        String pageInfoKeyValue = new String(pageInfo);

        textHashMap.put(RpcRequest.buildMultipleMSubscriptKey(pageInfoKey), pageInfoKeyValue);

        List<Object> requestParams = new ArrayList<Object>();
        requestParams.add(this.progressNoteIEN); // IEN of progressNote -  Required
        requestParams.add(textHashMap); // IEN of patient Required
        requestParams.add((this.suppressEditBuffer)? "1": "0"); // Required

        request.setRpcName("TIU SET DOCUMENT TEXT");
        request.clearParams();
        request.setParams(requestParams);

        RpcResponse response = connection.executeRPC(request);
        logger.debug("Set Progress Note Text: " + response.getResults());


        try {
            String[] responseArray = parseRpcResponse(response);
            if(responseArray.length == 3) {
                updateSuccessful = true;
            } else if(responseArray.length == 4) {
                throw new VistaLinkFaultException(responseArray[3]);
            } else {
                throw new VistaLinkFaultException("Create Progress Note response did not return a valid documented response.");
            }
        } catch (VistaLinkNoResponseException vlnre) {
            throw new VistaLinkResponseException("Expected a response; but found no response." , requestParams, response.getResults(), vlnre);
        }

        return updateSuccessful;
    }

    @Override
    public void store() throws VistaLinkRequestException {

    }

    @Override
    public void load() throws VistaLinkRequestException {

    }
}
