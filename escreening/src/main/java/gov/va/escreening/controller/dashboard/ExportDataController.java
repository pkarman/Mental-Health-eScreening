package gov.va.escreening.controller.dashboard;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/dashboard")
public class ExportDataController {
	
	private static final Logger logger = LoggerFactory.getLogger(ExportDataController.class);
		
	@RequestMapping(value = "exportData")
	public ModelAndView exportData(Locale locale, Model model) {
		logger.debug("In exportData ");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("exportData");
		return modelAndView;
	}
}
