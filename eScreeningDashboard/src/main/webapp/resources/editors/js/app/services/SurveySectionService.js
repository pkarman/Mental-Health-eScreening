/**
 * Angular service factory method that creates the StoreSerivce object.
 *
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
angular.module('EscreeningDashboardApp.services.surveysection', ['ngResource'])
    .factory('SurveySectionService', ['$q', '$resource', function ($q, $resource){
        "use strict";
        /**
         * Convenience method that supports the querying of the SurveySectionService.
         *
         * @private
         * @method
         * @param {{userId: *, surveySectionId: string}} querySurveySectionSearchCriteria Represents the search criteria to query the SurveySectionService.
         * @returns {promise} A promise.
         */
        var query = function (querySurveySectionSearchCriteria) {
            /**
             * Represents the angular Defer that is used for asynchronous service calls.
             *
             * @private
             * @field
             * @type {Defer}
             */
            var deferred = $q.defer(),
                service = $resource (
                    "services/:surveySections/:surveySectionId",
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

            service.query(querySurveySectionSearchCriteria, function (jsonResponse) {
                var response = handleSurveySectionQueryResponse(jsonResponse, EScreeningDashboardApp.models.SurveySectionsTransformer, null),
                    payload;

                if(response.isSuccessful()){
                    if(Object.isArray(response.getPayload())){
                       if(response.getPayload().length === 1){
                           payload = EScreeningDashboardApp.models.SurveySectionTransformer.transformJSONPayload({surveySection: response.getPayload()[0].toJSON()}, null);
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
         * Convenience method that supports updating a Survey Section via the SurveySectionService.
         *
         * @param {{surveySectionId: String, surveySection: EScreeningDashboardApp.models.SurveySection, surveySections: String }}  saveSurveySectionRequestParameters Represents the save parameters for the request.
         */
        update = function (updateSurveySectionRequestParameter) {
        	
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
                    "services/:surveySections/:surveySectionId",
                    {},
                    {
                        save: {
                            method: 'PUT',
                            params: {
                                "surveySectionId": updateSurveySectionRequestParameter.surveySectionId,
                                "surveySections": updateSurveySectionRequestParameter.surveySections
                            },
                            isArray: false,
                            headers:{
                                'Accept': 'application/json',
                                'Content-Type':'application/json'
                            }
                        }
                    }
                );

            service.save(updateSurveySectionRequestParameter.surveySection.toJSON(), function (jsonResponse) {
                var response = handleSurveySectionSaveResponse(jsonResponse, EScreeningDashboardApp.models.SurveySectionTransformer);
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
         * Convenience method that supports creating a Survey Section via the SurveySectionService.
         *
         * @param {{surveySectionId: String, surveySection: EScreeningDashboardApp.models.SurveySection, surveySections: String }}  saveSurveySectionRequestParameters Represents the save parameters for the request.
         */
        create = function (updateSurveySectionRequestParameter) {

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
                    "services/:surveySection",
                    {},
                    {
                        save: {
                            method: 'PUT',
                            params: {
                                "surveySection": updateSurveySectionRequestParameter.surveySection
                            },
                            isArray: false,
                            headers:{
                                'Accept': 'application/json',
                                'Content-Type':'application/json'
                            }
                        }
                    }
                );

            service.save(updateSurveySectionRequestParameter.payload.toJSON(), function (jsonResponse) {
                var response = handleSurveySectionSaveResponse(jsonResponse, EScreeningDashboardApp.models.SurveySectionTransformer);
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
         * Convenience method that supports removing an Survey Section via the SurveySectionService.
         *
         * @param {{surveySectionId: string}}  saveAuditRequestParameters Represents the save parameters for the request.
         */
        remove = function (removeSurveySectionRequestParameters) {
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
                        "services/:surveySections/:surveySectionId",
                    {},
                    {
                        save: {
                            method: 'DELETE',
                            params: {
                                "surveySectionId": removeSurveySectionRequestParameters.surveySectionId
                            },
                            isArray: false
                        }
                    }
                );

            service.save(removeSurveySectionRequestParameters, function (jsonResponse) {
                var response = handleSurveySectionRemoveResponse(jsonResponse);
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
        };

        /**
         * Convenience method that sets the request parameter for the survey section update service request.
         *
         * @private
         * @method
         * @param {EScreeningDashboardApp.models.SurveySection} surveySection Represents the SurveySection domain object to update.
         * @returns {{surveySectionId: *, surveySections: String}} A search criteria object.
         */
        var setUpdateSurveySectionRequestParameter = function (surveySection) {
            var saveSurveySectionRequestParameter = {
                "surveySectionId": surveySection.getId(),
                "surveySection": surveySection,
                "surveySections": "surveySections"
            };

            if (!Object.isDefined(surveySection.getId())) {
                delete saveSurveySectionRequestParameter.surveySectionId;
                saveSurveySectionRequestParameter.surveySections = saveSurveySectionRequestParameter.surveySections + ".json";
            } else {
                saveSurveySectionRequestParameter.surveySectionId = saveSurveySectionRequestParameter.surveySectionId + ".json";
            }
            return saveSurveySectionRequestParameter;
        };

        /**
         * Convenience method that sets the request parameter for the survey section remove service request.
         *
         * @private
         * @method
         * @param {String} surveySectionId Represent the SurveySection id to save.
         * @returns {{surveySectionId: *, surveySections: String}} A search criteria object.
         */
        var setRemoveSurveySectionRequestParameter = function (surveySectionId) {
            var removeSurveySectionRequestParameter = {
                "surveySectionId": surveySectionId,
                "surveySections": "surveySections"
            };

            if (!Object.isDefined(surveySectionId)) {
                throw new BytePushers.exceptions.NullPointerException("surveySectionId parameter can not be null");
            }

            return removeSurveySectionRequestParameter;
        };

        /**
         * Convenience method that sets the request parameter for the survey section create service request.
         *
         * @private
         * @method
         * @param {EScreeningDashboardApp.models.SurveySection} surveySection Represents the SurveySection domain object to update.
         * @returns {{payload: EScreeningDashboardApp.models.SurveySection, surveySections: String}} A create parameter request object.
         */
        var setCreateSurveySectionRequestParameter = function (surveySection) {
            var createSurveySectionRequestParameter = {
                "payload": surveySection,
                "surveySection": "surveySection"
            };


            return createSurveySectionRequestParameter;
        };

        /**
         * Convenience method that sets the search criteria for the survey section search service query request.
         *
         * @private
         * @method
         * @param {String} surveySectionId Represent the user id to search for.
         * @returns {{surveySectionId: *, surveySections: String}} A search criteria object.
         */
        var setQuerySurveySectionSearchCriteria = function (surveySectionId) {
            var findSurveySectionSearchCriteria = {
                "surveySectionId": surveySectionId,
                "surveySections": "surveySections"
            };

            if (!Object.isDefined(surveySectionId)) {
                delete findSurveySectionSearchCriteria.surveySectionId;
                findSurveySectionSearchCriteria.surveySections = findSurveySectionSearchCriteria.surveySections + ".json";
            } else {
                findSurveySectionSearchCriteria.surveySectionId = findSurveySectionSearchCriteria.surveySectionId + ".json";
            }

            return findSurveySectionSearchCriteria;
        };

        /**
         * Convenience method that handles the Survey Section Service query response by returning a fully
         * transformed SurveySection payload object.
         *
         * @private
         * @method
         * @param {String} jsonResponse Represents the Restful Survey Section Service query response.
         * @param {EScreeningDashboardApp.models.SurveySectionsTransformer} jsonResponsePayloadTransformer Represents the Survey Sections JSON Response Payload transformer.
         * @param {String} userId Represents the User who made the service query request.
         * @returns {*} A transformed SurveySection payload object.
         * @throws {Error} If the jsonResponse can not be property transformed.
         */
        var handleSurveySectionQueryResponse = function (jsonResponse, jsonResponsePayloadTransformer, userId) {
            /**
             * Represents the response of a service call request.
             *
             * @private
             * @field
             * @type {EScreeningDashboardApp.models.Response}
             */
            var response = BytePushers.models.ResponseTransformer.transformJSONResponse(jsonResponse, jsonResponsePayloadTransformer, userId);

            if (response === null) {
                throw new Error("handleSurveySectionServiceQueryResponse() method: Response is null.");
            }

            return response;
        };

        /**
         * Convenience method that handles the Survey Section Service save response by returning a fully
         * transformed SurveySection payload object.
         *
         * @private
         * @method
         * @param {String} jsonResponse Represents the Restful Survey Section Service query response.
         * @param {EScreeningDashboardApp.models.SurveySectionsTransformer} jsonResponsePayloadTransformer Represents the Survey Sections JSON Response Payload transformer.
         * @param {String} userId Represents the User who made the service query request.
         * @returns {*} A transformed SurveySection payload object.
         * @throws {Error} If the jsonResponse can not be property transformed.
         */
        var handleSurveySectionSaveResponse = function (jsonResponse, jsonResponsePayloadTransformer, userId) {
            /**
             * Represents the response of a service call request.
             *
             * @private
             * @field
             * @type {EScreeningDashboardApp.models.Response}
             */
            var response = BytePushers.models.ResponseTransformer.transformJSONResponse(jsonResponse, jsonResponsePayloadTransformer, userId);

            if (response === null) {
                throw new Error("handleSurveySectionServiceQueryResponse() method: Response is null.");
            }

            return response;
        };

        /**
         * Convenience method that handles the Survey Section Service save response by returning a fully
         * transformed SurveySection payload object.
         *
         * @private
         * @method
         * @param {String} jsonResponse Represents the Restful Survey Section Service query response.
         * @param {String} userId Represents the User who made the service query request.
         * @returns {*} A transformed SurveySection payload object.
         * @throws {Error} If the jsonResponse can not be property transformed.
         */
        var handleSurveySectionRemoveResponse = function (jsonResponse, userId) {
            /**
             * Represents the response of a service call request.
             *
             * @private
             * @field
             * @type {EScreeningDashboardApp.models.Response}
             */
        	
        	if (!jsonResponse.status.status == 'succeeded'){
	            var response = BytePushers.models.ResponseTransformer.transformJSONResponse(jsonResponse, null, userId);
	
	            if (response === null) {
	                throw new Error("handleSurveySectionServiceQueryResponse() method: Response is null.");
	            }
        	}

            return response;
        };

        // Expose the public SurveySectionService API to the rest of the application.
        return {
            query: query,
            update: update,
            remove: remove,
            create: create,
            setCreateSurveySectionRequestParameter: setCreateSurveySectionRequestParameter,
            setQuerySurveySectionSearchCriteria: setQuerySurveySectionSearchCriteria,
            setUpdateSurveySectionRequestParameter: setUpdateSurveySectionRequestParameter,
            setRemoveSurveySectionRequestParameter: setRemoveSurveySectionRequestParameter
        };
    }]);
