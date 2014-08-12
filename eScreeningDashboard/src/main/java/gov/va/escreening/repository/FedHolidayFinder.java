package gov.va.escreening.repository;

import org.joda.time.DateTime;

public abstract class FedHolidayFinder {
	private final String name;
	private final String description;

	public FedHolidayFinder(String name, String description) {
		this.name = name;
		this.description = description;
	}

	abstract public boolean fedHoliday(DateTime date);

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return String.format("FedHolidayFinder [name=%s, description=%s]", name, description);
	}
}
