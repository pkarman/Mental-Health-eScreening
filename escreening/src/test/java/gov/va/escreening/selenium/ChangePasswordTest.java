package gov.va.escreening.selenium;

import org.junit.Test;
import org.openqa.selenium.By;

public class ChangePasswordTest extends SeleniumTest{

	@Test
	public void testChangePassword() throws Exception {
		driver.get(baseUrl + "/home");
		driver.findElement(By.linkText("Staff Login")).click();
		driver.findElement(By.id("userNameParam")).clear();
		driver.findElement(By.id("userNameParam")).sendKeys("Bsmith123");
		driver.findElement(By.id("passwordParam")).clear();
		driver.findElement(By.id("passwordParam")).sendKeys("Bsmith123#");
		driver.findElement(By.id("dashboardLogin")).click();
		driver.findElement(By.id("myAccountTab")).click();
		driver.findElement(By.id("currentPassword")).click();
		driver.findElement(By.id("currentPassword")).clear();
		driver.findElement(By.id("currentPassword")).sendKeys("Bsmith123#");
		driver.findElement(By.id("newPassword")).click();
		driver.findElement(By.id("newPassword")).clear();
		driver.findElement(By.id("newPassword")).sendKeys("Bsmith12#");
		driver.findElement(By.id("confirmedPassword")).click();
		driver.findElement(By.id("confirmedPassword")).clear();
		driver.findElement(By.id("confirmedPassword")).sendKeys("Bsmith12#");
		driver.findElement(By.id("submitButton")).click();
		driver.findElement(By.linkText("Logout")).click();
		driver.findElement(By.id("userNameParam")).clear();
		driver.findElement(By.id("userNameParam")).sendKeys("Bsmith123");
		driver.findElement(By.id("passwordParam")).clear();
		driver.findElement(By.id("passwordParam")).sendKeys("Bsmith12#");
		driver.findElement(By.id("dashboardLogin")).click();
		driver.findElement(By.id("myAccountTab")).click();
		driver.findElement(By.id("currentPassword")).click();
		driver.findElement(By.id("currentPassword")).clear();
		driver.findElement(By.id("currentPassword")).sendKeys("Bsmith12#");
		driver.findElement(By.id("newPassword")).click();
		driver.findElement(By.id("newPassword")).clear();
		driver.findElement(By.id("newPassword")).sendKeys("Bsmith123#");
		driver.findElement(By.id("confirmedPassword")).click();
		driver.findElement(By.id("confirmedPassword")).clear();
		driver.findElement(By.id("confirmedPassword")).sendKeys("Bsmith123#");
		driver.findElement(By.id("submitButton")).click();
		driver.findElement(By.linkText("Logout")).click();
		driver.findElement(By.cssSelector("img[alt=\"Department of Veterans Affairs | eScreening Program\"]")).click();
	}
}
