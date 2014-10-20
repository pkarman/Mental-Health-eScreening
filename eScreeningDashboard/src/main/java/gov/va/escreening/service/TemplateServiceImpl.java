package gov.va.escreening.service;

import freemarker.core.TemplateElement;
import gov.va.escreening.constants.TemplateConstants;
import gov.va.escreening.constants.TemplateConstants.TemplateType;
import gov.va.escreening.dto.TemplateDTO;
import gov.va.escreening.dto.TemplateTypeDTO;
import gov.va.escreening.dto.template.INode;
import gov.va.escreening.dto.template.TemplateFileDTO;
import gov.va.escreening.entity.Battery;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.Template;
import gov.va.escreening.entity.VariableTemplate;
import gov.va.escreening.repository.BatteryRepository;
import gov.va.escreening.repository.SurveyRepository;
import gov.va.escreening.repository.TemplateRepository;
import gov.va.escreening.repository.TemplateTypeRepository;
import gov.va.escreening.repository.VariableTemplateRepository;
import gov.va.escreening.templateprocessor.TemplateProcessorService;
import gov.va.escreening.transformer.TemplateTransformer;

import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TemplateServiceImpl implements TemplateService {

	@Autowired
	private TemplateRepository templateRepository;

	@Autowired
	private TemplateTypeRepository templateTypeRepository;

	@Autowired
	private VariableTemplateRepository variableTemplateRepository;

	@Autowired
	private SurveyRepository surveyRepository;

	@Autowired
	private BatteryRepository batteryRepository;

	@Autowired
	private TemplateProcessorService templateProcessorService;

	@SuppressWarnings("serial")
	private static List<TemplateType> surveyTemplates = new ArrayList<TemplateType>() {
		{
			add(TemplateType.CPRS_ENTRY);
			add(TemplateType.VET_SUMMARY_ENTRY);
			add(TemplateType.VISTA_QA);

		}
	};

	@SuppressWarnings("serial")
	private static List<TemplateType> batteryTemplates = new ArrayList<TemplateType>() {
		{
			add(TemplateType.CPRS_HEADER);
			add(TemplateType.CPRS_FOOTER);
			add(TemplateType.ASSESS_SCORE_TABLE);
			add(TemplateType.ASSESS_CONCLUSION);
			add(TemplateType.VET_SUMMARY_HEADER);
			add(TemplateType.VET_SUMMARY_FOOTER);
		}
	};

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteTemplate(Integer templateId) {
		Template template = templateRepository.findOne(templateId);

		if (template == null) {
			throw new IllegalArgumentException();
		}

		if (surveyTemplates.contains(TemplateConstants.typeForId(template
				.getTemplateType().getTemplateTypeId()))) {
			// need to remove this template from associated survey
			List<Survey> surveys = surveyRepository
					.findByTemplateId(templateId);

			if (surveys != null && surveys.size() > 0) {
				for (Survey survey : surveys) {
					survey.getTemplates().remove(template);
					surveyRepository.update(survey);
				}
			}
		} else {
			// need to remove this template from associated battery

			// find the survey or battery
			List<Battery> batteries = batteryRepository
					.findByTemplateId(templateId);

			if (batteries != null && batteries.size() > 0) {
				for (Battery battery : batteries) {
					battery.getTemplates().remove(template);
					batteryRepository.update(battery);
				}
			}
		}

		templateRepository.deleteById(templateId);
	}

	@Override
	@Transactional(readOnly = true)
	public TemplateDTO getTemplate(Integer templateId) {
		Template template = templateRepository.findOne(templateId);

		if (template == null) {
			return null;
		} else {
			return TemplateTransformer.copyToTemplateDTO(template, null);
		}

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public TemplateDTO updateTemplate(TemplateDTO templateDTO) {

		Template template = templateRepository.findOne(templateDTO
				.getTemplateId());
		if (template == null) {
			throw new IllegalArgumentException("Could not find template");
		}
		TemplateTransformer.copyToTemplate(templateDTO, template);
		templateRepository.update(template);
		return TemplateTransformer.copyToTemplateDTO(template, null);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public TemplateDTO createTemplate(TemplateDTO templateDTO,
			Integer parentId, boolean isSurvey) {
		Template template = TemplateTransformer.copyToTemplate(templateDTO,
				null);

		template.setTemplateType(templateTypeRepository.findOne(templateDTO
				.getTemplateTypeId()));

		if (parentId == null) {
			templateRepository.create(template);
		} else {
			if (isSurvey) {
				Survey survey = surveyRepository.findOne(parentId);
				Set<Template> templateSet = survey.getTemplates();
				survey.setTemplates(addTemplateToSet(templateSet, template,
						surveyTemplates));
				surveyRepository.update(survey);
			} else {
				Battery battery = batteryRepository.findOne(parentId);
				Set<Template> templateSet = battery.getTemplates();
				battery.setTemplates(addTemplateToSet(templateSet, template,
						batteryTemplates));
				batteryRepository.update(battery);
			}
		}

		return TemplateTransformer.copyToTemplateDTO(template, null);

	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void addVariableTemplate(Integer templateId,
			Integer variableTemplateId) {
		Template template = templateRepository.findOne(templateId);
		VariableTemplate variableTemplate = variableTemplateRepository
				.findOne(variableTemplateId);
		template.getVariableTemplateList().add(variableTemplate);
		templateRepository.update(template);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void addVariableTemplates(Integer templateId,
			List<Integer> variableTemplateIds) {
		Template template = templateRepository.findOne(templateId);
		List<VariableTemplate> variableTemplates = variableTemplateRepository
				.findByIds(variableTemplateIds);
		template.getVariableTemplateList().addAll(variableTemplates);
		templateRepository.update(template);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void removeVariableTemplateFromTemplate(Integer templateId,
			Integer variableTemplateId) {
		Template template = templateRepository.findOne(templateId);
		VariableTemplate variableTemplate = variableTemplateRepository
				.findOne(variableTemplateId);
		template.getVariableTemplateList().remove(variableTemplate);
		templateRepository.update(template);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void removeVariableTemplatesFromTemplate(Integer templateId,
			List<Integer> variableTemplateIds) {
		Template template = templateRepository.findOne(templateId);
		List<VariableTemplate> variableTemplates = variableTemplateRepository
				.findByIds(variableTemplateIds);
		template.getVariableTemplateList().removeAll(variableTemplates);
		templateRepository.update(template);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void setVariableTemplatesToTemplate(Integer templateId,
			List<Integer> variableTemplateIds) {
		Template template = templateRepository.findOne(templateId);
		List<VariableTemplate> variableTemplates = variableTemplateRepository
				.findByIds(variableTemplateIds);
		template.setVariableTemplateList(variableTemplates);
		templateRepository.update(template);
	}

	/**
	 * 
	 * Ensure the uniqueness of the template in either survey or battery
	 * 
	 * @param templateSet
	 * @param template
	 * @param uniquTemplateTypes
	 * @return
	 */
	private Set<Template> addTemplateToSet(Set<Template> templateSet,
			Template template, List<TemplateType> uniquTemplateTypes) {
		if (templateSet == null) {
			templateSet = new HashSet<Template>();
			templateSet.add(template);
		} else {
			// first we make sure
			boolean needsToBeUnique = false;

			for (TemplateType tt : uniquTemplateTypes) {
				if (tt.getId() == template.getTemplateType()
						.getTemplateTypeId()) {
					needsToBeUnique = true;
					break;
				}
			}

			if (needsToBeUnique) {
				for (Template t : templateSet) {
					if (t.getTemplateType().getTemplateTypeId() == template
							.getTemplateType().getTemplateTypeId()) {
						templateSet.remove(t);
						break;
					}
				}
			}

			templateSet.add(template);
		}

		return templateSet;
	}

	@Override
	public TemplateDTO getTemplateBySurveyAndTemplateType(Integer surveyId,
			Integer templateTypeId) {

		Template t = templateRepository.getTemplateByIdAndTemplateType(
				surveyId, templateTypeId);
		TemplateDTO dto = null;
		if (t != null) {
			dto = new TemplateDTO();
			dto.setTemplateId(t.getTemplateId());
			dto.setName(t.getName());
			dto.setDateCreated(t.getDateCreated());
			dto.setDescription(t.getDescription());
			dto.setGraphical(t.getIsGraphical());
			dto.setTemplateFile(t.getTemplateFile());
			dto.setTemplateType(templateTypeId);
		}
		return dto;
	}
	
	
	@Override
	public TemplateFileDTO getTemplateFileAsTree(Integer templateId) {
		
			Template t = templateRepository.findOne(templateId);

			if (t == null)
				return null;
			
			// now parsing the template file
			ObjectMapper om = new ObjectMapper();
			
			TemplateFileDTO dto = new TemplateFileDTO();
			
			try
			{
				dto.setBlocks((List<INode>) om.readValue(t.getJsonFile(), List.class));
			}catch(IOException e)
			{
				e.printStackTrace();
				return null;
			}

			dto.setId(templateId);
			dto.setIsGraphical(t.getIsGraphical());

			TemplateTypeDTO ttDTO = new TemplateTypeDTO();
			dto.setType(ttDTO);
			ttDTO.setName(t.getTemplateType().getName());
			ttDTO.setId(t.getTemplateType().getTemplateTypeId());
			ttDTO.setDescription(t.getTemplateType().getDescription());

			/*
			  String templateFile = t.getTemplateFile();

			templateFile = templateFile.replace("${NBSP}", "&nbsp;")
					.replace("${LINE_BREAK}", "<br/>")
					.replace("<#include \"clinicalnotefunctions\">", "");

			try {
				freemarker.template.Template fmTemplate = templateProcessorService
						.getTemplate(templateId, templateFile);
				for (int i = 0; i < fmTemplate.getRootTreeNode()
						.getChildCount(); i++) {
					INode nod = nodeIterate(((TemplateElement) fmTemplate
							.getRootTreeNode().getChildAt(i)), null);
					if (nod == null)
						continue;

					dto.getBlocks().add(nod);
				}

			} catch (IOException e) {
				return null;
			}
			return dto;
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return dto;
	}

	private Properties parseMetaData() {
		String[] data = metaStr.replace("<#--", "").replace("-->", "").trim()
				.split(",");

		Properties p = new Properties();
		for (int i = 0; i < data.length; i++) {
			String dat[] = data[i].split("=");
			p.put(dat[0], dat[1]);
		}

		return p;

	}

	private String metaStr = null;

	private INode nodeIterate(TemplateElement node, List<Long> templateVariables) {

		INode nodeDTO = null;

	/*	String type = node.getClass().getSimpleName();

		String content = node.getCanonicalForm();

		if (!node.isLeaf()) {
			try {
				// nodeDTO.setContent(content.substring(0,
				// content.indexOf(((TemplateElement)node.getChildNodes().get(0)).getCanonicalForm())));

				if (type.equals("IfBlock")) {
					nodeDTO = new TemplateIfBlockDTO();
				} else if (type.equals("ConditionalBlock")) {

					// nodeDTO.setContent(content.substring(0, content
					// .indexOf(((TemplateElement) node.getChildAt(0))
					// .getCanonicalForm())));

					if (content.equals("<#else>")) {
						nodeDTO = new TemplateBaseBlockDTO();
						nodeDTO.setType("elseBlock");
					} else if (content.startsWith("<#if")) {
						nodeDTO = new TemplateConditionBlockDTO();
						nodeDTO.setType("ifBlock");
						
						String formula = content.substring(0, content
								 .indexOf(((TemplateElement) node.getChildAt(0))
								 .getCanonicalForm())).replace("<#if ", "").trim();
						formula = formula.substring(0, formula.length()-1);
						
						// parse the content here
						
						parseFormula(formula, (TemplateConditionBlockDTO)nodeDTO);
						
						
						
					} else {
						nodeDTO = new TemplateConditionBlockDTO();
						nodeDTO.setType("elseIfBlock");
						// parse the content here
					}
				} else {
				}
				// nodeDTO.setContent(content);
				// if (metaStr != null) {
				// Properties p = parseMetaData();
				// nodeDTO.setTitle(p.getProperty("TITLE"));
				// nodeDTO.setSection(p.getProperty("SECTION"));
				// metaStr = null;
				// }

			

			for (int i = 0; i < node.getChildCount(); i++) {
				TemplateElement childTemplateElement = (TemplateElement) node
						.getChildAt(i);
				INode n = nodeIterate(childTemplateElement,
				 templateVariables);
				if (n != null)
				{
					if (((TemplateBaseBlockDTO)nodeDTO).getChildren()==null)
					{
						((TemplateBaseBlockDTO)nodeDTO).setChildren(new ArrayList<INode>());
					}
				 ((TemplateBaseBlockDTO)nodeDTO).getChildren().add(n);
				}
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			if (type.equals("Comment")) {
				metaStr = content;
				return null;
			}

			if (type.equals("TextBlock")) {
				nodeDTO = new TemplateTextDTO();
				nodeDTO.setType(type);
				((TemplateTextDTO) nodeDTO).setContent(content);

				if (metaStr != null) {
					Properties p = parseMetaData();
					((TemplateTextDTO) nodeDTO)
							.setTitle(p.getProperty("TITLE"));
					((TemplateTextDTO) nodeDTO).setSection(p
							.getProperty("SECTION"));
					metaStr = null;
				}

			}
		}*/

		return nodeDTO;
	}
	
	

	@Override
	public Integer saveTemplateFile(Integer surveyId, TemplateFileDTO templateFile){
		Survey survey = surveyRepository.findOne(surveyId);
		
		Template template = new Template();
		
		
		gov.va.escreening.entity.TemplateType templateType = templateTypeRepository.findOne(templateFile.getType().getId());
		template.setTemplateType(templateType);
		
		template.setDateCreated(new Date());
		template.setIsGraphical(templateFile.getIsGraphical());
		
		// save raw json file to the database
		ObjectMapper om = new ObjectMapper();
		try
		{
			template.setJsonFile(om.writeValueAsString(templateFile.getBlocks()));
		}
		catch(IOException e)
		{
			e.printStackTrace();
			template.setJsonFile(null);
		}
		
		template.setTemplateFile(generateFreeMarkerTemplateFile(templateFile.getBlocks()));
		/**
		 * for survey one template per type
		 */
		for(Template t : survey.getTemplates())
		{
			if (t.getTemplateType().getTemplateTypeId().longValue() == templateFile.getType().getId().longValue())
			{
				survey.getTemplates().remove(t);
				break;
			}
		}
		
		survey.getTemplates().add(template);
		
		surveyRepository.update(survey);
		
		return template.getTemplateId();
	}

	private String generateFreeMarkerTemplateFile(List<INode> blocks) {
		StringBuffer file = new StringBuffer();
		file.append("<#include \"clinicalnotefunctions\">\n");
		
		file.append("<#-- generated file. Do not change -->");
		
		for(INode block : blocks)
		{
			file.append(block.toFreeMarkerFormat());
		}
		
		return file.toString();
	}

	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void updateTemplateFile(Integer templateId,
			TemplateFileDTO templateFile) {
		Template template = templateRepository.findOne(templateId);
		
		gov.va.escreening.entity.TemplateType templateType = templateTypeRepository.findOne(templateFile.getType().getId());
		template.setTemplateType(templateType);
		
		template.setDateCreated(new Date());
		template.setIsGraphical(templateFile.getIsGraphical());
		
		// save raw json file to the database
		ObjectMapper om = new ObjectMapper();
		try
		{
			template.setJsonFile(om.writeValueAsString(templateFile.getBlocks()));
		}
		catch(IOException e)
		{
			e.printStackTrace();
			template.setJsonFile(null);
		}
		
		template.setTemplateFile(generateFreeMarkerTemplateFile(templateFile.getBlocks()));

		templateRepository.update(template);
		
	}
}
