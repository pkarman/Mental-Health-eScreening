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
 * Constructor method for the Answer class.  The properties of this class can be initialized with
 * the jsonUserObject.
 * @class
 * @classdesc   This class is a domain model class; which means it has both behaviour and state
 *              information about the user.
 * @param {String}  jsonAnswerObject  Represents the JSON representation of a Answer object.
 * @constructor
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.Answer = function (jsonAnswerObject) {
    var that = this,
        id = (Object.isDefined(jsonAnswerObject) && Object.isDefined(jsonAnswerObject.id))? jsonAnswerObject.id : -1,
        exportName = (Object.isDefined(jsonAnswerObject) && Object.isDefined(jsonAnswerObject.exportName)) ? jsonAnswerObject.exportName : null,
        text = (Object.isDefined(jsonAnswerObject) && Object.isDefined(jsonAnswerObject.text))? jsonAnswerObject.text : null,
        type = (Object.isDefined(jsonAnswerObject) && Object.isDefined(jsonAnswerObject.type))? jsonAnswerObject.type : null;

    this.getId = function(){
        return id;
    };
    
    this.getExportName = function(){
    	return exportName;
    };

    this.getText = function() {
        return text;
    };

    this.getType = function() {
        return type;
    };
    
    this.toString = function() {
        return "Answer{id: " + id + ", exportName: " + exportName + ", text: " + text + ", type: " + type + "}";
    };

    this.toJSON = function () {
        return angular.toJson(this.toUIObject());
    };
    
    this.toUIObject = function(){
    	return {
    		'id' : (Object.isDefined(id) && id > 0)? id : null,
    		'text' : text,
    		'type' : type,
    		'exportName' : exportName
    	};
    };
};