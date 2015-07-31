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
 * @type {*|EScreeningDashboardApp.models.AssessmentVariablesTransformer|*|EScreeningDashboardApp.models.AssessmentVariablesTransformer|Object|*|Object|*}
 */
EScreeningDashboardApp.models.AssessmentVariablesTransformer =  EScreeningDashboardApp.namespace("gov.va.escreening.models.AssessmentVariablesTransformer");
/**
 * Static method for the AssessmentVariablesTransformer class that is responsible for transforming the JSON Representation of the
 * AssessmentVariable domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonResponse  Represents the JSON response of a service call request.
 * @returns {EScreeningDashboardApp.models.AssessmentVariable[]} A list of existing AssessmentVariable domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.AssessmentVariablesTransformer.transformJSONPayload = function (jsonResponse) {
    'use strict';
    /**
     * Represent the existing AssessmentVariables.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.AssessmentVariable[]}
     */
    var existingAssessmentVariables = [];

    if (!Object.isDefined(jsonResponse)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse can not be null or undefined.");
    }

    if (!Object.isDefined(jsonResponse.assessmentVariables)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.AssessmentVariables can not be null or undefined.");
    }

    if (!Array.isArray(jsonResponse.assessmentVariables)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.AssessmentVariables must be an Array.");
    }

    existingAssessmentVariables = EScreeningDashboardApp.models.AssessmentVariablesTransformer.transformJSONAssessmentVariables(jsonResponse.assessmentVariables);

    return existingAssessmentVariables;
};
/**
 * Static method for the AssessmentVariablesTransformer class that is responsible for transforming the JSON Representation of the
 * AssessmentVariable domain model object.
 *
 * @static
 * @method
 * @param {String[]}  jsonAssessmentVariables  Represents the JSON representation ofAssessmentVariables returned from the service call request.
 * @returns {EScreeningDashboardApp.models.AssessmentVariables[]} A list of existing AssessmentVariable domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.AssessmentVariablesTransformer.transformJSONAssessmentVariables = function (jsonAssessmentVariables) {
    'use strict';
    /**
     * Represent the existing AssessmentVariables.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.AssessmentVariable[]}
     */
    var existingAssessmentVariables = [];

    if (!Object.isDefined(jsonAssessmentVariables)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonAssessmentVariables can not be null or undefined.");
    }

    if (!Array.isArray(jsonAssessmentVariables)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonAssessmentVariables must be an Array.");
    }

    jsonAssessmentVariables.forEach(function(jsonAssessmentVariable){
        existingAssessmentVariables.push(new EScreeningDashboardApp.models.AssessmentVariable(jsonAssessmentVariable));
    });

    return existingAssessmentVariables;
};