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
 * Constructor method for the TemplateOperator class.  The properties of this class can be initialized with
 * the jsonConfig.
 * @class
 * @classdesc   This class is a domain model class; which means it has both behavior and state
 *              information about the user.
 * @param {String}  jsonConfig  Represents the JSON representation of an TemplateOperator object.
 * @constructor
 * @author Tonté Pouncil
 */
EScreeningDashboardApp.models.TemplateOperator = function (jsonConfig) {
    this.name;
    this.value;
    this.category;

    if(Object.isDefined(jsonConfig)) {
        this.name = (Object.isDefined(jsonConfig.name))? jsonConfig.name: null;
        this.value = (Object.isDefined(jsonConfig.value))? jsonConfig.value: null;
        this.category = (Object.isDefined(jsonConfig.category))? jsonConfig.category: null;
    }

    this.toString = function () {
        return "TemplateOperator [name: " + this.name + ", value: " + this.value + ", category: " + this.category + "]";
    };
};