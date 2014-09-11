/**
 * Created by pouncilt on 8/4/14.
 */
/**
 * Angular service factory method that creates the SurveySerivce object.
 *
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
angular.module('EscreeningDashboardApp.services.survey', ['ngResource'])
    .factory('SurveyService', ['$q', '$resource', function ($q, $resource){
        "use strict";
        /**
         * Convenience method that supports the querying of the SurveyService.
         *
         * @private
         * @method
         * @param {{userId: *, surveyId: string}} querySurveySearchCriteria Represents the search criteria to query the SurveyService.
         * @returns {promise} A promise.
         */
        var query = function (querySurveySearchCriteria) {
            /**
             * Represents the angular Defer that is used for asynchronous service calls.
             *
             * @private
             * @field
             * @type {Defer}
             */
            var deferred = $q.defer(),
                service = $resource (
                    "services/:surveys/:surveyId",
                    {},
                    {
                        query: {
                            method: 'GET',
                            params: {},
                            isArray: false,
                            headers:{
                                'Accept': 'application/json',
                                'Content-Type':'application/json'
                            }
                        }
                    }
                );

            service.query(querySurveySearchCriteria, function (jsonResponse) {
                var existingSurveys = handleSurveyQueryResponse(jsonResponse, EScreeningDashboardApp.models.SurveysTransformer, null);
                deferred.resolve((existingSurveys.length === 1)? existingSurveys[0]: existingSurveys);
            }, function (reason) {
                var errorMessage = "Sorry, we are unable to process your request at this time because we experiencing problems communicating with the server."

                if(Object.isDefined(reason) && Object.isDefined(reason.status) && Object.isNumber(reason.status)) {
                    errorMessage = HttpRejectionProcessor.processRejection(reason);
                }

                deferred.reject(new BytePushers.models.ResponseStatus(
                    {
                        "message": errorMessage,
                        "status": "failed"
                    }
                ));
            });

            return deferred.promise;
        },
        /**
         * Convenience method that supports updating a Survey  via the SurveyService.
         *
         * @param {{surveyId: String, survey: EScreeningDashboardApp.models.Survey, surveys: String }}  saveSurveyRequestParameters Represents the save parameters for the request.
         */
        update = function (updateSurveyRequestParameter) {

            /**
             * Represents the angular Defer that is used for asynchronous service calls.
             *
             * @private
             * @field
             * @type {Defer}
             */
            var deferred = $q.defer(),
                /**
                 * Represents the angular Resource object that is used for asynchronous service calls.
                 *
                 * @private
                 * @field
                 * @type {Resource}
                 */
                service = $resource(
                    "services/:surveys/:surveyId",
                    {},
                    {
                        save: {
                            method: 'PUT',
                            params: {
                                "surveyId": updateSurveyRequestParameter.surveyId,
                                "surveys": updateSurveyRequestParameter.surveys
                            },
                            isArray: false,
                            headers:{
                                'Accept': 'application/json',
                                'Content-Type':'application/json'
                            }
                        }
                    }
                );

            service.save(updateSurveyRequestParameter.survey.toJSON(), function (jsonResponse) {
                var response = handleSurveySaveResponse(jsonResponse, EScreeningDashboardApp.models.SurveyTransformer, null);
                deferred.resolve(response);
            }, function (reason) {
                deferred.reject(reason);
            });

            return deferred.promise;
        },
        /**
         * Convenience method that supports creating a Survey via the SurveyService.
         *
         * @param {{surveyId: String, survey: EScreeningDashboardApp.models.Survey, surveys: String }}  saveSurveyRequestParameters Represents the save parameters for the request.
         */
        create = function (updateSurveyRequestParameter) {

            /**
             * Represents the angular Defer that is used for asynchronous service calls.
             *
             * @private
             * @field
             * @type {Defer}
             */
            var deferred = $q.defer(),
                /**
                 * Represents the angular Resource object that is used for asynchronous service calls.
                 *
                 * @private
                 * @field
                 * @type {Resource}
                 */
                service = $resource(
                    "services/:survey",
                    {},
                    {
                        save: {
                            method: 'PUT',
                            params: {
                                "survey": updateSurveyRequestParameter.survey
                            },
                            isArray: false,
                            headers:{
                                'Accept': 'application/json',
                                'Content-Type':'application/json'
                            }
                        }
                    }
                );

            service.save(updateSurveyRequestParameter.payload.toJSON(), function (jsonResponse) {
                var response = handleSurveySaveResponse(jsonResponse, EScreeningDashboardApp.models.SurveyTransformer);
                deferred.resolve(response.getPayload());
            }, function (reason) {
                deferred.reject(reason);
            });

            return deferred.promise;
        },
        /**
         * Convenience method that supports removing an Survey via the SurveyService.
         *
         * @param {{surveyId: string}}  removeSurveyRequestParameters Represents the save parameters for the request.
         */
        remove = function (removeSurveyRequestParameters) {
            /**
             * Represents the angular Defer that is used for asynchronous service calls.
             *
             * @private
             * @field
             * @type {Defer}
             */
            var deferred = $q.defer(),
                /**
                 * Represents the angular Resource object that is used for asynchronous service calls.
                 *
                 * @private
                 * @field
                 * @type {Resource}
                 */
                service = $resource(
                    "services/:surveys/:surveyId",
                    {},
                    {
                        save: {
                            method: 'DELETE',
                            params: {
                                "surveyId": removeSurveyRequestParameters.surveyId
                            },
                            isArray: false
                        }
                    }
                );

            service.save(removeSurveyRequestParameters, function (jsonResponse) {
                var response = handleSurveyRemoveResponse(jsonResponse);
                deferred.resolve(response);
            }, function (reason) {
                deferred.reject(reason);
            });

            return deferred.promise;
        };

        /**
         * Convenience method that sets the request parameter for the survey update service request.
         *
         * @private
         * @method
         * @param {EScreeningDashboardApp.models.Survey} survey Represents the Survey domain object to update.
         * @returns {{surveyId: *, surveys: String}} A search criteria object.
         */
        var setUpdateSurveyRequestParameter = function (survey) {
            var saveSurveyRequestParameter = {
                "surveyId": survey.getId(),
                "survey": survey,
                "surveys": "surveys"
            };

            if (!Object.isDefined(survey.getId())) {
                delete saveSurveyRequestParameter.surveyId;
                saveSurveyRequestParameter.surveys = saveSurveyRequestParameter.surveys + ".json";
            } else {
                saveSurveyRequestParameter.surveyId = saveSurveyRequestParameter.surveyId + ".json";
            }
            return saveSurveyRequestParameter;
        };

        /**
         * Convenience method that sets the request parameter for the survey remove service request.
         *
         * @private
         * @method
         * @param {String} surveyId Represent the Survey id to save.
         * @returns {{surveyId: *, surveys: String}} A search criteria object.
         */
        var setRemoveSurveyRequestParameter = function (surveyId) {
            var removeSurveyRequestParameter = {
                "surveyId": surveyId,
                "surveys": "surveys"
            };

            if (!Object.isDefined(surveyId)) {
                throw new BytePushers.exceptions.NullPointerException("surveyId parameter can not be null");
            }

            return removeSurveyRequestParameter;
        };

        /**
         * Convenience method that sets the request parameter for the survey create service request.
         *
         * @private
         * @method
         * @param {EScreeningDashboardApp.models.Survey} survey Represents the Survey domain object to update.
         * @returns {{payload: EScreeningDashboardApp.models.Survey, survey: String}} A create parameter request object.
         */
        var setCreateSurveyRequestParameter = function (survey) {
            var createSurveyRequestParameter = {
                "payload": survey,
                "survey": "survey"
            };


            return createSurveyRequestParameter;
        };

        /**
         * Convenience method that sets the search criteria for the survey search service query request.
         *
         * @private
         * @method
         * @param {String} surveyId Represent the user id to search for.
         * @returns {{surveyId: *, surveys: String}} A search criteria object.
         */
        var setQuerySurveySearchCriteria = function (surveyId) {
            var findSurveySearchCriteria = {
                "surveyId": surveyId,
                "surveys": "surveys"
            };

            if (!Object.isDefined(surveyId)) {
                delete findSurveySearchCriteria.surveyId;
                findSurveySearchCriteria.surveys = findSurveySearchCriteria.surveys + ".json";
            } else {
                findSurveySearchCriteria.surveyId = findSurveySearchCriteria.surveyId + ".json";
            }

            return findSurveySearchCriteria;
        };

        /**
         * Convenience method that handles the Survey Service query response by returning a fully
         * transformed Survey payload object.
         *
         * @private
         * @method
         * @param {String} jsonResponse Represents the Restful Survey Service query response.
         * @param {EScreeningDashboardApp.models.SurveysTransformer} jsonResponsePayloadTransformer Represents the Surveys JSON Response Payload transformer.
         * @param {String} userId Represents the User who made the service query request.
         * @returns {*} A transformed Survey payload object.
         * @throws {Error} If the jsonResponse can not be property transformed.
         */
        var handleSurveyQueryResponse = function (jsonResponse, jsonResponsePayloadTransformer, userId) {
            /**
             * Represents the response of a service call request.
             *
             * @private
             * @field
             * @type {EScreeningDashboardApp.models.Response}
             */
            var response = BytePushers.models.ResponseTransformer.transformJSONResponse(jsonResponse, jsonResponsePayloadTransformer, userId),
                /**
                 * Represents the transformed payload object of a service call request.
                 *
                 * @private
                 * @field
                 * @type {Object}
                 */
                payload = null;

            if (response !== null) {
                if (response.isSuccessful()) {
                    payload = response.getPayload();
                } else {
                    throw new Error(response.getMessage());
                }
            } else {
                throw new Error("handleSurveyServiceQueryResponse() method: Response is null.");
            }

            return payload;
        };

        /**
         * Convenience method that handles the Survey Service save response by returning a fully
         * transformed Survey payload object.
         *
         * @private
         * @method
         * @param {String} jsonResponse Represents the Restful Survey Service query response.
         * @param {EScreeningDashboardApp.models.SurveysTransformer} jsonResponsePayloadTransformer Represents the Surveys JSON Response Payload transformer.
         * @param {String} userId Represents the User who made the service query request.
         * @returns {*} A transformed Survey payload object.
         * @throws {Error} If the jsonResponse can not be property transformed.
         */
        var handleSurveySaveResponse = function (jsonResponse, jsonResponsePayloadTransformer, userId) {
            /**
             * Represents the response of a service call request.
             *
             * @private
             * @field
             * @type {EScreeningDashboardApp.models.Response}
             */
            var response = BytePushers.models.ResponseTransformer.transformJSONResponse(jsonResponse, jsonResponsePayloadTransformer, userId);

            if (response === null) {
                throw new Error("handleSurveyServiceQueryResponse() method: Response is null.");
            }

            return response;
        };

        /**
         * Convenience method that handles the Survey Service save response by returning a fully
         * transformed Survey payload object.
         *
         * @private
         * @method
         * @param {String} jsonResponse Represents the Restful Survey Service query response.
         * @param {String} userId Represents the User who made the service query request.
         * @returns {*} A transformed Survey payload object.
         * @throws {Error} If the jsonResponse can not be property transformed.
         */
        var handleSurveyRemoveResponse = function (jsonResponse, userId) {
            /**
             * Represents the response of a service call request.
             *
             * @private
             * @field
             * @type {EScreeningDashboardApp.models.Response}
             */

            if (!jsonResponse.status.status == 'succeeded'){
                var response = BytePushers.models.ResponseTransformer.transformJSONResponse(jsonResponse, null, userId, false),
                    /**
                     * Represents the transformed payload object of a service call request.
                     *
                     * @private
                     * @field
                     * @type {Object}
                     */
                    payload = null;

                if (response !== null) {
                    if (response.isSuccessful()) {
                        payload = response.getPayload();
                    } else {
                        throw new Error(response.getMessage());
                    }
                } else {
                    throw new Error("handleSurveyServiceQueryResponse() method: Response is null.");
                }
            }

            return response;
        };

        // Expose the public SurveyService API to the rest of the application.
        return {
            query: query,
            update: update,
            remove: remove,
            create: create,
            setCreateSurveyRequestParameter: setCreateSurveyRequestParameter,
            setQuerySurveySearchCriteria: setQuerySurveySearchCriteria,
            setUpdateSurveyRequestParameter: setUpdateSurveyRequestParameter,
            setRemoveSurveyRequestParameter: setRemoveSurveyRequestParameter
        };
    }]);
