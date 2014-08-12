package gov.va.escreening.vista.request;

import gov.va.escreening.vista.VistaUtils;
import gov.va.escreening.vista.dto.VistaClinician;
import gov.va.escreening.vista.dto.VistaDateFormat;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pouncilt on 4/11/14.
 */
public class ORWU_NEWPERS_VistaLinkRequest extends VistaLinkBaseRequest implements VistaLinkRequest {
    private static final Logger logger = LoggerFactory.getLogger(ORWU_NEWPERS_VistaLinkRequest.class);
    private VistaLinkConnection connection = null;
    private RpcRequest request = null;
    private String firstNameFilterSearchCriteria = null;
    private String lastNameFilterSearchCriteria = null;
    private String startingNameSearchCriteria = null;
    private boolean sortDirectionFilterSearchCriteria = false;
    private String securityKeyFilterSearchCriteria = null;
    private Date dateFilterSearchCriteria = null;
    private boolean rdvUserFilterSearchCriteria = false;
    private boolean returnAllFilterSearchCriteria = false;


    public ORWU_NEWPERS_VistaLinkRequest(VistaLinkRequestContext<ORWU_NEWPERS_RequestParameters> requestContext) {
        this.request = requestContext.getRpcRequest();
        this.connection = requestContext.getVistaLinkConnection();
        this.firstNameFilterSearchCriteria = requestContext.getRequestParameters().getFirstNameFilterSearchCriteria();
        this.lastNameFilterSearchCriteria = requestContext.getRequestParameters().getLastNameFilterSearchCriteria();
        this.startingNameSearchCriteria = requestContext.getRequestParameters().getStartingNameSearchCriteria();
        Boolean sortDirectionFilterSearchCriteria = requestContext.getRequestParameters().isSortDirectionFilterSearchCriteria();
        if (sortDirectionFilterSearchCriteria != null) this.sortDirectionFilterSearchCriteria = sortDirectionFilterSearchCriteria;
        this.securityKeyFilterSearchCriteria = requestContext.getRequestParameters().getSecurityKeyFilterSearchCriteria();
        this.dateFilterSearchCriteria = requestContext.getRequestParameters().getDateFilterSearchCriteria();
        Boolean rdvUserFilterSearchCriteria = requestContext.getRequestParameters().isRdvUserFilterSearchCriteria();
        if (rdvUserFilterSearchCriteria != null) this.rdvUserFilterSearchCriteria = rdvUserFilterSearchCriteria;
        Boolean returnAllFilterSearchCriteria = requestContext.getRequestParameters().isReturnAllFilterSearchCriteria();
        if (returnAllFilterSearchCriteria != null) this.returnAllFilterSearchCriteria = returnAllFilterSearchCriteria;
    }

