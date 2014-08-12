package gov.va.escreening.vista.request;

import gov.va.escreening.vista.dto.ConsultationLocation;
import gov.va.escreening.vista.dto.ConsultationUrgency;
import gov.va.escreening.vista.dto.ConsultationUrgencyDataSet;
import gov.va.escreening.vista.dto.ConsultationUrgencyInfo;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcResponse;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pouncilt on 6/11/14.
 */
public class ORWDCN32_DEF_VistaLinkRequest extends VistaLinkBaseRequest implements VistaLinkRequest {
    private static final Logger logger = LoggerFactory.getLogger(ORWDCN32_DEF_VistaLinkRequest.class);
    private VistaLinkConnection connection = null;
    private RpcRequest request = null;
    private String orderType = null; // C for Consult or P for procedure

    public ORWDCN32_DEF_VistaLinkRequest(VistaLinkRequestContext<ORWDCN32_DEF_RequestParameters> requestContext) {
        this.request = requestContext.getRpcRequest();
        this.connection = requestContext.getVistaLinkConnection();
        this.orderType = requestContext.getRequestParameters().getOrderType();
    }

    @Override
    protected ConsultationUrgencyDataSet[] parseRpcResponse(RpcResponse response) throws Exception {
        String[] parsedRpcResponse = super.parseRpcSimpleResponseWithNewLineDelimiter(response);
        List<ConsultationUrgencyInfo> consultationUrgencyInfoList = new ArrayList<ConsultationUrgencyInfo>();

        ConsultationUrgencyInfo consultationUrgencyInfo = null;
        boolean processingConsultationUrgency = false;
        boolean processingConsultationLocation = false;
        String[] parsedRpcResponseLine = null;
        String header = null;
        String urgencyIEN = null;
        String urgencyName = null;
        Long protocolIEN = null;
        String choicePrefix = null;
        String locationName = null;
        String locationPrefix = null;

        for(int i = 0; i < parsedRpcResponse.length; i++) {
            if(isParsedRpcResponseLineAConsultationUrgencyHeader(parsedRpcResponse[i])) {
                header = parsedRpcResponse[i];
                if(isParsedRpcResponseLineAConsultationUrgency(parsedRpcResponse[i])) {
                    processingConsultationUrgency = true;
                    processingConsultationLocation = false;
                    continue;
                } else if(isParsedRpcResponseLineAConsultationLocation(parsedRpcResponse[i])) {
                    processingConsultationUrgency = false;
                    processingConsultationLocation = true;
                    continue;
                } else if(parsedRpcResponse[i].equalsIgnoreCase("~ShortList")) {
                    header = null;
                    continue;
                } else {
                    throw new VistaLinkResponseException("Could not determine Consultation header.");
                }
            } else {
                parsedRpcResponseLine = super.parseRpcResponseLineWithCarrotDelimiter(parsedRpcResponse[i].getBytes());
                if(processingConsultationUrgency) {
                    urgencyIEN = parsedRpcResponseLine[0];
                    urgencyName = parsedRpcResponseLine[1];
                    protocolIEN = (parsedRpcResponseLine.length > 2 && parsedRpcResponse[2] != null)? Long.parseLong(parsedRpcResponseLine[2]): null;
                    consultationUrgencyInfo = new ConsultationUrgency(header, urgencyIEN, urgencyName, protocolIEN);
                    consultationUrgencyInfoList.add(consultationUrgencyInfo);
                    urgencyIEN = null;
                    urgencyName = null;
                    protocolIEN = null;
                } else if (processingConsultationLocation) {
                    choicePrefix = parsedRpcResponseLine[0];
                    locationName = parsedRpcResponseLine[1];
                    locationPrefix = parsedRpcResponseLine[2];
                    consultationUrgencyInfo = new ConsultationLocation(header, choicePrefix, locationName, locationPrefix);
                    consultationUrgencyInfoList.add(consultationUrgencyInfo);
                    choicePrefix = null;
                    locationName = null;
                    locationPrefix = null;
                } else {
                    throw new VistaLinkResponseException("Could not determine if response line is consultation urgency or location.");
                }
                parsedRpcResponseLine = null;
            }
        }

        ConsultationUrgencyDataSet consultationUrgencyDataSet = new ConsultationUrgencyDataSet(consultationUrgencyInfoList);
        ConsultationUrgencyDataSet[] consultationUrgencyDataSets = {consultationUrgencyDataSet};
        return consultationUrgencyDataSets;
    }

    private Boolean isInpatientConsultationInfo(String rpcResponseLine) {
        return rpcResponseLine.startsWith("~") && rpcResponseLine.toLowerCase().contains("inpt");
    }

    private boolean isParsedRpcResponseLineAConsultationLocation(String rpcResponseLine) {
        return rpcResponseLine.startsWith("~") && rpcResponseLine.toLowerCase().contains("place");
    }

    private boolean isParsedRpcResponseLineAConsultationUrgency(String rpcResponseLine) {
        return rpcResponseLine.startsWith("~") && rpcResponseLine.toLowerCase().contains("urgencies");
    }

    private boolean isParsedRpcResponseLineAConsultationUrgencyHeader(String rpcResponseLine) {
        return rpcResponseLine.startsWith("~");
    }

    @Override
    public ConsultationUrgencyDataSet sendRequest() throws VistaLinkRequestException {
        ConsultationUrgencyDataSet consultationUrgencyDataSet = null;

        try {
            consultationUrgencyDataSet = this.getConsultationUrgencies();
        } catch (Exception e) {
            throw new VistaLinkRequestException(e);
        }

        return consultationUrgencyDataSet;
    }

    public ConsultationUrgencyDataSet getConsultationUrgencies() throws Exception{
        ConsultationUrgencyDataSet consultationUrgencyDataSet = null;

        List<Object> requestParams = new ArrayList<Object>();
        requestParams.add(this.orderType);

        request.setRpcName("ORWDCN32 DEF");
        request.clearParams();
        request.setParams(requestParams);

        RpcResponse response = connection.executeRPC(request);

        try {
            consultationUrgencyDataSet = parseRpcResponse(response)[0];
        } catch (VistaLinkNoResponseException vlnre) {
            throw new VistaLinkResponseException("Expected a response; but found no response." , requestParams, response.getResults(), vlnre);
        } catch(VistaLinkResponseException vlre) {
            throw new VistaLinkResponseException("Expected a response; but found no response." , requestParams, response.getResults(), vlre);
        }

        return consultationUrgencyDataSet;
    }

    @Override
    public void store() throws VistaLinkRequestException {

    }

    @Override
    public void load() throws VistaLinkRequestException {

    }
}
