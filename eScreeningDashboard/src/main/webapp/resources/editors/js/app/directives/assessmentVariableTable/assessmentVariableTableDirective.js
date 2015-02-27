/**
 * Created by pouncilt on 10/21/14.
 */
(function(angular) {
    "use strict";

    angular.module('EscreeningDashboardApp.services.assessmentVariable')
	    .directive('mheAssessmentVarTblDir', ['AssessmentVariableService', 'AssessmentVariableManager', 'MeasureService', 'ngTableParams', '$filter', function(AssessmentVariableService, AssessmentVariableManager, MeasureService, ngTableParams, $filter) {

	        return {
	            restrict: 'EA',
	            scope: {
		            assessmentVariable: '=',
		            show: '=',
					block: '='
	            },
				templateUrl: 'resources/editors/js/app/directives/assessmentVariableTable/assessmentVariableTable.html',
	            link: function (scope, element) {

					scope.toggles = {
						list: true,
						transformations: false
					};

		            scope.searchObj = {type: ''};

					scope.assessmentVariableTypes = ['Question', 'Custom', 'Formula'];

					scope.assessmentVariables = AssessmentVariableService.getLastCachedResults().$object;

		            scope.tableParams = new ngTableParams({
						page: 1, // show first page
						count: 10, // count per page
						filter: scope.searchObj
					}, {
						counts: [],
						total: 0,
						getData: function ($defer, params) {
							var avs,
								filteredData;

							if (scope.block.type && scope.block.type === 'table') {
								// Only display table questions for table block
								filteredData = [];
								_.each(scope.assessmentVariables, function(av) {
									if (av.getMeasureTypeName() === 'table') {
										filteredData.push(av);
									}
								});
							} else {
								filteredData = params.filter() ? $filter('filter')(scope.assessmentVariables, params.filter()) : scope.assessmentVariables;
							}

							avs = filteredData.slice((params.page() - 1) * params.count(), params.page() * params.count());

							params.total(filteredData.length); // set total for recalc pagination
							$defer.resolve(avs);
						},
						$scope: { $data: {} }
					});

		            scope.tableParams.settings().$scope = scope;

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
							AssessmentVariableManager.setTransformations(scope.assessmentVariable).then(function(transformations) {
							});
						}

						if (av.getMeasureTypeName() === 'table') {
							// Get the childQuestions and childQuestion answers for single and multi-matrix variables
							MeasureService.one(av.measureId).get().then(function(measure) {
								scope.childQuestions = measure.childQuestions;
							});
						}

						if (av.id !== 6 && scope.transformationName === 'single-select') {
							scope.show = false;
						} else if (scope.transformationName === 'freetext') {
							// Doing this manually here because setting transformations is not working as intended
							MeasureService.one(scope.assessmentVariable.measureId).getList('validations').then(function(validations) {
								var found;
								_.each(validations, function (validation) {
									if (validation.value === 'date') {
										found = true;
									}
								});

								if (found) {
									scope.toggles.list = false;
									scope.toggles.transformations = true;
								} else {
									scope.show = false;
								}
							});
						} else {
							scope.toggles.list = false;
							scope.toggles.transformations = true;
						}

			            scope.tableParams.reload();
	                };

					scope.applyTransformations = function applyTransformations(newScope) {

						scope.show = false;
						scope.toggles.list = false;
						scope.toggles.transformations = false;

						// Apply select transformation to AV
						if (newScope.transformationType) {
							scope.assessmentVariable.transformations = [newScope.transformationType];
							scope.assessmentVariable.name = newScope.transformationType.name + '_' + scope.assessmentVariable.displayName;
						}

						// Apply AV to block.table for table block types
						if (scope.block.type === 'table') {
							scope.block.table = scope.assessmentVariable;
						}
					};

					scope.dismiss = function dismiss() {
						scope.show = false;
						scope.toggles.transformations = false;
					};

		            element.bind('click', function(e) {
			            // Prevent bubbling
			            e.stopPropagation();
		            });

				}
	        };

	    }]
    );
})(angular);
