package gov.va.escreening.controller.wysiwyg;


import gov.va.escreening.dto.ae.Measure;
import gov.va.escreening.repository.MeasureRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/wysiwyg")
public class MeasureEditorController 
{
    @Autowired
    MeasureRepository measureRepo;
    
    
    @RequestMapping(value = "/measure/get", method = RequestMethod.GET)
    @ResponseBody
    public List<Measure> getMeasuresForSurvey(@RequestParam int surveyID)
    {
        List<Measure> measureList = measureRepo.getMeasureDtoBySurveyID(surveyID);
        return measureList;
    }

}
