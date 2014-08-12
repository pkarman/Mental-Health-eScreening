package gov.va.escreening.vista;

import gov.va.escreening.vista.dto.ConsultationUrgencyDataSet;
import gov.va.escreening.vista.dto.VistaClinician;
import gov.va.escreening.vista.dto.VistaDateFormat;
import gov.va.escreening.vista.dto.VistaLocation;
import gov.va.escreening.vista.dto.VistaNoteTitle;
import gov.va.escreening.vista.dto.VistaProgressNote;
import gov.va.escreening.vista.dto.VistaServiceCategoryEnum;
import gov.va.escreening.vista.request.VistaLinkRequestTestDouble;
import gov.va.med.exception.FoundationsException;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnectionFactory;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnectionSpec;
import gov.va.med.vistalink.adapter.cci.VistaLinkDuzConnectionSpec;
import gov.va.med.vistalink.adapter.record.VistaLinkFaultException;
import gov.va.med.vistalink.adapter.spi.VistaLinkManagedConnectionFactory;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcRequestFactory;
import gov.va.med.vistalink.rpc.RpcResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.resource.ResourceException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by pouncilt on 3/31/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/data-config.xml", "file:src/main/webapp/WEB-INF/spring/spring-security.xml","file:src/main/webapp/WEB-INF/spring/business-config.xml","/application-context-vistalink-test.xml" })
public class VistaRPCTest {
	@Autowired
	private VistaLinkManagedConnectionFactory vistaLinkManagedConnectionFactory;
	@Autowired
	private VistaLinkClient client;

	@Test
	public void testDoNothing(){
		
	}
	@Test
	public void testPreserveAllTokens() throws Exception {
		String targetString = "^^^^";
		int expectedResultsLength = 4;
		VistaLinkRequestTestDouble vistaLinkBaseRequest = new VistaLinkRequestTestDouble();
		String[] actualResults = vistaLinkBaseRequest.parseRpcResponseLineWithCarrotDelimiter(targetString.getBytes());
		Assert.assertEquals(expectedResultsLength, actualResults.length);
	}

	@Test
	public void findVisitLocationTest() throws Exception {
		String expectedClinicLocation = "PRIMARY CARE";
		Long expectedClinicLocationIEN = 32L;

		VistaLocation clinicLocation = client.findLocation(expectedClinicLocation, null, true);

		Assert.assertNotNull(clinicLocation);
		Assert.assertEquals(expectedClinicLocationIEN, clinicLocation.getIen());
		Assert.assertEquals(expectedClinicLocation, clinicLocation.getName().toUpperCase());
	}

	@Test
	public void findServiceCategoryTest() throws Exception {
		VistaLocation clinicLocation = client.findLocation("PRIMARY CARE", "", true); // PRIMARY CARE, OBSERVATION
		Assert.assertNotNull(clinicLocation);
		Long clinicId = clinicLocation.getIen(); // 234L; //128L

		VistaServiceCategoryEnum serviceCategoryEnum = client.findServiceCategory(VistaServiceCategoryEnum.AMBULATORY, clinicId, false);
		Assert.assertNotNull(serviceCategoryEnum);
	}

	@Test
	public void findProgressNoteTitlesTest() throws Exception {
		VistaNoteTitle[] vistaNoteTitles = client.findProgressNoteTitles();

		Assert.assertNotNull(vistaNoteTitles);
		Assert.assertTrue(vistaNoteTitles.length > 0);
	}

	@Test
	public void testFindingVistaLinkProgressNoteTitle() throws Exception {
		String expectedProgressNoteTitle = "CRISIS NOTE";
		VistaNoteTitle progressNoteTitle = client.findProgressNoteTitle(expectedProgressNoteTitle);

		Assert.assertNotNull(progressNoteTitle);
		Assert.assertEquals(expectedProgressNoteTitle, progressNoteTitle.getNoteTitleName());
	}

	@Test
	public void testFindingVistaLinkClinician() throws Exception {
		String expectedClinicianFirstName = "Forty-One";
		String expectedClinicianLastName = "Pharmacist";
		String startingNameSearchCriteria = "PHARMACIST,FORTY-ON@~"; // This
																		// criteria
																		// has
																		// to be
																		// all
																		// capital
																		// letters.
		VistaClinician[] clinicians = client.findClinicians(expectedClinicianFirstName, expectedClinicianLastName, startingNameSearchCriteria, true);

		Assert.assertNotNull(clinicians);
		Assert.assertTrue(clinicians.length > 0);
		Assert.assertEquals(expectedClinicianFirstName, clinicians[0].getFirstName());
		Assert.assertEquals(expectedClinicianLastName, clinicians[0].getLastName());
	}

