package gov.va.escreening.controller.dashboard;

import gov.va.escreening.dto.DataTableResponse;
import gov.va.escreening.dto.SearchAttributes;
import gov.va.escreening.dto.SortDirection;
import gov.va.escreening.dto.dashboard.AssessmentSearchResult;
import gov.va.escreening.dto.dashboard.SearchResult;
import gov.va.escreening.form.AssessmentReportFormBean;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.VeteranAssessmentService;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/dashboard")
public class AssessmentDashboardController extends BaseDashboardRestController {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(AssessmentDashboardController.class);

    @Autowired
    private VeteranAssessmentService veteranAssessmentService;

    @RequestMapping(value = "/assessmentDashboard", method = RequestMethod.GET)
    public String setupPage(Model model, HttpServletRequest request) {

        return "dashboard/assessmentDashboard";
    }

    @RequestMapping(value = "/assessmentDashboard/services/assessments/search", method = RequestMethod.POST)
    @ResponseBody
    public DataTableResponse<AssessmentSearchResult> getAssessments(HttpServletRequest request, @CurrentUser EscreenUser escreenUser) {

        String echo = request.getParameter("sEcho");

        SearchAttributes searchAttributes = getSearchAttributes(request);

        displaySearchParms(request);

        String programIdString = request.getParameter("programId");

        // Get the data.
        SearchResult<AssessmentSearchResult> assessmentSearchResult = veteranAssessmentService.searchVeteranAssessment(programIdString, searchAttributes);

        // Populate the DataTableResponse for the datatable.
        DataTableResponse<AssessmentSearchResult> dataTableResponse = new DataTableResponse<AssessmentSearchResult>();
        dataTableResponse.setEcho(echo);
        dataTableResponse.setTotalRecords(assessmentSearchResult.getTotalNumRowsFound());
        dataTableResponse.setTotalDisplayRecords(assessmentSearchResult.getTotalNumRowsFound());
        dataTableResponse.setData(assessmentSearchResult.getResultList());

        return dataTableResponse;
    }

    /**
     * Extracts the search parameters from the JQuery DataTable.
     * @param request
     * @return
     */
    private SearchAttributes getSearchAttributes(HttpServletRequest request) {

        SearchAttributes searchAttributes = new SearchAttributes();

        // Sort direction.
        String sortDirection = request.getParameter("sSortDir_0");

        if (StringUtils.isNotBlank(sortDirection) && "desc".equalsIgnoreCase(sortDirection)) {
            searchAttributes.setSortDirection(SortDirection.SORT_DESCENDING);
        }
        else {
            searchAttributes.setSortDirection(SortDirection.SORT_ASCENDING);
        }

        // Start index
        if (StringUtils.isNotBlank(request.getParameter("iDisplayStart"))
                && StringUtils.isNumeric(request.getParameter("iDisplayStart"))) {
            searchAttributes.setRowStartIndex(Integer.parseInt(request.getParameter("iDisplayStart")));
        }
        else {
            searchAttributes.setRowStartIndex(0);
        }

        // Page size
        if (StringUtils.isNotBlank(request.getParameter("iDisplayLength"))
                && StringUtils.isNumeric(request.getParameter("iDisplayLength"))) {
            searchAttributes.setPageSize(Integer.parseInt(request.getParameter("iDisplayLength")));
        }
        else {
            searchAttributes.setPageSize(10);
        }

        // Sort column
        int sortColumnIndex = 0;
        String sortColumn;

        if (StringUtils.isNotBlank(request.getParameter("iSortCol_0"))
                && StringUtils.isNumeric(request.getParameter("iSortCol_0"))) {
            sortColumnIndex = Integer.parseInt(request.getParameter("iSortCol_0"));
        }

        switch (sortColumnIndex) {
        case 0:
            sortColumn = "assessmentDate";
            break;
        case 1:
            sortColumn = "veteranName";
            break;
        case 2:
            sortColumn = "ssnLastFour";
            break;
        case 3:
            sortColumn = "programName";
            break;
        case 4:
            sortColumn = "clinicianName";
            break;
        case 5:
            sortColumn = "duration";
            break;
        case 6:
            sortColumn = "percentComplete";
            break;
        case 7:
            sortColumn = "assessmentStatusName";
            break;
        default:
            sortColumn = "veteranAssessmentId";
            break;
        }

        searchAttributes.setSortColumn(sortColumn);

        return searchAttributes;
    }
}
