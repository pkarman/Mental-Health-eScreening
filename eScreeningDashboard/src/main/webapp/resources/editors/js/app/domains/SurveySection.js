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
 *visible
 * @static
 * @type {*|EScreeningDashboardApp.models|*|EScreeningDashboardApp.models|Object|*|Object|*}
 */
EScreeningDashboardApp.models = EScreeningDashboardApp.models || EScreeningDashboardApp.namespace("gov.va.escreening.models");
/**
 * Constructor method for the SurveySection class.  The properties of this class can be initialized with
 * the jsonUserObject.
 * @class
 * @classdesc   This class is a domain model class; which means it has both behaviour and state
 *              information about the user.
 * @param {String}  jsonSurveySectionObject  Represents the JSON representation of a SurveySection object.
 * @constructor
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
EScreeningDashboardApp.models.SurveySection = function (jsonSurveySectionObject) {
    var that = this,
        id = (Object.isDefined(jsonSurveySectionObject) && Object.isDefined(jsonSurveySectionObject.id))? jsonSurveySectionObject.id : -1,
        name = (Object.isDefined(jsonSurveySectionObject) && Object.isDefined(jsonSurveySectionObject.name))? jsonSurveySectionObject.name : null,
        description = (Object.isDefined(jsonSurveySectionObject) && Object.isDefined(jsonSurveySectionObject.description))? jsonSurveySectionObject.description : null,
        displayOrder = (Object.isDefined(jsonSurveySectionObject) && Object.isDefined(jsonSurveySectionObject.displayOrder))? jsonSurveySectionObject.displayOrder : null,
        createdDate = (Object.isDefined(jsonSurveySectionObject) && Object.isDefined(jsonSurveySectionObject.createdDate))? (Object.isDate(jsonSurveySectionObject.createdDate)) ? jsonSurveySectionObject.createdDate : BytePushers.converters.DateConverter.convertToDate(jsonSurveySectionObject.createdDate, BytePushers.converters.DateConverter.YYYYMMDDThhmmsssTZD_DATE_FORMAT) : null,
        surveys = (Object.isDefined(jsonSurveySectionObject) && Object.isDefined(jsonSurveySectionObject.surveys) && Object.isArray(jsonSurveySectionObject.surveys))? EScreeningDashboardApp.models.SurveysTransformer.transformJSONPayload({"surveys": jsonSurveySectionObject.surveys}) : [],
        markedForDeletion = (Object.isDefined(jsonSurveySectionObject) && Object.isBoolean(jsonSurveySectionObject.markedForDeletion))? jsonSurveySectionObject.markedForDeletion: false,
        visible = (Object.isDefined(jsonSurveySectionObject) && Object.isBoolean(jsonSurveySectionObject.visible))? jsonSurveySectionObject.visible: false;
      

    this.surveyArrayHasIncreased = (Object.isDefined(jsonSurveySectionObject) && Object.isDefined(jsonSurveySectionObject.surveyArrayHasIncreased && Object.isBoolean(jsonSurveySectionObject.surveyArrayHasIncreased)))? jsonSurveySectionObject.surveyArrayHasIncreased :false;
    
    // Adding for Delete-handling purposes - JBH (7/30/2014).
    //this.markedForDeletion = false;
    //this.visibility = true;
    
    var generateJsonStringForSurveys = function () {
        var surveyJson = "[";

        surveys.forEach(function (survey) {
            surveyJson += survey.toJSON() + ",";
        });

        if (surveys.length > 0) surveyJson = surveyJson.slice(0, surveyJson.length-1);
        surveyJson += "]";

        return surveyJson;
    };
    
    var generateSurveyUIObjects = function(){
    	var survs = [];
    	if (surveys && surveys.length && surveys.length > 0){
    		surveys.forEach(function(survey){
    			survs.push(survey.toUIObject());
    		});
    	}else{
    		survs = null;
    	}
    	return survs;
    };

    this.getSelf = function(){
    	return this;
    };

    this.getId = function(){
        return id;
    };

    this.getName = function() {
        return name;
    };

    this.getDescription = function() {
        return description;
    };

    this.getDisplayOrder = function() {
        return displayOrder;
    };

    this.getCreatedDate = function() {
        return createdDate;
    };

    this.getSurveys = function() {
        return surveys;
    };
    
    this.isMarkedForDeletion = function(){
    	return markedForDeletion;
    };

    this.isVisible = function(){
        return visible;
    };

    this.toString = function() {
        return "Survey Section{id: " + id + ", name: " + name + ", description: " + description +
            ", displayOrder: " + displayOrder + ", visible: " + visible + ", surveyArrayHasIncreased: " + this.surveyArrayHasIncreased +
            ", markedForDeletion: " + markedForDeletion + ", createdDate: " + createdDate +
            ", surveys[" + surveys + "]}";
    };

    this.toJSON = function (serializeCollections, serializeUIProperties) {
        serializeCollections = (Object.isDefined(serializeCollections) && Object.isBoolean(serializeCollections))? serializeCollections : true;
        serializeUIProperties = (Object.isDefined(serializeUIProperties) && Object.isBoolean(serializeUIProperties))? serializeUIProperties : false;
        var jsonId = (id != null && id > 0)? id : null,
            jsonName = (name != null)? "\"" + name + "\"": null,
            jsonDescription = (description != null)? "\"" + description + "\"" : null,
            jsonDisplayOrder = (displayOrder != null)? displayOrder: null,
            jsonVisible = (serializeUIProperties)? Object.isDefined(visible)? "\"visible\": " + visible + ",": false: "",
            jsonMarkedForDeletion = (serializeUIProperties)? Object.isDefined(markedForDeletion)? "\"markedForDeletion\": " + markedForDeletion + ",": false: "",
            jsonCreatedDate =  (createdDate != null)? "\"" + createdDate.toISOString().substring(0, createdDate.toISOString().length-1) + "\"": null,
            jsonSurveys = (serializeCollections)? ",\"surveys\": " + generateJsonStringForSurveys(): "",
            json = "{" +
                "\"id\": " + jsonId + "," +
                "\"name\": " + jsonName + "," +
                "\"description\": " + jsonDescription + "," +
                "\"displayOrder\": " + jsonDisplayOrder + "," +
                jsonVisible +
                jsonMarkedForDeletion +
                "\"createdDate\": " + jsonCreatedDate +
                jsonSurveys +
            "}";

        return json;
    };
    
    this.toUIObject = function(){
    	var surveySectionUIObject = JSON.parse(this.toJSON(null, true));
        surveySectionUIObject.createdDate = this.getCreatedDate();
    	return surveySectionUIObject;
    };
};
EScreeningDashboardApp.models.SurveySection.toUIObjects = function(surveySections) {
    var surveySectionUIObjects = [];

    if(Object.isArray(surveySections)) {
        surveySections.forEach(function(surveySection){
            surveySectionUIObjects.push(surveySection.toUIObject());
        });
    }

    return surveySectionUIObjects;
};