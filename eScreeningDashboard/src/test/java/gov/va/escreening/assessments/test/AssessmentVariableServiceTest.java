package gov.va.escreening.assessments.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import gov.va.escreening.delegate.AssessmentDelegate;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.service.VeteranAssessmentService;

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

    @Resource(type=AssessmentDelegate.class)
    AssessmentDelegate ad;

	@Test
	public void testTimeSeries()
	{
		Map<String, String> timeSeries = vaSvc.getVeteranAssessmentVariableSeries(TEST_VET_ID, 11, 3);
		assertNotNull(timeSeries);
		assertEquals(1, timeSeries.size());
	}
	
	@Test
    public void testVeteran18_PainScoreTimeSeries(){
        Map<String, String> timeSeries = vaSvc.getVeteranAssessmentVariableSeries(18, 2300, 12);
        assertNotNull(timeSeries);
        assertEquals(1, timeSeries.size());
    }
	
   @Test
    public void testVeteran18_PHQ9TimeSeries(){
        Map<String, String> timeSeries = vaSvc.getVeteranAssessmentVariableSeries(18, 1599, 12);
        assertNotNull(timeSeries);
        assertEquals(1, timeSeries.size());
    }
	
	@Test
    public void testVeteran18_PTSDTimeSeries(){
        Map<String, String> timeSeries = vaSvc.getVeteranAssessmentVariableSeries(18, 1929, 12);
        assertNotNull(timeSeries);
        assertEquals(1, timeSeries.size());
    }
	
	@Test
    public void testVeteran16_PHQ9TimeSeries(){
        Map<String, String> timeSeries = vaSvc.getVeteranAssessmentVariableSeries(16, 1599, 12);
        assertNotNull(timeSeries);
        assertEquals(3, timeSeries.size());
    }

    @Test
    public void testRecordAllReportableScores(){
        final VeteranAssessment testAssessment = vaSvc.findByVeteranAssessmentId(18);
        ad.recordAllReportableScores(testAssessment);
    }
}
