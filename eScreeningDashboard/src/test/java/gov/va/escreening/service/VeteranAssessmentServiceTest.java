package gov.va.escreening.service;

import java.util.ArrayList;

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
public class VeteranAssessmentServiceTest {
	@Resource
	VeteranAssessmentService vetAssessmentSvc;
	
	@Test(expected=AssessmentAlreadyExistException.class)
	public void testAssessmentAlreadyExistException() throws AssessmentAlreadyExistException
	{
		vetAssessmentSvc.create(3, 2, 9, 5, 1, 1, 2, new ArrayList<Integer>());
	}

}
