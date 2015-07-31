package gov.va.escreening.repository;

import static org.junit.Assert.*;
import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.entity.AssessmentVarChildren;
import gov.va.escreening.entity.AssessmentVariable;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
//this is to ensure all tests do not leave trace, so they are repeatable.
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class AssessmentVariableRepositoryTest {

    @Resource(type = AssessmentVariableRepository.class)
    AssessmentVariableRepository avRepo;
    
    @Test
    public void getParentFormulaTest(){
        AssessmentVariable tbiScore2 = avRepo.findOne(10719);
        AssessmentVariable childFormula = null;
        for(AssessmentVarChildren child : tbiScore2.getAssessmentVarChildrenList()){
            Integer childType = child.getVariableChild().getAssessmentVariableTypeId().getAssessmentVariableTypeId();
            if(childType.equals(AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_FORMULA)){
                childFormula = child.getVariableChild();
                break;
            }
        }

        boolean found = false;
        for(AssessmentVariable parent : avRepo.getParentVariables(childFormula)){
            if(parent.equals(tbiScore2)){
                found = true;
                break;
            }
        }
        
        assertTrue(found);
    }
}
