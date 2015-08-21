package gov.va.escreening.selenium;

import org.junit.experimental.categories.Category;

import gov.va.escreening.test.Selenium;
import junit.framework.Test;
import junit.framework.TestSuite;

@Category(Selenium.class)
public class SeleniumTestSuite {

	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTestSuite(DashboardTabsTest.class);
		suite.addTestSuite(VeteranLoginTest.class);
		suite.addTestSuite(UserAddTest.class);
		suite.addTestSuite(UserEditTest.class);
		suite.addTestSuite(ChangePasswordTest.class);
		suite.addTestSuite(BriefAssessmentTest.class);
		suite.addTestSuite(AssessmentSearchTest.class);
		suite.addTestSuite(VeteranSearchTest.class);
		suite.addTestSuite(SystemConfigurationTest.class);
		suite.addTestSuite(AlertsSkippedQuestionsDemographicsBatteriesTest.class);
		suite.addTestSuite(ReportsIndividualStatsAndPositiveScreensTest.class);
		return suite;
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}
}
