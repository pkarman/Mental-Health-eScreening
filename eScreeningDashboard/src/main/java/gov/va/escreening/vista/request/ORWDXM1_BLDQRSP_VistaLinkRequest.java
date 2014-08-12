package gov.va.escreening.vista.request;

import gov.va.med.exception.FoundationsException;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.adapter.record.VistaLinkFaultException;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author khalid rizvi
 * 
 */
public class ORWDXM1_BLDQRSP_VistaLinkRequest extends VistaLinkBaseRequest implements VistaLinkRequest {

	private static final Logger logger = LoggerFactory.getLogger(ORWDXM1_BLDQRSP_VistaLinkRequest.class);

	private final VistaLinkConnection connection;
	private final RpcRequest request;
	private final ORWDXM1_BLDQRSP_RequestParameters reqParameters;

	public ORWDXM1_BLDQRSP_VistaLinkRequest(VistaLinkRequestContext<ORWDXM1_BLDQRSP_RequestParameters> requestContext) {
		this.request = requestContext.getRpcRequest();
		this.connection = requestContext.getVistaLinkConnection();
		this.reqParameters = requestContext.getRequestParameters();
	}

	private List<Map<String, Object>> getResult(RpcResponse response) throws Exception {
		String[] parsedRpcResponseLines = super.parseRpcSimpleResponseWithNewLineDelimiter(response);

		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for (String r : parsedRpcResponseLines) {
			String[] parsedRpcResponse = super.parseRpcResponseLineWithCarrotDelimiter(r.getBytes());

			Map<String, Object> record = new HashMap<String, Object>();
			record.put("QuickLevelIEN", Integer.valueOf(parsedRpcResponse[0]));
			record.put("ResponseID", Integer.valueOf(parsedRpcResponse[1]));
			record.put("DialogIEN", Integer.valueOf(parsedRpcResponse[2]));
			record.put("Type", parsedRpcResponse[3]);
			record.put("FormIDIEN", Integer.valueOf(parsedRpcResponse[4]));
			record.put("DisplayGroupIEN", Integer.valueOf(parsedRpcResponse[5]));

			resultList.add(record);
		}

		return resultList;
	}

	@Override
	public Map<String, Object> sendRequest() throws VistaLinkRequestException {

		List<Object> requestParams = new ArrayList<Object>();
		requestParams.add(reqParameters.getQuickOrderDialogIEN());
		requestParams.add(reqParameters.get8PiecesData());
		requestParams.add(reqParameters.getInpatientMedicationOnOutpatient());
		requestParams.add(reqParameters.getLocationIEN());

		request.setRpcName("ORWDXM1 BLDQRSP");
		request.clearParams();
		request.setParams(requestParams);

		RpcResponse response = null;
		try {
			response = connection.executeRPC(request);
			List<Map<String, Object>> resultList = getResult(response);
			return resultList.iterator().next();
		} catch (VistaLinkNoResponseException vlnre) {
			throw new VistaLinkResponseException("Expected a response; but found no response.", requestParams, "No Response", vlnre);
		} catch (VistaLinkFaultException e) {
			throw new VistaLinkResponseException("Expected a response; but found no response.", requestParams, "No Response", e);
		} catch (FoundationsException e) {
			throw new VistaLinkResponseException("Expected a response; but found no response.", requestParams, "No Response", e);
		} catch (Exception e) {
			throw new VistaLinkResponseException("Expected a response; but found no response.", requestParams, "No Response", e);
		}
	}

	@Override
	public void store() throws VistaLinkRequestException {

	}

	@Override
	public void load() throws VistaLinkRequestException {

	}

	@Override
	protected Object[] parseRpcResponse(RpcResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
