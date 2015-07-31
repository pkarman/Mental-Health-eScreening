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
 * Represents the application static variable. Use existing static variable, if one already exists,
 * otherwise create a new one.
 *
 * @static
 * @type {*|EScreeningDashboardApp.models.QuestionsTransformer|*|EScreeningDashboardApp.models.QuestionsTransformer|Object|*|Object|*}
 */
EScreeningDashboardApp.models.QuestionsTransformer =  EScreeningDashboardApp.namespace("gov.va.escreening.models.QuestionsTransformer");
/**
 * Static method for the QuestionsTransformer class that is responsible for transforming the JSON Representation of the
 * Questions domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonResponse  Represents the JSON response of a service call request.
 * @returns {EScreeningDashboardApp.models.Questions[]} A list of existing Question domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.QuestionsTransformer.transformJSONPayload = function (jsonResponse) {
    'use strict';
    /**
     * Represent the existing Questions.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.Questions[]}
     */
    var existingQuestions = [];

    if (!Object.isDefined(jsonResponse)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse can not be null or undefined.");
    }

    if (!Object.isDefined(jsonResponse.questions)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.Questions can not be null or undefined.");
    }

    if (!Array.isArray(jsonResponse.questions)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.Questions must be an Array or Object.");
    }

    existingQuestions = EScreeningDashboardApp.models.QuestionsTransformer.transformJSONQuestions(jsonResponse.questions);

    return existingQuestions;
};
/**
 * Static method for the QuestionsTransformer class that is responsible for transforming the JSON Representation of the
 * Question domain model object.
 *
 * @static
 * @method
 * @param {String[]}  jsonQuestions  Represents the JSON representation of Questions returned from the service call request.
 * @returns {EScreeningDashboardApp.models.Questions[]} A list of existing Question domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.QuestionsTransformer.transformJSONQuestions = function (jsonQuestions) {
    'use strict';
    /**
     * Represent the existing Questions.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.Questions[]}
     */
    var existingQuestions = [];

    if (!Object.isDefined(jsonQuestions)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonQuestions can not be null or undefined.");
    }

    if (!Array.isArray(jsonQuestions)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonQuestions must be an Array.");
    }

    jsonQuestions.forEach(function(jsonQuestion){
        existingQuestions.push(new EScreeningDashboardApp.models.Question(jsonQuestion));
    });

    return existingQuestions;
};