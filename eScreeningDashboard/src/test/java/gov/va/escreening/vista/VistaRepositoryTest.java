package gov.va.escreening.vista;

import static org.junit.Assert.*;
import gov.va.escreening.entity.HealthFactor;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.repository.HealthFactorRepository;
import gov.va.escreening.repository.SurveyRepository;
import gov.va.escreening.repository.VistaRepository;
import gov.va.escreening.service.VistaService;
import gov.va.escreening.vista.dto.DialogComponent;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Transactional
public class VistaRepositoryTest {
    
    private static final String ESCREENING_TEST = "ESCREENING_TEST";
    private static final Logger logger = Logger.getLogger("VistaRepositoryTest");
    public static final String DIVISION = "500";
    public static final String DUZ = "10000000056";
    
    @Resource
    VistaRepository vistaRepo;
    
    @Resource(name="vistaService")
    VistaService vistaServiceImpl;
    
    @Resource
    HealthFactorRepository healthFactorRepo;
    
    @Resource
    SurveyRepository surveyRepo;
    
    @Test
    public void testGetClinicalReminder()
    {
        List<DialogComponent> result = vistaRepo.getClinicalReminderDialogs(DIVISION, null, DUZ, ESCREENING_TEST, "56");
        
        logger.info(result.toString());
        
    }
    
    @Test
    public void testGetNoteTitles()
    {
        vistaRepo.getNoteTitles(DIVISION, null, DUZ, ESCREENING_TEST);
    }
    
    @Test
    public void testGetDialogPrompt()
    {
        vistaRepo.getDialogPrompt(DIVISION, null, DUZ, ESCREENING_TEST, "3084", false, "");
    }
    
    @Test
    @Transactional
    public void testVistaSvc()
    {   
        assertEquals(2, vistaServiceImpl.refreshHealthFactors(DIVISION, null, DUZ, ESCREENING_TEST));        
    }
    
    @Test
    @Transactional
    public void testRefreshMH()
    {
        Survey s = surveyRepo.findOne(26); //Audit-C
        
        s.setMhaResultGroupIen(null);
        surveyRepo.update(s);
        
        surveyRepo.commit();
        
        s = surveyRepo.findOne(26);
        assertNull(s.getMhaResultGroupIen());
        
        assertEquals(1, vistaServiceImpl.refreshMHAIens(DIVISION, null, DUZ, null));
        
        s = surveyRepo.findOne(26);
        assertNotNull(s.getMhaResultGroupIen());
    }
    
    @Test
    public void testGetMHA()
    {
        logger.info(vistaRepo.getMHATestDetail(DIVISION, null, DUZ, null, "GAD-7"));
        logger.info(vistaRepo.getMHATestDetail(DIVISION, null, DUZ, null, "WHODAS 2"));
    }
    
    @Test
    public void testRefreshHF()
    {
        vistaServiceImpl.refreshHealthFactors(DIVISION, null, DUZ, null);
    }
}
