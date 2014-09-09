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
 * Constructor method for the SurveyPage class.  The properties of this class can be initialized with
 * the jsonUserObject.
 * @class
 * @classdesc   This class is a domain model class; which means it has both behaviour and state
 *              information about the user.
 * @param {String}  jsonSurveyPageObject  Represents the JSON representation of a SurveyPage object.
 * @constructor
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
EScreeningDashboardApp.models.SurveyPage = function (jsonSurveyPageObject) {
    var id = (Object.isDefined(jsonSurveyPageObject) && Object.isDefined(jsonSurveyPageObject.id))? jsonSurveyPageObject.id : -1,
        title = (Object.isDefined(jsonSurveyPageObject) && Object.isDefined(jsonSurveyPageObject.title))? jsonSurveyPageObject.title : null,
        description = (Object.isDefined(jsonSurveyPageObject) && Object.isDefined(jsonSurveyPageObject.description))? jsonSurveyPageObject.description : null,
        pageNumber = (Object.isDefined(jsonSurveyPageObject) && Object.isDefined(jsonSurveyPageObject.pageNumber))? jsonSurveyPageObject.pageNumber : null,
        createdDate = (Object.isDefined(jsonSurveyPageObject) && Object.isDefined(jsonSurveyPageObject.createdDate))? (Object.isDate(jsonSurveyPageObject.createdDate)) ? jsonSurveyPageObject.createdDate : BytePushers.converters.DateConverter.convertToDate(jsonSurveyPageObject.createdDate, BytePushers.converters.DateConverter.YYYYMMDDThhmmsssTZD_DATE_FORMAT) : null;

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

    this.getTitle = function() {
        return title;
    };

    this.getDescription = function() {
        return description;
    };

    this.getPageNumber = function() {
        return pageNumber;
    };

    this.getCreatedDate= function() {
        return createdDate;
    };

    this.toString = function () {
        return "Survey {id: " + id + ", title: " + title + ", description: " + description + ", pageNumber: " + pageNumber +
            ", createdDate: " + createdDate + "}";
    };

    this.toJSON = function (serializeUIProperties) {
        serializeUIProperties = (Object.isDefined(serializeUIProperties) && Object.isBoolean(serializeUIProperties))? serializeUIProperties : false;
        var jsonId = (Object.isDefined(id) && id > 0)? id : null,
            jsonTitle = (Object.isDefined(title))? "\"" + title + "\"":  null,
            jsonDescription = (Object.isDefined(description))? "\"" + description + "\"": null,
            jsonVersion = (Object.isDefined(pageNumber))? pageNumber: null,
            jsonCreatedDate = (Object.isDefined(createdDate))? "\"" + createdDate.toISOString().substring(0, createdDate.toISOString().length-1) + "\"": null,
            json =  "{" +
                "\"id\": " + jsonId + "," +
                "\"name\": " + jsonTitle + "," +
                "\"description\": " +  jsonDescription + "," +
                "\"version\": " + jsonVersion + "," +
                "\"createdDate\": " + jsonCreatedDate +
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