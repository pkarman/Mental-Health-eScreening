package gov.va.escreening.controller;

import gov.va.escreening.delegate.VistaDelegate;
import gov.va.escreening.domain.MentalHealthAssessment;
import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.form.VistaTestFormBean;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.VeteranAssessmentService;
import gov.va.escreening.service.VistaService;
import gov.va.escreening.vista.DuzVistaLinkClientStrategy;
import gov.va.escreening.vista.VistaLinkClientException;
import gov.va.escreening.vista.VistaLinkClientStrategy;
import gov.va.escreening.vista.VistaRpcParam;
import gov.va.escreening.vista.VistaUtils;
import gov.va.escreening.vista.dto.DialogComponent;
import gov.va.escreening.vista.dto.DialogPrompt;
import gov.va.escreening.vista.dto.DialogPromptTypeEnum;
import gov.va.escreening.vista.dto.MentalHealthAssessmentResult;
import gov.va.escreening.vista.dto.PatientDemographics;
import gov.va.escreening.vista.dto.VistaBrokerUserInfo;
import gov.va.escreening.vista.dto.VistaClinicAppointment;
import gov.va.escreening.vista.dto.VistaClinicalReminderAndCategory;
import gov.va.escreening.vista.dto.VistaClinician;
import gov.va.escreening.vista.dto.VistaDateFormat;
import gov.va.escreening.vista.dto.VistaLocation;
import gov.va.escreening.vista.dto.VistaNoteTitle;
import gov.va.escreening.vista.dto.VistaProgressNote;
import gov.va.escreening.vista.dto.VistaServiceCategoryEnum;
import gov.va.escreening.vista.dto.VistaVeteranAppointment;
import gov.va.escreening.vista.dto.VistaVeteranClinicalReminder;
import gov.va.escreening.vista.extractor.DialogComponentListExtractor;
import gov.va.med.crypto.VistaKernelHashCountLimitExceededException;
import gov.va.med.vistalink.adapter.cci.VistaLinkDuzConnectionSpec;
import gov.va.med.vistalink.adapter.spi.VistaLinkManagedConnectionFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/dashboard")
public class VistaTestController {

    private static final Logger logger = LoggerFactory.getLogger(VistaTestController.class);

    @Autowired
    private VeteranAssessmentService veteranAssessmentService;
    @Resource(name="vistaService")
    private VistaService vistaService;
    @Autowired
    private VistaLinkManagedConnectionFactory vistaLinkManagedConnectionFactory;
    @Autowired
    private VistaDelegate vistaDelegate;

    private String appProxyName = "XOBVTESTER,APPLICATION PROXY";
    private String rpcContextOrCprs = "OR CPRS GUI CHART";
    private String rpcContextXobv = "XOBV VISTALINK TESTER";
    private String rpcContextXusSignon = "XUS SIGNON";

    @ModelAttribute
    public VistaTestFormBean getVistaTestFormBean() {

        VistaTestFormBean vistaTestFormBean = new VistaTestFormBean();
        vistaTestFormBean.setIen("100687");
        vistaTestFormBean.setLastFourSsn("0082");
        vistaTestFormBean.setLastName("Eightytwo");
        vistaTestFormBean.setSearchString("E0082");
        vistaTestFormBean.setSelectedRpcId("1");
        vistaTestFormBean.setClinicalReminderIen("500047");
        vistaTestFormBean.setDialogElementIen("500109");

        return vistaTestFormBean;
    }

    @ModelAttribute("rpcList")
    public Map<String, String> getRpcList() {

        Map<String, String> rpcList = new LinkedHashMap<String, String>();
        rpcList.put("1", "Ping");
        rpcList.put("2", "Search Veteran (last name, last 4 ssn");
        rpcList.put("3", "Get Veteran");
        rpcList.put("4", "Get Veteran Appointments");
        rpcList.put("5", "Search Veteran MUMPS (Search String)");
        rpcList.put("6", "Get Clinic List MUMPS");
        rpcList.put("7", "Get Veteran Clinical Reminder MUMPS");
        rpcList.put("8", "Get Veteran Appointments MUMPS");
        rpcList.put("9", "Get Veteran Clinical Reminder");
        rpcList.put("10", "Get Veteran Print Out MUMPS");
        rpcList.put("11", "Get Note Title List MUMPS");
        rpcList.put("12", "Get Note Title List");
        rpcList.put("13", "Get Appointment List for Clinic MUMPS");
        rpcList.put("14", "Get Appointment List for Clinic");
        rpcList.put("15", "Get Clinical Reminder and Category MUMPS");
        rpcList.put("16", "Get Clinical Reminder and Category");
        rpcList.put("17", "Get Clinical Reminder Dialog MUMPS");
        rpcList.put("18", "Get Clinical Reminder Dialog Element MUMPS");
        rpcList.put("19", "Get Text Object List MUMPS");
        rpcList.put("20", "Get Location List MUMPS");
        rpcList.put("21", "Get Health Factor for Clinical Reminder Dialog");
        rpcList.put("22", "Get Clinical Reminder Dialog Element");
        rpcList.put("23", "Encode/Decode");
        rpcList.put("24", "Verify");
        rpcList.put("25", "Get New Location List MUMPS");
        rpcList.put("26", "Get New Location List");
        rpcList.put("27", "Get Reminder Dialog Tree");
        rpcList.put("28", "Get Authors MUMPS");
        rpcList.put("29", "Ping MUMPS");
        rpcList.put("30", "Test VistA Broker");
        rpcList.put("31", "Create Progress Note");
        rpcList.put("32", "Save Health Factor to VistA");
        rpcList.put("33", "Test MHA");
        rpcList.put("34", "Test Veteran Demographic");
        return rpcList;
    }

