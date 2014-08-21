package gov.va.escreening.vista;

import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.vista.request.ORWDXM1_BLDQRSP_RequestParameters;
import gov.va.escreening.vista.request.ORWDXM1_BLDQRSP_VistaLinkRequest;
import gov.va.escreening.vista.request.ORWDXM1_BLDQRSP_VistaLinkRequestContext;
import gov.va.escreening.vista.request.VistaLinkRequest;
import gov.va.escreening.vista.request.VistaLinkRequestContext;
import gov.va.med.exception.FoundationsException;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.adapter.cci.VistaLinkDuzConnectionSpec;
import gov.va.med.vistalink.adapter.cci.VistaLinkVpidConnectionSpec;
import gov.va.med.vistalink.adapter.record.VistaLinkFaultException;
import gov.va.med.vistalink.adapter.spi.VistaLinkManagedConnectionFactory;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.springframework.core.env.Environment;

public class VistaLinkRPC_Client2 extends VistaLinkRPC_Client implements VistaLinkClient {

	/**
	 * inline template driven component to abstract rpc call
	 * 
	 * to use this component, the inherited class will have to provide facility to return the input parameters and to be
	 * able to construct the esoteric result from the raw response
	 * 
	 * @author khalid rizvi
	 * 
	 * @param <T>
	 *            the generic T represent the return type
	 */
	abstract class VistaLinkRpcInvoker<T> implements RpcInvoker<T> {

		/**
		 * workhorse method abstracting the rpc call to vista link
		 * 
		 * @param m
		 * @return
		 * @throws VistaLinkClientException
		 */
		protected String callRpcWithInputs(Map m) throws VistaLinkClientException {
			VistaLinkConnection vistaLinkConnection = (VistaLinkConnection) m.get("vistaLinkConnection");
			String rpcName = (String) m.get("rpcName");
			List<Object> reqParams = (List<Object>) m.get("inputParams");

			RpcRequest rpcReq = (RpcRequest) m.get("rpcRequest");
			rpcReq.setRpcName(rpcName);
			rpcReq.clearParams();
			if (reqParams != null) {
				rpcReq.setParams(reqParams);
			}

			RpcResponse vResp;
			try {
				vResp = vistaLinkConnection.executeRPC(rpcReq);
			} catch (VistaLinkFaultException e) {
				throw new VistaLinkClientException(e);
			} catch (FoundationsException e) {
				throw new VistaLinkClientException(e);
			}
			String results = vResp.getResults();

			return results;
		}

		/**
		 * method to call rpc in a well defined step by step sequence (based on template design pattern)
		 */
		@Override
		public T invokeRpc(VistaLinkConnection vistaLinkConnection,
				RpcRequest request, String rpcName) throws VistaLinkClientException {

			// 1. prepare inputs for rpc call to use
			List<Object> inputs = prepareReqParams();

			// 1.1 optionally validate the inputs here
			validateInputs(inputs);

			// 2. pack all parameters to pass onto the rpc caller
			Map m = new HashMap();
			m.put("vistaLinkConnection", vistaLinkConnection);
			m.put("rpcRequest", request);
			m.put("rpcName", rpcName);
			m.put("inputParams", inputs);

			// 3. call a method to invoke the real rpc
			String rawReponse = callRpcWithInputs(m);

			// 4. prepare response for requested response
			T requestedResponse = prepareResponse(rawReponse);

			// 5. return the response in the requested data type to the caller
			return requestedResponse;
		}

		protected abstract List<Object> prepareReqParams();

		protected abstract T prepareResponse(String rawReponse);

		protected void validateInputs(List<Object> inputs) {
		}
	}

	public VistaLinkRPC_Client2(VistaLinkManagedConnectionFactory vistaLinkManagedConnectionFactory, VistaLinkVpidConnectionSpec vpidConnectionSpec, String appProxyName, String rpcContext) throws VistaLinkClientException {
		super(vistaLinkManagedConnectionFactory, vpidConnectionSpec, appProxyName, rpcContext);
	}

