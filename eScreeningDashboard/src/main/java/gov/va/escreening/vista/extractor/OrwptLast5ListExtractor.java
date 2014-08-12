package gov.va.escreening.vista.extractor;

import gov.va.escreening.domain.VeteranDto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class OrwptLast5ListExtractor implements VistaRecordExtractor<List<VeteranDto>> {

    @Override
    public List<VeteranDto> extractData(String record) {

        List<VeteranDto> resultList = new ArrayList<VeteranDto>();

        if (StringUtils.isBlank(record)) {
            return null;
        }

        String[] records = StringUtils.split(record, '\n');

        if (records != null && records.length > 0) {

            OrwptLast5Extractor orwptLast5Extractor = new OrwptLast5Extractor();

            for (int i = 0; i < records.length; ++i) {
                resultList.add(orwptLast5Extractor.extractData(records[i]));
            }
        }

        return resultList;
    }
}
