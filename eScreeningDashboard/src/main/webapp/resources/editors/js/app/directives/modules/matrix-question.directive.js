(function() {
	'use strict';

	angular.module('Editors').directive('matrixQuestion', ['Answer', 'Question', function(Answer, Question) {

		return {
			restrict: 'EA',
			scope: {
				question: '='
			},
			templateUrl: 'resources/editors/partials/modules/matrix-question.html',
			link: function(scope) {

				scope.answers = [];

				scope.answerTypes = [
					{ name: 'Other', value: 'other' },
					{ name: 'None', value: 'none' }
				];

				scope.sortableAnswerOptions = {
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

				scope.sortableQuestionOptions = {
					'ui-floating': false,
					cancel: '.unsortable',
					items: 'li:not(.unsortable)',
					stop: function(e, ui) {
						var questions = ui.item.scope().$parent.question.childQuestions;
						for (var index in questions) {
							questions[index].displayOrder = index;
						}
					}
				};

				scope.$watch('question', function(question) {
					if (question && scope.question.childQuestions) {
						// Create question agnostic answers
						_.each(scope.question.childQuestions[0].answers, function(answer) {
							scope.answers.push({text: answer.text, exportName: answer.exportName.split('_')[1]});
						});
					}

				});

				scope.$watch('answers', function(answers) {
					if (answers.length) {
						_.each(scope.question.childQuestions, function(question) {
							mergeByProperty(question.answers, scope.answers, 'text');
							_.each(question.answers, function(answer) {
								answer.exportName = question.variableName + '_' + answer.exportName;
							});
						});
					}
				}, true);

				scope.addAnswer = function addAnswer() {
					scope.answers.push({text:'Enter Answer Test', exportName: 0});
				};

				scope.deleteAnswer = function deleteAnswer(index) {
					scope.answers.splice(index, 1);
				};

				scope.addQuestion = function addQuestion() {
					scope.question.childQuestions.push(Question.create());
				};

				scope.deleteQuestion = function deleteQuestion(index) {
					scope.question.childQuestions.splice(index, 1);
				};

				function mergeByProperty(arr1, arr2, prop) {
					_.each(arr2, function(arr2obj) {
						var arr1obj = _.find(arr1, function(arr1obj) {
							return arr1obj[prop] === arr2obj[prop];
						});

						//If the object already exist extend it with the new values from arr2
						// otherwise just add the new object to arr1
						arr1obj ? _.extend(arr1obj, arr2obj) : arr1.push(arr2obj);
					});
				}
			}
		};

	}]);

})();
