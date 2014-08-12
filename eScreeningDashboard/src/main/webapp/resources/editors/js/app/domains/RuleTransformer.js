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
 * @type {*|EScreeningDashboardApp.models.RuleTransformer|*|EScreeningDashboardApp.models.RuleTransformer|Object|*|Object|*}
 */
EScreeningDashboardApp.models.RuleTransformer =  EScreeningDashboardApp.namespace("gov.va.escreening.models.RuleTransformer");
/**
 * Static method for the RuleTransformer class that is responsible for transforming the JSON Representation of the
 * Rule domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonResponse  Represents the JSON response of a service call request.
 * @returns {EScreeningDashboardApp.models.Rule[]} A list of existing Rule domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.RuleTransformer.transformJSONPayload = function (jsonResponse) {
    'use strict';
    /**
     * Represent the existing Rule.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.Rule}
     */
    var existingRule = null;

    if (!Object.isDefined(jsonResponse)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse can not be null or undefined.");
    }

    if (!Object.isDefined(jsonResponse.Rule)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.Rule can not be null or undefined.");
    }

    existingRule = EScreeningDashboardApp.models.RuleTransformer.transformJSONRule(jsonResponse.Rule);

    return existingRule;
};
/**
 * Static method for the RuleTransformer class that is responsible for transforming the JSON Representation of the
 * Rule domain model object.
 *
 * @static
 * @method
 * @param {String[]}  jsonRule  Represents the JSON representation of the Rule returned from the service call request.
 * @returns {EScreeningDashboardApp.models.Rule} An existing Rule domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.RuleTransformer.transformJSONRule = function (jsonRule) {
    'use strict';
    /**
     * Represent the existing Rule.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.Rule}
     */
    var existingRule = null;

    if (!Object.isDefined(jsonRule)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonRule can not be null or undefined.");
    }

    return existingRule;
};