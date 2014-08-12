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
 * Constructor method for the RuleEvent class.  The properties of this class can be initialized with
 * the jsonUserObject.
 * @class
 * @classdesc   This class is a domain model class; which means it has both behavior and state
 *              information about the user.
 * @param {String}  jsonRuleEventObject  Represents the JSON representation of a RuleEvent object.
 * @constructor
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.RuleEvent = function (jsonRuleEventObject) {
    var that = this,
        id = (Object.isDefined(jsonRuleEventObject) && Object.isDefined(jsonRuleEventObject.id))? jsonRuleEventObject.id : -1,
        ruleId = (Object.isDefined(jsonRuleEventObject) && Object.isDefined(jsonRuleEventObject.ruleId))? jsonRuleEventObject.ruleId : -1,
        eventId = (Object.isDefined(jsonRuleEventObject) && Object.isDefined(jsonRuleEventObject.eventId))? jsonRuleEventObject.eventId : -1,
        createdDate = (Object.isDefined(jsonRuleEventObject) && Object.isDefined(jsonRuleEventObject.createdDate))? (Object.isDate(jsonRuleEventObject.createdDate)) ? jsonRuleEventObject.createdDate : BytePushers.converters.DateConverter.convertToDate(jsonRuleEventObject.createdDate, BytePushers.converters.DateConverter.YYYYMMDDThhmmsssTZD_DATE_FORMAT) : null;

    this.getId = function(){
        return id;
    };
    
    this.getRuleId = function(){
    	return ruleId;
    };
    
    this.getEventId= function(){
    	return eventId;
    };
    
    this.getClinicalReminderId = function(){
    	return clinicalReminderId;
    };
    
    this.getIsHistorical = function(){
    	return isHistorical;
    };

    this.getCreatedDate= function() {
        return createdDate;
    };

    this.toString = function () {
        return "RuleEvent [id: " + id + ", ruleId: " + ruleId + ", eventId: " + eventId + ",  createdDate: " + createdDate + "]";
    };

    this.toJSON = function () {
        return "{\"id\": " + id + ",\"ruleId\": \"" + ruleId + "\",\"eventId\": \"" + eventId + ",\"createdDate\": \"" + (createdDate != null) ? createdDate.isISOString() : null + "\"}";
    };
};