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
 * @type {*|EScreeningDashboardApp.models.AnswerTransformer|*|EScreeningDashboardApp.models.AnswerTransformer|Object|*|Object|*}
 */
EScreeningDashboardApp.models.AnswerTransformer =  EScreeningDashboardApp.namespace("gov.va.escreening.models.AnswerTransformer");
/**
 * Static method for the AnswerTransformer class that is responsible for transforming the JSON Representation of the
 * Answer domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonResponse  Represents the JSON response of a service call request.
 * @returns {EScreeningDashboardApp.models.Answer} An existing Answer domain model that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.AnswerTransformer.transformJSONPayload = function (jsonResponse) {
    'use strict';
    /**
     * Represent the existing Answer.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.Answer}
     */
    var existingAnswer = null;

    if (!Object.isDefined(jsonResponse)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse can not be null or undefined.");
    }

    if (!Object.isDefined(jsonResponse.answer)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.answer can not be null or undefined.");
    }

    existingAnswer = EScreeningDashboardApp.models.AnswerTransformer.transformJSONAnswer(jsonResponse.answer);

    return existingAnswer;
};
/**
 * Static method for the AnswerTransformer class that is responsible for transforming the JSON Representation of the
 * Answer domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonAnswer  Represents the JSON representation of answer returned from the service call request.
 * @returns {EScreeningDashboardApp.models.Answer} An existing Answer domain model that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.AnswerTransformer.transformJSONAnswer = function (jsonAnswer) {
    'use strict';
    /**
     * Represent the existing answer.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.Answer}
     */
    var existingAnswer;

    if (!Object.isDefined(jsonAnswer)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonAnswer can not be null or undefined.");
    }

    existingAnswer = new EScreeningDashboardApp.models.Answer(jsonAnswer);

    return existingAnswer;
};