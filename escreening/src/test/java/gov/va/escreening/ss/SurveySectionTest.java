package gov.va.escreening.ss;

import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.SurveySection;
import gov.va.escreening.repository.SurveyRepository;
import gov.va.escreening.repository.SurveySectionRepository;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class SurveySectionTest {

	@Resource
	SurveySectionRepository ssr;

	@Resource
	SurveyRepository sr;

	@Test
	public void doNothing(){}
	
	//@Test
	@Transactional
	@Rollback(value = false)
	public void testSSDelete() {
		List<SurveySection> ssrList = ssr.getSurveySectionList();
		int ssSize = ssrList.size();
		Iterator<SurveySection> ssIter = ssrList.iterator();
		SurveySection ss = ssIter.next();
		List<Survey> sList = ss.getSurveyList();
		int surveys = sList.size();

		Survey firstSurvey = sList.iterator().next();
		Integer ssId = firstSurvey.getSurveySection().getSurveySectionId();
		ssIter.remove();
		int ssNewSize = ssrList.size();
		ssr.delete(ss);
		List<SurveySection> ssrList1 = ssr.getSurveySectionList();
		int ssSize1 = ssrList.size();

		Survey oldS = sr.findOne(firstSurvey.getSurveyId());
		SurveySection oldSS = oldS.getSurveySection();

		int i = 0;
	}
}
