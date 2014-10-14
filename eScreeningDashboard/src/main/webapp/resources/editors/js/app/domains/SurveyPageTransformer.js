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
 * @type {*|EScreeningDashboardApp.models.SurveyPageTransformer|*|EScreeningDashboardApp.models.SurveyPageTransformer|Object|*|Object|*}
 */
EScreeningDashboardApp.models.SurveyPageTransformer =  EScreeningDashboardApp.namespace("gov.va.escreening.models.SurveyPageTransformer");
/**
 * Static method for the SurveyPageTransformer class that is responsible for transforming the JSON Representation of the
 * SurveyPage domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonResponse  Represents the JSON response of a service call request.
 * @returns {EScreeningDashboardApp.models.SurveyPage} An existing SurveyPage domain model that have been returned from a service call request.
 *
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
EScreeningDashboardApp.models.SurveyPageTransformer.transformJSONPayload = function (jsonResponse) {
    'use strict';
    /**
     * Represent the existing surveyPage for a particular store.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.SurveyPage}
     */
    var existingSurveyPage = null;

    if (!Object.isDefined(jsonResponse)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse can not be null or undefined.");
    }

    if (!Object.isDefined(jsonResponse.surveyPage)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.surveyPage can not be null or undefined.");
    }

    existingSurveyPage = EScreeningDashboardApp.models.SurveyPageTransformer.transformJSONSurveyPage(jsonResponse.surveyPage);

    return existingSurveyPage;
};
/**
 * Static method for the SurveyPageTransformer class that is responsible for transforming the JSON Representation of the
 * SurveyPage domain model object.
 *
 * @static
 * @method
 * @param {String}  transformJSONSurveyPage  Represents the JSON representation of surveyPage returned from the service call request.
 * @returns {EScreeningDashboardApp.models.SurveyPage} An existing SurveyPage domain model that have been returned from a service call request.
 *
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
EScreeningDashboardApp.models.SurveyPageTransformer.transformJSONSurveyPage = function (transformJSONSurveyPage) {
    'use strict';
    /**
     * Represent the existing surveyPage for a particular store.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.SurveyPage}
     */
    var existingSurveyPage;

    if (!Object.isDefined(transformJSONSurveyPage)) {
        throw new BytePushers.exceptions.InvalidParameterException("transformJSONSurveyPage can not be null or undefined.");
    }


    existingSurveyPage = new EScreeningDashboardApp.models.SurveyPage(transformJSONSurveyPage);

    return existingSurveyPage;
};