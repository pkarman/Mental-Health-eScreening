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
	            link: function (scope) {

		            scope.searchObj = {type: ''};

		            scope.tableParams = AssessmentVariableService.getTableParams(scope.searchObj);

		            scope.tableParams.settings().$scope = scope;

		            scope.assessmentVariableTypes = ['Question', 'Custom', 'Formula'];

		            scope.select = function(e, av) {

			            //e.stopPropagation();
			            //e.preventDefault();

		                if(av.id !== scope.assessmentVariable.id) {
			                // This is needed to trigger a change on $scope.$watch (unknown hack)
			                angular.copy(av, scope.assessmentVariable);

			                // This is also needed to the populate the $scope.assessmentVariable
			                scope.assessmentVariable = av;
		                }

		                // Hide table
			            scope.show = false;
	                };

	            },
	            templateUrl: 'resources/editors/js/app/directives/assessmentVariableTable/assessmentVariableTable.html'
	        };

	    }]
    );
})(angular);
