/**
 * Angular service factory method that creates the AssessmentVariableService object.
 *
 * @author Bryan Henderson
 */
angular.module('EscreeningDashboardApp.services.assessmentVariable', ['ngResource'])
    .factory('AssessmentVariableService', ['$q', '$resource', function ($q, $resource){
        "use strict";
        /**
         * Convenience method that supports the querying of the AssessmentVariableService.
         *
         * @private
         * @method
         * @param {{userId: *, assessmentVariableId: string}} queryAssessmentVariableSearchCriteria Represents the search criteria to query the AssessmentVariableService.
         * @returns {promise} A promise.
         */
        var query = function (queryAssessmentVariableSearchCriteria) {
            /**
             * Represents the angular $defer that is used for asynchronous service calls.
             *
             * @private
             * @field
             * @type {$defer}
             */
            var deferred = $q.defer(),
                service = $resource (
                    "services/:assessmentVariables/:assessmentVariableId",
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

            service.query(queryAssessmentVariableSearchCriteria, function (jsonResponse) {
                var existingAssessmentVariables = handleAssessmentVariableQueryResponse(jsonResponse, EScreeningDashboardApp.models.AssessmentVariablesTransformer, null);
                deferred.resolve(existingAssessmentVariables);
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
         * Convenience method that supports saving an Assessment Variable via the AssessmentVariableService.
         *
         * @param {{assessmentVariable: EScreeningDashboardApp.models.AssessmentVariable, assessmentVariables: String }}  createAssessmentVariableRequestParameters Represents the save parameters for the request.
         */
        create = function (createAssessmentVariableRequestParameter) {
        	
            /**
             * Represents the angular $defer that is used for asynchronous service calls.
             *
             * @private
             * @field
             * @type {$defer}
             */
            var deferred = $q.defer(),
                /**
                 * Represents the angular $resource object that is used for asynchronous service calls.
                 *
                 * @private
                 * @field
                 * @type {$resource}
                 */
                service = $resource(
                    "services/:assessmentVariables/?action=create",
                    {},
                    {
                        save: {
                            method: 'POST',
                            params: {
                                "assessmentVariables": createAssessmentVariableRequestParameter.assessmentVariables
                            },
                            isArray: false,
                            headers:{
                                'Accept': 'application/json',
                                'Content-Type':'application/json'
                            }
                        }
                    }
                );
            
            service.save(createAssessmentVariableRequestParameter.assessmentVariable.toJSON(), function (jsonResponse) {
                var existingAssessmentVariable = handleAssessmentVariableSaveResponse(jsonResponse, EScreeningDashboardApp.models.AssessmentVariableTransformer);
                deferred.resolve(existingAssessmentVariable);
            }, function (reason) {
                deferred.reject(reason);
            });

            return deferred.promise;
        },
        /**
         * Convenience method that supports saving an Assessment Variable via the AssessmentVariableService.
         *
         * @param {{assessmentId: String, assessmentVariable: EScreeningDashboardApp.models.AssessmentVariable, assessmentVariables: String }}  saveAssessmentVariableRequestParameters Represents the save parameters for the request.
         */
        update = function (updateAssessmentVariableRequestParameter) {
        	
            /**
             * Represents the angular $defer that is used for asynchronous service calls.
             *
             * @private
             * @field
             * @type {$defer}
             */
            var deferred = $q.defer(),
                /**
                 * Represents the angular $resource object that is used for asynchronous service calls.
                 *
                 * @private
                 * @field
                 * @type {$resource}
                 */
                service = $resource(
                    "services/:assessmentVariables/:assessmentVariableId",
                    {},
                    {
                        save: {
                            method: 'POST',
                            params: {
                                "assessmentVariableId": updateAssessmentVariableRequestParameter.assessmentVariableId,
                                "assessmentVariables": updateAssessmentVariableRequestParameter.assessmentVariables
                            },
                            isArray: false,
                            headers:{
                                'Accept': 'application/json',
                                'Content-Type':'application/json'
                            }
                        }
                    }
                );
            
            service.save(updateAssessmentVariableRequestParameter.assessmentVariable.toJSON(), function (jsonResponse) {
                var existingAssessmentVariable = handleAssessmentVariableSaveResponse(jsonResponse, EScreeningDashboardApp.models.AssessmentVariableTransformer);
                deferred.resolve(existingAssessmentVariable);
            }, function (reason) {
                deferred.reject(reason);
            });

            return deferred.promise;
        },
        /**
         * Convenience method that supports removing an Assessment Variable via the AssessmentVariableService.
         *
         * @param {{assessmentVariableId: string}}  removeAssessmentVariableRequestParameters Represents the save parameters for the request.
         */
        remove = function (removeAssessmentVariableRequestParameters) {
            /**
             * Represents the angular $defer that is used for asynchronous service calls.
             *
             * @private
             * @field
             * @type {$defer}
             */
            var deferred = $q.defer(),
                /**
                 * Represents the angular $resource object that is used for asynchronous service calls.
                 *
                 * @private
                 * @field
                 * @type {$resource}
                 */
                service = $resource(
                        "services/:assessmentVariables/:assessmentVariableId.json?action=delete",
                    {},
                    {
                        save: {
                            method: 'POST',
                            params: {
                                "assessmentVariableId": removeAssessmentVariableRequestParameters.assessmentVariableId,
                                "assessmentVariables": removeAssessmentVariableRequestParameters.assessmentVariables
                            },
                            isArray: false
                        }
                    }
                );

            service.save(removeAssessmentVariableRequestParameters, function (jsonResponse) {
                var response = handleAssessmentVariableRemoveResponse(jsonResponse);
                deferred.resolve(response);
            }, function (reason) {
                deferred.reject(reason);
            });

            return deferred.promise;
        };
        
        /**
         * Convenience method that sets the request parameter for the Assessment Variable create service request.
         *
         * @private
         * @method
         * @param {EScreeningDashboardApp.models.AssessmentVariable} assessmentVariable Represents the AssessmentVariable domain object to create.
         * @returns {{assessmentVariables: String}} A search criteria object.
         */
        var setUpdateAssessmentVariableRequestParameter = function (assessmentVariable) {
            var saveAssessmentVariableRequestParameter = {
                "assessmentVariable": assessmentVariable,
                "assessmentVariables": "assessmentVariables"
            };

            if (!Object.isDefined(assessmentVariable.getId())) {
                delete saveAssessmentVariableRequestParameter.assessmentVariableId;
                saveAssessmentVariableRequestParameter.assessmentVariables = saveAssessmentVariableRequestParameter.assessmentVariables + ".json";
            } else {
                saveAssessmentVariableRequestParameter.assessmentVariableId = saveAssessmentVariableRequestParameter.assessmentVariableId + ".json";
            }
            return saveAssessmentVariableRequestParameter;
        };

        /**
         * Convenience method that sets the request parameter for the Assessment Variable update service request.
         *
         * @private
         * @method
         * @param {EScreeningDashboardApp.models.AssessmentVariable} assessmentVariable Represents the AssessmentVariable domain object to update.
         * @returns {{assessmentVariableId: *, assessmentVariables: String}} A search criteria object.
         */
        var setUpdateAssessmentVariableRequestParameter = function (assessmentVariable) {
            var saveAssessmentVariableRequestParameter = {
                "assessmentVariableId": assessmentVariable.getId(),
                "assessmentVariable": assessmentVariable,
                "assessmentVariables": "assessmentVariables"
            };

            if (!Object.isDefined(assessmentVariable.getId())) {
                delete saveAssessmentVariableRequestParameter.assessmentVariableId;
                saveAssessmentVariableRequestParameter.assessmentVariables = saveAssessmentVariableRequestParameter.assessmentVariables + ".json";
            } else {
                saveAssessmentVariableRequestParameter.assessmentVariableId = saveAssessmentVariableRequestParameter.assessmentVariableId + ".json";
            }
            return saveAssessmentVariableRequestParameter;
        };

        /**
         * Convenience method that sets the request parameter for the Assessment Variable remove service request.
         *
         * @private
         * @method
         * @param {String} assessmentVariableId Represent the AssessmentVariable id to save.
         * @returns {{assessmentVariableId: *, AssessmentVariables: String}} A search criteria object.
         */
        var setRemoveAssessmentVariableRequestParameter = function (assessmentVariableId) {
            var removeAssessmentVariableRequestParameter = {
                "assessmentVariableId": assessmentVariableId,
                "assessmentVariables": "assessmentVariables"
            };

            if (!Object.isDefined(assessmentVariableId)) {
                throw new BytePushers.exceptions.NullPointerException("assessmentVariableId parameter can not be null");
            }

            return removeAssessmentVariableRequestParameter;
        };

        /**
         * Convenience method that sets the search criteria for the Assessment Variable search service query request.
         *
         * @private
         * @method
         * @param {String} assessmentVariableId Represent the Assessment Variable id to search for.
         * @returns {{assessmentVariableId: *, assessmentVariables: String}} A search criteria object.
         */
        var setQueryAssessmentVariableSearchCriteria = function (assessmentVariableId) {
            var findAssessmentVariableSearchCriteria = {
                "assessmentVariableId": assessmentVariableId,
                "assessmentVariables": "assessmentVariables"
            };

            if (!Object.isDefined(assessmentVariableId)) {
                delete findAssessmentVariableSearchCriteria.assessmentVariableId;
                findAssessmentVariableSearchCriteria.assessmentVariables = findAssessmentVariableSearchCriteria.assessmentVariables + ".json";
            } else {
                findAssessmentVariableSearchCriteria.assessmentVariableId = findAssessmentVariableSearchCriteria.assessmentVariableId + ".json";
            }

            return findAssessmentVariableSearchCriteria;
        };

        /**
         * Convenience method that handles the Assessment Variable Service query response by returning a fully
         * transformed AssessmentVariable payload object.
         *
         * @private
         * @method
         * @param {String} jsonResponse Represents the Restful Assessment Variable Service query response.
         * @param {EScreeningDashboardApp.models.AssessmentVariablesTransformer} jsonResponsePayloadTransformer Represents the Assessment Variables JSON Response Payload transformer.
         * @param {String} userId Represents the User who made the service query request.
         * @returns {*} A transformed AssessmentVariable payload object.
         * @throws {Error} If the jsonResponse can not be property transformed.
         */
        var handleAssessmentVariableQueryResponse = function (jsonResponse, jsonResponsePayloadTransformer, userId) {
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
                throw new Error("handleAssessmentVariableServiceQueryResponse() method: Response is null.");
            }

            return payload;
        };

        /**
         * Convenience method that handles the Assessment Variable Service save response by returning a fully
         * transformed AssessmentVariable payload object.
         *
         * @private
         * @method
         * @param {String} jsonResponse Represents the Restful Assessment Variable Service query response.
         * @param {EScreeningDashboardApp.models.AssessmentVariablesTransformer} jsonResponsePayloadTransformer Represents the Assessment Variables JSON Response Payload transformer.
         * @param {String} userId Represents the User who made the service query request.
         * @returns {*} A transformed AssessmentVariable payload object.
         * @throws {Error} If the jsonResponse can not be property transformed.
         */
        var handleAssessmentVariableSaveResponse = function (jsonResponse, jsonResponsePayloadTransformer, userId) {
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
                throw new Error("handleAssessmentVariableServiceQueryResponse() method: Response is null.");
            }

            return payload;
        };

        /**
         * Convenience method that handles the Assessment Variable Service save response by returning a fully
         * transformed AssessmentVariable payload object.
         *
         * @private
         * @method
         * @param {String} jsonResponse Represents the Restful Assessment Variable Service query response.
         * @param {String} userId Represents the User who made the service query request.
         * @returns {*} A transformed AssessmentVariable payload object.
         * @throws {Error} If the jsonResponse can not be property transformed.
         */
        var handleAssessmentVariableRemoveResponse = function (jsonResponse, userId) {
            /**
             * Represents the response of a service call request.
             *
             * @private
             * @field
             * @type {EScreeningDashboardApp.models.Response}
             */
            var response = BytePushers.models.ResponseTransformer.transformJSONResponse(jsonResponse, null, userId),
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
                throw new Error("handleAssessmentVariableServiceQueryResponse() method: Response is null.");
            }

            return response;
        };

        // Expose the public AssessmentVariableService API to the rest of the application.
        return {
            query: query,
            create:create,
            update: update,
            remove: remove,
            setQueryAssessmentVariableSearchCriteria: setQueryAssessmentVariableSearchCriteria,
            setCreateAssessmentVariableSearchCriteria:setCreateAssessmentVariableSearchCriteria,
            setUpdateAssessmentVariableRequestParameter: setUpdateAssessmentVariableRequestParameter,
            setRemoveAssessmentVariableRequestParameter: setRemoveAssessmentVariableRequestParameter
        };
    }]);