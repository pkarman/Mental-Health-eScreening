package gov.va.escreening.vista.extractor;

import gov.va.escreening.vista.VistaUtils;
import gov.va.escreening.vista.dto.VistaVeteranClinicalReminder;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class OrqqpxRemindersExtractor implements VistaRecordExtractor<VistaVeteranClinicalReminder> {

    @Override
    public VistaVeteranClinicalReminder extractData(String record) {
        // IEN^PRINT NAME^DUE DATE/TIME^LAST OCCURRENCE DATE/TIME
        // 500047^Hepatitis C risk Factor Screening^DUE NOW^^2^1^1^^^^0

        if (StringUtils.isBlank(record)) {
            return null;
        }

        VistaVeteranClinicalReminder vistaVeteranClinicalReminder = new VistaVeteranClinicalReminder();

        String[] fields = StringUtils.splitPreserveAllTokens(record, '^');

        // 0. IEN
        vistaVeteranClinicalReminder.setClinicalReminderIen(fields[0]);

        // 1. Name
        vistaVeteranClinicalReminder.setName(fields[1]);

        // 2. Due Date
        if (StringUtils.isNotBlank(fields[2])) {
            if (!"DUE NOW".equalsIgnoreCase(fields[2])) {
                Date dueDate = VistaUtils.convertVistaDate(fields[2]);

                if (dueDate != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    vistaVeteranClinicalReminder.setDueDateString(sdf.format(dueDate));
                }
            }
            else {
                vistaVeteranClinicalReminder.setDueDateString(fields[2]);
            }
        }

        return vistaVeteranClinicalReminder;
    }

}
