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
								exportName: question.childQuestions[0].variableName ? answer.exportName.replace(question.childQuestions[0].variableName + '_', '') : answer.exportName
							});
						});
					}

				});

				scope.$watchCollection('answers', function(answers) {
					scope.updateQuestionAnswers();
				});

				scope.$watchCollection('question.childQuestions', function(childQuestions) {
					scope.updateQuestionAnswers();
				});

				scope.addAnswer = function addAnswer() {
					scope.answers.push({text:'', exportName: ''});
				};

				scope.deleteAnswer = function deleteAnswer(index) {
					scope.answers.splice(index, 1);
				};

				scope.addQuestion = function addQuestion() {
					scope.question.childQuestions.push(Question.extend({type: scope.question.type === 'selectOneMatrix' ? 'selectOne' : 'selectMulti' }));
				};

				scope.deleteQuestion = function deleteQuestion(index) {
					scope.question.childQuestions.splice(index, 1);
				};

				scope.updateQuestionAnswers = function updateQuestionAnswers () {
					if (scope.answers.length && scope.question.childQuestions.length) {
						_.each(scope.question.childQuestions, function(question, i) {

							question.displayOrder = i;

							// Remove tertiary childQuestions array
							//delete question.childQuestions;
							_.each(scope.answers, function(answer, index) {

								if (!question.answers[index]) {
									question.answers.push(_.clone(answer));
								}
								_.merge(question.answers[index], scope.answers[index]);
								question.answers[index].exportName = question.variableName + '_' + answer.exportName;
								question.answers[index].displayOrder = index;
							});
						});
					}
				};
			}
		};

	}]);

})();
