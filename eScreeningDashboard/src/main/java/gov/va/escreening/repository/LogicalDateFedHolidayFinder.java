package gov.va.escreening.repository;

import org.joda.time.DateTime;

public class LogicalDateFedHolidayFinder extends FedHolidayFinder {

	private final String dayOfWeekAsText;
	private final String monthAsText;
	private final int weekOfMonth;

	public LogicalDateFedHolidayFinder(String name, String description, int weeklyPos, String dayName, String monthName) {
		super(name, String.format("%s%s %s of %s", weeklyPos, weeklyPos == 1 ? "st" : weeklyPos == 2 ? "nd" : weeklyPos == 3 ? "rd" : "th", dayName, monthName, description));

		this.dayOfWeekAsText = dayName;
		this.monthAsText = monthName;
		this.weekOfMonth = weeklyPos;
	}

	@Override
	public boolean fedHoliday(DateTime date) {
		// extract info from passed in date
		String txDayOfWeekAsText = date.dayOfWeek().getAsText();
		String txMonthAsText = date.monthOfYear().getAsText();
		int txWeekOfMonth = getWeekOfMonthFrom(date);

		// match the info with the instance's logical date
		boolean weekNoMatches = txWeekOfMonth == getRefWeekOfMonth(date);
		boolean dayNameMatches = txDayOfWeekAsText.equals(getRefDayOfWeekAsText(date));
		boolean monthNameMatches = txMonthAsText.equals(getRefDayOfMonthAsText(date));

		return weekNoMatches && dayNameMatches && monthNameMatches;

	}

	protected String getRefDayOfMonthAsText(DateTime date) {
		return this.monthAsText;
	}

	protected String getRefDayOfWeekAsText(DateTime date) {
		return this.dayOfWeekAsText;
	}

	protected int getRefWeekOfMonth(DateTime date) {
		return this.weekOfMonth;
	}

	protected int getWeekOfMonthFrom(DateTime date) {
		int dayOfMonth = date.getDayOfMonth();
		int remainder = dayOfMonth % 7;
		int weekOfMonth = dayOfMonth / 7 + (remainder > 0 ? 1 : 0);

		return weekOfMonth;
	}

	protected String getDayOfWeekAsText() {
		return dayOfWeekAsText;
	}

	protected String getMonthAsText() {
		return monthAsText;
	}

	protected int getWeekOfMonth() {
		return weekOfMonth;
	}

}
