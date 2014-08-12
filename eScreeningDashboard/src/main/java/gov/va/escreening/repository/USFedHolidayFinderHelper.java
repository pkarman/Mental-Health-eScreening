package gov.va.escreening.repository;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("usFedHolidayFinderHelper")
public class USFedHolidayFinderHelper implements FedHolidayFinderHelper {
	private static final Logger logger = LoggerFactory.getLogger(USFedHolidayFinderHelper.class);
	private final List<FedHolidayFinder> fedHolidayFinders;

	public USFedHolidayFinderHelper() {
		this.fedHolidayFinders = new ArrayList<FedHolidayFinder>();

		populateFindersNow(this.fedHolidayFinders);

	}

	private void populateFindersNow(List<FedHolidayFinder> fhfs) {
		// fix date holidays
		fhfs.add(new FixedDateFedHolidayFinder("New Year's Day", "Celebrates beginning of the Gregorian calendar year. Festivities usually start the previous evening.", "01/01"));
		fhfs.add(new FixedDateFedHolidayFinder("Independance Day", "Celebrates the adoption of the Declaration of Independence. Also popularly known as the 'Fourth of July'.", "07/04"));
		fhfs.add(new FixedDateFedHolidayFinder("Veterans Day", "In the United States, the holiday honors all veterans of the United States Armed Forces, whether or not they have served in a conflict; but it especially honors the surviving veterans of wars.", "11/11"));
		fhfs.add(new FixedDateFedHolidayFinder("Christmas Day", "A worldwide holiday that celebrates the birth of Jesus Christ.", "12/25"));

		// logical date finders
		fhfs.add(new LogicalDateFedHolidayFinder("Martin Luther King Day", "Martin Luther King, Jr., civil rights leader, was actually born on January 15, 1929. Renamed or combined with other holidays in some states.", 3, "Monday", "January"));
		fhfs.add(new LogicalDateFedHolidayFinder("Washington's Birthday", "Honors George Washington. Sometimes labeled as 'Presidents Day' by other than the federal government, in recognition of other American presidents, such as Abraham Lincoln (who was born February 12).", 3, "Monday", "February"));
		fhfs.add(new MemorialDayFedHolidayFinder("Memorial Day", "Also known as 'Decoration Day', Memorial Day originated in the 19th century as a day to remember the soldiers who gave their lives in the American Civil War by decorating their graves with flowers.", "Monday", "May"));
		fhfs.add(new LogicalDateFedHolidayFinder("Labor Day", "Celebrates achievements of workers and the labor movement. As Labor Day coincides (in many areas) with the beginning of the school year, Labor Day is unofficially considered the end of the summer recreational season in America.", 1, "Monday", "September"));
		fhfs.add(new LogicalDateFedHolidayFinder("Columbus Day", "Marks the arrival of Christopher Columbus in the Americas, when he landed in the Bahamas on October 12, 1492 (according to the Julian calendar).", 2, "Monday", "October"));
		fhfs.add(new LogicalDateFedHolidayFinder("Thanksgiving", "Many Americans have a turkey dinner in honor of the dinner shared by Native Americans and the Pilgrims at Plymouth.", 4, "Thursday", "November"));

		if (logger.isDebugEnabled()) {
			logger.debug(fhfs.toString());
		}
	}

	@Override
	public boolean fedHoliday(DateTime date) {
		for (FedHolidayFinder fhf : fedHolidayFinders) {
			if (fhf.fedHoliday(date)) {
				if (logger.isDebugEnabled()) {
					logger.debug(String.format("%s is true", fhf));
				}
				return true;
			}
		}
		return false;
	}

}
