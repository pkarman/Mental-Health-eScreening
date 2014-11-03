package gov.va.escreening.assessments.test;

import static org.junit.Assert.assertTrue;
import gov.va.escreening.repository.FedHolidayFinderHelper;

import javax.annotation.Resource;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class FedHolidayTest {
	@Resource(name = "usFedHolidayFinderHelper")
	FedHolidayFinderHelper fedHolidayFinderHelper;
	DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");

	@Test
	public void testFedHoldayJan1st2014() {
		LocalDate dt = formatter.parseLocalDate("01/01/2014");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldayJuly4th2014() {
		LocalDate dt = formatter.parseLocalDate("07/04/2014");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldayNov112014() {
		LocalDate dt = formatter.parseLocalDate("11/11/2014");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldayDec252014() {
		LocalDate dt = formatter.parseLocalDate("12/25/2014");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldayJan20th2014() {
		LocalDate dt = formatter.parseLocalDate("01/20/2014");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldayFeb172014() {
		LocalDate dt = formatter.parseLocalDate("02/17/2014");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldayMay26th2014() {
		LocalDate dt = formatter.parseLocalDate("05/26/2014");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldaySep1st2014() {
		LocalDate dt = formatter.parseLocalDate("09/01/2014");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldayOct13th2014() {
		LocalDate dt = formatter.parseLocalDate("10/13/2014");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldayNov27th2014() {
		LocalDate dt = formatter.parseLocalDate("11/27/2014");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldayJan1st2015() {
		LocalDate dt = formatter.parseLocalDate("01/01/2015");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldayJuly4th2015() {
		LocalDate dt = formatter.parseLocalDate("07/04/2015");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldayNov112015() {
		LocalDate dt = formatter.parseLocalDate("11/11/2015");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldayDec252015() {
		LocalDate dt = formatter.parseLocalDate("12/25/2015");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldayJan19th2015() {
		LocalDate dt = formatter.parseLocalDate("01/19/2015");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldayFeb162015() {
		LocalDate dt = formatter.parseLocalDate("02/16/2015");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldayMay25th2015() {
		LocalDate dt = formatter.parseLocalDate("05/25/2015");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldaySep7th2015() {
		LocalDate dt = formatter.parseLocalDate("09/07/2015");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldayOct12th2015() {
		LocalDate dt = formatter.parseLocalDate("10/12/2015");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldayNov26th2015() {
		LocalDate dt = formatter.parseLocalDate("11/26/2015");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	/* Logical dates for 2016 */
	@Test
	public void testFedHoldayJan18th2016() {
		LocalDate dt = formatter.parseLocalDate("01/18/2016");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldayFeb152016() {
		LocalDate dt = formatter.parseLocalDate("02/15/2016");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldayMay30th2016() {
		LocalDate dt = formatter.parseLocalDate("05/30/2016");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldaySep5th2016() {
		LocalDate dt = formatter.parseLocalDate("09/05/2016");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldayOct10th2016() {
		LocalDate dt = formatter.parseLocalDate("10/10/2016");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldayNov24th2016() {
		LocalDate dt = formatter.parseLocalDate("11/24/2016");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	/* Logical dates for 2017 */
	@Test
	public void testFedHoldayJan16th2017() {
		LocalDate dt = formatter.parseLocalDate("01/16/2017");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldayFeb202017() {
		LocalDate dt = formatter.parseLocalDate("02/20/2017");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldayMay29th2017() {
		LocalDate dt = formatter.parseLocalDate("05/29/2017");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldaySep4th2017() {
		LocalDate dt = formatter.parseLocalDate("09/04/2017");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldayOct9th2017() {
		LocalDate dt = formatter.parseLocalDate("10/09/2017");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldayNov23rd2017() {
		LocalDate dt = formatter.parseLocalDate("11/23/2017");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	/* memorial day tests */
	@Test
	public void testFedHoldayMay28th2018() {
		LocalDate dt = formatter.parseLocalDate("05/28/2018");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldayMay27th2019() {
		LocalDate dt = formatter.parseLocalDate("05/27/2019");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

	@Test
	public void testFedHoldayMay25th2020() {
		LocalDate dt = formatter.parseLocalDate("05/25/2020");
		assertTrue(fedHolidayFinderHelper.fedHoliday(dt));
	}

}
