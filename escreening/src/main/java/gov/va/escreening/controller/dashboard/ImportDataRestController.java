package gov.va.escreening.controller.dashboard;

import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.VistaService;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/dashboard")
public class ImportDataRestController {

    private static final Logger logger = LoggerFactory.getLogger(ImportDataRestController.class);

    @Resource(name = "vistaService")
    private VistaService vistaService;

    @RequestMapping(value = "/importData/clinics/refresh", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, String> importClinicListFromVista(@CurrentUser EscreenUser escreenUser) {

        logger.trace("In importClinicListFromVista");

        HashMap<String, String> result = new HashMap<String, String>();
        boolean hasError = false;
        int updateCnt = 0;
        int insertCnt = 0;
        try {
            Map<String, Integer> refreshResponseMap = vistaService.refreshClinicList(escreenUser.getVistaDivision(), escreenUser.getVistaVpid(),
                    escreenUser.getVistaDuz(), null);
            updateCnt = refreshResponseMap.get("updateCnt");
            insertCnt = refreshResponseMap.get("insertCnt");
        } catch (Exception e) {
            hasError = true;
            logger.error("Failed to import Clinic list from VistA: ", e);
        }

        if (hasError) {
            result.put("hasError", "true");
            result.put("userMessage", "An unexpected error occured while trying to import Clinic list from VistA.");
        } else {
            result.put("hasError", "false");
            result.put("userMessage",
                    String.format("Successfully processed Clinic list from VistA. Changed %s records. Added %s records", updateCnt, insertCnt));
        }

        return result;
    }

    @RequestMapping(value = "/importData/clinicalReminders/refresh", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, String> importClinicalNoteListFromVista(@CurrentUser EscreenUser escreenUser) {

        logger.trace("In importClinicalNoteListFromVista");

        HashMap<String, String> result = new HashMap<String, String>();
        int count = 0;
        boolean hasError = false;

        try {
            count = vistaService.refreshClinicalReminderList(escreenUser.getVistaDivision(),
                    escreenUser.getVistaVpid(), escreenUser.getVistaDuz(), null);
        } catch (Exception e) {
            hasError = true;
            logger.error("Failed to import Clinical Reminder list from VistA: " + e);
        }

        if (hasError) {
            result.put("hasError", "true");
            result.put("userMessage",
                    "An unexpected error occured while trying to import Clinical Reminder list from VistA.");
        } else {
            result.put("hasError", "false");
            result.put("userMessage",
                    String.format("Successfully processed Clinical Reminder list from VistA. Imported %s records.",
                            count));
        }

        return result;
    }

    @RequestMapping(value = "/importData/noteTitles/refresh", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, String> importNoteTitleListFromVista(@CurrentUser EscreenUser escreenUser) {

        logger.trace("In importNoteTitleListFromVista");

        HashMap<String, String> result = new HashMap<String, String>();
        int count = 0;
        boolean hasError = false;

        try {
            count = vistaService.refreshNoteTitleList(escreenUser.getVistaDivision(), escreenUser.getVistaVpid(),
                    escreenUser.getVistaDuz(), null);
        } catch (Exception e) {
            hasError = true;
            logger.error("Failed to import Note Title list from VistA: " + e);
        }

        if (hasError) {
            result.put("hasError", "true");
            result.put("userMessage", "An unexpected error occured while trying to import Note Title list from VistA.");
        } else {
            result.put("hasError", "false");
            result.put("userMessage",
                    String.format("Successfully processed Note Title list from VistA. Imported %s records.", count));
        }

        return result;
    }

    @RequestMapping(value = "/importData/hf/refresh", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, String> importHealthFactorsFromVista(@CurrentUser EscreenUser escreenUser) {

        logger.trace("In Health Factors Vista");

        HashMap<String, String> result = new HashMap<String, String>();
        int count = 0;
        boolean hasError = false;

        try {
            count = vistaService.refreshHealthFactors(escreenUser.getVistaDivision(), escreenUser.getVistaVpid(),
                    escreenUser.getVistaDuz(), null);
        } catch (Exception e) {
            hasError = true;
            logger.error("Failed to import Health Factors from VistA: " + e);
        }

        if (hasError) {
            result.put("hasError", "true");
            result.put("userMessage", "An unexpected error occured while trying to import Health Factors from VistA.");
        } else {
            result.put("hasError", "false");
            result.put("userMessage",
                    String.format("Successfully processed Health Factors from VistA. Imported %s records.", count));
        }

        return result;
    }

}
