package gov.va.escreening.vista.extractor;

import gov.va.escreening.vista.dto.VistaClinic;

import org.apache.commons.lang3.StringUtils;

public class OrwuClinlocExtractor implements VistaRecordExtractor<VistaClinic> {

    @Override
    public VistaClinic extractData(String record) {
        // IEN^CLINIC NAME
        // 64^AUDIOLOGY

        if (StringUtils.isBlank(record)) {
            return null;
        }

        VistaClinic vistaClinic = new VistaClinic();

        String[] fields = StringUtils.splitPreserveAllTokens(record, '^');

        // 1. IEN
        vistaClinic.setClinicIen(fields[0]);

        // 2. Clinic Name
        vistaClinic.setClinicName(fields[1]);

        return vistaClinic;
    }

}
