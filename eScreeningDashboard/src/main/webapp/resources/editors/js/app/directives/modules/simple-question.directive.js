(function() {
	'use strict';

	angular.module('Editors').directive('simpleQuestion', ['Answer', function(Answer) {

		return {
			restrict: 'EA',
			scope: {
				question: '='
			},
			templateUrl: 'resources/editors/partials/modules/simple-question.html',
			link: function(scope) {

				// Hack to get tableAnswers onto answers array as a result of the server sending answers on tableAnswers
				scope.$watch('question', function(question) {
					if (question && question.tableAnswers && question.tableAnswers.length && (!question.answers || !question.answers.length)) {
						question.answers = [];
						_.each(question.tableAnswers, function(answers) {
							// Server sends back and array of array of objects for no good reason
							_.each(answers, function(answer, index) {
								var clonedAnswer = _.clone(answer);
								clonedAnswer.displayOrder = index;
								question.answers.push(clonedAnswer);
							});
						});
						// Remove the tableAnswers so they don't override the answers
						delete question.tableAnswers;
					}

					if (question && question.answers) {
						// Initialize displayOrder property
						_.each(question.answers, function(answer, index) {
							answer.displayOrder = index;
						});
					}
				});

				scope.answerTypes = [
					{ name: 'Other', value: 'other' },
					{ name: 'None', value: 'none' }
				];

				scope.sortableAnswerOptions = {
					handle: '.glyphicon-align-justify',
					'ui-floating': false,
					cancel: '.unsortable',
					items: 'li:not(.unsortable)',
					stop: function(e, ui) {
						var answers = ui.item.scope().$parent.question.answers;
						for (var index in answers) {
							answers[index].displayOrder = +index;
						}
					}
				};

				scope.addAnswer = function addAnswer() {
					scope.question.answers.push(Answer.extend({displayOrder: scope.question.answers.length}));
				};

				scope.deleteAnswer = function deleteAnswer(index) {
					scope.question.answers.splice(index, 1);
				};

			}
		};

	}]);

})();
