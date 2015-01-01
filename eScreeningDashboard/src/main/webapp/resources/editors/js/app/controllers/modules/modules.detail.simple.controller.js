(function() {
	'use strict';
	angular.module('Editors').controller('ModulesDetailSimpleController', ['$scope', '$stateParams', 'Answer', function($scope, $stateParams, Answer) {

		if (!$scope.question) {
			// Look up the selected question by the id passed into the parameter
			/* TODO
			 $scope.survey.one('questions', $stateParams.questionId).get().then(function(question) {
			 console.log(question);
			 });
			 */
			$scope.question =_.find($scope.surveyPages[0].questions, function(question) {
				return question.id === +$stateParams.questionId;
			});
		}

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
