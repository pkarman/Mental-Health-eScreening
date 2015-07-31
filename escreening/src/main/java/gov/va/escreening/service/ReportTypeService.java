package gov.va.escreening.service;

import gov.va.escreening.dto.ReportTypeDTO;

import java.util.List;

/**
 * Created by kliu on 2/22/15.
 */
public interface ReportTypeService {

    public List<ReportTypeDTO> getAllReportTypes();
}
