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
					handle: '.glyphicon-align-justify',
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
					if (question && question.childQuestions.length) {
						// Create question agnostic answers
						_.each(question.childQuestions[0].answers, function (answer) {
							scope.answers.push({
								text: answer.text,
								exportName: answer.exportName.replace(question.childQuestions[0].variableName + '_', '')
							});
						});
					}

				});

				scope.$watchCollection('answers', function(answers) {
					updateQuestionAnswers();
				});

				scope.$watchCollection('question.childQuestions', function(childQuestions) {
					updateQuestionAnswers();
				});

				scope.addAnswer = function addAnswer() {
					scope.answers.push({text:'', exportName: ''});
				};

				scope.deleteAnswer = function deleteAnswer(index) {
					scope.answers.splice(index, 1);
				};

				scope.addQuestion = function addQuestion() {
					scope.question.childQuestions.push(Question.extend({}));
				};

				scope.deleteQuestion = function deleteQuestion(index) {
					scope.question.childQuestions.splice(index, 1);
				};

				function mergeByProperty (arr1, arr2, prop) {
					_.each(arr2, function(arr2obj) {
						var arr1obj = _.find(arr1, function(arr1obj) {
							console.log('arr1obj[prop]', arr1obj[prop]);
							console.log('arr2obj[prop]', arr2obj[prop]);
							return arr1obj[prop] === arr2obj[prop];
						});

						//If the object already exist extend it with the new values from arr2
						// otherwise just add the new object to arr1
						arr1obj ? _.extend(arr1obj, arr2obj) : arr1.push(arr2obj);
					});
				}

				function updateQuestionAnswers () {
					if (scope.answers.length && scope.question.childQuestions.length) {
						_.each(scope.question.childQuestions, function(question) {

							console.log('scope.question.childQuestions', scope.question.childQuestions);

							if (!question.answers.length) {
								question.answers = angular.copy(scope.answers);
							}
							//mergeByProperty(question.answers, scope.answers, 'text');
							_.each(question.answers, function(answer) {
								console.log('answer', answer);
								console.log('question', question);
								answer.exportName = question.variableName + '_' + answer.exportName;
								console.log(answer.exportName);
							});
						});
					}
				}
			}
		};

	}]);

})();