    @RequestMapping(value = "/vista", method = RequestMethod.GET)
    public String setupForm(@ModelAttribute VistaTestFormBean vistaTestFormBean, Model model) {

        logger.debug("vistaTestPage called");
        return "vistaTestPage";
    }

    @RequestMapping(value = "/vista", method = RequestMethod.POST, params = "testRpcButton")
    public String testProcess(@ModelAttribute VistaTestFormBean vistaTestFormBean, Model model,
            @CurrentUser EscreenUser escreenUser) {

        logger.debug(vistaTestFormBean.getSelectedRpcId());

        String selectedRpcId = vistaTestFormBean.getSelectedRpcId();

        String result = "Nothing selected";

        if (selectedRpcId.equals("1")) {
            result = ping();
        }
        else if (selectedRpcId.equals("2")) {
            if (StringUtils.isBlank(vistaTestFormBean.getLastName())
                    || StringUtils.isBlank(vistaTestFormBean.getLastFourSsn())) {
                result = "Enter Last Name and Last Four SSN";
            }
            else {
                result = searchVeteran(vistaTestFormBean.getLastName(), vistaTestFormBean.getLastFourSsn());
            }
        }
        else if (selectedRpcId.equals("3")) {
            if (StringUtils.isBlank(vistaTestFormBean.getIen())) {
                result = "Enter the Veteran IEN value";
            }
            else {
                result = getVeteran(vistaTestFormBean.getIen());
            }
        }
        else if (selectedRpcId.equals("4")) {
            if (StringUtils.isBlank(vistaTestFormBean.getIen())) {
                result = "Enter the Veteran IEN value";
            }
            else {
                result = getAppointments(vistaTestFormBean.getIen());
            }
        }
        else if (selectedRpcId.equals("5")) {
            if (StringUtils.isBlank(vistaTestFormBean.getSearchString())) {
                result = "Enter Last Name initial and Last 4 SSN in Search String text box";
            }
            else {
                result = searchVeteranRaw(vistaTestFormBean.getSearchString());
            }
        }
        else if (selectedRpcId.equals("6")) {
            result = getClinicListRaw();
        }
        else if (selectedRpcId.equals("7")) {
            if (StringUtils.isBlank(vistaTestFormBean.getIen())) {
                result = "Enter the Veteran IEN value";
            }
            else {
                result = getClinicalReminderListRaw(vistaTestFormBean.getIen());
            }
        }
        else if (selectedRpcId.equals("8")) {
            if (StringUtils.isBlank(vistaTestFormBean.getIen())) {
                result = "Enter the Veteran IEN value";
            }
            else {
                result = getAppointmentListRaw(vistaTestFormBean.getIen());
            }
        }
        else if (selectedRpcId.equals("9")) {
            if (StringUtils.isBlank(vistaTestFormBean.getIen())) {
                result = "Enter the Veteran IEN value";
            }
            else {
                result = getClinicalReminderList(vistaTestFormBean.getIen());
            }
        }
        else if (selectedRpcId.equals("10")) {
            if (StringUtils.isBlank(vistaTestFormBean.getIen())) {
                result = "Enter the Veteran IEN value";
            }
            else {
                result = getVeteranPrintOutRaw(vistaTestFormBean.getIen());
            }
        }
        else if (selectedRpcId.equals("11")) {
            result = getNoteTitleListRaw();
        }
        else if (selectedRpcId.equals("12")) {
            result = getNoteTitleList();
        }
        else if (selectedRpcId.equals("13")) {
            result = getAppointmentListForClinicRaw();
        }
        else if (selectedRpcId.equals("14")) {
            String clinicIen = "32";

            DateTime fromAppointmentDate = new DateTime(2014, 1, 1, 0, 0);
            DateTime toAppointmentDate = new DateTime(2014, 3, 1, 0, 0);
            result = getAppointmentListForClinic(clinicIen, fromAppointmentDate.toDate(), toAppointmentDate.toDate());
        }
        else if (selectedRpcId.equals("15")) {
            result = getClinicalReminderAndCategoryListRaw();
        }
        else if (selectedRpcId.equals("16")) {
            result = getClinicalReminderAndCategoryList();
        }
        else if (selectedRpcId.equals("17")) {
            if (StringUtils.isBlank(vistaTestFormBean.getClinicalReminderIen())
                    || StringUtils.isBlank(vistaTestFormBean.getIen())) {

                result = "Enter the Veteran IEN value and Clincial Reminder IEN value";
            }
            else {
                result = getClinicalReminderDialogRaw(vistaTestFormBean.getClinicalReminderIen(),
                        vistaTestFormBean.getIen());
            }
        }
        else if (selectedRpcId.equals("18")) {
            if (StringUtils.isBlank(vistaTestFormBean.getDialogElementIen())) {
                result = "Enter Dialog Element IEN value";
            }
            else {
                result = getClinicalReminderDialogElementRaw(vistaTestFormBean.getDialogElementIen(), "0", "");
            }
        }
        else if (selectedRpcId.equals("19")) {
            result = getTextObjectListRaw();
        }
        else if (selectedRpcId.equals("20")) {
            result = getLocationListRaw();
        }
        else if (selectedRpcId.equals("21")) {
            if (StringUtils.isBlank(vistaTestFormBean.getClinicalReminderIen())
                    || StringUtils.isBlank(vistaTestFormBean.getIen())) {

                result = "Enter the Veteran IEN value and Clincial Reminder IEN value";
            }
            else {
                result = getHealthFactorList(vistaTestFormBean.getClinicalReminderIen(),
                        vistaTestFormBean.getIen());
            }
        }
        else if (selectedRpcId.equals("22")) {
            if (StringUtils.isBlank(vistaTestFormBean.getDialogElementIen())) {
                result = "Enter Dialog Element IEN value";
            }
            else {
                result = getClinicalReminderDialogElement(vistaTestFormBean.getDialogElementIen(), false, "");
            }
        }
        else if (selectedRpcId.equals("23")) {
            if (StringUtils.isBlank(vistaTestFormBean.getSearchString())) {
                result = "Enter a string to encode and decode";
            }
            else {
                String encodedData = encodeData(vistaTestFormBean.getSearchString());

                if (encodedData != null) {
                    String decodedData = decodeData(encodedData);

                    result = String.format("Original %s, Encoded %s, Decoded %s", vistaTestFormBean.getSearchString(),
                            encodedData, decodedData);
                }
                else {
                    return "Could not encode data.";
                }
            }
        }
        else if (selectedRpcId.equals("24")) {
            result = "";
            // result = "Set Context: " + verifyAccessStep1();
            result += "\n\nLog In: " + verifyAccessStep2();
            result += "\n\nGet User Info: " + verifyAccessStep3();
        }
        else if (selectedRpcId.equals("25")) {
            result = getNewLocationListRaw();
        }
        else if (selectedRpcId.equals("26")) {
            result = getNewLocationList();
        }
        else if (selectedRpcId.equals("27")) {
            if (StringUtils.isBlank(vistaTestFormBean.getClinicalReminderIen())
                    || StringUtils.isBlank(vistaTestFormBean.getIen())) {

                result = "Enter the Veteran IEN value and Clincial Reminder IEN value";
            }
            else {
                result = getReminderDialogTree(vistaTestFormBean.getClinicalReminderIen(), vistaTestFormBean.getIen());
            }
        }
        else if (selectedRpcId.equals("28")) {
            result = getAuthorsRaw(vistaTestFormBean.getSearchString());
        }
        else if (selectedRpcId.equals("29")) {
            result = pingRaw();
        }
        else if (selectedRpcId.equals("30")) {
            result = testVistaBroker();
        }
        else if (selectedRpcId.equals("31")) {
            result = testProgressNote();
        }
        else if (selectedRpcId.equals("32")) {
            result = testSaveHealthFactorToVista(escreenUser);
        }
        else if (selectedRpcId.equals("33")) {
            if (StringUtils.isNumeric(vistaTestFormBean.getSearchString())) {
                result = testMha(Integer.valueOf(vistaTestFormBean.getSearchString()), Long.parseLong(vistaTestFormBean.getIen()));
            }
            else {
                result = "Enter a Veteran Assessment ID in the Search String field.";
            }
        } else if (selectedRpcId.equals("34")) {
            result = retrieveVeteranDemographicTest(Long.parseLong(vistaTestFormBean.getIen().trim()));
        }

        logger.debug("testRpc: " + result);
        model.addAttribute("userMessage", result);

        return "vistaTestPage";
    }

