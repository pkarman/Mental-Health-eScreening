(function() {
	'use strict';
	angular.module('Editors').controller('ModulesDetailOneController', ['$scope', '$stateParams', function($scope, $stateParams) {

		if (!$scope.question) {
			// Look up the selected question by the id passed into the parameter
			/* TODO
			 $scope.survey.one('questions', $stateParams.selectedQuestionId).get().then(function(question) {
			 console.log(question);
			 });
			 */
			$scope.question =_.find($scope.surveyPages[0].questions, function(question) {
				return question.id === +$stateParams.selectedQuestionId;
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

	}]);
})();
