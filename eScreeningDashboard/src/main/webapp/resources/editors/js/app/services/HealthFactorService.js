/**
 * Angular service factory method that creates the HealthFactorService object.
 *
 * @author Bryan Henderson
 */
angular.module('EscreeningDashboardApp.services.HealthFactor', ['ngResource'])
    .factory('HealthFactorService', ['$q', '$resource', function ($q, $resource){
        "use strict";
        /**
         * Convenience method that supports the querying of the HealthFactorService.
         *
         * @private
         * @method
         * @param {{userId: *, HealthFactorId: string}} queryHealthFactorSearchCriteria Represents the search criteria to query the HealthFactorService.
         * @returns {promise} A promise.
         */
        var query = function (queryHealthFactorSearchCriteria) {
            /**
             * Represents the angular $defer that is used for asynchronous service calls.
             *
             * @private
             * @field
             * @type {$defer}
             */
            var deferred = $q.defer(),
                service = $resource (
                    "services/:HealthFactors/:HealthFactorId",
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

            service.query(queryHealthFactorSearchCriteria, function (jsonResponse) {
                var existingHealthFactors = handleHealthFactorQueryResponse(jsonResponse, EScreeningDashboardApp.models.HealthFactorsTransformer, null);
                deferred.resolve(existingHealthFactors);
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
         * Convenience method that supports saving an Health Factor via the HealthFactorService.
         *
         * @param {{HealthFactor: EScreeningDashboardApp.models.HealthFactor, HealthFactors: String }}  createHealthFactorRequestParameters Represents the save parameters for the request.
         */
        create = function (createHealthFactorRequestParameter) {
        	
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
                    "services/:HealthFactors/?action=create",
                    {},
                    {
                        save: {
                            method: 'POST',
                            params: {
                                "HealthFactors": createHealthFactorRequestParameter.HealthFactors
                            },
                            isArray: false,
                            headers:{
                                'Accept': 'application/json',
                                'Content-Type':'application/json'
                            }
                        }
                    }
                );
            
            service.save(createHealthFactorRequestParameter.HealthFactor.toJSON(), function (jsonResponse) {
                var existingHealthFactor = handleHealthFactorSaveResponse(jsonResponse, EScreeningDashboardApp.models.HealthFactorTransformer);
                deferred.resolve(existingHealthFactor);
            }, function (reason) {
                deferred.reject(reason);
            });

            return deferred.promise;
        },
        /**
         * Convenience method that supports saving an Health Factor via the HealthFactorService.
         *
         * @param {{assessmentId: String, HealthFactor: EScreeningDashboardApp.models.HealthFactor, HealthFactors: String }}  saveHealthFactorRequestParameters Represents the save parameters for the request.
         */
        update = function (updateHealthFactorRequestParameter) {
        	
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
                    "services/:HealthFactors/:HealthFactorId",
                    {},
                    {
                        save: {
                            method: 'POST',
                            params: {
                                "HealthFactorId": updateHealthFactorRequestParameter.HealthFactorId,
                                "HealthFactors": updateHealthFactorRequestParameter.HealthFactors
                            },
                            isArray: false,
                            headers:{
                                'Accept': 'application/json',
                                'Content-Type':'application/json'
                            }
                        }
                    }
                );
            
            service.save(updateHealthFactorRequestParameter.HealthFactor.toJSON(), function (jsonResponse) {
                var existingHealthFactor = handleHealthFactorSaveResponse(jsonResponse, EScreeningDashboardApp.models.HealthFactorTransformer);
                deferred.resolve(existingHealthFactor);
            }, function (reason) {
                deferred.reject(reason);
            });

            return deferred.promise;
        },
        /**
         * Convenience method that supports removing an Health Factor via the HealthFactorService.
         *
         * @param {{HealthFactorId: string}}  removeHealthFactorRequestParameters Represents the save parameters for the request.
         */
        remove = function (removeHealthFactorRequestParameters) {
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
                        "services/:HealthFactors/:HealthFactorId.json?action=delete",
                    {},
                    {
                        save: {
                            method: 'POST',
                            params: {
                                "HealthFactorId": removeHealthFactorRequestParameters.HealthFactorId,
                                "HealthFactors": removeHealthFactorRequestParameters.HealthFactors
                            },
                            isArray: false
                        }
                    }
                );

            service.save(removeHealthFactorRequestParameters, function (jsonResponse) {
                var response = handleHealthFactorRemoveResponse(jsonResponse);
                deferred.resolve(response);
            }, function (reason) {
                deferred.reject(reason);
            });

            return deferred.promise;
        };
        
        /**
         * Convenience method that sets the request parameter for the Health Factor create service request.
         *
         * @private
         * @method
         * @param {EScreeningDashboardApp.models.HealthFactor} HealthFactor Represents the HealthFactor domain object to create.
         * @returns {{HealthFactors: String}} A search criteria object.
         */
        var setUpdateHealthFactorRequestParameter = function (HealthFactor) {
            var saveHealthFactorRequestParameter = {
                "HealthFactor": HealthFactor,
                "HealthFactors": "HealthFactors"
            };

            if (!Object.isDefined(HealthFactor.getId())) {
                delete saveHealthFactorRequestParameter.HealthFactorId;
                saveHealthFactorRequestParameter.HealthFactors = saveHealthFactorRequestParameter.HealthFactors + ".json";
            } else {
                saveHealthFactorRequestParameter.HealthFactorId = saveHealthFactorRequestParameter.HealthFactorId + ".json";
            }
            return saveHealthFactorRequestParameter;
        };

        /**
         * Convenience method that sets the request parameter for the Health Factor update service request.
         *
         * @private
         * @method
         * @param {EScreeningDashboardApp.models.HealthFactor} HealthFactor Represents the HealthFactor domain object to update.
         * @returns {{HealthFactorId: *, HealthFactors: String}} A search criteria object.
         */
        var setUpdateHealthFactorRequestParameter = function (HealthFactor) {
            var saveHealthFactorRequestParameter = {
                "HealthFactorId": HealthFactor.getId(),
                "HealthFactor": HealthFactor,
                "HealthFactors": "HealthFactors"
            };

            if (!Object.isDefined(HealthFactor.getId())) {
                delete saveHealthFactorRequestParameter.HealthFactorId;
                saveHealthFactorRequestParameter.HealthFactors = saveHealthFactorRequestParameter.HealthFactors + ".json";
            } else {
                saveHealthFactorRequestParameter.HealthFactorId = saveHealthFactorRequestParameter.HealthFactorId + ".json";
            }
            return saveHealthFactorRequestParameter;
        };

        /**
         * Convenience method that sets the request parameter for the Health Factor remove service request.
         *
         * @private
         * @method
         * @param {String} HealthFactorId Represent the HealthFactor id to save.
         * @returns {{HealthFactorId: *, HealthFactors: String}} A search criteria object.
         */
        var setRemoveHealthFactorRequestParameter = function (HealthFactorId) {
            var removeHealthFactorRequestParameter = {
                "HealthFactorId": HealthFactorId,
                "HealthFactors": "HealthFactors"
            };

            if (!Object.isDefined(HealthFactorId)) {
                throw new BytePushers.exceptions.NullPointerException("HealthFactorId parameter can not be null");
            }

            return removeHealthFactorRequestParameter;
        };

        /**
         * Convenience method that sets the search criteria for the Health Factor search service query request.
         *
         * @private
         * @method
         * @param {String} HealthFactorId Represent the Health Factor id to search for.
         * @returns {{HealthFactorId: *, HealthFactors: String}} A search criteria object.
         */
        var setQueryHealthFactorSearchCriteria = function (HealthFactorId) {
            var findHealthFactorSearchCriteria = {
                "HealthFactorId": HealthFactorId,
                "HealthFactors": "HealthFactors"
            };

            if (!Object.isDefined(HealthFactorId)) {
                delete findHealthFactorSearchCriteria.HealthFactorId;
                findHealthFactorSearchCriteria.HealthFactors = findHealthFactorSearchCriteria.HealthFactors + ".json";
            } else {
                findHealthFactorSearchCriteria.HealthFactorId = findHealthFactorSearchCriteria.HealthFactorId + ".json";
            }

            return findHealthFactorSearchCriteria;
        };

        /**
         * Convenience method that handles the Health Factor Service query response by returning a fully
         * transformed HealthFactor payload object.
         *
         * @private
         * @method
         * @param {String} jsonResponse Represents the Restful Health Factor Service query response.
         * @param {EScreeningDashboardApp.models.HealthFactorsTransformer} jsonResponsePayloadTransformer Represents the Health Factors JSON Response Payload transformer.
         * @param {String} userId Represents the User who made the service query request.
         * @returns {*} A transformed HealthFactor payload object.
         * @throws {Error} If the jsonResponse can not be property transformed.
         */
        var handleHealthFactorQueryResponse = function (jsonResponse, jsonResponsePayloadTransformer, userId) {
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
                throw new Error("handleHealthFactorServiceQueryResponse() method: Response is null.");
            }

            return payload;
        };

        /**
         * Convenience method that handles the Health Factor Service save response by returning a fully
         * transformed HealthFactor payload object.
         *
         * @private
         * @method
         * @param {String} jsonResponse Represents the Restful Health Factor Service query response.
         * @param {EScreeningDashboardApp.models.HealthFactorsTransformer} jsonResponsePayloadTransformer Represents the Health Factors JSON Response Payload transformer.
         * @param {String} userId Represents the User who made the service query request.
         * @returns {*} A transformed HealthFactor payload object.
         * @throws {Error} If the jsonResponse can not be property transformed.
         */
        var handleHealthFactorSaveResponse = function (jsonResponse, jsonResponsePayloadTransformer, userId) {
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
                throw new Error("handleHealthFactorServiceQueryResponse() method: Response is null.");
            }

            return payload;
        };

        /**
         * Convenience method that handles the Health Factor Service save response by returning a fully
         * transformed HealthFactor payload object.
         *
         * @private
         * @method
         * @param {String} jsonResponse Represents the Restful Health Factor Service query response.
         * @param {String} userId Represents the User who made the service query request.
         * @returns {*} A transformed HealthFactor payload object.
         * @throws {Error} If the jsonResponse can not be property transformed.
         */
        var handleHealthFactorRemoveResponse = function (jsonResponse, userId) {
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
                throw new Error("handleHealthFactorServiceQueryResponse() method: Response is null.");
            }

            return response;
        };

        // Expose the public HealthFactorService API to the rest of the application.
        return {
            query: query,
            create:create,
            update: update,
            remove: remove,
            setQueryHealthFactorSearchCriteria: setQueryHealthFactorSearchCriteria,
            setCreateHealthFactorSearchCriteria:setCreateHealthFactorSearchCriteria,
            setUpdateHealthFactorRequestParameter: setUpdateHealthFactorRequestParameter,
            setRemoveHealthFactorRequestParameter: setRemoveHealthFactorRequestParameter
        };
    }]);