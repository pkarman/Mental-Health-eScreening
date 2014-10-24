/**
 * Angular service factory method for Assessment Variables.
 *
 * @author Tonté Pouncil
 */
angular.module('EscreeningDashboardApp.services.assessmentVariable', ['restangular'])
    .factory('AssessmentVariableService', ['Restangular', 'AssessmentVariableInfo', function (Restangular, AssessmentVariableInfo){
        "use strict";

        var restAngular = Restangular.withConfig(function(Configurer) {
                Configurer.setBaseUrl('/escreeningdashboard/dashboard');
                Configurer.setRequestSuffix('.json');
            }),
            service = restAngular.service("services/assessmentVariables");

        restAngular.extendModel("services/assessmentVariables", function(model) {
            model = angular.extend(model, AssessmentVariableInfo);
            model.setType();
            return model;
        });

        // Expose the public TemplateService API to the rest of the application.
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

                if(Object.isDefined(queryParams) && Object.isDefined(queryParams.surveyId)) {
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
            }
        }
    }]);
