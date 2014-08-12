package gov.va.escreening.dto.ae;

import gov.va.escreening.entity.VeteranAssessmentSurvey;

import java.io.Serializable;

/**
 * Holds data about survey progress (e.g. question counts, response counts, display order)
 * 
 * @author Robin Carnow
 *
 */
public class SurveyProgress implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Integer surveyId;
    private final Integer sectionId;
    private final Integer displayOrder;
    private final Integer totalQuestionCount;
    private final Integer totalResponseCount;
    
    public Integer getSurveyId() {
        return surveyId;
    }
    
    public Integer getSectionId() {
        return sectionId;
    }
    
    public Integer getTotalQuestionCount() {
        return totalQuestionCount;
    }

    public Integer getTotalResponseCount() {
        return totalResponseCount;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public SurveyProgress(VeteranAssessmentSurvey veteranAssessmentSurvey) {
        this.surveyId = veteranAssessmentSurvey.getSurvey().getSurveyId();
        this.sectionId = veteranAssessmentSurvey.getSurvey().getSurveySection().getSurveySectionId();
        this.displayOrder = veteranAssessmentSurvey.getSurvey().getDisplayOrder();
        
        if(veteranAssessmentSurvey.getTotalQuestionCount() != null)
        {
            this.totalQuestionCount = veteranAssessmentSurvey.getTotalQuestionCount();
        }
        else
        {
            this.totalQuestionCount = 0;
        }
        this.totalResponseCount = veteranAssessmentSurvey.getTotalResponseCount();
        
        
    }

    @Override
    public String toString() {
        return "ProgressStatus [progressItem=" + surveyId 
                + ", totalQuestionCount=" + totalQuestionCount
                + ", totalResonseCount=" + totalResponseCount
                + ", displayOrder=" + displayOrder + "]";
    }
}
