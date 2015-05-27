package gov.va.escreening.delegate;

import gov.va.escreening.dto.report.TableReportDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by munnoo on 4/23/15.
 */
public interface ReportFunction {
    void createReport(Object[] args);
}
