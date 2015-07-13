package gov.va.escreening.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class ProgramEditViewFormBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer programId;

    @Size(max = 50, message = "Program Name must be 50 characters or less")
    @NotEmpty(message = "Program name is required")
    private String name;
    private Boolean isDisabled;
    private List<Integer> selectedBatteryIdList = new ArrayList<Integer>();
    private List<Integer> selectedClinicIdList = new ArrayList<Integer>();
    private List<Integer> selectedNoteTitleIdList = new ArrayList<Integer>();

    public Integer getProgramId() {
        return programId;
    }

    public void setProgramId(Integer programId) {
        this.programId = programId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public List<Integer> getSelectedClinicIdList() {
        return selectedClinicIdList;
    }

    public void setSelectedClinicIdList(List<Integer> selectedClinicIdList) {
        this.selectedClinicIdList = selectedClinicIdList;
    }

    public List<Integer> getSelectedNoteTitleIdList() {
        return selectedNoteTitleIdList;
    }

    public void setSelectedNoteTitleIdList(List<Integer> selectedNoteTitleIdList) {
        this.selectedNoteTitleIdList = selectedNoteTitleIdList;
    }

    public ProgramEditViewFormBean() {
        // default constructor.
    }

	public List<Integer> getSelectedBatteryIdList() {
		return selectedBatteryIdList;
	}

	public void setSelectedBatteryIdList(List<Integer> selectedBatteryIdList) {
		this.selectedBatteryIdList = selectedBatteryIdList;
	}
}
