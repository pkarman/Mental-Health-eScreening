(function() {
    'use strict';

    angular.module('Editors').controller('ModulesDetailController', ['$scope', '$state', '$stateParams', 'survey', 'surveySections', 'surveyPages', 'clinicalReminders', 'SurveyService', 'SurveyPageService', 'Question', 'MessageFactory', function($scope, $state, $stateParams, survey, surveySections, surveyPages, clinicalReminders, SurveyService, SurveyPageService, Question, MessageFactory){

        $scope.survey = survey;
        $scope.surveyPages = surveyPages;
        $scope.surveySections = surveySections;
		$scope.clinicalReminders = clinicalReminders;
        $scope.alerts = MessageFactory.get();

		updateQuestionOrder();

        $scope.sortablePageOptions = {
            'ui-floating': false,
            cancel: '.unsortable',
            items: 'li:not(.unsortable)',
            stop: function(e, ui) {
				_.each($scope.surveyPages, function(page, index) {
					page.pageNumber = index + 1;
				});
            }
        };

        $scope.sortableQuestionOptions = {
            'ui-floating': false,
            cancel: '.unsortable',
            items: 'li:not(.unsortable)',
			placeholder: 'list-group-item',
			connectWith: '.sortable-questions',
            stop: function() {
                // Update the display order of all questions for all pages
				updateQuestionOrder();
            }
        };

        $scope.addPage = function addPage() {
            var page = $scope.survey.one('pages');
            page.title = $scope.survey.surveySection.name || $scope.survey.name;
            page.description = $scope.survey.name + ' page';
            page.pageNumber = $scope.surveyPages.length + 1;
			page.questions = [];
            $scope.surveyPages.push(page);
        };

        $scope.deletePage = function deletePage(index) {
            var page = $scope.surveyPages[index];
			if (page.id) {
				// Remove page questions
				page.questions.length = 0;
				// Save the page due to constraint on deleted question
				page.save().then(function() {
					// Delete the page
					page.remove().then(function() {
						// Remove page from surveyPages array
						$scope.surveyPages.splice(index, 1);
					});
				});
			} else {
				// Remove page from surveyPages array
				$scope.surveyPages.splice(index, 1);
			}
        };

        $scope.addQuestion = function addQuestion(page) {
            $scope.question = Question.extend({displayOrder: page.questions.length + 1});
            page.questions.push($scope.question);
            $state.go('modules.detail.list');
        };

        $scope.getStateName = function getStateName(selectedStateName){
            var stateName;

            switch (selectedStateName){
                case 'freeText':
                    stateName = "modules.detail.question.text";
                    break;
                case 'selectOne':
                    stateName = "modules.detail.question.simple";
                    break;
                case'selectMulti':
                    stateName = "modules.detail.question.simple";
                    break;
                case 'selectOneMatrix':
                    stateName = "modules.detail.question.matrix";
                    break;
                case 'selectMultiMatrix':
                    stateName = "modules.detail.question.matrix";
                    break;
                case 'tableQuestion':
                    stateName = "modules.detail.question.table";
                    break;
                case 'instruction':
                    stateName = "modules.detail.question.instructions";
                    break;
                default:
                    stateName = "modules.detail.question.text";
            }

            return stateName;
        };

        $scope.editQuestion = function editQuestion(page, question){
            var stateName = $scope.getStateName(question.type);

            // Deselect all questions
            _.each(page.questions, function(question) {
                question.selected = false;
            });

            if(Object.isDefined(stateName)) {
                // Set selected on current question
                question.selected = true;

                $scope.question = question;
                $state.go(stateName, {questionId: question.id});
            }
        };

        $scope.deleteQuestion = function deleteQuestion(page, index){
            page.questions.splice(index, 1);
			$state.go('modules.detail', {surveyId: $stateParams.surveyId});
        };

        $scope.save = function () {

            $scope.survey.save().then(function(survey) {
				// Set the newly created ID and fromServer to true for subsequent saves
				$scope.survey.id = survey.id;
				$scope.survey.fromServer = true;

                MessageFactory.set('success', 'The ' + survey.name + ' module has been saved successfully.', false, true);

                _.each($scope.surveyPages, function(page) {
					// Overwrite parentResource since it is doing strange things
                    page.parentResource = {
						id: survey.id,
						route: 'surveys'
					};

                    // Only save new pages with at least one question
                    if (page.questions.length) {
                        // Deselect all questions: Server threw an error saying "selected" is not marked as ignorable
                        _.each(page.questions, function (question) {
                            delete question.selected;
                        });

                        page.save().then(function (updatedPage) {
							// Set the newly created ID and fromServer to true for subsequent saves
							page.id = updatedPage.id;
							page.fromServer = true;
                            // Update the id of each question with the persisted question ID.
                            // Questions are not a nested resource and therefore restangular won't
                            // update the question IDs automatically like it does for actual resources
                            _.each(updatedPage.questions, function (question, index) {
								var pageQuestion = page.questions[index];
								pageQuestion.id = question.id;
								// Add the answers array from the server onto the question in $scope
								pageQuestion.answers = question.answers;
                                // Same holds true for childQuestions
                                _.each(question.childQuestions, function(childQuestion, j) {
									pageQuestion.childQuestions[j].id = childQuestion.id;
									pageQuestion.childQuestions[j].answers = childQuestion.answers;
                                });
                            });

							$state.go('modules.detail', { surveyId: survey.id });

                        }, function (response) {
                            MessageFactory.error('There was an error saving module items.', true);
                        });
                    } else {
						if (page.id) {
							// Save the page due to constraint on deleted question
							page.save().then(function() {
								// Store the index of the page in the surveyPages array
								var index = $scope.surveyPages.indexOf(page);
								// Delete the page
								page.remove().then(function() {
									// Remove page from surveyPages array
									$scope.surveyPages.splice(index, 1);
								});
							});

						} else {
							MessageFactory.warning('Page items require a minimum of one question.', true);
						}
                    }
                });

                if (!$stateParams.surveyId) {
                    $state.go('modules.detail', { surveyId: survey.id });
                }

            }, function(response) {
                MessageFactory.set('danger', 'There was an error saving the module.', false, true);
            });
        };

        $scope.cancel = function cancel(moduleDetailsForm) {
            if (!moduleDetailsForm.$dirty || (moduleDetailsForm.$dirty && window.confirm("Would you like to discard all of your changes?"))) {
                $state.go('modules');
            }
        };

		function updateQuestionOrder() {
			// Add displayOrder to questions
			_.each($scope.surveyPages, function(page) {
				_.each(page.questions, function(question, index) {
					question.displayOrder = index + 1;
				});
			});
		}

    }]);
})();

