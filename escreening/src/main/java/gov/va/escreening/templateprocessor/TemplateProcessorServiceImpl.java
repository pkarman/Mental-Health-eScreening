package gov.va.escreening.templateprocessor;

import static com.google.common.base.Preconditions.checkArgument;
import static gov.va.escreening.templateprocessor.TemplateTags.*;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateModelException;
import freemarker.template.Version;
import gov.va.escreening.constants.TemplateConstants;
import gov.va.escreening.constants.TemplateConstants.DocumentType;
import gov.va.escreening.constants.TemplateConstants.TemplateType;
import gov.va.escreening.constants.TemplateConstants.ViewType;
import gov.va.escreening.context.VeteranAssessmentSmrList;
import gov.va.escreening.dto.ae.ErrorBuilder;
import gov.va.escreening.dto.template.GraphParamsDto;
import gov.va.escreening.entity.Battery;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.SurveySection;
import gov.va.escreening.entity.Template;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.exception.EntityNotFoundException;
import gov.va.escreening.exception.IllegalSystemStateException;
import gov.va.escreening.exception.TemplateProcessorException;
import gov.va.escreening.expressionevaluator.ExpressionExtentionUtil;
import gov.va.escreening.repository.SurveyRepository;
import gov.va.escreening.repository.SurveySectionRepository;
import gov.va.escreening.repository.VeteranAssessmentRepository;
import gov.va.escreening.service.TemplateService;
import gov.va.escreening.service.VeteranAssessmentService;
import gov.va.escreening.templateprocessor.TemplateTags.Style;
import gov.va.escreening.variableresolver.AssessmentVariableDto;
import gov.va.escreening.variableresolver.VariableResolverService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collection;
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
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.google.common.io.Files;

@Transactional(noRollbackFor = { TemplateProcessorException.class, Exception.class })
public class TemplateProcessorServiceImpl implements TemplateProcessorService {

	@Autowired
	private VariableResolverService variableResolverService;
	
	@Autowired
	private SurveySectionRepository surveySectionRepository;
	
	@Autowired
	private VeteranAssessmentRepository veteranAssessmentRepository;
	
	@Autowired
	private SurveyRepository surveyRepository;
	
	@Autowired
	private TemplateService templateService;
	
	@Resource
    VeteranAssessmentService assessmentService;

	@Resource(name="veteranAssessmentSmrList")
	VeteranAssessmentSmrList smrLister;
	
	static final String FILE_ENCODING = "UTF-8";
	private static final Logger logger = LoggerFactory.getLogger(TemplateProcessorServiceImpl.class);
	
	private static final Pattern freemarkerTags = Pattern.compile("\\s*(</*#[\\p{Graph}\\p{Space}&&[^>]]+>)\\s*");
	private static final Pattern escreeningTags = Pattern.compile("\\s*" + createTagRegex(Style.FREEMARKER_VAR, TemplateTags.values()) + "\\s*");


	// The freemarker manual can be found here:
	// http://freemarker.org/docs/index.html
	// The freemarker configuration instance will be kept at the instance level
	// as a variety of templates will be constantly passed in, and
	// may be modified so we will not worry about caching.
	private Configuration freemarkerConfiguration = null;

