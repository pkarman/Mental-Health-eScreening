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
        id = (Object.isDefined(jsonTemplateObject) && Object.isDefined(jsonTemplateObject.templateTypeId))? jsonTemplateObject.templateTypeId : -1,
        name = (Object.isDefined(jsonTemplateObject) && Object.isDefined(jsonTemplateObject.templateTypeName))? jsonTemplateObject.templateTypeName : null,
        description = (Object.isDefined(jsonTemplateObject) && Object.isDefined(jsonTemplateObject.templateTypeDescription))? jsonTemplateObject.templateTypeDescription : null,
        exists = (Object.isDefined(jsonTemplateObject) && Object.isBoolean(jsonTemplateObject.givenTemplateExists))? jsonTemplateObject.givenTemplateExists: false;

    this.getId = function(){
        return id;
    };

    this.getName = function() {
        return name;
    };

    this.getDescription = function() {
        return description;
    };

    this.getExists = function() {
        return exists;
    };

    this.toString = function () {
        return "TemplateType {id: " + id + ", name: " + name + ", description: " + description + ", exists: " + exists + "}";
    };

    this.toJSON = function () {
        return JSON.stringify({ 
            templateTypeId: (Object.isDefined(id) && id > 0)? id : null,
            templateTypeName: name,
            templateTypeDescription: description,
            givenTemplateExists: exists
        });
    };
    
    this.toUIObject = function(){
        return {
            "id" : id,
            "name" : name,
            "description" : description,
            "exists" : exists
        }
    };
};
EScreeningDashboardApp.models.TemplateType.toUIObjects = function(templateTypes) {
    var templateTypesUIObjects = [];

    if(Object.isDefined(templateTypes) && Object.isArray(templateTypes)) {
        templateTypes.forEach(function(templateType) {
            templateTypesUIObjects.push(templateType.toUIObject());
        });
    }

    return templateTypesUIObjects;
};