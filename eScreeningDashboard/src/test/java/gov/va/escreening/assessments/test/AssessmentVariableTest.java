package gov.va.escreening.assessments.test;

import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.repository.AssessmentVariableRepository;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;

@Transactional
// this is to ensure all tests do not leave trace, so they are repeatable.
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
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
        } else {
            addUpdateString(sb, name, descr, av.getAssessmentVariableId(), av.getAssessmentVariableTypeId().getAssessmentVariableTypeId());
        }

        return;
    }

    private void updateSqlUsingMeasure(Measure m, AssessmentVariable av,
                                       StringBuffer sb) {
        //if (m.getChildren().isEmpty()) {
        String name = m.getVariableName();
        if (name == null) {
            List<MeasureAnswer> maList = m.getMeasureAnswerList();
            if (maList != null && !maList.isEmpty()) {
                name = maList.iterator().next().getExportName();
            }
        }
        String descr = m.getMeasureText();
        addUpdateString(sb, name, descr, av.getAssessmentVariableId(), av.getAssessmentVariableTypeId().getAssessmentVariableTypeId());
        //} else {
        //	for (Measure m1 : m.getChildren()) {
        //		updateSqlUsingMeasure(m1, av, sb);
        //	}
        //}

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

    //@Test
    public void generateMissingMeasureAvRecs() {
        // new AbstractHibernateRepository<Measure>() {

        /**
         * PLEASE READ:::: This is a code to generate one time insert statments for all missing AssessmentVariable
         * entities for some measures to run this script. Temporarily expose entity manager to the
         * AssessmentVariableRepository so fully primed entity manger by spring can be used here
         */

        // Please read the above note and uncomment entityManager assignment. Replace the null assignment with the
        // uncommented assignment
        EntityManager entityMgr = null; // avr.getEntityManager();
        String namedQuery = "SELECT * FROM measure m WHERE measure_id NOT IN (SELECT m2.measure_id FROM measure m2 JOIN assessment_variable av ON m2.measure_id=av.measure_id) AND measure_type_id !=8";
        Query query = entityMgr.createNativeQuery(namedQuery);
        List result = query.getResultList();

        StringBuilder sb = new StringBuilder();
        int pkId = 10810;
        for (Iterator iter = result.iterator(); iter.hasNext(); ) {
            Object[] row = (Object[]) iter.next();
            int measureId = (int) row[0];
            String description = (String) row[2];

            String insertStement = String.format("INSERT INTO assessment_variable " + "(assessment_variable_id,assessment_variable_type_id,display_name,description," + "measure_id)" + " VALUES (%s, %s, '%s', '%s', %s);", pkId++, 1, "test123", description, measureId);

            sb.append(insertStement).append("\n");
        }

        String insertStatements = sb.toString();
    }

    @Test
    public void testReplaceTickTick() {
        String hh = "Loss of consciousness/\"knocked out\"".replaceAll("\"", "'");
        int i = 0;
    }
}
