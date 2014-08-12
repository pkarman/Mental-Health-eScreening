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
 * Constructor method for the RuleAssessmentVariable class.  The properties of this class can be initialized with
 * the jsonUserObject.
 * @class
 * @classdesc   This class is a domain model class; which means it has both behavior and state
 *              information about the user.
 * @param {String}  jsonRuleAssessmentVariableObject  Represents the JSON representation of a RuleAssessmentVariable object.
 * @constructor
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.RuleAssessmentVariable = function (jsonRuleAssessmentVariableObject) {
    var that = this,
        id = (Object.isDefined(jsonRuleAssessmentVariableObject) && Object.isDefined(jsonRuleAssessmentVariableObject.id))? jsonRuleAssessmentVariableObject.id : -1,
        ruleId = (Object.isDefined(jsonRuleAssessmentVariableObject) && Object.isDefined(jsonRuleAssessmentVariableObject.ruleId))? jsonRuleAssessmentVariableObject.ruleId : -1,
        assessmentVariableId = (Object.isDefined(jsonRuleAssessmentVariableObject) && Object.isDefined(jsonRuleAssessmentVariableObject.assessmentVariableId))? jsonRuleAssessmentVariableObject.assessmentVariableId : -1,
        createdDate = (Object.isDefined(jsonRuleAssessmentVariableObject) && Object.isDefined(jsonRuleAssessmentVariableObject.createdDate))? (Object.isDate(jsonRuleAssessmentVariableObject.createdDate)) ? jsonRuleAssessmentVariableObject.createdDate : BytePushers.converters.DateConverter.convertToDate(jsonRuleAssessmentVariableObject.createdDate, BytePushers.converters.DateConverter.YYYYMMDDThhmmsssTZD_DATE_FORMAT) : null;

    this.getId = function(){
        return id;
    };
    
    this.getRuleId = function(){
    	return ruleId;
    };
    
    this.getAssessmentVariableId = function(){
    	return assessmentVariableId;
    };

    this.getCreatedDate= function() {
        return createdDate;
    };

    this.toString = function () {
        return "Rule [id: " + id + ", ruleId: " + ruleId + ", assessmentVariableId: " + assessmentVariableId + ", createdDate: " + createdDate + "]";
    };

    this.toJSON = function () {
        return "{\"id\": " + id + ",\"ruleId\": \"" + ruleId + "\",\"assessmentVariableId\": \"" + assessmentVariableId + ",\"createdDate\": \"" + (createdDate != null) ? createdDate.isISOString() : null + "\"}";
    };
};