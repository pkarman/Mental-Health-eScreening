package gov.va.escreening.controller.dashboard;

import gov.va.escreening.dto.dashboard.AssessmentAuditLogReport;
import gov.va.escreening.service.VeteranAssessmentAuditLogService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/dashboard")
public class AssessmentAuditLogRestController extends BaseDashboardRestController {
	
    @Autowired
    private VeteranAssessmentAuditLogService veteranAssessmentAuditLogService;
	
    private static final Logger logger = LoggerFactory.getLogger(AssessmentAuditLogRestController.class);

    @RequestMapping(method = RequestMethod.GET, value = "assessments/{assessmentId}/assessmentAuditLog/report/pdf")
    public ModelAndView generateAssessmentAuditLogReport(ModelAndView modelAndView, @PathVariable Integer assessmentId) {
    	
        logger.debug("Generating assessment audit log for assessment id: " + assessmentId);
        
        List<AssessmentAuditLogReport> auditLogReportList = veteranAssessmentAuditLogService.getAssessmentAuditLogByAssessmentId(assessmentId);
        
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        JRDataSource JRdataSource = new JRBeanCollectionDataSource(auditLogReportList);
        parameterMap.put("datasource", JRdataSource);
        modelAndView = new ModelAndView("assessmentAuditLogPdfReport", parameterMap);
        
        return modelAndView;
    }
    
    //////////////////////////////////////////
    //TODO, remove this code

    /*
    @RequestMapping(method = RequestMethod.GET, value = "assessments/test/write")
    public void generateTestWrite() {
    	
        logger.debug("inserting test record ");

        VeteranAssessmentAuditLog auditLogEntry = new VeteranAssessmentAuditLog();
        auditLogEntry.setPersonFirstName("firstname");
        auditLogEntry.setPersonId(100);
        auditLogEntry.setPersonLastName("lastname");
        auditLogEntry.setVeteranAssessmentId(2);
        
        AssessmentStatus status = new AssessmentStatus(1, "Clean", new Date());
        auditLogEntry.setAssessmentStatusId(status);
        
        VeteranAssessmentEvent event = new VeteranAssessmentEvent(1,"Assessment created", new Date()); 
        auditLogEntry.setVeteranAssessmentEventId(event);
        
        PersonType personType = new PersonType(1, "USER", new Date());
        auditLogEntry.setPersonTypeId(personType);
        
        veteranAssessmentAuditLogService.addLogEntry(auditLogEntry);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "assessmentAuditLog/assessment/report/pdf/test")
    public ModelAndView generateAuditLogReportTest(HttpServletRequest request, ModelAndView modelAndView) {

        logger.debug("--------------generating assessment audit log test ----------");
        List<AssessmentAuditLogReport> testResults = getTestReportData();
        
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        JRDataSource JRdataSource = new JRBeanCollectionDataSource(testResults);
        parameterMap.put("datasource", JRdataSource);
        modelAndView = new ModelAndView("assessmentAuditLogPdfReport", parameterMap);
        
        return modelAndView;
    }
    
    private List<AssessmentAuditLogReport> getTestReportData() {
    	List<AssessmentAuditLogReport> rows = new ArrayList<AssessmentAuditLogReport>();
    	
    	Date testDate = null;
    	try {
    		testDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse("08/16/2011 11:05:12");
    	}
    	catch(Exception e) {}
    	
    	AssessmentAuditLogReport row1 = new AssessmentAuditLogReport();
    	row1.setEventId(1);
    	row1.setAssessmentEvent("Assessment created");
    	row1.setAssessmentState("CLEAN");
    	row1.setEventDate(testDate); 
    	row1.setUpdatedBy("Clinician, One");
    	rows.add(row1);
    	
    	AssessmentAuditLogReport row2 = new AssessmentAuditLogReport();
    	row2.setEventId(2);
    	row2.setAssessmentEvent("Assessment updated");
    	row2.setAssessmentState("INCOMPLETE");
    	row2.setEventDate(testDate); 
    	row2.setUpdatedBy("One, Veteran");
    	rows.add(row2);
    	
    	AssessmentAuditLogReport row3 = new AssessmentAuditLogReport();
    	row3.setEventId(3);
    	row3.setAssessmentEvent("Assessment updated");
    	row3.setAssessmentState("INCOMPLETE");
    	row3.setEventDate(testDate); 
    	row3.setUpdatedBy("Clinician, One");
    	rows.add(row3);
    	
    	return rows;
    }
    */
}