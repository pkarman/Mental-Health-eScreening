package gov.va.escreening.service.export;

import gov.va.escreening.domain.ExportTypeEnum;
import gov.va.escreening.dto.dashboard.ExportDataSearchResult;
import gov.va.escreening.entity.ExportLog;
import gov.va.escreening.repository.ExportLogRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ExportLogServiceImpl implements ExportLogService {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(ExportLogServiceImpl.class);

    @Autowired
    private ExportLogRepository exportLogRepository;

    @Override
    public List<ExportDataSearchResult> getExportLogs(List<Integer> programIdList) {

        // TODO will need to modify after questions are answered
        List<ExportLog> exportlogs = exportLogRepository.findAll();
        List<ExportDataSearchResult> exportSearchResults = new ArrayList<ExportDataSearchResult>();

        for (ExportLog exportLog : exportlogs) {
            exportSearchResults.add(createSearchResult(exportLog));
        }

        return exportSearchResults;
    }

    private ExportDataSearchResult createSearchResult(ExportLog exportLog) {

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        ExportDataSearchResult exportResult = new ExportDataSearchResult();

        if (exportLog.getDateCreated() != null) {
            Date date = exportLog.getDateCreated();
            exportResult.setExportedOn(df.format(date));
        }
        else
            exportResult.setExportedOn("");

        if (exportLog.getAssessmentEndFilter() != null) {
            Date date = exportLog.getAssessmentEndFilter();
            exportResult.setAssessmentEndDate(df.format(date));
        }
        else
            exportResult.setAssessmentEndDate("");

        if (exportLog.getAssessmentStartFilter() != null) {
            Date date = exportLog.getAssessmentStartFilter();
            exportResult.setAssessmentStartDate(df.format(date));
        }
        else
            exportResult.setAssessmentStartDate("");

        if (exportLog.getClinician() != null) {
            String name = exportLog.getClinician().getLastName();
            if (exportLog.getClinician().getFirstName() != null && !exportLog.getClinician().getFirstName().isEmpty()) {
                name = String.format("%s, %s", name, exportLog.getClinician().getFirstName());
            }
            exportResult.setAssignedClinician(name);
        }
        else
            exportResult.setAssignedClinician("");

        if (exportLog.getComment() != null && !exportLog.getComment().isEmpty())
            exportResult.setComment(exportLog.getComment());
        else
            exportResult.setComment("");

        if (exportLog.getCreatedByUser() != null) {
            String name = exportLog.getCreatedByUser().getLastName();
            if (exportLog.getCreatedByUser().getFirstName() != null
                    && !exportLog.getCreatedByUser().getFirstName().isEmpty()) {
                name = String.format("%s, %s", name, exportLog.getCreatedByUser().getFirstName());
            }
            exportResult.setCreatedByUser(name);
        }
        else
            exportResult.setCreatedByUser("");

        if (exportLog.getExportedByUser() != null) {
            String name = exportLog.getExportedByUser().getLastName();
            if (exportLog.getExportedByUser().getFirstName() != null
                    && !exportLog.getExportedByUser().getFirstName().isEmpty()) {
                name = String.format("%s, %s", name, exportLog.getExportedByUser().getFirstName());
            }
            exportResult.setExportedBy(name);
        }

        if (exportLog.getExportLogId() != null)
            exportResult.setExportLogId(exportLog.getExportLogId());

        if (exportLog.getExportType() != null) {
            Integer exportType = exportLog.getExportType().getExportTypeId();
            if (exportType == ExportTypeEnum.IDENTIFIED.getExportTypeId())
                exportResult.setExportType("Identified");
            else
                exportResult.setExportType("De-identified");
        }
        else
            exportResult.setExportType("");

        if (exportLog.getProgram() != null && exportLog.getProgram().getName() != null
                && !exportLog.getProgram().getName().isEmpty())
            exportResult.setProgramName(exportLog.getProgram().getName());
        else
            exportResult.setProgramName("");

        if (exportLog.getVeteran() != null)
            exportResult.setVeteranId(exportLog.getVeteran().getVeteranId());

        return exportResult;
    }
}