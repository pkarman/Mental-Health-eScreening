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
 * @type {*|EScreeningDashboardApp.models.AnswersTransformer|*|EScreeningDashboardApp.models.AnswersTransformer|Object|*|Object|*}
 */
EScreeningDashboardApp.models.AnswersTransformer =  EScreeningDashboardApp.namespace("gov.va.escreening.models.AnswersTransformer");
/**
 * Static method for the AnswersTransformer class that is responsible for transforming the JSON Representation of the
 * Answers domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonResponse  Represents the JSON response of a service call request.
 * @returns {EScreeningDashboardApp.models.Answers[]} A list of existing Answer domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.AnswersTransformer.transformJSONPayload = function (jsonResponse) {
    'use strict';
    /**
     * Represent the existing Answers.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.Answers[]}
     */
    var existingAnswers = [];

    if (!Object.isDefined(jsonResponse)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse can not be null or undefined.");
    }

    if (!Object.isDefined(jsonResponse.answers)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.Answers can not be null or undefined.");
    }

    if (!Array.isArray(jsonResponse.answers)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.Answers must be an Array or Object.");
    }

    existingAnswers = EScreeningDashboardApp.models.AnswersTransformer.transformJSONAnswers(jsonResponse.answers);

    return existingAnswers;
};
/**
 * Static method for the AnswersTransformer class that is responsible for transforming the JSON Representation of the
 * Answer domain model object.
 *
 * @static
 * @method
 * @param {String[]}  jsonAnswers  Represents the JSON representation of Answers returned from the service call request.
 * @returns {EScreeningDashboardApp.models.Answers[]} A list of existing Answer domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.AnswersTransformer.transformJSONAnswers = function (jsonAnswers) {
    'use strict';
    /**
     * Represent the existing Answers.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.Answers[]}
     */
    var existingAnswers = [];

    if (!Object.isDefined(jsonAnswers)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonAnswers can not be null or undefined.");
    }

    if (!Array.isArray(jsonAnswers)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonAnswers must be an Array.");
    }

    jsonAnswers.forEach(function(jsonAnswer){
        existingAnswers.push(new EScreeningDashboardApp.models.Answer(jsonAnswer));
    });

    return existingAnswers;
};