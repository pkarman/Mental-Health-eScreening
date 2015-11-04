package gov.va.escreening.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping("/")
    public String setupForm() {

        logger.trace("root page request");
        
        return "index";
    }
    
    @RequestMapping("/home")
    public String home() {

        logger.trace("root home requested");
        
        return setupForm();
    }
}
