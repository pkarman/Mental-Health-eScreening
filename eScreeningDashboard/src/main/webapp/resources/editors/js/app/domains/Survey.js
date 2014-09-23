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
 * Constructor method for the Survey class.  The properties of this class can be initialized with
 * the jsonUserObject.
 * @class
 * @classdesc   This class is a domain model class; which means it has both behaviour and state
 *              information about the user.
 * @param {String}  jsonSurveyObject  Represents the JSON representation of a Survey object.
 * @constructor
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
EScreeningDashboardApp.models.Survey = function (jsonSurveyObject) {
    var that = this,
        id = (Object.isDefined(jsonSurveyObject) && Object.isDefined(jsonSurveyObject.id))? jsonSurveyObject.id : -1,
        name = (Object.isDefined(jsonSurveyObject) && Object.isDefined(jsonSurveyObject.name))? jsonSurveyObject.name : null,
        vistaTitle = (Object.isDefined(jsonSurveyObject) && Object.isDefined(jsonSurveyObject.vistaTitle))? jsonSurveyObject.vistaTitle : null,
        description = (Object.isDefined(jsonSurveyObject) && Object.isDefined(jsonSurveyObject.description))? jsonSurveyObject.description : null,
        version = (Object.isDefined(jsonSurveyObject) && Object.isDefined(jsonSurveyObject.version))? jsonSurveyObject.version : null,
        displayOrder = (Object.isDefined(jsonSurveyObject) && Object.isDefined(jsonSurveyObject.displayOrder))? jsonSurveyObject.displayOrder : null,
        mha = (Object.isDefined(jsonSurveyObject) && Object.isDefined(jsonSurveyObject.mha) && Object.isBoolean((jsonSurveyObject.mha)))? jsonSurveyObject.mha : false,
        mhaTestName = (Object.isDefined(jsonSurveyObject) && Object.isDefined(jsonSurveyObject.mhaTestName))? jsonSurveyObject.mhaTestName : null,
        mhaResultGroupIen = (Object.isDefined(jsonSurveyObject) && Object.isDefined(jsonSurveyObject.mhaResultGroupIen))? jsonSurveyObject.mhaResultGroupIen : null,
        clinicalReminder = (Object.isDefined(jsonSurveyObject) && Object.isDefined(jsonSurveyObject.clinicalReminder) && Object.isBoolean((jsonSurveyObject.clinicalReminder)))? jsonSurveyObject.clinicalReminder : false,
        createdDate = (Object.isDefined(jsonSurveyObject) && Object.isDefined(jsonSurveyObject.createdDate))? (Object.isDate(jsonSurveyObject.createdDate)) ? jsonSurveyObject.createdDate : BytePushers.converters.DateConverter.convertToDate(jsonSurveyObject.createdDate, BytePushers.converters.DateConverter.YYYYMMDDThhmmsssTZD_DATE_FORMAT) : new Date(),
        surveySection = (Object.isDefined(jsonSurveyObject) && Object.isDefined(jsonSurveyObject.surveySection))? new EScreeningDashboardApp.models.SurveySection(jsonSurveyObject.surveySection): undefined,
        markedForDeletion = (Object.isDefined(jsonSurveyObject) && Object.isBoolean(jsonSurveyObject.markedForDeletion))? jsonSurveyObject.markedForDeletion: false,
        visible = (Object.isDefined(jsonSurveyObject) && Object.isBoolean(jsonSurveyObject.visible))? jsonSurveyObject.visible: false;

    var generateSurveySectionUIObject = function(){
        if (Object.isDefined(surveySection)){
            return this.surveySection.toUIObject();
        } else{
            return null;
        }
    };

    this.getId = function(){
        return id;
    };

    this.getName = function() {
        return name;
    };

    this.getVistaTitle = function () {
        return vistaTitle;
    };

    this.getDescription = function() {
        return description;
    };

    this.getVersion = function() {
        return version;
    };

    this.getDisplayOrder = function() {
        return displayOrder;
    };

    this.isMHA = function() {
        return mha;
    };

    this.getMhaTestName = function () {
        return mhaTestName;
    };

    this.getMhaResultGroupIen = function() {
        return mhaResultGroupIen;
    };

    this.isClinicalReminder = function () {
        return clinicalReminder;
    };

    this.getCreatedDate= function() {
        return createdDate;
    };

    this.getSurveySection = function () {
        return surveySection;
    };

    this.isMarkedForDeletion = function(){
        return markedForDeletion;
    };

    this.markedForDeletion = function(){
        markedForDeletion = true;
    };

    this.isVisible = function(){
        return visible;
    };

    this.toString = function () {
        return "Survey {id: " + id + ", name: " + name + ", vistaTitle: " + vistaTitle + ", description: " + description + ", version: " + version +
            ", displayOrder: " + displayOrder + ", mha: " + mha + ", mhaTestName: " + mhaTestName + ", mhaResultGroupIen: " + mhaResultGroupIen +
            ", clinicalReminder" + clinicalReminder + ", markedForDeletion: " + markedForDeletion +
            ", visible: " + visible + ", createdDate: " + createdDate + "}";
    };

    this.toJSON = function (serializeUIProperties) {
        serializeUIProperties = (Object.isDefined(serializeUIProperties) && Object.isBoolean(serializeUIProperties))? serializeUIProperties : false;
        var jsonId = (Object.isDefined(id) && id > 0)? id : null,
            jsonName = (Object.isDefined(name))? "\"" + name + "\"":  null,
            jsonVistaTitle = (Object.isDefined(vistaTitle))? "\"" + vistaTitle + "\"":  null,
            jsonDescription = (Object.isDefined(description))? "\"" + description + "\"": null,
            jsonVersion = (Object.isDefined(version))? version: null,
            jsonDisplayOrder = (Object.isDefined(displayOrder))? displayOrder: null,
            jsonIsMha = (Object.isDefined(mha))? mha: false,
            jsonMhaTestName = (Object.isDefined(mhaTestName))? "\"" + mhaTestName + "\"":  null,
            jsonMhaResultGroupIen = (Object.isDefined(mhaResultGroupIen ))? "\"" + mhaResultGroupIen + "\"":  null,
            jsonIsClinicalReminder = (Object.isDefined(clinicalReminder))? clinicalReminder: false,
            jsonVisible = (serializeUIProperties)? Object.isDefined(visible)? "\"visible\": " + visible + ",": false: "",
            jsonMarkForDeletion = (serializeUIProperties)? Object.isDefined(markedForDeletion)? "\"markedForDeletion\": " + markedForDeletion + ",": false: "",
            jsonCreatedDate = (Object.isDefined(createdDate))? "\"" + createdDate.toISOString().substring(0, createdDate.toISOString().length-1) + "\"": null,
            jsonSurveySection = (Object.isDefined(surveySection))? ",\"surveySection\":" + surveySection.toJSON(false, serializeUIProperties): "",
            json =  "{" +
                "\"id\": " + jsonId + "," +
                "\"name\": " + jsonName + "," +
                "\"description\": " +  jsonDescription + "," +
                "\"version\": " + jsonVersion + "," +
                "\"displayOrder\": " + jsonDisplayOrder + "," +
                "\"mha\": "+ jsonIsMha + "," +
                "\"mhaTestName\": "+ jsonMhaTestName + "," +
                "\"mhaResultGroupIen\": "+ jsonMhaResultGroupIen + "," +
                "\"clinicalReminder\": "+ jsonIsClinicalReminder + "," +
                jsonVisible +
                jsonMarkForDeletion +
                "\"createdDate\": " + jsonCreatedDate +
                jsonSurveySection +
            "}";

        return json;
    };
    
    this.toUIObject = function(){
    	var surveyUIObject = JSON.parse(this.toJSON(true));
        surveyUIObject.createdDate = this.getCreatedDate();
    	return surveyUIObject;
    };
};
EScreeningDashboardApp.models.Survey.toUIObjects = function(surveys) {
    var surveyUIObjects = [];

    if(Object.isDefined(surveys) && Object.isArray(surveys)) {
        surveys.forEach(function(survey) {
            surveyUIObjects.push(survey.toUIObject());
        });
    }

    return surveyUIObjects;
};