package gov.va.escreening.service;

import gov.va.escreening.entity.HealthFactor;
import gov.va.escreening.entity.Rule;
import gov.va.escreening.repository.HealthFactorRepository;
import gov.va.escreening.repository.RuleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by mzhu on 9/5/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/spring/root-context.xml"
})
@Transactional
public class EventServiceImplTest {
    @Resource
    private EventService eventService;

    @Resource
    private RuleRepository ruleRepository;

    @Resource
    private HealthFactorRepository healthFactorRepository;



    @Test
    public void testDeleteEventForHF()
    {
        Rule r = ruleRepository.findOne(3075);
        assert(r !=null);
        HealthFactor hf = healthFactorRepository.findOne(275);
        eventService.deleteEventForHealthFactor(hf);

        Rule r1 = ruleRepository.findOne(3075);
        assert (r1!=null);

        assert(r1.getEvents().isEmpty());
    }
}
