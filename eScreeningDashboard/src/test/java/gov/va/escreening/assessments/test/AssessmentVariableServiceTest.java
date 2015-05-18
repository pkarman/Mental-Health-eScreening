package gov.va.escreening.assessments.test;

import static org.junit.Assert.*;
import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.delegate.AssessmentDelegate;
import gov.va.escreening.entity.AssessmentVarChildren;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.AssessmentVariableType;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.repository.AssessmentVariableRepository;
import gov.va.escreening.service.AssessmentVariableService;
import gov.va.escreening.service.VeteranAssessmentService;
import gov.va.escreening.test.TestAssessmentVariableBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableSet;

@Transactional
//this is to ensure all tests do not leave trace, so they are repeatable.
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class AssessmentVariableServiceTest extends AssessmentTestBase
{
	@Resource
	VeteranAssessmentService vaSvc;
	
	@Resource
	AssessmentVariableRepository avRepo;
	
	@Resource
    AssessmentVariableService avService;
	
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
    
    @Test
    public void testNewChildFormulaDependencyUpdatesAllParents() throws Exception{
        //for child formula get parent
        List<AssessmentVariable> descendants = collectDescendantList(avRepo.findOne(10719));
        assertTrue(descendants.size() > 2);
        
        //Find a child formula which we will add a new dependency to
        AssessmentVariable childFormula = descendants.get(descendants.size()-1);
        
        //add new dep to child and save
        AssessmentVariable newDependency = new AssessmentVariable();
        newDependency.setDisplayName("testchildvar");
        newDependency.setAssessmentVariableTypeId(TestAssessmentVariableBuilder.TYPE_MEASURE);
        avRepo.create(newDependency);
        childFormula.getAssessmentVarChildrenList().add(new AssessmentVarChildren(childFormula, newDependency));
        avRepo.update(childFormula);
        
        //Call test method (this updates all formulas up the chain)
        avService.updateParentFormulas(childFormula);
        
        //check to see that the new dep is associated with every descendant
        for(AssessmentVariable descendant : collectDescendantList(avRepo.findOne(10719))){
            assertTrue("Formula with ID " + descendant.getAssessmentVariableId() + " does not have dependency with ID " + newDependency.getAssessmentVariableId(),
                    getDeps(descendant).contains(newDependency));
        }
        
    }
    
    private Set<AssessmentVariable> getDeps(AssessmentVariable parent){
        Set<AssessmentVariable> depSet = new TreeSet<>(new Comparator<AssessmentVariable>(){

            @Override
            public int compare(AssessmentVariable left, AssessmentVariable right) {
                return left.getAssessmentVariableId().compareTo(right.getAssessmentVariableId());  
            }
            
        });
        if(parent.getAssessmentVarChildrenList() != null){
            for(AssessmentVarChildren child : parent.getAssessmentVarChildrenList()){
                AssessmentVariable childAv = child.getVariableChild();
                depSet.add(childAv);
            }
        }
        return depSet;
    }
    
    /**
     * @param parent
     * @return the longest list of Descendant assessment variables for the given parent, 
     * in descending depth order, the parent will be the first element
     */
    private List<AssessmentVariable> collectDescendantList(AssessmentVariable parent){
        List<AssessmentVariable> descendents = new ArrayList<>();
        for(AssessmentVariable child : getDeps(parent)){
            if(child.isFormula()){
                List<AssessmentVariable> newDescendent = collectDescendantList(child);
                if(newDescendent.size() > descendents.size()){ 
                    descendents = newDescendent;
                }
            }
        }
        descendents.add(0, parent);
        return descendents;
    }
}
