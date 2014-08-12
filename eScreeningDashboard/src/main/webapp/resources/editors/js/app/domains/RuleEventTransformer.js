/**
 * Represents the application api.  If the variable is already defined use it,
 * otherwise create an empty object.
 *
 * @type {EScreeningDashboardApp|*|EScreeningDashboardApp|*|{}|{}}
 */
var EScreeningDashboardApp = EScreeningDashboardApp || {};
/**
 * Represents the application static variable. Use existing static variable, if one already exists,
 * otherwise create a new one.
 *
 * @static
 * @type {*|EScreeningDashboardApp.models|*|EScreeningDashboardApp.models|Object|*|Object|*}
 */
EScreeningDashboardApp.models = EScreeningDashboardApp.models || EScreeningDashboardApp.namespace("gov.va.escreening.models");
/**
 * Represents the application static variable. Use existing static variable, if one already exists,
 * otherwise create a new one.
 *
 * @static
 * @type {*|EScreeningDashboardApp.models.RuleEventTransformer|*|EScreeningDashboardApp.models.RuleEventTransformer|Object|*|Object|*}
 */
EScreeningDashboardApp.models.RuleEventTransformer =  EScreeningDashboardApp.namespace("gov.va.escreening.models.RuleEventTransformer");
/**
 * Static method for the RuleEventTransformer class that is responsible for transforming the JSON Representation of the
 * RuleEvent domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonResponse  Represents the JSON response of a service call request.
 * @returns {EScreeningDashboardApp.models.RuleEvent[]} A list of existing RuleEvent domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.RuleEventTransformer.transformJSONPayload = function (jsonResponse) {
    'use strict';
    /**
     * Represent the existing RuleEvent.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.RuleEvent}
     */
    var existingRuleEvent = null;

    if (!Object.isDefined(jsonResponse)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse can not be null or undefined.");
    }

    if (!Object.isDefined(jsonResponse.RuleEvent)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.RuleEvent can not be null or undefined.");
    }

    existingRuleEvent = EScreeningDashboardApp.models.RuleEventTransformer.transformJSONRuleEvent(jsonResponse.RuleEvent);

    return existingRuleEvent;
};
/**
 * Static method for the RuleEventTransformer class that is responsible for transforming the JSON Representation of the
 * RuleEvent domain model object.
 *
 * @static
 * @method
 * @param {String[]}  jsonRuleEvent  Represents the JSON representation of the RuleEvent returned from the service call request.
 * @returns {EScreeningDashboardApp.models.RuleEvent} An existing RuleEvent domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.RuleEventTransformer.transformJSONRuleEvent = function (jsonRuleEvent) {
    'use strict';
    /**
     * Represent the existing RuleEvent.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.RuleEvent}
     */
    var existingRuleEvent = null;

    if (!Object.isDefined(jsonRuleEvent)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonRuleEvent can not be null or undefined.");
    }

    return existingRuleEvent;
};