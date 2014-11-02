(function(angular) {
    "use strict";
    angular.module('EscreeningDashboardApp.services.assessmentVariable').directive('mheHideDropdownMenu', ['$document', function($document) {
        return {
            restrict: 'A',
            link: function(scope, elem, attr, ctrl) {
                elem.bind('click', function(e) {
                    e.stopPropagation();
                });
                $document.bind('click', function() {
                    scope.$apply(attr.mheHideDropdownMenu);
                })
            }
        }
    }]);

    angular.module('EscreeningDashboardApp.services.assessmentVariable').directive('mheAssessmentVarDropdownMenuDir', [function() {
        return {
            restrict: 'EA',
            scope: {
                guid: '=',
                assessmentVariable: '='
            },
            transclude: true,
            controller: ['$scope', '$rootScope', 'limitToWithEllipsisFilter', function ($scope, $rootScope, limitToWithEllipsisFilter) {
                var toggleAssessmentVariableSelectionTable = function() {
                    $scope.showAssessmentVariableSelectionTable = ($scope.showAssessmentVariableSelectionTable)? false: true;
                };

                $scope.showAssessmentVariableSelectionTable = false;

                $scope.displayAssessmentVariableSelectionTable = function (){
                    toggleAssessmentVariableSelectionTable();
                };
                $scope.hideDropDownMenu = function (){
                    $scope.showAssessmentVariableSelectionTable = false;
                };

                $rootScope.$on('closeAssessmentVariableMenuRequested', function(event, data) {
                    if(!Object.isDefined(data)) {
                        throw new BytePushers.exceptions.NullPointerException("data parameter can not be undefined or null.");
                    }

                    if($scope.guid === data.guid) {
                        if(!Object.isDefined(data.selectedAssessmentVariable)){
                            throw new BytePushers.exceptions.NullPointerException("data.selectedAssessmentVariable parameter can not be undefined or null.");
                        } else if(!Object.isDefined(data.selectedAssessmentVariable.name)) {
                            throw new BytePushers.exceptions.NullPointerException("data.selectedAssessmentVariable.name parameter can not be undefined or null.");
                        }
                        $(".assessmentVariableSelection[guid=\""+data.guid+"\"]").find("#assessmentVariableMenuLabel").text(" " + limitToWithEllipsisFilter(data.selectedAssessmentVariable.name, 20));
                        toggleAssessmentVariableSelectionTable();
                    }
                });

                if(Object.isDefined($scope.assessmentVariable) && Object.isDefined($scope.assessmentVariable.id)) {
                    $(".assessmentVariableSelection[guid=\""+data.guid+"\"]").find("#assessmentVariableMenuLabel").text(" " + limitToWithEllipsisFilter($scope.assessmentVariable.name, 20));
                    $scope.$emit('filterOperators', {guid: $scope.guid, selectedAssessmentVariable: $scope.assessmentVariable});
                }
            }],
            templateUrl: 'resources/editors/js/app/directives/assessmentVariableDropdownMenu/assessmentVariableDropdownMenu.html',
            link: function(scope, element, attributes, controller) {
                element.addClass("assessmentVariableSelection");
                element.attr("guid", scope.guid);
            }
        };

    }]);
})(angular);