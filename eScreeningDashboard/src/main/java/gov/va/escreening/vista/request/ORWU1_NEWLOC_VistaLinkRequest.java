package gov.va.escreening.vista.request;

import gov.va.escreening.vista.dto.VistaLocation;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pouncilt on 4/9/14.
 */
public class ORWU1_NEWLOC_VistaLinkRequest extends VistaLinkBaseRequest implements VistaLinkRequest {
    private static final Logger logger = LoggerFactory.getLogger(ORWU1_NEWLOC_VistaLinkRequest.class);
    private VistaLinkConnection connection = null;
    private RpcRequest request = null;
    private String locationNameSearchCriteria = null;
    private String startingNameSearchCriteria = null;
    private boolean sortDirectionFilterSearchCriteria = false;


    public ORWU1_NEWLOC_VistaLinkRequest(VistaLinkRequestContext<ORWU1_NEWLOC_RequestParameters> requestContext) {
        this.request = requestContext.getRpcRequest();
        this.connection = requestContext.getVistaLinkConnection();
        this.locationNameSearchCriteria = requestContext.getRequestParameters().getLocationNameSearchCriteria();
        this.startingNameSearchCriteria = requestContext.getRequestParameters().getStartingNameSearchCriteria();
        Boolean sortDirectionFilterSearchCriteria = requestContext.getRequestParameters().getSortDirectionFilterSearchCriteria();
        if (sortDirectionFilterSearchCriteria != null) this.sortDirectionFilterSearchCriteria = sortDirectionFilterSearchCriteria;
    }

    public VistaLocation findLocation() throws Exception{
        VistaLocation location = null;
        List<String> requestParams = new ArrayList<String>();
        requestParams.add((this.startingNameSearchCriteria != null)? this.startingNameSearchCriteria.toUpperCase(): "");  // Starting Name to begin with.
        requestParams.add((sortDirectionFilterSearchCriteria) ? "1": "-1"); // sort alphabetically, if 1 sort in descending direction; else if -1 sort in ascending direction.

        this.request.setRpcName("ORWU1 NEWLOC");
        this.request.clearParams();
        this.request.setParams(requestParams);

        RpcResponse response = this.connection.executeRPC(this.request);
        String[] clinicLocationArray = new String[0];

        try {
            clinicLocationArray = parseRpcResponse(response);
            location = new VistaLocation(Long.parseLong(clinicLocationArray[0].trim()), clinicLocationArray[1]);

            if (location == null && Arrays.asList(response.getResults().split("\n")).size() > 0) {
                location = this.findLocation();
            }
        } catch (VistaLinkNoResponseException vlnre) {
            new VistaLinkResponseException("Expected a response; but found no response." , requestParams, response.getResults(), VistaLinkRequestException.LogTypes.WARNING, vlnre);
            // Do nothing because this method may not find nothing.
            // we create an exception because we want to log warning.
        }


        return location;
    }

    @Override
    protected String[] parseRpcResponse(RpcResponse response) throws Exception {
        String[] clinicLocationArray = new String[0];

        if (response != null && response.getResults() != null) {
            List<String> existingClinicLocations = new ArrayList<String>(Arrays.asList(response.getResults().split("\n")));
            for(String existingClinicLocation : existingClinicLocations){
                this.startingNameSearchCriteria = existingClinicLocation;
                if (this.locationNameSearchCriteria != null && existingClinicLocation.contains(this.locationNameSearchCriteria.toUpperCase())) {
                    clinicLocationArray = super.parseRpcResponseLineWithCarrotDelimiter(existingClinicLocation.getBytes());
                    break;
                }
            }
        }

        if(clinicLocationArray.length == 0 ){
            throw new VistaLinkNoResponseException();
        }

        return clinicLocationArray;
    }

    @Override
    public VistaLocation sendRequest() throws VistaLinkRequestException {
        VistaLocation location = null;
        try {
            location = this.findLocation();
        } catch (Exception e) {
            throw new VistaLinkRequestException(e);
        }

        return location;
    }

    @Override
    public void store() throws VistaLinkRequestException {

    }

    @Override
    public void load() throws VistaLinkRequestException {

    }
}
