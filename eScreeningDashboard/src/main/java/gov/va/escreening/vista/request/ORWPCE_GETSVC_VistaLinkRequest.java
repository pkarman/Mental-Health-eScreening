package gov.va.escreening.vista.request;

import gov.va.escreening.vista.dto.VistaServiceCategoryEnum;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcResponse;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pouncilt on 4/10/14.
 */
public class ORWPCE_GETSVC_VistaLinkRequest extends VistaLinkBaseRequest implements VistaLinkRequest {
	private static final Logger logger = LoggerFactory.getLogger(ORWPCE_GETSVC_VistaLinkRequest.class);
	private VistaLinkConnection connection = null;
	private RpcRequest request = null;
	private String initialServiceConnectionCategory = null;
	private Long locationIEN = null;
	private boolean inpatientStatus = false;

	public ORWPCE_GETSVC_VistaLinkRequest(VistaLinkRequestContext<ORWPCE_GETSVC_RequestParameters> requestContext) {
		this.request = requestContext.getRpcRequest();
		this.connection = requestContext.getVistaLinkConnection();
		this.initialServiceConnectionCategory = requestContext.getRequestParameters().getInitialServiceConnectionCategory();
		this.locationIEN = requestContext.getRequestParameters().getLocationIEN();

		Boolean inpatientStatus = requestContext.getRequestParameters().getInpatientStatus();
		if (inpatientStatus != null) {
			this.inpatientStatus = inpatientStatus;
		}
	}

	@Override
	protected String[] parseRpcResponse(RpcResponse response) throws Exception {
		return super.parseRpcSimpleResponseWithNoDelimiter(response);
	}

	@Override
	public VistaServiceCategoryEnum sendRequest() throws VistaLinkRequestException {
		VistaServiceCategoryEnum serviceCategoryEnum = null;
		try {
			serviceCategoryEnum = this.findServiceCategory();
		} catch (Exception e) {
			throw new VistaLinkRequestException(e);
		}

		return serviceCategoryEnum;
	}

	private VistaServiceCategoryEnum findServiceCategory() throws Exception {
		VistaServiceCategoryEnum serviceCategoryEnum = null;

		List<String> requestParams = new ArrayList<String>();

		// Initial Service Connection Category.
		requestParams.add(this.initialServiceConnectionCategory);
		requestParams.add(this.locationIEN.toString()); // location IEN
		// Inpatient status (0 if no, 1 if yes)
		requestParams.add((this.inpatientStatus) ? "1" : "0");

		request.setRpcName("ORWPCE GETSVC");
		request.clearParams();
		request.setParams(requestParams);

		RpcResponse response = connection.executeRPC(request);

		try {
			String[] serviceCategoryArray = parseRpcResponse(response);
			for (VistaServiceCategoryEnum serviceCategory : VistaServiceCategoryEnum.values()) {
				if (serviceCategory.getCode().equals(serviceCategoryArray[0])) {
					serviceCategoryEnum = serviceCategory;
				}
			}
		} catch (VistaLinkNoResponseException vlnre) {
			// throw new
			// VistaLinkResponseException("Expected a response; but found no response."
			// , requestParams, response.getResults(), vlnre);
			serviceCategoryEnum = VistaServiceCategoryEnum.NOT_FOUND;
			logger.warn(String.format("Expected a response; but found no response. Request Parameters %s", requestParams));
		}

		return serviceCategoryEnum;
	}

	@Override
	public void store() throws VistaLinkRequestException {

	}

	@Override
	public void load() throws VistaLinkRequestException {

	}
}
