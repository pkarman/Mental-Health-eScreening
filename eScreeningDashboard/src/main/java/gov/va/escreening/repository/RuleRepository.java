package gov.va.escreening.repository;

import gov.va.escreening.entity.Rule;
import gov.va.escreening.entity.SurveyMeasureResponse;

import java.util.Collection;
import java.util.List;

public interface RuleRepository extends RepositoryInterface<Rule> {

    /**
     * Uses the given responses to calculate any associated Rules
     * @param responses 
     * @return List of any Rules that depend on at least one of the given responses.  If not rules 
     * depend on the given responses then an empty List is returned (i.e. never null).
     */
    List<Rule> getRulesForResponses(Collection<SurveyMeasureResponse> responses);

    public abstract List<Rule> getRuleForAssessment(int veteranAssessmentID);
}
