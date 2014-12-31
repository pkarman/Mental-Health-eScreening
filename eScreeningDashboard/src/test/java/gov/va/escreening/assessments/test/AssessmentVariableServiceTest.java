package gov.va.escreening.assessments.test;

import static org.junit.Assert.*;

import java.util.Calendar;

import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.service.AssessmentVariableService;
import gov.va.escreening.service.VeteranAssessmentService;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
//this is to ensure all tests do not leave trace, so they are repeatable.
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class AssessmentVariableServiceTest extends AssessmentTestBase
{
	@Resource
	VeteranAssessmentService vaSvc;
	
	@Test
	public void testTimeSeries()
	{
		VeteranAssessment a1 = createTestAssessment();
		Calendar c1 = Calendar.getInstance();
		c1.add(Calendar.MONTH, -1);
		vetAssessmentRepo.create(a1);
		
		a1.setDateCompleted(c1.getTime());
		
		VeteranAssessment a2 = createTestAssessment();
		a2.setDateCompleted(Calendar.getInstance().getTime());
		vetAssessmentRepo.create(a2);
		
		assertNotNull(vaSvc.getVeteranAssessmentVariableSeries(TEST_VET_ID, 1722, 3));
	}
}
