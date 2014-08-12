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
 * @type {*|EScreeningDashboardApp.models.RulesTransformer|*|EScreeningDashboardApp.models.RulesTransformer|Object|*|Object|*}
 */
EScreeningDashboardApp.models.RulesTransformer =  EScreeningDashboardApp.namespace("gov.va.escreening.models.RulesTransformer");
/**
 * Static method for the RulesTransformer class that is responsible for transforming the JSON Representation of the
 * Rule domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonResponse  Represents the JSON response of a service call request.
 * @returns {EScreeningDashboardApp.models.Rule[]} A list of existing Rule domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.RulesTransformer.transformJSONPayload = function (jsonResponse) {
    'use strict';
    /**
     * Represent the existing Rules.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.Rule[]}
     */
    var existingRules = [];

    if (!Object.isDefined(jsonResponse)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse can not be null or undefined.");
    }

    if (!Object.isDefined(jsonResponse.Rules)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.Rules can not be null or undefined.");
    }

    if (!Array.isArray(jsonResponse.Rules)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.Rules must be an Array.");
    }

    existingRules = EScreeningDashboardApp.models.RulesTransformer.transformJSONRules(jsonResponse.Rules);

    return existingRules;
};
/**
 * Static method for the RulesTransformer class that is responsible for transforming the JSON Representation of the
 * Rule domain model object.
 *
 * @static
 * @method
 * @param {String[]}  jsonRules  Represents the JSON representation ofRules returned from the service call request.
 * @returns {EScreeningDashboardApp.models.Rules[]} A list of existing Rule domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.RulesTransformer.transformJSONRules = function (jsonRules) {
    'use strict';
    /**
     * Represent the existing Rules.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.Rule[]}
     */
    var existingRules = [];

    if (!Object.isDefined(jsonRules)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonRules can not be null or undefined.");
    }

    if (!Array.isArray(jsonRules)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonRules must be an Array.");
    }

    jsonRules.forEach(function(jsonRule){
        existingRules.push(new EScreeningDashboardApp.models.Rule(jsonRule));
    });

    return existingRules;
};