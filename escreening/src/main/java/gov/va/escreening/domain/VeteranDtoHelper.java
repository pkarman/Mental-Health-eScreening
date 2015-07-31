package gov.va.escreening.domain;

public class VeteranDtoHelper {

	public static String getPhoneByPriority(VeteranDto veteranDto) {
		// Use phone if it has already been set other wise use cell phone if it exists, then home phone, if neither
		// none of these exist use work
		if (veteranDto.getPhone() != null && !veteranDto.getPhone().isEmpty())
			return veteranDto.getPhone();
		
		if (veteranDto.getCellPhone() != null && !veteranDto.getCellPhone().isEmpty())
			return veteranDto.getCellPhone();

		if (veteranDto.getHomePhone() != null && !veteranDto.getHomePhone().isEmpty())
			return veteranDto.getHomePhone();

		if (veteranDto.getWorkPhone() != null && !veteranDto.getWorkPhone().isEmpty())
			return veteranDto.getWorkPhone();

		return null;
	}
}
