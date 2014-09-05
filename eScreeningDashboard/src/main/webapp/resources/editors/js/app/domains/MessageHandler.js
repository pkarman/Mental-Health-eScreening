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
 * Constructor method for the MessageHandler class.  The properties of this class can be initialized with
 * the jsonUserObject.
 * @class
 * @classdesc   This class is a handler class; which means it has the behaviour to know how to handle the Message.
 * @param {String}  jsonMessagesArrayObject  Represents the JSON representation of a Answer object.
 * @constructor
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
EScreeningDashboardApp.models.MessageHandler = function (jsonMessagesArrayObject) {
    var messages = (Object.isArray(jsonMessagesArrayObject) && jsonMessagesArrayObject.length > 0)? jsonMessagesArrayObject : [];

    this.clearMessages = function(type) {
        type = (Object.isDefined(type))? type : "all";

        if(type === "all") {
            messages = [];
        }

        messages.forEach( function(message, index) {
            if(message.getType().toLowerCase() === "error"){
                messages.splice(index, 1);
            } else if (message.getType().toLowerCase() === "success") {
                messages.splice(index, 1);
            }
        });
    };

    this.addMessage = function (message){
        messages.push(message);
    };

    this.getMessages = function(){
        return messages;
    };
};