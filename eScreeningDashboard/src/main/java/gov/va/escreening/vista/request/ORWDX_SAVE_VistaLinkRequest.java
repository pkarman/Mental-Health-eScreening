package gov.va.escreening.vista.request;

import gov.va.escreening.vista.dto.ConsultOrder;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pouncilt on 6/9/14.
 */
public class ORWDX_SAVE_VistaLinkRequest extends VistaLinkBaseRequest implements VistaLinkRequest {
	private static final Logger logger = LoggerFactory.getLogger(ORWDX_SAVE_VistaLinkRequest.class);

	private final VistaLinkConnection connection;
	private final RpcRequest request;
	private final ORWDX_SAVE_RequestParameters reqParam;

	public ORWDX_SAVE_VistaLinkRequest(VistaLinkRequestContext<ORWDX_SAVE_RequestParameters> requestContext) {
		this.request = requestContext.getRpcRequest();
		this.connection = requestContext.getVistaLinkConnection();
		this.reqParam = requestContext.getRequestParameters();
	}

	// @Override
	// protected ConsultOrder[] parseRpcResponse(RpcResponse response) throws Exception {
	// ConsultOrder[] consultOrders = new ConsultOrder[0];
	// String[] parsedResponse = super.parseRpcSimpleResponseWithCarrotDelimiter(response);
	//
	// // Long orderIEN = Long.parseLong(parsedResponse[0].split(";")[0].substring(1)); // TODO: May need to add to the
	// // Consult Order constructor.
	// Long orderIEN = Long.parseLong(parsedResponse[0].split(";")[1]);
	// Integer groupNumber = Integer.parseInt(parsedResponse[1]);
	// Date orderTime = (parsedResponse[2] != null & parsedResponse[2].length() > 0) ?
	// VistaUtils.convertVistaDate(parsedResponse[2]) : null;
	// Date startTime = (parsedResponse[3] != null & parsedResponse[3].length() > 0) ?
	// VistaUtils.convertVistaDate(parsedResponse[3]) : null;
	// ;
	// Date endTime = (parsedResponse[4] != null & parsedResponse[4].length() > 0) ?
	// VistaUtils.convertVistaDate(parsedResponse[4]) : null;
	// ;
	// String status = parsedResponse[5];
	// String signature = parsedResponse[6];
	// String nurseName = parsedResponse[7];
	// String clerkName = parsedResponse[8];
	// Long providerIEN = (parsedResponse[9] != null && parsedResponse[9].length() > 0) ?
	// Long.parseLong(parsedResponse[9]) : null;
	// String providerName = parsedResponse[10];
	// String actDA = parsedResponse[11];
	// Boolean flagged = (parsedResponse[12] != null && (parsedResponse[12].equalsIgnoreCase("Y") ||
	// parsedResponse[12].equalsIgnoreCase("YES") || parsedResponse[12].equalsIgnoreCase("1"))) ? true : false;
	// String dcType = parsedResponse[13];
	// String chartRev = parsedResponse[14];
	// String deaNumber = parsedResponse[15];
	// String providerVANumber = parsedResponse[16];
	// String digSig = parsedResponse[17];
	// String locationName = (parsedResponse[18] != null && parsedResponse[18].length() > 0) ?
	// parsedResponse[18].split(":")[0] : null;
	// Long locationIEN = (parsedResponse[18] != null && parsedResponse[18].length() > 0) ?
	// Long.parseLong(parsedResponse[18].split(":")[1]) : null;
	// String dcOriginalOrder = parsedResponse[19];
	// String piece21 = parsedResponse[20];
	// String title = parsedResponse[21];
	// String piece23 = parsedResponse[22]; // TODO: Need to add to the ConsultOrder constructor.
	//
	// ConsultOrder consultOrder = new ConsultOrder(orderIEN, groupNumber, orderTime, startTime, endTime, status,
	// signature, nurseName, clerkName, providerIEN, providerName, actDA, flagged, dcType, chartRev, deaNumber,
	// providerVANumber, digSig, locationName, locationIEN, dcOriginalOrder, piece21, title);
	//
	// consultOrders = new ConsultOrder[1];
	// consultOrders[0] = consultOrder;
	//
	// return consultOrders;
	// }

	@Override
	public Map<String, Object> sendRequest() throws VistaLinkRequestException {
		try {
			return this.saveConsultOrder();
		} catch (Exception e) {
			throw new VistaLinkRequestException(e);
		}
	}

	private Map<String, Object> saveConsultOrder() throws Exception {
		ConsultOrder consultOrder = null;
		List<Object> requestParams = createReqParams();

		request.setRpcName("ORWDX SAVE");
		request.clearParams();
		request.setParams(requestParams);

		RpcResponse response = connection.executeRPC(request);
		return createResult(response);
	}

	private Map<String, Object> createResult(RpcResponse response) {
		String resultsStr = response.getResults();
		String[] resultsArray = resultsStr.split("\\^");
		String[] metaArray = { "IFN", "Group", "OrderTime", "StartTime", "StopTime", "Status", "Signature", "Nurse", "Clerk", "ProviderIEN", "ProviderName", "ActDA", "Flagged", "DCType", "ChrtRev", "DEA", "ProviderVA", "DigSig", "LocationIEN", "DCOrigOrder", "Unused", "OrderTitleText" };

		Map<String, Object> resultMap = new HashMap<String, Object>();
		int index = 0;
		for (String meta : metaArray) {
			resultMap.put(meta, resultsArray[index++]);
		}

		return resultMap;
	}

	private List<Object> createReqParams() {

		Long patientIEN = reqParam.getPatientIEN();
		Long providerIEN = reqParam.getProviderIEN();
		Long locationIEN = reqParam.getLocationIEN();
		String orderDialog = reqParam.getOrderDialog();
		Long dialogGroupIEN = reqParam.getDialogGroupIEN();
		Long quickOrderDialogIEN = reqParam.getQuickOrderDialogIEN();
		Long orderIEN = reqParam.getOrderIEN();
		Map responseList = reqParam.getResponseList();
		String drugControlledSubstanceSchedule = reqParam.getDrugControlledSubstanceSchedule();
		String appointment = reqParam.getAppointment();
		String orderSource = reqParam.getOrderSource();
		Boolean isEvent = reqParam.getIsEvent();

		List<Object> rpcReqParams = new ArrayList<Object>();
		rpcReqParams.add(patientIEN);
		rpcReqParams.add(providerIEN);
		rpcReqParams.add(locationIEN);
		rpcReqParams.add(orderDialog);
		rpcReqParams.add(dialogGroupIEN);
		rpcReqParams.add(quickOrderDialogIEN);
		rpcReqParams.add(orderIEN == null ? "" : orderIEN);
		rpcReqParams.add(responseList);
		rpcReqParams.add(drugControlledSubstanceSchedule == null ? "" : drugControlledSubstanceSchedule);
		rpcReqParams.add(appointment == null ? "" : appointment);
		rpcReqParams.add(orderSource == null ? "" : orderSource);
		rpcReqParams.add(isEvent ? 1 : 0);

		return rpcReqParams;
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
