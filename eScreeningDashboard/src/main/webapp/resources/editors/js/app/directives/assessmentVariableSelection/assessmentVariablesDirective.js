(function(angular) {
    "use strict";

    angular.module('EscreeningDashboardApp.services.assessmentVariable').directive('mheAssessmentVariables', ['AssessmentVariableService', function(AssessmentVariableService) {

        return {
            restrict: 'EA',
            scope: {
                assessmentVariable: '='
            },
            controller: ['$scope', '$filter', 'ngTableParams', function ($scope, $filter, ngTableParams) {
                var data = [
                    {
                        "id" : "87",                // the value of assessment_variable.assessment_variable_id
                        "typeId" : "1",             // the value of assessment_variable.assessment_variable_type_id
                        "name": "demo_email",       // filled with the value found in the export column (we can talk more about this)
                        "displayName" :  "email",   // filled with either measureText or answerText
                        "answerId": "1",           // the id of the answer if this is an AV of type Measure Answer
                        "measureId": "2",          // the id of the measure if this is an AV of type Measure
                        "measureTypeId" : "3"       // the type ID of the measure if this is an AV of type Measure
                    },
                    {
                        "id" : "287",                // the value of assessment_variable.assessment_variable_id
                        "typeId" : "2",             // the value of assessment_variable.assessment_variable_type_id
                        "name": "demo_phone",       // filled with the value found in the export column (we can talk more about this)
                        "displayName" :  "phone",   // filled with either measureText or answerText
                        "answerId": "15",           // the id of the answer if this is an AV of type Measure Answer
                        "measureId": "62",          // the id of the measure if this is an AV of type Measure
                        "measureTypeId" : "93"       // the type ID of the measure if this is an AV of type Measure
                    },
                    {
                        "id" : "872",                // the value of assessment_variable.assessment_variable_id
                        "typeId" : "3",             // the value of assessment_variable.assessment_variable_type_id
                        "name": "demo_age",       // filled with the value found in the export column (we can talk more about this)
                        "displayName" :  "age",   // filled with either measureText or answerText
                        "answerId": "41",           // the id of the answer if this is an AV of type Measure Answer
                        "measureId": "22",          // the id of the measure if this is an AV of type Measure
                        "measureTypeId" : "13"       // the type ID of the measure if this is an AV of type Measure
                    },
                    {
                        "id" : "721",                // the value of assessment_variable.assessment_variable_id
                        "typeId" : "4",             // the value of assessment_variable.assessment_variable_type_id
                        "name": "demo_age",       // filled with the value found in the export column (we can talk more about this)
                        "displayName" :  "age",   // filled with either measureText or answerText
                        "answerId": "41",           // the id of the answer if this is an AV of type Measure Answer
                        "measureId": "22",          // the id of the measure if this is an AV of type Measure
                        "measureTypeId" : "13"       // the type ID of the measure if this is an AV of type Measure
                    },{
                        "id" : "87",                // the value of assessment_variable.assessment_variable_id
                        "typeId" : "1",             // the value of assessment_variable.assessment_variable_type_id
                        "name": "demo_email",       // filled with the value found in the export column (we can talk more about this)
                        "displayName" :  "email",   // filled with either measureText or answerText
                        "answerId": "1",           // the id of the answer if this is an AV of type Measure Answer
                        "measureId": "2",          // the id of the measure if this is an AV of type Measure
                        "measureTypeId" : "3"       // the type ID of the measure if this is an AV of type Measure
                    },
                    {
                        "id" : "287",                // the value of assessment_variable.assessment_variable_id
                        "typeId" : "2",             // the value of assessment_variable.assessment_variable_type_id
                        "name": "demo_password",       // filled with the value found in the export column (we can talk more about this)
                        "displayName" :  "password",   // filled with either measureText or answerText
                        "answerId": "15",           // the id of the answer if this is an AV of type Measure Answer
                        "measureId": "62",          // the id of the measure if this is an AV of type Measure
                        "measureTypeId" : "93"       // the type ID of the measure if this is an AV of type Measure
                    },
                    {
                        "id" : "872",                // the value of assessment_variable.assessment_variable_id
                        "typeId" : "3",             // the value of assessment_variable.assessment_variable_type_id
                        "name": "demo_salary",       // filled with the value found in the export column (we can talk more about this)
                        "displayName" :  "salary",   // filled with either measureText or answerText
                        "answerId": "41",           // the id of the answer if this is an AV of type Measure Answer
                        "measureId": "22",          // the id of the measure if this is an AV of type Measure
                        "measureTypeId" : "13"       // the type ID of the measure if this is an AV of type Measure
                    },
                    {
                        "id" : "721",                // the value of assessment_variable.assessment_variable_id
                        "typeId" : "4",             // the value of assessment_variable.assessment_variable_type_id
                        "name": "demo_birthday",       // filled with the value found in the export column (we can talk more about this)
                        "displayName" :  "birthday",   // filled with either measureText or answerText
                        "answerId": "41",           // the id of the answer if this is an AV of type Measure Answer
                        "measureId": "22",          // the id of the measure if this is an AV of type Measure
                        "measureTypeId" : "13"       // the type ID of the measure if this is an AV of type Measure
                    }
                ];

                var toggleAssessmentVariableSelectionTable = function() {
                    $scope.showAssessmentVariableSelectionTable = ($scope.showAssessmentVariableSelectionTable)? false: true;
                };

                $scope.showAssessmentVariableSelectionTable = false;
                $scope.assessmentVariableTypes = [
                    {id: 1, type: "Measure", displayName: "Question"},
                    {id: 2, type: "Measure Answer", displayName: "Question"},
                    {id: 3, type: "Custom", displayName: "Custom"},
                    {id: 4, type: "Formula", displayName: "Formula"}
                ];

                $scope.selectAssessmentVariable = function(assessmentVariable) {
                    console.log("assessmentVariable: " + assessmentVariable);

                    $scope.assessmentVariable = assessmentVariable;
                    $scope.displayAssessmentVariableSelectionTable();
                };

                $scope.displayAssessmentVariableSelectionTable = function (){
                    toggleAssessmentVariableSelectionTable();
                };

                $scope.filterAssessmentVariableTable = function() {
                    /*var filteredAssessmentVariables = [];
                    if(Object.isDefined($scope.selectedAssessmentVariableType)){
                        filteredAssessmentVariables = data.filter(function (assessmentVariable) {
                            if(assessmentVariable.typeId === $scope.selectedAssessmentVariableType.id) {
                                return true;
                            }

                            return false;
                        });
                    };*/
                };

                $scope.types = function (column) {
                    var def = $q.defer(),
                        arr = [],
                        types = [];

                    angular.forEach(data, function(existingAssessmentVariable){
                        if (arr.indexOf(existingAssessmentVariable) === -1) {
                            arr.push(existingAssessmentVariable);
                            types.push(existingAssessmentVariable);
                        }
                    });

                    def.resolve(types);
                    return def;
                };

                $scope.filters = {
                    type: ''
                };

                function setTable() {
                    $scope.tableParams = new ngTableParams({
                        page: 1, // show first page
                        count: 10, // count per page
                        filter: $scope.filters,
                        sorting: {
                            name: 'asc'
                        }/*,
                        reload: function () {},
                        settings: function () {
                            return {};
                        }*/
                    }, {
                        counts: [],
                        total: data.length,
                        getData: function ($defer, params) {
                            // use build-in angular filter
                            //params.total(data.length);
                            //var filteredData = params.filter() ? $filter('filter')(data, params.filter()) : data;
                            var orderedData = params.sorting() ? $filter('orderBy')(data, params.orderBy()) : data;
                            params.total(orderedData.length); // set total for recalc pagination
                            $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                        }
                    });
                };

                setTable();

            }],
            templateUrl: 'resources/editors/js/app/directives/assessmentVariableSelection/assessmentVariableSelection.html',
            link: function(scope, element, attributes, controller) {
                element.addClass("assessmentVariableSelection");
            }
        };

    }]);
})(angular);