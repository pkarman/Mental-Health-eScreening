package gov.va.escreening.controller.dashboard;

import gov.va.escreening.delegate.AssessmentDelegate;
import gov.va.escreening.domain.ClinicDto;
import gov.va.escreening.domain.SurveyDto;
import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.dto.ReportTypeDTO;
import gov.va.escreening.dto.dashboard.AssessmentAuditLogReport;
import gov.va.escreening.dto.report.ModuleGraphReportDTO;
import gov.va.escreening.dto.report.ScoreHistoryDTO;
import gov.va.escreening.dto.report.TableReportDTO;
import gov.va.escreening.entity.Veteran;
import gov.va.escreening.form.AssessmentLoginFormBean;
import gov.va.escreening.form.ReportSearchFormBean;
import gov.va.escreening.service.ClinicService;
import gov.va.escreening.service.ReportTypeService;
import gov.va.escreening.service.SurveyService;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.FileResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.script.ScriptEngineManager;
import javax.validation.Valid;
import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/dashboard")
public class ReportsController {

    private static final Logger logger = LoggerFactory.getLogger(ReportsController.class);

    @Autowired
    private ReportTypeService reportTypeService;

    @Autowired
    private AssessmentDelegate assessmentDelegate;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private ClinicService clinicService;

    private FileResolver fileResolver = new FileResolver() {

        @Override
        public File resolveFile(String fileName) {
            URI uri;
            try {
                uri = new URI(ReportsController.class.getResource(fileName).getPath());
                return new File(uri.getPath());
            } catch (URISyntaxException e) {
                System.out.println(fileName);
                logger.error("Fail to load image file for jaspereport "+fileName);
                return null;
            }
        }
    };

    /**
     * Initialize and setup page.
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/reports", method = RequestMethod.GET)
    public String setUpPageListView(Model model) {
        return "reports/index";
    }

    @RequestMapping(value = "/reports/averageScoresForPatientsByClinic", method = RequestMethod.GET)
    public String getAverageScoresForPatientsByClinic(Model model) {
        return "reports/averageScoresForPatientsByClinic";
    }

//    @RequestMapping(value = "/listresports", method = RequestMethod.GET)
//    public List<ReportTypeDTO> getAllReportTypes() {
//        return reportTypeService.getAllReportTypes();
//    }

    @RequestMapping(value = "/reports/listSurveys", method = RequestMethod.GET)
    @ResponseBody
    public List<SurveyDto> getAllSurveys() {
        return surveyService.getSurveyList();
    }

    @RequestMapping(value = "/reports/listClinics", method = RequestMethod.GET)
    @ResponseBody
    public List<ClinicDto> getAllClinics() {
        return clinicService.getClinicDtoList();
    }


    @RequestMapping(value = "/reports/individualStatisticsGraph", method = RequestMethod.GET)
    public ModelAndView generateIndividuleStatisticsGraphReport(){


        Map<String, Object> parameterMap = new HashMap<String, Object>();


        parameterMap.put("fromToDate", "From 02/01/2014 to 01/05/2015");
        parameterMap.put("lastNameSSN","Veteran1123, 1234");

        List<ModuleGraphReportDTO> resultList = new ArrayList<>();

        ModuleGraphReportDTO moduleGraphReportDTO = new ModuleGraphReportDTO();

        moduleGraphReportDTO.setScore("20");
        moduleGraphReportDTO.setModuleName("PHQ-9");
        moduleGraphReportDTO.setScoreMeaning("Severe");
        moduleGraphReportDTO.setScoreName("Last Depression Score");
        moduleGraphReportDTO.setScoreHistoryTitle("Score History by VistA Clinic");

        List<ScoreHistoryDTO> history = new ArrayList<>();

        for(int i=0; i<15; i++) {
            ScoreHistoryDTO scoreHistoryDTO = new ScoreHistoryDTO();
            scoreHistoryDTO.setClinicName(" Test Clinic "+i);
            scoreHistoryDTO.setScreeningDate("01/"+i+"/2015");
            history.add(scoreHistoryDTO);
        }


        moduleGraphReportDTO.setScoreHistory(history);

        resultList.add(moduleGraphReportDTO);

        moduleGraphReportDTO = new ModuleGraphReportDTO();

        moduleGraphReportDTO.setScore("24");
        moduleGraphReportDTO.setModuleName("PCL-C");
        moduleGraphReportDTO.setScoreMeaning("Moderate");
        moduleGraphReportDTO.setScoreName("Last Depression Score");
        moduleGraphReportDTO.setScoreHistoryTitle("Score History by VistA Clinic");

        history = new ArrayList<>();

        for(int i=0; i<5; i++) {
            ScoreHistoryDTO scoreHistoryDTO = new ScoreHistoryDTO();
            scoreHistoryDTO.setClinicName(" Test Clinic "+i);
            scoreHistoryDTO.setScreeningDate("02/"+i+"/2015");
            history.add(scoreHistoryDTO);
        }

        moduleGraphReportDTO.setScoreHistory(history);

        resultList.add(moduleGraphReportDTO);


        JRDataSource dataSource = new JRBeanCollectionDataSource(resultList);

        parameterMap.put("datasource", dataSource);
        parameterMap.put("REPORT_FILE_RESOLVER", fileResolver);

        System.out.println("aaaaaaacccsaa");
        return new ModelAndView("individualStatisticsGraphReport", parameterMap);
    }

    @RequestMapping(value = "/reports/individualStatistics", method = RequestMethod.GET)
    public ModelAndView generateIndividuleStatisticsReport() {
        logger.debug("Generating the individual statistics reports numeric only.");
        //logger.debug("Generating the individual statistics reports ssn:" + reportSearchForm.getLastFourSsn());

        // search veteran

        // Create a user object for searching.
       /* VeteranDto veteran = new VeteranDto();
        veteran.setLastName(reportSearchForm.getLastName());
        veteran.setSsnLastFour(reportSearchForm.getLastFourSsn());

        List<VeteranDto> veterans = assessmentDelegate.findVeterans(veteran);

        if (veterans == null || veterans.size() < 0) {
            // no veteran found.
            //modelAndView.addAttribute("reportsErrorMessage", "Could not find veterans matching the search criteria");


        }

        // generate reports

        List<AssessmentAuditLogReport> auditLogReportList = reportService.getAssessmentAuditLogByVeteran(assessmentId);
*/

