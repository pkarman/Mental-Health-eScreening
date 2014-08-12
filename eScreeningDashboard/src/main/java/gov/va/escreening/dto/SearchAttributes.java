package gov.va.escreening.dto;

import java.io.Serializable;

public class SearchAttributes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer pageSize;
    private Integer rowStartIndex;
    private String sortColumn;
    private SortDirection sortDirection;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getRowStartIndex() {
        return rowStartIndex;
    }

    public void setRowStartIndex(Integer rowStartIndex) {
        this.rowStartIndex = rowStartIndex;
    }

    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(SortDirection sortDirection) {
        this.sortDirection = sortDirection;
    }

    public SearchAttributes() {

    }
}
