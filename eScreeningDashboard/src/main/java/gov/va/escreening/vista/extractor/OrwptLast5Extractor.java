package gov.va.escreening.vista.extractor;

import gov.va.escreening.domain.NameDto;
import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.vista.VistaUtils;

import org.apache.commons.lang3.StringUtils;

public class OrwptLast5Extractor implements VistaRecordExtractor<VeteranDto> {

    /**
     * IEN^NAME (Last,First Middle)^DOB (Internal Format)^Full SSN 
     * 100095^BCMA,EIGHTY-PATIENT^2350407^666330080
     */
    @Override
    public VeteranDto extractData(String record) {

        if (StringUtils.isBlank(record)) {
            return null;
        }

        VeteranDto veteranDto = new VeteranDto();

        String[] fields = StringUtils.splitPreserveAllTokens(record, '^');

        // 1. IEN
        veteranDto.setVeteranIen(fields[0]);

        // 2. Name
        NameDto nameDto = VistaUtils.convertVistaName(fields[1]);

        if (nameDto != null) {
            veteranDto.setLastName(nameDto.getLastName());
            veteranDto.setFirstName(nameDto.getFirstName());
            veteranDto.setMiddleName(nameDto.getMiddleName());
        }

        // 3. Date of Birth
        veteranDto.setBirthDate(VistaUtils.convertVistaDate(fields[2]));

        // 4. SSN
        veteranDto.setSsnLastFour(StringUtils.right(fields[3], 4));

        return veteranDto;
    }

}
