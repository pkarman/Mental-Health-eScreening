/**
 * Created by pouncilt on 10/23/14.
 */
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
 * Constructor method for the TemplateRightVariable class.  The properties of this class can be initialized with
 * the jsonConfig.
 * @class
 * @classdesc   This class is a domain model class; which means it has both behavior and state
 *              information about the user.
 * @param {String}  jsonConfig  Represents the JSON representation of an TemplateRightVariable object.
 * @constructor
 * @author Tonté Pouncil
 */
EScreeningDashboardApp.models.TemplateRightVariable = function (jsonConfig) {
    this.type;
    this.content;

    if(Object.isDefined(jsonConfig)) {
        this.type = (Object.isDefined(jsonConfig.type))? jsonConfig.type: null;

        if (this.type === "text") {
            this.content = jsonConfig.content;
        } else if (this.type === "var") {
            this.content = new EScreeningDashboardApp.models.TemplateVariableContent(jsonConfig.content);
        }
    }

    this.toString = function () {
        return "TemplateRightVariable [type: " + this.type + ", content: " + this.content + "]";
    };
};