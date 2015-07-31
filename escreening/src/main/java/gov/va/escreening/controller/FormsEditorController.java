package gov.va.escreening.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/dashboard")
public class FormsEditorController {

	private static final Logger logger = LoggerFactory.getLogger(FormsEditorController.class);
	
	@RequestMapping(value = "/formsEditor" , method = RequestMethod.GET)
	public ModelAndView formsEditorDo(Locale locale, Model model) {
		logger.debug("In formsEditor");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("formsEditor");
		return modelAndView;
	}
	
}
