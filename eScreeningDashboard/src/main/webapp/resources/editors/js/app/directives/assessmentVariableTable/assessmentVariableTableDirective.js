/**
 * Created by pouncilt on 10/21/14.
 */
(function(angular) {
    "use strict";

    angular.module('EscreeningDashboardApp.services.assessmentVariable').directive('mheAssessmentVarTblDir', [function() {

        return {
            restrict: 'EA',
            scope: {
                assessmentVariable: '=',
                guid: '='
            },
            controller: ['$scope', '$rootScope', '$filter', 'ngTableParams', 'AssessmentVariableService', function ($scope, $rootScope, $filter, ngTableParams, AssessmentVariableService) {
                $scope.assessmentVariableTypes = ['Measure', 'Measure Answer', 'Custom', 'Formula'
                    /*{id: 1, type: "Measure", displayName: "Question"},
                     {id: 2, type: "Measure Answer", displayName: "Question"},
                     {id: 3, type: "Custom", displayName: "Custom"},
                     {id: 4, type: "Formula", displayName: "Formula"}*/
                ];

                $scope.selectAssessmentVariable = function(assessmentVariable, guid) {
                    $scope.assessmentVariable = assessmentVariable;
                    $scope.$emit('closeAssessmentVariableMenuRequested', guid);
                };

                $scope.searchObj = {
                    type: ''
                };

                $scope.$watch("searchObj.type", function (currentlySelectedKind, previouslySelectedKind) {
                    if(currentlySelectedKind === previouslySelectedKind){

                    } else {
                        if(!Object.isDefined(currentlySelectedKind)) {
                            $scope.searchObj.type = "";
                            $scope.assessmentVariableTable.reload();
                        }
                    }

                }, true);

                var setTable = function () {
                    $scope.assessmentVariableTable = new ngTableParams({
                        page: 1, // show first page
                        count: 10, // count per page
                        filter: $scope.searchObj
                    }, {
                        counts: [],
                        total: 0,
                        getData: function ($defer, params) {
                            AssessmentVariableService.query({surveyId: 26}).then(function(data) {
                                // use build-in angular filter
                                //params.total(data.length);
                                data = params.filter() ? $filter('filter')(data, params.filter()) : data;
                                params.total(data.length); // set total for recalc pagination
                                $defer.resolve(data.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                            });
                        }
                    });
                };

                setTable();
            }],
            templateUrl: 'resources/editors/js/app/directives/assessmentVariableTable/assessmentVariableTable.html',
            link: function(scope, element, attributes, controller) {
                element.addClass("assessmentVariableTable");
            }
        };

    }]);
})(angular);