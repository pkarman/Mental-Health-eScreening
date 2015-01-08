(function() {
	'use strict';

	angular.module('Editors').directive('matrixQuestion', ['Answer', 'Question', function(Answer, Question) {

		return {
			restrict: 'EA',
			scope: {
				question: '='
			},
			templateUrl: 'resources/editors/partials/modules/matrix-question.html',
			link: function(scope) {

				scope.sortableAnswerOptions = {
					'ui-floating': false,
					cancel: '.unsortable',
					items: 'li:not(.unsortable)',
					stop: function(e, ui) {
						var answers = ui.item.scope().$parent.question.childQuestions.answers;
						for (var index in answers) {
							answers[index].displayOrder = index;
						}
					}
				};

				scope.sortableQuestionOptions = {
					'ui-floating': false,
					cancel: '.unsortable',
					items: 'li:not(.unsortable)',
					stop: function(e, ui) {
						var questions = ui.item.scope().$parent.question.childQuestions;
						for (var index in questions) {
							questions[index].displayOrder = index;
						}
					}
				};

				scope.answers = scope.question.childQuestions[0].answers;

				scope.addAnswer = function addAnswer() {
					scope.question.childQuestions[0].answers.push(Answer.create());
				};

				scope.deleteAnswer = function deleteAnswer(parentIndex, index) {
					scope.question.childQuestions[parentIndex].answers.splice(index, 1);
				};

				scope.addQuestion = function addQuestion() {
					scope.question.childQuestions.push(Question.create());
				};

				scope.deleteQuestion = function deleteQuestion(index) {
					scope.question.childQuestions.splice(index, 1);
				}
			}
		};

	}]);

})();
