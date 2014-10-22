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
                var data = [
                    {
                        "id" : "87",                // the value of assessment_variable.assessment_variable_id
                        "kind" : '1',             // the value of assessment_variable.assessment_variable_type_id
                        "name": "demo_email_phone",       // filled with the value found in the export column (we can talk more about this)
                        "displayName" :  "email",   // filled with either measureText or answerText
                        "answerId": "1",           // the id of the answer if this is an AV of type Measure Answer
                        "measureId": "2",          // the id of the measure if this is an AV of type Measure
                        "measureTypeId" : "3"       // the type ID of the measure if this is an AV of type Measure
                    },
                    {
                        "id" : "287",                // the value of assessment_variable.assessment_variable_id
                        "kind" : '2',             // the value of assessment_variable.assessment_variable_type_id
                        "name": "demo_phone",       // filled with the value found in the export column (we can talk more about this)
                        "displayName" :  "phone",   // filled with either measureText or answerText
                        "answerId": "15",           // the id of the answer if this is an AV of type Measure Answer
                        "measureId": "62",          // the id of the measure if this is an AV of type Measure
                        "measureTypeId" : "93"       // the type ID of the measure if this is an AV of type Measure
                    },
                    {
                        "id" : "872",                // the value of assessment_variable.assessment_variable_id
                        "kind" : '3',             // the value of assessment_variable.assessment_variable_type_id
                        "name": "demo_age",       // filled with the value found in the export column (we can talk more about this)
                        "displayName" :  "age",   // filled with either measureText or answerText
                        "answerId": "41",           // the id of the answer if this is an AV of type Measure Answer
                        "measureId": "22",          // the id of the measure if this is an AV of type Measure
                        "measureTypeId" : "13"       // the type ID of the measure if this is an AV of type Measure
                    },
                    {
                        "id" : "721",                // the value of assessment_variable.assessment_variable_id
                        "kind" : '4',             // the value of assessment_variable.assessment_variable_type_id
                        "name": "demo_age_email",       // filled with the value found in the export column (we can talk more about this)
                        "displayName" :  "age",   // filled with either measureText or answerText
                        "answerId": "41",           // the id of the answer if this is an AV of type Measure Answer
                        "measureId": "22",          // the id of the measure if this is an AV of type Measure
                        "measureTypeId" : "13"       // the type ID of the measure if this is an AV of type Measure
                    },{
                        "id" : "87",                // the value of assessment_variable.assessment_variable_id
                        "kind" : '1',             // the value of assessment_variable.assessment_variable_type_id
                        "name": "demo_email_age",       // filled with the value found in the export column (we can talk more about this)
                        "displayName" :  "email1",   // filled with either measureText or answerText
                        "answerId": "1",           // the id of the answer if this is an AV of type Measure Answer
                        "measureId": "2",          // the id of the measure if this is an AV of type Measure
                        "measureTypeId" : "3"       // the type ID of the measure if this is an AV of type Measure
                    },
                    {
                        "id" : "287",                // the value of assessment_variable.assessment_variable_id
                        "kind" : '2',             // the value of assessment_variable.assessment_variable_type_id
                        "name": "demo_password",       // filled with the value found in the export column (we can talk more about this)
                        "displayName" :  "password",   // filled with either measureText or answerText
                        "answerId": "15",           // the id of the answer if this is an AV of type Measure Answer
                        "measureId": "62",          // the id of the measure if this is an AV of type Measure
                        "measureTypeId" : "93"       // the type ID of the measure if this is an AV of type Measure
                    },
                    {
                        "id" : "872",                // the value of assessment_variable.assessment_variable_id
                        "kind" : '3',             // the value of assessment_variable.assessment_variable_type_id
                        "name": "demo_salary_password",       // filled with the value found in the export column (we can talk more about this)
                        "displayName" :  "salary",   // filled with either measureText or answerText
                        "answerId": "41",           // the id of the answer if this is an AV of type Measure Answer
                        "measureId": "22",          // the id of the measure if this is an AV of type Measure
                        "measureTypeId" : "13"       // the type ID of the measure if this is an AV of type Measure
                    },
                    {
                        "id" : "721",                // the value of assessment_variable.assessment_variable_id
                        "kind" : '4',             // the value of assessment_variable.assessment_variable_type_id
                        "name": "demo_birthday",       // filled with the value found in the export column (we can talk more about this)
                        "displayName" :  "birthday",   // filled with either measureText or answerText
                        "answerId": "41",           // the id of the answer if this is an AV of type Measure Answer
                        "measureId": "22",          // the id of the measure if this is an AV of type Measure
                        "measureTypeId" : "13"       // the type ID of the measure if this is an AV of type Measure
                    }
                ];

                $scope.data = AssessmentVariableService.query({surveyId: 26});

                $scope.assessmentVariableTypes = ['1', '2', '3', '4'
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
                    kind: ''
                };

                $scope.$watch("searchObj.kind", function (currentlySelectedKind, previouslySelectedKind) {
                    if(currentlySelectedKind === previouslySelectedKind){

                    } else {
                        if(!Object.isDefined(currentlySelectedKind)) {
                            $scope.searchObj.kind = "";
                            $scope.assessmentVariableTable.reload();
                        }
                    }

                }, true);

                var setTable = function () {
                    $scope.assessmentVariableTable = new ngTableParams({
                        page: 1, // show first page
                        count: 5, // count per page
                        filter: $scope.searchObj
                    }, {
                        counts: [],
                        total: data.length,
                        getData: function ($defer, params) {
                            // use build-in angular filter
                            //params.total(data.length);
                            var filteredData = params.filter() ? $filter('filter')(data, params.filter()) : data;
                            params.total(filteredData.length); // set total for recalc pagination
                            $defer.resolve(filteredData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
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