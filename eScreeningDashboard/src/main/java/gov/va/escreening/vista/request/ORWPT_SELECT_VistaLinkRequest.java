package gov.va.escreening.vista.request;

import gov.va.escreening.vista.VistaUtils;
import gov.va.escreening.vista.dto.PatientDemographics;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcResponse;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pouncilt on 5/29/14.
 */
public class ORWPT_SELECT_VistaLinkRequest extends VistaLinkBaseRequest implements VistaLinkRequest {
    private static final Logger logger = LoggerFactory.getLogger(ORWPT_SELECT_VistaLinkRequest.class);
    private VistaLinkConnection connection = null;
    private RpcRequest request = null;
    private Long patientIEN = null;

    public ORWPT_SELECT_VistaLinkRequest(VistaLinkRequestContext<ORWPT_SELECT_RequestParameters> requestContext){
        this.request = requestContext.getRpcRequest();
        this.connection = requestContext.getVistaLinkConnection();
        this.patientIEN = requestContext.getRequestParameters().getPatientIEN();
    }

    @Override
    protected String[] parseRpcResponse(RpcResponse response) throws Exception {
        return super.parseRpcSimpleResponseWithCarrotDelimiter(response);
    }

    @Override
    public PatientDemographics sendRequest() throws VistaLinkRequestException {
        PatientDemographics patientDemographics = null;

        try {
            patientDemographics = this.findPatientDemographics();
        } catch (Exception e) {
            throw new VistaLinkRequestException(e);
        }
        return patientDemographics;
    }

    private PatientDemographics findPatientDemographics() throws Exception {
        PatientDemographics patientDemographics = null;
        List<String> requestParams = new ArrayList<String>();
        requestParams.add(this.patientIEN.toString()); // patient IEN.

        request.setRpcName("ORWPT SELECT");
        request.clearParams();
        request.setParams(requestParams);

        RpcResponse response = connection.executeRPC(request);

        try {
            String[] parsedRpcResponse= parseRpcResponse(response);

            Boolean patientIsSensitive = (parsedRpcResponse[8] == null || parsedRpcResponse[8].equalsIgnoreCase("0") || parsedRpcResponse[8].equalsIgnoreCase("false"))?
                    false : (parsedRpcResponse[8].equalsIgnoreCase("1") || parsedRpcResponse[8].equalsIgnoreCase("true"))? true : null;
            Boolean serviceConnected = (parsedRpcResponse[11] == null || parsedRpcResponse[11].equalsIgnoreCase("0") || parsedRpcResponse[11].equalsIgnoreCase("false"))?
                    false : (parsedRpcResponse[11].equalsIgnoreCase("1") || parsedRpcResponse[11].equalsIgnoreCase("true"))? true : null;
            Boolean conv = (parsedRpcResponse[10] == null || parsedRpcResponse[10].equalsIgnoreCase("0") || parsedRpcResponse[10].equalsIgnoreCase("false"))?
                    false : (parsedRpcResponse[10].equalsIgnoreCase("1") || parsedRpcResponse[10].equalsIgnoreCase("true"))? true : null;

            patientDemographics = new PatientDemographics(parsedRpcResponse[0], parsedRpcResponse[1], parsedRpcResponse[2],
                    parsedRpcResponse[3], (parsedRpcResponse[4] != null)? Long.parseLong(parsedRpcResponse[4]): null,
                    parsedRpcResponse[5], parsedRpcResponse[6], parsedRpcResponse[7], patientIsSensitive,
                    (parsedRpcResponse[9] != null)? VistaUtils.convertVistaDate(parsedRpcResponse[9]) : null, conv,
                    serviceConnected, (parsedRpcResponse[12] != null)? Integer.parseInt(parsedRpcResponse[12]): null,
                    (parsedRpcResponse[13] != null)? Long.parseLong(parsedRpcResponse[13]): null,
                    (parsedRpcResponse[14] != null)? Integer.parseInt(parsedRpcResponse[14]): null,
                    (parsedRpcResponse[15] != null)? Integer.parseInt(parsedRpcResponse[15]): null);
        } catch (VistaLinkNoResponseException vlnre) {
            throw new VistaLinkResponseException("Expected a response; but found no response." , requestParams, response.getResults(), vlnre);
        }

        return patientDemographics;
    }

    @Override
    public void store() throws VistaLinkRequestException {

    }

    @Override
    public void load() throws VistaLinkRequestException {

    }
}
