package gov.va.escreening.vista.extractor;

import gov.va.escreening.vista.dto.VistaClinic;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class OrwuClinlocListExtractor implements VistaRecordExtractor<List<VistaClinic>> {

    @Override
    public List<VistaClinic> extractData(String record) {

        List<VistaClinic> resultList = new ArrayList<VistaClinic>();

        if (StringUtils.isBlank(record)) {
            return null;
        }

        String[] records = StringUtils.split(record, '\n');

        if (records != null && records.length > 0) {

            OrwuClinlocExtractor orwuClinlocExtractor = new OrwuClinlocExtractor();

            for (int i = 0; i < records.length; ++i) {
                resultList.add(orwuClinlocExtractor.extractData(records[i]));
            }
        }

        return resultList;
    }

}