        List<TableReportDTO> resultList = new ArrayList<>();

        TableReportDTO tb = new TableReportDTO();
        tb.setModuleName("PHQ-9");
        tb.setScreeningModuleName("Depression");
        tb.setScore("15 - Moderate\n" +
                "20 - Moderate Severe\n" +
                "27 - Severe");
        tb.setHistoryByClinic("02/01/2015  | LI SOC WK OEF ESCREENING\n" +
                "01/14/2015  | LI PRIM CARE HAGARICH\n" +
                "10/10/2014  | LI 2N MHAC URGENT CLINIC");
        resultList.add(tb);

        tb = new TableReportDTO();

        tb.setModuleName("PCL-C");
        tb.setScreeningModuleName("Post-traumatic Stress Disorder");
        tb.setScore("43 - Negative");
        tb.setHistoryByClinic("10/10/2014  |  LI SOC WK OEF ESCREENING");

        resultList.add(tb);

        JRDataSource dataSource = new JRBeanCollectionDataSource(resultList);

        Map<String, Object> parameterMap = new HashMap<String, Object>();

        parameterMap.put("lastNameSSN","Veteran1123, 1234");
        parameterMap.put("fromToDate", "From 02/01/2014 to 01/05/2015");
        parameterMap.put("datasource", dataSource);

        parameterMap.put("cast", resultList);



        parameterMap.put("reportTitle","PHQ-9");
        parameterMap.put("reportScore", "20");
        parameterMap.put("scoreMeaning", "Moderate Severe");

        parameterMap.put("REPORT_FILE_RESOLVER", fileResolver);
        parameterMap.put("history", "02/01/2015  | LI SOC WK OEF ESCREENING\n" +
                "01/14/2015  | LI PRIM CARE HAGARICH\n" +
                "10/10/2014  | LI 2N MHAC URGENT CLINIC");



       return new ModelAndView("IndividualStatisticsReportsNumericOnlyReport", parameterMap);
       //return new ModelAndView("GraphChart", parameterMap);
    }


}
