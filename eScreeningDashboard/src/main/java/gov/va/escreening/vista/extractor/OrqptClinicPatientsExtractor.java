package gov.va.escreening.vista.extractor;

import gov.va.escreening.domain.NameDto;
import gov.va.escreening.vista.VistaUtils;
import gov.va.escreening.vista.dto.VistaClinicAppointment;

import org.apache.commons.lang3.StringUtils;

public class OrqptClinicPatientsExtractor implements VistaRecordExtractor<VistaClinicAppointment> {

    @Override
    public VistaClinicAppointment extractData(String record) {

        // PATIENT IEN^PATIENT NAME^CLINIC IEN^APPOINTMENT DATE.TIME^^^^^OPT or IPT (In or out patient) (Pieces 5-8 are
        // sent back empty)
        // 100095^BCMA,EIGHTY-PATIENT^32^3140219.1^^^^^OPT

        if (StringUtils.isBlank(record)) {
            return null;
        }

        VistaClinicAppointment vistaClinicAppointment = new VistaClinicAppointment();

        String[] fields = StringUtils.splitPreserveAllTokens(record, '^');

        // 0. IEN
        vistaClinicAppointment.setVeteranIen(fields[0]);

        // 1. Name
        NameDto nameDto = VistaUtils.convertVistaName(fields[1]);

        if (nameDto != null) {
            vistaClinicAppointment.setLastName(nameDto.getLastName());
            vistaClinicAppointment.setFirstName(nameDto.getFirstName());
            vistaClinicAppointment.setMiddleName(nameDto.getMiddleName());
        }

        // 2. Clinic IEN
        vistaClinicAppointment.setClinicIen(fields[2]);

        // 3. Appointment Date
        vistaClinicAppointment.setAppointmentDate(VistaUtils.convertVistaDate(fields[3]));

        // 8. Clinic IEN
        vistaClinicAppointment.setVisitStatusName(fields[8]);

        return vistaClinicAppointment;

    }

}
