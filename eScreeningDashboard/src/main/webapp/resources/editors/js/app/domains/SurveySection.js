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
 * Constructor method for the SurveySection class.  The properties of this class can be initialized with
 * the jsonUserObject.
 * @class
 * @classdesc   This class is a domain model class; which means it has both behaviour and state
 *              information about the user.
 * @param {String}  jsonSurveySectionObject  Represents the JSON representation of a SurveySection object.
 * @constructor
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
EScreeningDashboardApp.models.SurveySection = function (jsonSurveySectionObject) {
    var that = this,
        id = (Object.isDefined(jsonSurveySectionObject) && Object.isDefined(jsonSurveySectionObject.id))? jsonSurveySectionObject.id : -1,
        name = (Object.isDefined(jsonSurveySectionObject) && Object.isDefined(jsonSurveySectionObject.name))? jsonSurveySectionObject.name : null,
        description = (Object.isDefined(jsonSurveySectionObject) && Object.isDefined(jsonSurveySectionObject.description))? jsonSurveySectionObject.description : null,
        displayOrder = (Object.isDefined(jsonSurveySectionObject) && Object.isDefined(jsonSurveySectionObject.displayOrder))? jsonSurveySectionObject.displayOrder : null,
        createdDate = (Object.isDefined(jsonSurveySectionObject) && Object.isDefined(jsonSurveySectionObject.createdDate))? (Object.isDate(jsonSurveySectionObject.createdDate)) ? jsonSurveySectionObject.createdDate : BytePushers.converters.DateConverter.convertToDate(jsonSurveySectionObject.createdDate, BytePushers.converters.DateConverter.YYYYMMDDThhmmsssTZD_DATE_FORMAT) : null,
        surveys = (Object.isDefined(jsonSurveySectionObject) && Object.isDefined(jsonSurveySectionObject.surveys) && Object.isArray(jsonSurveySectionObject.surveys))? EScreeningDashboardApp.models.SurveysTransformer.transformJSONPayload({"surveys": jsonSurveySectionObject.surveys}) : [],
        markedForDeletion = (Object.isDefined(jsonSurveySectionObject) && Object.isBoolean(jsonSurveySectionObject.markedForDeletion))? jsonSurveySectionObject.markedForDeletion: false,
        visible = (Object.isDefined(jsonSurveySectionObject) && Object.isBoolean(jsonSurveySectionObject.visible))? jsonSurveySectionObject.visible: false;
      

    this.surveyArrayHasIncreased = (Object.isDefined(jsonSurveySectionObject) && Object.isDefined(jsonSurveySectionObject.surveyArrayHasIncreased && Object.isBoolean(jsonSurveySectionObject.surveyArrayHasIncreased)))? jsonSurveySectionObject.surveyArrayHasIncreased :false;

};
