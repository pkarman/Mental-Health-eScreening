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
 * Constructor method for the Answer class.  The properties of question class can be initialized with
 * the jsonUserObject.
 * @class
 * @classdesc   question class is a domain model class; which means it has both behaviour and state
 *              information about the user.
 * @param {String}  jsonQuestionObject  Represents the JSON representation of a Answer object.
 * @constructor
 * @author Aaron Roberson
 */
EScreeningDashboardApp.models.Question = (function question() {

    function extend(obj) {
        var question = {
            id: '',
            text: '',
            type: '',
            displayOrder: 1,
            required: false,
            ppi: false,
            mha: false,
            visible: true,
            variableName: '',
            answers: [],
            validations: [],
            childQuestions: [],
            tableAnswers: []
        };

        for (var prop in obj) {
            if (question.hasOwnProperty(prop)) {
                question[prop] = obj[prop];
            }
        }

        return _.extend(obj, question);
    }

    return {
        extend: extend
    };

})();
