package gov.va.escreening.controller.dashboard;

import gov.va.escreening.domain.UserStatusEnum;
import gov.va.escreening.dto.DataTableResponse;
import gov.va.escreening.dto.DropDownObject;
import gov.va.escreening.dto.SearchAttributes;
import gov.va.escreening.dto.SortDirection;
import gov.va.escreening.dto.dashboard.AssessmentSearchResult;
import gov.va.escreening.dto.dashboard.SearchResult;
import gov.va.escreening.form.AssessmentReportFormBean;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.ProgramService;
import gov.va.escreening.service.UserService;
import gov.va.escreening.service.VeteranAssessmentService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/dashboard")
public class AssessmentReportRestController extends BaseDashboardRestController {

	private static final Logger logger = LoggerFactory.getLogger(AssessmentReportRestController.class);

	@Autowired
	private ProgramService programService;

	@Autowired
	private UserService userService;

	@Autowired
	private VeteranAssessmentService veteranAssessmentService;

	@RequestMapping(value = "/assessmentReport/services/assessments/search/init", method = RequestMethod.GET)
	@ResponseBody
	public AssessmentReportFormBean getFormBean(HttpServletRequest request) {

		AssessmentReportFormBean assessmentReportFormBean = new AssessmentReportFormBean();

		Integer veteranId = null;

		if (request.getSession().getAttribute("veteranId") != null) {
			String veteranIdString = request.getSession().getAttribute("veteranId").toString();
			request.getSession().removeAttribute("veteranId");

			if (StringUtils.isNotBlank(veteranIdString) && StringUtils.isNumeric(veteranIdString)) {
				veteranId = Integer.valueOf(veteranIdString);
			}
		}

		if (veteranId != null) {
			assessmentReportFormBean.setVeteranId(veteranId);
		}

		return assessmentReportFormBean;
	}

	@RequestMapping(value = "/assessmentReport/services/assessments/search", method = RequestMethod.POST)
	@ResponseBody
	public DataTableResponse<AssessmentSearchResult> searchAssessments(
			HttpServletRequest request, @CurrentUser EscreenUser escreenUser) {

		displaySearchParms(request);

		return getDataTableResponse(request, escreenUser, false);
	}

	@RequestMapping(value = "/assessmentReport/services/user/clinicians", method = RequestMethod.POST)
	@ResponseBody
	public List<DropDownObject> getClinician(@RequestParam Boolean includeAll,
			@CurrentUser EscreenUser escreenUser) {

		List<Integer> userStatusIdList = new ArrayList<Integer>();
		userStatusIdList.add(UserStatusEnum.ACTIVE.getUserStatusId());
		userStatusIdList.add(UserStatusEnum.INACTIVE.getUserStatusId());

		if (includeAll) {
			userStatusIdList.add(UserStatusEnum.DELETED.getUserStatusId());
		}

		List<DropDownObject> dropDownObjectList = userService.getAssessmentClinicianDropDownObjects(userStatusIdList, escreenUser.getProgramIdList());

		return dropDownObjectList;
	}

	@RequestMapping(value = "/assessmentReport/services/user/assessmentCreators", method = RequestMethod.POST)
	@ResponseBody
	public List<DropDownObject> getAssessmentCreators(
			@RequestParam Boolean includeAll,
			@CurrentUser EscreenUser escreenUser) {

		List<Integer> userStatusIdList = new ArrayList<Integer>();
		userStatusIdList.add(UserStatusEnum.ACTIVE.getUserStatusId());
		userStatusIdList.add(UserStatusEnum.INACTIVE.getUserStatusId());

		if (includeAll) {
			userStatusIdList.add(UserStatusEnum.DELETED.getUserStatusId());
		}

		List<DropDownObject> dropDownObjectList = userService.getAssessmentCreatorDropDownObjects(userStatusIdList, escreenUser.getProgramIdList());

		return dropDownObjectList;
	}

	@RequestMapping(value = "/assessmentReport/services/user/programs", method = RequestMethod.POST)
	@ResponseBody
	public List<DropDownObject> getPrograms(@CurrentUser EscreenUser escreenUser) {

		List<DropDownObject> dropDownObjectList = programService.getProgramDropDownObjects(escreenUser.getProgramIdList());

		return dropDownObjectList;
	}

	@RequestMapping(value = "/assessmentReport/services/assessments/search/pdf", method = RequestMethod.GET)
	public ModelAndView generateAssessmentSearchPdfReport(
			HttpServletRequest request, ModelAndView modelAndView,
			@CurrentUser EscreenUser escreenUser) {

		Map<String, Object> parameterMap = new HashMap<String, Object>();

		DataTableResponse<AssessmentSearchResult> dataTableResponse = getDataTableResponse(request, escreenUser, true);

		JRDataSource JRdataSource = new JRBeanCollectionDataSource(dataTableResponse.getData());
		parameterMap.put("datasource", JRdataSource);

		// assessmentSearchPdfReport bean has been declared in the
		// jasper-views.xml file
		modelAndView = new ModelAndView("assessmentSearchPdfReport", parameterMap);
		return modelAndView;
	}

