package gov.va.escreening.service;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import gov.va.escreening.dto.template.GraphParamsDto;
import gov.va.escreening.dto.template.TemplateFileDTO;
import gov.va.escreening.entity.Template;

public interface TemplateService {

	void deleteTemplate(Integer templateId);

	void addVariableTemplate(Integer templateId, Integer variableTemplateId);

	void addVariableTemplates(Integer templateId,
			List<Integer> variableTemplateIds);

	void removeVariableTemplateFromTemplate(Integer templateId,
			Integer variableTemplateId);

	void removeVariableTemplatesFromTemplate(Integer templateId,
			List<Integer> variableTemplateIds);

	void setVariableTemplatesToTemplate(Integer templateId,
			List<Integer> variableTemplateIds);

	TemplateFileDTO getTemplateFileAsTree(Integer templateId);

	Integer saveTemplateFileForSurvey(Integer surveyId, Integer templateTypeId, TemplateFileDTO templateFile);
	
	Integer saveTemplateFileForBattery(Integer batteryId, Integer templateTypeId, TemplateFileDTO templateFile);

	void updateTemplateFile(Integer templateId, TemplateFileDTO templateFile);
	
	/**
	 * Extracts the json text saved as the graphical parameters for this template and creates
	 * a GraphParamsDto object out of it. 
	 * @param t the template to extract the parameters from
	 * @return the generated graph params object or null if this template doesn't have one
	 * @throws IOException if the graph params text found is invalid
	 */
	GraphParamsDto getGraphParams(Template t) throws JsonParseException, JsonMappingException, IOException;

	/**
	 * Replaces the graph part of a rendered template with a string
	 * @param templateOutput
	 * @param replacement
	 * @return
	 */
    String replaceGraphWith(String templateOutput, String replacement);
}
