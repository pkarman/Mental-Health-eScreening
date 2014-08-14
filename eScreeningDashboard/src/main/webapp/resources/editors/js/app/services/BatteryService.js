/**
 * Angular service factory method that creates the BatteryService object.
 * 
 * @author Bryan Henderson
 */
angular.module('EscreeningDashboardApp.services.battery', ['ngResource'])
.factory('BatteryService',['$q','$resource',function($q,$resource){
	'use strict';
	/**
	 * Convenience method that supports querying BatteryService.
	 * 
	 * @private
	 * @method
	 * @param {{userID:*,batteryId:string}}
	 * queryBatterSearchCriteria Represents the search criteria to query the BatteryService.
	 * @returns {promise} a promise.
	 */
	var query = function(queryBatterySearchCriteria){
		/**
		 * Represents the angular $defer that is used for asynchronous service calls.
		 * 
		 * @private
		 * @field
		 * @type {$defer}
		 */
		var deferred = $q.defer(),
			service = $resource(
					"services/:batteries/:batteryId",
					{},
					{
						query:{
							method:'GET',
							params:{},
							isArray:false,
							headers:{
								'Accept':'application/json',
								'Content-Type':'application/json'
							}
						}
					});
			service.query(queryBatterySearchCriteria,function(jsonResponse){
                console.info("inside battery service query");
				var existingBatteries = handleBatteryQueryResponse(jsonResponse, EScreeningDashboardApp.models.BatteriesTransformer,null);
				deferred.resolve((existingBatteries.length === 1)? existingBatteries[0]: existingBatteries);
			}, function(reason){
				var errorMessage = "Sorry, we are unable to process your request at this time, because we are experiencing problems communicating with the server.";
				if (Object.isDefined(reason)&&Object.isDefined(reason.status)&&Object.isNumber(reason.status)){
					errorMessage = HttpRejectionProcessor.processRejection(reason);
				}
				deferred.reject(new BytePushers.models.ResponseStatus(
					{
                        "message":errorMessage,
                        "status":"failed"
                    }
                ));
			});
			return deferred.promise;
	},
    /**
     * Convenience method that supports saving a Battery via the BatteryService.
     *
     * @param {{batteryId:String, battery:EscreeningDashboardApp.models.Battery,batteries:String}}
     * saveBatteryRequestParameters Represents the save parameters for the request.
     */
    create = function(createBatteryRequestParameter){
        /**
         * Represents the Angular $defer that is used for asynchronous service calls.
         *
         * @private
         * @field
         * @type {$defer}
         */
        var deferred = $q.defer(),
            /**
             * Represents the Angular $resource object that is used for asynchronous service calls.
             *
             * @private
             * @field
             * @type {$resource}
             */
            service = $resource(
                "services/:battery",
                {},
                {
                    save:{
                        method:'POST',
                        params:{
                            "battery":createBatteryRequestParameter.battery
                        },
                        isArray:false,
                        headers:{
                            "Accept":'application/json',
                            'Content-Type':'application/json'
                        }
                    }
                }
            );

        service.save(createBatteryRequestParameter.payload.toJSON(),function(jsonResponse){
            var existingBattery = handleBatterySaveResponse(jsonResponse, EScreeningDashboardApp.models.BatteryTransformer);
            deferred.resolve(existingBattery);
        },function(reason){
            deferred.reject(reason);
        });
        return deferred.promise;
    },
	/**
	 * Convenience method that supports saving a Battery via the BatteryService.
	 * 
	 * @param {{batteryId:String, battery:EscreeningDashboardApp.models.Battery,batteries:String}}
	 * saveBatteryRequestParameters Represents the save parameters for the request.
	 */
	update = function(updateBatteryRequestParameter){
		/**
		 * Represents the Angular $defer that is used for asynchronous service calls.
		 * 
		 * @private
		 * @field
		 * @type {$defer}
		 */
		var deferred = $q.defer(),
		/**
		 * Represents the Angular $resource object that is used for asynchronous service calls.
		 * 
		 * @private
		 * @field
		 * @type {$resource}
		 */
		service = $resource(
			"services/:batteries/:batteryId",
			{},
			{
				save:{
					method:'PUT',
					params:{
						"batteryId":updateBatteryRequestParameter.batteryId,
						"batteries":updateBatteryRequestParameter.batteries
					},
					isArray:false,
					headers:{
						"Accept":'application/json',
						'Content-Type':'application/json'
					}
				}
			}
		);
		
		service.save(updateBatteryRequestParameter.payload.toJSON(),function(jsonResponse){
			var existingBattery = handleBatterySaveResponse(jsonResponse, EScreeningDashboardApp.models.BatteryTransformer);
			deferred.resolve(existingBattery);
		},function(reason){
			deferred.reject(reason);
		});
		return deferred.promise;
    },
	/**
     * Convenience method that supports removing an Battery via the BatteryService.
     *
     * @param {{batteryId: string}}  removeBatteryRequestParameters Represents the save parameters for the request.
     */
    remove = function (removeBatteryRequestParameters) {
        /**
         * Represents the angular $defer that is used for asynchronous service calls.
         *
         * @private
         * @field
         * @type {Defer}
         */
        var deferred = $q.defer(),
            /**
             * Represents the angular $resource object that is used for asynchronous service calls.
             *
             * @private
             * @field
             * @type {Resource}
             */
            service = $resource(
                    "services/:batteries/:batteryId",
                {},
                {
                    save: {
                        method: 'DELETE',
                        params: {
                            "batteryId": removeBatteryRequestParameters.batteryId,
                            "batteries": removeBatteryRequestParameters.batteries
                        },
                        isArray: false,
                        headers:{
                            'Accept':'application/json',
                            'Content-Type':'application/json'
                        }
                    }
                }
            );

        service.save(removeBatteryRequestParameters, function (jsonResponse) {
            var response = handleBatteryRemoveResponse(jsonResponse);
            deferred.resolve(response);
        }, function (reason) {
            deferred.reject(reason);
        });

        return deferred.promise;
    };

    /**
     * Convenience method that sets the request parameter for the battery update service request.
     *
     * @private
     * @method
     * @param {EScreeningDashboardApp.models.Battery} battery Represents the Battery domain object to update.
     * @returns {{batteryId: *, batteries: String}} A search criteria object.
     */
    var setUpdateBatteryRequestParameter = function (battery) {
        var updateBatteryRequestParameter = {
            "batteryId": battery.getId(),
            "payload": battery,
            "batteries": "batteries"
        };

        if (!Object.isDefined(battery.getId())) {
            delete updateBatteryRequestParameter.batteryId;
            updateBatteryRequestParameter.batteries = updateBatteryRequestParameter.batteries + ".json";
        } else {
            updateBatteryRequestParameter.batteryId = updateBatteryRequestParameter.batteryId + ".json";
        }
        return updateBatteryRequestParameter;
    };

    /**
     * Convenience method that sets the request parameter for the battery update service request.
     *
     * @private
     * @method
     * @param {EScreeningDashboardApp.models.Battery} battery Represents the Battery domain object to update.
     * @param {String} batteryPathName path of the battery request
     * @returns {{battery: String, payload: EScreeningDashboardApp.models.Battery}} A search criteria object.
     */
    var setCreateBatteryRequestParameter = function (battery, batteryPathName) {
        var saveBatteryRequestParameter = {
            "battery": (Object.isDefined(batteryPathName))? batteryPathName: "battery",
            "payload": battery
        };

        saveBatteryRequestParameter.battery = saveBatteryRequestParameter.battery + ".json";
        return saveBatteryRequestParameter;
    };

    /**
     * Convenience method that sets the request parameter for the battery remove service request.
     *
     * @private
     * @method
     * @param {String} batteryId Represent the Battery id to save.
     * @returns {{batteryId: *, batteries: String}} A search criteria object.
     */
    var setRemoveBatteryRequestParameter = function (batteryId) {
        var removeBatteryRequestParameter = {
            "batteryId": batteryId,
            "batteries": "batteries"
        };

        if (!Object.isDefined(batteryId)) {
            throw new BytePushers.exceptions.NullPointerException("batteryId parameter can not be null");
        }

        return removeBatteryRequestParameter;
    };

    /**
     * Convenience method that sets the search criteria for the battery search service query request.
     *
     * @private
     * @method
     * @param {String} batteryId Represent the battery id to search for.
     * @returns {{batteryId: *, batteries: String}} A search criteria object.
     */
    var setQueryBatterySearchCriteria = function (batteryId) {
        var findBatterySearchCriteria = {
            "batteryId": batteryId,
            "batteries": "batteries"
        };

        if (!Object.isDefined(batteryId)) {
            delete findBatterySearchCriteria.batteryId;
            findBatterySearchCriteria.batteries = findBatterySearchCriteria.batteries + ".json";
        } else {
            findBatterySearchCriteria.batteryId = findBatterySearchCriteria.batteryId + ".json";
        }

        return findBatterySearchCriteria;
    };

    /**
     * Convenience method that handles the Battery Service query response by returning a fully
     * transformed Battery payload object.
     *
     * @private
     * @method
     * @param {String} jsonResponse Represents the Restful Battery Service query response.
     * @param {EScreeningDashboardApp.models.BatteryTransformer} jsonResponsePayloadTransformer Represents the Batteries JSON Response Payload transformer.
     * @param {String} userId Represents the User who made the service query request.
     * @returns {*} A transformed Battery payload object.
     * @throws {Error} If the jsonResponse can not be property transformed.
     */
    var handleBatteryQueryResponse = function (jsonResponse, jsonResponsePayloadTransformer, userId) {
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
            throw new Error("handleBatteryServiceQueryResponse() method: Response is null.");
        }

        return payload;
    };

    /**
     * Convenience method that handles the Battery Service save response by returning a fully
     * transformed Battery payload object.
     *
     * @private
     * @method
     * @param {String} jsonResponse Represents the Restful Battery Service query response.
     * @param {EScreeningDashboardApp.models.BatteryTransformer} jsonResponsePayloadTransformer Represents the Batteries JSON Response Payload transformer.
     * @param {String} userId Represents the User who made the service query request.
     * @returns {*} A transformed Battery payload object.
     * @throws {Error} If the jsonResponse can not be property transformed.
     */
    var handleBatterySaveResponse = function (jsonResponse, jsonResponsePayloadTransformer, userId) {
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
            throw new Error("handleBatteryServiceQueryResponse() method: Response is null.");
        }

        return payload;
    };

    /**
     * Convenience method that handles the Battery Service save response by returning a fully
     * transformed Battery payload object.
     *
     * @private
     * @method
     * @param {String} jsonResponse Represents the Restful Battery Service query response.
     * @param {String} userId Represents the User who made the service query request.
     * @returns {*} A transformed Battery payload object.
     * @throws {Error} If the jsonResponse can not be property transformed.
     */
    var handleBatteryRemoveResponse = function (jsonResponse, userId) {
        /**
         * Represents the response of a service call request.
         *
         * @private
         * @field
         * @type {EScreeningDashboardApp.models.Response}
         */
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
            throw new Error("handleBatteryServiceQueryResponse() method: Response is null.");
        }

        return response;
    };

    // Expose the public BatteryService API to the rest of the application.
    return {
        query: query,
        update: update,
        remove: remove,
        create: create,
        setQueryBatterySearchCriteria: setQueryBatterySearchCriteria,
        setUpdateBatteryRequestParameter: setUpdateBatteryRequestParameter,
        setRemoveBatteryRequestParameter: setRemoveBatteryRequestParameter,
        setCreateBatteryRequestParameter: setCreateBatteryRequestParameter
    };
}]);