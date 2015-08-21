package gov.va.escreening.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

public class UserAddTest extends SeleniumTest{

	@Test
	public void testUserAdd() throws Exception {
		driver.get(baseUrl + "/home");
		driver.findElement(By.linkText("Staff Login")).click();
		driver.findElement(By.id("userNameParam")).clear();
		driver.findElement(By.id("userNameParam")).sendKeys("techadmin");
		driver.findElement(By.id("passwordParam")).clear();
		driver.findElement(By.id("passwordParam")).sendKeys("password");
		driver.findElement(By.id("dashboardLogin")).click();
		driver.findElement(By.id("adminTab")).click();
		driver.findElement(By.linkText("Create New User")).click();
		driver.findElement(By.id("loginId")).clear();
		driver.findElement(By.id("loginId")).sendKeys("BSmith123");
		new Select(driver.findElement(By.id("selectedRoleId"))).selectByVisibleText("Healthcare System Technical Administrator");
		new Select(driver.findElement(By.id("selectedUserStatusId"))).selectByVisibleText("Active");
		driver.findElement(By.id("firstName")).clear();
		driver.findElement(By.id("firstName")).sendKeys("Bob");
		driver.findElement(By.id("lastName")).clear();
		driver.findElement(By.id("lastName")).sendKeys("Smith");
		driver.findElement(By.id("phoneNumber")).clear();
		driver.findElement(By.id("phoneNumber")).sendKeys("1112223333");
		driver.findElement(By.id("emailAddress")).clear();
		driver.findElement(By.id("emailAddress")).sendKeys("bob@xxx.com");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("Bsmith123#");
		driver.findElement(By.id("passwordConfirmed")).clear();
		driver.findElement(By.id("passwordConfirmed")).sendKeys("Bsmith123#");
		driver.findElement(By.id("selectedProgramIdList4")).click();
		driver.findElement(By.id("selectedProgramIdList1")).click();
		driver.findElement(By.id("selectedProgramIdList2")).click();
		driver.findElement(By.id("selectedProgramIdList5")).click();
		driver.findElement(By.id("selectedProgramIdList3")).click();
		driver.findElement(By.id("saveButton")).click();
		driver.findElement(By.id("adminTab")).click();
		driver.findElement(By.cssSelector("img[alt=\"Department of Veterans Affairs | eScreening Program\"]")).click();
	}
}
