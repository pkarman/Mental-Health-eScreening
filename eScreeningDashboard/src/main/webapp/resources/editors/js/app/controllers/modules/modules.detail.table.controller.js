(function() {
	'use strict';
	angular.module('Editors').controller('ModulesDetailTableController', ['$scope', '$modal', 'Question', 'Answer', function($scope, $modal, Question, Answer) {

		$scope.$watch('question', function(question) {
			if (question && !question.answers.length) {
				$scope.question.answers.push(Answer.extend({type: 'none'}));
			}

			if (question && question.childQuestions) {
				_.each(question.childQuestions, function(question, index) {
					// Initialize display order
					question.displayOrder = index + 1;
				});
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
					questions[index].displayOrder = index + 1;
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

					$scope.isUpdate = angular.isDefined(question);

					$scope.tableQuestion = tableQuestion;

					$scope.question = question || Question.extend({displayOrder: tableQuestion.childQuestions.length + 1});

					$scope.questionTypes = [
						{id: 0, name: "freeText", displayName: "Free Text"},
						{id: 1, name: "selectOne", displayName: "Select One"},
						{id: 2, name: "selectMulti", displayName: "Select Multi"}
					];

					$scope.dismiss = function dismiss() {
						$modalInstance.dismiss();
					};

					$scope.update = function update() {
						$modalInstance.close({question: $scope.question, update: $scope.isUpdate});
					}
				}
			});

			modalInstance.result.then(function (result) {
				if (!result.update) {
					$scope.question.childQuestions.push(result.question);
				}
			});
		};

		$scope.deleteChildQuestion = function deleteChildQuestion(index){
			$scope.question.childQuestions.splice(index, 1);
		};

	}]);
})();
