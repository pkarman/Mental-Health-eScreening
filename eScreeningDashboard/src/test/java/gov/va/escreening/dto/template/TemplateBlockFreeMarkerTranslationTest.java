package gov.va.escreening.dto.template;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.service.AssessmentVariableService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.google.common.collect.ImmutableMap;
import static org.mockito.Mockito.*;

/**
 * This class is concerned with testing how each block type is translated into freemarker
 * @author Robin Carnow
 *
 */
@RunWith(JUnit4.class)
public class TemplateBlockFreeMarkerTranslationTest {

		/** TESTS table translation into freemarker **/
		
		@Test
		public void testTableBlockTranslationAddsHeader() throws Exception {
			
			TemplateTableBlockDTO block = createTableBlock(null, null);
			String name = "name4444";
			String section = "section4444";
			String summary = "summary4444";
			block.setName(name);
			block.setSection(section);
			block.setSummary(summary);
			
			String result = block.appendFreeMarkerFormat(new StringBuilder(), 
			        new HashSet<Integer>(), createServiceForBlock(block)).toString();
			
			assertTrue("Name is not in header", result.contains(name));
			assertTrue("Section is not in header", result.contains(section));
			assertTrue("Summary is not in header", result.contains(summary));
		}
		
		@Test
		public void testTableBlockTranslationBuildsHash() throws Exception {
			
			TemplateTableBlockDTO block = createTableBlock(null, null);
			StringBuilder sb = new StringBuilder();
			Set<Integer> ids = new HashSet<>();
			StringBuilder result = block.appendFreeMarkerFormat(sb, ids, createServiceForBlock(block));
			
			assertNotEquals(result.indexOf("createTableHash"), -1);
		}
		
		@Test
		public void testTableBlockTranslationAddsTableId() throws Exception {
			TemplateTableBlockDTO block = createTableBlock(999, null);
			StringBuilder sb = new StringBuilder();
			Set<Integer> ids = new HashSet<>();
			block.appendFreeMarkerFormat(sb, ids, createServiceForBlock(block));
			
			assertTrue(ids.contains(999));
		}
		
		@Test
		public void testTableBlockTranslationAddsChildren() throws Exception {
			TemplateTableBlockDTO block = createTableBlock(999, null);
			StringBuilder sb = new StringBuilder();
			Set<Integer> ids = new HashSet<>();
			
			List<INode> children = new ArrayList<>();
			INode child1 = mock(INode.class);
			when(child1.appendFreeMarkerFormat(eq(sb), eq(ids), any(AssessmentVariableService.class))).thenReturn(sb);
			children.add(child1);
			block.setChildren(children);
			
			block.appendFreeMarkerFormat(sb, ids, createServiceForBlock(block));
			
			verify(child1).appendFreeMarkerFormat(eq(sb), eq(ids), any(AssessmentVariableService.class));
		}
		
		/**
		 * Tests to make sure child assessment variables are replaced with calls to hash function. The 
		 * table's own references should not be replaced.
		 * @throws Exception
		 */
		@Test
		public void testTableBlockTranslationUpdatesChildAVs() throws Exception {
			Integer tableAvId = 999;
			TemplateTableBlockDTO block = createTableBlock(tableAvId, null);
			StringBuilder sb = new StringBuilder();
			Set<Integer> ids = new HashSet<>();
			
			List<INode> children = new ArrayList<>();
			INode child1 = mock(INode.class);
			
			//the child variable markers which should be replaced
			String var111 = String.format(TemplateTableBlockDTO.VAR_FORMAT, 111);
			String var222 = String.format(TemplateTableBlockDTO.VAR_FORMAT, 222);
			String tableVar = String.format(TemplateTableBlockDTO.VAR_FORMAT, tableAvId);
			
			//using the block's same logic to create freemarker variables
			String tableHashName = block.createTableHashName(sb);
			String rowIndexName = block.createRowIndexName(sb);
			
			//what children are supposed to be replaced with
			String var111Replacement = String.format(TemplateTableBlockDTO.REPLACEMENT_FORMAT, tableHashName, var111, rowIndexName);
			String var222Replacement = String.format(TemplateTableBlockDTO.REPLACEMENT_FORMAT, tableHashName, var222, rowIndexName);
			
			//Add the mock content for the child blocks which we want the table block dto to update with the correct replacement.
			StringBuilder childContent = new StringBuilder(
					 "test content" + var111
					+"table var next" + tableVar
					+"more content" + var111
					+"even more content"+ var222 + "lastContent");
			
			when(child1.appendFreeMarkerFormat(eq(sb), eq(ids), any(AssessmentVariableService.class))).thenReturn(childContent);
			children.add(child1);
			block.setChildren(children);
			
			String result = block.appendFreeMarkerFormat(sb, ids, createServiceForBlock(block)).toString();
			
			String expected = "test content" + var111Replacement
					+"table var next" + tableVar    // this should not be changed
					+"more content" + var111Replacement
					+"even more content"+ var222Replacement + "lastContent";
			
			assertTrue("Expected to find the following content: \n" + expected + "\nIn block output:\n" + result, result.contains(expected));
		}
		
		private TemplateTableBlockDTO createTableBlock(Integer tableAvId, Integer tableMeasureId){
			 if(tableAvId == null){
				 tableAvId = 123;
			 }
			 if(tableMeasureId == null){
				 tableMeasureId = 555;
			 }
			
			//create block
			TemplateAssessmentVariableDTO tableDTO = new TemplateAssessmentVariableDTO();
			tableDTO.setId(tableAvId);
			tableDTO.setMeasureId(tableMeasureId);
			
			TemplateVariableContent tableVar = new TemplateVariableContent();
			tableVar.setContent(tableDTO);
			
			TemplateTableBlockDTO block = new TemplateTableBlockDTO();
			
			block.setTable(tableVar);
			
			return block;
		}
		
		private AssessmentVariableService createServiceForBlock(TemplateTableBlockDTO block){
		    TemplateAssessmentVariableDTO tableDTO = block.getTable().getContent();
		    Integer tableAvId = tableDTO.getId();
		    Integer tableMeasureId = tableDTO.getMeasureId();
		    
	        AssessmentVariableService avService = mock(AssessmentVariableService.class);
	            Map<Integer, AssessmentVariable> avMap = ImmutableMap.of(
	                    tableAvId, mock(AssessmentVariable.class), 
	                    111, mock(AssessmentVariable.class),
	                    222, mock(AssessmentVariable.class));
	       when(avService.getAssessmentVarsForMeasure(tableMeasureId)).thenReturn(avMap);
	       
	       return avService; 
		}
}