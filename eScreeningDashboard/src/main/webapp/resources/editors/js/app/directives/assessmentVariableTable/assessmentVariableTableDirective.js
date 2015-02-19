/**
 * Created by pouncilt on 10/21/14.
 */
(function(angular) {
    "use strict";

    angular.module('EscreeningDashboardApp.services.assessmentVariable')
	    .directive('mheAssessmentVarTblDir', ['AssessmentVariableService', 'AssessmentVariableManager', 'MeasureService', function(AssessmentVariableService, AssessmentVariableManager, MeasureService) {

	        return {
	            restrict: 'EA',
	            scope: {
		            assessmentVariable: '=',
		            show: '='
	            },
	            link: function (scope, element) {

		            scope.searchObj = {type: ''};

		            scope.tableParams = AssessmentVariableService.getTableParams(scope.searchObj);

		            scope.tableParams.settings().$scope = scope;

		            scope.assessmentVariableTypes = ['Question', 'Custom', 'Formula'];

					scope.table = {
						show: true
					};

					scope.$watch('show', function(newVal, oldVal) {
						if (oldVal === false && newVal === true) {
							scope.table.show = true;
						}
					});

		            scope.select = function(e, av) {

			            e.stopPropagation();

		                if(av.id !== scope.assessmentVariable.id) {

			                // This is needed to trigger a change on $scope.$watch (unknown hack)
			                angular.copy(av, scope.assessmentVariable);

			                // This is also needed to the populate the $scope.assessmentVariable
			                scope.assessmentVariable = av;
		                }

						if (!scope.assessmentVariable.transformations) {
							AssessmentVariableManager.setTransformations(scope.assessmentVariable);
						}

		                // Hide table
						if (!scope.table.show) {
							scope.show = false;
						}

						scope.table.show = !scope.assessmentVariable.transformations;

						scope.type = AssessmentVariableManager.getType(scope.assessmentVariable);

			            scope.tableParams.reload();
	                };

					scope.dismiss = function dismiss() {
						scope.show = false;
					};

		            element.bind('click', function(e) {
			            // Prevent bubbling
			            e.stopPropagation();
		            });

					function dostuff(av) {

						var validations = {};
						var item = {};

						if (av.measureTypeId == 1) {

							// Get the validations for freetext
							MeasureService.one(av.measureId).getList('validations').then(function (validations) {
								angular.forEach(validations, function(validation) {
									AssessmentVariableManager.setValidations(validations, validation);
								});
							});

						} else if (av.measureTypeId === 2 || av.measureTypeId === 3) {

							// Get the answer list for multi or single select questions
							MeasureService.one(av.measureId).getList('answers').then(function (answers) {
								item.measureAnswers = answers;
							});

							// Ensure right variable is numeric
							if (item.right) item.right.content = parseInt(item.right.content);
						}
					}

				},
	            templateUrl: 'resources/editors/js/app/directives/assessmentVariableTable/assessmentVariableTable.html'
	        };

	    }]
    );
})(angular);