    private String retrieveVeteranDemographicTest(Long patientIen) {
        String veteranDemographics = "";
        String appProxyName = "";
        String rpcContext = "OR CPRS GUI CHART";
        VistaLinkDuzConnectionSpec duzConnectionSpec = new VistaLinkDuzConnectionSpec("500", "10000000056");
        try {
            VistaLinkClientStrategy vistaLinkClientStrategy = new DuzVistaLinkClientStrategy(
                    vistaLinkManagedConnectionFactory, duzConnectionSpec, appProxyName, rpcContext);
            PatientDemographics patientDemographics = vistaLinkClientStrategy.getClient().findPatientDemographics(patientIen);
            veteranDemographics = patientDemographics.toString();
        }
        catch (VistaLinkClientException vlce) {
            logger.error(vlce.getMessage(), vlce);
            veteranDemographics = vlce.getMessage();
        }
        return veteranDemographics;
    }

    private String testProgressNote() {
        String testProgressNoteStatus = "failed";
        String appProxyName = "";
        String rpcContext = "OR CPRS GUI CHART";
        VistaLinkDuzConnectionSpec duzConnectionSpec = new VistaLinkDuzConnectionSpec("500", "10000000056");
        try {
            VistaLinkClientStrategy vistaLinkClientStrategy = new DuzVistaLinkClientStrategy(
                    vistaLinkManagedConnectionFactory, duzConnectionSpec, appProxyName, rpcContext);
            VistaNoteTitle progressNoteTitle = vistaLinkClientStrategy.getClient().findProgressNoteTitle("CRISIS NOTE");
            VistaClinician clinician = vistaLinkClientStrategy.getClient().findClinician("Forty-One", "Pharmacist",
                    null, true);

            Long clinicId = vistaLinkClientStrategy.getClient().findLocation("PRIMARY CARE", "", true).getIen();
            VistaServiceCategoryEnum serviceCategoryEnum = vistaLinkClientStrategy.getClient().findServiceCategory(
                    VistaServiceCategoryEnum.AMBULATORY,
                    clinicId, false);
            String vistaVisitDate = VistaUtils.convertToVistaDateString(new Date(), VistaDateFormat.MMddHHmmss);

            String visitString = clinicId + ";" + vistaVisitDate + ";" + serviceCategoryEnum.getCode(); // Required to
                                                                                                        // create
                                                                                                        // Progress
                                                                                                        // Note.
            Long patientIEN = 100003L; // Required to create Progress Note.
            Long titleIEN = progressNoteTitle.getNoteTitleIen(); // Required to create Progress Note.
            Date vistaVisitDateTime = null; // Optional when creating Progress Note.
            Long locationIEN = Long.parseLong(VistaUtils.getClinicIEN(visitString).trim()); // Optional when creating Progress Note.
            Long visitIEN = null; // Optional when creating Progress Note.
            Object[] identifiers = { // Required to create Progress Note.
                clinician.getIEN(), // Author IEN - Required to create Progress Note.
                VistaUtils.convertVistaDate(visitString), // Required to create Progress Note.
                locationIEN, // Required to create Progress Note.
                ""
            };
            boolean suppressCommitPostLogic = false; // Required to create Progress Note.
            boolean saveCrossReference = false; // Required to create Progress Note.
            String content = "The patient is in need of immediate intervention."; // Required to create Progress Note.
            boolean appendContent = true; // Required to create Progress Note.

            @SuppressWarnings("unused")
            VistaProgressNote progressNote = vistaLinkClientStrategy.getClient().saveProgressNote(patientIEN, titleIEN,
                    locationIEN, visitIEN,
                    vistaVisitDateTime, visitString, identifiers, content, suppressCommitPostLogic, saveCrossReference,
                    appendContent);

            testProgressNoteStatus = "successful";
        }
        catch (VistaLinkClientException vlce) {
            logger.error(vlce.getMessage(), vlce);
        }
        return testProgressNoteStatus;
    }

