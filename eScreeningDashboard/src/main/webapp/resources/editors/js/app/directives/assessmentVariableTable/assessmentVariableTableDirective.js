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

						scope.transformationName = (scope.assessmentVariable.id === 6) ? 'appointment' : scope.assessmentVariable.getMeasureTypeName();

						if (!scope.assessmentVariable.transformations) {
							AssessmentVariableManager.setTransformations(scope.assessmentVariable);
						}

		                // Hide table
						if (!scope.table.show) {
							scope.show = false;
						}

						scope.table.show = !scope.assessmentVariable.transformations;

			            scope.tableParams.reload();
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
