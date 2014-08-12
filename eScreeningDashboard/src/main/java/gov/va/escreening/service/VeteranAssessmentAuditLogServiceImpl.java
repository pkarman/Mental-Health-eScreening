package gov.va.escreening.service;

import gov.va.escreening.dto.dashboard.AssessmentAuditLogReport;
import gov.va.escreening.entity.VeteranAssessmentAuditLog;
import gov.va.escreening.repository.VeteranAssessmentAuditLogRepository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class VeteranAssessmentAuditLogServiceImpl implements VeteranAssessmentAuditLogService {

	private static final Logger logger = LoggerFactory.getLogger(VeteranAssessmentAuditLogServiceImpl.class);

    @Autowired
    private VeteranAssessmentAuditLogRepository veteranAssessmentAuditLogRepository;
    
    @Transactional(readOnly = true)
    @Override
    public List<AssessmentAuditLogReport> getAssessmentAuditLogByAssessmentId (Integer assessmentId) {
        logger.trace("getAssessmentAuditLogByAssessmentId()");
        List<VeteranAssessmentAuditLog> assessmentAuditLogList = 
        		veteranAssessmentAuditLogRepository.getAssessmentAuditLogByAssessmentId(assessmentId);
        
        List<AssessmentAuditLogReport> auditLogReportList = new ArrayList<AssessmentAuditLogReport>();
        for(VeteranAssessmentAuditLog assessmentAuditLog : assessmentAuditLogList) {
        	AssessmentAuditLogReport auditLogReport = new AssessmentAuditLogReport();

        	auditLogReport.setEventId(assessmentAuditLog.getVeteranAssessmentAuditLogId());
        	auditLogReport.setAssessmentEvent(assessmentAuditLog.getVeteranAssessmentEventId().getName());
        	auditLogReport.setAssessmentState(assessmentAuditLog.getAssessmentStatusId().getName());
        	auditLogReport.setEventDate(assessmentAuditLog.getDateCreated());
        	
        	String lastName = assessmentAuditLog.getPersonLastName();
        	String firstName = assessmentAuditLog.getPersonFirstName();
        	String displayName = null;
        	if(firstName != null && !firstName.isEmpty()) 
        		displayName = lastName + ", " + firstName;
        	else 
        		displayName = lastName;

        	auditLogReport.setUpdatedBy(displayName);
        	
        	auditLogReportList.add(auditLogReport);
        }
        return auditLogReportList;
    }
    
    public void addLogEntry(VeteranAssessmentAuditLog auditLogEntry) {
    	veteranAssessmentAuditLogRepository.update(auditLogEntry);
    }
}
