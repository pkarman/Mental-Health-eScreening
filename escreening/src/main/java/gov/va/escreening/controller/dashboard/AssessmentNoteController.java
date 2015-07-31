package gov.va.escreening.controller.dashboard;


import gov.va.escreening.constants.TemplateConstants.ViewType;
import gov.va.escreening.exception.IllegalSystemStateException;
import gov.va.escreening.exception.TemplateProcessorException;
import gov.va.escreening.repository.VeteranAssessmentRepository;
import gov.va.escreening.templateprocessor.TemplateProcessorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/dashboard")
public class AssessmentNoteController {

	@Autowired
    private TemplateProcessorService templateProcessorService;
	@Autowired
    private VeteranAssessmentRepository veteranAssessmentRepository; 
    
    private static final Logger logger = LoggerFactory.getLogger(AssessmentNoteController.class);
    
    private static final String ERROR_DIV = "<div class='alert alert-danger'>";
    private static final String WARN_DIV = "<div class='assessmentNoteWarning'>";
    
    @RequestMapping(value = "/assessmentNote", method = RequestMethod.GET)
    public String setupPage(Model model, @RequestParam(value = "vaid", required = false) Integer veteranAssessmentId) {

        logger.debug("In AssessmentNoteController::setupPage");
        logger.debug("veteranAssessmentId: " + veteranAssessmentId);
        
        try {
        	String generatedNote = templateProcessorService.generateCPRSNote(veteranAssessmentId, ViewType.HTML);
        	model.addAttribute("assessmentClinicalNotes", generatedNote);
        }
       	catch(TemplateProcessorException tpe) {
       		logger.error(tpe.getErrorResponse().getLogMessage(), tpe);
       		model.addAttribute("errorMessage", ERROR_DIV + tpe.getErrorResponse().getUserMessage("<br/>") + "</div>");
    	}
        catch(IllegalSystemStateException isse){
            logger.error(isse.getErrorResponse().getLogMessage(), isse);
            model.addAttribute("errorMessage", ERROR_DIV + isse.getErrorResponse().getUserMessage("<br/>") + "</div>"); 
        }
        catch(Exception e) {
        	logger.error(String.format("%s %s", e.getClass(), e.getMessage()), e);
        	model.addAttribute("errorMessage", ERROR_DIV + "An error occured while generating the veteran summary printout.  Please contact an administrator for assistance.</div>");
        }

        return "dashboard/assessmentNote";
    }
}