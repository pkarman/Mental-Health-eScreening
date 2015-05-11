package gov.va.escreening.repository;

import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.dto.rule.RuleDto;
import gov.va.escreening.entity.Rule;
import gov.va.escreening.entity.SurveyMeasureResponse;

import java.util.Collection;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RuleRepositoryImpl extends AbstractHibernateRepository<Rule> implements RuleRepository {

    @Autowired
    VeteranAssessmentRepository vetAssessRepo;

    public RuleRepositoryImpl() {
        super();

        setClazz(Rule.class);
    }

    @Override
    public List<Rule> getRulesForResponses(Collection<SurveyMeasureResponse> responses) {
        // TODO: make this smarter. First we can only grab rules that can possibly be triggered by the responses, but we
        // can also filter out any rules that have event which have already fired. Make sure to think about the right
        // way to include visibility-base rules (i.e. the questions they affect are in the events and not in the rule's
        // expression)

        // Also, if, when a formula is added to the system, we not only record the variables it requires but also copy
        // any of the required
        // variables of any sub-formulas it relies on, we would be able to make this calculation (of what rules need to
        // be run) more efficient

        return findAll();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Rule> getRuleForAssessment(int veteranAssessmentID){
        Integer assessmentId = veteranAssessmentID;
        
        String sql = String.format(ASSESSMENT_RULES_QUERY_FORMAT, assessmentId, assessmentId, assessmentId, assessmentId);

        return entityManager.createNativeQuery(sql, Rule.class).getResultList();
    }

    @Override
    public List<RuleDto> findAllLight() {
        return entityManager.createQuery("SELECT NEW " + RuleDto.class.getCanonicalName() + "(r.ruleId, r.name) FROM Rule r", RuleDto.class).getResultList();
    }
    
    //TODO: This and getRulesForResponses can be combined to make the process of picking rules more efficient since we know what has changed 
    private static String ASSESSMENT_RULES_QUERY_FORMAT =
            "select distinct rule.* from rule, rule_assessment_variable, assessment_variable "
            + "where rule_assessment_variable.rule_id = rule.rule_id "
            + "and rule_assessment_variable.assessment_variable_id=assessment_variable.assessment_variable_id "
            + "and ( "
                //include all rules based on custom variables
                + "assessment_variable.assessment_variable_type_id=" + AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_CUSTOM
                //include rules that use measures in this assessment
                +" OR assessment_variable.measure_id in "
                    + "(select measure_id from survey_measure_response where veteran_assessment_id=%d) "
                //include rules that use answers in this assessment
                + "OR assessment_variable.measure_answer_id in "
                    + "(select measure_answer_id from survey_measure_response where veteran_assessment_id=%d) "
                //include rules that use formulas which are based on questions or answers for this assessment
                + "OR assessment_variable.assessment_variable_id in " 
                    + "(select parent_av.assessment_variable_id from assessment_variable parent_av "
                    + "join assessment_var_children avc on parent_av.assessment_variable_id=avc.variable_parent "
                    + "join assessment_variable child_av on avc.variable_child=child_av.assessment_variable_id "
                    + "where "
                        + "child_av.measure_id in (select measure_id from survey_measure_response where veteran_assessment_id=%d) " 
                    + "OR "
                        + "child_av.measure_answer_id in (select measure_answer_id from survey_measure_response where veteran_assessment_id=%d)"
                    + ")"
            + ")";
}
