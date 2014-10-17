(function(angular) {
    "use strict";

    angular.module('EscreeningDashboardApp.services.assessmentVariable').directive('mheAssessmentVariables', ['AssessmentVariableService', function(AssessmentVariableService) {

        return {
            restrict: 'EA',
            scope: {},
            controller: function ($scope) {
                $scope.showAssessmentVariableSelectionTable = false;
                $scope.toggleAssessmentVariableSelectionTable = function() {
                    $scope.showAssessmentVariableSelectionTable = ($scope.showAssessmentVariableSelectionTable)? false: true;
                };
            },
            templateUrl: 'resources/editors/js/app/directives/assessmentVariableSelection/assessmentVariables.html',
            link: function(scope, element, attributes, controller) {

                /*AssessmentVariableService.query(AssessmentVariableService.setQueryAssessmentVariableSearchCriteria(null)).then(function(response) {

                    scope.variables = response;
                });*/

            }
        };

    }]);
})(angular);