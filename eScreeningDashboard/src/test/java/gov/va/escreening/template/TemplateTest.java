package gov.va.escreening.template;

import java.util.ArrayList;

import javax.annotation.Resource;

import gov.va.escreening.dto.TemplateTypeDTO;
import gov.va.escreening.dto.template.INode;
import gov.va.escreening.dto.template.TemplateAssessmentVariableDTO;
import gov.va.escreening.dto.template.TemplateBaseContent;
import gov.va.escreening.dto.template.TemplateFileDTO;
import gov.va.escreening.dto.template.TemplateFollowingConditionBlock;
import gov.va.escreening.dto.template.TemplateIfBlockDTO;
import gov.va.escreening.dto.template.TemplateTextContent;
import gov.va.escreening.dto.template.TemplateTextDTO;
import gov.va.escreening.dto.template.TemplateVariableContent;
import gov.va.escreening.entity.Template;
import gov.va.escreening.repository.TemplateRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class TemplateTest {
	
	@Resource
	TemplateRepository templateRepository;
	
	@Test
	public void test() throws Exception
	{
		ObjectMapper om = new ObjectMapper();
		
	/*	TemplateIfBlockDTO ifBlock = new TemplateIfBlockDTO();
		
		TemplateTextContent ttc = new TemplateTextContent();
		ttc.setContent("testVar1");
		
		TemplateTextContent ttc1 = new TemplateTextContent();
		ttc.setContent("testVar2");
		
		ifBlock.setLeft(ttc);
		ifBlock.setRight(ttc1);
		ifBlock.setOperator("eq");
		
		ifBlock.setChildren(new ArrayList<INode>());
		
		ifBlock.setConditions(new ArrayList<TemplateFollowingConditionBlock>());
		
		
		TemplateTextDTO text = new TemplateTextDTO();
		TemplateTextContent textC = new TemplateTextContent();
		textC.setContent("Here comes test 1");
		text.setContents(new ArrayList<TemplateBaseContent>());
		text.getContents().add(textC);
		
		ifBlock.getChildren().add(text);
		
		TemplateFollowingConditionBlock tfcb = new TemplateFollowingConditionBlock();
		TemplateVariableContent left = new TemplateVariableContent();
		TemplateAssessmentVariableDTO assessmentVar = new TemplateAssessmentVariableDTO();
		assessmentVar.setId(999);
		assessmentVar.setMeasureTypeId(3);
		assessmentVar.setTypeId(3);
		left.setContent(assessmentVar);
		tfcb.setLeft(left);
		tfcb.setOperator("result");
		tfcb.setConnector("AND");
		
		ifBlock.getConditions().add(tfcb);
		
		System.out.println(om.writeValueAsString(ifBlock));
		
		System.out.println("freeMarker:"+ifBlock.toFreeMarkerFormat());
		
		
		String jSonStr = om.writeValueAsString(ifBlock);//.replace("\"type\":\"if\",\"c", "\"c");
		
		System.out.println(jSonStr);
		
		ifBlock = om.readValue(jSonStr, TemplateIfBlockDTO.class);
		
		System.out.println("freeMarker:"+ifBlock.toFreeMarkerFormat());*/
		
		TemplateFileDTO fileDTO = new TemplateFileDTO();
		
		Template template = templateRepository.findOne(40);
		
		fileDTO.setId(40);;
		fileDTO.setIsGraphical(template.getIsGraphical());

		fileDTO.setBlocks(new ArrayList<INode>());
		
		TemplateTextDTO text1 = new TemplateTextDTO();
		text1.setName("Depression Screening");
		text1.setSection("1.");
		TemplateTextContent textC1 = new TemplateTextContent();
		textC1.setContent("1. Little interest or pleasure in doing things");
		text1.setContents(new ArrayList<TemplateBaseContent>());
		text1.getContents().add(textC1);
		fileDTO.getBlocks().add(text1);
		
		
		text1 = new TemplateTextDTO();
		text1.setName(null);
		text1.setSection("2.");
		textC1 = new TemplateTextContent();
		textC1.setContent("2. Feeling down, depressed, or hopeless");
		text1.setContents(new ArrayList<TemplateBaseContent>());
		text1.getContents().add(textC1);
		fileDTO.getBlocks().add(text1);
		
		text1 = new TemplateTextDTO();
		text1.setName(null);
		text1.setSection("3.");
		textC1 = new TemplateTextContent();
		textC1.setContent("3. Trouble falling or staying asleep, or sleeping too much");
		text1.setContents(new ArrayList<TemplateBaseContent>());
		text1.getContents().add(textC1);
		fileDTO.getBlocks().add(text1);
		
		
		TemplateTypeDTO typeDTO = new TemplateTypeDTO();
		typeDTO.setId(template.getTemplateType().getTemplateTypeId());
		typeDTO.setTemplateId(40);
		typeDTO.setName(template.getTemplateType().getDescription());
		fileDTO.setType(typeDTO);
		
		System.out.println(om.writeValueAsString(fileDTO));
//		for(int i=0; i<3; i++)
//		System.out.println(fileDTO.getBlocks().get(i).toFreeMarkerFormat());
	}
	
	

}
