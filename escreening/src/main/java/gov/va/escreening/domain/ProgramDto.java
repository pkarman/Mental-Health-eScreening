package gov.va.escreening.domain;

import java.io.Serializable;

public class ProgramDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Integer programId;
    private final String name;
    private final Boolean isDisabled;

    public ProgramDto(Integer programId, String name, Boolean isDisabled) {
        this.isDisabled = isDisabled;
        this.name = name;
        this.programId = programId;
    }

    public Integer getProgramId() {
        return programId;
    }

    public String getName() {
        return name;
    }

    public Boolean getIsDisabled() {
        return isDisabled;
    }

}
