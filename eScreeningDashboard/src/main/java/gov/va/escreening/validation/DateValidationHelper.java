package gov.va.escreening.validation;

import gov.va.escreening.domain.AssessmentExpirationDaysEnum;
import gov.va.escreening.repository.FedHolidayFinderHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

@Component("dateValidationHelper")
public class DateValidationHelper {
	@Resource(name = "usFedHolidayFinderHelper")
	FedHolidayFinderHelper fedHolidayFinderHelper;

	public List<LocalDate> validWorkingDates(AssessmentExpirationDaysEnum days) {
		LocalDate dt = LocalDate.now();

		int validDaysCnt = 0;
		List<LocalDate> dates = new ArrayList<LocalDate>();

		LocalDate pdt = dt;
		while (validDaysCnt < days.getExpirationDays()) {
			if (validDate(pdt)) {
				validDaysCnt++;
				dates.add(pdt);
			}
			pdt = pdt.minusDays(1);
		}
		return dates;
	}

	private boolean validDate(LocalDate date) {
		return !weekEnd(date) && !fedHoliday(date);
	}

	private boolean fedHoliday(LocalDate date) {
		return fedHolidayFinderHelper.fedHoliday(date);
	}

	private boolean weekEnd(LocalDate date) {
		String weekDayAsText = date.dayOfWeek().getAsText();
		return weekDayAsText.equals("Saturday") || weekDayAsText.equals("Sunday");
	}

	public Collection<Date> validWorkingDatesForSQL(
			AssessmentExpirationDaysEnum expirationDays) {
		List<LocalDate> localDates = validWorkingDates(expirationDays);

		Function<LocalDate, Date> func = new Function<LocalDate, Date>() {
			@Override
			public Date apply(LocalDate input) {
				Date d = input.toDate();
				return d;
			}
		};

		Collection<Date> localJavaDates = Collections2.transform(localDates, func);
		return localJavaDates;
	}

}
