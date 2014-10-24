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
 * Constructor method for the TemplateLeftVariable class.  The properties of this class can be initialized with
 * the jsonConfig.
 * @class
 * @classdesc   This class is a domain model class; which means it has both behavior and state
 *              information about the user.
 * @param {String}  jsonConfig  Represents the JSON representation of an TemplateLeftVariable object.
 * @constructor
 * @author Tonté Pouncil
 */
EScreeningDashboardApp.models.TemplateLeftVariable = function (jsonConfig) {
    this.id;
    this.templateContent;

    if(Object.isDefined(jsonConfig)) {
        this.id = (Object.isDefined(jsonConfig.id))? jsonConfig.id: null;
        this.templateContent = (Object.isDefined(jsonConfig.templateContent))? jsonConfig.templateContent: null;
    }

    this.toString = function () {
        return "TemplateLeftVariable [id: " + this.id + ", content: " + this.templateContent + "]";
    };
};
EScreeningDashboardApp.models.TemplateLeftVariable.create = function (){
    return new EScreeningDashboardApp.models.TemplateLeftVariable();
};