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
 * Constructor method for the Message class.  The properties of this class can be initialized with
 * the jsonUserObject.
 * @class
 * @classdesc   This class is a domain model message class; which means it has both behavior and state information about the Message.
 * @param {type: String, value: String} jsonMessageObject  Represents the JSON representation of a Message object.
 * @constructor
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
BytePushers.models.Message = function (jsonMessageObject) {
    var type = (Object.isDefined(jsonMessageObject) && Object.isDefined(jsonMessageObject.type))? jsonMessageObject.type : null,
        value = (Object.isDefined(jsonMessageObject) && Object.isDefined(jsonMessageObject.value))? jsonMessageObject.value: null;

    this.getType = function() {
        return type;
    };

    this.getValue = function () {
        return value;
    };

    this.setValue = function(someValue){
        value = someValue;
    };
};
BytePushers.models.Message.SUCCESSFUL_SAVE = "successful-save";
BytePushers.models.Message.SUCCESSFUL_DELETE = "successful-delete";
BytePushers.models.Message.SUCCESS_SAVE_MSG = "Your data has been saved successfully.";
BytePushers.models.Message.SUCCESS_DELETE_MSG = "Your data has been deleted successfully.";
BytePushers.models.Message.ERROR = "error";
BytePushers.models.Message.ERROR_MSG = "Sorry, we are unable save your data at this time.  Please contact your System Administrator if this continues.";