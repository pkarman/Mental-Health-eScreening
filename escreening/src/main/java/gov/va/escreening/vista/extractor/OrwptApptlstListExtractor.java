package gov.va.escreening.vista.extractor;

import gov.va.escreening.vista.dto.VistaVeteranAppointment;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class OrwptApptlstListExtractor implements VistaRecordExtractor<List<VistaVeteranAppointment>> {

    @Override
    public List<VistaVeteranAppointment> extractData(String record) {

        List<VistaVeteranAppointment> resultList = new ArrayList<VistaVeteranAppointment>();

        if (StringUtils.isBlank(record)) {
            return null;
        }
        
        if ("^Error encountered^Error encountered\n".equalsIgnoreCase(record)) {
            return null;
        }

        String[] records = StringUtils.split(record, '\n');

        if (records != null && records.length > 0) {

            OrwptApptlstExtractor orwptApptlstExtractor = new OrwptApptlstExtractor();

            for (int i = 0; i < records.length; ++i) {
                VistaVeteranAppointment appt = orwptApptlstExtractor.extractData(records[i]);
                if (appt != null) {
                    resultList.add(appt);
                }
            }
        }

        return resultList;
    }

}
