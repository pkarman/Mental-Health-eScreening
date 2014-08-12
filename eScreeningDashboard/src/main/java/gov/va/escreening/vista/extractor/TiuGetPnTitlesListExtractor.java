package gov.va.escreening.vista.extractor;

import gov.va.escreening.vista.dto.VistaNoteTitle;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class TiuGetPnTitlesListExtractor implements VistaRecordExtractor<List<VistaNoteTitle>> {

    @Override
    public List<VistaNoteTitle> extractData(String record) {

        List<VistaNoteTitle> resultList = new ArrayList<VistaNoteTitle>();

        if (StringUtils.isBlank(record)) {
            return null;
        }

        String[] records = StringUtils.split(record, '\n');

        // First line contains no data.
        if (records != null && records.length > 1) {

            TiuGetPnTitlesExtractor tiuGetPnTitlesExtractor = new TiuGetPnTitlesExtractor();

            // Start with index 1, since the 0 index will have this string: ~LONG LIST
            for (int i = 1; i < records.length; ++i) {
                resultList.add(tiuGetPnTitlesExtractor.extractData(records[i]));
            }
        }

        return resultList;
    }

}
