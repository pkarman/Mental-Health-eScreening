package gov.va.escreening.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class DataTableResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty(value = "iTotalRecords")
    private int totalRecords;

    @JsonProperty(value = "iTotalDisplayRecords")
    private int totalDisplayRecords;

    @JsonProperty(value = "sEcho")
    private String echo;

    @JsonProperty(value = "sColumns")
    private String columns;

    @JsonProperty(value = "aaData")
    private List<T> data = new ArrayList<T>();

    /**
     * Total number of data.
     * @return
     */
    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    /**
     * Total number of records after filtering data.
     * @return
     */
    public int getTotalDisplayRecords() {
        return totalDisplayRecords;
    }

    public void setTotalDisplayRecords(int totalDisplayRecords) {
        this.totalDisplayRecords = totalDisplayRecords;
    }

    /**
     * Property to hold sEcho string sent by the client to be echoed back.
     * @return
     */
    public String getEcho() {
        return echo;
    }

    public void setEcho(String echo) {
        this.echo = echo;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    /**
     * The payload.
     * @return
     */
    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public DataTableResponse() {
    }

    @Override
    public String toString() {
        return "DataTableResponse [totalRecords=" + totalRecords + ", totalDisplayRecords=" + totalDisplayRecords
                + ", echo=" + echo + ", columns=" + columns + ", data=" + data + "]";
    }

}
