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
        createdDate = (Object.isDefined(jsonSurveyPageObject) && Object.isDate(jsonSurveyPageObject.createdDate))? (Object.isDate(jsonSurveyPageObject.createdDate)) ? jsonSurveyPageObject.createdDate : BytePushers.converters.DateConverter.convertToDate(jsonSurveyPageObject.createdDate, BytePushers.converters.DateConverter.YYYYMMDDThhmmsssTZD_DATE_FORMAT) : null,
        questions = (Object.isDefined(jsonSurveyPageObject) && Object.isDefined(jsonSurveyPageObject.questions) && Object.isArray(jsonSurveyPageObject.questions))? EScreeningDashboardApp.models.QuestionsTransformer.transformJSONPayload({"questions": jsonSurveyPageObject.questions}) : [];

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

    this.setPageNumber = function(somePageNumber) {
        pageNumber = somePageNumber;
    };

    this.getCreatedDate = function() {
        return createdDate;
    };

    this.getQuestions = function () {
        return questions;
    };

    this.toString = function () {
        return "Survey {id: " + id + ", title: " + title + ", description: " + description + ", pageNumber: " + pageNumber +
            ", createdDate: " + createdDate + ", questions[" + questions + "]}";
    };

    this.toJSON = function (serializeCollections) {   
        var uiObj = this.toUIObject(serializeCollections);
        if(Object.isDefined(uiObj.createdDate)){
            var dateText = uiObj.createdDate.toISOString();
            uiObj.jsonCreatedDate = dateText.substring(0, dateText.length-1);
        }
        return angular.toJson(uiObj);
    };

    this.toUIObject = function(includeCollections){
        var incCollections = (Object.isDefined(includeCollections) && Object.isBoolean(includeCollections))? includeCollections : true;
        
        var uiObj = {
            'id': id,
            'title': title,
            'description' : description,
            'pageNumber': pageNumber,
            'createdDate': createdDate,
        };
        
        if(incCollections){
            uiObj.questions = EScreeningDashboardApp.models.Question.toUIObjects(questions);
        }
        
        uiObj.toString = function() {
            return id;
        };
        return uiObj;
    };
};
EScreeningDashboardApp.models.SurveyPage.toJSON = function(surveyPages) {
    var json = "[";

    if(Object.isArray(surveyPages)) {
        surveyPages.forEach(function(surveyPage, index) {
            json += surveyPage.toJSON() + ((index + 1 !== surveyPages.length)? "," : "");
        });
    }

    json += "]";

    return json;
};
EScreeningDashboardApp.models.SurveyPage.toUIObjects = function(surveyPages) {
    var surveyPageUIObjects = [];

    if(Object.isDefined(surveyPages) && Object.isArray(surveyPages)) {
        surveyPages.forEach(function(surveyPage) {
            surveyPageUIObjects.push(surveyPage.toUIObject());
        });
    }

    return surveyPageUIObjects;
};