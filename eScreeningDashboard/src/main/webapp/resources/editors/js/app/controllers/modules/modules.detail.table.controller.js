(function() {
	'use strict';
	angular.module('Editors').controller('ModulesDetailTableController', ['$scope', '$modal', 'Question', 'Answer', function($scope, $modal, Question, Answer) {

		$scope.$watch('question', function(question) {
			if (question && !question.answers.length) {
				$scope.question.answers.push(Answer.extend({type: 'none'}));
			}
		});

		$scope.sortableQuestionOptions = {
			handle: '.glyphicon-align-justify',
			'ui-floating': false,
			cancel: '.unsortable',
			items: 'li:not(.unsortable)',
			stop: function(e, ui) {
				// Update the display order
				var questions = ui.item.scope().$parent.question.childQuestions;
				for (var index in questions) {
					questions[index].displayOrder = +index;
				}
			}
		};

		$scope.updateChildQuestion = function updateChildQuestion(question) {

			var modalInstance = $modal.open({
				templateUrl: 'resources/editors/partials/modules/table-question-modal.html',
				resolve: {
					tableQuestion: function() {
						return $scope.question
					}
				},
				controller: function($scope, $modalInstance, tableQuestion) {

					$scope.tableQuestion = tableQuestion;

					$scope.question = question || Question.extend({});

					$scope.questionTypes = [
						{id: 0, name: "freeText", displayName: "Free Text"},
						{id: 1, name: "selectOne", displayName: "Select One"},
						{id: 2, name: "selectMulti", displayName: "Select Multi"}
					];

					$scope.dismiss = function dismiss() {
						$modalInstance.dismiss();
					};

					$scope.update = function update() {
						$modalInstance.close($scope.question);
					}
				}
			});

			modalInstance.result.then(function (question) {
				if (!question.id) {
					$scope.question.childQuestions.push(question);
				}
			});
		};

		$scope.deleteChildQuestion = function deleteChildQuestion(index){
			$scope.question.childQuestions.splice(index, 1);
		};

	}]);
})();
