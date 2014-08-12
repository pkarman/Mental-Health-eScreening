/**
 * Created by pouncilt on 8/4/14.
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
 * @type {*|EScreeningDashboardApp.models.SurveyTransformer|*|EScreeningDashboardApp.models.SurveyTransformer|Object|*|Object|*}
 */
EScreeningDashboardApp.models.SurveyTransformer =  EScreeningDashboardApp.namespace("gov.va.escreening.models.SurveyTransformer");
/**
 * Static method for the SurveyTransformer class that is responsible for transforming the JSON Representation of the
 * Survey domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonResponse  Represents the JSON response of a service call request.
 * @returns {EScreeningDashboardApp.models.Survey} An existing Survey domain model that have been returned from a service call request.
 *
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
EScreeningDashboardApp.models.SurveyTransformer.transformJSONPayload = function (jsonResponse) {
    'use strict';
    /**
     * Represent the existing survey for a particular store.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.Survey}
     */
    var existingSurvey = null;

    if (!Object.isDefined(jsonResponse)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse can not be null or undefined.");
    }

    if (!Object.isDefined(jsonResponse.survey)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.survey can not be null or undefined.");
    }

    existingSurvey = EScreeningDashboardApp.models.SurveyTransformer.transformJSONSurvey(jsonResponse.survey);

    return existingSurvey;
};
/**
 * Static method for the SurveyTransformer class that is responsible for transforming the JSON Representation of the
 * Survey domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonSurvey  Represents the JSON representation of survey returned from the service call request.
 * @returns {EScreeningDashboardApp.models.Survey} An existing Survey domain model that have been returned from a service call request.
 *
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
EScreeningDashboardApp.models.SurveyTransformer.transformJSONSurvey = function (jsonSurvey) {
    'use strict';
    /**
     * Represent the existing survey for a particular store.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.Survey}
     */
    var existingSurvey;

    if (!Object.isDefined(jsonSurvey)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonSurvey can not be null or undefined.");
    }


    existingSurvey = new EScreeningDashboardApp.models.Survey(jsonSurvey);

    return existingSurvey;
};