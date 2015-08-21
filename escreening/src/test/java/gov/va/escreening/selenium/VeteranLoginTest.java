package gov.va.escreening.selenium;

import org.junit.Test;
import org.openqa.selenium.By;

public class VeteranLoginTest extends SeleniumTest{

	@Test
	public void testVeteranLogin() throws Exception {
		navigateToVeteranLogin();
		driver.findElement(By.cssSelector("img[alt=\"Department of Veterans Affairs | eScreening Program\"]")).click();
	}
}
