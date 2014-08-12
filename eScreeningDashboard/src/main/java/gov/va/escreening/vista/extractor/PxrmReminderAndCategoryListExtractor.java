package gov.va.escreening.vista.extractor;

import gov.va.escreening.vista.dto.VistaClinicalReminderAndCategory;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class PxrmReminderAndCategoryListExtractor implements
        VistaRecordExtractor<List<VistaClinicalReminderAndCategory>> {

    @Override
    public List<VistaClinicalReminderAndCategory> extractData(String record) {

        List<VistaClinicalReminderAndCategory> resultList = new ArrayList<VistaClinicalReminderAndCategory>();

        if (StringUtils.isBlank(record)) {
            return null;
        }

        String[] records = StringUtils.split(record, '\n');

        if (records != null && records.length > 0) {

            PxrmReminderAndCategoryExtractor pxrmReminderAndCategoryExtractor = new PxrmReminderAndCategoryExtractor();

            for (int i = 0; i < records.length; ++i) {
                resultList.add(pxrmReminderAndCategoryExtractor.extractData(records[i]));
            }
        }

        return resultList;
    }

}
