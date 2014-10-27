package gov.va.escreening.assessments.test;

import static org.junit.Assert.*;

import java.util.List;

import gov.va.escreening.domain.BatteryDto;
import gov.va.escreening.dto.ae.Answer;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.MeasureValidation;
import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.entity.SurveyPage;
import gov.va.escreening.entity.SurveySection;
import gov.va.escreening.entity.Validation;
import gov.va.escreening.entity.Veteran;
import gov.va.escreening.repository.BatteryRepository;
import gov.va.escreening.repository.MeasureRepository;
import gov.va.escreening.repository.SurveyMeasureResponseRepository;
import gov.va.escreening.repository.SurveyPageRepository;
import gov.va.escreening.repository.SurveySectionRepository;
import gov.va.escreening.repository.VeteranRepository;
import gov.va.escreening.service.BatteryService;
import gov.va.escreening.service.SurveyService;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional //this is to ensure all tests do not leave trace, so they are repeatable.
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class RepositoryTest {
    @Resource
    SurveyPageRepository surveyPageRepo;
    
    @Resource
    SurveyMeasureResponseRepository smrRepo;
    
    @Resource
    SurveySectionRepository surveySectionRepo;
    
    @Resource
    BatteryRepository batteryRepo;
    
    @Resource(type = BatteryService.class)
    BatteryService bs;
    
    @Resource(type=SurveyService.class)
    SurveyService ss;
    
    @Resource
    MeasureRepository measureRepo;
    
    @Resource
    VeteranRepository veteranRepo;
    
    @Test
    public void testgetSurveyPage()
    {
        List<SurveyPage> result =surveyPageRepo.getSurveyPagesForVeteranAssessmentId(59);
        assertNotNull(result);
        
        int[] measureIdInOrder = {341,342,343,344,346,347,348,349,350};
        
        //test measure ordering
        SurveyPage sp = surveyPageRepo.findOne(51);
        
        List<Measure> measures = sp.getMeasures();
        
        for(int i=0; i<measureIdInOrder.length; i++)
        {
        	assertTrue(measureIdInOrder[i] == measures.get(i).getMeasureId());
        }
    }
    
    @Test
    public void checkSurveyPage()
    {
    	List<SurveyPage> all = surveyPageRepo.findAll();
    	
    	for(SurveyPage p : all)
    	{
    		if(p.getMeasures() !=null)
    		{
    			for(Measure m : p.getMeasures())
    			{
    				if(m==null)
    				{
    					fail("page has null measures: "+p.getSurveyPageId());
    				}
    			}
    		}
    	}
    }
    
    @Test
    public void testgetSurveyMeasureResponse()
    {
        smrRepo.getForVeteranAssessmentAndSurvey(56, 2);
    }

    @Test
    public void testSurveySectionRepo()
    {
        List<SurveySection> result = surveySectionRepo.findForVeteranAssessmentId(60);
        assertNotNull(result);
    }
    
    @Test
    public void testBatteryDelete() {
        
        List<BatteryDto> batteries = bs.getBatteryDtoList();
        int totalbatteiesBeforedelete = batteries.size();

        bs.delete(4);

        List<BatteryDto> batteries1 = bs.getBatteryDtoList();
        int totalbatteiesAfterdelete = batteries1.size();

        Assert.assertTrue(totalbatteiesAfterdelete == totalbatteiesBeforedelete - 1);

        
    }
    
    @Test
    public void testMeasureRepo()
    {
        Measure m = measureRepo.findOne(1);
        m.setVistaText("TEST VISTA");
        
        measureRepo.update(m);
        
        m= measureRepo.findOne(1);
        assertEquals("TEST VISTA", m.getVistaText());
        
    }
    
    @Test
    public void testMeasureValidation()
    {
    	Measure m = measureRepo.findOne(1);
    	assertEquals(1, m.getMeasureValidationList().size());
    	
    	gov.va.escreening.dto.ae.Measure mdto = new gov.va.escreening.dto.ae.Measure(m, null, null);
    	
    	List<gov.va.escreening.dto.ae.Validation> vdtoList = mdto.getValidations();
    	
    	vdtoList.clear();
    	gov.va.escreening.dto.ae.Validation newValidation = new gov.va.escreening.dto.ae.Validation();
    	newValidation.setName("minLength");
    	newValidation.setValue("6");
    	
    	
    	vdtoList.add(newValidation);
    	
    	measureRepo.updateMeasure(mdto);
    	
    	m = measureRepo.findOne(1);
    	assertEquals(1, m.getMeasureValidationList().size());
    	
    	MeasureValidation mv = m.getMeasureValidationList().get(0);
    	assertEquals(new Integer(6), mv.getNumberValue());
    	
    }
    
    @Test
    public void testMeasureAnswer()
    {
    	Measure m = measureRepo.findOne(10);
    	
    	gov.va.escreening.dto.ae.Measure mdto = new gov.va.escreening.dto.ae.Measure(m, null, null);
    	
    	Answer a = mdto.getAnswers().get(0);
    	a.setExportName("testExport");
    	a.setAnswerText("TEST ANSWER TEXT");
    	a.setVistaText("Test Vista text");
    	a.setAnswerType("NO TYPE");
    	
    	measureRepo.updateMeasure(mdto);
    	
    	m = measureRepo.findOne(10);
    	
    	MeasureAnswer ma = m.getMeasureAnswerList().get(0);
    	
    	assertEquals("testExport", ma.getExportName());
    	assertEquals("TEST ANSWER TEXT", ma.getAnswerText());
    	assertEquals("Test Vista text", ma.getVistaText());
    	assertEquals("NO TYPE", ma.getAnswerType());
    }
    
    @Test
    public void testVeteranRepo()
    {
    	Veteran v = veteranRepo.findOne(17);
    	assertFalse(v.getIsSensitive());
    	
    	v.setIsSensitive(true);
    	veteranRepo.update(v);
    	
    	v = veteranRepo.findOne(17);
    	assertTrue(v.getIsSensitive());
    }
    
    @Test
    public void testSS()
    {
    	List a = ss.getSurveyPages(2);
    	assertEquals(a.size(), 3);
    }
}
