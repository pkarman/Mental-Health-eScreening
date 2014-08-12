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
	var that = this,
	id = (Object.isDefined(jsonBatteryObject) && Object.isDefined(jsonBatteryObject.id))? jsonBatteryObject.id : -1,
	name = (Object.isDefined(jsonBatteryObject) && Object.isDefined(jsonBatteryObject.name))? jsonBatteryObject.name : null,
	description = (Object.isDefined(jsonBatteryObject) && Object.isDefined(jsonBatteryObject.description))? jsonBatteryObject.description : null,
	disabled = (Object.isDefined(jsonBatteryObject) && Object.isDefined(jsonBatteryObject.disabled) && Object.isBoolean(jsonBatteryObject.disabled)) ? jsonBatteryObject.disabled : false,
    createdDate = (Object.isDefined(jsonBatteryObject) && Object.isDefined(jsonBatteryObject.createdDate))? (Object.isDate(jsonBatteryObject.createdDate)) ? jsonBatteryObject.createdDate : BytePushers.converters.DateConverter.convertToDate(jsonBatteryObject.createdDate, BytePushers.converters.DateConverter.YYYYMMDDThhmmsssTZD_DATE_FORMAT) : null,
    surveys = (Object.isDefined(jsonBatteryObject) && Object.isDefined(jsonBatteryObject.surveys)&& Object.isArray(jsonBatteryObject.surveys))? EScreeningDashboardApp.models.SurveysTransformer.transformJSONPayload({"surveys":jsonBatteryObject.surveys}) : [];

    var generateJsonStringForSurveys = function () {
        var surveyJson = "[";

        surveys.forEach(function (survey) {
            surveyJson += survey.toJSON() + ",";
        });

        if (surveys.length > 0){
            surveyJson = surveyJson.slice(0, surveyJson.length-1);
        }

        surveyJson += "]";

        return surveyJson;
    };
    
    this.getId = function(){
    	return id;
    };
    this.getName = function(){
    	return name;
    };
    this.getDescription = function(){
    	return description;
    };
    this.isDisabled = function(){
    	return disabled;
    };
    this.disabled = function(isDisabled){
        disabled = isDisabled;
    };
    this.getCreatedDate = function(){
    	return createdDate;
    };
    this.getSurveys = function(){
    	return surveys;
    };
    this.retrieveSurveySections = function () {
        var surveySections = [],
            surveySectionIndex;

        surveys.forEach(function (survey) {
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
    	return "Battery {id: " + id + ", name: " + name + ", description: " + description + ", isDisabled: " + disabled +
            ", createdDate: " + createdDate + ", surveys: [" + surveys + "]}";
    };
    
    this.toJSON = function(serializeCollections){
        serializeCollections = (Object.isDefined(serializeCollections) && Object.isBoolean(serializeCollections))? serializeCollections : true;
        var jsonId = (id != null && id > 0)? id : null,
            jsonName = (name != null)? "\"" + name + "\"":  null,
            jsonDescription = (description != null)? "\"" + description + "\"": null,
            jsonDisabled = (disabled != null)? disabled: false,
            jsonCreatedDate = (createdDate != null)? "\"" + createdDate.toISOString().substring(0, createdDate.toISOString().length-1) + "\"": null,
            jsonSurveys = (serializeCollections)? ",\"surveys\": " + generateJsonStringForSurveys(): "",
            json =  "{" +
                "\"id\": " + jsonId + "," +
                "\"name\": " + jsonName + "," +
                "\"description\": " +  jsonDescription + "," +
                "\"disabled\": " + jsonDisabled + "," +
                "\"createdDate\": " + jsonCreatedDate +
                jsonSurveys +
            "}";

        return json;
    };
    
    this.toUIObject = function(){
    	var batteryUIObjects = JSON.parse(this.toJSON());
        batteryUIObjects.createdDate = this.getCreatedDate();
    	return batteryUIObjects;
    };

    this.getSurveysAsSurveyUIObjects = function(){
        var surveyUIObjects = [];

        surveys.forEach(function(survey){
            surveyUIObjects.push(survey.toUIObject());
        });

        return surveyUIObjects;
    };
};
EScreeningDashboardApp.models.Battery.findVisibleSurveys = function (targetSurveySections) {
    var visibleSurveys = [];

    targetSurveySections.forEach(function(surveySection) {
        surveySection.getSurveys().forEach(function (survey) {
            if(survey.isVisible()){
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