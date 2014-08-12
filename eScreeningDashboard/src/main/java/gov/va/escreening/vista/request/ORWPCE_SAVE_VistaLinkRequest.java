package gov.va.escreening.vista.request;

import gov.va.escreening.vista.dto.HealthFactor;
import gov.va.escreening.vista.dto.HealthFactorHeader;
import gov.va.escreening.vista.dto.HealthFactorImmunization;
import gov.va.escreening.vista.dto.HealthFactorProvider;
import gov.va.escreening.vista.dto.HealthFactorVisitData;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcResponse;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pouncilt on 5/5/14.
 */
public class ORWPCE_SAVE_VistaLinkRequest extends VistaLinkBaseRequest implements VistaLinkRequest {
    private static final Logger logger = LoggerFactory.getLogger(ORWPCE_SAVE_VistaLinkRequest.class);
    private VistaLinkConnection connection = null;
    private RpcRequest request = null;
    private HealthFactorHeader healthFactorHeader = null;
    private Set<HealthFactorVisitData> healthFactorVisitDataList = new LinkedHashSet<HealthFactorVisitData>();
    private HealthFactorProvider healthFactorProvider = null;
    //TODO: Re-factor to include Immunizations and Health Factors in one collection as they both use the same base sequence number.
    private Set<HealthFactorImmunization> healthFactorImmunizations = new LinkedHashSet<HealthFactorImmunization>();
    private Set<HealthFactor> healthFactors = new LinkedHashSet<HealthFactor>();
    private Long noteIEN = null;
    private Long locationIEN = null;

    public ORWPCE_SAVE_VistaLinkRequest(VistaLinkRequestContext<ORWPCE_SAVE_RequestParameters> requestContext) {
        this.request = requestContext.getRpcRequest();
        this.connection = requestContext.getVistaLinkConnection();
        this.healthFactorHeader = requestContext.getRequestParameters().getHealthFactorHeader();
        this.healthFactorVisitDataList = requestContext.getRequestParameters().getHealthFactorVisitDataSet();
        this.healthFactorProvider = requestContext.getRequestParameters().getHealthFactorProvider();
        this.healthFactorImmunizations = requestContext.getRequestParameters().getHealthFactorImmunizations();
        this.healthFactors = requestContext.getRequestParameters().getHealthFactors();
        this.noteIEN = requestContext.getRequestParameters().getNoteIEN();
        this.locationIEN = requestContext.getRequestParameters().getLocationIEN();
    }
    @Override
    protected String[] parseRpcResponse(RpcResponse response) throws Exception {
        return super.parseRpcSimpleResponseWithNoDelimiter(response);
    }

    @Override
    public Boolean sendRequest() throws VistaLinkRequestException {
        Boolean requestSentSuccessfully = false;
        try {
            requestSentSuccessfully = this.saveHealthFactors();
        } catch (Exception e) {
            throw new VistaLinkRequestException(e);
        }

        return requestSentSuccessfully;
    }

    public static Logger getLogger() {
        return logger;
    }

    private Boolean saveHealthFactors() throws Exception{
        Boolean healthFactorsSaved = false;
        List<String> visitData = new ArrayList<String>();
        visitData.add(this.healthFactorHeader.toString());

        for(HealthFactorVisitData healthFactorVisitData: this.healthFactorVisitDataList){
            visitData.add(healthFactorVisitData.toString());
        }

        visitData.add(this.healthFactorProvider.toString());

        //TODO: Re-factor to include Immunizations and Health Factors in one collection as they both use the same base sequence number.
        for(HealthFactor healthFactor: this.healthFactors) {
            visitData.add(healthFactor.toString());
            visitData.add(healthFactor.getHealthFactorComment().toString());
        }

        List<Object> requestParams = new ArrayList<Object>();
        requestParams.add(visitData); // Initial Service Connection Category.
        requestParams.add(this.noteIEN); // note IEN
        requestParams.add(this.locationIEN); // location IEN

        request.setRpcName("ORWPCE SAVE");
        request.clearParams();
        request.setParams(requestParams);

        RpcResponse response = connection.executeRPC(request);

        try {
            String[] responseArray = parseRpcResponse(response);
            throw new VistaLinkResponseException("Expected no response; but found an unexpected response." , requestParams, response.getResults());
        } catch (VistaLinkNoResponseException vlnre) {
            healthFactorsSaved = true;
        }

        return healthFactorsSaved;
    }

    @Override
    public void store() throws VistaLinkRequestException {

    }

    @Override
    public void load() throws VistaLinkRequestException {

    }
}
