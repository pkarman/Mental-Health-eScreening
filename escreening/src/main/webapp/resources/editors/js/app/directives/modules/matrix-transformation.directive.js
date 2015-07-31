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

					scope.selectedMatrixAnswers = [];
					scope.selectedMatrixQuestions = [];

					// Get the childQuestions and childQuestion answers for single and multi-matrix variables
					MeasureService.one(scope.assessmentVariable.parentMeasureId || scope.assessmentVariable.measureId).get().then(function(measure) {
						scope.matrixQuestions = measure.childQuestions;

						scope.matrixAnswers = [];
						_.each(scope.matrixQuestions, function(question) {
							question.hasOther= false;
							var setAnswers = scope.matrixAnswers.length == 0;
							_.each(question.answers, function(answer){
								if(setAnswers && answer.type !== "other"){
									scope.matrixAnswers.push(answer);
								}
								if(answer.type === "other"){
									question.hasOther = true;
								}
							});
						});
					});

					scope.updateSelectedMatrixAnswers = function updateSelectedMatrixAnswers() {
						scope.selectedMatrixAnswers = $filter('filter')(scope.matrixAnswers, {checked: true});
						scope.updateTransformation();
					};

					scope.updateSelectedMatrixQuestions = function updateSelectedMatrixQuestions() {
						scope.selectedMatrixQuestions = [];
						_.each(scope.matrixQuestions, function(question){
							if(question.checked){
								scope.selectedMatrixQuestions.push(question);
							}
							//clear the text contents if not needed
							if(!question.checked || question.useOther){
								question.outputText = '';
							}
						});
						
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
							var index = 0;
							_.each(question.answers, function(answer) {
								if(answer.type !== "other"){
									if (_.indexOf(indexes, index) !== -1) {
										answerIds.push(answer.id);
									}
									//we only count indexes for non-other answers
									index++;
								}
							});
						});

						// Build an array of selected question objects
						_.each(scope.selectedMatrixQuestions, function(question) {
							var obj = {};
							if(question.useOther){
								//if this is changed update ExpressionExtentionUtil
								obj[question.id] = "$other_value$";
							}
							else{
								obj[question.id] = question.outputText || '';
							}
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
