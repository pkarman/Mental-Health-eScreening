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
EScreeningDashboardApp.models.Template = function (templateType) {
    if(Object.isDefined(templateType)){
        this.type = templateType;
        this.name = templateType.name;
    }
    
    /**
     * Because of some of the awkward nature of our endpoints and pre-restangular, UI code we have to do this for now
     * @param related object is the object which is associated with the template being saved. The related object 
     * can be either a battery or a survey.  The related object parameter should look like this:
     * {
     *   id : [surveyId | batteryId],
     *   name : the display name of the related object,
     *   type : ["module" | "battery"]
     * }
     * 
     */
    this.saveFor = function(relatedObject){
        
        if(Object.isDefined(this.id) 
                && this.id != -1
                && this.id.length > 0){
            //this is an update
            return $scope.template.put();
        }
        else if(relatedObject.type == "module"){ //this is a new template
            console.log("Saving new template of type " + this.type.id  + " for survey with ID: " + relatedObject.id)
            //TODO: if/when we move to a more OO restful end point structure we should remove this in favor of:
            // var templateTypes = module.getList('templateTypes');
            // ... at some point: new EScreeningDashboardApp.models.Template(selectedTemplateType);
            // this.type.post(this);  
            // The goal would be to have "this" sent to either:
            //  -  POST: /services/surveys/{surveyId}/templateTypes/{templateTypeId}
            //  or this:
            //  -  POST: /services/batteries/{batteryId}/templateTypes/{templateTypeId}
            return this.type.customPOST(this, "surveys/" + relatedObject.id);
        }
        else if(relatedObject.type == "battery"){ //this is a new template
            console.log("Saving new template of type " + this.type.id + " for battery with ID: " + relatedObject.id);
            return this.type.customPOST(this, "batteries/" + relatedObject.id);
        }
        
        console.error("Unsupported operation. Template is new and related object is unsupported: " + JSON.stringify(relatedObject));
        var deferred = $q.defer();
        deferred.reject("Unsupported operation. Call support.");
        return deferred.promise;
    }
    
    this.toString = function () {
        return "Template {id: " + this.id + ", type: " + this.type + ", isGraphical: " + this.isGraphical + ", with " + blocks.length + " blocks.}";
    };

};