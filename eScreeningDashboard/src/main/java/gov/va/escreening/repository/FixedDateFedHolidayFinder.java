package gov.va.escreening.repository;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class FixedDateFedHolidayFinder extends FedHolidayFinder {

	private final int monthOfYear;
	private final int dayOfMonth;

	private static DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd");

	public FixedDateFedHolidayFinder(String name, String description, String fixedDateAsStr) {
		super(name, String.format("%s %s", fixedDateAsStr, description));

		DateTime fixedDate = formatter.parseDateTime(fixedDateAsStr);
		this.monthOfYear = fixedDate.getMonthOfYear();
		this.dayOfMonth = fixedDate.getDayOfMonth();
	}

	@Override
	public boolean fedHoliday(DateTime date) {
		int m = date.getMonthOfYear();
		int d = date.getDayOfMonth();

		return m == this.monthOfYear && d == this.dayOfMonth;
	}

}
