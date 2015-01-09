/**
 * Angular service factory method for Assessment Variables.
 *
 * @author Tonté Pouncil
 */
angular.module('EscreeningDashboardApp.services.assessmentVariable', ['restangular'])
    .factory('AssessmentVariableService', ['Restangular', 'TemplateVariableContent', 'ngTableParams', '$filter', function (Restangular, TemplateVariableContent, ngTableParams, $filter){
        "use strict";

        var restAngular = Restangular.withConfig(function(config) {
                config.setBaseUrl('services/');
                config.setRequestSuffix('.json');
            }),
            service = restAngular.service("assessmentVariables");

        restAngular.extendModel("assessmentVariables", function(model) {
            model = angular.extend(model, TemplateVariableContent);
            model.setType();
            return model;
        });

        // Expose the public AssessmentVariableService API to the rest of the application.
        //return service;
        return {
            /**
             * Will retrieve the list of assessment variables given the query parameter.
             */
            cachedResults: [],
	        cachedHashResults: [],
            query: function (queryParams, useQueryCache) {
                var results = [];
                useQueryCache = (Object.isBoolean(useQueryCache))? useQueryCache: false;

                if(Object.isDefined(queryParams) && (Object.isDefined(queryParams.surveyId) || Object.isDefined(queryParams.batteryId))) {
                    if(useQueryCache) {
                        if(Object.isDefined(this.cachedHashResults[queryParams])){
                            results = this.cachedHashResults[queryParams];
                        } else {
                            this.cachedResults.push(this.cachedHashResults[queryParams] = service.getList(queryParams));
                            results = this.cachedHashResults[queryParams];
                        }
                    } else {
                        this.cachedResults.push(this.cachedHashResults[queryParams] = service.getList(queryParams));
                        results = this.cachedHashResults[queryParams];
                    }
                } else {
                    throw new BytePushers.exceptions.InvalidParameterException("query parameters can not be null.");
                }

                return results;
            },
            getCachedResults: function(queryParams) {
                return this.cachedHashResults[queryParams];
            },
            getLastCachedResults: function(){
                return this.cachedResults[this.cachedResults.length - 1];
            },
            clearCachedResults: function () {
                this.cachedHashResults = [];
                this.cachedResults = [];
            },
	        getTableParams: function(searchObj, avs) {

		        var assessmentVariables = this.getLastCachedResults().$object;

		        return new ngTableParams({
			        page: 1, // show first page
			        count: 10, // count per page
			        filter: searchObj
		        }, {
			        counts: [],
			        total: 0,
			        getData: function ($defer, params) {
				        var filteredData = params.filter() ? $filter('filter')(assessmentVariables, params.filter()) : assessmentVariables;
				        var avs = filteredData.slice((params.page() - 1) * params.count(), params.page() * params.count());

				        params.total(filteredData.length); // set total for recalc pagination
				        $defer.resolve(avs);
			        },
			        $scope: { $data: {} }
		        });
	        }
        }
    }]);
