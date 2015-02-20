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

				scope.$watch('question', function(question) {
					if (question && question.answers) {
						// Initialize displayOrder property
						_.each(question.answers, function(answer, index) {
							answer.displayOrder = index + 1;
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
						_.each(answers, function(answer, index) {
							answer.displayOrder = index + 1;
						});
					}
				};

				scope.addAnswer = function addAnswer() {
					scope.question.answers.push(Answer.extend({displayOrder: scope.question.answers.length + 1}));
				};

				scope.deleteAnswer = function deleteAnswer(index) {
					scope.question.answers.splice(index, 1);
				};

			}
		};

	}]);

})();
