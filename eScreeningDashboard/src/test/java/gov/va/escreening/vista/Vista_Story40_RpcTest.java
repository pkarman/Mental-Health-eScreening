package gov.va.escreening.vista;

import gov.va.escreening.delegate.AssessmentDelegate;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.VeteranAssessmentService;
import gov.va.escreening.vista.request.ORWDXM1_BLDQRSP_RequestParameters;
import gov.va.escreening.vista.request.ORWDXM1_BLDQRSP_VistaLinkRequest;
import gov.va.escreening.vista.request.ORWDXM1_BLDQRSP_VistaLinkRequestContext;
import gov.va.escreening.vista.request.VistaLinkRequest;
import gov.va.escreening.vista.request.VistaLinkRequestContext;
import gov.va.med.exception.FoundationsException;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnectionFactory;
import gov.va.med.vistalink.adapter.cci.VistaLinkDuzConnectionSpec;
import gov.va.med.vistalink.adapter.record.VistaLinkFaultException;
import gov.va.med.vistalink.adapter.spi.VistaLinkManagedConnectionFactory;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcRequestFactory;
import gov.va.med.vistalink.rpc.RpcResponse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.resource.ResourceException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author khalid rizvi
 * @dated 07/10/2014
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/data-config.xml", "file:src/main/webapp/WEB-INF/spring/spring-security.xml", "file:src/main/webapp/WEB-INF/spring/business-config.xml", "/application-context-vistalink-test.xml" })
public class Vista_Story40_RpcTest {

    private static final Logger logger = LoggerFactory.getLogger(Vista_Story40_RpcTest.class);

	@Resource(name = "duzConnSpec-10000000056")
	gov.va.med.vistalink.adapter.cci.VistaLinkDuzConnectionSpec connSpec;

	@Resource(name = "veteranAssessmentService")
	VeteranAssessmentService vaSrv;

	@Resource(name = "vistaLinkManagedConnectionFactory")
	private VistaLinkManagedConnectionFactory vistaLinkManagedConnectionFactory;

	private RpcRequest createRpcRequest(String rpcName, String rpcContext,
			VistaLinkConnection vistaLinkConnection,
			boolean useProprietaryMessageFormat) throws FoundationsException {

		RpcRequest request = RpcRequestFactory.getRpcRequest(rpcContext, rpcName);
		request.setTimeOut(vistaLinkConnection.getTimeOut());
		request.setUseProprietaryMessageFormat(useProprietaryMessageFormat);
		return request;
	}

	private VistaLinkClientStrategy createVistaLinkClientStrategy(
			EscreenUser escreenUser, String appProxyName, String rpcContext) throws VistaLinkClientException {

		// production code will send the logged in escreen user else for test purpose, the escreen will be null, in
		// which case the test spec will be used
		VistaLinkDuzConnectionSpec duzConnectionSpec = escreenUser == null ? connSpec : new VistaLinkDuzConnectionSpec(escreenUser.getVistaDivision(), escreenUser.getVistaDuz());

		VistaLinkClientStrategy vistaLinkClientStrategy = new DuzVistaLinkClientStrategy(vistaLinkManagedConnectionFactory, duzConnectionSpec, appProxyName, rpcContext);

		return vistaLinkClientStrategy;
	}