	@Test
	public void testVistaLinkCreateProgressNote() throws Exception {
		VistaNoteTitle progressNoteTitle = client.findProgressNoteTitle("CRISIS NOTE");
		VistaClinician clinician = client.findClinician("Forty-One", "Pharmacist", null, true);
		String visitString = calculateVistaVisitString("PRIMARY CARE", false); // Required
																				// to
																				// create
																				// Progress
																				// Note.
		Long patientIEN = 100003L; // Required to create Progress Note.
		Long titleIEN = progressNoteTitle.getNoteTitleIen(); // Required to
																// create
																// Progress
																// Note.
		Date vistaVisitDateTime = null; // Optional when creating Progress Note.
		Long locationIEN = Long.parseLong(VistaUtils.getClinicIEN(visitString).trim()); // Optional
																						// when
																						// creating
																						// Progress
																						// Note.
		Long visitIEN = null; // Optional when creating Progress Note.
		Object[] identifiers = { // Required to create Progress Note.
		clinician.getIEN(), // Author IEN - Required to create Progress Note.
		VistaUtils.convertVistaDate(visitString), // Required to create Progress
													// Note.
		locationIEN, // Required to create Progress Note.
		"" };
		boolean suppressCommitPostLogic = false; // Required to create Progress
													// Note.
		boolean saveCrossReference = false; // Required to create Progress Note.
		String content = "The patient is in need of immediate intervention."; // Required
																				// to
																				// create
																				// Progress
																				// Note.
		boolean appendContent = true; // Required to create Progress Note.

		VistaProgressNote progressNote = client.saveProgressNote(patientIEN, titleIEN, locationIEN, visitIEN, vistaVisitDateTime, visitString, identifiers, content, suppressCommitPostLogic, saveCrossReference, appendContent);

		Assert.assertNotNull(progressNote);
		Assert.assertNotNull(progressNote.getIEN());

		Assert.assertEquals("0", deleteProgressNote(progressNote.getIEN()));
	}

	@Test
	public void getConsultationUrgencyDataSetTest() throws Exception {
		ConsultationUrgencyDataSet consultationUrgencyDataSet = client.getConsultationUrgencyDataSet("C");
		Assert.assertNotNull(consultationUrgencyDataSet);
		Assert.assertTrue(consultationUrgencyDataSet.getConsultationUrgencyInfoList() > 0);
		Assert.assertTrue(consultationUrgencyDataSet.findOutpatientConsultationUrgencyByName("Routine").equalsIgnoreCase("Routine"));
		Assert.assertTrue(consultationUrgencyDataSet.findInpatientConsultationUrgencyByName("Routine").equalsIgnoreCase("Routine"));
		Assert.assertTrue(consultationUrgencyDataSet.findOutpatientConsultationLocationByName("Consultant's Choice").equalsIgnoreCase("Consultant's Choice"));
		Assert.assertTrue(consultationUrgencyDataSet.findInpatientConsultationLocationByName("Consultant's Choice").equalsIgnoreCase("Consultant's Choice"));
	}
	
	//@Test
	public void testSaveWHODASMha()
	{
	    client.saveMentalHealthAssessment(100049L, "WHODAS 2", "443202023444432023444432Y023444444444YYY");
	}

	private String deleteProgressNote(Long progressNoteIEN) throws VistaLinkFaultException, FoundationsException, ResourceException {
		System.out.println("Delete Progress Note - IEN: " + progressNoteIEN);
		VistaLinkConnection connection = getVistaLinkDuzConnection("500", "10000000056");
		RpcRequest request = createRpcRequest("OR CPRS GUI CHART", connection, true);
		List requestParams = new ArrayList();
		requestParams.add(progressNoteIEN); // IEN of progress note. Required

		request.setRpcName("TIU DELETE RECORD");
		request.clearParams();
		request.setParams(requestParams);

		RpcResponse response = connection.executeRPC(request);
		System.out.println("Delete Progress Note Response: " + response.getResults());
		request = null;
		connection = null;
		return response.getResults();
	}

	private String calculateVistaVisitString(String clinicLocationName,
			boolean inpatientStatus) throws Exception {
		Long clinicId = client.findLocation(clinicLocationName, "", true).getIen();
		VistaServiceCategoryEnum serviceCategoryEnum = client.findServiceCategory(VistaServiceCategoryEnum.AMBULATORY, clinicId, inpatientStatus);
		String vistaVisitDate = VistaUtils.convertToVistaDateString(new Date(), VistaDateFormat.MMddHHmmss);
		return clinicId + ";" + vistaVisitDate + ";" + serviceCategoryEnum.getCode();
	}

	private RpcRequest createRpcRequest(String rpcContext,
			VistaLinkConnection vistaLinkConnection,
			boolean useProprietaryMessageFormat) throws FoundationsException {
		RpcRequest request = RpcRequestFactory.getRpcRequest();
		request.setTimeOut(vistaLinkConnection.getTimeOut() * 2);
		request.setUseProprietaryMessageFormat(useProprietaryMessageFormat);
		request.setRpcContext(rpcContext);
		return request;
	}

	private VistaLinkConnection getVistaLinkDuzConnection(String division,
			String duz) throws ResourceException {
		// 1. Create the correct connection spec.
		VistaLinkConnectionSpec connSpec = new VistaLinkDuzConnectionSpec(division, duz);

		// 2. Non-managed mode:
		// In the future, 'vistaLinkConnectionFactory' will be returned by a
		// another method and not directly
		// instantiated. This class is for a specific PRIMARY STATION which is
		// determined by the user's DIVISION.
		VistaLinkConnectionFactory vistaLinkConnectionFactory = new VistaLinkConnectionFactory(vistaLinkManagedConnectionFactory, null);

		// 3. Get Connection
		VistaLinkConnection vistaLinkConnection = (VistaLinkConnection) vistaLinkConnectionFactory.getConnection(connSpec);
		vistaLinkConnection.setTimeOut(1000);
		return vistaLinkConnection;
	}
}
