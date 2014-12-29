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
 * @param {String}  jsonSurveyObject  Represents the JSON representation of a Survey object.
 * @constructor create
 * @author Aaron Roberson
 */
EScreeningDashboardApp.models.Survey = (function Survey() {

    function create(config) {
        var survey = {
            id: null,
            name: null,
            vistaTitle: null,
            description: null,
            version: null,
            displayOrder: null,
            mha: null,
            mhaTestName: null,
            mhaResultGroupIen: null,
            clinicalReminder: false,
            createdDate: null,
            surveySection: null,
            markedForDeletion: false,
            visible: false
        };

        survey.sortByDisplayOrder = function sortByDisplayOrder(surveys, sortDirection) {
            sortDirection = (sortDirection && sortDirection === "-")? "-" : "+";
            if(Object.isArray(surveys)){
                if(sortDirection === "+") {
                    surveys.sort(function (a, b) {
                        return a.displayOrder - b.displayOrder;
                    });
                } else if(sortDirection === "-") {
                    surveys.sort(function (a, b) {
                        return b.displayOrder - a.displayOrder;
                    });
                }
            }

            return surveys;
        };

        return _.extend(survey, config);
    }

    return {
        create: create
    }

})();
