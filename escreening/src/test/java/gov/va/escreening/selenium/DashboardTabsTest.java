package gov.va.escreening.selenium;

import org.junit.Test;
import org.openqa.selenium.By;

public class DashboardTabsTest extends SeleniumTest {

	@Test
	public void testDashboardTabs() throws Exception {
		driver.get(baseUrl + "/home");
		driver.findElement(By.linkText("Staff Login")).click();
		driver.findElement(By.id("userNameParam")).clear();
		driver.findElement(By.id("userNameParam")).sendKeys("techadmin");
		driver.findElement(By.id("passwordParam")).clear();
		driver.findElement(By.id("passwordParam")).sendKeys("password");
		driver.findElement(By.id("dashboardLogin")).click();
		driver.findElement(By.id("dashboardTab")).click();
		driver.findElement(By.id("createBatteryTab")).click();
		driver.findElement(By.id("assessmentReportTab")).click();
		driver.findElement(By.id("veteranSearchTab")).click();
		driver.findElement(By.id("exportDataTab")).click();
		driver.findElement(By.id("formsEditorTab")).click();
		driver.findElement(By.id("programManagementTab")).click();
		driver.findElement(By.id("reportsTab")).click();
		driver.findElement(By.id("adminTab")).click();
		driver.findElement(By.id("myAccountTab")).click();
		driver.findElement(By.id("systemConfigurationTab")).click();
		driver.findElement(By.cssSelector("img[alt=\"Department of Veterans Affairs | eScreening Program\"]")).click();
	}
}
