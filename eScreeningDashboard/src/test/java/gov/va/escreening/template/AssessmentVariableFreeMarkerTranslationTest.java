package gov.va.escreening.template;

import java.util.ArrayList;
import java.util.HashSet;

import gov.va.escreening.dto.template.INode;
import gov.va.escreening.dto.template.TemplateAssessmentVariableDTO;
import gov.va.escreening.dto.template.TemplateBaseContent;
import gov.va.escreening.dto.template.TemplateFileDTO;
import gov.va.escreening.dto.template.TemplateTextDTO;
import gov.va.escreening.dto.template.TemplateVariableContent;
import gov.va.escreening.dto.template.VariableTransformationDTO;

import javax.annotation.Nullable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import static org.junit.Assert.*;
import static gov.va.escreening.constants.AssessmentConstants.*;

/**
 * This class is concerned with testing the translation of assessment variable DTO objects into FreeMarker
 *  
 * @author Robin Carnow
 */
@RunWith(JUnit4.class)
public class AssessmentVariableFreeMarkerTranslationTest {

		/**  TESTS for transformation translation into freemarker **/
		
		@Test
		public void testVariableTransformationTranslation() throws Exception	{
			testTransformationTranslation("transformationFunc(var123,\"first\",\"second\",\"third\")", 123, "transformationFunc", "first", "second", "third");
		}
		
		@Test
		public void testVariableTransformationTranslationContainingBoolean() throws Exception	{
			testTransformationTranslation("transformationFunc1(var123,\"first\",true,\"third\",false)", 123, "transformationFunc1", "first", "true", "third","false");
			testTransformationTranslation("transformationFunc2(var123,\"first\",true,\"third\",false)", 123, "transformationFunc2", "first", "True", "third","False");
			testTransformationTranslation("transformationFunc3(var123,\"first\",true,\"third\",false)", 123, "transformationFunc3", "first", "TrUe", "third","FaLse");
			testTransformationTranslation("transformationFunc4(var123,\"first\",true,\"third\",false)", 123, "transformationFunc4", "first", "TRUE", "third","FALSE");
		}
		
		/**
		 * Array should be translated into 
		 * <a href="http://freemarker.org/docs/dgui_template_exp.html#dgui_template_exp_direct_seuqence">Freemarker Sequence literals</a> 
		 * like: ["winter", "spring", "summer", "autumn"]
		 * 
		 */
		@Test
		public void testVariableTransformationTranslationContainingArray() throws Exception	{
			testTransformationTranslation("transformationFunc(var123,\"first\",[1,2,3,4],\"third\")", 123, "transformationFunc", "first", "[1,2,3,4]", "third");
			testTransformationTranslation("transformationFunc(var123,\"first\",[1,2,\"test\",4],\"third\")", 123, "transformationFunc", "first", "[1,2,\"test\",4]", "third");
		}

		/**
		 * Maps should be translated into 
		 * <a href=" http://freemarker.org/docs/dgui_template_exp.html#dgui_template_exp_direct_hash">Freemarker Hash literals</a> 
		 * like: { "name": "green mouse", "price": 150 }
		 * 
		 */
		@Test
		public void testVariableTransformationTranslationContainingMap() throws Exception	{
			testTransformationTranslation("transformationFunc(var123,\"first\",{\"k1\":\"v1\",\"k2\":2},\"third\")", 123, "transformationFunc", "first", "{\"k1\":\"v1\",\"k2\":2}", "third");
		}
		
		@Test
		public void testNoTransformationTranslation() throws Exception{
			testTransformationTranslation("var123", 123, null);
		}
		
		@Test
		public void testNoParameterTransformationTranslation() throws Exception{
			//check null params list
			testTransformationTranslation("transformationFunc(var123)", 123, "transformationFunc");
			//check empty params list
			testTransformationTranslation("transformationFunc(var123)", 123, "transformationFunc", new String[0]);
		}	
		
		@Test
		public void testMultipleTransformationTranslation() throws Exception{
			//add first transformation
			TemplateAssessmentVariableDTO variable = createAV(123, null, null, "transformationFunc1", "first", "second", "third");
			
			//add second transformation
			VariableTransformationDTO secondTransformation = createTransformation("transformationFunc2", "true", "false");
			variable.getTransformations().add(secondTransformation);
			
			TemplateVariableContent varContent = new TemplateVariableContent();
			varContent.setContent(variable);
			
			String translation = TemplateBaseContent.translate(null, varContent, null, new HashSet<Integer>());
			
			System.out.println(translation);
			String expected = "transformationFunc2(transformationFunc1(var123,\"first\",\"second\",\"third\"),true,false)";
			assertEquals(expected, translation);
		}
		
		/** TESTS condition translation into freemarker **/
		/*
		 	{ name: 'Equals', value: 'eq', category: 'nonselect' },
		    { name: 'Doesn\'t Equals', value: 'neq', category: ''nonselect'' },
		    { name: 'Is Less Than', value: 'lt', category: 'nonselect' },
		    { name: 'Is Greater Than', value: 'gt', category: 'nonselect },
		    { name: 'Is Less Than or Equals', value: 'lte', category: nonselect' },
		    { name: 'Is Greater Than or Equals', value: 'gte', category: 'nonselect' },
		
		    { name: 'Was Answered', value: 'answered', category: 'question' },
		    { name: 'Wasn\'t Answered', value: 'nanswered', category: 'question' },
		
		    { name: 'Has Result', value: 'result', category: 'formula & custom' },
		    { name: 'Has No Result', value: 'nresult', category: 'formula & custom' },
		
		    { name: 'Response is', value: 'response', category: 'select' },
		    { name: 'Response isn\t', value: 'nresponse', category: 'select' },
		    
		    { name: 'None was selected', value: 'none', category: 'table' },
		    { name: 'None wasn't selected', value: 'nnone', category: 'table' }
		 */
		@Test
		public void testMatrixHasResultConditionFreemarkerTranslation() throws Exception{
			//result --> should use: matrixHasResult
			
			testConditionTranslation(measureAv(123, MEASURE_TYPE_SELECT_ONE_MATRIX), 
					"result", null, "matrixHasResult(var123)");
			testConditionTranslation(measureAv(123, MEASURE_TYPE_SELECT_MULTI_MATRIX), 
					"result", null, "matrixHasResult(var123)");
		}
		
