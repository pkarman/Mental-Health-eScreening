package gov.va.escreening.service;

import freemarker.core.TemplateElement;
import gov.va.escreening.constants.TemplateConstants;
import gov.va.escreening.constants.TemplateConstants.TemplateType;
import gov.va.escreening.dto.MeasureAnswerDTO;
import gov.va.escreening.dto.MeasureValidationSimpleDTO;
import gov.va.escreening.dto.TemplateDTO;
import gov.va.escreening.dto.TemplateTypeDTO;
import gov.va.escreening.dto.template.INode;
import gov.va.escreening.dto.template.TemplateFileDTO;
import gov.va.escreening.entity.Battery;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.MeasureValidation;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.Template;
import gov.va.escreening.entity.VariableTemplate;
import gov.va.escreening.repository.AssessmentVariableRepository;
import gov.va.escreening.repository.BatteryRepository;
import gov.va.escreening.repository.MeasureAnswerRepository;
import gov.va.escreening.repository.MeasureRepository;
import gov.va.escreening.repository.SurveyRepository;
import gov.va.escreening.repository.TemplateRepository;
import gov.va.escreening.repository.TemplateTypeRepository;
import gov.va.escreening.repository.VariableTemplateRepository;
import gov.va.escreening.templateprocessor.TemplateProcessorService;
import gov.va.escreening.transformer.TemplateTransformer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TemplateServiceImpl implements TemplateService {
	private static final Logger logger = LoggerFactory.getLogger(TemplateServiceImpl.class);

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
	
	@Autowired
	private AssessmentVariableRepository avRepository;
	
	@Autowired
	private MeasureAnswerRepository measureAnswerRepository;
	
	@Autowired
	private MeasureRepository measureRepository;

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
		template.setModifiedDate(new Date());
		templateRepository.update(template);
		return TemplateTransformer.copyToTemplateDTO(template, null);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public TemplateDTO createTemplate(TemplateDTO templateDTO, Integer templateTypeId, 
			Integer parentId, boolean isSurvey) {
		Template template = TemplateTransformer.copyToTemplate(templateDTO,	null);

		template.setTemplateType(templateTypeRepository.findOne(templateTypeId));
		template.setModifiedDate(new Date());

		if (parentId == null) {
			templateRepository.create(template);
		} else {
			if (isSurvey) {
				Survey survey = surveyRepository.findOne(parentId);
				Set<Template> templateSet = survey.getTemplates();
				survey.setTemplates(addTemplateToSet(templateSet, template,
						surveyTemplates));
				surveyRepository.update(survey);
				template.setName("Survey "+template.getTemplateType().getName());
			} else {
				Battery battery = batteryRepository.findOne(parentId);
				Set<Template> templateSet = battery.getTemplates();
				battery.setTemplates(addTemplateToSet(templateSet, template,
						batteryTemplates));
				template.setName("Battery "+template.getTemplateType().getName());
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
		template.setModifiedDate(new Date());
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
		template.setModifiedDate(new Date());
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
			
			TemplateFileDTO dto = new TemplateFileDTO();
			
			dto.setJson(t.getJsonFile());
			
			if (t.getJsonFile() != null)
			{
				// now parsing the template file
				ObjectMapper om = new ObjectMapper();
				try
				{
					dto.setBlocks(Arrays.asList( om.readValue(t.getJsonFile(), INode[].class)));
				}catch(IOException e)
				{
					e.printStackTrace();
					return null;
				}
			}else{
				if (t.getTemplateFile() != null && t.getTemplateFile().length() > 0 ){
					dto.setBlocks(null);
				}
			}
				
			
			dto.setName(t.getName());
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
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public Integer saveTemplateFileForSurvey(Integer surveyId, Integer templateTypeId, TemplateFileDTO templateFile){
		
		Survey survey = surveyRepository.findOne(surveyId);
		
		Integer templateId = createTemplate( templateTypeId,  templateFile,survey.getTemplates() );
		
		surveyRepository.update(survey);
		
		return templateId;
	}
	
	

	private String generateFreeMarkerTemplateFile(List<INode> blocks, Set<Integer>ids) {
		StringBuffer file = new StringBuffer();
		file.append("<#include \"clinicalnotefunctions\">\n");
		
		file.append("<#-- generated file. Do not change -->\n");

		file.append("${MODULE_START}\n");
		for(INode block : blocks)
		{
			file.append(block.toFreeMarkerFormat(ids));
		}
		file.append("\n${MODULE_END}\n");
		
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
		
		if (templateFile.getBlocks() == null || templateFile.getBlocks().size() == 0)
		{
			template.setJsonFile(null);
		}
		else
		{
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
			
			Set<Integer> assessmentVariableIds = new HashSet<Integer>();
			
			
			//get current variable template entries for this template
			Map<Integer, VariableTemplate> vtMap = new HashMap<>();
			for(VariableTemplate vt : template.getVariableTemplateList()){
				vtMap.put(vt.getAssessmentVariableId().getAssessmentVariableId(), vt);
			}
			
			template.setTemplateFile(generateFreeMarkerTemplateFile(templateFile.getBlocks(), assessmentVariableIds));
			
			//clear out list of variable template entries
			if (template.getVariableTemplateList()==null){
				template.setVariableTemplateList(new ArrayList<VariableTemplate>());
			}
			else{
				template.getVariableTemplateList().clear();
			}
			
			if (!assessmentVariableIds.isEmpty()){
				
				for(Integer id : assessmentVariableIds){
					VariableTemplate vt;
					if(vtMap.containsKey(id)){
						vt = vtMap.get(id);
					}
					else{
						vt = new VariableTemplate();
						vt.setAssessmentVariableId(avRepository.findOne(id));
						vt.setTemplateId(template);
					}
					template.getVariableTemplateList().add(vt);
				}
			}
		}
		
		if (template.getTemplateFile()==null)
		{
			template.setTemplateFile("");
		}

		templateRepository.update(template);
		
	}
	
	private Integer createTemplate(Integer templateTypeId, TemplateFileDTO templateFile, Set<Template> templates ){
		
		Template template = new Template();
		
		gov.va.escreening.entity.TemplateType templateType = templateTypeRepository.findOne(templateTypeId);
		template.setTemplateType(templateType);
		
		template.setDateCreated(new Date());
		template.setModifiedDate(new Date());
		template.setIsGraphical(templateFile.getIsGraphical());
		template.setName(templateFile.getName());
		
		// save raw json file to the database
		if (templateFile.getBlocks() == null || templateFile.getBlocks().size() == 0)
		{
			template.setJsonFile(null);
		}
		else
		{
			ObjectMapper om = new ObjectMapper();
			try
			{
				template.setJsonFile(om.writeValueAsString(templateFile.getBlocks()));
			}
			catch(IOException e)
			{
				logger.error("Error setting json blocks into template", e); 
				e.printStackTrace();
				template.setJsonFile(null);
			}
			Set<Integer> ids = new HashSet<Integer>();
			
			template.setTemplateFile(generateFreeMarkerTemplateFile(templateFile.getBlocks(), ids));
			
			if (ids.size()>0)
			{
				for(Integer id : ids){
					VariableTemplate vt = new VariableTemplate();
					vt.setDateCreated(new Date());
					vt.setAssessmentVariableId(avRepository.findOne(id));
					vt.setTemplateId(template);
					if (template.getVariableTemplateList()==null)
					{
						template.setVariableTemplateList(new ArrayList<VariableTemplate>());
					}
					template.getVariableTemplateList().add(vt);
				}
			}
		 }	
		
		if (template.getTemplateFile()==null)
		{
			template.setTemplateFile("");
		}
		/**
		 * for survey one template per type
		 */
		for(Template t : templates)
		{
			if (t.getTemplateType().getTemplateTypeId().longValue() == templateFile.getType().getId().longValue())
			{
				templates.remove(t);
				break;
			}
		}
		
		templates.add(template);
		
		return template.getTemplateId();
		
	}
	

	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public Integer saveTemplateFileForBattery(Integer batteryId,
			Integer templateTypeId, TemplateFileDTO templateFile) {
		Battery battery = batteryRepository.findOne(batteryId);
		
		Integer templateId = createTemplate(templateTypeId, templateFile, battery.getTemplates());
		batteryRepository.update(battery);
		
		return templateId;
	}

	@Override
	public List<MeasureAnswerDTO> getMeasureAnswerValues(Integer measureId) {
		List<MeasureAnswer> answers = measureAnswerRepository.getAnswersForMeasure(measureId);
		
		List<MeasureAnswerDTO> answerDTOs = new ArrayList<MeasureAnswerDTO> ();
		if (answers!=null && answers.size() >0){
			for (MeasureAnswer a : answers )
			{
				MeasureAnswerDTO aDTO = new MeasureAnswerDTO();
				BeanUtils.copyProperties(a, aDTO);
				answerDTOs.add(aDTO);
			}
		}
		
		return answerDTOs;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<MeasureValidationSimpleDTO> getMeasureValidations(
			Integer measureId) {
		gov.va.escreening.entity.Measure measure = measureRepository.findOne(measureId);
		
		List<MeasureValidationSimpleDTO> results = new ArrayList<MeasureValidationSimpleDTO>();
		
		if (measure!=null && measure.getMeasureValidationList()!=null && measure.getMeasureValidationList().size()>0){
			for(MeasureValidation mv : measure.getMeasureValidationList()){
				MeasureValidationSimpleDTO sDTO = new MeasureValidationSimpleDTO();
				
				sDTO.setValidateId(mv.getValidation().getValidationId());
				if (mv.getBooleanValue()!=null){
					sDTO.setValue(mv.getBooleanValue()+"");
				}else if (mv.getNumberValue()!=null){
					sDTO.setValue(mv.getNumberValue()+"");
				}
				else{
					sDTO.setValue(mv.getTextValue());
				}
				
				results.add(sDTO);
			}
		}
		return results;
	}
}
