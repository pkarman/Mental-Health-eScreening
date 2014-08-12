package gov.va.escreening.vista.request;

import gov.va.escreening.vista.VistaUtils;
import gov.va.escreening.vista.dto.VistaDateFormat;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pouncilt on 5/30/14.
 */
public class ORQQPXRM_MENTAL_HEALTH_SAVE_VistaLinkRequest extends VistaLinkBaseRequest implements VistaLinkRequest {
    private static final Logger logger = LoggerFactory.getLogger(ORQQPXRM_MENTAL_HEALTH_SAVE_VistaLinkRequest.class);
    private VistaLinkConnection connection = null;
    private RpcRequest request = null;
    private Long patientIEN = null;
    private String mentalHealthTestName = null;
    private Date date = null;
    private String staffCode = null;
    private String mentalHealthTestAnswers = null;

    public ORQQPXRM_MENTAL_HEALTH_SAVE_VistaLinkRequest(VistaLinkRequestContext<ORQQPXRM_MENTAL_HEALTH_SAVE_RequestParameters> requestContext) {
        this.request = requestContext.getRpcRequest();
        this.connection = requestContext.getVistaLinkConnection();
        this.patientIEN = requestContext.getRequestParameters().getPatientIEN();
        this.mentalHealthTestName = requestContext.getRequestParameters().getMentalHealthTestName();
        this.date = requestContext.getRequestParameters().getDate();
        this.staffCode = requestContext.getRequestParameters().getStaffCode();
        this.mentalHealthTestAnswers = requestContext.getRequestParameters().getMentalHealthTestAnswers();
    }

    @Override
    protected String[] parseRpcResponse(RpcResponse response) throws Exception {
        return super.parseRpcSimpleResponseWithCarrotDelimiter(response);
    }

    @Override
    public Boolean sendRequest() throws VistaLinkRequestException {
        Boolean mentalHealthAssessmentSaved = false;

        try {
            mentalHealthAssessmentSaved = this.saveMentalHealthAssessmentResults();
        } catch (Exception e) {
            throw new VistaLinkRequestException(e);
        }

        return mentalHealthAssessmentSaved;
    }

    private Boolean saveMentalHealthAssessmentResults() throws Exception{
        Boolean mentalHealthAssessmentSaved = false;
        HashMap<String, String> textHashMap = new HashMap<String, String>();
        textHashMap.put(RpcRequest.buildMultipleMSubscriptKey("\"DFN\""), this.patientIEN.toString());
        textHashMap.put(RpcRequest.buildMultipleMSubscriptKey("\"CODE\""), this.mentalHealthTestName);
        textHashMap.put(RpcRequest.buildMultipleMSubscriptKey("\"ADATE\""), VistaUtils.convertToVistaDateString(this.date, VistaDateFormat.MMdd));
        textHashMap.put(RpcRequest.buildMultipleMSubscriptKey("\"STAFF\""), this.staffCode);
        textHashMap.put(RpcRequest.buildMultipleMSubscriptKey("\"R1\""), this.mentalHealthTestAnswers);

        List<Object> requestParams = new ArrayList<Object>();
        requestParams.add(textHashMap); // Array

        request.setRpcName("ORQQPXRM MENTAL HEALTH SAVE");
        request.clearParams();
        request.setParams(requestParams);

        RpcResponse response = connection.executeRPC(request);

        try {
            String[] responseArray = parseRpcResponse(response);
            throw new VistaLinkResponseException("Expected no response; but found a response.", requestParams, response.getResults());
        } catch (VistaLinkNoResponseException vlnre) {
            mentalHealthAssessmentSaved = true;
        }

        return mentalHealthAssessmentSaved;
    }

    @Override
    public void store() throws VistaLinkRequestException {

    }

    @Override
    public void load() throws VistaLinkRequestException {

    }
}
