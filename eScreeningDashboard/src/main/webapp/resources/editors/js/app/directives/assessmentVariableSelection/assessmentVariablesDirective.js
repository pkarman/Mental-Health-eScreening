(function(angular) {
    "use strict";

    angular.module('EscreeningDashboardApp.services.assessmentVariable').directive('mheAssessmentVariables', ['AssessmentVariableService', function(AssessmentVariableService) {

        return {
            restrict: 'EA',
            scope: {},
            controller: function ($scope) {

            },
            templateUrl: 'resources/editors/views/templates/assessmentvariables.html',
            link: function(scope, element, attributes, controller) {

                /*AssessmentVariableService.query(AssessmentVariableService.setQueryAssessmentVariableSearchCriteria(null)).then(function(response) {

                    scope.variables = response;
                });*/

            }
        };

    }]);
})(angular);