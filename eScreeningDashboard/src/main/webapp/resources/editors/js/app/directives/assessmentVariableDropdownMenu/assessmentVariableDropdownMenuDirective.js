(function(angular) {
    "use strict";

    angular.module('EscreeningDashboardApp.services.assessmentVariable').directive('mheAssessmentVarDropdownMenuDir', [function() {
        return {
            restrict: 'EA',
            scope: {
                guid: '='
            },
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
                    if(!Object.isDefined(data)) {
                        throw new BytePushers.exceptions.NullPointerException("data parameter can not be undefined or null.");
                    }

                    if($scope.guid === data.guid) {
                        if(!Object.isDefined(data.selectedVariableName)){
                            throw new BytePushers.exceptions.NullPointerException("data.selectedVariableName parameter can not be undefined or null.");
                        }
                        $('#assessmentVariableMenuLabel').text(" " + data.selectedVariableName);
                        toggleAssessmentVariableSelectionTable();
                    }
                });
            }],
            templateUrl: 'resources/editors/js/app/directives/assessmentVariableDropdownMenu/assessmentVariableDropdownMenu.html',
            link: function(scope, element, attributes, controller) {
                element.addClass("assessmentVariableSelection");
            }
        };

    }]);
})(angular);