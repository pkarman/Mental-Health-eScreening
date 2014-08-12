package gov.va.escreening.assessments.test;

import static org.junit.Assert.*;

import java.util.List;

import gov.va.escreening.domain.BatteryDto;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.entity.SurveyPage;
import gov.va.escreening.entity.SurveySection;
import gov.va.escreening.repository.BatteryRepository;
import gov.va.escreening.repository.MeasureRepository;
import gov.va.escreening.repository.SurveyMeasureResponseRepository;
import gov.va.escreening.repository.SurveyPageRepository;
import gov.va.escreening.repository.SurveySectionRepository;
import gov.va.escreening.service.BatteryService;

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
    
    @Resource
    MeasureRepository measureRepo;
    
    @Test
    public void testgetSurveyPage()
    {
        List<SurveyPage> result =surveyPageRepo.getSurveyPagesForVeteranAssessmentId(59);
        assertNotNull(result);
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
}
