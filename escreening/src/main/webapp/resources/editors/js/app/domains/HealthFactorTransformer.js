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
 * @type {*|EScreeningDashboardApp.models.HealthFactorTransformer|*|EScreeningDashboardApp.models.HealthFactorTransformer|Object|*|Object|*}
 */
EScreeningDashboardApp.models.HealthFactorTransformer =  EScreeningDashboardApp.namespace("gov.va.escreening.models.HealthFactorTransformer");
/**
 * Static method for the HealthFactorTransformer class that is responsible for transforming the JSON Representation of the
 * HealthFactor domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonResponse  Represents the JSON response of a service call request.
 * @returns {EScreeningDashboardApp.models.HealthFactor[]} A list of existing HealthFactor domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.HealthFactorTransformer.transformJSONPayload = function (jsonResponse) {
    'use strict';
    /**
     * Represent the existing HealthFactor.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.HealthFactor}
     */
    var existingHealthFactor = null;

    if (!Object.isDefined(jsonResponse)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse can not be null or undefined.");
    }

    if (!Object.isDefined(jsonResponse.HealthFactor)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.HealthFactor can not be null or undefined.");
    }

    existingHealthFactor = EScreeningDashboardApp.models.HealthFactorTransformer.transformJSONHealthFactor(jsonResponse.HealthFactor);

    return existingHealthFactor;
};
/**
 * Static method for the HealthFactorTransformer class that is responsible for transforming the JSON Representation of the
 * HealthFactor domain model object.
 *
 * @static
 * @method
 * @param {String[]}  jsonHealthFactor  Represents the JSON representation of the HealthFactor returned from the service call request.
 * @returns {EScreeningDashboardApp.models.HealthFactor} An existing HealthFactor domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.HealthFactorTransformer.transformJSONHealthFactor = function (jsonHealthFactor) {
    'use strict';
    /**
     * Represent the existing HealthFactor.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.HealthFactor}
     */
    var existingHealthFactor = null;

    if (!Object.isDefined(jsonHealthFactor)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonHealthFactor can not be null or undefined.");
    }

    return existingHealthFactor;
};