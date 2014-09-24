package gov.va.escreening.templateprocessor;

import static com.google.common.base.Preconditions.checkArgument;
import static gov.va.escreening.templateprocessor.TemplateTags.BATTERY_FOOTER_END;
import static gov.va.escreening.templateprocessor.TemplateTags.BATTERY_FOOTER_START;
import static gov.va.escreening.templateprocessor.TemplateTags.BATTERY_HEADER_END;
import static gov.va.escreening.templateprocessor.TemplateTags.BATTERY_HEADER_START;
import static gov.va.escreening.templateprocessor.TemplateTags.MODULE_COMPONENTS_END;
import static gov.va.escreening.templateprocessor.TemplateTags.MODULE_COMPONENTS_START;
import static gov.va.escreening.templateprocessor.TemplateTags.SECTION_END;
import static gov.va.escreening.templateprocessor.TemplateTags.SECTION_START;
import static gov.va.escreening.templateprocessor.TemplateTags.SECTION_TITLE_END;
import static gov.va.escreening.templateprocessor.TemplateTags.SECTION_TITLE_START;
import static gov.va.escreening.templateprocessor.TemplateTags.createTagRegex;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import gov.va.escreening.constants.TemplateConstants;
import gov.va.escreening.constants.TemplateConstants.DocumentType;
import gov.va.escreening.constants.TemplateConstants.TemplateType;
import gov.va.escreening.constants.TemplateConstants.ViewType;
import gov.va.escreening.context.VeteranAssessmentSmrList;
import gov.va.escreening.dto.ae.ErrorBuilder;
import gov.va.escreening.entity.Battery;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.SurveySection;
import gov.va.escreening.entity.Template;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.exception.IllegalSystemStateException;
import gov.va.escreening.exception.TemplateProcessorException;
import gov.va.escreening.repository.SurveySectionRepository;
import gov.va.escreening.repository.TemplateRepository;
import gov.va.escreening.repository.VeteranAssessmentRepository;
import gov.va.escreening.service.SurveyMeasureResponseService;
import gov.va.escreening.templateprocessor.TemplateTags.Style;
import gov.va.escreening.variableresolver.AssessmentVariableDto;
import gov.va.escreening.variableresolver.VariableResolverService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import com.google.common.io.Files;

@Transactional(noRollbackFor = { TemplateProcessorException.class, Exception.class })
public class TemplateProcessorServiceImpl implements TemplateProcessorService {

	@Autowired
	private TemplateRepository templateRepository;
	@Autowired
	private VariableResolverService variableResolverService;
	@Autowired
	SurveySectionRepository surveySectionRepository;
	@Autowired
	private VeteranAssessmentRepository veteranAssessmentRepository;
	
	@Autowired
	private SurveyMeasureResponseService surveyMeasureRespSvc;

	@Resource(name="veteranAssessmentSmrList")
	VeteranAssessmentSmrList smrLister;
	
	private static final String FILE_ENCODING = "UTF-8";
	private static final Logger logger = LoggerFactory.getLogger(TemplateProcessorServiceImpl.class);
	
	private static final Pattern freemarkerTags = Pattern.compile("\\s*(</*#[\\p{Graph}\\p{Space}&&[^>]]+>)\\s*");
	private static final Pattern escreeningTags = Pattern.compile("\\s*" + createTagRegex(Style.FREEMARKER_VAR, TemplateTags.values()) + "\\s*");


	// The freemarker manual can be found here:
	// http://freemarker.org/docs/index.html
	// The freemarker configuration instance will be kept at the instance level
	// as a variety of templates will be constantly passed in, and
	// may be modified so we will not worry about caching.
	private Configuration freemarkerConfiguration = null;

	private synchronized Configuration getFreemarkerConfiguration() throws IOException {
		if (freemarkerConfiguration == null)
			freemarkerConfiguration = initializeFreemarkerConfig();
		return freemarkerConfiguration;
	}

	@Override
	public String generateVeteranPrintout(int veteranAssessmentId) 
			throws IllegalSystemStateException, TemplateProcessorException {
		
		return createDocument(veteranAssessmentId, ViewType.HTML, DocumentType.VET_SUMMARY, 
				EnumSet.noneOf(TemplateConstants.TemplateType.class), false); 
	}
	
	@Override 
	public String generateCPRSNote(int veteranAssessmentId, ViewType viewType) throws IllegalSystemStateException, TemplateProcessorException {
		return generateCPRSNote(veteranAssessmentId, viewType, EnumSet.noneOf(TemplateConstants.TemplateType.class));
	}
	
