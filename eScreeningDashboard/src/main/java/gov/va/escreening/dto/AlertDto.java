package gov.va.escreening.dto;

import gov.va.escreening.entity.DashboardAlert;

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

    /**
     * Constructor using entity. Please prefer this method.
     * @param dbAlert
     */
    public AlertDto(DashboardAlert dbAlert) {
        this.alertId = dbAlert.getDashboardAlertId();
        this.alertName = dbAlert.getName();
    }
    
    public AlertDto(Integer alertId, String alertName) {
        this.alertId = alertId;
        this.alertName = alertName;
    }
    
    /**
     * Needed for json decoding. please use the other constructor 
     */ 
    @SuppressWarnings("unused")
    private AlertDto(){}
}
