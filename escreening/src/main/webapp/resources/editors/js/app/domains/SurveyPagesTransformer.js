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
 * @type {*|EScreeningDashboardApp.models.SurveyPagesTransformer|*|EScreeningDashboardApp.models.SurveyPagesTransformer|Object|*|Object|*}
 */
EScreeningDashboardApp.models.SurveyPagesTransformer =  EScreeningDashboardApp.namespace("gov.va.escreening.models.SurveyPagesTransformer");
/**
 * Static method for the SurveyPagesTransformer class that is responsible for transforming the JSON Representation of the
 * SurveyPage domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonResponse  Represents the JSON response of a service call request.
 * @returns {EScreeningDashboardApp.models.SurveyPage[]} A list of existing SurveyPage domain models that have been returned from a service call request.
 *
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
EScreeningDashboardApp.models.SurveyPagesTransformer.transformJSONPayload = function (jsonResponse) {
    'use strict';
    /**
     * Represent the existing SurveyPages for a particular store.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.SurveyPage[]}
     */
    var existingSurveyPages = [];

    if (!Object.isDefined(jsonResponse)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse can not be null or undefined.");
    }

    if (!Object.isDefined(jsonResponse.surveyPages)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.SurveyPages can not be null or undefined.");
    }

    if (!Array.isArray(jsonResponse.surveyPages)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.SurveyPages must be an Array.");
    }

    existingSurveyPages = EScreeningDashboardApp.models.SurveyPagesTransformer.transformJSONSurveyPages(jsonResponse.surveyPages);

    return existingSurveyPages;
};
/**
 * Static method for the SurveyPagesTransformer class that is responsible for transforming the JSON Representation of the
 * SurveyPage domain model object.
 *
 * @static
 * @method
 * @param {String[]}  jsonSurveyPages  Represents the JSON representation of SurveyPages returned from the service call request.
 * @returns {EScreeningDashboardApp.models.SurveyPage[]} A list of existing SurveyPage domain models that have been returned from a service call request.
 *
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
EScreeningDashboardApp.models.SurveyPagesTransformer.transformJSONSurveyPages = function (jsonSurveyPages) {
    'use strict';
    /**
     * Represent the existing SurveyPages for a particular store.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.SurveyPage[]}
     */
    var existingSurveyPages = [];

    if (!Object.isDefined(jsonSurveyPages)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonSurveySections can not be null or undefined.");
    }

    if (!Array.isArray(jsonSurveyPages)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonSurveySections must be an Array.");
    }

    jsonSurveyPages.forEach(function(jsonSurveyPage){
        existingSurveyPages.push(new EScreeningDashboardApp.models.SurveyPage(jsonSurveyPage));
    });

    return existingSurveyPages;
};