	@Override
	public String generateCPRSNote(int veteranAssessmentId, ViewType viewType, EnumSet<TemplateType> optionalTemplates) 
			throws IllegalSystemStateException, TemplateProcessorException{
		
		return createDocument(veteranAssessmentId, viewType, DocumentType.CPRS_NOTE, optionalTemplates, true); 
	}
	
	
	/**
	 * Renders an entire document containing a header, entries, footer, and any optional templates
	 * @param assessment the assessment the document is being created for
	 * @param viewType the type of rendering
	 * @param documentType the type of document that is to be built (e.g. cprs note, or veteran summary printout)
	 * @param templateMap map from template type to the template to render
	 * @param includeSections if true then sections should be surrounded by section tags. If templates are find to be 
	 * graphical, then they will not be appended in their section.  They will be appended to the end. So includeSections
	 * should be false when graphical templates are included.
	 * @return the rendered document
	 * @throws IllegalSystemStateException
	 */
	private String createDocument(int veteranAssessmentId, ViewType viewType, DocumentType documentType, 
			EnumSet<TemplateType> optionalTemplates, boolean includeSections) throws IllegalSystemStateException{
		
		smrLister.clearSmrFromCache();
		
		//get assessment
		VeteranAssessment assessment = veteranAssessmentRepository.findOne(veteranAssessmentId);
		checkArgument(assessment != null, "Assessment ID is invalid");
		
		//get Battery level templates
		Map<TemplateType, Template> templateMap = getTemplateMap(assessment, 
				Sets.union(EnumSet.of(documentType.getHeaderType(), documentType.getFooterType()), optionalTemplates));
				
		// start generation of template with header, section, templates for each
		// module in a section, and in the end the footer
		TemplateEvaluator evaluator = new TemplateEvaluator(veteranAssessmentId, viewType)
			.appendHeader(templateMap.get(documentType.getHeaderType()));
		
		StringBuilder quesAndAnswers = new StringBuilder();
		
		Map<Integer, Survey> surveysTaken = assessment.getSurveyMap();
		List<SurveySection> sections = surveySectionRepository.findForVeteranAssessmentId(veteranAssessmentId);
		List<Template> graphicalTemplates = new LinkedList();
		for (SurveySection section : sections) {
			boolean sectionStarted = false;
			
			// start section
//			if(includeSections)
//				evaluator.startSection(section);
			
			// append templates in section-order for each survey found in the battery
			for (Survey survey : section.getSurveyList()) {
				if (surveysTaken.containsKey(survey.getSurveyId())) {
					for (Template template : survey.getTemplates()) {
						if(!sectionStarted)
						{
							if(includeSections)
								evaluator.startSection(section);
							sectionStarted = true;
						}
						TemplateType type = TemplateConstants.typeForId(template.getTemplateType().getTemplateTypeId());
						if(type.equals(documentType.getEntryType())) {
							if(template.getIsGraphical()){
								graphicalTemplates.add(template);
							}
							else{
								evaluator.appendModule(template);
							}
						}
					}
					
                    if (templateMap.containsKey(TemplateType.VISTA_QA) 
                    		&& survey.getClinicalReminderSurveyList() != null 
                    		&& (!survey.getClinicalReminderSurveyList().isEmpty())){
                        quesAndAnswers.append(surveyMeasureRespSvc.generateQuestionsAndAnswers(survey, veteranAssessmentId));
                    }
				}
			}
		}
		
		/* now add graphical module templates after all the rest */
		for(Template template : graphicalTemplates){
			evaluator.appendModule(template);
		}
		
		/* Add optional Assessment Scoring Table */
		if(templateMap.containsKey(TemplateType.ASSESS_SCORE_TABLE)){
			evaluator.appendTemplate(templateMap.get(TemplateType.ASSESS_SCORE_TABLE));
		}
		
		/* Add optional VistA questions/answer text */
		if(templateMap.containsKey(TemplateType.VISTA_QA)) {
			evaluator.appendQuestionsAndAnswers(quesAndAnswers.toString());
		}
		
		return evaluator.appendFooter(templateMap.get(documentType.getFooterType())).generate();
	}	
	
