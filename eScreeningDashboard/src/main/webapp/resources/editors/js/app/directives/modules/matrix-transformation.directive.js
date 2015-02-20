(function () {
	'use strict';

	angular.module('Editors')
		.directive('matrixTransformation', function(MeasureService) {

			return {
				restrict: 'E',
				scope: {
					assessmentVariable: '=',
					applyTransformations: '&',
					dismiss: '&'
				},
				templateUrl: 'resources/editors/partials/modules/matrix-transformation.html',
				link: function(scope) {

					// Get the childQuestions and childQuestion answers for single and multi-matrix variables
					MeasureService.one(scope.assessmentVariable.parentMeasureId || scope.assessmentVariable.measureId).get().then(function(measure) {
						scope.matrixQuestions = measure.childQuestions;

						scope.matrixAnswers = measure.childQuestions[0].answers;
					});

					scope.updateSelectedMatrixQuestions = function updateSelectedMatrixQuestions() {
						scope.slectedMatrixQuestions = $filter('filter')(scope.matrixQuestions, {checked: true});
					};

					scope.updateSelectedMatrixAnswers = function updateSelectedMatrixAnswers() {
						scope.slectedMatrixAnswers = $filter('filter')(scope.matrixAnswers, {checked: true});
					};
				}
			};

		});

})();
