package gov.va.escreening.repository;

import org.joda.time.LocalDate;

public abstract class FedHolidayFinder {
	private final String name;
	private final String description;

	public FedHolidayFinder(String name, String description) {
		this.name = name;
		this.description = description;
	}

	abstract public boolean fedHoliday(LocalDate date);

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
