package gov.va.escreening.repository;

import org.joda.time.LocalDate;

public interface FedHolidayFinderHelper {
	public boolean fedHoliday(LocalDate date);
}
