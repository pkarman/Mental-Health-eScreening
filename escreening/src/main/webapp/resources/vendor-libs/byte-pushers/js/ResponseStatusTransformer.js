/**
 * Represents the application api.  If the variable is already defined use it,
 * otherwise create an empty object.
 *
 * @type {BytePushers|*|BytePushers|*|{}|{}}
 */
var BytePushers = BytePushers || {};
/**
 * Represents the application static variable. Use existing static variable, if one already exists,
 * otherwise create a new one.
 *
 * @static
 * @type {*|BytePushers.models|*|BytePushers.models|Object|*|Object|*}
 */
BytePushers.models = BytePushers.models || BytePushers.namespace("com.byte-pushers.models");
/**
 * Represents the application static variable. Use existing static variable, if one already exists,
 * otherwise create a new one.
 *
 * @static
 * @type {*|BytePushers.models.ResponseStatusTransformer|*|BytePushers.models.ResponseStatusTransformer|Object|*|Object|*}
 */
BytePushers.models.ResponseStatusTransformer =  BytePushers.namespace("com.byte-pushers.models.ResponseStatusTransformer");
/**
 * Static method for the ResponseStatusTransformer class that is responsible for transforming the JSON response status.
 *
 * @static
 * @method
 * @param {String}  jsonResponse  Represents the JSON response of a Restful service call.
 * @returns {BytePushers.models.ResponseStatus} The response status domain model of a service call request.
 *
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
BytePushers.models.ResponseStatusTransformer.transformJSONResponseStatus = function (jsonResponse) {
    'use strict';
    /**
     * Represent the response status from a Restful service call.
     *
     * @private
     * @field
     * @type {BytePushers.models.ResponseStatus}
     */
    var responseStatus;

    if (!Object.isDefined(jsonResponse)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse can not be null or undefined.");
    }

    if (!Object.isDefined(jsonResponse.status)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.status can not be null or undefined.");
    }

    responseStatus = new BytePushers.models.ResponseStatus(jsonResponse.status);

    return responseStatus;
};