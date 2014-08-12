package gov.va.escreening.domain;

public enum AssessmentStatusEnum {

    CLEAN(1),
    INCOMPLETE(2),
    COMPLETE(3),
    REVIEWED(4),
    FINALIZED(5),
    ERROR(6);

    private final int assessmentStatusId;

    public int getAssessmentStatusId() {
        return this.assessmentStatusId;
    }

    AssessmentStatusEnum(int assessmentStatusId) {
        this.assessmentStatusId = assessmentStatusId;
    }

}
