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
public class SystemConfigurationController {

	private static final Logger logger = LoggerFactory.getLogger(SystemConfigurationController.class);
	
	@RequestMapping(value = "/systemConfiguration", method = RequestMethod.GET)
	public ModelAndView login(Locale locale, Model model) {
		logger.debug("In systemConfiguration");
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("systemConfiguration");
		return modelAndView;
	}
}
