/**
 * Created by pouncilt on 10/21/14.
 */
(function(angular) {
    "use strict";

    angular.module('EscreeningDashboardApp.services.assessmentVariable')
	    .directive('mheAssessmentVarTblDir', ['AssessmentVariableService', 'AssessmentVariableManager', 'MeasureService', '$filter', function(AssessmentVariableService, AssessmentVariableManager, MeasureService, $filter) {

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

						scope.transformationType = (scope.assessmentVariable.id === 6) ? 'appointment' : scope.assessmentVariable.getMeasureTypeName();

						if (!scope.assessmentVariable.transformations) {
							AssessmentVariableManager.setTransformations(scope.assessmentVariable);
						}

		                // Hide table
						if (!scope.table.show) {
							scope.show = false;
						}

						scope.table.show = !scope.assessmentVariable.transformations;

						// Get the childQuestions and childQuestion answers for single and multi-matrix variables
						if (scope.assessmentVariable.measureTypeId === 6 || scope.assessmentVariable.measureTypeId === 7) {
							var measureId = scope.assessmentVariable.parentMeasureId || scope.assessmentVariable.measureId;

							MeasureService.one(measureId).get().then(function(measure) {
								scope.matrixQuestions = measure.childQuestions;

								scope.matrixAnswers = measure.childQuestions[0].answers;
							});
						}

			            scope.tableParams.reload();
	                };

					scope.updateSelectedMatrixQuestions = function updateSelectedMatrixQuestions() {
						scope.slectedMatrixQuestions = $filter('filter')(scope.matrixQuestions, {checked: true});
					};

					scope.updateSelectedMatrixAnswers = function updateSelectedMatrixAnswers() {
						scope.slectedMatrixAnswers = $filter('filter')(scope.matrixAnswers, {checked: true});
					};

					scope.dismiss = function dismiss() {
						scope.show = false;
					};

		            element.bind('click', function(e) {
			            // Prevent bubbling
			            e.stopPropagation();
		            });

				},
	            templateUrl: 'resources/editors/js/app/directives/assessmentVariableTable/assessmentVariableTable.html'
	        };

	    }]
    );
})(angular);