    public String ping() {

        Boolean result = vistaService.getPing("500", null, "10000000056", appProxyName);

        return "Ping: " + result;
    }

    public String searchVeteran(String lastName, String lastFourSsn) {

        String duz = "10000000056";

        List<VeteranDto> resultList = vistaService.searchVeteran("500", null, duz, appProxyName, lastName, lastFourSsn);

        return resultList.toString();
    }

    public String getVeteran(String veteranIen) {

        VeteranDto result = vistaService.getVeteran("500", null, "10000000056", appProxyName, veteranIen);

        if (result != null) {
            return result.toString();
        }
        else {
            return "Veteran not found: " + veteranIen;
        }
    }

    public String getAppointments(String veteranIen) {

        List<VistaVeteranAppointment> resultList = vistaService.getVeteranAppointments("500", null, "10000000056",
                appProxyName, veteranIen);

        if (resultList != null) {
            return resultList.toString();
        }
        else {
            return "NULL returned for appointment list for veteran: " + veteranIen;
        }
    }

    public String getClinicalReminderList(String veteranIen) {

        List<VistaVeteranClinicalReminder> resultList = vistaService.getVeteranClinicalReminders("500", null,
                "10000000056", appProxyName, veteranIen);

        if (resultList != null) {
            return resultList.toString();
        }
        else {
            return "NULL returned for clinical reminder list for veteran: " + veteranIen;
        }
    }

    public String getNoteTitleList() {

        List<VistaNoteTitle> resultList = vistaService.getNoteTitles("500", null, "10000000056",
                appProxyName);

        return resultList.toString();
    }

    public String searchVeteranRaw(String searchString) {

        logger.debug("vistaTestPage::rawRpcCall01 called");

        String vpid = null;
        String duz = null;
        String rpcName = "ORWPT LAST5";

        List<VistaRpcParam> vistaRpcParamList = new ArrayList<VistaRpcParam>();
        vistaRpcParamList.add(new VistaRpcParam("string", searchString));

        String result = vistaService.callRpc("500", vpid, duz, appProxyName, rpcContextOrCprs, rpcName,
                vistaRpcParamList);

        return result;
    }

