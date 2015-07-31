package gov.va.escreening.service;

import static org.junit.Assert.*;
import gov.va.escreening.dto.DropDownObject;
import gov.va.escreening.entity.DashboardAlert;
import gov.va.escreening.repository.DashboardAlertRepository;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/spring/root-context.xml"
})
@org.springframework.transaction.annotation.Transactional
public class DashboardAlertServiceTest {
	@Resource
	DashboardAlertService alertTypeService;
	
	@Resource
	DashboardAlertRepository alertRepo;
	
	@Test
	public void testGetAll()
	{
		List<DropDownObject> result = alertTypeService.getAll();
		assertTrue(result.size() == 9);
	}
	
	@Test
	public void testUpdate()
	{
		alertTypeService.updateAlertType(1, "TEST");
		DashboardAlert alert = alertRepo.findOne(1);
		assertNotNull(alert);
		assert(alert.getName().equals("TEST"));
		
		alertTypeService.updateAlertType(null, "TEST 111");
		assert(alertTypeService.getAll().size() == 10);
	}
	
	@Test//(expected = Exception.class)
	public void testDelete()
	{
		alertTypeService.deleteAlertType(2);
		assert(alertTypeService.getAll().size() == 8);
	}

	@Test(expected = Exception.class)
	public void testDeleteNonExist()
	{
		alertTypeService.deleteAlertType(20);
		
	}
}
