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
 * Constructor method for the HealthFactor class.  The properties of this class can be initialized with
 * the jsonUserObject.
 * @class
 * @classdesc   This class is a domain model class; which means it has both behavior and state
 *              information about the user.
 * @param {String}  jsonHealthFactorObject  Represents the JSON representation of a HealthFactor object.
 * @constructor
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.HealthFactor = function (jsonHealthFactorObject) {
    var that = this,
        id = (Object.isDefined(jsonHealthFactorObject) && Object.isDefined(jsonHealthFactorObject.id))? jsonHealthFactorObject.id : -1,
        name = (Object.isDefined(jsonHealthFactorObject) && Object.isDefined(jsonHealthFactorObject.name))? jsonHealthFactorObject.name : null,
        vistaIEN = (Object.isDefined(jsonHealthFactorObject) && Object.isDefined(jsonHealthFactorObject.vistaIEN))? jsonHealthFactorObject.vistaIEN : -1,
        clinicalReminderId = (Object.isDefined(jsonHealthFactorObject) && Object.isDefined(jsonHealthFactorObject.clinicalReminderId))? jsonHealthFactorObject.clinicalReminderId : -1,
        isHistorical = (Object.isDefined(jsonHealthFactorObject) && Object.isDefined(jsonHealthFactorObject.isHistorical))? jsonHealthFactorObject.isHistorical : false,
        createdDate = (Object.isDefined(jsonHealthFactorObject) && Object.isDefined(jsonHealthFactorObject.createdDate))? (Object.isDate(jsonHealthFactorObject.createdDate)) ? jsonHealthFactorObject.createdDate : BytePushers.converters.DateConverter.convertToDate(jsonHealthFactorObject.createdDate, BytePushers.converters.DateConverter.YYYYMMDDThhmmsssTZD_DATE_FORMAT) : null;

    this.getId = function(){
        return id;
    };
    
    this.getName = function(){
    	return name;
    };
    
    this.getVistaIEN = function(){
    	return vistaIEN;
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
        return "HealthFactor [id: " + id + ", name: " + name + ", vistaIEN: " + vistaIEN + ", clinicalReminderId: " + clinicalReminderId + ", isHistorical: " + isHistorical +",  createdDate: " + createdDate + "]";
    };

    this.toJSON = function () {
        return "{\"id\": " + id + ",\"name\": \"" + name + "\",\"vistaIEN\": \"" + vistaIEN + "\",clinicalReminderId\": \"" + clinicalReminderId + "\"isHistorical\": \"" + isHistorical + ",\"createdDate\": \"" + (createdDate != null) ? createdDate.isISOString() : null + "\"}";
    };
};