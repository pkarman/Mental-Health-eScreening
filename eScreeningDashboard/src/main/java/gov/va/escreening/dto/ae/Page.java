package gov.va.escreening.dto.ae;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("page")
public class Page implements Serializable {

    private static final long serialVersionUID = 1L;

    private String pageTitle;
    private List<Measure> measures;
    private int pageNumber;
    private String description;

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

    public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Page() {

    }

    @Override
    public String toString() {
        return "Page [pageTitle=" + pageTitle + ", measures=" + measures + "]";
    }

}
