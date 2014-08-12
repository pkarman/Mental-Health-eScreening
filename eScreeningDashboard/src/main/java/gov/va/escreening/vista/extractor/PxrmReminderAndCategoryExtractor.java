package gov.va.escreening.vista.extractor;

import gov.va.escreening.vista.dto.VistaClinicalReminderAndCategory;

import org.apache.commons.lang3.StringUtils;

public class PxrmReminderAndCategoryExtractor implements VistaRecordExtractor<VistaClinicalReminderAndCategory> {

    @Override
    public VistaClinicalReminderAndCategory extractData(String record) {

        // Result Format: Type ^ IEN ^ Print Name ^ Reminder Name ^ Class (Reminders being returned first, then classes)
        // * Type is either R (Reminder) or C (Category)
        // * Print name is the name displayed to user
        // * Class is either N (National), V (VISN), or L (Local)
        // (Piece 4 and 5 only returned if a reminder)

        // R^41^00 Anticoagulation Afib^ARD 00 ANTICOAGULATION AFIB^L
        // C^19^CAMP10

        if (StringUtils.isBlank(record)) {
            return null;
        }

        VistaClinicalReminderAndCategory vistaClinicalReminderAndCategory = new VistaClinicalReminderAndCategory();

        String[] fields = StringUtils.splitPreserveAllTokens(record, '^');

        // 0. Type
        vistaClinicalReminderAndCategory.setClinicalReminderTypeName(fields[0]);

        // 1. IEN
        vistaClinicalReminderAndCategory.setClinicalReminderIen(fields[1]);

        // 2. Print Name
        vistaClinicalReminderAndCategory.setPrintName(fields[2]);

        // Only Reminders have the last 2 fields.
        if (fields.length == 5) {

            // 3. Reminder Name
            vistaClinicalReminderAndCategory.setClinicalReminderName(fields[3]);

            // 4. Class
            vistaClinicalReminderAndCategory.setClinicalReminderClass(fields[4]);
        }

        return vistaClinicalReminderAndCategory;
    }

}
