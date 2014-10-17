package gov.va.escreening.assessments.test;

import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.repository.AssessmentVariableRepository;

import java.util.Collection;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;

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
			if (av.getAssessmentVariableTypeId().getAssessmentVariableTypeId() < 3) {
				Optional<String> updateStatement = buildUpdate(av);
				if (updateStatement.isPresent()) {
					sb.append(updateStatement.get()).append(";").append("\n");
				}
			}
		}

		String updateString = sb.toString();
	}

	private Optional<String> buildUpdate(AssessmentVariable av) {
		MeasureAnswer ma = av.getMeasureAnswer();

		String name = ma != null ? ma.getExportName() : null;
		String descr = ma != null ? ma.getAnswerText() : null;

		if (name == null && descr == null && av.getMeasure() != null) {
			Measure m = av.getMeasure();
			if (m.getChildren().isEmpty()) {
				name = m.getMeasureAnswerList().iterator().next().getExportName();
				descr = m.getMeasureAnswerList().iterator().next().getAnswerText();
			} else {
				return Optional.absent();
			}
		}

		if (name == null || "null".equals(name)) {
			return Optional.absent();
		}
		if (descr == null || "null".equals(descr)) {
			return Optional.absent();
		} else {
			descr = descr.replaceAll("\"", "'");
		}

		return Optional.of(String.format("UPDATE assessment_variable SET display_name = '%s', description = \"%s\" WHERE assessment_variable_id = %s", name, descr, av.getAssessmentVariableId()));
	}

	@Test
	public void testReplaceTickTick() {
		String hh = "Loss of consciousness/\"knocked out\"".replaceAll("\"", "'");
		int i = 0;
	}
}