    @Override
    protected VistaClinician[] parseRpcResponse(RpcResponse response) throws Exception {
        VistaClinician[] results = new VistaClinician[0];

        if (response != null && response.getResults() != null) {
            boolean foundAMatch = false;
            List<String> existingClinicians = new ArrayList<String>(Arrays.asList(response.getResults().split("\n")));
            List<VistaClinician> existingClinicianList = new ArrayList<VistaClinician>();

            for(String existingClinician : existingClinicians) {
                startingNameSearchCriteria = existingClinician;
                if(firstNameFilterSearchCriteria == null && lastNameFilterSearchCriteria == null) {
                    if (existingClinician.contains(firstNameFilterSearchCriteria)) {
                        logger.debug("Existing Clinician: " + existingClinician);
                        String[] clinicianArray = super.parseRpcResponseLineWithCarrotDelimiter(existingClinician.getBytes());
                        if (clinicianArray.length == 0) {
                            throw new VistaLinkNoResponseException("Response is empty for RPC: ORWU NEWPERS.  Expected a response.");
                        } else {
                            String[] clinicianNameArray = clinicianArray[1].split(",");
                            String clinicianTitle = null;

                            if (clinicianNameArray[0].equals(lastNameFilterSearchCriteria)) {
                                if (clinicianArray.length == 3 && clinicianArray[2] != null && clinicianArray[2].contains("- ")) {
                                    clinicianTitle = clinicianArray[2].split("- ")[0];
                                }
                                existingClinicianList.add(new VistaClinician(Long.parseLong(clinicianArray[0].trim()), clinicianNameArray[1], clinicianNameArray[0], clinicianTitle));
                                foundAMatch = true;
                                break;
                            }
                        }
                    }
                } else if (firstNameFilterSearchCriteria != null && lastNameFilterSearchCriteria == null) {
                    if (existingClinician.contains(firstNameFilterSearchCriteria)) {
                        logger.debug("Existing Clinician: " + existingClinician);
                        String[] clinicianArray = super.parseRpcResponseLineWithCarrotDelimiter(existingClinician.getBytes());

                        if (clinicianArray.length == 0) {
                            throw new VistaLinkNoResponseException("Response is empty for RPC: ORWU NEWPERS.  Expected a response.");
                        } else {
                            String[] clinicianNameArray = clinicianArray[1].split(",");
                            String clinicianTitle = null;
                            if (clinicianArray.length == 3 && clinicianArray[2] != null && clinicianArray[2].contains("- ")) {
                                clinicianTitle = clinicianArray[2].split("- ")[0];
                            }
                            existingClinicianList.add(new VistaClinician(Long.parseLong(clinicianArray[0].trim()), clinicianNameArray[1], clinicianNameArray[0], clinicianTitle));
                        }
                    }
                } else if(firstNameFilterSearchCriteria == null && lastNameFilterSearchCriteria !=null){
                    if (existingClinician.contains(lastNameFilterSearchCriteria)) {
                        logger.debug("Existing Clinician: " + existingClinician);
                        String[] clinicianArray = super.parseRpcResponseLineWithCarrotDelimiter(existingClinician.getBytes());
                        if (clinicianArray.length == 0) {
                            throw new VistaLinkNoResponseException("Response is empty for RPC: ORWU NEWPERS.  Expected a response.");
                        } else {
                            String[] clinicianNameArray = clinicianArray[1].split(",");
                            String clinicianTitle = null;
                            if (clinicianArray.length == 3 && clinicianArray[2] != null && clinicianArray[2].contains("- ")) {
                                clinicianTitle = clinicianArray[2].split("- ")[0];
                            }
                            existingClinicianList.add(new VistaClinician(Long.parseLong(clinicianArray[0].trim()), clinicianNameArray[1], clinicianNameArray[0], clinicianTitle));
                        }
                    }
                } else {
                    logger.debug("Existing Clinician: " + existingClinician);
                    String[] clinicianArray = super.parseRpcResponseLineWithCarrotDelimiter(existingClinician.getBytes());
                    if (clinicianArray.length == 0) {
                        throw new VistaLinkNoResponseException("Response is empty for RPC: ORWU NEWPERS.  Expected a response.");
                    } else {
                        String[] clinicianNameArray = clinicianArray[1].split(",");
                        String clinicianTitle = null;
                        if (clinicianArray.length == 3 && clinicianArray[2] != null && clinicianArray[2].contains("- ")) {
                            clinicianTitle = clinicianArray[2].split("- ")[0];
                        }
                        existingClinicianList.add(new VistaClinician(Long.parseLong(clinicianArray[0].trim()), clinicianNameArray[1], clinicianNameArray[0], clinicianTitle));
                    }
                }

            }


            if (foundAMatch && existingClinicians.size() > 0) {
                String[] clinicianArray = super.parseRpcResponseLineWithCarrotDelimiter(startingNameSearchCriteria.getBytes());
                String[] clinicianNameArray = clinicianArray[1].split(",");
                startingNameSearchCriteria = clinicianNameArray[0].toUpperCase()+","+clinicianNameArray[1].toUpperCase();
                existingClinicianList.addAll(Arrays.asList(findClinicians()));
            }

            results = existingClinicianList.toArray(new VistaClinician[existingClinicianList.size()]);
        }

        return results;
    }

    @Override
    public VistaClinician[] sendRequest() throws VistaLinkRequestException {
        VistaClinician[] clinicians = null;
        try {
            clinicians = this.findClinicians();
        } catch (Exception e) {
            throw new VistaLinkRequestException(e);
        }
        return clinicians;
    }

    private VistaClinician[] findClinicians() throws Exception{
        List<String> requestParams = new ArrayList<String>();
        requestParams.add((startingNameSearchCriteria !=null)?startingNameSearchCriteria.toUpperCase(): ""); // Starting Name (Optional)
        requestParams.add((sortDirectionFilterSearchCriteria)? "1": "-1"); // Sort alphabetically, if 1 sort in ascending direction; else if -1 sort in descending direction.
        requestParams.add((securityKeyFilterSearchCriteria != null)? securityKeyFilterSearchCriteria: ""); // ORKEY - screen users by a particular security key (Optional).
        requestParams.add((dateFilterSearchCriteria != null)? VistaUtils.convertToVistaDateString(dateFilterSearchCriteria, VistaDateFormat.MMddHHmmss): ""); // ORDATE - Returns only active users for a particular date (Optional).
        requestParams.add((rdvUserFilterSearchCriteria)? "1" : "0"); // ORVIZ - if true, returns RDV users, otherwise not (Optional).
        requestParams.add((returnAllFilterSearchCriteria)? "1": "0"); // ORALL - (Optional)


        request.setRpcName("ORWU NEWPERS");
        request.clearParams();
        request.setParams(requestParams);

        RpcResponse response = connection.executeRPC(request);
        logger.debug("Find Clinician: " + response.getResults());

        VistaClinician[] existingClinicians = new VistaClinician[0];
        try {
            existingClinicians = parseRpcResponse(response);
        } catch (VistaLinkNoResponseException vlnre) {
            new VistaLinkResponseException("Expected a response; but found no response." , requestParams, response.getResults(), VistaLinkRequestException.LogTypes.WARNING, vlnre);
            // Do nothing because this method may not find nothing.
            // we create an exception because we want to log warning.
        }
        return existingClinicians;
    }

    @Override
    public void store() throws VistaLinkRequestException {

    }

    @Override
    public void load() throws VistaLinkRequestException {

    }
}
