package gov.va.escreening.selenium;

import org.junit.Test;
import org.openqa.selenium.By;

public class VeteranSearchTest extends SeleniumTest{

	@Test
	public void testVeteranSearch() throws Exception {
		driver.get(baseUrl + "/home");
		driver.findElement(By.linkText("Staff Login")).click();
		driver.findElement(By.id("userNameParam")).clear();
		driver.findElement(By.id("userNameParam")).sendKeys("techadmin");
		driver.findElement(By.id("passwordParam")).clear();
		driver.findElement(By.id("passwordParam")).sendKeys("password");
		driver.findElement(By.id("dashboardLogin")).click();
		driver.findElement(By.id("veteranSearchTab")).click();
		driver.findElement(By.id("ssnLastFour")).clear();
		driver.findElement(By.id("ssnLastFour")).sendKeys("1234");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.linkText("View Total Assessments 1")).click();
		driver.findElement(By.cssSelector("span[name=\"View Assessment Summary for Veteran17, 17\"]")).click();
		driver.findElement(By.id("veteranSearchTab")).click();
		driver.findElement(By.id("lastName")).clear();
		driver.findElement(By.id("lastName")).sendKeys("veteran17");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.linkText("View Total Assessments 1")).click();
		driver.findElement(By.cssSelector("span[name=\"View Assessment Summary for Veteran17, 17\"]")).click();
		driver.findElement(By.cssSelector("img[alt=\"Department of Veterans Affairs | eScreening Program\"]")).click();
	}
}
