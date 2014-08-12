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
 * @type {*|EScreeningDashboardApp.models.ValidationsTransformer|*|EScreeningDashboardApp.models.ValidationsTransformer|Object|*|Object|*}
 */
EScreeningDashboardApp.models.ValidationsTransformer =  EScreeningDashboardApp.namespace("gov.va.escreening.models.ValidationsTransformer");
/**
 * Static method for the ValidationsTransformer class that is responsible for transforming the JSON Representation of the
 * Validations domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonResponse  Represents the JSON response of a service call request.
 * @returns {EScreeningDashboardApp.models.Validations[]} A list of existing Validation domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.ValidationsTransformer.transformJSONPayload = function (jsonResponse) {
    'use strict';
    /**
     * Represent the existing Validations.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.Validations[]}
     */
    var existingValidations = [];

    if (!Object.isDefined(jsonResponse)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse can not be null or undefined.");
    }

    if (!Object.isDefined(jsonResponse.validations) && !Object.isDefined(jsonResponse.validation)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.Validations can not be null or undefined.");
    }

    if (!Array.isArray(jsonResponse.validations) && !Object.isDefined(jsonResponse.validation)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.Validations must be an Array or Object.");
    }

    if (!jsonResponse.validations)
    	existingValidations = EScreeningDashboardApp.models.ValidationsTransformer.transformJSONValidation(jsonResponse.validation);
    else
    	existingValidations = EScreeningDashboardApp.models.ValidationsTransformer.transformJSONValidations(jsonResponse.validations);

    return existingValidations;
};
/**
 * Static method for the ValidationsTransformer class that is responsible for transforming the JSON Representation of the
 * Validation domain model object.
 *
 * @static
 * @method
 * @param {String[]}  jsonValidations  Represents the JSON representation of Validations returned from the service call request.
 * @returns {EScreeningDashboardApp.models.Validations[]} A list of existing Validation domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.ValidationsTransformer.transformJSONValidation = function (jsonValidation) {
    'use strict';
    /**
     * Represent the existing Validation.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.Validation}
     */
    var existingValidations = [];

    if (!Object.isDefined(jsonValidation)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonValidations can not be null or undefined.");
    }

    existingValidations.push(new EScreeningDashboardApp.models.Validation(jsonValidation));

    return existingValidations;
};

/**
 * Static method for the ValidationsTransformer class that is responsible for transforming the JSON Representation of the
 * Validation domain model object.
 *
 * @static
 * @method
 * @param {String[]}  jsonValidations  Represents the JSON representation of Validations returned from the service call request.
 * @returns {EScreeningDashboardApp.models.Validations[]} A list of existing Validation domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.ValidationsTransformer.transformJSONValidations = function (jsonValidations) {
    'use strict';
    /**
     * Represent the existing Validations.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.Validations[]}
     */
    var existingValidations = [];

    if (!Object.isDefined(jsonValidations)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonValidations can not be null or undefined.");
    }

    if (!Array.isArray(jsonValidations)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonValidations must be an Array.");
    }

    jsonValidations.forEach(function(jsonValidation){
        existingValidations.push(new EScreeningDashboardApp.models.Validation(jsonValidation));
    });

    return existingValidations;
};