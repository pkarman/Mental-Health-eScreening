/**
 * Created by pouncilt on 9/10/14.
 */
/**
 * Angular service factory method that creates the SurveyPageService object.
 *
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
angular.module('EscreeningDashboardApp.services.surveypage', ['ngResource'])
    .factory('SurveyPageService', ['$q', '$resource', function ($q, $resource){
        "use strict";
        /**
         * Convenience method that supports the querying of the SurveyPageService.
         *
         * @private
         * @method
         * @param {{userId: *, surveyId: string}} querySurveyPageSearchCriteria Represents the search criteria to query the SurveyPageService.
         * @returns {promise} A promise.
         */
        var query = function (querySurveyPageSearchCriteria) {
                /**
                 * Represents the angular Defer that is used for asynchronous service calls.
                 *
                 * @private
                 * @field
                 * @type {Defer}
                 */
                var deferred = $q.defer(),
                    service = $resource (
                        "/services/surveys/:surveyId/pages.json",
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

                service.query(querySurveyPageSearchCriteria, function (jsonResponse) {
                    var response = handleSurveyPageQueryResponse(jsonResponse, EScreeningDashboardApp.models.SurveysTransformer, null),
                        payload;

                    if(response.isSuccessful()){
                        if(Object.isArray(response.getPayload())){
                            if(response.getPayload().length === 1){
                                payload = EScreeningDashboardApp.models.SurveyTransformer.transformJSONPayload(response.getPayload()[0].toJSON(), null);
                                response = new BytePushers.models.Response(response.getStatus(), payload);
                            }
                        }
                    }
                    deferred.resolve(response);
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
             * Convenience method that supports updating a Survey  via the SurveyPageService.
             *
             * @param {{surveyId: String, survey: EScreeningDashboardApp.models.Survey, surveys: String }}  saveSurveyRequestParameters Represents the save parameters for the request.
             */
            update = function (updateSurveyPageRequestParameter) {

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
                        "services/surveys/:surveyId/pages",
                        {},
                        {
                            save: {
                                method: 'PUT',
                                params: {
                                    /*"surveyId": updateSurveyRequestParameter.surveyId,
                                    "surveys": updateSurveyRequestParameter.surveys*/
                                },
                                isArray: false,
                                headers:{
                                    'Accept': 'application/json',
                                    'Content-Type':'application/json'
                                }
                            }
                        }
                    );

                service.save(updateSurveyPageRequestParameter.survey.toJSON(), function (jsonResponse) {
                    var response = handleSurveyPageSaveResponse(jsonResponse, EScreeningDashboardApp.models.SurveyTransformer, null);
                    deferred.resolve(response);
                }, function (reason) {
                    deferred.reject(reason);
                });

                return deferred.promise;
            },
            /**
             * Convenience method that supports creating a Survey via the SurveyPageService.
             *
             * @param {{surveyId: String, survey: EScreeningDashboardApp.models.Survey, surveys: String }}  saveSurveyRequestParameters Represents the save parameters for the request.
             */
            create = function (updateSurveyPageRequestParameter) {

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
                                    "survey": updateSurveyPageRequestParameter.survey
                                },
                                isArray: false,
                                headers:{
                                    'Accept': 'application/json',
                                    'Content-Type':'application/json'
                                }
                            }
                        }
                    );

                service.save(updateSurveyPageRequestParameter.payload.toJSON(), function (jsonResponse) {
                    var response = handleSurveyPageSaveResponse(jsonResponse, EScreeningDashboardApp.models.SurveyTransformer);
                    deferred.resolve(response.getPayload());
                }, function (reason) {
                    deferred.reject(reason);
                });

                return deferred.promise;
            };

        /**
         * Convenience method that sets the request parameter for the survey page update service request.
         *
         * @private
         * @method
         * @param {EScreeningDashboardApp.models.SurveyPage} surveyPage Represents the Survey domain object to update.
         * @returns {{surveyPageId: *, surveyPage: EScreeningDashboardApp.models.SurveyPage, surveys: String}} A search criteria object.
         */
        var setUpdateSurveyPageRequestParameter = function (surveyPage) {
            var saveSurveyPageRequestParameter = {
                "surveyPageId": surveyPage.getId(),
                "surveyPage": surveyPage,
                "surveys": "surveys"
            };

            if (!Object.isDefined(surveyPage.getId())) {
                delete saveSurveyPageRequestParameter.surveyId;
                saveSurveyPageRequestParameter.surveys = saveSurveyPageRequestParameter.surveys + ".json";
            } else {
                saveSurveyPageRequestParameter.surveyPageId = saveSurveyPageRequestParameter.surveyPageId + ".json";
            }
            return saveSurveyPageRequestParameter;
        };

        /**
         * Convenience method that sets the request parameter for the survey page remove service request.
         *
         * @private
         * @method
         * @param {String} surveyPageId Represent the Survey id to save.
         * @returns {{surveyPageId: *}} A search criteria object.
         */
        var setRemoveSurveyPageRequestParameter = function (surveyPageId) {
            var removeSurveyPageRequestParameter = {
                "surveyPageId": surveyPageId
            };

            if (!Object.isDefined(surveyPageId)) {
                throw new BytePushers.exceptions.NullPointerException("surveyPageId parameter can not be null");
            }

            return removeSurveyPageRequestParameter;
        };

        /**
         * Convenience method that sets the request parameter for the survey page create service request.
         *
         * @private
         * @method
         * @param {EScreeningDashboardApp.models.SurveyPage} surveyPage Represents the Survey Page domain object to update.
         * @returns {{payload: EScreeningDashboardApp.models.SurveyPage}} A create parameter request object.
         */
        var setCreateSurveyPageRequestParameter = function (surveyPage) {
            var createSurveyPageRequestParameter = {
                "payload": surveyPage
            };


            return createSurveyPageRequestParameter;
        };

        /**
         * Convenience method that sets the search criteria for the survey page search service query request.
         *
         * @private
         * @method
         * @param {String} surveyId Represent the user id to search for.
         * @returns {{surveyId: *}} A search criteria object.
         */
        var setQuerySurveyPageSearchCriteria = function (surveyId) {
            var findSurveyPageSearchCriteria = {
                "surveyId": surveyId
            };

            if (!Object.isDefined(surveyId)) {
                throw new Error("surveyId must be defined.");
            }

            return findSurveyPageSearchCriteria;
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
        var handleSurveyPageQueryResponse = function (jsonResponse, jsonResponsePayloadTransformer, userId) {
            /**
             * Represents the response of a service call request.
             *
             * @private
             * @field
             * @type {EScreeningDashboardApp.models.Response}
             */
            var response = BytePushers.models.ResponseTransformer.transformJSONResponse(jsonResponse, jsonResponsePayloadTransformer, userId);

            if (response !== null) {
                throw new Error("handleSurveyPageServiceQueryResponse() method: Response is null.");
            }

            return response;
        };

        /**
         * Convenience method that handles the Survey Service save response by returning a fully
         * transformed Survey Page payload object.
         *
         * @private
         * @method
         * @param {String} jsonResponse Represents the Restful Survey Service query response.
         * @param {EScreeningDashboardApp.models.SurveysTransformer} jsonResponsePayloadTransformer Represents the Surveys JSON Response Payload transformer.
         * @param {String} userId Represents the User who made the service query request.
         * @returns {*} A transformed Survey payload object.
         * @throws {Error} If the jsonResponse can not be property transformed.
         */
        var handleSurveyPageSaveResponse = function (jsonResponse, jsonResponsePayloadTransformer, userId) {
            /**
             * Represents the response of a service call request.
             *
             * @private
             * @field
             * @type {EScreeningDashboardApp.models.Response}
             */
            var response = BytePushers.models.ResponseTransformer.transformJSONResponse(jsonResponse, jsonResponsePayloadTransformer, userId);

            if (response === null) {
                throw new Error("handleSurveyPageServiceQueryResponse() method: Response is null.");
            }

            return response;
        };

        // Expose the public SurveyPageService API to the rest of the application.
        return {
            query: query,
            update: update,
            create: create,
            setCreateSurveyPageRequestParameter: setCreateSurveyPageRequestParameter,
            setQuerySurveyPageSearchCriteria: setQuerySurveyPageSearchCriteria,
            setUpdateSurveyPageRequestParameter: setUpdateSurveyPageRequestParameter
        };
    }]);