		@Test
		public void testMatrixHasNoResultConditionFreemarkerTranslation() throws Exception{
			//nresult --> should use: matrixHasNoResult
			testConditionTranslation(measureAv(123, MEASURE_TYPE_SELECT_MULTI_MATRIX), 
					"nresult", null, "matrixHasNoResult(var123)");
			testConditionTranslation(measureAv(123, MEASURE_TYPE_SELECT_ONE_MATRIX), 
					"nresult", null, "matrixHasNoResult(var123)");
		}
		
		@Test
		public void testTableWasAnsweredConditionFreemarkerTranslation() throws Exception{
			//answered --> should use: wasAnswered
			testConditionTranslation(measureAv(123, MEASURE_TYPE_TABLE), 
					"answered", null, "wasAnswered(var123)");
		}
		
		@Test
		public void testTableWasntAnsweredConditionFreemarkerTranslation() throws Exception{
			//nanswered --> should use: wasntAnswered
			testConditionTranslation(measureAv(123, MEASURE_TYPE_TABLE), 
					"nanswered", null, "wasntAnswered(var123)");
		}
		
		@Test
		public void testTableWasAnswerNoneConditionFreemarkerTranslation() throws Exception{
			//none --> should use: wasAnswerNone
			testConditionTranslation(measureAv(123, MEASURE_TYPE_TABLE), 
					"none", null, "wasAnswerNone(var123)");
		}
		
		@Test
		public void testTableWasntAnswerNoneConditionFreemarkerTranslation() throws Exception{
			//nnone --> should use: wasntAnswerNone
			testConditionTranslation(measureAv(123, MEASURE_TYPE_TABLE), 
					"nnone", null, "wasntAnswerNone(var123)");
		}
		
		@Test
		public void testValueCompareFromTableNumEntriesTransformationFreemarkerTranslation() throws Exception{
			//we must support value comparisons (eq, lt, etc.) with table questions which have been transformed using NumEntries 
			
		}
		
		private void testTransformationTranslation(String expectedOutput, Integer varId, 
				@Nullable String transformationName, String... params){
			
			TemplateAssessmentVariableDTO variable = createAV(varId, null, null, transformationName, params);
			TemplateVariableContent varContent = new TemplateVariableContent();
			varContent.setContent(variable);
			
			String translation = TemplateBaseContent.translate(null, varContent, null, new HashSet<Integer>());
			
			//System.out.println(translation);
			
			assertEquals(expectedOutput, translation);
		}		
		
		private void testConditionTranslation(TemplateAssessmentVariableDTO leftVariable, 
				@Nullable String jsConditionOp, 
				@Nullable TemplateBaseContent rightContent, 
				String expectedOutput){
			
			TemplateVariableContent leftContent = new TemplateVariableContent();
			leftContent.setContent(leftVariable);
			
			String translation = TemplateBaseContent.translate(jsConditionOp, leftContent, rightContent, new HashSet<Integer>());
			
			//System.out.println(translation);
			
			assertEquals(expectedOutput, translation);
		}
		
		private VariableTransformationDTO createTransformation(String name, String... params){
			VariableTransformationDTO transformation = new VariableTransformationDTO();
			transformation.setName(name);
			if(params != null){
				transformation.setParams(ImmutableList.copyOf(params));
			}
			return transformation;
		}
		
		private TemplateAssessmentVariableDTO measureAv(Integer varId, 
				@Nullable Integer measureTypeId){  //defaults to unset){
			return createAV(varId, null, measureTypeId, null);
		}
		
		private TemplateAssessmentVariableDTO createAV(Integer varId, 
				@Nullable Integer varTypeId,  //defaults to measure
				@Nullable Integer measureTypeId,  //defaults to unset
				@Nullable String transformationName, String... params){
			
			TemplateAssessmentVariableDTO variable = new TemplateAssessmentVariableDTO();
			variable.setId(varId);
			variable.setTypeId(varTypeId != null ? varTypeId : ASSESSMENT_VARIABLE_TYPE_MEASURE);
			variable.setMeasureTypeId(measureTypeId);
			
			if(transformationName != null){
				VariableTransformationDTO transformation = createTransformation(transformationName, params);
				variable.setTransformations(Lists.newArrayList(transformation));	
			}			
			
			return variable;
		}
		
		private TemplateFileDTO createTemplateFile(TemplateVariableContent content){
			
			TemplateFileDTO fileDTO = new TemplateFileDTO();
			fileDTO.setId(40);
			fileDTO.setIsGraphical(false);
			fileDTO.setBlocks(new ArrayList<INode>());
			
			TemplateTextDTO text1 = new TemplateTextDTO();
			text1.setName("Depression Screening");
			text1.setSection("1.");
			text1.setContents(new ArrayList<TemplateBaseContent>());
			
			text1.getContents().add(content);
			fileDTO.getBlocks().add(text1);
			
			return fileDTO;
		}		
}
