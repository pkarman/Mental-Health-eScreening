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

					var transformations = {
						custom: [
							{
								name: 'delimit',
								params: {
									prefix: ',',
									lastPrefix: 'and',
									suffix: '""',
									includeSuffixAtEnd: true,
									defaultValue: ''
								}
							}
						],
						freeText: [
							{ name: 'yearsFromDate' }
						],
						matrix: [
							{
								name: 'delimitedMatrixQuestions',
								params: {
									lastPrefix: 'and'
								}
							}
						],
						table: [
							{ name: 'numberOfEntries' },
							{
								name: 'delimitTableField',
								params: {
									prefix: ',',
									lastPrefix: 'and',
									suffix: '""',
									includeSuffixAtEnd: true,
									defaultValue: ''
								}
							}
						]
					};

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
							updateTransformations(scope.assessmentVariable);
							console.log(scope.assessmentVariable.transformations);
						}

		                // Hide table
						if (!scope.table.show) {
							scope.show = false;
						}

						scope.table.show = false;

			            scope.tableParams.reload();
	                };

					scope.dismiss = function dismiss() {
						scope.show = false;
					};

		            element.bind('click', function(e) {
			            // Prevent bubbling
			            e.stopPropagation();
		            });

					function updateTransformations(av) {
						switch (av.typeId) {
							case 1:
								av.transformations = transformations.freeText;
								break;
							case 2:
								av.transformations = transformations.custom;
								break;
							case 3:
								av.transformations = transformations.custom;
								break;
							case 4:
								av.transformations = transformations.table;
								break;
						}
					}

				},
	            templateUrl: 'resources/editors/js/app/directives/assessmentVariableTable/assessmentVariableTable.html'
	        };

	    }]
    );
})(angular);
