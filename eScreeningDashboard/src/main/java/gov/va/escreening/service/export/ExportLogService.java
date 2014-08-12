package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.ExportDataSearchResult;

import java.util.List;

public interface ExportLogService {

    List<ExportDataSearchResult> getExportLogs(List<Integer> programIdList);

}
