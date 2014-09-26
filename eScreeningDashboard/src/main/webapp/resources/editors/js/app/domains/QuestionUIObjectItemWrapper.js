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
EScreeningDashboardApp.models.QuestionUIObjectItemWrapper = function (questionItemConfig) {
    var id = new Date().getTime(),
        questionUIObject = (Object.isDefined(questionItemConfig) && Object.isDefined(questionItemConfig.questionIUObject))? questionItemConfig.questionIUObject: null,
        enabled = (Object.isDefined(questionItemConfig) && Object.isBoolean(questionItemConfig.enabled))? questionItemConfig.enabled : true;

    if(!Object.isDefined(questionUIObject)) {
        throw new Error("questionUIObject has to be defined.");
    }

    this.isQuestion = function() {
        return true;
    };

    this.isPage = function() {
        return false;
    };

    this.getId = function () {
        return id;
    };

    this.isEnabled = function () {
        return enabled;
    };

    this.getItem = function(){
        return questionUIObject;
    };

    this.getDescription = function() {
        return questionUIObject.description;
    };

    this.getType = function () {
        return questionUIObject.type;
    };
};