	public VistaLinkRPC_Client2(VistaLinkManagedConnectionFactory vistaLinkManagedConnectionFactory, VistaLinkVpidConnectionSpec vpidConnectionSpec, String appProxyName, String rpcContext, Boolean useProprietaryMessageFormat, int connectionTimeOut) throws VistaLinkClientException {
		super(vistaLinkManagedConnectionFactory, vpidConnectionSpec, appProxyName, rpcContext, useProprietaryMessageFormat, connectionTimeOut);
	}

	public VistaLinkRPC_Client2(VistaLinkManagedConnectionFactory vistaLinkManagedConnectionFactory, VistaLinkDuzConnectionSpec duzConnectionSpec, String appProxyName, String rpcContext) throws VistaLinkClientException {
		super(vistaLinkManagedConnectionFactory, duzConnectionSpec, appProxyName, rpcContext);
	}

	public VistaLinkRPC_Client2(VistaLinkManagedConnectionFactory vistaLinkManagedConnectionFactory, VistaLinkDuzConnectionSpec duzConnectionSpec, String appProxyName, String rpcContext, Boolean useProprietaryMessageFormat, int connectionTimeOut) throws VistaLinkClientException {
		super(vistaLinkManagedConnectionFactory, duzConnectionSpec, appProxyName, rpcContext, useProprietaryMessageFormat, connectionTimeOut);
	}

	/**
	 * Implementation of Appendix D, Para D7 of CPRS Reminder Dialogs
	 * 
	 * @see https://docs.google.com/document/d/1mzRgPRvxdPH8rGt6yGtJykn-iE9sIdHle5u79F0_qrU/edit#
	 */
	@Override
	public Map<String, Long> getIENsMapForResponseList() {
		RpcInvoker<Map<String, Long>> rpcInvoker = new VistaLinkRpcInvoker<Map<String, Long>>() {

			@Override
			protected List<Object> prepareReqParams() {
				return Arrays.asList((Object) "GMRCOR CONSULT");
			}

			@Override
			protected Map<String, Long> prepareResponse(String rawResponse) {
				String[] resRows = rawResponse.split("\\n");

				Map<String, Long> respMap = new HashMap<String, Long>();
				for (String res : resRows) {
					String[] resAry = res.split("\\^");
					respMap.put(String.format("%sIEN", resAry[0]), Long.valueOf(resAry[1]));
				}
				return respMap;
			}
		};

		return rpcInvoker.invokeRpc(getConnection(), getRequest(), "ORWDX DLGDEF");
	}

	/**
	 * Implementation of Appendix D, Para D3 of CPRS Reminder Dialogs
	 * 
	 * @see https://docs.google.com/document/d/1mzRgPRvxdPH8rGt6yGtJykn-iE9sIdHle5u79F0_qrU/edit#
	 */
	@Override
	public Map<String, Long> getConsultationServiceNameDataSet2(
			final String startPositionSearchCriteria, final String purpose,
			final Boolean includeSynonyms) throws VistaLinkClientException {

		RpcInvoker<Map<String, Long>> rpcInvoker = new VistaLinkRpcInvoker<Map<String, Long>>() {

			@Override
			protected List<Object> prepareReqParams() {
				List<Object> rp = new ArrayList<Object>();
				rp.add(startPositionSearchCriteria);
				rp.add(purpose);
				rp.add(includeSynonyms);
				return rp;
			}

			@Override
			protected Map<String, Long> prepareResponse(String rawResponse) {
				Map<String, Long> responseMap = new HashMap<String, Long>();
				String[] strAry = rawResponse.split("\\n");
				for (String row : strAry) {
					String[] tokens = row.split("\\^");
					responseMap.put(tokens[1].trim(), Long.valueOf(tokens[tokens.length - 1].toString().trim()));
				}
				return responseMap;
			}
		};

		return rpcInvoker.invokeRpc(getConnection(), getRequest(), "ORQQCN SVC W/SYNONYMS");
	}

	/**
	 * Implementation of Appendix D, Para D5 of CPRS Reminder Dialogs
	 * 
	 * @see https://docs.google.com/document/d/1mzRgPRvxdPH8rGt6yGtJykn-iE9sIdHle5u79F0_qrU/edit#
	 */

