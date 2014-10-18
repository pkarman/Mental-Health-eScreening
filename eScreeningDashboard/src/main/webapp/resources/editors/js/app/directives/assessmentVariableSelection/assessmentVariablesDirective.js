(function(angular) {
    "use strict";

    angular.module('EscreeningDashboardApp.services.assessmentVariable').directive('mheAssessmentVariables', ['AssessmentVariableService', function(AssessmentVariableService) {

        return {
            restrict: 'EA',
            scope: {
                displayAsModal: '=modalDisplay'
            },
            controller: ['$scope', '$modal', '$filter', 'ngTableParams', function ($scope, $modal, $filter, ngTableParams) {
                $scope.showAssessmentVariableSelectionTable = false;
                var data = [
                    {
                        "id" : "87",                // the value of assessment_variable.assessment_variable_id
                        "typeId" : "2",             // the value of assessment_variable.assessment_variable_type_id
                        "name": "demo_email",       // filled with the value found in the export column (we can talk more about this)
                        "displayName" :  "email",   // filled with either measureText or answerText
                        "answerId": "1",           // the id of the answer if this is an AV of type Measure Answer
                        "measureId": "2",          // the id of the measure if this is an AV of type Measure
                        "measureTypeId" : "3"       // the type ID of the measure if this is an AV of type Measure
                    },
                    {
                        "id" : "287",                // the value of assessment_variable.assessment_variable_id
                        "typeId" : "24",             // the value of assessment_variable.assessment_variable_type_id
                        "name": "demo_phone",       // filled with the value found in the export column (we can talk more about this)
                        "displayName" :  "phone",   // filled with either measureText or answerText
                        "answerId": "15",           // the id of the answer if this is an AV of type Measure Answer
                        "measureId": "62",          // the id of the measure if this is an AV of type Measure
                        "measureTypeId" : "93"       // the type ID of the measure if this is an AV of type Measure
                    },
                    {
                        "id" : "872",                // the value of assessment_variable.assessment_variable_id
                        "typeId" : "52",             // the value of assessment_variable.assessment_variable_type_id
                        "name": "demo_age",       // filled with the value found in the export column (we can talk more about this)
                        "displayName" :  "age",   // filled with either measureText or answerText
                        "answerId": "41",           // the id of the answer if this is an AV of type Measure Answer
                        "measureId": "22",          // the id of the measure if this is an AV of type Measure
                        "measureTypeId" : "13"       // the type ID of the measure if this is an AV of type Measure
                    }
                ];

                var toggleAssessmentVariableSelectionTable = function() {
                        $scope.showAssessmentVariableSelectionTable = ($scope.showAssessmentVariableSelectionTable)? false: true;
                    },
                    selectAssessmentVariable = function(assessmentVariable) {
                        console.log("assessmentVariable: " + assessmentVariable);
                    },
                    displayAssessmentVariableSelectionTableAsModal = function () {
                        // Create the modal
                        var modalInstance = $modal.open({
                            templateUrl: 'resources/editors/js/app/directives/assessmentVariableSelection/assessmentVariableSelection',
                            resolve: {

                            },
                            controller: function($scope, $modalInstance) {

                                $scope.selectAssessmentVariable = selectAssessmentVariable;
                            }

                        });
                    };

                $scope.selectAssessmentVariable = selectAssessmentVariable;

                $scope.displayAssessmentVariableSelectionTable = function (){
                    $scope.displayAsModal = ($scope.displayAsModal === undefined || $scope.displayAsModal === null)? false: $scope.displayAsModal;

                    if($scope.displayAsModal) {
                        $scope.showAssessmentVariableSelectionTable = false;
                        displayAssessmentVariableSelectionTableAsModal();
                    } else {
                        toggleAssessmentVariableSelectionTable();
                    }
                };

                function setTable() {
                    $scope.tableParams = new ngTableParams({
                        page: 1, // show first page
                        count: 10, // count per page
                        filter: {
                            name: 'M' // initial filter
                        },
                        sorting: {
                            name: 'asc'
                        },
                        reload: function () {},
                        settings: function () {
                            return {};
                        }
                    }, {
                        total: data.length,
                        getData: function ($defer, params) {
                            // use build-in angular filter
                            params.total(data.length);
                            var filteredData = params.filter() ? $filter('filter')(data, params.filter()) : data;
                            var orderedData = params.sorting() ? $filter('orderBy')(filteredData, params.orderBy()) : data;
                            //params.total(orderedData.length); // set total for recalc pagination
                            $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                        }
                    });
                };

                setTable();

            }],
            templateUrl: 'resources/editors/js/app/directives/assessmentVariableSelection/assessmentVariableSelection.html',
            link: function(scope, element, attributes, controller) {
                element.addClass("assessmentVariableSelection");

                /*AssessmentVariableService.query(AssessmentVariableService.setQueryAssessmentVariableSearchCriteria(null)).then(function(response) {

                    scope.variables = response;
                });*/

            }
        };

    }]);
})(angular);