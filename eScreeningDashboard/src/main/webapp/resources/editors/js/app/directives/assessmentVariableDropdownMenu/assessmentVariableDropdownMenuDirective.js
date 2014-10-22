(function(angular) {
    "use strict";

    angular.module('EscreeningDashboardApp.services.assessmentVariable').directive('mheAssessmentVarDropdownMenuDir', [function() {
        return {
            restrict: 'EA',
            scope: {},
            transclude: true,
            controller: ['$scope', '$rootScope', function ($scope, $rootScope) {
                var toggleAssessmentVariableSelectionTable = function() {
                    $scope.showAssessmentVariableSelectionTable = ($scope.showAssessmentVariableSelectionTable)? false: true;
                };
                //$scope.guid = "assessment-variable-menu-" + new Date().getTime();
                $scope.showAssessmentVariableSelectionTable = false;

                $scope.displayAssessmentVariableSelectionTable = function (){
                    toggleAssessmentVariableSelectionTable();
                };

                $rootScope.$on('closeAssessmentVariableMenuRequested', function(event, data) {
                    if($scope.guid === data) {
                        toggleAssessmentVariableSelectionTable();
                    }
                });
            }],
            templateUrl: 'resources/editors/js/app/directives/assessmentVariableDropdownMenu/assessmentVariableDrondownMenu.html',
            link: function(scope, element, attributes, controller) {
                element.addClass("assessmentVariableSelection");
            }
        };

    }]);
})(angular);