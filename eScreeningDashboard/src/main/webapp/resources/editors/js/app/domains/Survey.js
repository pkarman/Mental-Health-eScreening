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
 * Constructor method for the Survey class.  The properties of this class can be initialized with
 * the jsonUserObject.
 * @class
 * @classdesc   This class is a domain model class; which means it has both behaviour and state
 *              information about the user.
 * @param {String}  jsonSurvey  Represents the JSON representation of a Survey object.
 * @constructor
 * @author Aaron Roberson
 */
EScreeningDashboardApp.models.Survey = (function survey() {

    function extend(obj) {
        var survey = {
            id: '',
            name: '',
            vistaTitle: '',
            description: '',
            version: '',
            displayOrder: '',
            mha: '',
            mhaTestName: '',
            mhaResultGroupIen: '',
            clinicalReminderId: '',
            createdDate: '',
            surveySection: {},
            markedForDeletion: '',
            visible: ''
        };

        for (var prop in obj) {
            if (survey.hasOwnProperty(prop)) {
                survey[prop] = obj[prop];
            }
        }

        return _.extend(obj, survey);
    }

    return {
        extend: extend
    };

})();
