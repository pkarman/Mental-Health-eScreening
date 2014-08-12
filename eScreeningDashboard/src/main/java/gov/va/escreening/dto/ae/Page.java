package gov.va.escreening.dto.ae;

import java.io.Serializable;
import java.util.List;

public class Page implements Serializable {

    private static final long serialVersionUID = 1L;

    private String pageTitle;
    private List<Measure> measures;

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public List<Measure> getMeasures() {
        return measures;
    }

    public void setMeasures(List<Measure> measures) {
        this.measures = measures;
    }

    public Page() {

    }

    @Override
    public String toString() {
        return "Page [pageTitle=" + pageTitle + ", measures=" + measures + "]";
    }

}
