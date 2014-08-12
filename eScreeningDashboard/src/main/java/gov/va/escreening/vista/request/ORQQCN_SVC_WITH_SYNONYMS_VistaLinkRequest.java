package gov.va.escreening.vista.request;

import gov.va.escreening.vista.dto.ConsultationServiceNameDataSet;
import gov.va.escreening.vista.dto.ConsultationServiceNameInfo;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcResponse;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pouncilt on 6/13/14.
 */
public class ORQQCN_SVC_WITH_SYNONYMS_VistaLinkRequest extends VistaLinkBaseRequest implements VistaLinkRequest {
    private static final Logger logger = LoggerFactory.getLogger(ORQQCN_SVC_WITH_SYNONYMS_VistaLinkRequest.class);
    private VistaLinkConnection connection = null;
    private RpcRequest request = null;
    private String startPositionSearchCriteria = null;
    private String purpose = null;
    private Boolean includeSynonyms = null;

    public ORQQCN_SVC_WITH_SYNONYMS_VistaLinkRequest(VistaLinkRequestContext<ORQQCN_SVC_WITH_SYNONYMS_RequestParameters> requestContext){
        this.request = requestContext.getRpcRequest();
        this.connection = requestContext.getVistaLinkConnection();
        this.startPositionSearchCriteria = requestContext.getRequestParameters().getStartPositionSearchCriteria();
        this.purpose = requestContext.getRequestParameters().getPurpose();
        this.includeSynonyms = requestContext.getRequestParameters().getIncludeSynonyms();
    }

    @Override
    protected ConsultationServiceNameDataSet[] parseRpcResponse(RpcResponse response) throws Exception {
        String[] parsedRpcResponseLines = super.parseRpcSimpleResponseWithNewLineDelimiter(response);
        List<ConsultationServiceNameInfo> consultationServiceNameInfoList = new ArrayList<ConsultationServiceNameInfo>();
        String[] parsedRpcResponse = null;
        Long ien;
        String name;
        Long parentIEN;
        Boolean hasChild;
        String usage;
        Long orderableItemIEN;

        for(int i = 0; i < parsedRpcResponseLines.length; i++) {
            parsedRpcResponse = super.parseRpcResponseLineWithCarrotDelimiter(parsedRpcResponseLines[i].getBytes());
            ien = Long.parseLong(parsedRpcResponse[0]);
            name = parsedRpcResponse[1];
            parentIEN = Long.parseLong(parsedRpcResponse[2]);
            hasChild = (parsedRpcResponse[3] != null && parsedRpcResponse[3].equals("+"))? true : false;
            usage = parsedRpcResponse[4];
            orderableItemIEN = Long.parseLong(parsedRpcResponse[5]);
            consultationServiceNameInfoList.add(new ConsultationServiceNameInfo(ien, name, parentIEN, hasChild, usage, orderableItemIEN));
        }

        ConsultationServiceNameDataSet[] consultationServiceNameDataSets = {new ConsultationServiceNameDataSet(consultationServiceNameInfoList)};
        return consultationServiceNameDataSets;
    }

    @Override
    public ConsultationServiceNameDataSet sendRequest() throws VistaLinkRequestException {
        ConsultationServiceNameDataSet consultationServiceNameDataSet = null;

        try {
            consultationServiceNameDataSet = this.findConsultationServiceNames();
        } catch (Exception e) {
            throw new VistaLinkRequestException(e);
        }

        return consultationServiceNameDataSet;
    }

    private ConsultationServiceNameDataSet findConsultationServiceNames() throws Exception {
        ConsultationServiceNameDataSet consultationServiceNameDataSet = null;

        List<Object> requestParams = new ArrayList<Object>();
        requestParams.add(this.startPositionSearchCriteria);
        requestParams.add(this.purpose);
        requestParams.add((this.includeSynonyms)? "1": "0");

        request.setRpcName("ORQQCN SVC W/SYNONYMS");
        request.clearParams();
        request.setParams(requestParams);

        RpcResponse response = connection.executeRPC(request);

        try {
            consultationServiceNameDataSet = parseRpcResponse(response)[0];
        } catch (VistaLinkNoResponseException vlnre) {
            throw new VistaLinkResponseException("Expected a response; but found no response." , requestParams, response.getResults(), vlnre);
        }

        return consultationServiceNameDataSet;
    }

    @Override
    public void store() throws VistaLinkRequestException {

    }

    @Override
    public void load() throws VistaLinkRequestException {

    }
}