	@RequestMapping(value = "/assessmentReport/services/assessments/search/xls", method = RequestMethod.GET)
	public ModelAndView generateAssessmentSearchXlsReport(
			HttpServletRequest request, ModelAndView modelAndView,
			@CurrentUser EscreenUser escreenUser) {

		Map<String, Object> parameterMap = new HashMap<String, Object>();

		DataTableResponse<AssessmentSearchResult> dataTableResponse = getDataTableResponse(request, escreenUser, true);

		JRDataSource JRdataSource = new JRBeanCollectionDataSource(dataTableResponse.getData());
		parameterMap.put("datasource", JRdataSource);

		// assessmentSearchPdfReport bean has been declared in the
		// jasper-views.xml file
		modelAndView = new ModelAndView("assessmentSearchXlsReport", parameterMap);
		return modelAndView;
	}

	@RequestMapping(value = "/assessmentReport/services/assessments/search/csv", method = RequestMethod.GET)
	public ModelAndView generateAssessmentSearchCsvReport(
			HttpServletRequest request, ModelAndView modelAndView,
			@CurrentUser EscreenUser escreenUser) {

		Map<String, Object> parameterMap = new HashMap<String, Object>();

		DataTableResponse<AssessmentSearchResult> dataTableResponse = getDataTableResponse(request, escreenUser, true);

		JRDataSource JRdataSource = new JRBeanCollectionDataSource(dataTableResponse.getData());
		parameterMap.put("datasource", JRdataSource);

		// assessmentSearchPdfReport bean has been declared in the
		// jasper-views.xml file
		modelAndView = new ModelAndView("assessmentSearchCsvReport", parameterMap);
		return modelAndView;
	}

	private DataTableResponse<AssessmentSearchResult> getDataTableResponse(
			HttpServletRequest request, EscreenUser escreenUser,
			boolean useMaxPageSize) {

		String echo = request.getParameter("sEcho");

		SearchAttributes searchAttributes = getSearchAttributes(request);

		if (useMaxPageSize) {
			searchAttributes.setPageSize(MAX_PAGE_SIZE);
		}

		AssessmentReportFormBean assessmentReportFormBean = getSearchFormBean(request, escreenUser);

		SearchResult<AssessmentSearchResult> assessmentSearchResult = null;

		if (assessmentReportFormBean.getHasParameter()) {
			// Get the data.
			assessmentSearchResult = veteranAssessmentService.searchVeteranAssessment(assessmentReportFormBean, searchAttributes);
		} else {
			assessmentSearchResult = new SearchResult<AssessmentSearchResult>();
			assessmentSearchResult.setResultList(new ArrayList<AssessmentSearchResult>());
			assessmentSearchResult.setTotalNumRowsFound(0);
		}

		// Populate the DataTableResponse for the data table.
		DataTableResponse<AssessmentSearchResult> dataTableResponse = new DataTableResponse<AssessmentSearchResult>();
		dataTableResponse.setEcho(echo);
		dataTableResponse.setTotalRecords(assessmentSearchResult.getTotalNumRowsFound());
		dataTableResponse.setTotalDisplayRecords(assessmentSearchResult.getTotalNumRowsFound());
		dataTableResponse.setData(assessmentSearchResult.getResultList());

		return dataTableResponse;
	}

