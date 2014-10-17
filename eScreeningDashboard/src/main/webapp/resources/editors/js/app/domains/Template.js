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
 * Constructor method for the Template class.  The properties of this class can be initialized with
 * the jsonTemplateObject.
 * @class
 * @classdesc   This class is a domain model class; which means it has both behavior and state
 *              information about Template Types.
 * @param {String}  jsonTemplateObject  Represents the JSON representation of a Template object.
 * @constructor
 * @author Robin Carnow
 */
EScreeningDashboardApp.models.Template = function () {

    this.toString = function () {
        return "Template {id: " + this.id + ", type: " + this.type + ", isGraphical: " + this.isGraphical + ", with " + blocks.length + " blocks.}";
    };

};