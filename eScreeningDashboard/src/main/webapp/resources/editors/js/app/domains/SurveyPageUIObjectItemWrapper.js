/**
 * Created by pouncilt on 9/25/14.
 */
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
 * Constructor method for the PageQuestionItem class.  The properties of this class can be initialized with
 * the jsonUserObject.
 * @class
 * @classdesc   This class is a domain model class; which means it has both behaviour and state
 *              information about the user.
 * @param {String}  jsonPageQuestionItemObject  Represents the JSON representation of a PageQuestionItem object.
 * @constructor
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
EScreeningDashboardApp.models.SurveyPageUIObjectItemWrapper = function (surveyPageConfig) {
    var id = new Date().getTime(),
        surveyPageUIObject = (Object.isDefined(surveyPageConfig) && Object.isDefined(surveyPageConfig.surveyPageUIObject))? surveyPageConfig.surveyPageUIObject: null,
        enabled = (Object.isDefined(surveyPageConfig) && Object.isBoolean(surveyPageConfig.enabled))? surveyPageConfig.enabled : true;

    if(!Object.isDefined(surveyPageUIObject)) {
        throw new Error("Either Page has to be defined.");
    }

    this.isQuestion = function() {
        return false;
    };

    this.isPage = function() {
        return true;
    };

    this.getId = function () {
        return id;
    };

    this.isEnabled = function () {
        return enabled;
    };

    this.getItem = function(){
        return surveyPageUIObject;
    };
    this.getDescription = function() {
        return "";
    };
    this.getType = function () {
        return "Page";
    };
};