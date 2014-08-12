package gov.va.escreening.vista.request;

import gov.va.escreening.vista.VistaUtils;
import gov.va.escreening.vista.dto.VistaDateFormat;
import gov.va.escreening.vista.dto.VistaProgressNote;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.adapter.record.VistaLinkFaultException;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pouncilt on 4/16/14.
 */
public class TIU_CREATE_RECORD_VistaLinkRequest extends VistaLinkBaseRequest implements VistaLinkRequest {
    private static final Logger logger = LoggerFactory.getLogger(TIU_CREATE_RECORD_VistaLinkRequest.class);
    private VistaLinkConnection connection = null;
    private RpcRequest request = null;
    private Long patientIEN = null;  // Required to create Progress Note.
    private Long titleIEN = null;   // Required to create Progress Note.
    private Date vistaVisitDateTime = null; // Optional when creating Progress Note.
    private Long locationIEN = null;  // Optional when creating Progress Note.
    private Long visitIEN = null; // Optional when creating Progress Note.
    private Object[] identifiers = null;
    private String visitString = null; // Required to create Progress Note.
    boolean suppressCommitPostLogic = false; // Required to create Progress Note.
    boolean saveTelnetCrossReference = false; // Required to create Progress Note.

    public TIU_CREATE_RECORD_VistaLinkRequest(VistaLinkRequestContext<TIU_CREATE_RECORD_RequestParameters> requestContext) {
        this.request = requestContext.getRpcRequest();
        this.connection = requestContext.getVistaLinkConnection();
        this.patientIEN = requestContext.getRequestParameters().getPatientIEN();  // Required to create Progress Note.
        this.titleIEN = requestContext.getRequestParameters().getTitleIEN();   // Required to create Progress Note.
        this.vistaVisitDateTime = requestContext.getRequestParameters().getVistaVisitDateTime(); // Optional when creating Progress Note.
        this.locationIEN = requestContext.getRequestParameters().getLocationIEN();  // Optional when creating Progress Note.
        this.visitIEN = requestContext.getRequestParameters().getVisitIEN(); // Optional when creating Progress Note.
        this.identifiers = requestContext.getRequestParameters().getIdentifiers();
        this.visitString = requestContext.getRequestParameters().getVisitString(); // Required to create Progress Note.
        Boolean suppressCommitFlag = requestContext.getRequestParameters().isSuppressCommitPostLogic();
        this.suppressCommitPostLogic = (suppressCommitFlag != null) ? suppressCommitFlag : false; // Required to create Progress Note.
        Boolean telnetXRefFlag = requestContext.getRequestParameters().isSaveTelnetCrossReference();
        this.saveTelnetCrossReference = (telnetXRefFlag != null) ? telnetXRefFlag : false; // Required to create Progress Note.

    }

    @Override
    protected String[] parseRpcResponse(RpcResponse response) throws Exception {
        return super.parseRpcSimpleResponseWithCarrotDelimiter(response);
    }

    @Override
    public VistaProgressNote sendRequest() throws VistaLinkRequestException {
        VistaProgressNote progressNote = null;
        try {
            progressNote = this.createProgressNote();
        } catch (Exception e) {
            throw new VistaLinkRequestException(e);
        }
        return progressNote;
    }

    private VistaProgressNote createProgressNote() throws Exception{
        if(identifiers.length >= 2 && identifiers[1] != null) {
            identifiers[1] = VistaUtils.convertToVistaDateString((Date) identifiers[1], VistaDateFormat.MMddHHmmss);
        }

        HashMap<String, Object> textHashMap = new HashMap<String, Object>();
        textHashMap.put(RpcRequest.buildMultipleMSubscriptKey("\"1202\""), this.identifiers[0]);
        textHashMap.put(RpcRequest.buildMultipleMSubscriptKey("\"1301\""), this.identifiers[1]);
        textHashMap.put(RpcRequest.buildMultipleMSubscriptKey("\"1205\""), this.identifiers[2]);
        //textHashMap.put(RpcRequest.buildMultipleMSubscriptKey("\"1701\""), this.identifiers[3]);


        VistaProgressNote progressNote = null;
        List<Object> requestParams = new ArrayList<Object>();
        requestParams.add(this.patientIEN); // IEN of patient Required
        requestParams.add(this.titleIEN);   // IEN of title Required
        requestParams.add((this.vistaVisitDateTime != null)?VistaUtils.convertToVistaDateString(this.vistaVisitDateTime, VistaDateFormat.MMddHHmmss): "");  // Optional
        requestParams.add(this.locationIEN); // Optional
        requestParams.add((this.visitIEN != null)? this.visitIEN : ""); // Optional IEN of Visit
        requestParams.add(this.identifiers); // Required  //requestParams.add(textHashMap);
        requestParams.add((this.visitString != null)? this.visitString: ""); // Optional
        requestParams.add((this.suppressCommitPostLogic)? "1": "0");  // Optional
        requestParams.add((this.saveTelnetCrossReference)? "1": "0"); // Optional

        request.setRpcName("TIU CREATE RECORD");
        request.clearParams();
        request.setParams(requestParams);

        RpcResponse response = connection.executeRPC(request);
        logger.debug("Create Progress Note Response: " + response.getResults());

        try {
            String[] responseArray = parseRpcResponse(response);
            if(responseArray.length == 1) {
                progressNote = new VistaProgressNote(Long.parseLong(responseArray[0].trim()));
                logger.debug("Create Progress Note IEN: " + progressNote.getIEN());
            } else if(responseArray.length == 2) {
                throw new VistaLinkFaultException(responseArray[1]);
            } else {
                throw new VistaLinkFaultException("Create Progress Note response did not return a valid documented response.");
            }
        } catch (VistaLinkNoResponseException vlnre) {
            throw new VistaLinkResponseException("Expected a response; but found no response." , requestParams, response.getResults(), vlnre);
        }
        return progressNote;
    }

    @Override
    public void store() throws VistaLinkRequestException {

    }

    @Override
    public void load() throws VistaLinkRequestException {

    }
}