	/**
	 * Inspects the request parameters and populates the searchAttributes.
	 * 
	 * @param request
	 * @return
	 */
	private SearchAttributes getSearchAttributes(HttpServletRequest request) {

		SearchAttributes searchAttributes = new SearchAttributes();

		// Sort direction.
		String sortDirection = request.getParameter("sSortDir_0");

		if (StringUtils.isNotBlank(sortDirection) && "desc".equalsIgnoreCase(sortDirection)) {
			searchAttributes.setSortDirection(SortDirection.SORT_DESCENDING);
		} else {
			searchAttributes.setSortDirection(SortDirection.SORT_ASCENDING);
		}

		// Start index
		if (StringUtils.isNotBlank(request.getParameter("iDisplayStart")) && StringUtils.isNumeric(request.getParameter("iDisplayStart"))) {
			searchAttributes.setRowStartIndex(Integer.parseInt(request.getParameter("iDisplayStart")));
		} else {
			searchAttributes.setRowStartIndex(0);
		}

		// Page size
		if (StringUtils.isNotBlank(request.getParameter("iDisplayLength")) && StringUtils.isNumeric(request.getParameter("iDisplayLength"))) {
			searchAttributes.setPageSize(Integer.parseInt(request.getParameter("iDisplayLength")));
		} else {
			searchAttributes.setPageSize(10);
		}

		// Sort column
		int sortColumnIndex = 0;
		String sortColumn;

		if (StringUtils.isNotBlank(request.getParameter("iSortCol_0")) && StringUtils.isNumeric(request.getParameter("iSortCol_0"))) {
			sortColumnIndex = Integer.parseInt(request.getParameter("iSortCol_0"));
		}

		switch (sortColumnIndex) {
		case 0:
			sortColumn = "veteranAssessmentId";
			break;
		case 1:
			sortColumn = "programName";
			break;
		case 2:
			sortColumn = "clinicianName";
			break;
		case 3:
			sortColumn = "createdBy";
			break;
		case 4:
			sortColumn = "assessmentDate";
			break;
		case 5:
			sortColumn = "veteranId";
			break;
		case 6:
			sortColumn = "veteranName";
			break;
		case 7:
			sortColumn = "assessmentStatusName";
			break;
		case 8:
			sortColumn = "veteranAssessmentId";
			break;
		case 9:
			sortColumn = "veteranAssessmentId";
			break;
		case 10:
			sortColumn = "veteranAssessmentId";
			break;
		default:
			sortColumn = "veteranAssessmentId";
			break;
		}

		searchAttributes.setSortColumn(sortColumn);

		return searchAttributes;
	}

	/**
	 * Inspects the request parameters and populates the
	 * assessmentReportFormBean.
	 * 
	 * @param request
	 * @return
	 */
	private AssessmentReportFormBean getSearchFormBean(
			HttpServletRequest request, EscreenUser escreenUser) {

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		AssessmentReportFormBean assessmentReportFormBean = new AssessmentReportFormBean();
		assessmentReportFormBean.setHasParameter(false);

		if (StringUtils.isNotBlank(request.getParameter("assessmentReportFormBean.clinicianId")) && StringUtils.isNumeric(request.getParameter("assessmentReportFormBean.clinicianId"))) {
			assessmentReportFormBean.setClinicianId(Integer.parseInt(request.getParameter("assessmentReportFormBean.clinicianId")));
			assessmentReportFormBean.setHasParameter(true);
		}

		if (StringUtils.isNotBlank(request.getParameter("assessmentReportFormBean.createdByUserId")) && StringUtils.isNumeric(request.getParameter("assessmentReportFormBean.createdByUserId"))) {
			assessmentReportFormBean.setCreatedByUserId(Integer.parseInt(request.getParameter("assessmentReportFormBean.createdByUserId")));
			assessmentReportFormBean.setHasParameter(true);
		}

		if (StringUtils.isNotBlank(request.getParameter("assessmentReportFormBean.programId")) && StringUtils.isNumeric(request.getParameter("assessmentReportFormBean.programId"))) {
			assessmentReportFormBean.setProgramId(Integer.parseInt(request.getParameter("assessmentReportFormBean.programId")));
			assessmentReportFormBean.setHasParameter(true);
		}

		if (StringUtils.isNotBlank(request.getParameter("assessmentReportFormBean.veteranAssessmentId")) && StringUtils.isNumeric(request.getParameter("assessmentReportFormBean.veteranAssessmentId"))) {
			assessmentReportFormBean.setVeteranAssessmentId(Integer.parseInt(request.getParameter("assessmentReportFormBean.veteranAssessmentId")));
			assessmentReportFormBean.setHasParameter(true);
		}

		if (StringUtils.isNotBlank(request.getParameter("assessmentReportFormBean.veteranId")) && StringUtils.isNumeric(request.getParameter("assessmentReportFormBean.veteranId"))) {
			assessmentReportFormBean.setVeteranId(Integer.parseInt(request.getParameter("assessmentReportFormBean.veteranId")));
			assessmentReportFormBean.setHasParameter(true);
		}

		if (StringUtils.isNotBlank(request.getParameter("assessmentReportFormBean.fromAssessmentDate"))) {

			try {
				assessmentReportFormBean.setFromAssessmentDate(sdf.parse(request.getParameter("assessmentReportFormBean.fromAssessmentDate")));

				assessmentReportFormBean.setHasParameter(true);
			} catch (ParseException e) {
			}
		}

		if (StringUtils.isNotBlank(request.getParameter("assessmentReportFormBean.toAssessmentDate"))) {

			try {
				assessmentReportFormBean.setToAssessmentDate(sdf.parse(request.getParameter("assessmentReportFormBean.toAssessmentDate")));
				assessmentReportFormBean.setHasParameter(true);
			} catch (ParseException e) {
			}

		}

		// Populate the program id list from the logged in user.
		assessmentReportFormBean.setProgramIdList(escreenUser.getProgramIdList());

		return assessmentReportFormBean;
	}

}