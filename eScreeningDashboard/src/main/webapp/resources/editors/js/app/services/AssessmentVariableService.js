/**
 * Angular service factory method for Assessment Variables.
 *
 * @author Tonté Pouncil
 */
angular.module('EscreeningDashboardApp.services.assessmentVariable', ['restangular'])
    .factory('AssessmentVariableService', ['Restangular', 'AssessmentVariable', function (Restangular, AssessmentVariable){
        "use strict";

        var restAngular = Restangular.withConfig(function(config) {
                config.setBaseUrl('services/');
                config.setRequestSuffix('.json');
            }),
            service = restAngular.service("assessmentVariables");

        restAngular.extendModel("assessmentVariables", function(model) {
            return new AssessmentVariable(model);
        });

		var cachedResults = [],
			cachedHashResults = [];

        // Expose the public AssessmentVariableService API to the rest of the application.
        //return service;
        return {
        	
            /**
             * Will retrieve the list of assessment variables given the query parameter.
             */

            query: function (queryParams, useQueryCache) {
                var results;

                var params = Object.isDefined(queryParams) && (Object.isDefined(queryParams.surveyId) || Object.isDefined(queryParams.batteryId)) ?  queryParams : null;
                var paramsHash = params == null ? "all" : params;
                
                if(useQueryCache) {
                    results = cachedHashResults[paramsHash];
                } 
                
                if(!results){
                	results = service.getList(params);
                	cachedHashResults[paramsHash] = results;
                	cachedResults.push(results);
                }
                
                return results;
            },

            getCachedResults: function(queryParams) {
                return cachedHashResults[queryParams];
            },

            getLastCachedResults: function(){
                return cachedResults[cachedResults.length - 1];
            },

            clearCachedResults: function () {
                cachedHashResults = [];
                cachedResults = [];
            }
        };

    }]
);
