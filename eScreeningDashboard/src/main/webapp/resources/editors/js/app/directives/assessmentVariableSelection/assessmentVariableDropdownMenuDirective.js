(function(angular) {
    "use strict";

    angular.module('EscreeningDashboardApp.services.assessmentVariable').directive('mheAssessmentVarDropdownMenuDir', [function() {
        return {
            restrict: 'EA',
            scope: {
                assessmentVariable: '='
            },
            transclude: true,
            controller: ['$scope', function ($scope) {
                var toggleAssessmentVariableSelectionTable = function() {
                    $scope.showAssessmentVariableSelectionTable = ($scope.showAssessmentVariableSelectionTable)? false: true;
                };
                $scope.showAssessmentVariableSelectionTable = false;

                $scope.displayAssessmentVariableSelectionTable = function (){
                    toggleAssessmentVariableSelectionTable();
                };
            }],
            templateUrl: 'resources/editors/js/app/directives/assessmentVariableSelection/assessmentVariableSelection.html',
            link: function(scope, element, attributes, controller) {
                element.addClass("assessmentVariableSelection");
            }
        };

    }]);
})(angular);