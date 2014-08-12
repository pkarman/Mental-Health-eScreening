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
 * @type {*|EScreeningDashboardApp.models.RuleAssessmentVariablesTransformer|*|EScreeningDashboardApp.models.RuleAssessmentVariablesTransformer|Object|*|Object|*}
 */
EScreeningDashboardApp.models.RuleAssessmentVariablesTransformer =  EScreeningDashboardApp.namespace("gov.va.escreening.models.RuleAssessmentVariablesTransformer");
/**
 * Static method for the RuleAssessmentVariablesTransformer class that is responsible for transforming the JSON Representation of the
 * RuleAssessmentVariable domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonResponse  Represents the JSON response of a service call request.
 * @returns {EScreeningDashboardApp.models.RuleAssessmentVariable[]} A list of existing RuleAssessmentVariable domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.RuleAssessmentVariablesTransformer.transformJSONPayload = function (jsonResponse) {
    'use strict';
    /**
     * Represent the existing RuleAssessmentVariables.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.RuleAssessmentVariable[]}
     */
    var existingRuleAssessmentVariables = [];

    if (!Object.isDefined(jsonResponse)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse can not be null or undefined.");
    }

    if (!Object.isDefined(jsonResponse.RuleAssessmentVariables)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.RuleAssessmentVariables can not be null or undefined.");
    }

    if (!Array.isArray(jsonResponse.RuleAssessmentVariables)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.RuleAssessmentVariables must be an Array.");
    }

    existingRuleAssessmentVariables = EScreeningDashboardApp.models.RuleAssessmentVariablesTransformer.transformJSONRuleAssessmentVariables(jsonResponse.RuleAssessmentVariables);

    return existingRuleAssessmentVariables;
};
/**
 * Static method for the RuleAssessmentVariablesTransformer class that is responsible for transforming the JSON Representation of the
 * RuleAssessmentVariable domain model object.
 *
 * @static
 * @method
 * @param {String[]}  jsonRuleAssessmentVariables  Represents the JSON representation ofRuleAssessmentVariables returned from the service call request.
 * @returns {EScreeningDashboardApp.models.RuleAssessmentVariables[]} A list of existing RuleAssessmentVariable domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.RuleAssessmentVariablesTransformer.transformJSONRuleAssessmentVariables = function (jsonRuleAssessmentVariables) {
    'use strict';
    /**
     * Represent the existing RuleAssessmentVariables.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.RuleAssessmentVariable[]}
     */
    var existingRuleAssessmentVariables = [];

    if (!Object.isDefined(jsonRuleAssessmentVariables)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonRuleAssessmentVariables can not be null or undefined.");
    }

    if (!Array.isArray(jsonRuleAssessmentVariables)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonRuleAssessmentVariables must be an Array.");
    }

    jsonRules.forEach(function(jsonRuleAssessmentVariable){
        existingRuleAssessmentVariables.push(new EScreeningDashboardApp.models.RuleAssessmentVariable(jsonRuleAssessmentVariable));
    });

    return existingRuleAssessmentVariables;
};