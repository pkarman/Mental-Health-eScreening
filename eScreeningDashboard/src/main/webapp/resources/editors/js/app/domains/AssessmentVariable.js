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
 * Constructor method for the AssessmentVariable class.  The properties of this class can be initialized with
 * the jsonUserObject.
 * @class
 * @classdesc   This class is a domain model class; which means it has both behavior and state
 *              information about the user.
 * @param {String}  jsonSurveySectionObject  Represents the JSON representation of an Assessment Variable object.
 * @constructor
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.AssessmentVariable = function (jsonAssessmentVariableObject) {
    var that = this,
        id = (Object.isDefined(jsonAssessmentVariableObject) && Object.isDefined(jsonAssessmentVariableObject.id))? jsonAssessmentVariableObject.id : -1,
        variableTypeId = (Object.isDefined(jsonAssessmentVariableObject) && Object.isDefined(jsonAssessmentVariableObject.variableTypeId)) ? jsonAssessmentVariableObject.variableTypeId : -1,		
        displayName = (Object.isDefined(jsonAssessmentVariableObject) && Object.isDefined(jsonAssessmentVariableObject.displayName))? jsonAssessmentVariableObject.displayName : null,
        description = (Object.isDefined(jsonAssessmentVariableObject) && Object.isDefined(jsonAssessmentVariableObject.decription))? jsonAssessmentVariableObject.description : null,
        measureId = (Object.isDefined(jsonAssessmentVariableObject) && Object.isDefined(jsonAssessmentVariableObject.measureId))? jsonAssessmentVariableObject.measureId : -1,
        measureAnswerId = (Object.isDefined(jsonAssessmentVariableObject) && Object.isDefined(jsonAssessmentVariableObject.measureAnserId))? jsonAssessmentVariableObject.measureAnswerId : -1,
        formulaTemplate = (Object.isDefined(jsonAssessmentVariableObject) && Object.isDefined(jsonAssessmentVariableObject.formulaTemplate))? jsonAssessmentVariableObject.formulaTemplate : null,
        createdDate = (Object.isDefined(jsonAssessmentVariableObject) && Object.isDefined(jsonAssessmentVariableObject.createdDate))? (Object.isDate(jsonAssessmentVariableObject.createdDate)) ? jsonAssessmentVariableObject.createdDate : BytePushers.converters.DateConverter.convertToDate(jsonAssessmentVariableObject.createdDate, BytePushers.converters.DateConverter.YYYYMMDDThhmmsssTZD_DATE_FORMAT) : null;

    this.getId = function(){
        return id;
    };
    
    this.getVariableTypeId = function(){
    	return variableTypeId;
    };

    this.getDisplayName = function() {
        return displayName;
    };

    this.getDescription = function() {
        return description;
    };

    this.getMeasureId = function(){
    	return measureId;
    };
    
    this.getMeasureAnswerId = function(){
    	return measureAnswerId;
    };
    
    this.getFormulaTemplate = function(){
    	return formulaTemplate;
    };

    this.getCreatedDate= function() {
        return createdDate;
    };

    this.toString = function () {
        return "AssessmentVariable [id: " + id + ", displayName: " + displayNameame + ", description: " + description + ", measureId: " + measureId +
            ", measureAnswerId: " + measureAnswerId + ", formulaTemplate: " + formulaTemplate + ", createdDate: " + createdDate + "]";
    };

    this.toJSON = function () {
        return "{\"id\": " + id + ",\"displayName\": \"" + displayName + "\",\"description\": \"" + description + "\",\"measureId\": " + measureId +
            ",\"measureAnswerId\": " + measureAnswerId + ",\"formulaTemplate\": "+ formulaTemplate + ",\"createdDate\": \"" + (createdDate != null) ? createdDate.isISOString() : null + "\"}";
    };
};