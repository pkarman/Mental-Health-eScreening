package gov.va.escreening.template.function;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import freemarker.ext.beans.StringModel;
import freemarker.template.DefaultObjectWrapper;
import gov.va.escreening.variableresolver.AssessmentVariableDto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

@RunWith(JUnit4.class)
public class TableHashCreatorTest {
	
	private List<StringModel> createParam(AssessmentVariableDto av){
		StringModel sm = new StringModel(av, new DefaultObjectWrapper());
		return Lists.newArrayList(sm);
	}
	
	@Test
	public void testTableHashStructure() throws Exception {
		AssessmentVariableDto table = new AssessmentVariableDto();
		table.setChildren(ImmutableList.of(
				createQuestionVariable(1, 3), 
				createQuestionVariable(2, 5),
				createQuestionVariable(3, 5)));
		
		List<Map<String, AssessmentVariableDto>> rows = new TableHashCreator().exec(createParam(table));
		
		assertTrue(rows.get(0).isEmpty());
		assertTrue(rows.get(1).isEmpty());
		assertTrue(rows.get(2).isEmpty());
		//row 3 should have one AV with an ID of 1
		assertTrue(rows.get(3).containsKey("1"));
		assertTrue(rows.get(4).isEmpty());
		//row 5 should have two AVs (id 2 and 3)
		assertTrue(rows.get(5).containsKey("2"));
		assertTrue(rows.get(5).containsKey("3"));
		assertEquals(6, rows.size());
	}
	
	@Test
	public void testTableHashNullRowQuestinoInTable() throws Exception {
		AssessmentVariableDto table = new AssessmentVariableDto();
		table.setChildren(ImmutableList.of(
				createQuestionVariable(1, null)));
		
		List<Map<String, AssessmentVariableDto>> rows = new TableHashCreator().exec(createParam(table));
		
		assertTrue(rows.isEmpty());
	}
	
	@Test
	public void testTableHashNullTable() throws Exception {
		TableHashCreator creator = new TableHashCreator();
		assertTrue("A null parameter should give back an empty List", creator.exec(createParam(null)).size() == 0);
	}

	@Test
	public void testTableHashNullChildrenTable() throws Exception {
		TableHashCreator creator = new TableHashCreator();
		AssessmentVariableDto table = new AssessmentVariableDto();
		assertTrue("A null parameter should give back an empty List", creator.exec(createParam(table)).size() == 0);
	}

	@Test
	public void testTableHashEmptyChildrenTable() throws Exception {
		TableHashCreator creator = new TableHashCreator();
		AssessmentVariableDto table = new AssessmentVariableDto();
		table.setChildren(Collections.<AssessmentVariableDto>emptyList());
		assertTrue("A null parameter should give back an empty List", creator.exec(createParam(table)).size() == 0);
	}
	
	private AssessmentVariableDto createQuestionVariable(Integer id, Integer rowIndex){
		AssessmentVariableDto questionVar = new AssessmentVariableDto();
		questionVar.setVariableId(id);
		AssessmentVariableDto answer = new AssessmentVariableDto();
		answer.setRow(rowIndex);
		questionVar.setChildren(ImmutableList.of(answer));
		
		return questionVar;
	}
}
