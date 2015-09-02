package gov.va.escreening.service;

import com.google.common.base.Throwables;
import gov.va.escreening.controller.dashboard.EditVeteranAssessmentController;
import gov.va.escreening.entity.ClinicalReminder;
import gov.va.escreening.entity.User;
import gov.va.escreening.repository.ClinicRepository;
import gov.va.escreening.repository.ClinicalReminderRepository;
import gov.va.escreening.repository.UserRepository;
import gov.va.escreening.security.EscreenUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.AssertionErrors;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional
//this is to ensure all tests do not leave trace, so they are repeatable.
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class ClinicalReminderServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(ClinicalReminderServiceTest.class);
    @Resource
    ClinicalReminderRepository clinicalReminderRepository;
    @Resource(name = "editVeteranAssessmentController")
    EditVeteranAssessmentController editVeteranAssessmentController;
    @Resource(name = "vistaService")
    private VistaService vistaService;
    @Resource
    private UserRepository userRepository;

    @Test
    public void testAllowedClinicalReminders() {
        Map<String, ClinicalReminder> crMap = clinicalReminderRepository.deriveAllowedClinicalReminders();
        AssertionErrors.assertTrue("The Clinical Reminder List cannot be empty or null", crMap != null && !crMap.isEmpty());
    }

    @Test
    public void importClinicalNoteListFromVista() {
        List<User> clinicianIds = userRepository.findAll();
        for (User u : clinicianIds) {
            try {
                EscreenUser escreenUser = editVeteranAssessmentController.findEscreenUser(u.getUserId());
                int count = vistaService.refreshClinicalReminderList(escreenUser.getVistaDivision(),
                        escreenUser.getVistaVpid(), escreenUser.getVistaDuz(), null);
                AssertionErrors.assertTrue(String.format("The clinic reminder canot be zero for any user %s", u.getUserId()), count != 0);
            } catch (UsernameNotFoundException unfe) {
                logger.warn(Throwables.getRootCause(unfe).getMessage());
            }
        }
    }

}
