package gov.va.escreening.expressionevaluator;

import static org.junit.Assert.*;

import java.util.Set;

import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.entity.AssessmentVarChildren;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.repository.AssessmentVariableRepository;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;

@Transactional
//this is to ensure all tests do not leave trace, so they are repeatable.
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class ExpressionEvaluatorServiceTest {

    @Resource
    AssessmentVariableRepository avRepo;
    
    @Test
    public void testFlatteningOfFormulasThatUseOtherFormulas() throws Exception{
        
        AssessmentVariable tbiScore2 = avRepo.findOne(10719);
        Set<AssessmentVariable> expectedDependents = Sets.newHashSetWithExpectedSize(50);
        addDependencies(tbiScore2, expectedDependents);
        
        Set<AssessmentVariable> actualDependents = Sets.newHashSetWithExpectedSize(tbiScore2.getAssessmentVarChildrenList().size());
        for(AssessmentVarChildren child : tbiScore2.getAssessmentVarChildrenList()){
            actualDependents.add(child.getVariableChild());
        }
        
        assertEquals(0, Sets.difference(expectedDependents, actualDependents).size());
        
    }
    
    private void addDependencies(AssessmentVariable formula, Set<AssessmentVariable> allDependents){
        for(AssessmentVarChildren child : formula.getAssessmentVarChildrenList()){
            AssessmentVariable childAv = child.getVariableChild();
            allDependents.add(childAv);
            if(childAv.getAssessmentVariableTypeId().getAssessmentVariableTypeId().equals(AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_FORMULA)){
                addDependencies(childAv, allDependents);
            }
        }
    }
}