	private Map<String, Object> getRefRespLst(VeteranAssessment va,
			VistaLinkClient vistaClient) {

		Map<String, Long> respListMap = vistaClient.getIENsMapForResponseList();

		Map<String, Object> respLstMap = new LinkedHashMap<String, Object>();

		respLstMap.put(RpcRequest.buildMultipleMSubscriptKey(String.format("%s,1", respListMap.get("ORDERABLEIEN".toUpperCase()))), vistaClient.getConsultationServiceNameDataSet2("1", "1", true).get("SURGERY"));

		Long commentIEN = respListMap.get("CommentIEN".toUpperCase());
		respLstMap.put(RpcRequest.buildMultipleMSubscriptKey(String.format("%s,1", commentIEN)), String.format("ORDIALOG(\"WP\",%s,1)", commentIEN));

		respLstMap.put(RpcRequest.buildMultipleMSubscriptKey(String.format("\"WP\",%s,1,1,0", commentIEN)), "Please evaluate and refer on for additional services as necessary");

		Long patientIen = Long.valueOf(va.getVeteran().getVeteranIen());
		Boolean partInpatient = vistaClient.findPatientDemographics(patientIen).getInpatientStatus();
		respLstMap.put(RpcRequest.buildMultipleMSubscriptKey(String.format("%s,1", respListMap.get("ClassIEN".toUpperCase()))), partInpatient ? "I" : "O");

		respLstMap.put(RpcRequest.buildMultipleMSubscriptKey(String.format("%s,1", respListMap.get("UrgencyIEN".toUpperCase()))), 1);
		respLstMap.put(RpcRequest.buildMultipleMSubscriptKey(String.format("%s,1", respListMap.get("PlaceIEN".toUpperCase()))), "C");
		respLstMap.put(RpcRequest.buildMultipleMSubscriptKey(String.format("%s,1", respListMap.get("ProviderIEN".toUpperCase()))), "");
		respLstMap.put(RpcRequest.buildMultipleMSubscriptKey(String.format("%s,1", respListMap.get("EarliestIEN".toUpperCase()))), "TODAY");
		respLstMap.put(RpcRequest.buildMultipleMSubscriptKey(String.format("%s,1", respListMap.get("MiscIEN".toUpperCase()))), "Positive OIF/OEF TBI screen.");
		respLstMap.put(RpcRequest.buildMultipleMSubscriptKey(String.format("%s,1", respListMap.get("CodeIEN".toUpperCase()))), "");
		respLstMap.put(RpcRequest.buildMultipleMSubscriptKey("\"ORCHECK\""), 0);
		respLstMap.put(RpcRequest.buildMultipleMSubscriptKey("\"ORTS\""), 0);
		return respLstMap;
	}

	private VistaLinkConnection getVistaLinkDuzConnection() throws ResourceException {
		VistaLinkConnectionFactory vistaLinkConnectionFactory = new VistaLinkConnectionFactory(vistaLinkManagedConnectionFactory, null);
		VistaLinkConnection vistaLinkConnection = (VistaLinkConnection) vistaLinkConnectionFactory.getConnection(connSpec);
		if (vistaLinkConnection.getTimeOut() < 1000) {
			vistaLinkConnection.setTimeOut(1000);
		}
		return vistaLinkConnection;
	}

	@Test
	public void testD3_Raw() throws Exception {
		VistaLinkConnection vistaLinkConnection = getVistaLinkDuzConnection();

		RpcRequest vReq = createRpcRequest("ORQQCN SVC W/SYNONYMS", "XOBV VISTALINK TESTER", vistaLinkConnection, true);
		List<String> requestParams = Arrays.asList("1", "1", "1");
		vReq.setParams(requestParams);

		RpcResponse vResp = vistaLinkConnection.executeRPC(vReq);
		String response = vResp.getResults();
		String[] resAry = response.split("\\n");

		int i = 0;
	}

	@Test
	public void testQuickOrderDialogIEN() throws Exception {
		RpcRequest vReq = RpcRequestFactory.getRpcRequest();
		vReq.setRpcName("ORQQPXRM DIALOG PROMPTS");
		vReq.setUseProprietaryMessageFormat(true);
		vReq.setRpcContext("XOBV VISTALINK TESTER");
		vReq.setParams(Arrays.asList("3172", "0", ""));

		VistaLinkConnection connection = getVistaLinkDuzConnection();
		RpcResponse vResp = connection.executeRPC(vReq);

		String resp = vResp.getResults();
		Assert.assertEquals("3^3172^^Q^1^51^^Consult^^^D\n", resp);
	}

	@Test
	public void testResultListIENs_VistaClient() throws Exception {
		VistaLinkClientStrategy vistaLinkClientStrategy = createVistaLinkClientStrategy(null, "", "OR CPRS GUI CHART");
		VistaLinkClient client = vistaLinkClientStrategy.getClient();
		try {
			logger.error(""+client.getIENsMapForResponseList());
		} finally {
			client.closeConnection();
		}

	}

