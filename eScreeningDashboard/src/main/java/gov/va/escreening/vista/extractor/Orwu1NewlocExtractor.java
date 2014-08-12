package gov.va.escreening.vista.extractor;

import gov.va.escreening.vista.dto.VistaLocation;

import org.apache.commons.lang3.StringUtils;

public class Orwu1NewlocExtractor implements VistaRecordExtractor<VistaLocation> {

    /**
     * IEN^LOCATION NAME 
     * 33232^AUDIOLOGY
     */
    @Override
    public VistaLocation extractData(String record) {

        if (StringUtils.isBlank(record)) {
            return null;
        }

        String[] fields = StringUtils.splitPreserveAllTokens(record, '^');

        VistaLocation vistaLocation = new VistaLocation(Long.parseLong(fields[0].trim()),fields[1]);

        // 1. IEN
        //vistaLocation.setIen(fields[0]);

        // 2. location/clinic Name
        //vistaLocation.setName(fields[1]);

        return vistaLocation;
    }

}
