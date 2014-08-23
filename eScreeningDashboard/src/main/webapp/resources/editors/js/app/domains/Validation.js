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
        createdDate = (Object.isDefined(jsonValidationObject) && Object.isDefined(jsonValidationObject.createdDate))? (Object.isDate(jsonValidationObject.createdDate)) ? jsonValidationObject.createdDate : BytePushers.converters.DateConverter.convertToDate(jsonValidationObject.createdDate, BytePushers.converters.DateConverter.YYYYMMDDThhmmsssTZD_DATE_FORMAT) : null,
        name = (Object.isDefined(jsonValidationObject) && Object.isDefined(jsonValidationObject.name))? jsonValidationObject.name : null,
        value = (Object.isDefined(jsonValidationObject) && Object.isDefined(jsonValidationObject.value))? jsonValidationObject.value : null,
        selected = (Object.isDefined(jsonValidationObject))? (Object.isBoolean(jsonValidationObject.selected))? jsonValidationObject.selected : true: false;

    this.getId = function(){
        return id;
    };

    this.getCode = function(){
    	return code;
    };

    this.getName = function() {
        return name;
    }

    this.getValue = function() {
        return value;
    }
    
    this.getDescription = function(){
    	return description;
    };
    
    this.getDataType = function(){
    	return dataType;
    };
    
    this.getCreatedDate = function(){
    	return createdDate;
    };

    this.isSelected = function () {
        return selected;
    };

    this.selected = function (selectedStatus) {
        selected = selectedStatus;
    };
    
    this.toString = function() {
        return "Validation{id: " + id + ", code: " + code + ", name: " + name + ", value: " + value + ", description: " +
                description + ", dataType: " + dataType + ", dateCreated: " + createdDate + ", selected: " + selected + "}";
    };

    this.toJSON = function (serializeUIProperties) {
        serializeUIProperties = (Object.isDefined(serializeUIProperties) && Object.isBoolean(serializeUIProperties))? serializeUIProperties : false;
        var jsonId = (Object.isDefined(id) && id > 0)? id : null,
            jsonCode = (Object.isDefined(code))? "\"" + code + "\"": null,
            jsonName = (Object.isDefined(name))? "\"" + name + "\"": null,
            jsonValue = (Object.isDefined(value))? "\"" + value + "\"": null,
            jsonDescription = (Object.isDefined(description))? "\"" + description + "\"" : null,
            jsonDataType = (Object.isDefined(dataType))? "\"" + dataType + "\"": null,
            jsonCreatedDate =  (Object.isDefined(createdDate))? "\"" + createdDate.toISOString().substring(0, createdDate.toISOString().length-1) + "\"": null,
            jsonSelected = (serializeUIProperties)? Object.isDefined(selected)? "\"selected\": " + selected + "," : "\"selected\": " + false  + "," : "",
            json = "{" +
                "\"id\": " + jsonId + "," +
                "\"code\": " + jsonCode + "," +
                "\"name\": " + jsonName + "," +
                "\"value\": " + jsonValue + "," +
                "\"description\": " + jsonDescription + "," +
                "\"dataType\": " + jsonDataType + "," +
                jsonSelected +
                "\"createdDate\": " + jsonCreatedDate +
            "}";

        return json;
    };
    
    this.toUIObject = function(){
    	var ValidationUIObject = JSON.parse(this.toJSON(true));
    	return ValidationUIObject;
    };
};