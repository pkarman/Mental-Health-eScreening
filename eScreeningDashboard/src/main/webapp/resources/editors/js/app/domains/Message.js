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
 *visible
 * @static
 * @type {*|EScreeningDashboardApp.models|*|EScreeningDashboardApp.models|Object|*|Object|*}
 */
EScreeningDashboardApp.models = EScreeningDashboardApp.models || EScreeningDashboardApp.namespace("gov.va.escreening.models");
/**
 * Constructor method for the Message class.  The properties of this class can be initialized with
 * the jsonUserObject.
 * @class
 * @classdesc   This class is a domain model message class; which means it has both behavior and state information about the Message.
 * @param {String} jsonMessageObject  Represents the JSON representation of a Message object.
 * @constructor
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
EScreeningDashboardApp.models.Message = function (jsonMessageObject) {
    var type = (Object.isDefined(jsonMessageObject) && Object.isDefined(jsonMessageObject.type))? jsonMessageObject.type : null,
        value = (Object.isDefined(jsonMessageObject) && Object.isDefined(jsonMessageObject.value))? jsonMessageObject.value: null;

    this.getType = function() {
        return type;
    };

    this.getValue = function () {
        return value;
    };
};