    public String getClinicListRaw() {

        logger.debug("vistaTestPage::getClinicListRaw called");

        String vpid = null;
        String duz = "10000000056";
        String rpcName = "ORWU CLINLOC";

        String startWithName = "OBSERVATION";
        startWithName = ""; // to start from top of list.

        List<VistaRpcParam> vistaRpcParamList = new ArrayList<VistaRpcParam>();
        vistaRpcParamList.add(new VistaRpcParam("string", startWithName));
        vistaRpcParamList.add(new VistaRpcParam("string", "1")); // ASC vs DESC

        String result = vistaService.callRpc("500", vpid, duz, appProxyName, rpcContextOrCprs, rpcName,
                vistaRpcParamList);

        return result;
    }

    public String getClinicalReminderListRaw(String veteranIen) {

        logger.debug("vistaTestPage::getClinicReminderListRaw called");

        String vpid = null;
        String duz = "10000000056";
        String rpcName = "ORQQPX REMINDERS LIST";

        List<VistaRpcParam> vistaRpcParamList = new ArrayList<VistaRpcParam>();
        vistaRpcParamList.add(new VistaRpcParam("string", veteranIen));

        String result = vistaService.callRpc("500", vpid, duz, appProxyName, rpcContextOrCprs, rpcName,
                vistaRpcParamList);

        return result;
    }

    public String getAppointmentListRaw(String veteranIen) {

        logger.debug("vistaTestPage::getClinicReminderListRaw called");

        String vpid = null;
        String duz = "10000000056";
        String rpcName = "ORWPT APPTLST";

        List<VistaRpcParam> vistaRpcParamList = new ArrayList<VistaRpcParam>();
        vistaRpcParamList.add(new VistaRpcParam("string", veteranIen));

        String result = vistaService.callRpc("500", vpid, duz, appProxyName, rpcContextOrCprs, rpcName,
                vistaRpcParamList);

        return result;
    }

    public String getVeteranPrintOutRaw(String veteranIen) {

        logger.debug("vistaTestPage::getVeteranPrintOutRaw called");

        String vpid = null;
        String duz = "10000000056";
        String rpcName = "ORWPT PTINQ";

        List<VistaRpcParam> vistaRpcParamList = new ArrayList<VistaRpcParam>();
        vistaRpcParamList.add(new VistaRpcParam("string", veteranIen));

        String result = vistaService.callRpc("500", vpid, duz, appProxyName, rpcContextOrCprs, rpcName,
                vistaRpcParamList);

        return result;
    }

    public String getNoteTitleListRaw() {

        logger.debug("vistaTestPage::getNoteTitleListRaw called");

        String vpid = null;
        String duz = "10000000056";
        String rpcName = "TIU GET PN TITLES";

        List<VistaRpcParam> vistaRpcParamList = new ArrayList<VistaRpcParam>();

        String result = vistaService.callRpc("500", vpid, duz, appProxyName, rpcContextOrCprs, rpcName,
                vistaRpcParamList);

        return result;
    }

    public String getAppointmentListForClinicRaw() {

        logger.debug("vistaTestPage::getAppointmentListForClinicRaw called");

        String vpid = null;
        String duz = "10000000056";
        String rpcName = "ORQPT CLINIC PATIENTS";

        List<VistaRpcParam> vistaRpcParamList = new ArrayList<VistaRpcParam>();
        vistaRpcParamList.add(new VistaRpcParam("string", "32"));
        vistaRpcParamList.add(new VistaRpcParam("string", "1/1/14"));
        vistaRpcParamList.add(new VistaRpcParam("string", "3/1/14"));

        String result = vistaService.callRpc("500", vpid, duz, appProxyName, rpcContextOrCprs, rpcName,
                vistaRpcParamList);

        return result;
    }

    public String getAppointmentListForClinic(String clinicIen, Date fromAppointmentDate, Date toAppointmentDate) {

        String duz = "10000000056";

        List<VistaClinicAppointment> resultList = vistaService.getAppointmentsForClinic("500", null, duz, appProxyName,
                clinicIen, fromAppointmentDate, toAppointmentDate);

        if (resultList != null) {
            return resultList.toString();
        }
        else {
            return "NULL";
        }
    }

    public String getClinicalReminderAndCategoryListRaw() {

        logger.debug("vistaTestPage::getClinicalReminderAndCategoryListRaw called");

        String vpid = null;
        String duz = "10000000056";
        String rpcName = "PXRM REMINDERS AND CATEGORIES";

        List<VistaRpcParam> vistaRpcParamList = new ArrayList<VistaRpcParam>();

        String result = vistaService.callRpc("500", vpid, duz, appProxyName, rpcContextOrCprs, rpcName,
                vistaRpcParamList);

        return result;
    }

    public String getClinicalReminderAndCategoryList() {

        String duz = "10000000056";

        List<VistaClinicalReminderAndCategory> resultList = vistaService.getClinicalReminderAndCategories("500",
                null, duz, appProxyName);

        if (resultList != null) {
            return resultList.toString();
        }
        else {
            return "NULL";
        }
    }

