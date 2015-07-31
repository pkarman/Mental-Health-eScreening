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
 * @type {*|EScreeningDashboardApp.models.HealthFactorsTransformer|*|EScreeningDashboardApp.models.HealthFactorsTransformer|Object|*|Object|*}
 */
EScreeningDashboardApp.models.HealthFactorsTransformer =  EScreeningDashboardApp.namespace("gov.va.escreening.models.HealthFactorsTransformer");
/**
 * Static method for the HealthFactorsTransformer class that is responsible for transforming the JSON Representation of the
 * HealthFactor domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonResponse  Represents the JSON response of a service call request.
 * @returns {EScreeningDashboardApp.models.HealthFactor[]} A list of existing HealthFactor domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.HealthFactorsTransformer.transformJSONPayload = function (jsonResponse) {
    'use strict';
    /**
     * Represent the existing HealthFactors.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.Rule[]}
     */
    var existingHealthFactors = [];

    if (!Object.isDefined(jsonResponse)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse can not be null or undefined.");
    }

    if (!Object.isDefined(jsonResponse.HealthFactors)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.HealthFactors can not be null or undefined.");
    }

    if (!Array.isArray(jsonResponse.HealthFactors)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.HealthFactors must be an Array.");
    }

    existingHealthFactors = EScreeningDashboardApp.models.HealthFactorsTransformer.transformJSONHealthFactors(jsonResponse.HealthFactors);

    return existingHealthFactors;
};
/**
 * Static method for the HealthFactorsTransformer class that is responsible for transforming the JSON Representation of the
 * Rule domain model object.
 *
 * @static
 * @method
 * @param {String[]}  jsonHealthFactors  Represents the JSON representation ofHealthFactors returned from the service call request.
 * @returns {EScreeningDashboardApp.models.HealthFactors[]} A list of existing Rule domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.HealthFactorsTransformer.transformJSONHealthFactors = function (jsonHealthFactors) {
    'use strict';
    /**
     * Represent the existing HealthFactors.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.HealthFactor[]}
     */
    var existingHealthFactors = [];

    if (!Object.isDefined(jsonHealthFactors)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonHealthFactors can not be null or undefined.");
    }

    if (!Array.isArray(jsonHealthFactors)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonHealthFactors must be an Array.");
    }

    jsonHealthFactors.forEach(function(jsonRule){
        existingHealthFactors.push(new EScreeningDashboardApp.models.Rule(jsonHealthFactor));
    });

    return existingHealthFactors;
};