	// @Test
	public void testStory40_TBI_Consult_AppendixD5_Ref() throws Exception {
		Long quickOrderDialogIEN = Long.valueOf(51L);
		Long partPatientIEN = Long.valueOf(100687L);
		Long partLocationIEN = Long.valueOf(228L);
		Long partProviderIEN = Long.valueOf(10000000210L);
		Boolean partInpatient = Boolean.valueOf(false);
		String partSex = String.valueOf("M");
		Integer partAge = Integer.valueOf(69);
		Long partEventIEN = Long.valueOf(0L);
		String partEventType = String.valueOf("C");
		Long partTimeStamp = Long.valueOf(0L);
		Long partEffective = Long.valueOf(0L);
		Long partScPercentage = Long.valueOf(0L);
		Boolean inpatientMedicationOnOutpatient = Boolean.valueOf(false);
		Long locationIEN = Long.valueOf(228);

		ORWDXM1_BLDQRSP_RequestParameters rp = new ORWDXM1_BLDQRSP_RequestParameters(quickOrderDialogIEN, partPatientIEN, partLocationIEN, partProviderIEN, partInpatient, partSex, partAge, partEventIEN, partEventType, partTimeStamp, partEffective, partScPercentage, inpatientMedicationOnOutpatient, locationIEN);

		VistaLinkConnection connection = getVistaLinkDuzConnection();
		RpcRequest request = createRpcRequest(null, "OR CPRS GUI CHART", connection, true);

		VistaLinkRequestContext<ORWDXM1_BLDQRSP_RequestParameters> rCtxt = new ORWDXM1_BLDQRSP_VistaLinkRequestContext<ORWDXM1_BLDQRSP_RequestParameters>(request, connection, rp);

		VistaLinkRequest vistaReq = new ORWDXM1_BLDQRSP_VistaLinkRequest(rCtxt);
		try {
			Map<String, Object> response = (Map<String, Object>) vistaReq.sendRequest();
			Assert.assertTrue(response.toString().equals("{FormIDIEN=110, Type=D, ResponseID=0, QuickLevelIEN=0, DisplayGroupIEN=11, DialogIEN=51}"));
		} finally {
			connection.close();
		}
	}
	
	@Value("${quick.order.ien}")
    private long qoi;
	
	@Test
	public void testStory40_TBI_Consult_Save_Scenario_1() throws Exception {
		VistaLinkClientStrategy vistaLinkClientStrategy = createVistaLinkClientStrategy(null, "", "OR CPRS GUI CHART");
		VistaLinkClient client = vistaLinkClientStrategy.getClient();
		VeteranAssessment va = vaSrv.findByVeteranAssessmentId(55);
		try {
			Map<String, String> exportColumnsMap = new HashMap<String, String>();
			exportColumnsMap.put("TBI_consult_where", "Baghdad, Iraq");
			//exportColumnsMap.put("TBI_consult_when", "2003");
			//exportColumnsMap.put("TBI_consult_how", "Shell exploded within 100 feet");
			logger.debug(""+client.saveTBIConsultOrders(va, qoi, exportColumnsMap));
		} finally {
			client.closeConnection();
		}
	}

	@Test
	public void testGetConsultInfo() throws Exception {
		VistaLinkClientStrategy vistaLinkClientStrategy = createVistaLinkClientStrategy(null, "", "OR CPRS GUI CHART");
		VistaLinkClient client = vistaLinkClientStrategy.getClient();
		try {
			logger.debug(""+client.getConsultInfo("C"));
		} finally {
			client.closeConnection();
		}
	}

	@Test
	public void testVistaPing() throws Exception {
		RpcRequest vReq = RpcRequestFactory.getRpcRequest();
		vReq.setRpcName("XOBV TEST PING");
		vReq.setUseProprietaryMessageFormat(true);
		vReq.setRpcContext("XOBV VISTALINK TESTER");
		VistaLinkConnection connection = getVistaLinkDuzConnection();
		try {
			RpcResponse vResp = connection.executeRPC(vReq);
			String resp = vResp.getResults();
		} finally {
			connection.close();
		}

	}

	@Test
	public void testVistaRequestCycle() {
		VistaLinkConnection vistaLinkConnection = null;
		try {
			vistaLinkConnection = getVistaLinkDuzConnection();
			RpcRequest vReq = createRpcRequest("ORQQPXRM REMINDER DIALOG", "XOBV VISTALINK TESTER", vistaLinkConnection, true);
			vReq.setParams(Arrays.asList("793", "100687"));

			RpcResponse vResp = vistaLinkConnection.executeRPC(vReq);
			String results = vResp.getResults();
			logger.debug(results);

		} catch (VistaLinkFaultException e) {
			e.printStackTrace();
		} catch (ResourceException e) {
			e.printStackTrace();
		} catch (FoundationsException e) {
			e.printStackTrace();
		} finally {
			if (vistaLinkConnection != null) {
				try {
					vistaLinkConnection.close();
				} catch (ResourceException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
