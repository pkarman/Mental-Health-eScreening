package gov.va.escreening.transformer;

import java.util.Date;

import gov.va.escreening.dto.TemplateDTO;
import gov.va.escreening.entity.Template;

public class TemplateTransformer {

	public static Template copyToTemplate(TemplateDTO templateDTO,
			Template template) {
		if (template == null) {
			template = new Template();
		}
		template.setName(templateDTO.getName());
		template.setDescription(templateDTO.getDescription());
		template.setIsGraphical(templateDTO.isGraphical());
		template.setTemplateFile(templateDTO.getTemplateFile());
		template.setTemplateId(templateDTO.getTemplateId());

		if (templateDTO.getDateCreated() == null) {
			template.setDateCreated(new Date());
		} else {
			template.setDateCreated(templateDTO.getDateCreated());
		}

		return template;
	}

	public static TemplateDTO copyToTemplateDTO(Template template,
			TemplateDTO templateDTO) {
		if (templateDTO == null) {
			templateDTO = new TemplateDTO();
		}

		templateDTO.setDateCreated(template.getDateCreated());
		templateDTO.setDescription(template.getDescription());
		templateDTO.setGraphical(template.getIsGraphical());
		templateDTO.setName(template.getName());
		templateDTO.setTemplateFile(template.getTemplateFile());
		templateDTO.setTemplateId(template.getTemplateId());

		return templateDTO;
	}
}
