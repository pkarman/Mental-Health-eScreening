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
 * Constructor method for the ResponseExceptionStackTrace Domain Model class.  The properties of this class can be initialized with
 * the jsonResponseExceptionStackTrace Object.
 * @class
 * @classdesc   This class is a domain model class; which means it has both behaviour and state
 *              information about the user.
 * @param {String}  jsonResponseExceptionStackTrace  Represents the JSON representation of a Response Exception object.
 * @constructor
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
BytePushers.models.ResponseExceptionStackTrace =  function (jsonResponseExceptionStackTrace) {
    /**
     * Represent the exception stack trace method name.
     *
     * @private
     * @field
     * @type {String}
     */
    var methodName = (Object.isDefined(jsonResponseExceptionStackTrace) && Object.isDefined(jsonResponseExceptionStackTrace.methodName)) ? jsonResponseExceptionStackTrace.methodName : null,
        /**
         * Represent the exception stack trace file name.
         *
         * @private
         * @field
         * @type {String}
         */
        fileName = (Object.isDefined(jsonResponseExceptionStackTrace) && Object.isDefined(jsonResponseExceptionStackTrace.fileName)) ? jsonResponseExceptionStackTrace.fileName : null,
        /**
         * Represents the exception stack trace line number.
         *
         * @private
         * @field
         * @type {Number}
         */
        lineNumber = (Object.isDefined(jsonResponseExceptionStackTrace) && Object.isDefined(jsonResponseExceptionStackTrace.lineNumber)) ? jsonResponseExceptionStackTrace.lineNumber: null,
        /**
         * Represents the exception stack trace class name.
         *
         * @private
         * @field
         * @type {String}
         */
        className = (Object.isDefined(jsonResponseExceptionStackTrace) && Object.isDefined(jsonResponseExceptionStackTrace.className)) ? jsonResponseExceptionStackTrace.className : null,
        /**
         * Represents the exception stack trace class name is a native method or not.
         *
         * @private
         * @field
         * @type {Boolean}
         */
        nativeMethod = (Object.isDefined(jsonResponseExceptionStackTrace) && Object.isDefined(jsonResponseExceptionStackTrace.nativeMethod)) ? jsonResponseExceptionStackTrace.nativeMethod : false;


    /**
     * Convenience method that gets the response exception trace method name.
     *
     * @public
     * @method
     * @returns {String} The method name of the response exception stack trace of the service call.
     */
    this.getMethodName = function () {
        return methodName;
    };

    /**
     * Convenience method that gets the response exception stack trace file name.
     *
     * @public
     * @method
     * @returns {String} The file name of the response exception stack trace of the service call.
     */
    this.getFileName = function () {
        return fileName;
    };

    /**
     * Convenience method that gets the response exception stack trace line number.
     *
     * @public
     * @method
     * @returns {Number} The line number of the response exception stack trace of the service call.
     */
    this.getLineNumber = function () {
        return lineNumber;
    };

    /**
     * Convenience method that gets the response exception stack trace class name.
     *
     * @private
     * @method
     * @returns {String} The class name of the response exception stack trace of the service call.
     */
    this.getClassName = function () {
        return className;
    };

    /**
     * Convenience method that determines if the response exception stack trace class name is a native method.
     *
     * @private
     * @field
     * @returns {Boolean} True if the class name is a native method, otherwise return false.
     */
    this.isNativeMethod = function () {
        return nativeMethod;
    };

    /**
     * Convenience method that overrides the toString method.
     *
     * @returns {string} The string representation of the Exception Stack Trace.
     */
    this.toString = function () {
        return "[\n\tmethodName: " + methodName + ",\n\tfileName: " + fileName + ",\n\tlineNumber: " + lineNumber + ",\n\tclassName: " + className + ",\n\tnativeMethod: " + nativeMethod + "\n]";
    };
};
