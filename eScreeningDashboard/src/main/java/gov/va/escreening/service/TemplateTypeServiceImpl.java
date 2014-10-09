package gov.va.escreening.service;

import gov.va.escreening.dto.ModuleTemplateTypeDTO;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.Template;
import gov.va.escreening.entity.TemplateType;
import gov.va.escreening.repository.SurveyRepository;
import gov.va.escreening.repository.TemplateRepository;
import gov.va.escreening.repository.TemplateTypeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class TemplateTypeServiceImpl implements TemplateTypeService {
	
	@Autowired
	private TemplateRepository templateRepository;
	
	@Autowired 
	private SurveyRepository surveyRepository;
	
	
	@Autowired
	private TemplateTypeRepository templateTypeRepository;
	
    private static final Logger logger = LoggerFactory.getLogger(TemplateTypeServiceImpl.class);	

	@Override
	public List<ModuleTemplateTypeDTO> getModuleTemplateTypes(Integer templateId) {
		
		Template template = templateRepository.findOne(templateId);
		
		int theTemplateTypeId = template.getTemplateType().getTemplateTypeId().intValue();
		
		List<TemplateType> templateTypes = templateTypeRepository.findAllModuleTypeOrderByName();
		List<ModuleTemplateTypeDTO> results = new ArrayList<>(templateTypes.size());
		
		for(TemplateType templateType : templateTypes)
		{
			ModuleTemplateTypeDTO moduleTemplateTypeDTO = new ModuleTemplateTypeDTO();
			moduleTemplateTypeDTO.setTemplateTypeId(templateType.getTemplateTypeId());
			moduleTemplateTypeDTO.setTemplateTypeName(templateType.getName());
			moduleTemplateTypeDTO.setTemplateTypeDescription(templateType.getDescription());			
			if(theTemplateTypeId == templateType.getTemplateTypeId().intValue());
			{
				moduleTemplateTypeDTO.setTemplateID(template.getTemplateId());
			}
			
			results.add(moduleTemplateTypeDTO);
		}
		
		return results;
	}

	@Override
	public List<ModuleTemplateTypeDTO> getModuleTemplateTypesBySurvey(
			Integer surveyId) {
		Survey survey = surveyRepository.findOne(surveyId);
		
		Set<Template> templates = survey.getTemplates();
		
		List<TemplateType> templateTypes = templateTypeRepository.findAllModuleTypeOrderByName();
		List<ModuleTemplateTypeDTO> results = new ArrayList<>(templateTypes.size());
		
		for(TemplateType templateType : templateTypes)
		{
			ModuleTemplateTypeDTO moduleTemplateTypeDTO = new ModuleTemplateTypeDTO();
			moduleTemplateTypeDTO.setTemplateTypeId(templateType.getTemplateTypeId());
			moduleTemplateTypeDTO.setTemplateTypeName(templateType.getName());
			moduleTemplateTypeDTO.setTemplateTypeDescription(templateType.getDescription());
			
			boolean exists = false;
			
			for(Template template : templates)
			{
				if (templateType.getTemplateTypeId().intValue() == template.getTemplateType().getTemplateTypeId().intValue())
				{
					moduleTemplateTypeDTO.setTemplateID(template.getTemplateId());
					break;
				}
			}
			
			results.add(moduleTemplateTypeDTO);
		}
		
		
		return results;
	}

}
