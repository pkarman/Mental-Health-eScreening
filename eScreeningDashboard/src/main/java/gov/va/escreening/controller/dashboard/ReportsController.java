package gov.va.escreening.controller.dashboard;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gov.va.escreening.delegate.AssessmentDelegate;
import gov.va.escreening.domain.ClinicDto;
import gov.va.escreening.domain.SurveyDto;
import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.dto.report.ModuleGraphReportDTO;
import gov.va.escreening.dto.report.ScoreHistoryDTO;
import gov.va.escreening.dto.report.TableReportDTO;
import gov.va.escreening.entity.SurveyScoreInterval;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.*;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.FileResolver;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.LocalDataSourceJobStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Controller
@RequestMapping(value = "/dashboard")
public class ReportsController {

    private static final Logger logger = LoggerFactory.getLogger(ReportsController.class);

    private static final String LASTNAME = "lastName";
    private static final String FROMDATE = "fromDate";
    private static final String SSN_LAST_FOUR="ssnLastFour";
    private static final String SURVEY_ID_LIST="surveysList";
    private static final String TODATE="toDate";

    @Autowired
    private AssessmentDelegate assessmentDelegate;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private VeteranAssessmentSurveyScoreService scoreService;

    @Autowired
    private SurveyScoreIntervalService intervalService;

