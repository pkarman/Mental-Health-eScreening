package gov.va.escreening.vista.extractor;

import gov.va.escreening.vista.dto.VistaClinicAppointment;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class OrqptClinicPatientsListExtractor implements VistaRecordExtractor<List<VistaClinicAppointment>> {

    @Override
    public List<VistaClinicAppointment> extractData(String record) {

        List<VistaClinicAppointment> resultList = new ArrayList<VistaClinicAppointment>();

        if (StringUtils.isBlank(record)) {
            return null;
        }

        String[] records = StringUtils.split(record, '\n');

        if (records != null && records.length > 0) {

            OrqptClinicPatientsExtractor orqptClinicPatientsExtractor = new OrqptClinicPatientsExtractor();

            for (int i = 0; i < records.length; ++i) {
                resultList.add(orqptClinicPatientsExtractor.extractData(records[i]));
            }
        }

        return resultList;
    }

}
