package gov.va.escreening.service;

import static gov.va.escreening.constants.AssessmentConstants.*;
import gov.va.escreening.constants.TemplateConstants;
import gov.va.escreening.constants.TemplateConstants.TemplateType;
import gov.va.escreening.dto.TemplateDTO;
import gov.va.escreening.dto.TemplateTypeDTO;
import gov.va.escreening.dto.template.INode;
import gov.va.escreening.dto.template.TemplateFileDTO;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.Battery;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.Template;
import gov.va.escreening.entity.VariableTemplate;
import gov.va.escreening.exception.IllegalSystemStateException;
import gov.va.escreening.repository.AssessmentVariableRepository;
import gov.va.escreening.repository.BatteryRepository;
import gov.va.escreening.repository.SurveyRepository;
import gov.va.escreening.repository.TemplateRepository;
import gov.va.escreening.repository.TemplateTypeRepository;
import gov.va.escreening.repository.VariableTemplateRepository;
import gov.va.escreening.transformer.TemplateTransformer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

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
	private AssessmentVariableRepository avRepository;
	
	@Autowired
	AssessmentVariableService assessmentVariableService;
	

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
					logger.error("Error reading json file field as template blocks. Template ID: " + t.getTemplateId(), e);
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
 
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public Integer saveTemplateFileForSurvey(Integer surveyId, Integer templateTypeId, TemplateFileDTO templateFile){
		
		Survey survey = surveyRepository.findOne(surveyId);
		
		Integer templateId = createTemplate( templateTypeId,  templateFile,survey.getTemplates() );
		
		surveyRepository.update(survey);
		
		return templateId;
	}
	
	

	private String generateFreeMarkerTemplateFile(List<INode> blocks, Set<Integer>ids) {
		StringBuilder file = new StringBuilder();
		file.append("<#include \"clinicalnotefunctions\">\n");
		
		file.append("<#-- generated file. Do not change -->\n");

		file.append("${MODULE_START}\n");
		for(INode block : blocks){
			file = block.appendFreeMarkerFormat(file, ids, assessmentVariableService);
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
		
		
		template.setIsGraphical(templateFile.getIsGraphical());
		
		if (templateFile.getBlocks() == null || templateFile.getBlocks().isEmpty())
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
			catch(IOException e) {
				logger.error("Error setting block data", e);
				template.setJsonFile(null);
			}
			
			Set<Integer> assessmentVariableIds = new HashSet<Integer>();
			
			template.setTemplateFile(generateFreeMarkerTemplateFile(templateFile.getBlocks(), assessmentVariableIds));			
			setVariableTemplates(template, assessmentVariableIds);
		}
		
		if (template.getTemplateFile()==null)
		{
			template.setTemplateFile("");
		}

		templateRepository.update(template);
		
	}
	
	/**
	 * 
	 * @param templateTypeId
	 * @param templateFile
	 * @param templates
	 * @return
	 * @throws IllegalSystemStateException if a measure was found which does not have an associated assessment variable
	 */
	private Integer createTemplate(Integer templateTypeId, TemplateFileDTO templateFile, Set<Template> templates ) throws IllegalSystemStateException{
		
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
			setVariableTemplates(template, ids);
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

	/**
	 * 
	 * Adds VariableTemplate objects for the given set of assessment variable IDs
	 * @param template
	 * @param ids
	 */
	private void setVariableTemplates(Template template, Set<Integer> ids) {
		//clear out list of variable template entries
		
		//map from AV ID to VariableTemplate (previously saved)
		Map<Integer, VariableTemplate> currentVtMap = Collections.emptyMap();
		
		if (template.getVariableTemplateList()==null){
			template.setVariableTemplateList(new ArrayList<VariableTemplate>());
		}
		else{
			currentVtMap = Maps.newHashMapWithExpectedSize(template.getVariableTemplateList().size()); 
			for(VariableTemplate vt : template.getVariableTemplateList()){
				currentVtMap.put(vt.getAssessmentVariableId().getAssessmentVariableId(), vt);
			}
			template.getVariableTemplateList().clear();
		}
		
		Set<Integer> addedAvIds = new HashSet<>();
		
		for(Integer id : ids){
			AssessmentVariable av = currentVtMap.containsKey(id) ? currentVtMap.get(id).getAssessmentVariableId() : avRepository.findOne(id);
			addVariableTemplate(template, av, currentVtMap, addedAvIds);
			
			//Add measure specific VariableTemplates
			if(av.getAssessmentVariableTypeId().getAssessmentVariableTypeId() == ASSESSMENT_VARIABLE_TYPE_MEASURE){
    			Measure measure = av.getMeasure();
    			addAnswerVariableTemplates(template, measure, currentVtMap, addedAvIds);
    			
    			//check for child questions to add
				if(measure.isParent() && measure.getChildren() != null){
					for(Measure child : measure.getChildren()){
						AssessmentVariable childAv = child.getAssessmentVariable();
						addVariableTemplate(template, childAv, currentVtMap, addedAvIds);
						addAnswerVariableTemplates(template, child, currentVtMap, addedAvIds);
					}
				}
			}
		}
	}
	
	/**
	 * Adds a variableTemplate to the given template if it has not bee added already. Uses previously saved VT if there is one
	 * @param template
	 * @param vt
	 * @param addedAvIds
	 */
	private void addVariableTemplate(Template template, AssessmentVariable av, Map<Integer, VariableTemplate> currentVtMap, Set<Integer>addedAvIds){
	    
	    Integer avId = av.getAssessmentVariableId();
	    if(!addedAvIds.contains(avId)){
	        VariableTemplate vt;
            if(currentVtMap.containsKey(avId)){
                vt = currentVtMap.get(avId);
            }
            else{
                vt = new VariableTemplate(av, template);
            }
	        
            template.getVariableTemplateList().add(vt);
            addedAvIds.add(avId);
        }
	}
	
	private void addAnswerVariableTemplates(Template template, Measure measure, Map<Integer, VariableTemplate> currentVtMap, Set<Integer>addedAvIds){
		for(MeasureAnswer ma : measure.getMeasureAnswerList()){
		    addVariableTemplate(template, ma.getAssessmentVariable(), currentVtMap, addedAvIds);
		}
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
}
