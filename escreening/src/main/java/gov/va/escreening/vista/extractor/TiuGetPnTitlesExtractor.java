package gov.va.escreening.vista.extractor;

import gov.va.escreening.vista.dto.VistaNoteTitle;

import org.apache.commons.lang3.StringUtils;

public class TiuGetPnTitlesExtractor implements VistaRecordExtractor<VistaNoteTitle> {

    /**
     * IEN^NAME i8^ADVANCE DIRECTIVE
     */
    @Override
    public VistaNoteTitle extractData(String record) {

        if (StringUtils.isBlank(record)) {
            return null;
        }

        String[] fields = StringUtils.splitPreserveAllTokens(record, '^');

        // 1. IEN. Skip the 'i' prefix.
        VistaNoteTitle vistaNoteTitle = new VistaNoteTitle(Long.parseLong(StringUtils.substring(fields[0], 1).trim()), fields[1]);

        return vistaNoteTitle;
    }

}
