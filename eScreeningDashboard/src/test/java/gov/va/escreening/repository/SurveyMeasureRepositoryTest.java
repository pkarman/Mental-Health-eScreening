package gov.va.escreening.repository;

import static org.junit.Assert.*;
import gov.va.escreening.entity.SurveyMeasureResponse;

import java.util.List;

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
public class SurveyMeasureRepositoryTest {

	@Resource
	SurveyMeasureResponseRepository smrRepo;
	
	@Test
	public void testFindPast48Hr()
	{
		List<SurveyMeasureResponse> resultList = smrRepo.findLast48HourAnswersForVet(16);
		assertNotNull(resultList);
		assertTrue(!resultList.isEmpty());
	}
}
