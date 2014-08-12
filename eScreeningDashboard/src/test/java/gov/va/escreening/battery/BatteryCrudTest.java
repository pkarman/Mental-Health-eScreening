package gov.va.escreening.battery;

import gov.va.escreening.domain.BatteryDto;
import gov.va.escreening.service.BatteryService;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class BatteryCrudTest {
	@Resource(type = BatteryService.class)
	BatteryService bs;

	@Test
	public void donothing(){
		
	}
	//@Rollback(value = true)
	//@Test
	public void batteryDelete() {
		List<BatteryDto> batteries = bs.getBatteryDtoList();
		int totalbatteiesBeforedelete = batteries.size();

		bs.delete(4);

		List<BatteryDto> batteries1 = bs.getBatteryDtoList();
		int totalbatteiesAfterdelete = batteries.size();

		Assert.assertTrue(totalbatteiesAfterdelete == totalbatteiesBeforedelete - 1);

	}
}
