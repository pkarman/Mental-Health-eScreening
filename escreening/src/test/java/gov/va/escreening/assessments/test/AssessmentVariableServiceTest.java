package gov.va.escreening.assessments.test;

import static org.junit.Assert.*;
import gov.va.escreening.delegate.AssessmentDelegate;
import gov.va.escreening.entity.AssessmentVarChildren;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.repository.AssessmentVariableRepository;
import gov.va.escreening.repository.VeteranAssessmentRepository;
import gov.va.escreening.service.AssessmentVariableService;
import gov.va.escreening.service.VeteranAssessmentService;
import gov.va.escreening.test.TestAssessmentVariableBuilder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
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

import com.google.common.collect.Lists;

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
	
	@Resource
	VeteranAssessmentRepository assessmentRepo;
	
    @Resource(type=AssessmentDelegate.class)
    AssessmentDelegate ad;

	@Test
	public void testTimeSeries()
	{
		Map<String, Double> timeSeries = vaSvc.getVeteranAssessmentVariableSeries(TEST_VET_ID, 11, 3);
		assertNotNull(timeSeries);
		assertEquals(1, timeSeries.size());
	}
	
	@Test
    public void testVeteran18_PainScoreTimeSeries(){
        Map<String, Double> timeSeries = vaSvc.getVeteranAssessmentVariableSeries(18, 2300, 12);
        assertNotNull(timeSeries);
        assertEquals(1, timeSeries.size());
    }
	
    @Test
    public void testVeteran18_PHQ9TimeSeries(){
        Map<String, Double> timeSeries = vaSvc.getVeteranAssessmentVariableSeries(18, 1599, 12);
        assertNotNull(timeSeries);
        assertEquals(1, timeSeries.size());
    }
	
	@Test
    public void testVeteran18_PTSDTimeSeries(){
        Map<String, Double> timeSeries = vaSvc.getVeteranAssessmentVariableSeries(18, 1929, 12);
        assertNotNull(timeSeries);
        assertEquals(1, timeSeries.size());
    }
	
	@Test
    public void testVeteran16_PHQ9TimeSeries(){
        Map<String, Double> timeSeries = vaSvc.getVeteranAssessmentVariableSeries(16, 1599, 12);
        assertNotNull(timeSeries);
        assertEquals(2, timeSeries.size());
    }

	@Test
    public void testVeteran16_PHQ9TimeSeries_archivedAssessments(){
		//Set archive date for these
		for(VeteranAssessment assessment : assessmentRepo.findByVeteranId(16)){
			if(assessment.getDateArchived() != null){
				throw new IllegalStateException("If archive date is set then this code must change");
			}
			assessment.setDateArchived(new Date());
			assessmentRepo.update(assessment);
		}
		
        Map<String, Double> timeSeries = vaSvc.getVeteranAssessmentVariableSeries(16, 1599, 12);
        assertNotNull(timeSeries);
        //clean assessments should not be returned so only 2 here
        assertEquals(2, timeSeries.size());
        
        //unset archive date for these
		for(VeteranAssessment assessment : assessmentRepo.findByVeteranId(16)){
			assessment.setDateArchived(null);
			assessmentRepo.update(assessment);
		}
    }
	
    @Test
    public void testRecordAllReportableScores(){
        final VeteranAssessment testAssessment = vaSvc.findByVeteranAssessmentId(18);
        ad.recordAllReportableScores(testAssessment);
    }
    
    @Test
    public void testNewChildFormulaDependencyUpdatesAllParents() throws Exception{
        AssessmentVariable rootFormula = createRootFormula();
        
        //for child formula get parent
        List<AssessmentVariable> descendants = collectDescendantList(rootFormula);
        assertTrue(descendants.size() > 2);
        
        //Find a child formula which we will add a new dependency to
        AssessmentVariable childFormula = descendants.get(descendants.size()-1);
        
        //add new dep to child and save
        AssessmentVariable newDependency = addNewDependency(childFormula);
        
        //Call test method (this updates all formulas up the chain)
        avService.updateParentFormulas(childFormula);
        
        //check to see that the new dep is associated with every descendant
        assertDescendantsHaveDep(avRepo.findOne(rootFormula.getAssessmentVariableId()), newDependency);
    }
    
    @Test
    public void testRemovingChildsDependencyRemovesDepFromParents(){
        AssessmentVariable rootFormula = createRootFormula();
        
        //for child formula get parent
        List<AssessmentVariable> descendants = collectDescendantList(rootFormula);
        assertTrue(descendants.size() > 2);

        //Find a child formula which we will add a new dependency to
        AssessmentVariable childFormula = descendants.get(descendants.size()-1);

        //create and add new dep to child formula and save
        AssessmentVariable newDependency = addNewDependency(childFormula);

        //Call test method (this updates all formulas up the chain)
        avService.updateParentFormulas(childFormula);

        //check to see that the new dep is associated with every descendant
        assertDescendantsHaveDep(avRepo.findOne(rootFormula.getAssessmentVariableId()), newDependency);

        //Remove the dependency from the child formula
        childFormula.setFormulaTemplate("");
        removeChildAv(childFormula, newDependency);
        
        //Call test method (this updates all formulas up the chain)
        avService.updateParentFormulas(childFormula);

        //test to make sure the dependency has been removed from all descendants
        assertDescendantsMissingDep(avRepo.findOne(rootFormula.getAssessmentVariableId()), newDependency);
    }
    
    /**
     * Test to make sure if two different child formulas have the same dependency, 
     * removing that dependency from one of the child formulas will not cause it to be 
     * removed from the parent until it is removed from both child formulas.
     */
    @Test
    public void testRemovingCommonDependencyKeepsDepInParents(){
        AssessmentVariable rootFormula = createRootFormula();
        
        //for child formula get parent
        List<AssessmentVariable> descendants = collectDescendantList(rootFormula);
        assertTrue(descendants.size() > 2);

        //Find a child formula which we will add a new dependency to
        AssessmentVariable childFormula1 = descendants.get(descendants.size()-1);
        
        //Add another sibling descendant
        AssessmentVariable childFormula2 = addChildFormula(rootFormula);
        
        //create and add new dep to child formula 1 and save
        AssessmentVariable newDependency = addNewDependency(childFormula1);
        
        //add same dep to child formula 2
        childFormula2.getAssessmentVarChildrenList().add(new AssessmentVarChildren(childFormula2, newDependency));
        avRepo.update(childFormula2);
        
        //Call test method (this updates all formulas up the chain)
        avService.updateParentFormulas(childFormula1);
        avService.updateParentFormulas(childFormula2);

        //check to see that the new dep is associated with every descendant
        assertDescendantsHaveDep(avRepo.findOne(rootFormula.getAssessmentVariableId()), newDependency);

        //remove the dep from one of the descendants but not the other
        removeChildAv(childFormula2, newDependency);
        childFormula2.setFormulaTemplate("");
        
        //Call test method to update parents (this should not remove the dep from the root formula)
        avService.updateParentFormulas(childFormula2);
        
        //check to see that the new dep is associated with every descendant
        assertDescendantsHaveDep(avRepo.findOne(rootFormula.getAssessmentVariableId()), newDependency);
        
        //Now remove the dep from the other sibling and the dep show go away
        removeChildAv(childFormula1, newDependency);
        childFormula1.setFormulaTemplate("");
        
        //Call test method to update parents (this should remove the dep from the root formula because this is the last reference to it)
        avService.updateParentFormulas(childFormula1);
        
        //test to make sure the dependency has been removed from all descendants
        assertDescendantsMissingDep(avRepo.findOne(rootFormula.getAssessmentVariableId()), newDependency);
    }
    
    /**
     * Creates a formula that has associated with it at least two child formulas with 
     * an empty list of dependencies
     * @return formula that has been saved to the DB.
     */
    private AssessmentVariable createRootFormula(){
               
        AssessmentVariable rootFormula = new AssessmentVariable();
        rootFormula.setDisplayName("Root Formula");
        rootFormula.setAssessmentVariableTypeId(TestAssessmentVariableBuilder.TYPE_FORMULA);
        avRepo.create(rootFormula);
        
        //add descendant formulas 
        AssessmentVariable childFormula = addChildFormula(rootFormula);
        for(int i = 0; i < 4; i++){
            childFormula = addChildFormula(childFormula);
        }
        
        return rootFormula;
    }
    
    /**
     * Adds a child formula to {@code formula} and returns the child
     */
    private AssessmentVariable addChildFormula(AssessmentVariable formula){
        if(formula.getAssessmentVarChildrenList() == null){
            formula.setAssessmentVarChildrenList(Lists.<AssessmentVarChildren>newArrayList());
        }
        
        AssessmentVariable childFormula = new AssessmentVariable();
        childFormula.setDisplayName("Child Formula");
        childFormula.setAssessmentVariableTypeId(TestAssessmentVariableBuilder.TYPE_FORMULA);
        childFormula.setAssessmentVarChildrenList(Lists.<AssessmentVarChildren>newArrayList());
        avRepo.create(childFormula);        
        
        AssessmentVarChildren varChild = new AssessmentVarChildren(formula, childFormula);
        formula.getAssessmentVarChildrenList().add(varChild);
        avRepo.update(formula);
        logger.trace("Added child formula {} to formula {}", childFormula.getAssessmentVariableId(), formula.getAssessmentVariableId());
        return childFormula;
    }
    
    /**
     * Creates and adds new dependency (AV) to formula and saves
     * @param formula
     * @return
     */
    private AssessmentVariable addNewDependency(AssessmentVariable formula){
        AssessmentVariable newDependency = new AssessmentVariable();
        newDependency.setDisplayName("testchildvar");
        newDependency.setAssessmentVariableTypeId(TestAssessmentVariableBuilder.TYPE_ANSWER);
        avRepo.create(newDependency);
        //add dependency to fake formula 
        formula.setFormulaTemplate("[" + newDependency.getAssessmentVariableId() + "]");
        formula.getAssessmentVarChildrenList().add(new AssessmentVarChildren(formula, newDependency));
        avRepo.update(formula);
        logger.trace("Added new dependency {} to formula {}", newDependency.getAssessmentVariableId(), formula.getAssessmentVariableId());
        return newDependency;
    }
    
    /**
     * Collects the longest list of descendants for the formula and asserts that each descendant contains the given dependency.
     * @param formula
     * @param dependency
     */
    private void assertDescendantsHaveDep(AssessmentVariable formula, AssessmentVariable dependency){
        for(AssessmentVariable descendant : collectDescendantList(formula)){
            Set<AssessmentVariable> descendentDeps = getDeps(descendant);
            assertTrue("Formula with ID " + descendant.getAssessmentVariableId() 
                    + " does not have dependency with ID " + dependency.getAssessmentVariableId(),
                    descendentDeps.contains(dependency));
        }
    }
    
    /**
     * Collects the longest list of descendants for the formula and asserts that 
     * each descendant does not contain the given dependency.
     */
    private void assertDescendantsMissingDep(AssessmentVariable formula, AssessmentVariable dependency){
        for(AssessmentVariable descendant : collectDescendantList(formula)){
            Set<AssessmentVariable> descendentDeps = getDeps(descendant);
            assertFalse("Formula with ID " + descendant.getAssessmentVariableId() 
                    + " does have a dependency with variable ID " + dependency.getAssessmentVariableId(),
                    descendentDeps.contains(dependency));
        }
    }
    
    /**
     * Removes the given child AV from the parent and (Hibernate) updates the parent
     * @param parent
     * @param child
     */
    @Transactional
    private void removeChildAv(AssessmentVariable parent, AssessmentVariable child){
        Iterator<AssessmentVarChildren> childIter = parent.getAssessmentVarChildrenList().iterator();
        while(childIter.hasNext()){
            AssessmentVarChildren children = childIter.next();
            if(children.getVariableParent().equals(parent) && children.getVariableChild().equals(child)){
                childIter.remove();
                avRepo.update(parent);
                avRepo.commit();
                return;
            }
        }
        throw new AssertionError("No child found. This probably means a bug in the test code.");
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
                if(child.getVariableParent().equals(parent)){
                    AssessmentVariable childAv = child.getVariableChild();
                    depSet.add(childAv);
                }
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
