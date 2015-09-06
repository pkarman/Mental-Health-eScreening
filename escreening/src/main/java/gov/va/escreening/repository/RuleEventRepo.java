package gov.va.escreening.repository;

import gov.va.escreening.entity.Rule;
import gov.va.escreening.entity.RuleEvent;

/**
 * Created by mzhu on 9/5/15.
 */
public interface RuleEventRepo extends RepositoryInterface<RuleEvent> {
    void deleteRuleEventByEventId(int eventId);
}
