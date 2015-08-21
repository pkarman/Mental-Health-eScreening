package gov.va.escreening.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

public class AssessmentSearchTest extends SeleniumTest{

	@Test
	public void testAssessmentSearch() throws Exception {
		driver.get(baseUrl + "/home");
		driver.findElement(By.linkText("Staff Login")).click();
		driver.findElement(By.id("userNameParam")).clear();
		driver.findElement(By.id("userNameParam")).sendKeys("techadmin");
		driver.findElement(By.id("passwordParam")).clear();
		driver.findElement(By.id("passwordParam")).sendKeys("password");
		driver.findElement(By.id("dashboardLogin")).click();
		driver.findElement(By.id("assessmentReportTab")).click();
		new Select(driver.findElement(By.id("programId"))).selectByVisibleText("OOO");
		driver.findElement(By.id("searchAssessmentButton")).click();
		driver.findElement(By.cssSelector("tr.even > td.alignCenter > a.assessmentPreviewIFramePush > i.fa.fa-file")).click();
		driver.findElement(By.cssSelector("#AssessmentReportPreviewIFrame > div.modal-dialog > div.modal-content > div.modal-header.nonPrintableArea > button.close")).click();
		driver.findElement(By.cssSelector("tr.even > td.alignCenter > a.assessmentNotePush > i.fa.fa-search")).click();
		driver.findElement(By.cssSelector("button.close")).click();
		driver.findElement(By.cssSelector("span[name=\"View Assessment Summary for Veteran17, 17\"]")).click();
		driver.findElement(By.cssSelector("img[alt=\"Department of Veterans Affairs | eScreening Program\"]")).click();
	}
}
