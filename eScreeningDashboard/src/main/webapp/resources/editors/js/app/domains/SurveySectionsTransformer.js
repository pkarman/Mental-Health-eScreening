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
 * @type {*|EScreeningDashboardApp.models.SurveySectionsTransformer|*|EScreeningDashboardApp.models.SurveySectionsTransformer|Object|*|Object|*}
 */
EScreeningDashboardApp.models.SurveySectionsTransformer =  EScreeningDashboardApp.namespace("gov.va.escreening.models.SurveySectionsTransformer");
/**
 * Static method for the SurveySectionsTransformer class that is responsible for transforming the JSON Representation of the
 * SurveySection domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonResponse  Represents the JSON response of a service call request.
 * @returns {EScreeningDashboardApp.models.SurveySection[]} A list of existing SurveySection domain models that have been returned from a service call request.
 *
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
EScreeningDashboardApp.models.SurveySectionsTransformer.transformJSONPayload = function (jsonResponse) {
    'use strict';
    /**
     * Represent the existing SurveySections for a particular store.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.SurveySection[]}
     */
    var existingSurveySections = [];

    if (!Object.isDefined(jsonResponse)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse can not be null or undefined.");
    }

    if (!Object.isDefined(jsonResponse.surveySections)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.SurveySections can not be null or undefined.");
    }

    if (!Array.isArray(jsonResponse.surveySections)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.SurveySections must be an Array.");
    }

    existingSurveySections = EScreeningDashboardApp.models.SurveySectionsTransformer.transformJSONSurveySections(jsonResponse.surveySections);

    return existingSurveySections;
};
/**
 * Static method for the SurveySectionsTransformer class that is responsible for transforming the JSON Representation of the
 * SurveySection domain model object.
 *
 * @static
 * @method
 * @param {String[]}  jsonSurveySections  Represents the JSON representation of SurveySections returned from the service call request.
 * @returns {EScreeningDashboardApp.models.SurveySection[]} A list of existing SurveySection domain models that have been returned from a service call request.
 *
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
EScreeningDashboardApp.models.SurveySectionsTransformer.transformJSONSurveySections = function (jsonSurveySections) {
    'use strict';
    /**
     * Represent the existing SurveySections for a particular store.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.SurveySection[]}
     */
    var existingSurveySections = [];

    if (!Object.isDefined(jsonSurveySections)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonSurveySections can not be null or undefined.");
    }

    if (!Array.isArray(jsonSurveySections)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonSurveySections must be an Array.");
    }

    jsonSurveySections.forEach(function(jsonSurveySection){
        existingSurveySections.push(new EScreeningDashboardApp.models.SurveySection(jsonSurveySection));
    });

    return existingSurveySections;
};