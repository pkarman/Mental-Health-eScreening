package gov.va.escreening.vista.extractor;

import gov.va.escreening.vista.dto.VistaVeteranClinicalReminder;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class OrqqpxRemindersListExtractor implements VistaRecordExtractor<List<VistaVeteranClinicalReminder>> {

    @Override
    public List<VistaVeteranClinicalReminder> extractData(String record) {

        List<VistaVeteranClinicalReminder> resultList = new ArrayList<VistaVeteranClinicalReminder>();

        if (StringUtils.isBlank(record)) {
            return null;
        }

        String[] records = StringUtils.split(record, '\n');

        if (records != null && records.length > 0) {

            OrqqpxRemindersExtractor orqqpxRemindersExtractor = new OrqqpxRemindersExtractor();

            for (int i = 0; i < records.length; ++i) {
                resultList.add(orqqpxRemindersExtractor.extractData(records[i]));
            }
        }

        return resultList;
    }

}
