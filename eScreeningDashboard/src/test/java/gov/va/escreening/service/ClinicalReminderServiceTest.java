package gov.va.escreening.service;

import gov.va.escreening.entity.ClinicalReminder;
import gov.va.escreening.repository.ClinicalReminderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    @Resource
    ClinicalReminderRepository clinicalReminderRepository;

    @Test
    public void testAllowedClinicalReminders() {
        Map<String, ClinicalReminder> crMap = clinicalReminderRepository.deriveAllowedClinicalReminders();
        AssertionErrors.assertTrue("The Clinical Reminder List cannot be empty or null", crMap != null && !crMap.isEmpty());
    }

}
