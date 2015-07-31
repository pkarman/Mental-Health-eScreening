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
						
						//set the other field on each child question
						_.each(question.childQuestions, function(childQuestion){
							//Although we are only adding to the head, older answers might have been added in another order
							childQuestion.other = false;
							//lodash's find with a property object didn't work here
							_.each(childQuestion.answers, function(answer){
								if(answer.type && answer.type === "other"){
									childQuestion.other = true;
								}
							});
							console.debug("other: " + childQuestion.other);
						});
						
						// Create question agnostic answers
						// Find questions with mha to use for prototype answers
						prototypeQuestions = $filter('filter')(question.childQuestions, { mha: true} );

						// Set the first question in childQuestions as the prototype if no mha is found
						if (!prototypeQuestions || !prototypeQuestions.length) {
							prototypeQuestions = question.childQuestions;
						}

						_.each(prototypeQuestions[0].answers, function (answer) {
							if(answer.type !== 'other'){
								scope.answers.push({
									text: answer.text,
									type: answer.type,
									displayOrder: answer.displayOrder,
									exportName: (prototypeQuestions[0].variableName && question.type === 'selectMulti') ? answer.exportName.replace(prototypeQuestions[0].variableName + '_', '') : answer.exportName,
									calculationValue: answer.calculationValue,
									mhaValue: answer.mhaValue || ''
								});
							}
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
						
						//update the order values
						_.each(scope.answers, function(answer, index){
							answer.displayOrder = index + 1;
						});
						
						_.each(scope.question.childQuestions, function(question, index) {

							question.displayOrder = index + 1;

							//add an other answer if the question is of type other
							/*if(question.other){
								if(scope.answers.len == 0 || scope.answers[0].type !== 'other'){
									scope.answers.unshift(Answer.extend({type: 'other'}));
								}
							}
							else if(scope.answers[0].type === 'other'){ //if there should be no other answer remove it
								scope.answers.shift();
							}
							*/
							
							//add an extra answer and set the index shifting to 1 if this question will have an other field added 
							var indexShift = 0;
							if(question.other){
								indexShift = 1;
								if(question.answers.len == 0 || question.answers[0].type !== 'other'){
									question.answers.unshift(Answer.extend({type: 'other', displayOrder : 1}));
								}		
							}
							else if(question.answers.length > 0 && question.answers[0].type === 'other'){
								question.answers.shift();
							}
							
							//shorten the answers if there are too many
							if(question.answers.length - indexShift > scope.answers.length){
								question.answers.splice(scope.answers.length, question.answers.length - scope.answers.length);
							}
							
							// Remove tertiary childQuestions array
							// Delete question.childQuestions
							_.each(scope.answers, function(answer, j) {
								var questionAnswerIndex = j + indexShift;
								
								if (!question.answers[questionAnswerIndex]) {
									question.answers.push(_.clone(answer));
								}

								_.merge(question.answers[questionAnswerIndex], scope.answers[j]);

								// Remove mhaAnswer from answers associated to questions without MHA
								if (!question.mha) {
									delete question.answers[questionAnswerIndex].mhaValue;
								}

								if (question.type === 'selectMulti') {
									question.answers[questionAnswerIndex].exportName = answer.exportName;
								}
								
								question.answers[questionAnswerIndex].displayOrder = questionAnswerIndex + 1;
							});
						});
					}

					updateMHAQuestions();

				};
			}
		};

	}]);

})();
