/**
 * Created by pouncilt on 10/21/14.
 */
(function(angular) {
    "use strict";

    angular.module('EscreeningDashboardApp.services.assessmentVariable')
	    .directive('assessmentVarTbl', ['AssessmentVariableService', 'AssessmentVariableManager', 'MeasureService', 'TemplateBlockService', 'ngTableParams', '$filter', 'limitToWithEllipsisFilter', 
	                            function(AssessmentVariableService, AssessmentVariableManager, MeasureService, TemplateBlockService, ngTableParams, $filter, limitToWithEllipsisFilter) {

	        return {
	            restrict: 'EA',
	            scope: {
		            assessmentVariable: '=',
		            assessmentVariables: '=',
		            show: '=',
		            editorType: '=',   //e.g. "text", "table", "condition" (used for both condition blocks and rule expression), "graphicalTemplate"
		            allowTransformations: '='
	            },
				templateUrl: 'resources/editors/js/app/directives/assessmentVariableTable/assessmentVariableTable.html',
	            link: function (scope, element) {
	            	
	            	if(!scope.assessmentVariable){
	            		throw "Assessment variable is a required attribute";
	            	}
	            	
	            	if(!scope.editorType){
	            		throw "editor-type is a required attribute";
	            	}
					            	
					var parentBlock = AssessmentVariableService.parentBlock || {};
					scope.toggles = {
						list: true,
						transformations: false
					};
					scope.variablesLoaded = false;
		            scope.searchObj = {type: '', displayName:''};
					scope.assessmentVariableTypes = ['Question', 'Custom', 'Formula'];

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

							if (scope.editorType === 'table') {
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

		            //Please remove this when we can get the table we use to update when the model changes (i.e. assessmentVariables)
					scope.$watch('assessmentVariables',function(newVar, oldVar){
						if (!angular.equals(newVar, oldVar)) {
							scope.tableParams.reload();
						}
						scope.variablesLoaded = angular.isFunction(newVar.one);
					}, true);
					
		            scope.select = function(e, av) {
		            	scope.allowTransformations = angular.isUndefined(scope.allowTransformations) ? true : scope.allowTransformations;
		            	
			            if (e) e.stopPropagation();

		                if(!scope.assessmentVariable || av.id !== scope.assessmentVariable.id) {
			                // This is needed to trigger a change on in main.js for textAngular (unknown hack)
			                angular.copy(av, scope.assessmentVariable);
		                }
		                
						scope.transformationName = (scope.assessmentVariable.id === 6) ? 'appointment' : scope.assessmentVariable.getMeasureTypeName();

						// NOTE: Blocks are not passed in when directive is called from textAngular
						if (scope.allowTransformations) {
							
							AssessmentVariableManager.setTransformations(scope.assessmentVariable, scope.editorType).then(
								function(transformations){
									
									if (transformations.length !== 0) {
										
										if (av.getMeasureTypeName() === 'table') {
											// Get the childQuestions table variables
											MeasureService.one(av.measureId).get().then(function (measure) {
												scope.childQuestions = measure.childQuestions;
											});
										}
										
										scope.show = true;
										scope.toggles.list = false;
										scope.toggles.transformations = true;
									}
									else{
										scope.show = false;
									}
									
									scope.tableParams.reload();
								});														
						}
						else{
							scope.show = false;
							scope.tableParams.reload();
						}
	                };

					scope.applyTransformations = function applyTransformations(newScope) {
						scope.toggles.list = false;
						scope.toggles.transformations = false;

						// Apply select transformation to AV
						if (newScope.transformationType 
								|| scope.transformationName == 'mulit-select' 
								|| scope.transformationName === 'assessment') {
							
							scope.assessmentVariable.transformations = [newScope.transformationType];
							scope.assessmentVariable.name = newScope.transformationType.name + '_' + scope.assessmentVariable.getName();

							if (newScope.transformationType.params) {

								// Convert delimitedMatrixQuestions rowAvIdToOutputMap array into a list
								if (scope.assessmentVariable.transformations[0].name === 'delimitedMatrixQuestions') {
									var newParam = {};
									
									//translate the array of object into one object with all keys (
									scope.assessmentVariable.transformations[0].params[0].map(
											function(pair){ angular.extend(newParam, pair);});
									
									scope.assessmentVariable.transformations[0].params[0] = newParam;
								}

								// Convert params into strings for freeMarker
								scope.assessmentVariable.transformations[0].params = _.map(newScope.transformationType.params, function(param) {
									if(angular.isString(param) || angular.isNumber(param)){ return param; }
									return JSON.stringify(param);
								});
							}

							// Remove displayName property which is not to be persisted
							delete scope.assessmentVariable.transformations[0].displayName;

						} else if (scope.assessmentVariable.getMeasureTypeName() === 'table' || scope.assessmentVariable.measureTypeId === 1) {
							// Reset transformations to empty array
							scope.assessmentVariable.transformations = [];
						}

						TemplateBlockService.addVariableToHash(scope.assessmentVariable);
						scope.show = false;
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
