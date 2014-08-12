/**
 * Angular service factory method that creates the RuleService object.
 *
 * @author Bryan Henderson
 */
angular.module('EscreeningDashboardApp.services.rule', ['ngResource'])
    .factory('RuleService', ['$q', '$resource', function ($q, $resource){
        "use strict";
        /**
         * Convenience method that supports the querying of the RuleService.
         *
         * @private
         * @method
         * @param {{userId: *, RuleId: string}} queryRuleSearchCriteria Represents the search criteria to query the RuleService.
         * @returns {promise} A promise.
         */
        var query = function (queryRuleSearchCriteria) {
            /**
             * Represents the angular $defer that is used for asynchronous service calls.
             *
             * @private
             * @field
             * @type {$defer}
             */
            var deferred = $q.defer(),
                service = $resource (
                    "services/:Rules/:RuleId",
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

            service.query(queryRuleSearchCriteria, function (jsonResponse) {
                var existingRules = handleRuleQueryResponse(jsonResponse, EScreeningDashboardApp.models.RulesTransformer, null);
                deferred.resolve(existingRules);
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
         * Convenience method that supports saving an Assessment Variable via the RuleService.
         *
         * @param {{Rule: EScreeningDashboardApp.models.Rule, Rules: String }}  createRuleRequestParameters Represents the save parameters for the request.
         */
        create = function (createRuleRequestParameter) {
        	
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
                    "services/:Rules/?action=create",
                    {},
                    {
                        save: {
                            method: 'POST',
                            params: {
                                "Rules": createRuleRequestParameter.Rules
                            },
                            isArray: false,
                            headers:{
                                'Accept': 'application/json',
                                'Content-Type':'application/json'
                            }
                        }
                    }
                );
            
            service.save(createRuleRequestParameter.Rule.toJSON(), function (jsonResponse) {
                var existingRule = handleRuleSaveResponse(jsonResponse, EScreeningDashboardApp.models.RuleTransformer);
                deferred.resolve(existingRule);
            }, function (reason) {
                deferred.reject(reason);
            });

            return deferred.promise;
        },
        /**
         * Convenience method that supports saving an Assessment Variable via the RuleService.
         *
         * @param {{assessmentId: String, Rule: EScreeningDashboardApp.models.Rule, Rules: String }}  saveRuleRequestParameters Represents the save parameters for the request.
         */
        update = function (updateRuleRequestParameter) {
        	
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
                    "services/:Rules/:RuleId",
                    {},
                    {
                        save: {
                            method: 'POST',
                            params: {
                                "RuleId": updateRuleRequestParameter.RuleId,
                                "Rules": updateRuleRequestParameter.Rules
                            },
                            isArray: false,
                            headers:{
                                'Accept': 'application/json',
                                'Content-Type':'application/json'
                            }
                        }
                    }
                );
            
            service.save(updateRuleRequestParameter.Rule.toJSON(), function (jsonResponse) {
                var existingRule = handleRuleSaveResponse(jsonResponse, EScreeningDashboardApp.models.RuleTransformer);
                deferred.resolve(existingRule);
            }, function (reason) {
                deferred.reject(reason);
            });

            return deferred.promise;
        },
        /**
         * Convenience method that supports removing an Assessment Variable via the RuleService.
         *
         * @param {{RuleId: string}}  removeRuleRequestParameters Represents the save parameters for the request.
         */
        remove = function (removeRuleRequestParameters) {
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
                        "services/:Rules/:RuleId.json?action=delete",
                    {},
                    {
                        save: {
                            method: 'POST',
                            params: {
                                "RuleId": removeRuleRequestParameters.RuleId,
                                "Rules": removeRuleRequestParameters.Rules
                            },
                            isArray: false
                        }
                    }
                );

            service.save(removeRuleRequestParameters, function (jsonResponse) {
                var response = handleRuleRemoveResponse(jsonResponse);
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
         * @param {EScreeningDashboardApp.models.Rule} Rule Represents the Rule domain object to create.
         * @returns {{Rules: String}} A search criteria object.
         */
        var setUpdateRuleRequestParameter = function (Rule) {
            var saveRuleRequestParameter = {
                "Rule": Rule,
                "Rules": "Rules"
            };

            if (!Object.isDefined(Rule.getId())) {
                delete saveRuleRequestParameter.RuleId;
                saveRuleRequestParameter.Rules = saveRuleRequestParameter.Rules + ".json";
            } else {
                saveRuleRequestParameter.RuleId = saveRuleRequestParameter.RuleId + ".json";
            }
            return saveRuleRequestParameter;
        };

        /**
         * Convenience method that sets the request parameter for the Assessment Variable update service request.
         *
         * @private
         * @method
         * @param {EScreeningDashboardApp.models.Rule} Rule Represents the Rule domain object to update.
         * @returns {{RuleId: *, Rules: String}} A search criteria object.
         */
        var setUpdateRuleRequestParameter = function (Rule) {
            var saveRuleRequestParameter = {
                "RuleId": Rule.getId(),
                "Rule": Rule,
                "Rules": "Rules"
            };

            if (!Object.isDefined(Rule.getId())) {
                delete saveRuleRequestParameter.RuleId;
                saveRuleRequestParameter.Rules = saveRuleRequestParameter.Rules + ".json";
            } else {
                saveRuleRequestParameter.RuleId = saveRuleRequestParameter.RuleId + ".json";
            }
            return saveRuleRequestParameter;
        };

        /**
         * Convenience method that sets the request parameter for the Assessment Variable remove service request.
         *
         * @private
         * @method
         * @param {String} RuleId Represent the Rule id to save.
         * @returns {{RuleId: *, Rules: String}} A search criteria object.
         */
        var setRemoveRuleRequestParameter = function (RuleId) {
            var removeRuleRequestParameter = {
                "RuleId": RuleId,
                "Rules": "Rules"
            };

            if (!Object.isDefined(RuleId)) {
                throw new BytePushers.exceptions.NullPointerException("RuleId parameter can not be null");
            }

            return removeRuleRequestParameter;
        };

        /**
         * Convenience method that sets the search criteria for the Assessment Variable search service query request.
         *
         * @private
         * @method
         * @param {String} RuleId Represent the Assessment Variable id to search for.
         * @returns {{RuleId: *, Rules: String}} A search criteria object.
         */
        var setQueryRuleSearchCriteria = function (RuleId) {
            var findRuleSearchCriteria = {
                "RuleId": RuleId,
                "Rules": "Rules"
            };

            if (!Object.isDefined(RuleId)) {
                delete findRuleSearchCriteria.RuleId;
                findRuleSearchCriteria.Rules = findRuleSearchCriteria.Rules + ".json";
            } else {
                findRuleSearchCriteria.RuleId = findRuleSearchCriteria.RuleId + ".json";
            }

            return findRuleSearchCriteria;
        };

        /**
         * Convenience method that handles the Assessment Variable Service query response by returning a fully
         * transformed Rule payload object.
         *
         * @private
         * @method
         * @param {String} jsonResponse Represents the Restful Assessment Variable Service query response.
         * @param {EScreeningDashboardApp.models.RulesTransformer} jsonResponsePayloadTransformer Represents the Assessment Variables JSON Response Payload transformer.
         * @param {String} userId Represents the User who made the service query request.
         * @returns {*} A transformed Rule payload object.
         * @throws {Error} If the jsonResponse can not be property transformed.
         */
        var handleRuleQueryResponse = function (jsonResponse, jsonResponsePayloadTransformer, userId) {
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
                throw new Error("handleRuleServiceQueryResponse() method: Response is null.");
            }

            return payload;
        };

        /**
         * Convenience method that handles the Assessment Variable Service save response by returning a fully
         * transformed Rule payload object.
         *
         * @private
         * @method
         * @param {String} jsonResponse Represents the Restful Assessment Variable Service query response.
         * @param {EScreeningDashboardApp.models.RulesTransformer} jsonResponsePayloadTransformer Represents the Assessment Variables JSON Response Payload transformer.
         * @param {String} userId Represents the User who made the service query request.
         * @returns {*} A transformed Rule payload object.
         * @throws {Error} If the jsonResponse can not be property transformed.
         */
        var handleRuleSaveResponse = function (jsonResponse, jsonResponsePayloadTransformer, userId) {
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
                throw new Error("handleRuleServiceQueryResponse() method: Response is null.");
            }

            return payload;
        };

        /**
         * Convenience method that handles the Assessment Variable Service save response by returning a fully
         * transformed Rule payload object.
         *
         * @private
         * @method
         * @param {String} jsonResponse Represents the Restful Assessment Variable Service query response.
         * @param {String} userId Represents the User who made the service query request.
         * @returns {*} A transformed Rule payload object.
         * @throws {Error} If the jsonResponse can not be property transformed.
         */
        var handleRuleRemoveResponse = function (jsonResponse, userId) {
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
                throw new Error("handleRuleServiceQueryResponse() method: Response is null.");
            }

            return response;
        };

        // Expose the public RuleService API to the rest of the application.
        return {
            query: query,
            create:create,
            update: update,
            remove: remove,
            setQueryRuleSearchCriteria: setQueryRuleSearchCriteria,
            setCreateRuleSearchCriteria:setCreateRuleSearchCriteria,
            setUpdateRuleRequestParameter: setUpdateRuleRequestParameter,
            setRemoveRuleRequestParameter: setRemoveRuleRequestParameter
        };
    }]);