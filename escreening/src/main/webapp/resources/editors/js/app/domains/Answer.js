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
 * @author Aaron Roberson
 */
EScreeningDashboardApp.models.Answer = (function answer() {

    function extend(obj) {
        var answer = {
            id: '',
            text: '',
            type: '',
            exportName: '',
            calculationValue: '',
            displayOrder: 1,
			mhaValue: ''
        };

        for (var prop in obj) {
            if (answer.hasOwnProperty(prop)) {
                answer[prop] = obj[prop];
            }
        }

        return _.extend(obj, answer);
    }

    return {
        extend: extend
    };
})();
