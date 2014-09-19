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
                        "services/surveys/:surveyId/pages.json",
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
                    var response = handleSurveyPageQueryResponse(jsonResponse, EScreeningDashboardApp.models.SurveyPagesTransformer, null),
                        payload;

                    if(response.isSuccessful()){
                        if(Object.isArray(response.getPayload())){
                            if(response.getPayload().length === 1){
                                payload = EScreeningDashboardApp.models.SurveyPageTransformer.transformJSONPayload({surveyPage: response.getPayload()[0].toJSON()}, null);
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
                        "services/surveys/:surveyId/:pages",
                        {},
                        {
                            save: {
                                method: 'PUT',
                                params: {
                                    "surveyId": updateSurveyPageRequestParameter.surveyId,
                                    "pages": updateSurveyPageRequestParameter.pages
                                },
                                isArray: false,
                                headers:{
                                    'Accept': 'application/json',
                                    'Content-Type':'application/json'
                                }
                            }
                        }
                    );

                service.save(EScreeningDashboardApp.models.SurveyPage.toUIObjects(updateSurveyPageRequestParameter.payload), function (jsonResponse) {
                    var response = handleSurveyPageUpdateResponse(jsonResponse, EScreeningDashboardApp.models.SurveyPageTransformer, null);
                    deferred.resolve(response);
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
         * @param {Number} surveyId Represents the Survey Id to update.
         * @param {EScreeningDashboardApp.models.SurveyPage[]} surveyPages Represents the Survey Page domain objects to update.
         * @returns {{surveyId: Number, surveyPages: EScreeningDashboardApp.models.SurveyPage[], pages: String}} A search criteria object.
         */
        var setUpdateSurveyPageRequestParameter = function (surveyId, surveyPages) {
            var saveSurveyPageRequestParameter = {
                "surveyId": (Object.isDefined(surveyId))? surveyId: 1,
                "payload": surveyPages,
                "pages": "pages"
            };

            if (!Object.isDefined(surveyId)) {
                delete saveSurveyPageRequestParameter.surveyId;
                saveSurveyPageRequestParameter.pages = saveSurveyPageRequestParameter.pages + ".json";
            } else {
                saveSurveyPageRequestParameter.pages = saveSurveyPageRequestParameter.pages + ".json";
            }

            return saveSurveyPageRequestParameter;
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

            if (response === null) {
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
        var handleSurveyPageUpdateResponse = function (jsonResponse, jsonResponsePayloadTransformer, userId) {
            /**
             * Represents the response of a service call request.
             *
             * @private
             * @field
             * @type {EScreeningDashboardApp.models.Response}
             */
            var response = BytePushers.models.ResponseTransformer.transformJSONResponse(jsonResponse, jsonResponsePayloadTransformer, userId, false);

            if (response === null) {
                throw new Error("handleSurveyPageServiceQueryResponse() method: Response is null.");
            }

            return response;
        };

        // Expose the public SurveyPageService API to the rest of the application.
        return {
            query: query,
            update: update,
            setQuerySurveyPageSearchCriteria: setQuerySurveyPageSearchCriteria,
            setUpdateSurveyPageRequestParameter: setUpdateSurveyPageRequestParameter
        };
    }]);
