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
					if (question && question.tableAnswers.length && !question.answers.length) {
						_.each(question.tableAnswers, function(answer) {
							question.answers.push(answer[0]);
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
							answers[index].displayOrder = index;
						}
					}
				};

				scope.addAnswer = function addAnswer() {
					scope.question.answers.push(Answer.extend({}));
				};

				scope.deleteAnswer = function deleteAnswer(index) {
					scope.question.answers.splice(index, 1);
				};


			}
		};

	}]);

})();