	private String processTemplate(Template template, Integer assessmentId) throws IllegalSystemStateException {
		String templateText = getTemplateText(template);
		Integer templateId = template.getTemplateId();
		
		// It is valid to get an empty list of AssessmentVariables back as the
		// questions might not have been answered yet.
        List<AssessmentVariableDto> assessmentVariables = variableResolverService
                .resolveVariablesForTemplateAssessment(assessmentId, templateId);
		if (assessmentVariables == null)
            throwError(String.format(
                    "No AssessmentVariables are defined for assessment with ID: %s, and Template with ID: %s",
                    assessmentId, templateId));

		String templateOutput = null;
		try {
			templateOutput = processTemplate(templateText, assessmentVariables, templateId);
		}
        catch (Exception e) {
            throwError(String.format("Processing templateId: %s for veteranassessmentid: %s failed with the "
                    + "following exception: %s", templateId, assessmentId, e.getMessage()));
        }

		return templateOutput;
	}

	/**
	 * Queries the DB to get the required templates and returns a map from template type to the template instance.
	 * @param assessment
	 * @param requiredTemplates
	 * @return
	 * @throws IllegalSystemStateException
	 */
	private Map<TemplateType, Template> getTemplateMap(VeteranAssessment assessment, Set<TemplateType> requiredTemplates)
			throws IllegalSystemStateException{
		
		EnumMap<TemplateType, Template> templateMap = new EnumMap<>(TemplateConstants.TemplateType.class);
		
		//TODO: PLEASE remove this when we go to a template system for questions and answers
		if(requiredTemplates.contains(TemplateType.VISTA_QA)){ 
			templateMap.put(TemplateType.VISTA_QA, new Template());
		}

		Battery battery = assessment.getBattery();

		if (battery != null) {
			for (Template template : battery.getTemplates()) {
				TemplateType type = TemplateConstants.typeForId(template.getTemplateType().getTemplateTypeId());
				
				if(requiredTemplates.contains(type)){
					templateMap.put(type, template);
					
					//TODO: Commenting out this shortcut just in case we forget to remove the VISTA_QA temp code above. Please uncomment when we go to vista question answer
//					if(templateMap.size() == requiredTemplates.size())
//						break;
				}
			}
		} 
		else {
			ErrorBuilder
			.throwing(IllegalSystemStateException.class)
			.toUser("No battery was found for this assessment. Please contact the technical administrator.")
			.toAdmin("The following veteran assessment does not have any battery created for assessment: " + assessment)
			.throwIt();
		}

		// throw a system configuration exception if we don't have all required templates
		Set<TemplateType> missingTemplates = Sets.difference(requiredTemplates, templateMap.keySet());
		if(templateMap.size() != requiredTemplates.size()) {
			String errorMsg = "For battery '" + battery.getName()
					+ "' the following required templates are missing: " +Joiner.on(',').join(missingTemplates);
			
			ErrorBuilder
				.throwing(IllegalSystemStateException.class)
				.toUser(errorMsg + ". Please contact the technical administrator.")
				.toAdmin(errorMsg + ".  Battery details: " + battery)
				.throwIt();
		}
		
		return templateMap;
	}
	
	/**
     * Throws a TemplateProcessorException with a generic error message and the given technical message. This should be
     * used when a bug is found.
	 * 
	 * @param technicalMessage
	 */
	private void throwError(String technicalMessage) {
		ErrorBuilder
			.throwing(TemplateProcessorException.class)
			.toUser("An error occured while processing one or more template.  Please contact the technical administrator for assistance.")
			.toAdmin(technicalMessage).throwIt();
	}

	private String processTemplate(String templateText,
			List<AssessmentVariableDto> assessmentVariables, int templateId) throws IOException, TemplateException {

		// populate the root object which holds all beans to be merged with the
		// template.
		SimpleHash root = new SimpleHash();
		for (AssessmentVariableDto variableDto : assessmentVariables) {
			root.put(variableDto.getKey(), variableDto);
		}

		// Convert the template to a string, load it into the freemarker
		// configuration
		Configuration fmConfiguration = getFreemarkerConfiguration();
		String templateCacheName = String.format("template%s", templateId);
		((StringTemplateLoader) fmConfiguration.getTemplateLoader()).putTemplate(templateCacheName, templateText);
		freemarker.template.Template resolvedTemplate = fmConfiguration.getTemplate(templateCacheName);

		// process the freemarker template and convert it's output to a string
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		String templateOutput = new String();
		Writer out = new OutputStreamWriter(outStream);
		resolvedTemplate.process(root, out);
		templateOutput = new String(outStream.toByteArray(), FILE_ENCODING);

		return templateOutput;
	}