    public String getClinicalReminderDialogRaw(String clinicalReminderIen, String veteranIen) {

        logger.debug("vistaTestPage::getClinicalReminderDialogRaw called");

        String vpid = null;
        String duz = "10000000056";
        String rpcName = "ORQQPXRM REMINDER DIALOG";

        List<VistaRpcParam> vistaRpcParamList = new ArrayList<VistaRpcParam>();
        vistaRpcParamList.add(new VistaRpcParam("string", clinicalReminderIen)); // dialog number
        vistaRpcParamList.add(new VistaRpcParam("string", veteranIen)); // patient number

        String result = vistaService.callRpc("500", vpid, duz, appProxyName, rpcContextOrCprs, rpcName,
                vistaRpcParamList);

        return result;
    }

    public String getClinicalReminderDialogElementRaw(String dialogElementIen, String isHistorical,
            String findingType) {

        logger.debug("vistaTestPage::getClinicalReminderDialogElementRaw called");

        String vpid = null;
        String duz = "10000000056";
        String rpcName = "ORQQPXRM DIALOG PROMPTS";

        List<VistaRpcParam> vistaRpcParamList = new ArrayList<VistaRpcParam>();
        vistaRpcParamList.add(new VistaRpcParam("string", dialogElementIen));
        vistaRpcParamList.add(new VistaRpcParam("string", isHistorical));
        vistaRpcParamList.add(new VistaRpcParam("string", findingType));

        String result = vistaService.callRpc("500", vpid, duz, appProxyName, rpcContextOrCprs, rpcName,
                vistaRpcParamList);

        return result;
    }

    public String getTextObjectListRaw() {

        logger.debug("vistaTestPage::getTextObjectListRaw called");

        String vpid = null;
        String duz = "10000000056";
        String rpcName = "TIU GET LIST OF OBJECTS";

        List<VistaRpcParam> vistaRpcParamList = new ArrayList<VistaRpcParam>();

        String result = vistaService.callRpc("500", vpid, duz, appProxyName, rpcContextOrCprs, rpcName,
                vistaRpcParamList);

        return result;
    }

    public String getLocationListRaw() {

        logger.debug("vistaTestPage::getLocationListRaw called");

        String vpid = null;
        String duz = "10000000056";
        String rpcName = "ORQQPX GET HIST LOCATIONS";

        List<VistaRpcParam> vistaRpcParamList = new ArrayList<VistaRpcParam>();

        String result = vistaService.callRpc("500", vpid, duz, appProxyName, rpcContextOrCprs, rpcName,
                vistaRpcParamList);

        return result;
    }

    public String getHealthFactorList(String clinicalReminderIen, String veteranIen) {

        String vpid = null;
        String duz = "10000000056";

        String data = getClinicalReminderDialogRaw(clinicalReminderIen, veteranIen);

        DialogComponentListExtractor dialogComponentListExtractor = new DialogComponentListExtractor();

        List<DialogComponent> dialogComponentList = dialogComponentListExtractor.extractData(data);

        for (DialogComponent dialogComponent : dialogComponentList) {
            List<DialogPrompt> dialogPromptList = vistaService.getDialogPrompt("500", vpid, duz, appProxyName,
                    dialogComponent.getDialogComponentIen(), false, "");

            if (dialogPromptList != null) {
                dialogComponent.setDialogPromptList(dialogPromptList);
            }
        }

        return dialogComponentList.toString();
    }

    public String getClinicalReminderDialogElement(String dialogElementIen, Boolean isHistorical,
            String findingType) {

        logger.debug("vistaTestPage::getClinicalReminderDialogElement called");

        String vpid = null;
        String duz = "10000000056";

        List<DialogPrompt> resultList = vistaService.getDialogPrompt("500", vpid, duz, appProxyName,
                dialogElementIen, isHistorical, findingType);

        if (resultList != null) {
            return resultList.toString();
        }
        else {
            return "NULL returned for dialog element ien " + dialogElementIen;
        }
    }

    public String encodeData(String data) {

        String result = null;

        try {
            result = gov.va.med.crypto.VistaKernelHash.encrypt(data, false);
        }
        catch (VistaKernelHashCountLimitExceededException e) {
            logger.error("Error encoding data: " + e);
        }

        return result;
    }

    public String decodeData(String data) {

        String result = null;

        result = gov.va.med.crypto.VistaKernelHash.decrypt(data);

        return result;
    }

    public String verifyAccessStep1() {
        logger.debug("vistaTestPage::verifyAccessStep1 called");

        String vpid = null;
        String duz = null;
        String rpcName = "XUS SIGNON SETUP";

        List<VistaRpcParam> vistaRpcParamList = new ArrayList<VistaRpcParam>();
        vistaRpcParamList.add(new VistaRpcParam("string", ""));
        vistaRpcParamList.add(new VistaRpcParam("string", ""));
        vistaRpcParamList.add(new VistaRpcParam("string", ""));

        String result = vistaService.callRpc("500", vpid, duz, appProxyName, rpcContextXusSignon, rpcName,
                vistaRpcParamList);

        return result;
    }

