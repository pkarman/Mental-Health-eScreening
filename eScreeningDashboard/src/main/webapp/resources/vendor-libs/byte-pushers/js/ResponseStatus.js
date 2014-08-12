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
 * Constructor method for the ResponseStatus Domain Model class.  The properties of this class can be initialized with
 * the jsonResponseStatus Object.
 * @class
 * @classdesc   This class is a domain model class; which means it has both behaviour and state
 *              information about the user.
 * @param {JSON Object}  jsonResponseStatus  Represents the JSON representation of a Response Status object.
 * @constructor
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
BytePushers.models.ResponseStatus =  function (jsonResponseStatus) {
    /**
     * Represent the response status message from a Restful service call.
     *
     * @private
     * @field
     * @type {String}
     */
    var message = (Object.isDefined(jsonResponseStatus) && Object.isDefined(jsonResponseStatus.message)) ? jsonResponseStatus.message : null,
        /**
         * Represent the response status from a Restful service call.
         *
         * @private
         * @field
         * @type {String}
         */
        status = (Object.isDefined(jsonResponseStatus) && Object.isDefined(jsonResponseStatus.status)) ? jsonResponseStatus.status : "failed",
        /**
         * Represent the response status exception from a Restful service call.
         *
         * @private
         * @field
         * @type {BytePushers.models.ResponseException}
         */
        exception = (Object.isDefined(jsonResponseStatus) && Object.isDefined(jsonResponseStatus.exception)) ? new BytePushers.models.ResponseException(jsonResponseStatus.exception) : null;

    /**
     * Convenience method that gets the response message of a service call.
     *
     * @public
     * @method
     * @returns {String} The response message of the service call.
     */
    this.getMessage = function () {
        return message;
    };

    /**
     * Convenience method that gets the response status of a service call.
     *
     * @public
     * @method
     * @returns {String} The response status of a service call.
     */
    this.getStatus = function () {
        return status;
    };

    /**
     * Convenience method that gets the response status exception of a service call.
     *
     * @public
     * @method
     * @returns {BytePushers.models.ResponseException} The response status exception of a service call.
     */
    this.getException = function () {
        return exception;
    };
};