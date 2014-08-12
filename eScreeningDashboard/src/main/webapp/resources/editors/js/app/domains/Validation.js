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
 * Constructor method for the Validation class.  The properties of this class can be initialized with
 * the jsonUserObject.
 * @class
 * @classdesc   This class is a domain model class; which means it has both behaviour and state
 *              information about the user.
 * @param {String}  jsonValidationObject  Represents the JSON representation of a Validation object.
 * @constructor
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.Validation = function (jsonValidationObject) {
    var that = this,
        id = (Object.isDefined(jsonValidationObject) && Object.isDefined(jsonValidationObject.id))? jsonValidationObject.id : -1,
        code = (Object.isDefined(jsonValidationObject) && Object.isDefined(jsonValidationObject.code))? jsonValidationObject.code : null,
        description = (Object.isDefined(jsonValidationObject) && Object.isDefined(jsonValidationObject.description))? jsonValidationObject.description : null,
        dataType = (Object.isDefined(jsonValidationObject) && Object.isDefined(jsonValidationObject.dataType))? jsonValidationObject.dataType : null,
        createdDate = (Object.isDefined(jsonValidationObject) && Object.isDefined(jsonValidationObject.createdDate))? (Object.isDate(jsonValidationObject.createdDate)) ? jsonValidationObject.createdDate : BytePushers.converters.DateConverter.convertToDate(jsonValidationObject.createdDate, BytePushers.converters.DateConverter.YYYYMMDDThhmmsssTZD_DATE_FORMAT) : null;

    this.getId = function(){
        return id;
    };

    this.getCode = function(){
    	return code;
    };
    
    this.getDescription = function(){
    	return description;
    };
    
    this.getDataType = function(){
    	return dataType;
    };
    
    this.getCreatedDate = function(){
    	return createdDate;
    };
    
    this.toString = function() {
        return "Validation{id: " + id + ", code: " + code + ", description: " + description + ", dataType: " + dataType + ", dateCreated: " + createdDate + "}";
    };

    this.toJSON = function () {
        var jsonId = (Object.isDefined(id) && id > 0)? id : null,
            jsonCode = (Object.isDefined(code))? "\"" + code + "\"": null,
            jsonDescription = (Object.isDefined(description))? "\"" + description + "\"" : null,
            jsonDataType = (Object.isDefined(dataType))? "\"" + dataType + "\"": null,
            jsonCreatedDate =  (createdDate != null)? "\"" + createdDate.toISOString().substring(0, createdDate.toISOString().length-1) + "\"": null,
            json = "{" +
                "\"id\": " + jsonId + "," +
                "\"code\": " + jsonCode + "," +
                "\"description\": " + jsonDescription + "," +
                "\"dataType\": " + jsonDataType +
                ", \"createdDate\": " + jsonCreatedDate +
            "}";

        return json;
    };
    
    this.toUIObject = function(){
    	var ValidationUIObject = JSON.parse(this.toJSON());
    	return ValidationUIObject;
    };
};