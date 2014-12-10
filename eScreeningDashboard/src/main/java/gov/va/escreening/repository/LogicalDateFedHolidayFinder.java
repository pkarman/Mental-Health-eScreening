package gov.va.escreening.repository;

import org.joda.time.LocalDate;

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
	public boolean fedHoliday(LocalDate date) {
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

	protected String getRefDayOfMonthAsText(LocalDate date) {
		return this.monthAsText;
	}

	protected String getRefDayOfWeekAsText(LocalDate date) {
		return this.dayOfWeekAsText;
	}

	protected int getRefWeekOfMonth(LocalDate date) {
		return this.weekOfMonth;
	}

	protected int getWeekOfMonthFrom(LocalDate date) {
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
