package gov.va.escreening.repository;

import gov.va.escreening.entity.ExportLog;
import gov.va.escreening.entity.ExportLogAudit;

import java.util.List;

public interface ExportLogAuditRepository extends RepositoryInterface<ExportLogAudit> {

    List<ExportLogAudit> findAllForDays(int noOfDays);
}