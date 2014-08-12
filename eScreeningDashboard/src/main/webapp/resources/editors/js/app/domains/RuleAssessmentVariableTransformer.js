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
 * @type {*|EScreeningDashboardApp.models.RuleAssessmentVariableTransformer|*|EScreeningDashboardApp.models.RuleAssessmentVariableTransformer|Object|*|Object|*}
 */
EScreeningDashboardApp.models.RuleAssessmentVariableTransformer =  EScreeningDashboardApp.namespace("gov.va.escreening.models.RuleAssessmentVariableTransformer");
/**
 * Static method for the RuleAssessentVariableTransformer class that is responsible for transforming the JSON Representation of the
 * RuleAssessmentVariable domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonResponse  Represents the JSON response of a service call request.
 * @returns {EScreeningDashboardApp.models.RuleAssessmentVariable[]} A list of existing RuleAssessmentVariable domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.RuleAssessmentVariableTransformer.transformJSONPayload = function (jsonResponse) {
    'use strict';
    /**
     * Represent the existing RuleAssessmentVariable.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.RuleAssessmentVariable}
     */
    var existingRuleAssessmentVariable = null;

    if (!Object.isDefined(jsonResponse)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse can not be null or undefined.");
    }

    if (!Object.isDefined(jsonResponse.Rule)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.RuleAssessmentVariable can not be null or undefined.");
    }

    existingRuleAssessmentVariable = EScreeningDashboardApp.models.RuleAssessmentVariableTransformer.transformJSONRule(jsonResponse.RuleAssessmentVariable);

    return existingRuleAssessmentVariable;
};
/**
 * Static method for the RuleAssessmentVariableTransformer class that is responsible for transforming the JSON Representation of the
 * RuleAssessmentVariable domain model object.
 *
 * @static
 * @method
 * @param {String[]}  jsonRuleAssessmentVariable  Represents the JSON representation of the RuleAssessmentVariable returned from the service call request.
 * @returns {EScreeningDashboardApp.models.RuleAssessmentVariable} An existing RuleAssessmentVariable domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.RuleAssessmentVariableTransformer.transformJSONRule = function (jsonRuleAssessmentVariable) {
    'use strict';
    /**
     * Represent the existing RuleAssessmentVariable.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.RuleAssessmentVariable}
     */
    var existingRuleAssessmentVariable = null;

    if (!Object.isDefined(jsonRuleAssessmentVariable)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonRuleAssessmentVariable can not be null or undefined.");
    }

    return existingRuleAssessmentVariable;
};