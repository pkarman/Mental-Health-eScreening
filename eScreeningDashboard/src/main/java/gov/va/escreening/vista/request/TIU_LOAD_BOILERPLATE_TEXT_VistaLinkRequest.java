package gov.va.escreening.vista.request;

import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcResponse;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pouncilt on 4/16/14.
 */
public class TIU_LOAD_BOILERPLATE_TEXT_VistaLinkRequest extends VistaLinkBaseRequest implements VistaLinkRequest {
    private static final Logger logger = LoggerFactory.getLogger(TIU_LOAD_BOILERPLATE_TEXT_VistaLinkRequest.class);
    private VistaLinkConnection connection = null;
    private RpcRequest request = null;
    private Long patientIEN = null;  // Required to create Progress Note.
    private Long titleIEN = null;   // Required to create Progress Note.
    private String visitString = null; // Required to create Progress Note.

    public TIU_LOAD_BOILERPLATE_TEXT_VistaLinkRequest(VistaLinkRequestContext<TIU_LOAD_BOILERPLATE_TEXT_RequestParameters> requestContext) {
        this.request = requestContext.getRpcRequest();
        this.connection = requestContext.getVistaLinkConnection();
        this.patientIEN = requestContext.getRequestParameters().getPatientIEN();  // Required to create Progress Note.
        this.titleIEN = requestContext.getRequestParameters().getTitleIEN();   // Required to create Progress Note.
        this.visitString = requestContext.getRequestParameters().getVisitString(); // Required to create Progress Note.
    }

    @Override
    protected String[] parseRpcResponse(RpcResponse response) throws Exception {
        return parseRpcSimpleResponseWithNewLineDelimiter(response);
    }

    @Override
    public String[] sendRequest() throws VistaLinkRequestException {
        String[] boilerPlateText = null;
        try {
            boilerPlateText = this.getProgressNoteBoilerPlateText();
        } catch (Exception e) {
            throw new VistaLinkRequestException(e);
        }
        return boilerPlateText;
    }

    private String[] getProgressNoteBoilerPlateText() throws Exception{
        List<String> requestParams = new ArrayList<String>();
        requestParams.add(this.titleIEN.toString());
        requestParams.add(this.patientIEN.toString());
        requestParams.add(this.visitString);

        request.setRpcName("TIU LOAD BOILERPLATE TEXT");
        request.clearParams();
        request.setParams(requestParams);

        RpcResponse response = connection.executeRPC(request);
        logger.debug("Load Boilerplate Text for Progress Note: " + response.getResults());

        String[] existingBoilerplateText = new String[0];

        try {
            existingBoilerplateText = parseRpcResponse(response);
        } catch (VistaLinkNoResponseException vlnre) {
            new VistaLinkResponseException("Expected a response; but found no response." , requestParams, response.getResults(), VistaLinkRequestException.LogTypes.WARNING, vlnre);
            // Do nothing because this method may not find nothing.
            // we create an exception because we want to log warning.
        }

        return existingBoilerplateText;
    }

    @Override
    public void store() throws VistaLinkRequestException {

    }

    @Override
    public void load() throws VistaLinkRequestException {

    }
}
