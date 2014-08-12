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
 * @type {*|EScreeningDashboardApp.models.RuleEventsTransformer|*|EScreeningDashboardApp.models.RuleEventsTransformer|Object|*|Object|*}
 */
EScreeningDashboardApp.models.RuleEventsTransformer =  EScreeningDashboardApp.namespace("gov.va.escreening.models.RuleEventsTransformer");
/**
 * Static method for the RuleEventsTransformer class that is responsible for transforming the JSON Representation of the
 * RuleEvent domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonResponse  Represents the JSON response of a service call request.
 * @returns {EScreeningDashboardApp.models.RuleEvent[]} A list of existing RuleEvent domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.RuleEventsTransformer.transformJSONPayload = function (jsonResponse) {
    'use strict';
    /**
     * Represent the existing RuleEvents.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.RuleEvent[]}
     */
    var existingRuleEvents = [];

    if (!Object.isDefined(jsonResponse)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse can not be null or undefined.");
    }

    if (!Object.isDefined(jsonResponse.RuleEvents)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.RuleEvents can not be null or undefined.");
    }

    if (!Array.isArray(jsonResponse.RuleEvents)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.RuleEvents must be an Array.");
    }

    existingRuleEvents = EScreeningDashboardApp.models.RuleEventsTransformer.transformJSONRuleEvents(jsonResponse.RuleEvents);

    return existingRuleEvents;
};
/**
 * Static method for the RuleEventsTransformer class that is responsible for transforming the JSON Representation of the
 * RuleEvent domain model object.
 *
 * @static
 * @method
 * @param {String[]}  jsonRuleEvents  Represents the JSON representation of RuleEvents returned from the service call request.
 * @returns {EScreeningDashboardApp.models.RuleEvent[]} A list of existing RuleEvent domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.RuleEventsTransformer.transformJSONRuleEvents = function (jsonRuleEvents) {
    'use strict';
    /**
     * Represent the existing RuleEvents.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.RuleEvent[]}
     */
    var existingRuleEvents = [];

    if (!Object.isDefined(jsonRuleEvents)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonRuleEvents can not be null or undefined.");
    }

    if (!Array.isArray(jsonRuleEvents)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonRuleEvents must be an Array.");
    }

    jsonRuleEvents.forEach(function(jsonRule){
        existingRuleEvents.push(new EScreeningDashboardApp.models.Rule(jsonRuleEvent));
    });

    return existingRuleEvents;
};