    public String verifyAccessStep2() {
        logger.debug("vistaTestPage::verifyAccessStep2 called");

        String vpid = null;
        String duz = null;
        String rpcName = "XUS AV CODE";

        String accessCodeVerifyCode = encodeData("1pharmacist;pharmacist1");

        List<VistaRpcParam> vistaRpcParamList = new ArrayList<VistaRpcParam>();
        vistaRpcParamList.add(new VistaRpcParam("string", accessCodeVerifyCode));

        String result = vistaService.callRpc("500", vpid, duz, appProxyName, rpcContextXusSignon, rpcName,
                vistaRpcParamList);

        return result;
    }

    public String verifyAccessStep3() {
        logger.debug("vistaTestPage::verifyAccessStep3 called");

        String vpid = null;
        String duz = null;
        String rpcName = "XUS GET USER INFO";

        List<VistaRpcParam> vistaRpcParamList = new ArrayList<VistaRpcParam>();

        String result = vistaService.callRpc("500", vpid, duz, appProxyName, rpcContextXusSignon, rpcName,
                vistaRpcParamList);

        return result;
    }

    public String getNewLocationListRaw() {

        logger.debug("vistaTestPage::getNewLocationListRaw called");

        String vpid = null;
        String duz = "10000000056";
        String rpcName = "ORWU1 NEWLOC";

        String startWithName = "OBSERVATION";
        startWithName = ""; // to start from top of list.

        List<VistaRpcParam> vistaRpcParamList = new ArrayList<VistaRpcParam>();
        vistaRpcParamList.add(new VistaRpcParam("string", startWithName));
        vistaRpcParamList.add(new VistaRpcParam("string", "1")); // ASC vs DESC

        String result = vistaService.callRpc("500", vpid, duz, appProxyName, rpcContextOrCprs, rpcName,
                vistaRpcParamList);

        return result;
    }

    public String getNewLocationList() {

        logger.debug("vistaTestPage::getNewLocationList called");

        String division = "500";
        String vpid = null;
        String duz = "10000000056";

        List<VistaLocation> resultList = vistaService.getLocationList(division, vpid, duz, appProxyName);

        return resultList.toString();
    }

    public String getReminderDialogTree(String clinicalReminderIen, String veteranIen) {

        String vpid = null;
        String duz = "10000000056";

        // Get the root and level 1 nodes.
        String data = getClinicalReminderDialogRaw(clinicalReminderIen, veteranIen);

        DialogComponentListExtractor dialogComponentListExtractor = new DialogComponentListExtractor();

        // Deserialize the dialog components.
        List<DialogComponent> dialogComponentList = dialogComponentListExtractor.extractData(data);

        if (dialogComponentList == null) {
            return data;
        }

        // Get the prompts (health factors) associated with each components, if any.
        for (DialogComponent dialogComponent : dialogComponentList) {
            List<DialogPrompt> dialogPromptList = vistaService.getDialogPrompt("500", vpid, duz, appProxyName,
                    dialogComponent.getDialogComponentIen(), false, "");

            if (dialogPromptList != null) {
                dialogComponent.setDialogPromptList(dialogPromptList);
            }
        }

        // Create a reminder dialog dialog tree.
        Hashtable<String, DialogComponent> ht = new Hashtable<String, DialogComponent>();
        List<DialogComponent> orderedList = new ArrayList<DialogComponent>();

        for (DialogComponent dialogComponent : dialogComponentList) {

            int pos = dialogComponent.getSequenceString().lastIndexOf(".");

            // Assume root element.
            if (pos < 0) {
                orderedList.add(dialogComponent);
                ht.put(dialogComponent.getSequenceString(), dialogComponent);
            }
            else {
                String parentId = dialogComponent.getSequenceString().substring(0, pos);

                // Another root element.
                if (!ht.containsKey(parentId)) {
                    orderedList.add(dialogComponent);
                }
                else {
                    DialogComponent parent = ht.get(parentId);

                    if (parent.getChildDialogComponents() == null) {
                        parent.setChildDialogComponents(new ArrayList<DialogComponent>());
                    }

                    parent.getChildDialogComponents().add(dialogComponent);
                }

                ht.put(dialogComponent.getSequenceString(), dialogComponent);
            }
        }

        // Now generate a print friendly string using the hierarchy.
        String msg = "";
        for (DialogComponent dialogComponent : orderedList) {
            msg += '\n' + getRecordString(dialogComponent, 0);
        }

        return msg;
    }

    /**
     * Returns a string representation of the dialog tree.
     * @param dialogComponent
     * @param indent
     * @return
     */
    public String getRecordString(DialogComponent dialogComponent, int indent) {

        String s = String.format("%s %s %s %s",
                StringUtils.repeat(" ", indent),
                dialogComponent.getInputType(),
                dialogComponent.getDoneElsewhereHistorical() ? "H" : "C",
                dialogComponent.getDisplayText());

        // Concatenate the Health Factors, etc. for this dialog component
        if (dialogComponent.getDialogPromptList() != null) {
            for (DialogPrompt dialogPrompt : dialogComponent.getDialogPromptList()) {

                if (dialogPrompt.getDialogPromptType() == DialogPromptTypeEnum.PROGRESS_NOTE_TEXT) {
                    s += String.format("\n%s  %s%s %s",
                            StringUtils.repeat(" ", indent),
                            "-",
                            dialogPrompt.getDialogPromptType(),
                            dialogPrompt.getProgressNoteText());
                }
                else {
                    s += String.format("\n%s  %s%s %s %s",
                            StringUtils.repeat(" ", indent),
                            "-",
                            dialogPrompt.getDialogPromptType(),
                            dialogPrompt.getFindingTypeCode(),
                            dialogPrompt.getDisplayText());
                }
            }
        }

        if (dialogComponent.getChildDialogComponents() != null) {
            indent += 3;

            for (DialogComponent child : dialogComponent.getChildDialogComponents()) {
                s += '\n' + getRecordString(child, indent);
            }
        }

        return s;
    }