	/**
	 * @param templateId
	 * @return a string containing the template text
	 * @throws IllegalSystemStateException
     * @throws IllegalStateException if the Id given is invalid
	 */
	private String getTemplateText(Template templateToProcess) throws IllegalSystemStateException {
		if (templateToProcess == null)
			ErrorBuilder
				.throwing(IllegalSystemStateException.class)
				.toUser("A needed template does not exist. Please contact the technical administrator.")
				.toAdmin(String.format("A required template with ID: %s, could not be found in the database.", templateToProcess)).throwIt();

		String templateText = templateToProcess.getTemplateFile();
		if (templateText == null)
			ErrorBuilder
				.throwing(IllegalSystemStateException.class)
				.toUser(String.format("Template %s does not contain a definition. Please contact the technical administrator.", templateToProcess.getName()))
				.toAdmin(String.format("A required template with ID: %s, was found but its contents are null (i.e. not set).", templateToProcess.getTemplateId())).throwIt();

		templateText = removeTempateSpace(templateText);
		
		return templateText;
	}
	
	private String removeTempateSpace(String templateText){
		//logger.debug("Template text before shrinking:\n{}", templateText);
		
		String noSpace = freemarkerTags.matcher(templateText).replaceAll("$1");//remove space around freemarker tags
		noSpace = escreeningTags.matcher(noSpace).replaceAll("$1"); 		   //remove space around template tags
		
		//logger.debug("Template text after shrinking:\n{}", noSpace);
		
		return noSpace;
	}

	private Configuration initializeFreemarkerConfig() throws IOException {
		Configuration cfg = new Configuration();

		// We have to use the string loader because we are storing the templates
		// in the database and are calling including templates
		// within the templates. If we don't use string loader then the include
		// will not be able to resolve the location of the
		// referenced template.
		StringTemplateLoader stringLoader = null;
		if ((cfg.getTemplateLoader() == null) || !(cfg.getTemplateLoader() instanceof StringTemplateLoader)) {
			stringLoader = new StringTemplateLoader();
			cfg.setTemplateLoader(stringLoader);
        }
        else
			stringLoader = (StringTemplateLoader) cfg.getTemplateLoader();

		// add utility templates which will be included in other templates
        appendUtilityTemplateToConfig(stringLoader, "clinicalnotefunctions",
                "templates/ClinicalNoteTemplateFunctions.ftl");

		// Specify how templates will see the data-model. This is an advanced
		// topic...
		// for now just use this:
		cfg.setObjectWrapper(new DefaultObjectWrapper());

		// Set your preferred charset template files are stored in. UTF-8 is
		// a good choice in most applications:
		cfg.setDefaultEncoding(FILE_ENCODING);

		// Sets how errors will appear. Here we assume we are developing HTML
		// pages.
		// For production systems TemplateExceptionHandler.RETHROW_HANDLER is
		// better.
		// cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

		// At least in new projects, specify that you want the fixes that aren't
		// 100% backward compatible too (these are very low-risk changes as far
		// as the
		// 1st and 2nd version number remains):
		cfg.setIncompatibleImprovements(new Version(2, 3, 20)); // FreeMarker
																// 2.3.

		return cfg;
	}

	private void appendUtilityTemplateToConfig(
			StringTemplateLoader stringLoader, String templateName,
			String relativeTemplatePath) throws IOException {
		String clinicalNoteFunctions = getUtilityFile(relativeTemplatePath);
		String templateCacheName = templateName;
		stringLoader.putTemplate(templateCacheName, clinicalNoteFunctions);
	}

	/**
	 * 
	 * @param relativeTemplatePath
	 * @return
     * @throws IOException If the file with the given path does not exist or there is an error reading it
	 */
	private String getUtilityFile(String relativeTemplatePath) throws IOException {
		File templateFile = new ClassPathResource(relativeTemplatePath).getFile();
		return Files.toString(templateFile, Charsets.UTF_8);
	}

	/**
     * Template evaluation class which you append headers, footers, module templates, and sections to a, then call
     * generate to render everything into a string.
	 * 
	 * Note: The generator can only be used once. This class is not threadsafe.
	 * 
	 * @author Robin Carnow
	 */

	private class TemplateEvaluator {
		private final int assessmentId;
		private final ViewType viewType;
		private final StringBuilder text = new StringBuilder("");
		private boolean sectionStarted = false;

		private TemplateEvaluator(int assessmentId, ViewType viewType) {
			this.assessmentId = assessmentId;
			this.viewType = viewType;
		}

		private void endPreviousSection() {
			if (sectionStarted) {
				logger.debug("Ending section");
				text.append(SECTION_END.xml());
				sectionStarted = false;
			}
		}

