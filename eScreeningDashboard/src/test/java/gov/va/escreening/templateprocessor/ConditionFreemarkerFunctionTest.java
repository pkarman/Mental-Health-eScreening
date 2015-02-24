package gov.va.escreening.templateprocessor;

import gov.va.escreening.test.IntegrationTest;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * This class is concerned with the testing of freemarker functions defined for all templates.<br/>
 * This is an integration test.<br/>
 * 
 * @author Robin Carnow
 *
 */
@Category(IntegrationTest.class)
@RunWith(JUnit4.class)
public class ConditionFreemarkerFunctionTest extends FreeMarkerFunctionTest{

	@Test
	public void testMatrixHasResultConditionNull() throws Exception{
		//if null return false
		
	}
	
	@Test
	public void testMatrixHasResultConditionEmptyString() throws Exception{
		//if empty return false
	}
	
	@Test
	public void testMatrixHasResultConditionValue() throws Exception{
		//if has at least one char then true
	}
	
	@Test
	public void testMatrixHasNoResultConditionNull() throws Exception{
		//if null return true
		
	}
	
	@Test
	public void testMatrixHasNoResultConditionEmptyString() throws Exception{
		//if empty return true
	}
	
	@Test
	public void testMatrixHasNoResultConditionValue() throws Exception{
		//if has at least one char then false
	}
	
	@Test
	public void testTableWasAnsweredConditionForNone() throws Exception{
		//if None was selected returns true
	}
	
	@Test
	public void testTableWasAnsweredConditionForEntry() throws Exception{
		//if an entry was added returns true
	}		
	
	@Test
	public void testTableWasAnsweredConditionForFalse() throws Exception{
		//if no entries and no None answer was selected returns false
	}		
	
	@Test
	public void testTableWasntAnsweredConditionForNone() throws Exception{
		//if None was selected returns false
	}
	
	@Test
	public void testTableWasntAnsweredConditionForEntry() throws Exception{
		//if an entry was added returns false
	}		
	
	@Test
	public void testTableWasntAnsweredConditionForFalse() throws Exception{
		//if no entries and no None answer was selected returns true
	}
}
