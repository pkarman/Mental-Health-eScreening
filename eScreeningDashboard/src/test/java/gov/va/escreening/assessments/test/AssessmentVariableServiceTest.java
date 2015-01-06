package gov.va.escreening.assessments.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.va.escreening.service.VeteranAssessmentService;

import java.util.Date;
import java.util.Map;

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
		Map<Date, String> timeSeries = vaSvc.getVeteranAssessmentVariableSeries(TEST_VET_ID, 11, 3);
		assertNotNull(timeSeries);
		assertEquals(1, timeSeries.size());
		
	}
}
