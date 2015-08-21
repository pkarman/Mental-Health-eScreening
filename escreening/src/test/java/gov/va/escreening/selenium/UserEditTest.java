package gov.va.escreening.selenium;

import org.junit.Test;
import org.openqa.selenium.By;

public class UserEditTest extends SeleniumTest{

	@Test
	public void testUserEdit() throws Exception {
		driver.get(baseUrl + "/home");
		driver.findElement(By.linkText("Staff Login")).click();
		driver.findElement(By.id("userNameParam")).clear();
		driver.findElement(By.id("userNameParam")).sendKeys("techadmin");
		driver.findElement(By.id("passwordParam")).clear();
		driver.findElement(By.id("passwordParam")).sendKeys("password");
		driver.findElement(By.id("dashboardLogin")).click();
		driver.findElement(By.id("adminTab")).click();
		driver.findElement(By.cssSelector("span[name=\"Edit for BSmith123\"]")).click();
		driver.findElement(By.id("middleName")).clear();
		driver.findElement(By.id("middleName")).sendKeys("billy");
		driver.findElement(By.id("phoneNumberExt")).clear();
		driver.findElement(By.id("phoneNumberExt")).sendKeys("144");
		driver.findElement(By.id("emailAddress2")).clear();
		driver.findElement(By.id("emailAddress2")).sendKeys("gomez@ggg.com");
		driver.findElement(By.id("saveButton")).click();
		driver.findElement(By.cssSelector("img[alt=\"Department of Veterans Affairs | eScreening Program\"]")).click();
	}
}
