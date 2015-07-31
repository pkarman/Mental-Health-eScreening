package gov.va.escreening.domain;

import java.io.Serializable;

public class ProgramDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer programId;
    private String name;
    private Boolean isDisabled;

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

    public ProgramDto() {

    }

}
