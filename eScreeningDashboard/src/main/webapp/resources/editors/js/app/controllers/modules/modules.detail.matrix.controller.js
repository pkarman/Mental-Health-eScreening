(function() {
	'use strict';
	angular.module('Editors').controller('ModulesDetailMatrixController', ['$scope', '$state', function ($scope, $state) {

		if (!$scope.question) {
			// Look up the selected question by the id passed into the parameter
			MeasureService.one($stateParams.questionId).get().then(function(question){
				$scope.question = question;
			});
		}

		$scope.sortableChildAnswerOptions = {
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

	}]);

})();
