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
 * @type {*|EScreeningDashboardApp.models.ValidationTransformer|*|EScreeningDashboardApp.models.ValidationTransformer|Object|*|Object|*}
 */
EScreeningDashboardApp.models.ValidationTransformer =  EScreeningDashboardApp.namespace("gov.va.escreening.models.ValidationTransformer");
/**
 * Static method for the ValidationTransformer class that is responsible for transforming the JSON Representation of the
 * Validation domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonResponse  Represents the JSON response of a service call request.
 * @returns {EScreeningDashboardApp.models.Validation} An existing Validation domain model that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.ValidationTransformer.transformJSONPayload = function (jsonResponse) {
    'use strict';
    /**
     * Represent the existing Validation.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.Validation}
     */
    var existingValidation = null;

    if (!Object.isDefined(jsonResponse)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse can not be null or undefined.");
    }

    if (!Object.isDefined(jsonResponse.validation)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.validation can not be null or undefined.");
    }

    existingValidation = EScreeningDashboardApp.models.ValidationTransformer.transformJSONValidation(jsonResponse.validation);

    return existingValidation;
};
/**
 * Static method for the ValidationTransformer class that is responsible for transforming the JSON Representation of the
 * Validation domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonValidation  Represents the JSON representation of validation returned from the service call request.
 * @returns {EScreeningDashboardApp.models.Validation} An existing Validation domain model that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.ValidationTransformer.transformJSONValidation = function (jsonValidation) {
    'use strict';
    /**
     * Represent the existing validation.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.Validation}
     */
    var existingValidation;

    if (!Object.isDefined(jsonValidation)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonValidation can not be null or undefined.");
    }

    existingValidation = new EScreeningDashboardApp.models.Validation(jsonValidation);

    return existingValidation;
};