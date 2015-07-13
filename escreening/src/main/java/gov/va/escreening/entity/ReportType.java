package gov.va.escreening.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by kliu on 2/22/15.
 */
@Entity
@Table(name = "report_type")
@NamedQueries({
        @NamedQuery(name = "ReportType.findAll", query = "SELECT r FROM ReportType r order by r.reportTypeId")})
public class ReportType implements Serializable{
    @Id
    @Basic(optional = false)
    @Column(name = "report_type_id")
    private Integer reportTypeId;

    @Basic(optional = false)
    @Column(name = "report_name")
    private String name;

    public Integer getReportTypeId() {
        return reportTypeId;
    }

    public void setReportTypeId(Integer reportTypeId) {
        this.reportTypeId = reportTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
