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
				templateUrl: 'resources/editors/js/app/directives/assessmentVariableTable/assessmentVariableTable.html',
	            link: function (scope, element) {

					scope.toggles = {
						list: true,
						transformations: false
					};

		            scope.searchObj = {type: ''};

		            scope.tableParams = AssessmentVariableService.getTableParams(scope.searchObj);

		            scope.tableParams.settings().$scope = scope;

		            scope.assessmentVariableTypes = ['Question', 'Custom', 'Formula'];

					scope.freetextNone = false;

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

					scope.applyTransformations = function applyTransformations(av) {

						// This is not working because the ng-if is giving the form its own scope
						if (scope.freetextNone) {
							av.transformations = null;
						}

						scope.show = false;
						scope.toggles.list = false;
						scope.toggles.transformations = false;
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
