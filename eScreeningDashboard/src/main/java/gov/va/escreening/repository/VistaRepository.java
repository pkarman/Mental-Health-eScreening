package gov.va.escreening.repository;

import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.vista.VistaRpcParam;
import gov.va.escreening.vista.dto.DialogComponent;
import gov.va.escreening.vista.dto.DialogPrompt;
import gov.va.escreening.vista.dto.VistaBrokerUserInfo;
import gov.va.escreening.vista.dto.VistaClinicAppointment;
import gov.va.escreening.vista.dto.VistaClinicalReminderAndCategory;
import gov.va.escreening.vista.dto.VistaLocation;
import gov.va.escreening.vista.dto.VistaNoteTitle;
import gov.va.escreening.vista.dto.VistaVeteranAppointment;
import gov.va.escreening.vista.dto.VistaVeteranClinicalReminder;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface VistaRepository {

    /**
     * Generic method to call an RPC and return the resulting string.
     * @param division
     * @param vpid
     * @param duz
     * @param appProxyName
     * @param rpcContext
     * @param rpcName
     * @param vistaRpcParamList
     * @return
     */
    String callRpc(String division, String vpid, String duz, String appProxyName, String rpcContext, String rpcName,
            List<VistaRpcParam> vistaRpcParamList);

    /**
     * Pings the VistA server
     * @param division
     * @param vpid
     * @param duz
     * @param appProxyName
     * @return
     */
    Boolean getPing(String division, String vpid, String duz, String appProxyName);

    /**
     * Searches for veteran using last name initial and last four ssn string.
     * @param division
     * @param vpid
     * @param duz
     * @param appProxyName
     * @param lastNameSsn
     * @return
     */
    List<VeteranDto> searchVeteran(String division, String vpid, String duz, String appProxyName, String lastNameSsn);

    /**
     * Returns the veteran information.
     * @param division
     * @param vpid
     * @param duz
     * @param appProxyName
     * @param veteranIen
     * @return
     */
    VeteranDto getVeteran(String division, String vpid, String duz, String appProxyName, String veteranIen);

    /**
     * Retrieves the print screen of the veteran information. This is not in a record format.
     * @param division
     * @param vpid
     * @param duz
     * @param appProxyName
     * @param veteranIen
     * @return
     */
    VeteranDto getVeteranDetail(String division, String vpid, String duz, String appProxyName, String veteranIen);

    /**
     * Retrieves the list of appointments for a veteran.
     * @param division
     * @param vpid
     * @param duz
     * @param appProxyName
     * @param veteranIen
     * @return
     */
    List<VistaVeteranAppointment> getVeteranAppointments(String division, String vpid, String duz, String appProxyName,
            String veteranIen);

    /**
     * Retrieves the clinical reminders for a veteran.
     * @param division
     * @param vpid
     * @param duz
     * @param appProxyName
     * @param veteranIen
     * @return
     */
    List<VistaVeteranClinicalReminder> getVeteranClinicalReminders(String division, String vpid, String duz,
            String appProxyName, String veteranIen);

    /**
     * Retrieves the clinical note titles.
     * @param division
     * @param vpid
     * @param duz
     * @param appProxyName
     * @return
     */
    List<VistaNoteTitle> getNoteTitles(String division, String vpid, String duz, String appProxyName);

    /**
     * Retrieves the appointments for the clinic.
     * @param division
     * @param vpid
     * @param duz
     * @param appProxyName
     * @param clinicIen
     * @param fromAppointmentDate
     * @param toAppointmentDate
     * @return
     */
    List<VistaClinicAppointment> getAppointmentsForClinic(String division, String vpid, String duz,
            String appProxyName, String clinicIen, Date fromAppointmentDate, Date toAppointmentDate);

    /**
     * Retrieves all the entries associated with 'dialogElementIen'.
     * @param division
     * @param vpid
     * @param duz
     * @param appProxyName
     * @param dialogElementIen
     * @param isHistorical
     * @param findingType
     * @return
     */
    List<DialogPrompt> getDialogPrompt(String division, String vpid, String duz, String appProxyName,
            String dialogElementIen, Boolean isHistorical, String findingType);

    /**
     * Returns the clinical reminder and clinical category list from VistA.
     * @param division
     * @param vpid
     * @param duz
     * @param appProxyName
     * @return
     */
    List<VistaClinicalReminderAndCategory> getClinicalReminderAndCategories(String division, String vpid,
            String duz, String appProxyName);

    /**
     * Returns a page of Locations starting after 'locationNameStartRow'. If 'locationNameStartRow' is numm or empty,
     * then returns Locations from the beginning of the list.
     * @param division
     * @param vpid
     * @param duz
     * @param appProxyName
     * @param locationNameStartRow
     * @param ascendingOrder
     * @return
     */
    List<VistaLocation> getLocationList(String division, String vpid, String duz, String appProxyName,
            String locationNameStartRow, Boolean ascendingOrder);

    /**
     * Returns the Service Category code that should be used.
     * @param division
     * @param vpid
     * @param duz
     * @param appProxyName
     * @param initialServiceConnectionCategory
     * @param locationIen
     * @param isInPatient
     * @return
     */
    String getServiceCategory(String division, String vpid, String duz, String appProxyName,
            String initialServiceConnectionCategory, String locationIen, Boolean isInPatient);

    /**
     * Logs user into VistA and return their DUZ and First, Middle, Last, and Suffix. Can throw
     * VistaVerificationException if access code and verify code or when user is not authorized for division or can
     * throw VistaLinkException for VistA Link connection related errors.
     * @param division
     * @param applicationName Should be the app name, for example 'eScreening'
     * @param accessCode
     * @param verifyCode
     * @param clientIp Should the the IP address of the user's client.
     * @return
     */
    VistaBrokerUserInfo doVistaSignon(String division, String applicationName, String accessCode, String verifyCode,
            String clientIp);

    public abstract List<DialogComponent> getClinicalReminderDialogs(String division, String vpid, String duz, String appProxyName, String clinicalReminderIEN);

    Map<String, String> getHealthFactorIENMap(String division, String vpid, String duz, String appProxyName,
            String componentIen);

    public abstract String[] getDialogPromptsAsString(String division, String vpid, String duz, String appProxyName, String componentIen);

    public abstract String getMHATestDetail(String division, String vpid, String duz, String appProxyName, String MHATestName);

}