		private TemplateEvaluator startSection(SurveySection section) {
			endPreviousSection();

			logger.debug("Starting section {}", section);

			text.append(SECTION_TITLE_START.xml())
				.append(section.getName())
				.append(SECTION_TITLE_END.xml())
				.append(SECTION_START.xml());

			sectionStarted = true;
			return this;
		}

		private TemplateEvaluator appendHeader(Template headerTemplate) throws IllegalSystemStateException {
			endPreviousSection();
			logger.debug("Appending header template {}", headerTemplate);
			text.append(BATTERY_HEADER_START.xml());
			appendTemplate(headerTemplate);
			text.append(BATTERY_HEADER_END.xml());
			return this;
		}

		private TemplateEvaluator appendFooter(Template footerTemplate) throws IllegalSystemStateException {
			endPreviousSection();
			logger.debug("Appending footer template {}", footerTemplate);
			text.append(BATTERY_FOOTER_START.xml());
			appendTemplate(footerTemplate);
			text.append(BATTERY_FOOTER_END.xml());
			return this;
		}
		
		private TemplateEvaluator appendModule(Template moduleTemplate) throws IllegalSystemStateException {
			String templateText = processTemplate(moduleTemplate, assessmentId);
			if(!templateText.trim().isEmpty()){ // only append if there is content
				logger.debug("Appending module template {}", moduleTemplate);
				text.append(MODULE_COMPONENTS_START.xml())
					.append(templateText)
					.append(MODULE_COMPONENTS_END.xml());
			}
			return this;
		}

		/**
		 * Appends the given template if it has any contents
		 * @param moduleTemplate
		 * @return true if the template had contents 
		 * @throws IllegalSystemStateException
		 */
		private TemplateEvaluator appendTemplate(Template moduleTemplate) throws IllegalSystemStateException {
			logger.debug("Appending template {}", moduleTemplate.getName());
			text.append(processTemplate(moduleTemplate, assessmentId));
			return this;
		}
		
	    private TemplateEvaluator appendQuestionsAndAnswers(String s){
            endPreviousSection();
            logger.debug("Appending clinical reminder questions");
            startSection(new SurveySection(null, "CLINICAL REMINDERS", null));
            text.append(s);
            endPreviousSection();
            logger.debug("Completed clinical reminder questions");
            return this;
        }

		private String generate() {
			endPreviousSection();
			logger.debug("Resolving template tags for viewType {}", viewType);
			return TemplateTagProcessor.resolveClinicalNoteTags(text.toString(), viewType);
		}
	}

	@Override
	public String generateCompletionMsgFor(int batteryId) {
		String tt="<div class='moduleTemplateTitle'>";
		String closeDiv="</div>";
		String brk="<br/>";
		String st="<div class='templateSectionTitle'>";
		
		StringBuilder sb = new StringBuilder();
		sb.append(tt).append("Thank You!").append(closeDiv);
		sb.append(brk);
		sb.append(st).append("Please let the assistant know that you have completed your screen. They will provide you with a personalized summary of your screens. The results of this screen will be sent electronically to a Transition Case Manager for review.").append(closeDiv);
		sb.append(brk);
		sb.append(st).append("The goal of OEF/OIF/OND Care Management is to help you maximize your VA services and benefits. Here are some services that we provide: ").append(closeDiv);
		sb.append(brk);
		
		sb.append(st).append("- Care coordination and support with access to VA healthcare services & benefits").append(closeDiv);
		sb.append(st).append("- Advocacy to address post-deployment health concerns").append(closeDiv);
		sb.append(st).append("- Resources to address  employment, education or housing  concerns  ").append(closeDiv);
		sb.append(st).append("- Applying for VA, other government, and community benefits").append(closeDiv);
		sb.append(st).append("- Resources for marriage, family, and spirituality concerns ").append(closeDiv);
		sb.append(st).append("- Aid with concerns about drinking or drug use").append(closeDiv);
		sb.append(st).append("- Assistance if you are feeling sad, depressed or anxious").append(closeDiv);
		sb.append(st).append("- Assistance with visual impairments").append(closeDiv);
		sb.append(st).append("- Help if you really aren’t sure what you need, but things just don’t feel right").append(closeDiv);
		sb.append(brk);

		sb.append(st).append("You may ask to meet with a Transition Case Manager today to discuss any issues presented in this screen. You can also call the OEF/OIF/OND Care Management team at any point in the future for assistance. Their contact information is listed on your personalized summary.").append(closeDiv);

		return sb.toString();
	}

}

