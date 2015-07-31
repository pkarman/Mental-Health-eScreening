/* Represents the Application API. If the variable is already defined, use it,*/
var EScreeningDashboardApp = EScreeningDashboardApp || {};

/**
 * Represents the application static variable. Use existing static variable, if one already exists.
 * Otherwise, create a new one.
 * 
 * @static
 * @type
 * {|EScreeningDashboardApp.models\*\EScreeningDashboardApp.models|Object|*|Object|*}
 */
EScreeningDashboardApp.models = EScreeningDashboardApp.models || EScreeningDashboardApp.namespace('gov.va.escreening.models');

/**
 * Constructor method for the Battery class.  The properties of this class can be initialized with the jsonUserObject.
 * 
 * @class
 * @classdesc This class is a domain model class which means it has both behavior and state information about the Battery.
 * @param {String} jsonBatteryObject Represents the JSON representation of a Battery object.
 * @constructor
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.Battery = function(jsonBatteryObject){
	this.id = (Object.isDefined(jsonBatteryObject) && Object.isDefined(jsonBatteryObject.id))? jsonBatteryObject.id : -1;
	this.name = (Object.isDefined(jsonBatteryObject) && Object.isDefined(jsonBatteryObject.name))? jsonBatteryObject.name : null;
	this.description = (Object.isDefined(jsonBatteryObject) && Object.isDefined(jsonBatteryObject.description))? jsonBatteryObject.description : null;
	this.disabled = Object.isDefined(jsonBatteryObject) ? jsonBatteryObject.disabled : false;
    this.createdDate = (Object.isDefined(jsonBatteryObject) && Object.isDefined(jsonBatteryObject.createdDate))? (Object.isDate(jsonBatteryObject.createdDate)) ? jsonBatteryObject.createdDate : BytePushers.converters.DateConverter.convertToDate(jsonBatteryObject.createdDate, BytePushers.converters.DateConverter.YYYYMMDDThhmmsssTZD_DATE_FORMAT) : null;
    this.surveys = (Object.isDefined(jsonBatteryObject) && Object.isDefined(jsonBatteryObject.surveys)&& Object.isArray(jsonBatteryObject.surveys))? EScreeningDashboardApp.models.SurveysTransformer.transformJSONPayload({"surveys":jsonBatteryObject.surveys}) : [];
    
    //please don't use these anymore. directly access the field you need
    this.getId = function(){
    	return this.id;
    };
    this.getName = function(){
    	return this.name;
    };
    this.getDescription = function(){
    	return this.description;
    };
    this.isDisabled = function(){
    	return this.disabled;
    };
    this.getCreatedDate = function(){
    	return this.createdDate;
    };
    this.getSurveys = function(){
    	return this.surveys;
    };
    this.retrieveSurveySections = function () {
        var surveySections = [],
            surveySectionIndex;

        this.surveys.forEach(function (survey) {
            surveySectionIndex = surveySections.indexOf(survey.getSurveySection());
            if(surveySectionIndex == -1){
                surveySections.push(survey.getSurveySection());
            }

            surveySectionIndex = surveySections.indexOf(survey.getSurveySection());
            if(surveySectionIndex != -1){
                surveySections[surveySectionIndex].getSurveys().push(survey);
            }
        });

        return surveySections;
    };
    this.retrieveSurveySection = function(surveySectionCriteria) {
        var selectedSurveySection = this.retrieveSurveySections().findIndex(function (surveySection) {
            if(surveySection.getId() === surveySectionCriteria.getId()){
                return true;
            }
        });

        return (selectedSurveySection == -1)? null: selectedSurveySection;
    };
    this.toString = function(){
    	return "Battery {id: " + id + ", name: " + name + ", description: " + description + ", disabled: " + disabled +
            ", createdDate: " + createdDate + ", surveys: [" + surveys + "]}";
    };
    
    this.toJSON = function(serializeCollections){
    	return angular.toJson(this.toUIObject()); 
    };
    
    //please don't use this
	this.toUIObject = function(serializeCollections){		
		var uiObj= {
			'id': (this.id != null && this.id > 0)? this.id : null,
			'name': this.name,
			'description': this.description,
			'disabled': this.disabled ? this.disabled: false, //maps null to false
			//TODO: PLEASE REMOVE THIS
			'createdDate': (this.createdDate != null)? this.createdDate.toISOString().substring(0, this.createdDate.toISOString().length-1) : null
		};
		 
		if( (!Object.isDefined(serializeCollections) || serializeCollections)){
			uiObj.surveys = this.surveys;
		}
		
		return uiObj;
	};
};

//targetSurveySections takes a list of unprotected (not domain with getters) object 
EScreeningDashboardApp.models.Battery.findVisibleSurveys = function (targetSurveySections) {
    var visibleSurveys = [];

    targetSurveySections.forEach(function(surveySection) {
    	surveySection.surveys.forEach(function (survey) {
            if(survey.visible){
                visibleSurveys.push(survey);
            }
        });
    });

    return visibleSurveys;
};
EScreeningDashboardApp.models.Battery.convertToSurveySectionDomainObjects = function (surveySectionUIObjects) {
    var surveySections = [];

    surveySectionUIObjects.forEach(function(surveySectionUIObject) {
        surveySections.push(EScreeningDashboardApp.models.Battery.convertToSurveySectionDomainObject(surveySectionUIObject));
    });

    return surveySections;
};
EScreeningDashboardApp.models.Battery.convertToSurveySectionDomainObject = function (surveySectionUIObject) {
    return new EScreeningDashboardApp.models.SurveySection(surveySectionUIObject);
};
