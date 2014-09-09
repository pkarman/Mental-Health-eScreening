package gov.va.escreening.dto.editors;

import java.util.List;

/**
 * Created by pouncilt on 9/9/14.
 */
public class SurveyPageInfo {
    private String pageTitle;
    private List<SurveyInfo> surveys;

    public SurveyPageInfo() {}

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public List<SurveyInfo> getSurveys() {
        return surveys;
    }

    public void setSurveys(List<SurveyInfo> surveys) {
        this.surveys = surveys;
    }

    @Override
    public String toString() {
        return "SurveyPageInfo [pageTitle=" + pageTitle + ", surveys=" + surveys + "]";
    }
}