	private synchronized Configuration getFreemarkerConfiguration() throws IOException, TemplateModelException {
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
	
	@Override
	public String renderSurveyTemplate(int surveyId, TemplateType type, VeteranAssessment veteranAssessment, ViewType viewType) throws IllegalSystemStateException {
		Survey survey = surveyRepository.findOne(surveyId);
		checkArgument(survey != null, String.format("Module not found with ID %s", surveyId));
		
		for(Template t : survey.getTemplates()){
			if(type.getId() == t.getTemplateType().getTemplateTypeId()){
				String text = new TemplateEvaluator(veteranAssessment, viewType)
					.appendTemplate(t)
					.generate();
				logger.trace("Rendered module template:\n{}", text);
				return text;
			}
		}
		
		ErrorBuilder
			.throwing(EntityNotFoundException.class)
			.toUser(String.format("No template of type %s is defined for module %s. Please have the technical administrator use the template editor to create one.", type, survey.getName()))
			.toAdmin(String.format("No template of type %s is defined for module: %s. Please use the template editor to create one.", type, survey))
			.throwIt();	
		
		//since last statement will throw an exception we will never get here
		return null;
	}
	
	@Override
	public String renderBatteryTemplate(Battery battery, TemplateType type, VeteranAssessment veteranAssessment, ViewType viewType)
			throws IllegalSystemStateException {
		for(Template t : battery.getTemplates()){
			if(type.getId() == t.getTemplateType().getTemplateTypeId()){
				String text = new TemplateEvaluator(veteranAssessment, viewType)
					.appendTemplate(t)
					.generate();
				logger.trace("Rendered battery template:\n{}", text);
				return text;
			}
		}
		
		ErrorBuilder
			.throwing(EntityNotFoundException.class)
			.toUser("No template available for battery. Please contact support.")
			.toAdmin(String.format("No template of type %s is defined for battery: %s. Please use the template editor to create one.", type, battery.getName()))
			.throwIt();	
		
		//since last statement will throw an exception we will never get here
		return null;
	}
	
	/**
	 * method to reset the {@link ThreadLocal} of VeteranAssessmentSmrList#clearSmrFromCache()}, as threads are not created here but served from the thread pool
	 * @param veteranAssessmentId
	 * @param viewType
	 * @param cprsNote
	 * @param optionalTemplates
	 * @param includeSections
	 * @return
	 * @throws IllegalSystemStateException
	 */
	private String createDocument(int veteranAssessmentId, ViewType viewType,
			DocumentType cprsNote, EnumSet<TemplateType> optionalTemplates,
			boolean includeSections) throws IllegalSystemStateException {
		smrLister.clearSmrFromCache();
		String docAsString=createDocumentNow(veteranAssessmentId, viewType, cprsNote, optionalTemplates, includeSections);
		smrLister.clearSmrFromCache();
		return docAsString;
	}

	/**
	 * Renders an entire document containing a header, entries, footer, and any optional templates
	 * @param veteranAssessmentId the assessment the document is being created for
	 * @param viewType the type of rendering
	 * @param documentType the type of document that is to be built (e.g. cprs note, or veteran summary printout)
	 * @param optionalTemplates set of optional template types to be included in rendered template 
	 * @param includeSections if true then sections should be surrounded by section tags. If templates are find to be 
	 * graphical, then they will not be appended in their section.  They will be appended to the end. So includeSections
	 * should be false when graphical templates are included.
	 * @return the rendered document
	 * @throws IllegalSystemStateException
	 */
	private String createDocumentNow(int veteranAssessmentId, ViewType viewType, DocumentType documentType, 
			EnumSet<TemplateType> optionalTemplates, boolean includeSections) throws IllegalSystemStateException{
		
		//get assessment
		VeteranAssessment assessment = veteranAssessmentRepository.findOne(veteranAssessmentId);
		checkArgument(assessment != null, "Assessment ID is invalid");
		
		//get Battery level templates
		Set<TemplateType> requiredBatteryTemplateTypes = Sets.union(documentType.getRequiredBatteryTypes(), 
                    Sets.difference(optionalTemplates, TemplateConstants.moduleTemplateTypes()));
		Map<TemplateType, Template> templateMap = getTemplateMap(assessment, requiredBatteryTemplateTypes);
				
		// start generation of template with header, section, templates for each
		// module in a section, and in the end the footer
		TemplateEvaluator evaluator = new TemplateEvaluator(assessment, viewType)
			.appendHeader(templateMap.get(documentType.getHeaderType()));
		
		StringBuilder quesAndAnswers = new StringBuilder();
		StringBuilder cprsHistory = new StringBuilder();
		
		Map<Integer, Survey> surveysTaken = assessment.getSurveyMap();
		List<SurveySection> sections = surveySectionRepository.findForVeteranAssessmentId(veteranAssessmentId);
		List<Template> graphicalTemplates = new LinkedList<Template>();
		for (SurveySection section : sections) {
			boolean sectionStarted = false;
			
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
						else if(type.equals(TemplateType.VISTA_QA) 
						        && optionalTemplates.contains(TemplateType.VISTA_QA)){
							quesAndAnswers.append(processTemplate(template, assessment));
						}
						else if(type.equals(TemplateType.CPRS_PROGRESS_HISTORY)
						        && documentType.getRequiredTypes().contains(TemplateType.CPRS_PROGRESS_HISTORY)){
						    cprsHistory.append(processTemplate(template, assessment));
						}
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
		
		/* Add Veteran Progress templates grouped together */
		if(documentType.getRequiredTypes().contains(TemplateType.CPRS_PROGRESS_HISTORY)){
		    evaluator.appendGroupedTemplates("Historical Values", cprsHistory.toString());
		}
		
		/* Add optional VistA questions/answer text */
		if(optionalTemplates.contains(TemplateType.VISTA_QA)) {
			evaluator.appendGroupedTemplates("Clinical Reminders", quesAndAnswers.toString());
		}
		
		return evaluator.appendFooter(templateMap.get(documentType.getFooterType())).generate();
	}	
	
	/**
	 * 
	 * @param template
	 * @param assessmentId
	 * @return
	 * @throws IllegalSystemStateException if the content of the template cannot be retrieved
	 * @throws TemplateProcessorException if there is a problem getting the assessment variables for the template or an error during rendering
	 */
	private String processTemplate(Template template, VeteranAssessment assessment) throws IllegalSystemStateException {
		String templateText = getTemplateText(template);
		Integer templateId = template.getTemplateId();
		// It is valid to get an empty list of AssessmentVariables back as the
		// questions might not have been answered yet.
        List<AssessmentVariableDto> assessmentVariables = variableResolverService
                .resolveVariablesForTemplateAssessment(assessment, template);
		if (assessmentVariables == null)
            throwError(String.format(
                    "No AssessmentVariables are defined for assessment with ID: %s, and Template with ID: %s",
                    assessment.getVeteranAssessmentId(), templateId));

		String templateOutput = null;
		try {//TODO: This should be updated to have a ResolveParameters object passed into it. This allows us to reused the same parameters object which should reduce processing times.
			templateOutput = processTemplate(templateText, assessmentVariables, templateId);
			if(TemplateConstants.typeForId(template.getTemplateType().getTemplateTypeId()).equals(TemplateType.CPRS_PROGRESS_HISTORY)){
			    templateOutput = insertProgressHistoryGraph(template, assessment, templateOutput);
			}    
		}
        catch (Exception e) {
            throwError(String.format("Processing templateId: %s for veteranassessmentid: %s failed with the "
                    + "following exception: %s", templateId, assessment.getVeteranAssessmentId(), e.getMessage()));
        }

		return templateOutput;
	}

	private String insertProgressHistoryGraph(Template template,  VeteranAssessment assessment, String templateOutput) {
        if(!template.getIsGraphical() || Strings.nullToEmpty(templateOutput).trim().isEmpty())
            return templateOutput;
        
        //create translate graph params object into object 
        GraphParamsDto graphParams = null;
        try {
            graphParams = templateService.getGraphParams(template);
        } catch (IOException e) {
            ErrorBuilder.throwing(IllegalSystemStateException.class)
            .toAdmin("The template " + template.getName() 
                    + " (" +  template.getTemplateId() 
                    + ") has an invalid graph_params field. Error is: " + e.getLocalizedMessage())
            .toUser("There was an unexpected error during template rendering. Please call support.")
            .throwIt();
        }
        
        if(graphParams == null)
            return templateOutput;
        
        //get values for the veteran 
        Map<String, Double> historicalValues = assessmentService.getVeteranAssessmentVariableSeries(
                assessment.getVeteran().getVeteranId(), 
                graphParams.getVarId(), 
                graphParams.getNumberOfMonths());  
        
        if(historicalValues.size() < 2)
        	return "";
        
        //generate graph
        String historicalGraph = VeteranProgressHistoryAsciiGraph.generateHistoricalGraph(template.getName(), graphParams, historicalValues);
        historicalGraph = PRE_START.xml() + historicalGraph + PRE_END.xml();
        historicalGraph = historicalGraph.replaceAll(" ", NBSP.xml());
        historicalGraph = historicalGraph.replaceAll("\n", LINE_BREAK.xml());
        
        //replace graph portion with ascii graph
        return templateService.replaceGraphWith(templateOutput, historicalGraph);
    }

    /**
	 * Queries the DB to get the required battery level templates and returns a map from template type to the template instance.
	 * @param assessment
	 * @param requiredTemplates the battery level templates to include
	 * @return
	 * @throws IllegalSystemStateException
	 */
	private Map<TemplateType, Template> getTemplateMap(VeteranAssessment assessment, Set<TemplateType> requiredTemplates)
			throws IllegalSystemStateException{
		
		EnumMap<TemplateType, Template> templateMap = new EnumMap<>(TemplateConstants.TemplateType.class);

		Battery battery = assessment.getBattery();

		if (battery != null) {
			for (Template template : battery.getTemplates()) {
				TemplateType type = TemplateConstants.typeForId(template.getTemplateType().getTemplateTypeId());
				
				if(requiredTemplates.contains(type)){
					templateMap.put(type, template);
					
					//TODO: Commenting out this shortcut just in case we forget to remove the VISTA_QA temp code above. Please uncomment when we go to vista question answer
					if(templateMap.size() == requiredTemplates.size())
						break;
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
		
		if(templateMap.size() != requiredTemplates.size()) {
		    Set<TemplateType> missingTemplates = Sets.difference(requiredTemplates, templateMap.keySet());
		    
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

	//TODO: This should be updated to have a ResolveParameters object passed into it. This allows us to reused the same parameters object which should reduce processing times.
	String processTemplate(String templateText,
			Collection<AssessmentVariableDto> assessmentVariables, int templateId) throws IOException, TemplateException {

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
		Writer out = new OutputStreamWriter(outStream, FILE_ENCODING);
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
				.toAdmin("A required template, could not be found in the database.").throwIt();

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
		//logger.trace("Template text before shrinking:\n{}", templateText);
		
		String noSpace = freemarkerTags.matcher(templateText).replaceAll("$1");//remove space around freemarker tags
		noSpace = escreeningTags.matcher(noSpace).replaceAll("$1"); 		   //remove space around template tags
		
		//logger.trace("Template text after shrinking:\n{}", noSpace);
		
		return noSpace;
	}

	private Configuration initializeFreemarkerConfig() throws IOException, TemplateModelException {
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
		
		// Set the expression utility object (carries out the template editor operations)
		cfg.setSharedVariable("util", new ExpressionExtentionUtil());

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
		private final VeteranAssessment veteranAssessment;
		private final ViewType viewType;
		private final StringBuilder text = new StringBuilder("");
		private boolean sectionStarted = false;

		private TemplateEvaluator(VeteranAssessment veteranAssessment, ViewType viewType) {
			this.veteranAssessment = veteranAssessment;
			this.viewType = viewType;
		}

		private void endPreviousSection() {
			if (sectionStarted) {
				logger.trace("Ending section");
				text.append(SECTION_END.xml());
				sectionStarted = false;
			}
		}

		private TemplateEvaluator startSection(SurveySection section) {
			endPreviousSection();

			logger.trace("Starting section {}", section);

			text.append(SECTION_TITLE_START.xml())
				.append(section.getName())
				.append(SECTION_TITLE_END.xml())
				.append(SECTION_START.xml());

			sectionStarted = true;
			return this;
		}

		private TemplateEvaluator appendHeader(Template headerTemplate) throws IllegalSystemStateException {
			endPreviousSection();
			logger.trace("Appending header template {}", headerTemplate);
			text.append(BATTERY_HEADER_START.xml());
			appendTemplate(headerTemplate);
			text.append(BATTERY_HEADER_END.xml());
			return this;
		}

		private TemplateEvaluator appendFooter(Template footerTemplate) throws IllegalSystemStateException {
			endPreviousSection();
			logger.trace("Appending footer template {}", footerTemplate);
			text.append(BATTERY_FOOTER_START.xml());
			appendTemplate(footerTemplate);
			text.append(BATTERY_FOOTER_END.xml());
			return this;
		}
		
		private TemplateEvaluator appendModule(Template moduleTemplate) throws IllegalSystemStateException {
			String templateText = processTemplate(moduleTemplate, veteranAssessment);
			if(!templateText.trim().isEmpty()){ // only append if there is content
				logger.trace("Appending module template {}", moduleTemplate);
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
			logger.trace("Appending template {}", moduleTemplate.getName());
			text.append(processTemplate(moduleTemplate, veteranAssessment));
			return this;
		}
		
	    private TemplateEvaluator appendGroupedTemplates(String sectionLabel, String templateGroupText) throws IllegalSystemStateException{
	        if(!Strings.isNullOrEmpty(templateGroupText)){
                endPreviousSection();
                logger.trace("Appending {} section", sectionLabel);
                startSection(new SurveySection(null, sectionLabel, null));
                text.append(templateGroupText);
                endPreviousSection();
                logger.trace("Completed {} section", sectionLabel);
	        }
            return this;
        }

		private String generate() {
			endPreviousSection();
			logger.trace("Resolving template tags for viewType {}", viewType);
			return TemplateTagProcessor.resolveClinicalNoteTags(text.toString(), viewType);
		}
	}
}

