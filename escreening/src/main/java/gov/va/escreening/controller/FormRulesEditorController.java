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
public class FormRulesEditorController {
	private static final Logger logger = LoggerFactory.getLogger(FormRulesEditorController.class);
	
	@RequestMapping(value = "/formRulesEditor" , method = RequestMethod.GET)
	public ModelAndView rulesEditorDo(Locale locale, Model model) {
		logger.debug("In formRulesEditor");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("formRulesEditor");
		return modelAndView;
	}
}
