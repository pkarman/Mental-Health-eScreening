/**
 * Created by pouncilt on 10/21/14.
 */
(function(angular) {
    "use strict";

    angular.module('EscreeningDashboardApp.services.assessmentVariable')
	    .directive('mheAssessmentVarTblDir', ['AssessmentVariableService', 'AssessmentVariableManager', 'MeasureService', 'TemplateBlockService', 'ngTableParams', '$filter', function(AssessmentVariableService, AssessmentVariableManager, MeasureService, TemplateBlockService, ngTableParams, $filter) {

	        return {
	            restrict: 'EA',
	            scope: {
		            assessmentVariable: '=',
		            show: '=',
					block: '='
	            },
				templateUrl: 'resources/editors/js/app/directives/assessmentVariableTable/assessmentVariableTable.html',
	            link: function (scope, element) {

					var parentBlock = AssessmentVariableService.parentBlock || {};

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

							if (scope.block && scope.block.type === 'table') {
								// Only display table questions for table block
								filteredData = [];
								_.each(scope.assessmentVariables, function(av) {
									if (av.getMeasureTypeName() === 'table') {
										filteredData.push(av);
									}

								});
							} else {
								if (parentBlock && parentBlock.type === 'table') {
									filteredData = $filter('filter')(scope.assessmentVariables, {parentMeasureId: parentBlock.table.content.measureId});
								} else {
									filteredData = params.filter() ? $filter('filter')(scope.assessmentVariables, params.filter()) : scope.assessmentVariables;

									// Remove child table AVs
									_.each(filteredData, function (av) {
										var parent;
										if (av && av.parentMeasureId) {
											parent = _.find(scope.assessmentVariables, function (fd) {
												return fd.measureId === av.parentMeasureId;
											});

											if (parent && parent.measureTypeId === 4) {
												filteredData = $filter('filter')(scope.assessmentVariables, {parentMeasureId: '!' + parent.measureId});
											}
										}
									});
								}
							}

							avs = filteredData.slice((params.page() - 1) * params.count(), params.page() * params.count());

							params.total(filteredData.length); // set total for recalc pagination
							$defer.resolve(avs);
						},
						$scope: { $data: {} }
					});

		            scope.tableParams.settings().$scope = scope;

		            scope.select = function(e, av) {

			            if (e) e.stopPropagation();

		                if(!scope.assessmentVariable || av.id !== scope.assessmentVariable.id) {
			                // This is needed to trigger a change on in main.js for textAngular (unknown hack)
			                angular.copy(av, scope.assessmentVariable);
			                // This is also needed to the populate the $scope.assessmentVariable
			                scope.assessmentVariable = angular.copy(av);
		                }

						scope.transformationName = (scope.assessmentVariable.id === 6) ? 'appointment' : scope.assessmentVariable.getMeasureTypeName();

						// Do not apply transformations to parent table blocks
						// NOTE: Blocks are not passsed in when directive is called from textAngular
						if (!scope.block || scope.block.type !== 'table') {
							if (scope.assessmentVariable.transformations.length === 0) {
								AssessmentVariableManager.setTransformations(scope.assessmentVariable).then(function (transformations) {
								});
							}

							if (av.getMeasureTypeName() === 'table') {
								// Get the childQuestions table variables
								MeasureService.one(av.measureId).get().then(function (measure) {
									scope.childQuestions = measure.childQuestions;
								});
							}

							// Apply assessmentVariable to block even though it should be done via the two-way data binding
							if (scope.block) {
								scope.block.left.content = scope.assessmentVariable;
							}
						}

						if ((scope.assessmentVariable.type === 'Custom' && scope.assessmentVariable.id !== 6)
							|| scope.transformationName === 'single-select'
							|| (scope.block && scope.block.type === 'table')
							|| (scope.block && scope.assessmentVariable.getMeasureTypeName() === 'multi-select'
							|| scope.assessmentVariable.type === 'Formula')) {
							scope.show = false;
							scope.$emit('assessmentVariableSelected');

							// Apply AV to block.table for table block types even though it should be working from the view. . .
							if (scope.block && scope.block.type === 'table') {
								scope.block.table.content = scope.assessmentVariable;
							}

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
									scope.$emit('assessmentVariableSelected');
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
							scope.assessmentVariable.name = newScope.transformationType.name + '_' + scope.assessmentVariable.getName();

							if (newScope.transformationType.params) {

								// Convert delimitedMatrixQuestions rowAvIdToOutputMap array into a list
								if (scope.assessmentVariable.transformations[0].name === 'delimitedMatrixQuestions') {
									scope.assessmentVariable.transformations[0].params[0].join(',');
								}

								// Convert params into strings for freeMarker
								scope.assessmentVariable.transformations[0].params = _.map(newScope.transformationType.params, function(param) {
									if(angular.isString(param) || angular.isNumber(param)){ return param; }
									return JSON.stringify(param);
								});
							}
							// Remove displayName property which is not to be persisted
							delete scope.assessmentVariable.transformations[0].displayName;
						} else {
							if (scope.assessmentVariable.getMeasureTypeName() === 'table') {
								scope.assessmentVariable.transforamtions = [];
							}
						}

						TemplateBlockService.getVariableHash()[scope.assessmentVariable.id] = scope.assessmentVariable;

						scope.$emit('assessmentVariableSelected');
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
