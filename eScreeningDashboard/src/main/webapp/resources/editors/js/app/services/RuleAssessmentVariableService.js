/**
 * Angular service factory method that creates the RuleService object.
 *
 * @author Bryan Henderson
 */
angular.module('EscreeningDashboardApp.services.ruleAssessmentVariable', ['ngResource'])
    .factory('RuleService', ['$q', '$resource', function ($q, $resource){
        "use strict";
        /**
         * Convenience method that supports the querying of the RuleAssessmentVariableService.
         *
         * @private
         * @method
         * @param {{userId: *, RuleId: string}} queryRuleAssessmentVariableSearchCriteria Represents the search criteria to query the RuleAssessmentVariableService.
         * @returns {promise} A promise.
         */
        var query = function (queryRuleAssessmentVariableSearchCriteria) {
            /**
             * Represents the angular $defer that is used for asynchronous service calls.
             *
             * @private
             * @field
             * @type {$defer}
             */
            var deferred = $q.defer(),
                service = $resource (
                    "services/:RulesAssessmentVariable/:RuleAssessmentVariableId",
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

            service.query(queryRuleAssessmentVariableSearchCriteria, function (jsonResponse) {
                var existingRules = handleRuleAssessmentVariableQueryResponse(jsonResponse, EScreeningDashboardApp.models.RulesAssessmentVariableTransformer, null);
                deferred.resolve(existingRuleAssessmentVariables);
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
         * Convenience method that supports saving an Rule Assessment Variable via the RuleAssessmentVariableService.
         *
         * @param {{Rule: EScreeningDashboardApp.models.RuleAssessmentVariable, RuleAssessmentVariables: String }}  createRuleAssessmentVariableRequestParameters Represents the save parameters for the request.
         */
        create = function (createRuleAssessmentVariableRequestParameter) {
        	
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
                    "services/:RulesAssessmentVariables/?action=create",
                    {},
                    {
                        save: {
                            method: 'POST',
                            params: {
                                "Rules": createRuleAssessmentVariablesRequestParameter.RuleAssessmentVariable
                            },
                            isArray: false,
                            headers:{
                                'Accept': 'application/json',
                                'Content-Type':'application/json'
                            }
                        }
                    }
                );
            
            service.save(createRuleAssessmentVariableRequestParameter.RuleAssessmentVariable.toJSON(), function (jsonResponse) {
                var existingRuleAssessmentVariable = handleRuleAssessmentVariableSaveResponse(jsonResponse, EScreeningDashboardApp.models.RuleAssessmentVariableTransformer);
                deferred.resolve(existingRuleAssessmentVariable);
            }, function (reason) {
                deferred.reject(reason);
            });

            return deferred.promise;
        },
        /**
         * Convenience method that supports saving an Rule Assessment Variable via the RuleAssessmentVariableService.
         *
         * @param {{assessmentId: String, Rule: EScreeningDashboardApp.models.RuleAssessmentVariable, RuleAssessmentVariables: String }}  saveRuleAssessmentVariableRequestParameters Represents the save parameters for the request.
         */
        update = function (updateRuleAssessmentVariableRequestParameter) {
        	
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
                    "services/:RuleAssessmentVariables/:RuleAssessmentVariableId",
                    {},
                    {
                        save: {
                            method: 'POST',
                            params: {
                                "RuleId": updateRuleAssessmentVariableRequestParameter.RuleAssessmentVariableId,
                                "Rules": updateRuleAssessmentVariableRequestParameter.RuleAssessmentVariables
                            },
                            isArray: false,
                            headers:{
                                'Accept': 'application/json',
                                'Content-Type':'application/json'
                            }
                        }
                    }
                );
            
            service.save(updateRuleAssessmentVariableRequestParameter.RuleAssessmentVariable.toJSON(), function (jsonResponse) {
                var existingRuleAssessmentVariable = handleRuleAssessmentVariableSaveResponse(jsonResponse, EScreeningDashboardApp.models.RuleAssessmentVariableTransformer);
                deferred.resolve(existingRuleAssessmentVariable);
            }, function (reason) {
                deferred.reject(reason);
            });

            return deferred.promise;
        },
        /**
         * Convenience method that supports removing an Rule Assessment Variable via the RuleAssessmentVariableService.
         *
         * @param {{RuleAssessmentVariableId: string}}  removeRuleAssessmentVariableRequestParameters Represents the save parameters for the request.
         */
        remove = function (removeRuleAssessmentVariableRequestParameters) {
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
                        "services/:RuleAssessmentVariables/:RuleAssessmentVariableId.json?action=delete",
                    {},
                    {
                        save: {
                            method: 'POST',
                            params: {
                                "RuleId": removeRuleAssessmentVariableRequestParameters.RuleAssessmentVariableId,
                                "Rules": removeRuleAssessmentVariableRequestParameters.RuleAssessmentVariables
                            },
                            isArray: false
                        }
                    }
                );

            service.save(removeRuleAssessmentVariableRequestParameters, function (jsonResponse) {
                var response = handleRuleAssessmentVariableRemoveResponse(jsonResponse);
                deferred.resolve(response);
            }, function (reason) {
                deferred.reject(reason);
            });

            return deferred.promise;
        };
        
        /**
         * Convenience method that sets the request parameter for the Rule Assessment Variable create service request.
         *
         * @private
         * @method
         * @param {EScreeningDashboardApp.models.RuleAssessmentVariable} RuleAssessmentVariable Represents the RuleAssessmentVariable domain object to create.
         * @returns {{RuleAssessmentVariables: String}} A search criteria object.
         */
        var setUpdateRuleAssessmentVariableRequestParameter = function (RuleAssessmentVariable) {
            var saveRuleAssessmentVariableRequestParameter = {
                "RuleAssessmentVariable": Rule,
                "RuleAssessmentVariables": "RuleAssessmentVariables"
            };

            if (!Object.isDefined(RuleAssessmentVariable.getId())) {
                delete saveRuleAssessmentVariableRequestParameter.RuleAssessmentVariableId;
                saveRuleAssessmentVariableRequestParameter.RuleAssessmentVariables = saveRuleAssessmentVariableRequestParameter.RuleAssessmentVariables + ".json";
            } else {
                saveRuleAssessmentVariableRequestParameter.RuleAssessmentVariableId = saveRuleAssessmentVariableRequestParameter.RuleAssessmentVariableId + ".json";
            }
            return saveRuleAssessmentVariableRequestParameter;
        };

        /**
         * Convenience method that sets the request parameter for the Rule Assessment Variable update service request.
         *
         * @private
         * @method
         * @param {EScreeningDashboardApp.models.RuleAssessmentVariable} RuleAssessmentVariable Represents the RuleAssessmentVariable domain object to update.
         * @returns {{RuleAssessmentVariableId: *, RuleAssessmentVariables: String}} A search criteria object.
         */
        var setUpdateRuleAssessmentVariableRequestParameter = function (RuleAssessmentVariable) {
            var saveRuleAssessmentVariableRequestParameter = {
                "RuleAssessmentVariableId": RuleAssessmentVariable.getId(),
                "RuleAssessmentVariable": RuleAssessmentVariable,
                "RuleAssessmentVariables": "RuleAssessmentVariables"
            };

            if (!Object.isDefined(RuleAssessmentVariable.getId())) {
                delete saveRuleAssessmentVariableRequestParameter.RuleAssessmentVariableId;
                saveRuleAssessmentVariableRequestParameter.RuleAssessmentVariables = saveRuleAssessmentVariableRequestParameter.RuleAssessmentVariables + ".json";
            } else {
                saveRuleAssessmentVariableRequestParameter.RuleAssessmentVariableId = saveRuleAssessmentVariableRequestParameter.RuleAssessmentVariableId + ".json";
            }
            return saveRuleAssessmentVariableRequestParameter;
        };

        /**
         * Convenience method that sets the request parameter for the Rule Assessment Variable remove service request.
         *
         * @private
         * @method
         * @param {String} RuleAssessmentVariableId Represent the RuleAssessmentVariable id to save.
         * @returns {{RuleAssessmentVariableId: *, RuleAssessmentVariables: String}} A search criteria object.
         */
        var setRemoveRuleAssessmentVariableRequestParameter = function (RuleAssessmentVariableId) {
            var removeRuleAssessmentVariableRequestParameter = {
                "RuleAssessmentVariableId": RuleAssessmentVariableId,
                "RuleAssessmentVariables": "RuleAssessmentVariables"
            };

            if (!Object.isDefined(RuleAssessmentVariableId)) {
                throw new BytePushers.exceptions.NullPointerException("RuleAssessmentVariableId parameter can not be null");
            }

            return removeRuleAssessmentVariableRequestParameter;
        };

        /**
         * Convenience method that sets the search criteria for the Rule Assessment Variable search service query request.
         *
         * @private
         * @method
         * @param {String} RuleAssessmentVariableId Represent the Rule Assessment Variable id to search for.
         * @returns {{RuleAssessmentVariableId: *, RuleAssessmentVariables: String}} A search criteria object.
         */
        var setQueryRuleAssessmentVariableSearchCriteria = function (RuleAssessmentVariableId) {
            var findRuleAssessmentVariableSearchCriteria = {
                "RuleAssessmentVariableId": RuleAssessmentVariableId,
                "RuleAssessmentVariables": "RuleAssessmentVariables"
            };

            if (!Object.isDefined(RuleAssessmentVariableId)) {
                delete findRuleAssessmentVariableSearchCriteria.RuleAssessmentVariableId;
                findRuleAssessmentVariableSearchCriteria.RuleAssessmentVariables = findRuleAssessmentVariableSearchCriteria.RuleAssessmentVariables + ".json";
            } else {
                findRuleAssessmentVariableSearchCriteria.RuleAssessmentVariableId = findRuleAssessmentVariableSearchCriteria.RuleAssessmentVariableId + ".json";
            }

            return findRuleAssessmentVariableSearchCriteria;
        };

        /**
         * Convenience method that handles the Rule Assessment Variable Service query response by returning a fully
         * transformed Rule Assessment Variable payload object.
         *
         * @private
         * @method
         * @param {String} jsonResponse Represents the Restful Rule Assessment Variable Service query response.
         * @param {EScreeningDashboardApp.models.RuleAssessmentVariablesTransformer} jsonResponsePayloadTransformer Represents the Rule Assessment Variables JSON Response Payload transformer.
         * @param {String} userId Represents the User who made the service query request.
         * @returns {*} A transformed Rule Assessment Variable payload object.
         * @throws {Error} If the jsonResponse can not be property transformed.
         */
        var handleRuleAssessmentVariableQueryResponse = function (jsonResponse, jsonResponsePayloadTransformer, userId) {
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
                throw new Error("handleRuleAssessmentVariableServiceQueryResponse() method: Response is null.");
            }

            return payload;
        };

        /**
         * Convenience method that handles the Rule Assessment Variable Service save response by returning a fully
         * transformed Rule Assessment Variable payload object.
         *
         * @private
         * @method
         * @param {String} jsonResponse Represents the Restful Rule Assessment Variable Service query response.
         * @param {EScreeningDashboardApp.models.RuleAssessmentVariablesTransformer} jsonResponsePayloadTransformer Represents the Rule Assessment Variables JSON Response Payload transformer.
         * @param {String} userId Represents the User who made the service query request.
         * @returns {*} A transformed Rule Assessment Variable payload object.
         * @throws {Error} If the jsonResponse can not be property transformed.
         */
        var handleRuleAssessmentVariableSaveResponse = function (jsonResponse, jsonResponsePayloadTransformer, userId) {
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
                throw new Error("handleRuleAssessmentVariableServiceQueryResponse() method: Response is null.");
            }

            return payload;
        };

        /**
         * Convenience method that handles the Rule Assessment Variable Service save response by returning a fully
         * transformed Rule Assessment Variable payload object.
         *
         * @private
         * @method
         * @param {String} jsonResponse Represents the Restful Rule Assessment Variable Service query response.
         * @param {String} userId Represents the User who made the service query request.
         * @returns {*} A transformed Rule Assessment Variable payload object.
         * @throws {Error} If the jsonResponse can not be property transformed.
         */
        var handleRuleAssessmentVariableRemoveResponse = function (jsonResponse, userId) {
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
                throw new Error("handleRuleAssessmentVariableServiceQueryResponse() method: Response is null.");
            }

            return response;
        };

        // Expose the public RuleAssessmentVariableService API to the rest of the application.
        return {
            query: query,
            create:create,
            update: update,
            remove: remove,
            setQueryRuleAssessmentVariableSearchCriteria: setQueryRuleAssessmentVariableSearchCriteria,
            setCreateRuleAssessmentVariableSearchCriteria:setCreateRuleAssessmentVariableSearchCriteria,
            setUpdateRuleAssessmentVariableRequestParameter: setUpdateRuleAssessmentVariableRequestParameter,
            setRemoveRuleAssessmentVariableRequestParameter: setRemoveRuleAssessmentVariableRequestParameter
        };
    }]);