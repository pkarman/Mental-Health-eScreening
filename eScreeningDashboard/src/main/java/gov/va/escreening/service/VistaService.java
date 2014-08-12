package gov.va.escreening.service;

import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.exception.VistaLinkException;
import gov.va.escreening.exception.VistaVerificationException;
import gov.va.escreening.vista.VistaLinkClientException;
import gov.va.escreening.vista.VistaRpcParam;
import gov.va.escreening.vista.VistaServiceContext;
import gov.va.escreening.vista.dto.DialogPrompt;
import gov.va.escreening.vista.dto.ProgressNoteParameters;
import gov.va.escreening.vista.dto.VistaBrokerUserInfo;
import gov.va.escreening.vista.dto.VistaClinicAppointment;
import gov.va.escreening.vista.dto.VistaClinicalReminderAndCategory;
import gov.va.escreening.vista.dto.VistaLocation;
import gov.va.escreening.vista.dto.VistaNoteTitle;
import gov.va.escreening.vista.dto.VistaProgressNote;
import gov.va.escreening.vista.dto.VistaVeteranAppointment;
import gov.va.escreening.vista.dto.VistaVeteranClinicalReminder;

import java.util.Date;
import java.util.List;

public interface VistaService {

    /**
     * Calls the RPC and returns the resulting string.
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
     * Issues a ping test on the VistA instance.
     * @param division
     * @param vpid
     * @param duz
     * @param appProxyName
     * @return
     */
    Boolean getPing(String division, String vpid, String duz, String appProxyName);

    /**
     * Searches for a veteran using lastName and ssnLastFour.
     * @param division
     * @param vpid
     * @param duz
     * @param appProxyName
     * @param lastName
     * @param ssnLastFour
     * @return
     */
    List<VeteranDto> searchVeteran(String division, String vpid, String duz, String appProxyName, String lastName,
            String ssnLastFour);

    /**
     * Retrieves certain demographics information of a veteran.
     * @param division
     * @param vpid
     * @param duz
     * @param appProxyName
     * @param veteranIen
     * @return
     */
    VeteranDto getVeteran(String division, String vpid, String duz, String appProxyName, String veteranIen);

    /**
     * Retrieves the appointments for a veteran.
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
     * Searches VistA for veterans using ssnLastFour and last name. Further filters the result if birthDate and
     * middleName are passed.
     * @param division
     * @param vpid
     * @param duz
     * @param appProxyName
     * @param ssnLastFour
     * @param lastName
     * @param birthDate
     * @param middleName
     * @return
     */
    List<VeteranDto> findVeterans(String division, String vpid, String duz, String appProxyName, String ssnLastFour,
            String lastName, Date birthDate, String middleName);

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
     * Retrieves all the note titles.
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
     * Retrieves all the dialogPrompts associated with 'dialogElementIen'.
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

    void saveHealthFactor(String locationIen, String clinicalNoteIen, String veteranIen, String visitLocationIen,
            Date visitDate, String parentVisitIen, String healthFactorIen, String healthFactorName);

    /**
     * Retrieves all the locations in ascending order.
     * @param division
     * @param vpid
     * @param duz
     * @param appProxyName
     * @return
     */
    List<VistaLocation> getLocationList(String division, String vpid, String duz, String appProxyName);

    /**
     * Saves the progress note to VistA.
     * 
     * @param vistaServiceContext Represents required the Vista context to save progress note to VistA.
     * @return Returns the VistaProgressNote Domain Object Model.
     * @throws VistaLinkClientException Throws exception if there is any problems saving progress note to VistA.
     */
    VistaProgressNote saveProgressNote(VistaServiceContext<ProgressNoteParameters> vistaServiceContext)
            throws VistaLinkClientException;

    /**
     * Logs user into VistA and return their DUZ and First, Middle, Last, and Suffix. Can throw
     * VistaVerificationException if access code and verify code or when user is not authorized for division or can
     * throw VistaLinkException for VistA Link connection related errors.
     * @param division The division user wants to log in to. Should be the primary station.
     * @param applicationName Should be the app name, for example 'eScreening'
     * @param accessCode user's Access Code
     * @param verifyCode user's Verify Code
     * @param clientIp Should the the IP address of the user's client.
     * @return
     */
    VistaBrokerUserInfo doVistaSignon(String division, String applicationName, String accessCode, String verifyCode,
            String clientIp);

    /**
     * Verifies the VistA Access/Verify code for user. Can throw
     * @throws VistaLinkException Connection to VistA server issues
     * @throws VistaVerificationException Issues with Access/Verify code or already mapped by another account
     * @param userId
     * @param accessCode
     * @param verifyCode
     * @param clientIp
     */
    void verifyVistaAccount(int userId, String accessCode, String verifyCode, String clientIp);

    /**
     * Imports the Clinic list from VistA and then updates the database
     * @param division
     * @param vpid
     * @param duz
     * @param appProxyName
     */
    int refreshClinicList(String division, String vpid, String duz, String appProxyName);
    
    /**
     * Imports the Clinical Reminder list from VistA and then updates the database
     * @param division
     * @param vpid
     * @param duz
     * @param appProxyName
     */
    int refreshClinicalReminderList(String division, String vpid, String duz, String appProxyName);

    /**
     * Imports the Note Title list from VistA and then updates the database
     * @param division
     * @param vpid
     * @param duz
     * @param appProxyName
     */
    int refreshNoteTitleList(String division, String vpid, String duz, String appProxyName);
    
    /**
     * Imports the Health Factor list from VistA and then updates the database
     * @param division
     * @param vpid
     * @param duz
     * @param appProxyName
     */
    int refreshHealthFactors(String division, String vpid, String duz, String appProxyName);

    public abstract int refreshMHAIens(String division, String vpid, String duz, String appProxyName);
}
