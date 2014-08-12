package gov.va.escreening.repository;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

/**
 * 
 * Class to represent Memorial Day of USA. Memorial days falls on every last
 * Monday of May. Depending on the date of last Monday, the week could be either
 * the 4th or 5th week
 * 
 * Also known as "Decoration Day", Memorial Day originated in the 19th century
 * as a day to remember the soldiers who gave their lives in the American Civil
 * War by decorating their graves with flowers. As the end of May coincides (in
 * many areas) with the end of the school year, Memorial Day is unofficially
 * considered the beginning of the summer recreational season in America. It was
 * historically observed on May 30, prior to the Uniform Monday Holiday Act.
 * 
 * @author munnoo
 * 
 */
public class MemorialDayFedHolidayFinder extends LogicalDateFedHolidayFinder {

	public MemorialDayFedHolidayFinder(String name, String description, String dayName, String monthName) {
		super(name, description, 0, dayName, monthName);
	}

	@Override
	protected int getRefWeekOfMonth(DateTime date) {
		DateTime lastMondayDate = new DateTime(date.getYear(), 5, 30, 0, 0);
		// look for Monday
		int dayOfMonth = 0;
		while (dayOfMonth == 0) {
			if (lastMondayDate.getDayOfWeek() == DateTimeConstants.MONDAY) {
				dayOfMonth = lastMondayDate.getDayOfMonth();
			} else {
				lastMondayDate = lastMondayDate.minusDays(1);
			}
		}
		return getWeekOfMonthFrom(lastMondayDate);
	}

}
