/**
 * Created by pouncilt on 9/24/14.
 */
/**
 * Created by pouncilt on 9/24/14.
 */
/**
 * Created by pouncilt on 9/24/14.
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
EScreeningDashboardApp.models.MenuItemSurveySectionUIObjectWrapper = function (surveySectionUIObject) {
    if (!Object.isDefined(surveySectionUIObject)) {
        throw new Error("SurveySection can not be null and has to be defined.");
    }

    if (typeof surveySectionUIObject === "object" && surveySectionUIObject instanceof EScreeningDashboardApp.models.SurveySection) {
        throw new Error("SurveySection has to be of type EScreeningDashboardApp.models.SurveyQuestion.");
    }

    this.item = surveySectionUIObject;

    this.name = surveySectionUIObject.name
};