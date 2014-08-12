package gov.va.escreening.dto.dashboard;

import java.io.Serializable;
import java.util.List;

public class SearchResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer totalNumRowsFound;
    private List<T> resultList;

    public Integer getTotalNumRowsFound() {
        return totalNumRowsFound;
    }

    public void setTotalNumRowsFound(Integer totalNumRowsFound) {
        this.totalNumRowsFound = totalNumRowsFound;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }

    public SearchResult() {

    }
}
