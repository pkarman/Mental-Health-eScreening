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
 * @type {*|EScreeningDashboardApp.models.SurveysTransformer|*|EScreeningDashboardApp.models.SurveysTransformer|Object|*|Object|*}
 */
EScreeningDashboardApp.models.SurveysTransformer =  EScreeningDashboardApp.namespace("gov.va.escreening.models.SurveysTransformer");
/**
 * Static method for the SurveysTransformer class that is responsible for transforming the JSON Representation of the
 * SurveySection domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonResponse  Represents the JSON response of a service call request.
 * @returns {EScreeningDashboardApp.models.SurveySection[]} A list of existing SurveySection domain models that have been returned from a service call request.
 *
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
EScreeningDashboardApp.models.SurveysTransformer.transformJSONPayload = function (jsonResponse) {
    'use strict';
    /**
     * Represent the existing Surveys for a particular store.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.SurveySection[]}
     */
    var existingSurveys = [];

    if (!Object.isDefined(jsonResponse)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse can not be null or undefined.");
    }

    if (!Object.isDefined(jsonResponse.surveys)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.Surveys can not be null or undefined.");
    }

    if (!Array.isArray(jsonResponse.surveys)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.Surveys must be an Array.");
    }

    existingSurveys = EScreeningDashboardApp.models.SurveysTransformer.transformJSONSurveys(jsonResponse.surveys);

    return existingSurveys;
};
/**
 * Static method for the SurveysTransformer class that is responsible for transforming the JSON Representation of the
 * SurveySection domain model object.
 *
 * @static
 * @method
 * @param {String[]}  jsonSurveySection  Represents the JSON representation of Surveys returned from the service call request.
 * @returns {EScreeningDashboardApp.models.Survey[]} A list of existing SurveySection domain models that have been returned from a service call request.
 *
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
EScreeningDashboardApp.models.SurveysTransformer.transformJSONSurveys = function (jsonSurveys) {
    'use strict';
    /**
     * Represent the existing Surveys for a particular store.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.Survey[]}
     */
    var existingSurveys = [];

    if (!Object.isDefined(jsonSurveys)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonSurveys can not be null or undefined.");
    }

    if (!Array.isArray(jsonSurveys)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonSurveys must be an Array.");
    }

    jsonSurveys.forEach(function(jsonSurvey){
        existingSurveys.push(new EScreeningDashboardApp.models.Survey(jsonSurvey));
    });

    return existingSurveys;
};