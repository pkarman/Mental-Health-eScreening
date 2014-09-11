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
EScreeningDashboardApp.models.PageQuestionItem = function (targetItem) {
    var page = (Object.isDefined(targetItem) && typeof targetItem === "object" && targetItem instanceof EScreeningDashboardApp.models.SurveyPage)? targetItem: null,
        question = (Object.isDefined(targetItem) && typeof targetItem === "object" && targetItem instanceof EScreeningDashboardApp.models.Question)? targetItem: null;

    if(!Object.isDefined(page) && !Object.isDefined(question)) {
        throw new Error("Either Page or Question has to be defined.");
    }

    this.isQuestion = function() {
        return (Object.isDefined(question))? true : false;
    };

    this.isPage = function() {
        return (Object.isDefined(page))? true : false;
    };

    this.getItem = function(){
        var item = (Object.isDefined(page))? page: (Object.isDefined(question))? question: null;

        if(!Object.isDefined(item)) {
            throw new Error("Either Page or Question has to be defined.");
        }

        return item;
    };
    this.getDescription = function() {
        var description;

        if(Object.isDefined(page)) {
            description = page.getTitle();
        } else if(Object.isDefined(question)){
            description = question.getVariableName();
        }

        return description;
    };
    this.getType = function () {
        var type;

        if(Object.isDefined(page)) {
            type = "Page";
        } else if(Object.isDefined(question)){
            type = question.getType();
        }

        return type;
    };
};