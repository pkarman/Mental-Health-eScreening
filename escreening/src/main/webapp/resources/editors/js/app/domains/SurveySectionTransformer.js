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
 * @type {*|EScreeningDashboardApp.models.SurveySectionTransformer|*|EScreeningDashboardApp.models.SurveySectionTransformer|Object|*|Object|*}
 */
EScreeningDashboardApp.models.SurveySectionTransformer =  EScreeningDashboardApp.namespace("gov.va.escreening.models.SurveySectionTransformer");
/**
 * Static method for the SurveySectionTransformer class that is responsible for transforming the JSON Representation of the
 * SurveySection domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonResponse  Represents the JSON response of a service call request.
 * @returns {EScreeningDashboardApp.models.SurveySection} An existing SurveySection domain model that have been returned from a service call request.
 *
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
EScreeningDashboardApp.models.SurveySectionTransformer.transformJSONPayload = function (jsonResponse) {
    'use strict';
    /**
     * Represent the existing surveySection for a particular store.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.SurveySection}
     */
    var existingSurveySection = null;

    if (!Object.isDefined(jsonResponse)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse can not be null or undefined.");
    }

    if (!Object.isDefined(jsonResponse.surveySection)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.surveySection can not be null or undefined.");
    }

    existingSurveySection = EScreeningDashboardApp.models.SurveySectionTransformer.transformJSONSurveySection(jsonResponse.surveySection);

    return existingSurveySection;
};
/**
 * Static method for the SurveySectionTransformer class that is responsible for transforming the JSON Representation of the
 * SurveySection domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonSurveySection  Represents the JSON representation of surveySection returned from the service call request.
 * @returns {EScreeningDashboardApp.models.SurveySection} An existing SurveySection domain model that have been returned from a service call request.
 *
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
EScreeningDashboardApp.models.SurveySectionTransformer.transformJSONSurveySection = function (jsonSurveySection) {
    'use strict';
    /**
     * Represent the existing surveySection for a particular store.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.SurveySection}
     */
    var existingSurveySection;

    if (!Object.isDefined(jsonSurveySection)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonSurveySection can not be null or undefined.");
    }


    existingSurveySection = new EScreeningDashboardApp.models.SurveySection(jsonSurveySection);

    return existingSurveySection;
};