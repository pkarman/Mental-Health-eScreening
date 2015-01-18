package gov.va.escreening.service;

import gov.va.escreening.domain.ProgramDto;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.security.EscreenUserDetailsService;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@Transactional
//this is to ensure all tests do not leave trace, so they are repeatable.
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class EscreenUserDetailServiceTest 
{
	@Resource
	private EscreenUserDetailsService userDetailSvc;
	
	@Resource
	private ProgramService programService;
	
	@Test
	public void testDisabledProgeam()
	{
		EscreenUser details = (EscreenUser)userDetailSvc.loadUserByUsername("1pharmacist");
		assertEquals(5, details.getProgramIdList().size());
		
		programService.updatePrgoramStatus(1, true);
			
		details = (EscreenUser)userDetailSvc.loadUserByUsername("1pharmacist");
		assertEquals(4, details.getProgramIdList().size());
	}

}
