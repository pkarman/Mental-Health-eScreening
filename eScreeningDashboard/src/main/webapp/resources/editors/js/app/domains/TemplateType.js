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
 * Constructor method for the TemplateType class.  The properties of this class can be initialized with
 * the jsonTemplateObject.
 * @class
 * @classdesc   This class is a domain model class; which means it has both behavior and state
 *              information about Template Types.
 * @param {String}  jsonTemplateObject  Represents the JSON representation of a TemplateType object.
 * @constructor
 * @author Robin Carnow
 */
EScreeningDashboardApp.models.TemplateType = function (jsonTemplateObject) {
    var that = this,
        id = (Object.isDefined(jsonTemplateObject) && Object.isDefined(jsonTemplateObject.id))? jsonTemplateObject.id : -1,
        name = (Object.isDefined(jsonTemplateObject) && Object.isDefined(jsonTemplateObject.name))? jsonTemplateObject.name : null,
        description = (Object.isDefined(jsonTemplateObject) && Object.isDefined(jsonTemplateObject.description))? jsonTemplateObject.description : null,
        templateId = (Object.isDefined(jsonTemplateObject) && Object.isDefined(jsonTemplateObject.templateId)) ? jsonTemplateObject.templateId : null,
        exists =  Object.isDefined(templateId);

    this.exists = function(){
        return Object.isDefined(this.templateId);
    }
    
    this.toString = function () {
        return "TemplateType {id: " + this.id + ", name: " + this.name + ", description: " + this.description + ", exists: " + this.exists + "}";
    };

};