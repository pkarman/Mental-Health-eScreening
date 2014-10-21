package gov.va.escreening.template;

import java.util.ArrayList;

import javax.annotation.Resource;

import gov.va.escreening.dto.TemplateTypeDTO;
import gov.va.escreening.dto.template.INode;
import gov.va.escreening.dto.template.TemplateFileDTO;
import gov.va.escreening.dto.template.TemplateFollowingConditionBlock;
import gov.va.escreening.dto.template.TemplateIfBlockDTO;
import gov.va.escreening.dto.template.TemplateTextDTO;
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
		
		TemplateIfBlockDTO ifBlock = new TemplateIfBlockDTO();
		
		ifBlock.setLeft("testVar1");
		ifBlock.setRight("testVar2");
		ifBlock.setOperator("eq");
		
		ifBlock.setChildren(new ArrayList<INode>());
		
		ifBlock.setConditions(new ArrayList<TemplateFollowingConditionBlock>());
		
		
		TemplateTextDTO text = new TemplateTextDTO();
		text.setContent("Here comes test 1");
		
		ifBlock.getChildren().add(text);
		
		TemplateFollowingConditionBlock tfcb = new TemplateFollowingConditionBlock();
		tfcb.setLeft("testVar1");
		tfcb.setOperator("result");
		tfcb.setConnector("AND");
		
		ifBlock.getConditions().add(tfcb);
		
		System.out.println(om.writeValueAsString(ifBlock));
		
		System.out.println("freeMarker:"+ifBlock.toFreeMarkerFormat());
		
		
		String jSonStr = om.writeValueAsString(ifBlock);//.replace("\"type\":\"if\",\"c", "\"c");
		
		System.out.println(jSonStr);
		
		ifBlock = om.readValue(jSonStr, TemplateIfBlockDTO.class);
		
		System.out.println("freeMarker:"+ifBlock.toFreeMarkerFormat());
		
		TemplateFileDTO fileDTO = new TemplateFileDTO();
		
		Template template = templateRepository.findOne(40);
		
		fileDTO.setId(40);;
		fileDTO.setIsGraphical(template.getIsGraphical());
		fileDTO.setBlocks(new ArrayList<INode>());
		fileDTO.getBlocks().add(ifBlock);
		TemplateTypeDTO typeDTO = new TemplateTypeDTO();
		typeDTO.setId(template.getTemplateType().getTemplateTypeId());
		typeDTO.setTemplateId(40);
		typeDTO.setName(template.getTemplateType().getDescription());
		fileDTO.setType(typeDTO);
		
		System.out.println(om.writeValueAsString(fileDTO));
	
		
	}
	
	

}
