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
 * Constructor method for the ResponseException Domain Model class.  The properties of this class can be initialized with
 * the jsonResponseException Object.
 * @class
 * @classdesc   This class is a domain model class; which means it has both behaviour and state
 *              information about the user.
 * @param {String}  jsonResponseException  Represents the JSON representation of a Response Exception object.
 * @constructor
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
BytePushers.models.ResponseException =  function (jsonResponseException) {
    /**
     * Represent the cause of the response exception from a Restful service call.
     *
     * @private
     * @field
     * @type {String}
     */
    var cause = (Object.isDefined(jsonResponseException) && Object.isDefined(jsonResponseException.cause)) ? jsonResponseException.cause : null,
        /**
         * Represent the stackTrace of the response exception from a Restful service call.
         *
         * @private
         * @field
         * @type {BytePushers.models.ResponseExceptionStackTrace}
         */
        stackTrace = [];

    if (Object.isDefined(jsonResponseException) && Object.isDefined(jsonResponseException.stackTrace) && Array.isArray(jsonResponseException.stackTrace))  {
        jsonResponseException.stackTrace.forEach(function (jsonStackTrace) {
            stackTrace.push(new BytePushers.models.ResponseExceptionStackTrace(jsonStackTrace));
        });
    }

    /**
     * Convenience method that gets the response exception cause of a service call.
     *
     * @public
     * @method
     * @returns {String} The response exception cause of the service call.
     */
    this.getCause = function () {
        return cause;
    };

    /**
     * Convenience method that gets the response exception stack trace array of a service call.
     *
     * @public
     * @method
     * @returns {BytePushers.models.ResponseExceptionStackTrace[]} The response exception stack trace array of a service call.
     */
    this.getStackTrace = function () {
        return stackTrace;
    };
};