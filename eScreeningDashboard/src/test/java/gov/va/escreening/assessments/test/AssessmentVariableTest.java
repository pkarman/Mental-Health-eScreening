package gov.va.escreening.assessments.test;

import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.repository.AssessmentVariableRepository;

import java.util.Collection;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

@Transactional
// this is to ensure all tests do not leave trace, so they are repeatable.
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class AssessmentVariableTest extends AssessmentTestBase {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Resource(type = AssessmentVariableRepository.class)
	AssessmentVariableRepository avr;

	@Test
	public void synvAvWithMeasureDdl() {
		Collection<AssessmentVariable> avList = avr.findAll();
		StringBuffer sb = new StringBuffer();
		for (AssessmentVariable av : avList) {
			buildUpdate(av, sb);
		}

		String updateString = sb.toString();
		logger.info(updateString);
	}

	private void buildUpdate(AssessmentVariable av, StringBuffer sb) {
		MeasureAnswer ma = av.getMeasureAnswer();

		String name = ma != null ? ma.getExportName() : null;
		String descr = ma != null ? ma.getAnswerText() : null;

		if (name == null && descr == null && av.getMeasure() != null) {
			updateSqlUsingMeasure(av.getMeasure(), av, sb);
		}

		addUpdateString(sb, name, descr, av.getAssessmentVariableId(), av.getAssessmentVariableTypeId().getAssessmentVariableTypeId());
		return;
	}

	private void updateSqlUsingMeasure(Measure m, AssessmentVariable av,
			StringBuffer sb) {
		if (m.getChildren().isEmpty()) {
			String name = m.getMeasureAnswerList().iterator().next().getExportName();
			String descr = m.getMeasureText();
			addUpdateString(sb, name, descr, av.getAssessmentVariableId(), av.getAssessmentVariableTypeId().getAssessmentVariableTypeId());
		} else {
			for (Measure m1 : m.getChildren()) {
				updateSqlUsingMeasure(m1, av, sb);
			}
		}
	}

	Set<Integer> vaIdSet = Sets.newHashSet();

	private void addUpdateString(StringBuffer sb, String name, String descr,
			int avId, int avTypeId) {
		if (name == null || "null".equals(name)) {
			return;
		}
		if (descr == null || "null".equals(descr)) {
			return;
		} else {
			descr = descr.replaceAll("\"", "'");
		}

		if (vaIdSet.add(avId)) {
			String updateSql = String.format("UPDATE assessment_variable SET assessment_variable_type_id = %s, display_name = '%s', description = \"%s\" WHERE assessment_variable_id = %s", avTypeId, name, descr, avId);
			sb.append(updateSql).append(";").append("\n");
		} else {
			logger.warn(String.format("displayName=%s,  description=%s could not be added as %s has an update sql already", name, descr, avId));
		}
	}

	@Test
	public void testReplaceTickTick() {
		String hh = "Loss of consciousness/\"knocked out\"".replaceAll("\"", "'");
		int i = 0;
	}
}
