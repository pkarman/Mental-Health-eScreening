/**
 * Created by pouncilt on 10/21/14.
 */
(function(angular) {
    "use strict";

    angular.module('EscreeningDashboardApp.services.assessmentVariable')
	    .directive('mheAssessmentVarTblDir', ['AssessmentVariableService', function(AssessmentVariableService) {

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

					scope.transformation = {};

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

		                // Hide table
						if (!scope.table.show) {
							scope.show = false;
						}

						scope.table.show = false;

			            scope.tableParams.reload();
	                };

					scope.dismiss = function dismiess() {
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
