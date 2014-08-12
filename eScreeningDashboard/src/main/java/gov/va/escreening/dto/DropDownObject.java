package gov.va.escreening.dto;

import java.io.Serializable;

public class DropDownObject implements Serializable {

    private static final long serialVersionUID = 1L;

    private String stateId;
    private String stateName;

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public DropDownObject(String stateId, String stateName) {
        this.stateId = stateId;
        this.stateName = stateName;
    }

}
