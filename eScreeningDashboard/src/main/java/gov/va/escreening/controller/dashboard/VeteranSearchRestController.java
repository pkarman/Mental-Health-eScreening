package gov.va.escreening.controller.dashboard;

import gov.va.escreening.dto.DataTableResponse;
import gov.va.escreening.dto.SearchAttributes;
import gov.va.escreening.dto.SortDirection;
import gov.va.escreening.dto.dashboard.SearchResult;
import gov.va.escreening.dto.dashboard.VeteranSearchResult;
import gov.va.escreening.exception.UnregisteredDataTableColumnException;
import gov.va.escreening.form.VeteranSearchFormBean;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.VeteranService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/dashboard")
public class VeteranSearchRestController extends BaseDashboardRestController {

	private static final Logger logger = LoggerFactory.getLogger(VeteranSearchRestController.class);

	@Autowired
	private VeteranService veteranService;

	@RequestMapping(value = "/veteranSearch/services/veterans/search", method = RequestMethod.POST)
	@ResponseBody
	public DataTableResponse<VeteranSearchResult> searchVeterans(
			HttpServletRequest request,
			VeteranSearchFormBean veteranSearchFormBean, BindingResult result,
			@CurrentUser EscreenUser escreenUser) {

		logger.debug("In VeteranSearchRestController searchVeterans");

		// Get the search criteria sent by the client.
		// Need to figure out how we can get Spring to serialize this instead of
		// doing this ourselves.
		String veteranId = request.getParameter("veteranSearchFormBean.veteranId");
		String lastName = request.getParameter("veteranSearchFormBean.lastName");
		String ssnLastFour = request.getParameter("veteranSearchFormBean.ssnLastFour");

		if (StringUtils.isNotBlank(veteranId) && StringUtils.isNumeric(veteranId)) {
			veteranSearchFormBean.setVeteranId(Integer.valueOf(veteranId));
		}

		veteranSearchFormBean.setLastName(lastName);
		veteranSearchFormBean.setSsnLastFour(ssnLastFour);

		displaySearchParms(request);

		SearchAttributes searchAttributes = createSearchAttributes(request);

		// Do a search and get the result.
		SearchResult<VeteranSearchResult> serchResult = getVeteranSearchResultsFromForm(veteranSearchFormBean, searchAttributes, escreenUser);

		// Start populating the response.
		String echo = request.getParameter("sEcho");
		logger.debug("sEcho: " + echo);

		DataTableResponse<VeteranSearchResult> dataTableResponse = new DataTableResponse<VeteranSearchResult>();
		dataTableResponse.setEcho(echo);
		dataTableResponse.setTotalRecords(serchResult.getTotalNumRowsFound());
		dataTableResponse.setTotalDisplayRecords(serchResult.getTotalNumRowsFound());

		dataTableResponse.setData(serchResult.getResultList());

		return dataTableResponse;
	}

	private SearchAttributes createSearchAttributes(HttpServletRequest request) {

		SearchAttributes searchAttributes = new SearchAttributes();

		if (request.getParameter("iDisplayStart") != null) {
			try {
				Integer displayStart = Integer.parseInt(request.getParameter("iDisplayStart"));
				searchAttributes.setRowStartIndex(displayStart);
			} catch (NumberFormatException e) {
				logger.warn("iDisplayStart was not a number");
			}
		}

		if (request.getParameter("iDisplayLength") != null) {
			try {
				Integer pageSize = Integer.parseInt(request.getParameter("iDisplayLength"));
				searchAttributes.setPageSize(pageSize);
			} catch (NumberFormatException e) {
				logger.warn("iDisplayLength was not a number");
			}
		}

		if (request.getParameter("iSortCol_0") != null) {
			try {
				Integer sortColumn = Integer.parseInt(request.getParameter("iSortCol_0"));
				searchAttributes.setSortColumn(getViewColumnNameById(sortColumn));

				// get the sort direction if we got this far
				if (request.getParameter("sSortDir_0") != null) {
					if (request.getParameter("sSortDir_0").equalsIgnoreCase("asc"))
						searchAttributes.setSortDirection(SortDirection.SORT_ASCENDING);
					else
						searchAttributes.setSortDirection(SortDirection.SORT_DESCENDING);
				} else {
					searchAttributes.setSortDirection(SortDirection.SORT_DESCENDING);
				}
			} catch (NumberFormatException nfe) {
				logger.warn("iSortCol_0 was not a number");
			} catch (UnregisteredDataTableColumnException udtce) {
				logger.error(udtce.getMessage());
			}
		}

		return searchAttributes;
	}

	private String getViewColumnNameById(Integer columnId) {
		switch (columnId) {
		case 0:
			return VeteranSearchResult.VETERAN_ID;
		case 1:
			return VeteranSearchResult.LAST_NAME;
		case 2:
			return VeteranSearchResult.SSN_LAST_FOUR;
		case 3:
			return VeteranSearchResult.EMAIL;
		case 4:
			return VeteranSearchResult.GENDER;
		case 5:
			return VeteranSearchResult.LAST_ASSESSMENT_DATE;
		case 6:
			return VeteranSearchResult.TOTAL_ASSESSMENTS;
		}
		;

		throw new UnregisteredDataTableColumnException("columnId of: " + columnId + " was not registered.");
	}

