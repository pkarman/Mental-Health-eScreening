/**
 * Angular service factory method that creates the RuleEventService object.
 *
 * @author Bryan Henderson
 */
angular.module('EscreeningDashboardApp.services.RuleEvent', ['ngResource'])
    .factory('RuleEventService', ['$q', '$resource', function ($q, $resource){
        "use strict";
        /**
         * Convenience method that supports the querying of the RuleEventService.
         *
         * @private
         * @method
         * @param {{userId: *, RuleEventId: string}} queryRuleEventSearchCriteria Represents the search criteria to query the RuleEventService.
         * @returns {promise} A promise.
         */
        var query = function (queryRuleEventSearchCriteria) {
            /**
             * Represents the angular $defer that is used for asynchronous service calls.
             *
             * @private
             * @field
             * @type {$defer}
             */
            var deferred = $q.defer(),
                service = $resource (
                    "services/:RuleEvents/:RuleEventId",
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

            service.query(queryRuleEventSearchCriteria, function (jsonResponse) {
                var existingRuleEvents = handleRuleEventQueryResponse(jsonResponse, EScreeningDashboardApp.models.RuleEventsTransformer, null);
                deferred.resolve(existingRuleEvents);
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
         * Convenience method that supports saving an Rule Event via the RuleEventService.
         *
         * @param {{RuleEvent: EScreeningDashboardApp.models.RuleEvent, RuleEvents: String }}  createRuleEventRequestParameters Represents the save parameters for the request.
         */
        create = function (createRuleEventRequestParameter) {
        	
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
                    "services/:RuleEvents/?action=create",
                    {},
                    {
                        save: {
                            method: 'POST',
                            params: {
                                "RuleEvents": createRuleEventRequestParameter.RuleEvents
                            },
                            isArray: false,
                            headers:{
                                'Accept': 'application/json',
                                'Content-Type':'application/json'
                            }
                        }
                    }
                );
            
            service.save(createRuleEventRequestParameter.RuleEvent.toJSON(), function (jsonResponse) {
                var existingRuleEvent = handleRuleEventSaveResponse(jsonResponse, EScreeningDashboardApp.models.RuleEventTransformer);
                deferred.resolve(existingRuleEvent);
            }, function (reason) {
                deferred.reject(reason);
            });

            return deferred.promise;
        },
        /**
         * Convenience method that supports saving an Rule Event via the RuleEventService.
         *
         * @param {{assessmentId: String, RuleEvent: EScreeningDashboardApp.models.RuleEvent, RuleEvents: String }}  saveRuleEventRequestParameters Represents the save parameters for the request.
         */
        update = function (updateRuleEventRequestParameter) {
        	
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
                    "services/:RuleEvents/:RuleEventId",
                    {},
                    {
                        save: {
                            method: 'POST',
                            params: {
                                "RuleEventId": updateRuleEventRequestParameter.RuleEventId,
                                "RuleEvents": updateRuleEventRequestParameter.RuleEvents
                            },
                            isArray: false,
                            headers:{
                                'Accept': 'application/json',
                                'Content-Type':'application/json'
                            }
                        }
                    }
                );
            
            service.save(updateRuleEventRequestParameter.RuleEvent.toJSON(), function (jsonResponse) {
                var existingRuleEvent = handleRuleEventSaveResponse(jsonResponse, EScreeningDashboardApp.models.RuleEventTransformer);
                deferred.resolve(existingRuleEvent);
            }, function (reason) {
                deferred.reject(reason);
            });

            return deferred.promise;
        },
        /**
         * Convenience method that supports removing an Rule Event via the RuleEventService.
         *
         * @param {{RuleEventId: string}}  removeRuleEventRequestParameters Represents the save parameters for the request.
         */
        remove = function (removeRuleEventRequestParameters) {
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
                        "services/:RuleEvents/:RuleEventId.json?action=delete",
                    {},
                    {
                        save: {
                            method: 'POST',
                            params: {
                                "RuleEventId": removeRuleEventRequestParameters.RuleEventId,
                                "RuleEvents": removeRuleEventRequestParameters.RuleEvents
                            },
                            isArray: false
                        }
                    }
                );

            service.save(removeRuleEventRequestParameters, function (jsonResponse) {
                var response = handleRuleEventRemoveResponse(jsonResponse);
                deferred.resolve(response);
            }, function (reason) {
                deferred.reject(reason);
            });

            return deferred.promise;
        };
        
        /**
         * Convenience method that sets the request parameter for the Rule Event create service request.
         *
         * @private
         * @method
         * @param {EScreeningDashboardApp.models.RuleEvent} RuleEvent Represents the RuleEvent domain object to create.
         * @returns {{RuleEvents: String}} A search criteria object.
         */
        var setUpdateRuleEventRequestParameter = function (RuleEvent) {
            var saveRuleEventRequestParameter = {
                "RuleEvent": RuleEvent,
                "RuleEvents": "RuleEvents"
            };

            if (!Object.isDefined(RuleEvent.getId())) {
                delete saveRuleEventRequestParameter.RuleEventId;
                saveRuleEventRequestParameter.RuleEvents = saveRuleEventRequestParameter.RuleEvents + ".json";
            } else {
                saveRuleEventRequestParameter.RuleEventId = saveRuleEventRequestParameter.RuleEventId + ".json";
            }
            return saveRuleEventRequestParameter;
        };

        /**
         * Convenience method that sets the request parameter for the Rule Event update service request.
         *
         * @private
         * @method
         * @param {EScreeningDashboardApp.models.RuleEvent} RuleEvent Represents the RuleEvent domain object to update.
         * @returns {{RuleEventId: *, RuleEvents: String}} A search criteria object.
         */
        var setUpdateRuleEventRequestParameter = function (RuleEvent) {
            var saveRuleEventRequestParameter = {
                "RuleEventId": RuleEvent.getId(),
                "RuleEvent": RuleEvent,
                "RuleEvents": "RuleEvents"
            };

            if (!Object.isDefined(RuleEvent.getId())) {
                delete saveRuleEventRequestParameter.RuleEventId;
                saveRuleEventRequestParameter.RuleEvents = saveRuleEventRequestParameter.RuleEvents + ".json";
            } else {
                saveRuleEventRequestParameter.RuleEventId = saveRuleEventRequestParameter.RuleEventId + ".json";
            }
            return saveRuleEventRequestParameter;
        };

        /**
         * Convenience method that sets the request parameter for the Rule Event remove service request.
         *
         * @private
         * @method
         * @param {String} RuleEventId Represent the RuleEvent id to save.
         * @returns {{RuleEventId: *, RuleEvents: String}} A search criteria object.
         */
        var setRemoveRuleEventRequestParameter = function (RuleEventId) {
            var removeRuleEventRequestParameter = {
                "RuleEventId": RuleEventId,
                "RuleEvents": "RuleEvents"
            };

            if (!Object.isDefined(RuleEventId)) {
                throw new BytePushers.exceptions.NullPointerException("RuleEventId parameter can not be null");
            }

            return removeRuleEventRequestParameter;
        };

        /**
         * Convenience method that sets the search criteria for the Rule Event search service query request.
         *
         * @private
         * @method
         * @param {String} RuleEventId Represent the Rule Event id to search for.
         * @returns {{RuleEventId: *, RuleEvents: String}} A search criteria object.
         */
        var setQueryRuleEventSearchCriteria = function (RuleEventId) {
            var findRuleEventSearchCriteria = {
                "RuleEventId": RuleEventId,
                "RuleEvents": "RuleEvents"
            };

            if (!Object.isDefined(RuleEventId)) {
                delete findRuleEventSearchCriteria.RuleEventId;
                findRuleEventSearchCriteria.RuleEvents = findRuleEventSearchCriteria.RuleEvents + ".json";
            } else {
                findRuleEventSearchCriteria.RuleEventId = findRuleEventSearchCriteria.RuleEventId + ".json";
            }

            return findRuleEventSearchCriteria;
        };

        /**
         * Convenience method that handles the Rule Event Service query response by returning a fully
         * transformed RuleEvent payload object.
         *
         * @private
         * @method
         * @param {String} jsonResponse Represents the Restful Rule Event Service query response.
         * @param {EScreeningDashboardApp.models.RuleEventsTransformer} jsonResponsePayloadTransformer Represents the Rule Events JSON Response Payload transformer.
         * @param {String} userId Represents the User who made the service query request.
         * @returns {*} A transformed RuleEvent payload object.
         * @throws {Error} If the jsonResponse can not be property transformed.
         */
        var handleRuleEventQueryResponse = function (jsonResponse, jsonResponsePayloadTransformer, userId) {
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
                throw new Error("handleRuleEventServiceQueryResponse() method: Response is null.");
            }

            return payload;
        };

        /**
         * Convenience method that handles the Rule Event Service save response by returning a fully
         * transformed RuleEvent payload object.
         *
         * @private
         * @method
         * @param {String} jsonResponse Represents the Restful Rule Event Service query response.
         * @param {EScreeningDashboardApp.models.RuleEventsTransformer} jsonResponsePayloadTransformer Represents the Rule Events JSON Response Payload transformer.
         * @param {String} userId Represents the User who made the service query request.
         * @returns {*} A transformed RuleEvent payload object.
         * @throws {Error} If the jsonResponse can not be property transformed.
         */
        var handleRuleEventSaveResponse = function (jsonResponse, jsonResponsePayloadTransformer, userId) {
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
                throw new Error("handleRuleEventServiceQueryResponse() method: Response is null.");
            }

            return payload;
        };

        /**
         * Convenience method that handles the Rule Event Service save response by returning a fully
         * transformed RuleEvent payload object.
         *
         * @private
         * @method
         * @param {String} jsonResponse Represents the Restful Rule Event Service query response.
         * @param {String} userId Represents the User who made the service query request.
         * @returns {*} A transformed RuleEvent payload object.
         * @throws {Error} If the jsonResponse can not be property transformed.
         */
        var handleRuleEventRemoveResponse = function (jsonResponse, userId) {
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
                throw new Error("handleRuleEventServiceQueryResponse() method: Response is null.");
            }

            return response;
        };

        // Expose the public RuleEventService API to the rest of the application.
        return {
            query: query,
            create:create,
            update: update,
            remove: remove,
            setQueryRuleEventSearchCriteria: setQueryRuleEventSearchCriteria,
            setCreateRuleEventSearchCriteria:setCreateRuleEventSearchCriteria,
            setUpdateRuleEventRequestParameter: setUpdateRuleEventRequestParameter,
            setRemoveRuleEventRequestParameter: setRemoveRuleEventRequestParameter
        };
    }]);