	@Override
	public Long getTBIConsultDisplayGroupIEN(Long quickOrderIen,
			Long partPatientIEN, Long partLocationIEN, Long partProviderIEN,
			Boolean partInpatient, String partSex, Integer partAge,
			Long locationIEN) {

		ORWDXM1_BLDQRSP_RequestParameters rp = new ORWDXM1_BLDQRSP_RequestParameters(quickOrderIen, partPatientIEN, partLocationIEN, partProviderIEN, partInpatient, partSex, partAge, 0L, "C", 0L, 0L, 0L, Boolean.valueOf(false), locationIEN);
		VistaLinkRequestContext<ORWDXM1_BLDQRSP_RequestParameters> rCtxt = new ORWDXM1_BLDQRSP_VistaLinkRequestContext<ORWDXM1_BLDQRSP_RequestParameters>(getRequest(), getConnection(), rp);

		VistaLinkRequest<Map<String, Object>> vistaReq = new ORWDXM1_BLDQRSP_VistaLinkRequest(rCtxt);
		Map<String, Object> response = vistaReq.sendRequest();

		return Long.valueOf(response.get("DisplayGroupIEN").toString());
	}

	/**
	 * Implementation of Appendix D, Para D9 of CPRS Reminder Dialogs
	 * 
	 * @see https://docs.google.com/document/d/1mzRgPRvxdPH8rGt6yGtJykn-iE9sIdHle5u79F0_qrU/edit#
	 */
	@Override
	public Map<String, Object> saveTBIConsultOrders(
			final VeteranAssessment veteranAssessment,
			final long quickOrderIen, final Map<String, String> exportColumnsMap) {

		return new VistaLinkRpcInvoker<Map<String, Object>>() {
			/**
			 * Input Parameters being collected for RpcRequest as per Appendex D, Para D9 of CPRS Reminder Dialogs
			 * Document
			 * 
			 * @return
			 */
			@Override
			protected List<Object> prepareReqParams() {

				List<Object> reqParams = new ArrayList<Object>();
				// 1. DFN (IEN of the patient) (Should already be known)
				reqParams.add(Long.parseLong(veteranAssessment.getVeteran().getVeteranIen()));
				// 2. Provider IEN (Should already be known)
				reqParams.add(Long.parseLong(veteranAssessment.getClinician().getVistaDuz()));
				// 3.Location IEN (Step D1)
				// Ming Zhu suggested to not use this but getthe clinic location from the veteran Assessment
				// Long vistaLocationIEN=findLocation("PRIMARY CARE", null, true).getIen();
				reqParams.add(veteranAssessment.getClinic().getVistaIen());
				// 4. Order Dialog (Should always be “GMRCOR CONSULT”)
				reqParams.add(String.valueOf("GMRCOR CONSULT"));
				// 5. Display Group (Step D5)
				reqParams.add(getDlgGrpIEN(quickOrderIen, veteranAssessment));
				// 6. Quick Order Dialog IEN (IEN from ORQQPXRM DIALOG PROMPTS)
				reqParams.add(quickOrderIen);
				// 7. ORIFN - null if new order (Always be null for e-screening)
				reqParams.add("");
				// 8. Response List (Variables are defined in Step D7)
				reqParams.add(getRefRespLst(veteranAssessment, exportColumnsMap));
				// 9. ORDEA (Doesn’t seem to be used - Leave Blank)
				reqParams.add("");
				// 10. Appointment (Doesn’t seem to be used - Leave Blank)
				reqParams.add("");
				// 11. Order Source (Doesn’t seem to be used - Leave Blank)
				reqParams.add("");
				// 12. Is Event Default? - Boolean (Set to 0)
				reqParams.add(0);

				return reqParams;

			}

			private Map<String, Object> getRefRespLst(
					VeteranAssessment veteranAssessment,
					Map<String, String> exportColumnsMap) {

				Map<String, Long> respListMap = getIENsMapForResponseList();

				Map<String, Object> respLstMap = new LinkedHashMap<String, Object>();

				// 1. ARRAY(OrderIEN,1)=IEN of Service (Step D3)
				respLstMap.put(RpcRequest.buildMultipleMSubscriptKey(String.format("%s,1", respListMap.get("ORDERABLEIEN".toUpperCase()))), getConsultationServiceNameDataSet2("1", "1", true).get("TBI/POLYTRAUMA SUPPORT CLINIC TEAM"));
				// 2. ARRAY(CommentIEN,1)=”ORDIALOG("”WP”",CommentIEN,1)”
				Long commentIEN = respListMap.get("CommentIEN".toUpperCase());
				respLstMap.put(RpcRequest.buildMultipleMSubscriptKey(String.format("%s,1", commentIEN)), String.format("ORDIALOG(\"WP\",%s,1)", commentIEN));
				// 3. ARRAY(“WP”,CommentIEN,1,#,0)=TEXT FOR LINE # (Step D8)
				respLstMap.put(RpcRequest.buildMultipleMSubscriptKey(String.format("\"WP\",%s,1,1,0", commentIEN)), prepareTbiConsultReasonText(exportColumnsMap));
				// 4. ARRAY(ClassIEN,1)= Class(“O” or “I”)(Step D6a)
				Long patientIen = Long.valueOf(veteranAssessment.getVeteran().getVeteranIen());
				Boolean partInpatient = findPatientDemographics(patientIen).getInpatientStatus();
				respLstMap.put(RpcRequest.buildMultipleMSubscriptKey(String.format("%s,1", respListMap.get("ClassIEN".toUpperCase()))), partInpatient ? "I" : "O");

				// 5. ARRAY(UrgencyIEN,1)=IEN for Urgency (Step D2)
				Map<String, Map<String, String>> consultInfoMap = getConsultInfo("C");
				Map<String, String> urgencyMap = partInpatient ? consultInfoMap.get("inpatient_urgencies") : consultInfoMap.get("outpatient_urgencies");
				respLstMap.put(RpcRequest.buildMultipleMSubscriptKey(String.format("%s,1", respListMap.get("UrgencyIEN".toUpperCase()))), Long.valueOf(urgencyMap.get("ROUTINE")));
				// 6. ARRAY(PlaceIEN,1)=IEN Place for Consult (Step D2)
				Map<String, String> placeMap = partInpatient ? consultInfoMap.get("inpatient_places") : consultInfoMap.get("outpatient_places");
				respLstMap.put(RpcRequest.buildMultipleMSubscriptKey(String.format("%s,1", respListMap.get("PlaceIEN".toUpperCase()))), placeMap.get("CONSULTANT'S CHOICE"));
				// 7. ARRAY(ProviderIEN,1)=IEN of Provider’s Attention (Step D4)
				respLstMap.put(RpcRequest.buildMultipleMSubscriptKey(String.format("%s,1", respListMap.get("ProviderIEN".toUpperCase()))), "");
				// 8. ARRAY(EarliestIEN,1)=Earliest Appt Date (Step D6b)
				respLstMap.put(RpcRequest.buildMultipleMSubscriptKey(String.format("%s,1", respListMap.get("EarliestIEN".toUpperCase()))), "TODAY");
				// 9. ARRAY(MiscIEN,1)=Provisional Diagnosis (Free Text) (Step D6c)
				respLstMap.put(RpcRequest.buildMultipleMSubscriptKey(String.format("%s,1", respListMap.get("MiscIEN".toUpperCase()))), "Positive OIF/OEF TBI screen.");
				// 10. ARRAY(CodeIEN,1)=Not used?
				respLstMap.put(RpcRequest.buildMultipleMSubscriptKey(String.format("%s,1", respListMap.get("CodeIEN".toUpperCase()))), "");
				// 11. ARRAY("ORCHECK")=Num of order checks (Always “0” ?)
				respLstMap.put(RpcRequest.buildMultipleMSubscriptKey("\"ORCHECK\""), 0);
				// 12. ARRAY("ORTS")=IEN of Treating Specialty (Always “0” ?)
				respLstMap.put(RpcRequest.buildMultipleMSubscriptKey("\"ORTS\""), 0);

				return respLstMap;
			}

			private String prepareTbiConsultReasonText(
					Map<String, String> exportColumnsMap) {

				StringBuilder sb = new StringBuilder();
				sb.append("Veteran not previously seen by the San Diego VA TBI clinic\n");
				sb.append("OIF/OEF Veteran\n");
				sb.append("OIF/OEF Veteran with:\n");
				sb.append("\tOIF/OEF deployment-related, suspected TBI.\n");
				sb.append("\tPersistent symptoms.\n");
				sb.append("\tPositive OIF/OEF TBI screen.\n");
				sb.append("OIF/OEF TBI screen completed date: " + (new LocalDate()).toString("MMM dd,yyyy") + "\n");
				sb.append("When & Where did the TBI occur:\n");
				sb.append("\t" + (exportColumnsMap.get("TBI_consult_when") == null ? "Year not provided to determine when" : exportColumnsMap.get("TBI_consult_when")) + ", " + (exportColumnsMap.get("TBI_consult_where") == null ? "Not provided" : exportColumnsMap.get("TBI_consult_where")) + "\n");
				sb.append("How did the TBI occur:\n");
				sb.append("\t" + (exportColumnsMap.get("TBI_consult_how") == null ? "Not Provided" : exportColumnsMap.get("TBI_consult_how")) + "\n");
				sb.append("GOALS for TBI clinic evaluation:\n");
				sb.append("\tPlease evaluate and refer on for additional services as necessary\n");

				return sb.toString();
			}

			private Long getDlgGrpIEN(Long quickOrderIen,
					VeteranAssessment veteranAssessment) {
				Long patientIen = Long.valueOf(veteranAssessment.getVeteran().getVeteranIen());
				Long locationIen = Long.valueOf(veteranAssessment.getClinic().getVistaIen());
				Long partProviderIEN = Long.valueOf(veteranAssessment.getClinician().getVistaDuz());
				Boolean partInpatient = findPatientDemographics(patientIen).getInpatientStatus();
				String partSex = String.valueOf(veteranAssessment.getVeteran().getGender().charAt(0)).toUpperCase();
				Integer partAge = Integer.valueOf(Years.yearsBetween(new LocalDate(veteranAssessment.getVeteran().getBirthDate()), new LocalDate()).getYears());
				return getTBIConsultDisplayGroupIEN(quickOrderIen, patientIen, locationIen, partProviderIEN, partInpatient, partSex, partAge, locationIen);
			}

			@Override
			protected Map<String, Object> prepareResponse(String rawReponse) {
				return createResultMap(rawReponse, Arrays.asList("IFN", "Group", "OrderTime", "StartTime", "StopTime", "Status", "Signature", "Nurse", "Clerk", "ProviderIEN", "ProviderName", "ActDA", "Flagged", "DCType", "ChrtRev", "DEA", "ProviderVA", "DigSig", "LocationIEN", "DCOrigOrder", "Unused", "OrderTitleText"));
			}

		}.invokeRpc(getConnection(), getRequest(), "ORWDX SAVE");
	}

	private Map<String, Object> createResultMap(String responseStr,
			List<String> keys) {
		String[] resultsArray = responseStr.split("\\^");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		int index = 0;
		for (String meta : keys) {
			resultMap.put(meta, resultsArray[index++].trim());
		}

		return resultMap;
	}

	@Override
	public Map<String, Map<String, String>> getConsultInfo(String orderType) {
		RpcInvoker<Map<String, Map<String, String>>> rpcInvoker = new VistaLinkRpcInvoker<Map<String, Map<String, String>>>() {

			@Override
			protected List<Object> prepareReqParams() {
				return Arrays.asList((Object) "C");
			}

			@Override
			protected Map<String, Map<String, String>> prepareResponse(
					String rawResponse) {
				String[] resAry = rawResponse.split("\\n");

				return ResponseHelper.createConsultInfoMap(Arrays.asList(resAry));
			}
		};

		return rpcInvoker.invokeRpc(getConnection(), getRequest(), "ORWDCN32 DEF");
	}
}