	@RequestMapping(method = RequestMethod.GET, value = "veteranSearch/services/veterans/search/report/pdf")
	public ModelAndView generateVeteranSearchPdfReport(
			HttpServletRequest request, ModelAndView modelAndView,
			VeteranSearchFormBean veteranSearchFormBean, BindingResult result,
			@CurrentUser EscreenUser escreenUser) {

		logger.debug("--------------generating Veteran Search PDF Report ----------");

		Map<String, Object> parameterMap = getReportParameterMap(request, veteranSearchFormBean, escreenUser);

		// veteranSearchPdfReport bean has been declared in the jasper-views.xml
		// file
		modelAndView = new ModelAndView("veteranSearchPdfReport", parameterMap);

		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET, value = "veteranSearch/services/veterans/search/report/csv")
	public ModelAndView generateVeteranSearchCsvReport(
			HttpServletRequest request, ModelAndView modelAndView,
			VeteranSearchFormBean veteranSearchFormBean, BindingResult result,
			@CurrentUser EscreenUser escreenUser) {

		logger.debug("--------------generating Veteran Search CSV Report ----------");

		Map<String, Object> parameterMap = getReportParameterMap(request, veteranSearchFormBean, escreenUser);

		// veteranSearchCsvReport bean has been declared in the jasper-views.xml
		// file
		modelAndView = new ModelAndView("veteranSearchCsvReport", parameterMap);

		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET, value = "veteranSearch/services/veterans/search/report/xls")
	public ModelAndView generateVeteranSearchXlsReport(
			HttpServletRequest request, ModelAndView modelAndView,
			VeteranSearchFormBean veteranSearchFormBean, BindingResult result,
			@CurrentUser EscreenUser escreenUser) {

		logger.debug("--------------generating Veteran Search CSV Report ----------");

		Map<String, Object> parameterMap = getReportParameterMap(request, veteranSearchFormBean, escreenUser);

		// veteranSearchXlsReport bean has been declared in the jasper-views.xml
		// file
		modelAndView = new ModelAndView("veteranSearchXlsReport", parameterMap);
		return modelAndView;
	}

	private Map<String, Object> getReportParameterMap(
			HttpServletRequest request,
			VeteranSearchFormBean veteranSearchFormBean, EscreenUser escreenUser) {
		// Get the search criteria sent by the client.
		// Need to figure out how we can get Spring to serialize this instead of
		// doing this ourselves.
		String veteranId = request.getParameter("veteranSearchFormBean.veteranId");
		String lastName = request.getParameter("veteranSearchFormBean.lastName");
		String ssnLastFour = request.getParameter("veteranSearchFormBean.ssnLastFour");

		if (StringUtils.isNotBlank(veteranId) && StringUtils.isNumeric(veteranId)) {
			veteranSearchFormBean.setVeteranId(Integer.valueOf(veteranId));
		}

		veteranSearchFormBean.setLastName(lastName);
		veteranSearchFormBean.setSsnLastFour(ssnLastFour);

		displaySearchParms(request);

		Map<String, Object> parameterMap = new HashMap<String, Object>();

		SearchAttributes searchAttributes = createSearchAttributes(request);

		// Override to get up to max.
		searchAttributes.setPageSize(MAX_PAGE_SIZE);

		SearchResult<VeteranSearchResult> searchResult = getVeteranSearchResultsFromForm(veteranSearchFormBean, searchAttributes, escreenUser);

		JRDataSource JRdataSource = new JRBeanCollectionDataSource(searchResult.getResultList());
		parameterMap.put("datasource", JRdataSource);

		return parameterMap;
	}

	/**
	 * 
	 * @param veteranSearchFormBean
	 * @param searchAttributes
	 * @param basicUser
	 * @return
	 */
	private SearchResult<VeteranSearchResult> getVeteranSearchResultsFromForm(
			VeteranSearchFormBean veteranSearchFormBean,
			SearchAttributes searchAttributes, EscreenUser escreenUser) {

		boolean searchCriteriaProvided = hasSearchCriteria(veteranSearchFormBean);

		SearchResult<VeteranSearchResult> searchResult = null;

		if (searchCriteriaProvided) {
			searchResult = veteranService.searchVeterans(veteranSearchFormBean.getVeteranId(), veteranSearchFormBean.getLastName(), veteranSearchFormBean.getSsnLastFour(), escreenUser.getProgramIdList(), searchAttributes);
		} else {
			logger.debug("Veteran search criteria was not provided, returning an empty result set.");

			searchResult = new SearchResult<VeteranSearchResult>();
			searchResult.setResultList(new ArrayList<VeteranSearchResult>());
			searchResult.setTotalNumRowsFound(0);
		}

		return searchResult;
	}

	/**
	 * 
	 * @param veteranSearchFormBean
	 * @return
	 */
	private boolean hasSearchCriteria(
			VeteranSearchFormBean veteranSearchFormBean) {

		boolean searchCriteriaProvided = false;

		if (StringUtils.isNotBlank(veteranSearchFormBean.getLastName())) {
			searchCriteriaProvided = true;
		} else if (StringUtils.isNotBlank(veteranSearchFormBean.getSsnLastFour())) {
			searchCriteriaProvided = true;
		} else if (veteranSearchFormBean.getVeteranId() != null) {
			searchCriteriaProvided = true;
		}

		return searchCriteriaProvided;
	}
}
