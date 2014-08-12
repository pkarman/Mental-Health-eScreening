package gov.va.escreening.vista.extractor;

import gov.va.escreening.vista.VistaUtils;
import gov.va.escreening.vista.dto.VistaVeteranAppointment;

import org.apache.commons.lang3.StringUtils;

public class OrwptApptlstExtractor implements VistaRecordExtractor<VistaVeteranAppointment> {

    @Override
    public VistaVeteranAppointment extractData(String record) {
        // FilemanDateTime^IEN of clinic(File #44)^clinic name
        // 3140218.1^32^PRIMARY CARE^

        if (StringUtils.isBlank(record)) {
            return null;
        }

        VistaVeteranAppointment vistaVeteranAppointment = new VistaVeteranAppointment();

        String[] fields = StringUtils.splitPreserveAllTokens(record, '^');

        vistaVeteranAppointment.setAppointmentDate(VistaUtils.convertVistaDate(fields[0]));
        vistaVeteranAppointment.setClinicIen(fields[1]);
        vistaVeteranAppointment.setClinicName(fields[2]);

        return vistaVeteranAppointment;
    }

}
