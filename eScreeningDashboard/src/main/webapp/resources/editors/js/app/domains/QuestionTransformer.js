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
 * @type {*|EScreeningDashboardApp.models.QuestionTransformer|*|EScreeningDashboardApp.models.QuestionTransformer|Object|*|Object|*}
 */
EScreeningDashboardApp.models.QuestionTransformer =  EScreeningDashboardApp.namespace("gov.va.escreening.models.QuestionTransformer");
/**
 * Static method for the QuestionTransformer class that is responsible for transforming the JSON Representation of the
 * Question domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonResponse  Represents the JSON response of a service call request.
 * @returns {EScreeningDashboardApp.models.Question} An existing Question domain model that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.QuestionTransformer.transformJSONPayload = function (jsonResponse) {
    'use strict';
    /**
     * Represent the existing Question.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.Question}
     */
    var existingQuestion = null;

    if (!Object.isDefined(jsonResponse)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse can not be null or undefined.");
    }

    if (!Object.isDefined(jsonResponse.question)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.question can not be null or undefined.");
    }

    existingQuestion = EScreeningDashboardApp.models.QuestionTransformer.transformJSONQuestion(jsonResponse.question);

    return existingQuestion;
};
/**
 * Static method for the QuestionTransformer class that is responsible for transforming the JSON Representation of the
 * Question domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonQuestion  Represents the JSON representation of question returned from the service call request.
 * @returns {EScreeningDashboardApp.models.Question} An existing Question domain model that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.QuestionTransformer.transformJSONQuestion = function (jsonQuestion) {
    'use strict';
    /**
     * Represent the existing question.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.Question}
     */
    var existingQuestion;

    if (!Object.isDefined(jsonQuestion)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonQuestion can not be null or undefined.");
    }

    existingQuestion = new EScreeningDashboardApp.models.Question(jsonQuestion);

    return existingQuestion;
};