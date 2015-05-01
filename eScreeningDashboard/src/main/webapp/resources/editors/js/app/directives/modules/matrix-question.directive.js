(function() {
	'use strict';

	angular.module('Editors').directive('matrixQuestion', ['$filter', 'Answer', 'Question', function($filter, Answer, Question) {

		return {
			restrict: 'EA',
			scope: {
				question: '=',
				survey: '='
			},
			templateUrl: 'resources/editors/partials/modules/matrix-question.html',
			link: function(scope) {

				scope.answers = [];
				scope.selectedMHAQuestions = [];

				scope.answerTypes = [
					{ name: 'Regular', value: '' },
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
						_.each(answers, function(answer, index) {
							answer.displayOrder = index + 1;
						});
					}
				};

				scope.sortableQuestionOptions = {
					'ui-floating': false,
					cancel: '.unsortable',
					items: 'li:not(.unsortable)',
					stop: function(e, ui) {
						var questions = ui.item.scope().$parent.question.childQuestions;
						_.each(questions, function(question, index) {
							question.displayOrder = index + 1;
						});
					}
				};

				scope.$watch('question', function(question) {

					var prototypeQuestions = [];

					if (question && question.childQuestions.length) {
						// Create question agnostic answers
						// Find questions with mha to use for prototype answers
						prototypeQuestions = $filter('filter')(question.childQuestions, { mha: true} );

						// Set the first question in childQuestions as the prototype if no mha is found
						if (!prototypeQuestions || !prototypeQuestions.length) {
							prototypeQuestions = question.childQuestions;
						}

						_.each(prototypeQuestions[0].answers, function (answer) {
							scope.answers.push({
								text: answer.text,
								type: answer.type,
								exportName: (prototypeQuestions[0].variableName && question.type === 'selectMulti') ? answer.exportName.replace(prototypeQuestions[0].variableName + '_', '') : answer.exportName,
								calculationValue: answer.calculationValue,
								mhaValue: answer.mhaValue || ''
							});
						});

						updateMHAQuestions();
					}

				});

				scope.$watchCollection('answers', function(answers) {
					scope.updateQuestionAnswers();
				});

				scope.$watchCollection('question.childQuestions', function(childQuestions) {
					scope.updateQuestionAnswers();
				});

				function updateMHAQuestions() {
					scope.selectedMHAQuestions = $filter('filter')(scope.question.childQuestions, { mha: true });
				}

				scope.addAnswer = function addAnswer() {
					scope.answers.push(Answer.extend({displayOrder: scope.answers.length + 1}));
				};

				scope.deleteAnswer = function deleteAnswer(index) {
					scope.answers.splice(index, 1);
				};

				scope.addQuestion = function addQuestion() {
					scope.question.childQuestions.push(Question.extend({type: scope.question.type === 'selectOneMatrix' ? 'selectOne' : 'selectMulti', displayOrder: scope.question.childQuestions.length + 1 }));
				};

				scope.deleteQuestion = function deleteQuestion(index) {
					scope.question.childQuestions.splice(index, 1);
				};

				scope.updateQuestionAnswers = function updateQuestionAnswers () {

					if (scope.answers.length && scope.question.childQuestions.length) {

						_.each(scope.question.childQuestions, function(question, index) {

							question.displayOrder = index + 1;

							// Remove tertiary childQuestions array
							// Delete question.childQuestions
							_.each(scope.answers, function(answer, j) {

								if (!question.answers[j]) {
									question.answers.push(_.clone(answer));
								}

								_.merge(question.answers[j], scope.answers[j]);

								// Remove mhaAnswer from answers associated to questions without MHA
								if (!question.mha) {
									delete question.answers[j].mhaValue;
								}

								if (question.type === 'selectMulti') {
									question.answers[j].exportName = answer.exportName;
								}

								question.answers[j].displayOrder = j + 1;
							});
						});
					}

					updateMHAQuestions();

				};
			}
		};

	}]);

})();
