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
 * Constructor method for the Rule class.  The properties of this class can be initialized with
 * the jsonUserObject.
 * @class
 * @classdesc   This class is a domain model class; which means it has both behavior and state
 *              information about the user.
 * @param {String}  jsonRuleObject  Represents the JSON representation of a Rule object.
 * @constructor
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.Rule = function (jsonRuleObject) {
    var that = this,
        id = (Object.isDefined(jsonRuleObject) && Object.isDefined(jsonRuleObject.id))? jsonRuleObject.id : -1,
        name = (Object.isDefined(jsonRuleObject) && Object.isDefined(jsonRuleObject.name))? jsonRuleObject.name : null,
        expression = (Object.isDefined(jsonRuleObject) && Object.isDefined(jsonRuleObject.expression))? jsonRuleObject.expression : null,
        createdDate = (Object.isDefined(jsonRuleObject) && Object.isDefined(jsonRuleObject.createdDate))? (Object.isDate(jsonRuleObject.createdDate)) ? jsonRuleObject.createdDate : BytePushers.converters.DateConverter.convertToDate(jsonRuleObject.createdDate, BytePushers.converters.DateConverter.YYYYMMDDThhmmsssTZD_DATE_FORMAT) : null;

    this.getId = function(){
        return id;
    };
    
    this.getName = function(){
    	return name;
    };
    
    this.getExpression = function(){
    	return expression;
    };

    this.getCreatedDate= function() {
        return createdDate;
    };

    this.toString = function () {
        return "Rule [id: " + id + ", name: " + name + ", expression: " + expression + ", createdDate: " + createdDate + "]";
    };

    this.toJSON = function () {
        return "{\"id\": " + id + ",\"name\": \"" + name + "\",\"expression\": \"" + expression + ",\"createdDate\": \"" + (createdDate != null) ? createdDate.isISOString() : null + "\"}";
    };
};