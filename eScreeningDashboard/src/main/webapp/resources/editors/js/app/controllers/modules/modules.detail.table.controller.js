(function() {
	'use strict';
	angular.module('Editors').controller('ModulesDetailTableController', ['$scope', '$modal', 'Question', function($scope, $modal, Question) {

		$scope.addTableQuestion = function addTableQuestion() {

			var modalInstance = $modal.open({
				templateUrl: 'resources/editors/partials/modules/table-question-modal.html',
				controller: function($scope) {

					$scope.question = Question.create();

					$scope.questionTypes = [
						{id: 0, name: "freeText", displayName: "Free Text"},
						{id: 1, name: "selectOne", displayName: "Select One"},
						{id: 2, name: "selectMulti", displayName: "Select Multi"}
					];
				}
			});
		}

	}]);
})();
