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
 * @type {*|EScreeningDashboardApp.models.AssessmentVariableTransformer|*|EScreeningDashboardApp.models.AssessmentVariableTransformer|Object|*|Object|*}
 */
EScreeningDashboardApp.models.AssessmentVariableTransformer =  EScreeningDashboardApp.namespace("gov.va.escreening.models.AssessmentVariableTransformer");
/**
 * Static method for the AssessmentVariableTransformer class that is responsible for transforming the JSON Representation of the
 * AssessmentVariable domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonResponse  Represents the JSON response of a service call request.
 * @returns {EScreeningDashboardApp.models.AssessmentVariable[]} A list of existing AssessmentVariable domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.AssessmentVariableTransformer.transformJSONPayload = function (jsonResponse) {
    'use strict';
    /**
     * Represent the existing AssessmentVariable.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.AssessmentVariable}
     */
    var existingAssessmentVariable = null;

    if (!Object.isDefined(jsonResponse)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse can not be null or undefined.");
    }

    if (!Object.isDefined(jsonResponse.assessmentVariable)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.AssessmentVariable can not be null or undefined.");
    }

    existingAssessmentVariable = EScreeningDashboardApp.models.AssessmentVariableTransformer.transformJSONAssessmentVariable(jsonResponse.assessmentVariable);

    return existingAssessmentVariable;
};
/**
 * Static method for the AssessmentVariableTransformer class that is responsible for transforming the JSON Representation of the
 * AssessmentVariable domain model object.
 *
 * @static
 * @method
 * @param {String[]}  jsonAssessmentVariable  Represents the JSON representation of the AssessmentVariable returned from the service call request.
 * @returns {EScreeningDashboardApp.models.AssessmentVariable} An existing AssessmentVariable domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.AssessmentVariableTransformer.transformJSONAssessmentVariable = function (jsonAssessmentVariable) {
    'use strict';
    /**
     * Represent the existing AssessmentVariable.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.AssessmentVariable}
     */
    var existingAssessmentVariable = null;

    if (!Object.isDefined(jsonAssessmentVariable)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonAssessmentVariable can not be null or undefined.");
    }

    return existingAssessmentVariable;
};