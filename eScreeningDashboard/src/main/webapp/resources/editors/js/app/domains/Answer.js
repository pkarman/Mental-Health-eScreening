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
        vistaText = (Object.isDefined(jsonAnswerObject) && Object.isDefined(jsonAnswerObject.vistaText)) ? jsonAnswerObject.vistaText : null,
        exportName = (Object.isDefined(jsonAnswerObject) && Object.isDefined(jsonAnswerObject.exportName)) ? jsonAnswerObject.exportName : null,
        text = (Object.isDefined(jsonAnswerObject) && Object.isDefined(jsonAnswerObject.text))? jsonAnswerObject.text : null,
        type = (Object.isDefined(jsonAnswerObject) && Object.isDefined(jsonAnswerObject.type))? jsonAnswerObject.type : null,
        response = (Object.isDefined(jsonAnswerObject) && Object.isDefined(jsonAnswerObject.response))? jsonAnswerObject.response : null,
        otherResponse = (Object.isDefined(jsonAnswerObject) && Object.isDefined(jsonAnswerObject.otherResponse)) ? jsonAnswerObject.otherResponse : null,
        rowId = (Object.isDefined(jsonAnswerObject) && Object.isDefined(jsonAnswerObject.rowId)) ? jsonAnswerObject.rowId : null;

    this.getId = function(){
        return id;
    };
    
    this.getVistaText = function(){
    	return vistaText;
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

    this.getResponse = function(){
    	return response;
    };
    
    this.getOtherResponse = function(){
    	return otherResponse;
    };
    
    this.getRowId = function(){
    	return rowId;
    };

    this.toString = function() {
        return "Answer{id: " + id + ", vistaText: " + vistaText + ", exportName: " + exportName + ", text: " + text + ", type: " + type + 
            ", response: " + response + ", otherResponse: " + otherResponse + ", rowId: " + rowId + "}";
    };

    this.toJSON = function () {
        return angular.toJson(this.toUIObject());
    };
    
    this.toUIObject = function(){
    	return {
    	    'id': id,
            'vistaText': vistaText,
            'exportName': exportName,
            'text': text,
            'type': type,
            'response': response,
            'otherResponse': otherResponse,
            'rowId':  rowId
    	}
    };
};