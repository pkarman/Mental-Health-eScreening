package gov.va.escreening.dto;

import java.io.Serializable;

public class AlertDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer alertId;
    private String alertName;

    public Integer getAlertId() {
        return alertId;
    }

    public void setAlertId(Integer alertId) {
        this.alertId = alertId;
    }

    public String getAlertName() {
        return alertName;
    }

    public void setAlertName(String alertName) {
        this.alertName = alertName;
    }

    public AlertDto() {
        // default constructor.
    }

    public AlertDto(Integer alertId, String alertName) {
        this.alertId = alertId;
        this.alertName = alertName;
    }

}
