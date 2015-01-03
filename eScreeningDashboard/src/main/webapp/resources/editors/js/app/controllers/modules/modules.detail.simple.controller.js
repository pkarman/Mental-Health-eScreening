(function() {
	'use strict';
	angular.module('Editors').controller('ModulesDetailSimpleController', ['$scope', '$stateParams', 'Answer', function($scope, $stateParams, Answer) {

		$scope.sortableAnswerOptions = {
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

		$scope.addAnswer = function addAnswer() {
			$scope.question.answers.push(Answer.create());
		};

		$scope.deleteAnswer = function deleteAnswer(index) {
			$scope.question.answers.splice(index, 1);
		};

	}]);
})();
