package gov.va.escreening.selenium;

import org.junit.Test;
import org.openqa.selenium.By;

public class SystemConfigurationTest extends SeleniumTest{

	@Test
	public void testSystemConfiguration() throws Exception {
		driver.get(baseUrl + "/home");
		driver.findElement(By.linkText("Staff Login")).click();
		driver.findElement(By.id("userNameParam")).clear();
		driver.findElement(By.id("userNameParam")).sendKeys("techadmin");
		driver.findElement(By.id("passwordParam")).clear();
		driver.findElement(By.id("passwordParam")).sendKeys("password");
		driver.findElement(By.id("dashboardLogin")).click();
		driver.findElement(By.id("systemConfigurationTab")).click();
		driver.findElement(By.id("importClinicListButton")).click();
		driver.findElement(By.id("btn_verify")).click();
		driver.findElement(By.id("btn_close")).click();
		driver.findElement(By.cssSelector("img[alt=\"Department of Veterans Affairs | eScreening Program\"]")).click();
	}
}
