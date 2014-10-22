/**
 * Angular service factory method for Assessment Variables.
 *
 * @author Tonté Pouncil
 */
angular.module('EscreeningDashboardApp.services.templateType', ['restangular'])
    .factory('AssessmentVariableService', ['Restangular', 'AssessmentVariable', function (Restangular, AssessmentVariable){
        "use strict";

        var currentAssessmentVariables = [];
        var currentAssessmentVariable = null;

        var restAngular = Restangular.withConfig(function(Configurer) {
                Configurer.setBaseUrl('/escreeningdashboard/dashboard');
                Configurer.setRequestSuffix('.json');
            }),
            service = restAngular.service("services/assessmentVariables");

        restAngular.extendModel("services/assessmentVariables", function(model) {
            return angular.extend(model, AssessmentVariable);
        });


        var registerAssessmentVariables = function($scope, assessmentVariables){
            $scope.assessmentVariables = assessmentVariables;
            $scope.$watch('assessmentVariables', function(newVal, oldVal){
                console.log("Updating assessment variable list");
                currentAssessmentVariables = newVal;
            }, true);
        };

        // Expose the public TemplateService API to the rest of the application.
        //return service;
        return {
            /**
             * Will retrieve the list of template types given the query parameter or return what was last
             * list queried if queryParam is null.  If queryParam is empty then the server will be queried.
             */
            getAssessmentVariables: function (queryParams) {
                if(Object.isDefined(queryParams)){
                    return service.getList(queryParams).then(function(assessmentVariables){
                        currentAssessmentVariables = assessmentVariables;
                        return assessmentVariables;
                    });
                }
                return currentAssessmentVariables;
            },
            /**
             * Connects the given types to the give scope and sets up a $watch for changes so
             * the state is kept up to date.  If this is not called in a controller, then changes
             * will not be reflected when getAssessmentVariables is called.
             */
            registerAssessmentVariables : registerAssessmentVariables,

            /**
             * To track the currently selected assessmentVariable.
             */
            setSelectedAssessmentVariable : function(someAssessmentVariable){
                currentAssessmentVariable = someAssessmentVariable;
            },
            getSelectedAssessmentVariable : function(){
                return currentAssessmentVariable;
            }
        }
    }]);