package gov.va.escreening.repository;

import static org.junit.Assert.*;

import java.util.List;

import gov.va.escreening.entity.ClinicalReminderSurvey;
import gov.va.escreening.entity.Survey;

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
public class ClinicalReminderSurveyRepoTest {
	@Resource
	ClinicalReminderSurveyRepository crsRepo;
	
	@Test
	public void testDelete()
	{
		List<ClinicalReminderSurvey> list1 = crsRepo.findAllByVistaIen("568022");
		int origSize = list1.size();
		
		Survey s = list1.get(0).getSurvey();
		 crsRepo.removeSurveyMapping(s.getSurveyId());
		 
		 List<ClinicalReminderSurvey>  list2=crsRepo.findAllByVistaIen("568022");
		 
		 assertTrue("One less", list2.size() == origSize-1);
		
	}

	@Test
	public void testCreate()
	{
		List<ClinicalReminderSurvey> list1 = crsRepo.findAllByVistaIen("568022");
		int origSize = list1.size();
	
		crsRepo.createClinicalReminderSurvey(52, 2);
		List<ClinicalReminderSurvey>  list2=crsRepo.findAllByVistaIen("568022");
		 
		assertTrue("One less", list2.size() == origSize+1);
		
	}
}
