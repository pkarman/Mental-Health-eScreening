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
 * Constructor method for the SurveyPage class.  The properties of this class can be initialized with
 * the jsonUserObject.
 * @class
 * @classdesc   This class is a domain model class; which means it has both behaviour and state
 *              information about the user.
 * @param {String}  jsonSurveyPageObject  Represents the JSON representation of a SurveyPage object.
 * @constructor
 * @author Aaron Roberson
 */
EScreeningDashboardApp.models.SurveyPage = (function surveyPage() {

    function extend(obj) {
        var surveyPage = {
            id: '',
            title: '',
            description: '',
            pageNumber: 0,
            questions: []
        };

        for (var prop in obj) {
            if (surveyPage.hasOwnProperty(prop)) {
                surveyPage[prop] = obj[prop];
            }
        }

        return _.extend(obj, surveyPage);
    }

    return {
        extend: extend
    };

})();
