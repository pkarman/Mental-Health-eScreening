package gov.va.escreening.vista.extractor;

import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.vista.VistaUtils;

import org.apache.commons.lang3.StringUtils;

public class OrwptPtinqExtractor implements VistaRecordExtractor<VeteranDto> {

    @Override
    public VeteranDto extractData(String record) {

        // Parses the record that is a printout and not a real delimited record.

        if (StringUtils.isBlank(record)) {
            return null;
        }

        VeteranDto veteranDto = new VeteranDto();

        // There are multiple Phone labels. At least two.
        String phone = VistaUtils.readDataFromText(record, "Phone: ");
        String workPhone = VistaUtils.readDataFromText(record, "Office: ");
        String cellPhone = VistaUtils.readDataFromText(record, "Cell: ");
        String email = VistaUtils.readDataFromText(record, "E-mail: ");

        veteranDto.setPhone(phone);
        veteranDto.setWorkPhone(workPhone);
        veteranDto.setCellPhone(cellPhone);
        veteranDto.setEmail(email);

        return veteranDto;
    }

}
