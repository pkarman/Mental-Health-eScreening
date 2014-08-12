/**
 * Created by pouncilt on 8/6/14.
 */
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
 * @type {*|EScreeningDashboardApp.models.TableAnswersTransformer|*|EScreeningDashboardApp.models.TableAnswersTransformer|Object|*|Object|*}
 */
EScreeningDashboardApp.models.TableAnswersTransformer =  EScreeningDashboardApp.namespace("gov.va.escreening.models.TableAnswersTransformer");
/**
 * Static method for the TableAnswersTransformer class that is responsible for transforming the JSON Representation of the
 * Answers domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonResponse  Represents the JSON response of a service call request.
 * @returns {EScreeningDashboardApp.models.Answers[]} A list of existing Answer domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.TableAnswersTransformer.transformJSONPayload = function (jsonResponse) {
    'use strict';
    /**
     * Represent the existing Answers.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.Answers[]}
     */
    var existingTableAnswers = [];

    if (!Object.isDefined(jsonResponse)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse can not be null or undefined.");
    }

    if (!Object.isDefined(jsonResponse.tableAnswers)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.Answers can not be null or undefined.");
    }

    if (!Array.isArray(jsonResponse.tableAnswers)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.Answers must be an Array or Object.");
    }

    existingTableAnswers = EScreeningDashboardApp.models.TableAnswersTransformer.transformJSONAnswers(jsonResponse.tableAnswers);

    return existingTableAnswers;
};
/**
 * Static method for the TableAnswersTransformer class that is responsible for transforming the JSON Representation of the
 * Answer domain model object.
 *
 * @static
 * @method
 * @param {String[]}  jsonTableAnswers  Represents the JSON representation of Answers returned from the service call request.
 * @returns {EScreeningDashboardApp.models.Answers[]} A list of existing Answer domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.TableAnswersTransformer.transformJSONAnswers = function (jsonTableAnswers) {
    'use strict';
    /**
     * Represent the existing Answers.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.Answers[]}
     */
    var existingTableAnswers = [];

        if (!Object.isDefined(jsonTableAnswers)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonTableAnswers can not be null or undefined.");
    }

    if (!Array.isArray(jsonTableAnswers)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonTableAnswers must be an Array.");
    }

    jsonTableAnswers.forEach(function(jsonAnswers){
        if(Object.isDefined(jsonAnswers) && Object.isArray(jsonAnswers)) {
            if(jsonAnswers.length > 0 ) {
                var existingAnswers = [];
                jsonAnswers.forEach(function (jsonAnswer) {
                    existingAnswers.push(new EScreeningDashboardApp.models.Answer(jsonAnswer));
                });
                existingTableAnswers.push(existingAnswers);
            }
        }
    });

    return existingTableAnswers;
};