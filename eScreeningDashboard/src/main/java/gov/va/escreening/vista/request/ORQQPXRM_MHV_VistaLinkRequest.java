package gov.va.escreening.vista.request;

import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcResponse;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pouncilt on 5/17/14.
 */
public class ORQQPXRM_MHV_VistaLinkRequest extends VistaLinkBaseRequest implements VistaLinkRequest {
    private static final Logger logger = LoggerFactory.getLogger(ORQQPXRM_MHV_VistaLinkRequest.class);
    private VistaLinkConnection connection = null;
    private RpcRequest request = null;
    private Long patientIEN = null;
    private String mentalHealthTestName = null;
    private String mentalHealthTestAnswers = null;

    public ORQQPXRM_MHV_VistaLinkRequest(VistaLinkRequestContext<ORQQPXRM_MHV_RequestParameters> requestContext) {
        this.request = requestContext.getRpcRequest();
        this.connection = requestContext.getVistaLinkConnection();
        this.patientIEN = requestContext.getRequestParameters().getPatientIEN();
        this.mentalHealthTestName = requestContext.getRequestParameters().getMentalHealthTestName();
        this.mentalHealthTestAnswers = requestContext.getRequestParameters().getMentalHealthTestAnswers();
    }

    @Override
    protected String[] parseRpcResponse(RpcResponse response) throws Exception {
        return super.parseRpcSimpleResponseWithNoDelimiter(response);
    }

    @Override
    public Boolean sendRequest() throws VistaLinkRequestException {
        Boolean mentalHealthAssessmentSaved = false;
        try {
            mentalHealthAssessmentSaved = this.saveMentalHealthAssessment();
        } catch (Exception e) {
            throw new VistaLinkRequestException(e);
        }

        return mentalHealthAssessmentSaved;
    }

    private Boolean saveMentalHealthAssessment() throws Exception{
        Boolean mentalHealthAssessmentSaved = false;
        List<String> requestParams = new ArrayList<String>();
        requestParams.add(this.patientIEN.toString()); // Patient IEN
        requestParams.add(this.mentalHealthTestName); // Mental Health Test Name
        requestParams.add(this.mentalHealthTestAnswers); // Mental Health Test Answers

        request.setRpcName("ORQQPXRM MHV");
        request.clearParams();
        request.setParams(requestParams);

        RpcResponse response = connection.executeRPC(request);

        try {
            String[] responseArray = parseRpcResponse(response);
            mentalHealthAssessmentSaved = (responseArray[0].equals("1"))? true : false;
            if(!mentalHealthAssessmentSaved)
            {
                StringBuilder logError = new StringBuilder("save MHA failed. Params:");
                int i=0;
                for(String s: requestParams)
                {
                    logError.append(i++ +":" + s + ",");
                }
                logError.append("\n     Response: ");
                i=0;
                for(String s: responseArray)
                {
                    logError.append(i++ +":" + s + ",");
                }
                logger.error(logError.toString());
            }
            
        } catch (VistaLinkNoResponseException vlnre) {
            throw new VistaLinkResponseException("Expected a response; but found no response." , requestParams, response.getResults(), vlnre);
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
