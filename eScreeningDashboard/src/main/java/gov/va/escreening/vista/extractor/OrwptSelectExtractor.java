package gov.va.escreening.vista.extractor;

import gov.va.escreening.domain.NameDto;
import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.vista.VistaUtils;

import org.apache.commons.lang3.StringUtils;

public class OrwptSelectExtractor implements VistaRecordExtractor<VeteranDto> {

    @Override
    public VeteranDto extractData(String record) {

        // NAME^SEX^DOB^SSN^LOCIEN^LOCNM^RMBD^CWAD^SENSITIVE^ADMITTED^CONV^SC^SC%^ICN^AGE^TS
        // BCMA,EIGHTYNINE-PATIENT^M^2350407^666330089^11^BCMA^13-A^A^0^3020208.075529^0^1^25^^78^9

        if (StringUtils.isBlank(record)) {
            return null;
        }

        String[] fields = StringUtils.splitPreserveAllTokens(record, '^');

        // Check if user exists.
        if ("-1".equalsIgnoreCase(fields[0])) {
            return null;
        }

        VeteranDto veteranDto = new VeteranDto();

        // 1. Name
        NameDto nameDto = VistaUtils.convertVistaName(fields[0]);

        if (nameDto != null) {
            veteranDto.setLastName(nameDto.getLastName());
            veteranDto.setFirstName(nameDto.getFirstName());
            veteranDto.setMiddleName(nameDto.getMiddleName());
        }

        // 2. sex
        if ("M".equals(fields[1])) {
            veteranDto.setGender("Male");
        }
        else if ("F".equals(fields[1])) {
            veteranDto.setGender("Female");
        }

        // 3. Date of Birth
        veteranDto.setBirthDate(VistaUtils.convertVistaDate(fields[2]));

        // 4. SSN
        veteranDto.setSsnLastFour(StringUtils.right(fields[3], 4));

        // 5. LocationIEN

        // 6. LocationName
        veteranDto.setInpatientStatus((fields[5]!= null)? true: false);

        return veteranDto;
    }

}
