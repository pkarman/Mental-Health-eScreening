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
            return angular.extend(model, AssessmentVariableInfo);
        });

        // Expose the public TemplateService API to the rest of the application.
        //return service;
        return {
            /**
             * Will retrieve the list of assessment variables given the query parameter.
             */
            query: function (queryParams) {
                if(Object.isDefined(queryParams) && Object.isDefined(queryParams.surveyId)) {
                    return service.getList(queryParams);
                } else {
                    throw new BytePushers.exceptions.InvalidParameterException("query parameters can not be null.");
                }
            }
        }
    }]);