package gov.va.escreening.vista.extractor;

import gov.va.escreening.vista.dto.VistaLocation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Orwu1NewlocListExtractor implements VistaRecordExtractor<List<VistaLocation>> {

    @Override
    public List<VistaLocation> extractData(String record) {

        List<VistaLocation> resultList = new ArrayList<VistaLocation>();

        if (StringUtils.isBlank(record)) {
            return null;
        }

        String[] records = StringUtils.split(record, '\n');

        if (records != null && records.length > 0) {

            Orwu1NewlocExtractor extractor = new Orwu1NewlocExtractor();

            for (int i = 0; i < records.length; ++i) {
                resultList.add(extractor.extractData(records[i]));
            }
        }

        return resultList;
    }

}
