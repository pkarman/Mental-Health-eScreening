package gov.va.escreening.vista.request;

import gov.va.escreening.vista.dto.MentalHealthAssessmentResult;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pouncilt on 5/17/14.
 */
public class ORQQPXRM_MENTAL_HEALTH_RESULTS_VistaLinkRequest extends VistaLinkBaseRequest implements VistaLinkRequest {
    private static final Logger logger = LoggerFactory.getLogger(ORQQPXRM_MENTAL_HEALTH_RESULTS_VistaLinkRequest.class);
    private VistaLinkConnection connection = null;
    private RpcRequest request = null;
    private Long reminderDialogIEN = null;
    private Long patientIEN = null;
    private String mentalHealthTestName = null;
    private String dateCode = null;
    private String staffCode = null;
    private String mentalHealthTestAnswers = null;

    public ORQQPXRM_MENTAL_HEALTH_RESULTS_VistaLinkRequest(VistaLinkRequestContext<ORQQPXRM_MENTAL_HEALTH_RESULTS_RequestParameters> requestContext) {
        this.request = requestContext.getRpcRequest();
        this.connection = requestContext.getVistaLinkConnection();
        this.reminderDialogIEN = requestContext.getRequestParameters().getReminderDialogIEN();
        this.patientIEN = requestContext.getRequestParameters().getPatientIEN();
        this.mentalHealthTestName = requestContext.getRequestParameters().getMentalHealthTestName();
        this.dateCode = requestContext.getRequestParameters().getDateCode();
        this.staffCode = requestContext.getRequestParameters().getStaffCode();
        this.mentalHealthTestAnswers = requestContext.getRequestParameters().getMentalHealthTestAnswers();
    }

    @Override
    protected String[] parseRpcResponse(RpcResponse response) throws Exception {
        return super.parseRpcSimpleResponseWithCarrotDelimiter(response);
    }

    @Override
    public MentalHealthAssessmentResult sendRequest() throws VistaLinkRequestException {
        MentalHealthAssessmentResult mentalHealthAssessmentResult = null;

        try {
            String mentalHealthAssessmentResultDescription = this.getMentalHealthAssessmentResults();
            mentalHealthAssessmentResult = new MentalHealthAssessmentResult(reminderDialogIEN, patientIEN, mentalHealthTestName, dateCode, staffCode, mentalHealthTestAnswers, mentalHealthAssessmentResultDescription);
        } catch (Exception e) {
            throw new VistaLinkRequestException(e);
        }

        return mentalHealthAssessmentResult;
    }

    private String getMentalHealthAssessmentResults() throws Exception{
        String mentalHealthAssessmentResultDescription = null;
        HashMap<String, String> textHashMap = new HashMap<String, String>();
        textHashMap.put(RpcRequest.buildMultipleMSubscriptKey("\"DFN\""), this.patientIEN.toString());
        textHashMap.put(RpcRequest.buildMultipleMSubscriptKey("\"CODE\""), this.mentalHealthTestName);
        textHashMap.put(RpcRequest.buildMultipleMSubscriptKey("\"ADATE\""), this.dateCode);
        textHashMap.put(RpcRequest.buildMultipleMSubscriptKey("\"STAFF\""), this.staffCode);
        textHashMap.put(RpcRequest.buildMultipleMSubscriptKey("\"R1\""), this.mentalHealthTestAnswers);

        List<Object> requestParams = new ArrayList<Object>();
        requestParams.add(this.reminderDialogIEN); // Reminder Dialog IEN
        requestParams.add(textHashMap); // Array

        request.setRpcName("ORQQPXRM MENTAL HEALTH RESULTS");
        request.clearParams();
        request.setParams(requestParams);

        RpcResponse response = connection.executeRPC(request);

        try {
            String[] responseArray = parseRpcResponse(response);
            mentalHealthAssessmentResultDescription = responseArray[1];
        } catch (VistaLinkNoResponseException vlnre) {
            throw new VistaLinkResponseException("Expected a response; but found no response." , requestParams, response.getResults(), vlnre);
        }

        return mentalHealthAssessmentResultDescription;
    }

    @Override
    public void store() throws VistaLinkRequestException {

    }

    @Override
    public void load() throws VistaLinkRequestException {

    }
}
