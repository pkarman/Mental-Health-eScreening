(function () {
	'use strict';

	angular.module('Editors')
		.directive('matrixTransformation', function(MeasureService, $filter) {

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

					scope.updateSelectedMatrixAnswers = function updateSelectedMatrixAnswers() {
						scope.selectedMatrixAnswers = $filter('filter')(scope.matrixAnswers, {checked: true});
						scope.updateTransformation();
					};

					scope.updateSelectedMatrixQuestions = function updateSelectedMatrixQuestions() {
						scope.selectedMatrixQuestions = $filter('filter')(scope.matrixQuestions, {checked: true});
						scope.updateTransformation();
					};

					scope.updateTransformation = function updateTransformation() {

						var indexes = [],
							answerIds = [],
							questions =[];

						// Build an array of indexes the first questions selected answers
						_.each(scope.matrixAnswers, function(answer, index) {
							if (answer.checked)  {
								indexes.push(index);
							}
						});

						// Build an array of answers IDs from each selected question and selected answers
						_.each(scope.selectedMatrixQuestions, function(question) {
							_.each(question.answers, function(answer, index) {
								if (_.indexOf(indexes, index) !== -1) {
									answerIds.push(answer.id);
								}
							});
						});

						// Build an array of selected question objects
						_.each(scope.selectedMatrixQuestions, function(question) {
							var obj = {};
							obj[question.id] = question.outputText || '';
							questions.push(obj);
						});

						// questions is a String representing a map from AV ID to the output text for that question.
						// Example: "{"234":"question one's text", "445":"question two's text"}"
						// answerIds a string representing an array of column (answer) AV IDs e.g. "[555,333,666]".
						// The array should contain all of the assessment variable IDs for each answer for each question in the matrix question.
						scope.transformationType = {
							name: 'delimitedMatrixQuestions',
							params: [questions, answerIds]
						};
					};

				}
			};

		});

})();