    private FileResolver fileResolver = new FileResolver() {

        @Override
        public File resolveFile(String fileName) {
            URI uri;
            try {
                uri = new URI(ReportsController.class.getResource(fileName).getPath());
                return new File(uri.getPath());
            } catch (URISyntaxException e) {
                System.out.println(fileName);
                logger.error("Fail to load image file for jasper report " + fileName);
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
        return "reports";
    }

    @RequestMapping(value = "/individualStatisticsReports", method = RequestMethod.GET)
    public ModelAndView getIindividualStatisticReports() {
        return new ModelAndView("individualStatisticsReports");
    }

    @RequestMapping(value = "/listSurveys", method = RequestMethod.GET)
    @ResponseBody
    public List<SurveyDto> getAllSurveys() {
        return surveyService.getSurveyList();
    }

    @RequestMapping(value = "/listClinics", method = RequestMethod.GET)
    @ResponseBody
    public List<ClinicDto> getAllClinics() {
        return clinicService.getClinicDtoList();
    }

    @RequestMapping(value = "/individualStatisticsGraphicAndNumber", method = RequestMethod.POST)
    public ModelAndView genIndividualStatisticsGraphicAndNumber(HttpServletRequest request,
                                                       @RequestBody Map<String, Object> requestData,
                                                       @CurrentUser EscreenUser escreenUser) {
        return getIndividualStaticsGraphicPDF(requestData,  escreenUser,
                "individualStatisticsGraphNumberReport");
    }
    /**
     * REST endpoint will receive an list of svg objects plus any other data required to render a graphical report
     * This will simply prepare the required data + svg objects for the target Jasper report and forward
     *
     * @param request
     * @param requestData
     * @param escreenUser
     * @return
     */
    @RequestMapping(value = "/individualStatisticsGraphic", method = RequestMethod.POST)
    public ModelAndView genIndividualStatisticsGraphic(HttpServletRequest request,
                                                       @RequestBody Map<String, Object> requestData,
                                                       @CurrentUser EscreenUser escreenUser) {

        return getIndividualStaticsGraphicPDF(requestData,  escreenUser,
                 "individualStatisticsGraphReport");

    }

    private ModelAndView getIndividualStaticsGraphicPDF(Map<String, Object> requestData, EscreenUser escreenUser,
                                                        String viewName){

       ArrayList<String> svgObject = (ArrayList<String>)requestData.get("svgData");
       LinkedHashMap<String, Object> userReqData = (LinkedHashMap<String, Object>)requestData.get("userReqData");

        Map<String, Object> parameterMap = new HashMap<String, Object>();

        String lastName = (String)userReqData.get(LASTNAME);
        String last4SSN = (String)userReqData.get(SSN_LAST_FOUR);
        String fromDate = (String)userReqData.get(FROMDATE);
        String toDate = (String)userReqData.get(TODATE);
        ArrayList surveyIds = (ArrayList)userReqData.get(SURVEY_ID_LIST);

        parameterMap.put("fromToDate", "From "+fromDate+" to "+toDate);
        parameterMap.put("lastNameSSN", lastName+", "+last4SSN);

        VeteranDto veteran = new VeteranDto();
        veteran.setLastName(lastName);
        veteran.setSsnLastFour(last4SSN);

        List<VeteranDto> veterans = assessmentDelegate.findVeterans(veteran);

        Integer veteranId = -1;

        if (veterans!=null && !veterans.isEmpty()){
           veteranId =  veterans.get(0).getVeteranId();
        }

        List<ModuleGraphReportDTO> resultList = new ArrayList<>();

        for(int i=0; i< surveyIds.size(); i++){
            Integer surveyId = (Integer)surveyIds.get(i);
            ModuleGraphReportDTO moduleGraphReportDTO = scoreService.getGraphReportDTOForIndividual(surveyId, veteranId, fromDate, toDate);
            if (moduleGraphReportDTO.getHasData()) {
                moduleGraphReportDTO.setImageInput(
                        "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n" +
                                svgObject.get(i));

                moduleGraphReportDTO.setScoreHistoryTitle("Score History by VistA Clinic");
            }
            resultList.add(moduleGraphReportDTO);

        }

        JRDataSource dataSource = new JRBeanCollectionDataSource(resultList);

        parameterMap.put("datasource", dataSource);
        parameterMap.put("REPORT_FILE_RESOLVER", fileResolver);

        return new ModelAndView(viewName, parameterMap);
    }

    /**
     * REST enpoint point will receive search data (data entered bu the user in IndivStatsFormBean for numeric data report.
     * This should seek ehelp from service to grab the data form back end and forward it to the target JasperReport
     *
     * @param request
     * @param requestData
     * @param escreenUser
     * @return
     */
    @RequestMapping(value = "/requestChartableData", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> requestChartableData(HttpServletRequest request,
                                                          @RequestBody Map<String, Object> requestData,
                                                          @CurrentUser EscreenUser escreenUser) {

        List<Map<String, Object>> chartableDataList = Lists.newArrayList();

        logger.debug("Generating the individual statistics reports numeric only.");

        String lastName = (String)requestData.get(LASTNAME);
        String last4SSN = (String)requestData.get(SSN_LAST_FOUR);
        String fromDate = (String)requestData.get(FROMDATE);
        String toDate = (String)requestData.get(TODATE);

        VeteranDto veteran = new VeteranDto();
        veteran.setLastName(lastName);
        veteran.setSsnLastFour(last4SSN);

        List<VeteranDto> veterans = assessmentDelegate.findVeterans(veteran);

        if (veterans==null || veterans.isEmpty()){
            return chartableDataList;
        }

        Integer veteranId = veterans.get(0).getVeteranId();

        for (Object strSurveyId : (ArrayList) requestData.get(SURVEY_ID_LIST)) {

            Integer surveyId = (Integer) strSurveyId;

            chartableDataList.add(createChartableData(surveyId, veteranId, fromDate, toDate));
        }

        return chartableDataList;
    }

    public Map<String, Object> createChartableData(Integer surveyId, Integer veteranId, String fromDate, String toDate) {

        Map<String, Object> chartableDataMap = Maps.newHashMap();

        //first create the data
        String dataInJsonFormat = scoreService.getSurveyDataJsonForIndividualStatisticsGraph(surveyId, veteranId, fromDate, toDate);

        Gson gson = new GsonBuilder().create();
        Map dataSet = gson.fromJson(dataInJsonFormat, Map.class);

        String dataFormatInJsonFormat =
                intervalService.generateMetadataJson(surveyId);

        Map dataFormat = gson.fromJson(dataFormatInJsonFormat, Map.class);

        chartableDataMap.put("dataSet", dataSet);
        chartableDataMap.put("dataFormat", dataFormat);

        return chartableDataMap;
    }

    /**
     * REST enpoint point will receive search data (data entered bu the user in IndivStatsFormBean for numeric data report.
     * This should seek ehelp from service to grab the data form back end and forward it to the target JasperReport
     *
     * @param request
     * @param requestData
     * @param escreenUser
     * @return
     */
    @RequestMapping(value = "/individualStatisticsNumeric", method = RequestMethod.POST)
    public ModelAndView genIndividualStatisticsNumeric(HttpServletRequest request,
                                                       @RequestBody Map<String, Object> requestData,
                                                       @CurrentUser EscreenUser escreenUser) {

        logger.debug("Generating the individual statistics reports numeric only.");

        String lastName = (String)requestData.get(LASTNAME);
        String last4SSN = (String)requestData.get(SSN_LAST_FOUR);
        String fromDate = (String)requestData.get(FROMDATE);
        String toDate = (String)requestData.get(TODATE);

        logger.debug(" lastName :"+lastName);
        logger.debug(" last4SSN :"+ last4SSN);
        logger.debug( " From :"+fromDate+ " TO: "+toDate);

        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("lastNameSSN", lastName+", "+last4SSN);
        parameterMap.put("fromToDate", "From "+fromDate+" to "+toDate);

        VeteranDto veteran = new VeteranDto();
        veteran.setLastName(lastName);
        veteran.setSsnLastFour(last4SSN);

        List<VeteranDto> veterans = assessmentDelegate.findVeterans(veteran);

        JRDataSource dataSource = null;

        if (veterans == null || veterans.isEmpty()){
            dataSource = new JREmptyDataSource();
        }
        else {


            List<TableReportDTO> resultList = new ArrayList<>();


            for (Object strSurveyId : (ArrayList) requestData.get(SURVEY_ID_LIST)) {

                Integer surveyId = (Integer)strSurveyId;

                TableReportDTO tableReportDTO = scoreService.getSurveyDataForIndividualStatisticsReport(surveyId, veterans.get(0).getVeteranId(), fromDate, toDate);
                if (tableReportDTO != null){
                    resultList.add(tableReportDTO);
                }
            }

            if (resultList.isEmpty()) {
                dataSource = new JREmptyDataSource();
            }
            else{
                dataSource = new JRBeanCollectionDataSource(resultList);
            }
        }

        parameterMap.put("datasource", dataSource);
        parameterMap.put("REPORT_FILE_RESOLVER", fileResolver);

        return new ModelAndView("IndividualStatisticsReportsNumericOnlyReport", parameterMap);
    }
}
