package gov.va.escreening.vista.extractor;

import gov.va.escreening.vista.VistaUtils;
import gov.va.escreening.vista.dto.VistaVeteranAppointment;

import org.apache.commons.lang3.StringUtils;

public class OrwptApptlstExtractor implements VistaRecordExtractor<VistaVeteranAppointment> {

    /**
     * <pre>
     *     RPC: ORWCV VST
     *      Parameters:
     *          1. IEN of the patient
     *          2. Beginning Date (Fileman format), which is usually 1 year ago
     *          3. Ending Date (Fileman format), usually today
     *          4. Skip (1 or 0), if 1 skip over admissions else include admissions
     *      Result Type: String
     *          Result Format: VisitData^Date^Clinic Name^Status
     *              VisitData is arranged as:
     *                  Piece 1: A or V  (Appointment or Visit)
     *                  Piece 2: Fileman DateTime
     *                  Piece 3: Location IEN
     *      Result Example:
     *          Params ------------------------------------------------------------------
     *          literal	100687
     *          literal	3130331
     *          literal	3140331.2359
     *          literal	1
     *      Results -----------------------------------------------------------------
     *          A;3140218.1;32^3140218.1^PRIMARY CARE^ACTION REQUIRED
     * </pre>
     *
     * @param record
     * @return
     */
    @Override
    public VistaVeteranAppointment extractData(String record) {
        // FilemanDateTime^IEN of clinic(File #44)^clinic name
        // 3140218.1^32^PRIMARY CARE^

        if (StringUtils.isBlank(record)) {
            return null;
        }

        VistaVeteranAppointment vistaVeteranAppointment = new VistaVeteranAppointment();

        // we are only interested in appointments

        String[] fields = StringUtils.splitPreserveAllTokens(record, '^');

        if (fields.length > 3 && !fields[3].startsWith("CANCELLED")) {

            vistaVeteranAppointment.setAppointmentDate(VistaUtils.convertVistaDate(fields.length > 1 ? fields[1] : null));
            vistaVeteranAppointment.setClinicName(fields.length > 2 ? fields[2] : null);
            vistaVeteranAppointment.setStatus(fields.length > 3 ? fields[3] : null);


            return vistaVeteranAppointment;
        } else {
            return null;
        }
    }

}
