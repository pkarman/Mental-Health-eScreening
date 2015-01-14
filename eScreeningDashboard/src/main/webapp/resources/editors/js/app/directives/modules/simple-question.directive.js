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

				scope.answerTypes = [
					{ name: 'Other', value: 'other' },
					{ name: 'None', value: 'none' }
				];

				scope.sortableAnswerOptions = {
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