    public String getAuthorsRaw(String startWithName) {

        logger.debug("vistaTestPage::getAuthorsRaw called");

        String vpid = null;
        String duz = "10000000056";
        String rpcName = "ORWU NEWPERS";

        List<VistaRpcParam> vistaRpcParamList = new ArrayList<VistaRpcParam>();
        vistaRpcParamList.add(new VistaRpcParam("string", startWithName));
        vistaRpcParamList.add(new VistaRpcParam("string", "1")); // ASC vs DESC

        String result = vistaService.callRpc("500", vpid, duz, appProxyName, rpcContextOrCprs, rpcName,
                vistaRpcParamList);

        return result;
    }

    public String pingRaw() {

        String vpid = null;
        String duz = "10000000056";
        String rpcName = "XOBV TEST PING";

        String result = vistaService.callRpc("500", vpid, duz, appProxyName, rpcContextXobv, rpcName,
                new ArrayList<VistaRpcParam>());

        return "Ping: " + result;
    }

    public String testRaw() {

        String vpid = null;
        String duz = "10000000056";
        String rpcName = "XOBV TEST PING";

        String result = vistaService.callRpc("500", vpid, duz, appProxyName, rpcContextXobv, rpcName,
                new ArrayList<VistaRpcParam>());

        return "Ping: " + result;
    }

    public String testVistaBroker() {

        String division = "500";
        String applicationName = "eScreening";
        String accessCode = "1Radiologist";
        String verifyCode = "Radiologist1";
        String clientIp = "192.168.10.108";

        VistaBrokerUserInfo result = vistaService.doVistaSignon(division, applicationName, accessCode, verifyCode,
                clientIp);

        return "Test VistA Broker: \n" + result;
    }

    public String testSaveHealthFactorToVista(EscreenUser escreenUser) {

        int veteranAssessmentId = 55;

        try {
            vistaDelegate.saveVeteranAssessmentToVista(veteranAssessmentId, escreenUser);
        }
        catch (Exception e) {
            return "Exception calling testSaveHealthFactorToVista: " + e.toString();
        }

        return "Returning from testSaveHealthFactorToVista";
    }

    public String testMha(int veteranAssessmentId, Long veteranIen) {
        String appProxyName = "";
        String rpcContext = "OR CPRS GUI CHART";
        String s = "";
        VistaLinkDuzConnectionSpec duzConnectionSpec = new VistaLinkDuzConnectionSpec("500", "10000000056");
        try {
            VistaLinkClientStrategy vistaLinkClientStrategy = new DuzVistaLinkClientStrategy(
                    vistaLinkManagedConnectionFactory, duzConnectionSpec, appProxyName, rpcContext);

            VeteranAssessment veteranAssessment = veteranAssessmentService.findByVeteranAssessmentId(veteranAssessmentId);
            List<MentalHealthAssessment> results = veteranAssessmentService.getMentalHealthAssessments(veteranAssessmentId);
            MentalHealthAssessmentResult mentalHealthAssessmentResults = null;

            if (results != null) {
                for (MentalHealthAssessment mha : results) {
                    s += String.format("%s %s %s %s\n", mha.getSurveyId(), mha.getReminderDialogIEN(),
                            mha.getMentalHealthTestName(), mha.getMentalHealthTestAnswers());

                    if (vistaLinkClientStrategy.getClient().saveMentalHealthAssessment(veteranIen,
                            mha.getMentalHealthTestName(), mha.getMentalHealthTestAnswers())) {

                        mentalHealthAssessmentResults =  vistaLinkClientStrategy.getClient()
                                .getMentalHealthAssessmentResults(mha.getReminderDialogIEN(), veteranIen,
                                        mha.getMentalHealthTestName(), "T",
                                        veteranAssessment.getClinician().getVistaDuz(),
                                        mha.getMentalHealthTestAnswers());

                        vistaLinkClientStrategy.getClient().saveMentalHealthPackage(veteranIen,
                                mha.getMentalHealthTestName(), new Date(), veteranAssessment.getClinician().getVistaDuz(),
                                mha.getMentalHealthTestAnswers());

                        s += "MHA Score: " + mentalHealthAssessmentResults.getMentalHealthAssessmentResultDescription() + "\n";
                    }
                }
            }

            return s;

        } catch (Exception e) {
            return "Exception calling testSaveHealthFactorToVista: " + e.toString();
        }
    }
}
