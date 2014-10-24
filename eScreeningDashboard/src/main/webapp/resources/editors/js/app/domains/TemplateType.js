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
 * the jsonConfig.
 * @class
 * @classdesc   This class is a domain model class; which means it has both behavior and state
 *              information about Template Types.
 * @param {String}  jsonConfig  Represents the JSON representation of a TemplateType object.
 * @constructor
 * @author Robin Carnow
 */
EScreeningDashboardApp.models.TemplateType = function (jsonConfig) {
    this.id;
    this.name;
    this.description;
    this.templateId;

    if(Object.isDefined(jsonConfig)){
        this.id = (Object.isDefined(jsonConfig) && Object.isDefined(jsonConfig.id))? jsonConfig.id : -1;
        this.name = (Object.isDefined(jsonConfig) && Object.isDefined(jsonConfig.name))? jsonConfig.name : null;
        this.description = (Object.isDefined(jsonConfig) && Object.isDefined(jsonConfig.description))? jsonConfig.description : null;
        this.templateId = (Object.isDefined(jsonConfig) && Object.isDefined(jsonConfig.templateId)) ? jsonConfig.templateId : null;
    }
    
    this.exists = function(){
        return Object.isDefined(this.templateId);
    };
    
    this.toString = function () {
        return "TemplateType {id: " + this.id + ", name: " + this.name + ", description: " + this.description + ", exists: " + this.exists + "}";
    };

};
EScreeningDashboardApp.models.TemplateType.create = function () {
    return new EScreeningDashboardApp.models.TemplateType();
};