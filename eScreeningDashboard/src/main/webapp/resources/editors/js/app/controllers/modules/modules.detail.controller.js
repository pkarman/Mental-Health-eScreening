(function() {
    'use strict';

    angular.module('Editors').controller('ModulesDetailController', ['$scope', '$state', '$stateParams', 'survey', 'surveySections', 'SurveyService', 'SurveyPageService', 'Question', function($scope, $state, $stateParams, survey, surveySections, SurveyService, SurveyPageService, Question){

        $scope.survey = survey;
        $scope.surveyPages = [];
        $scope.surveySections = surveySections;
        $scope.alerts = [];

        if (survey.id) {
            survey.getList('pages').then(function(pages) {
                $scope.surveyPages = pages;

                // Add displayOrder to questions
                _.each($scope.surveyPages, function(page) {
                    _.each(page.questions, function(question, index) {
                        question.displayOrder = index;
                    });
                });
            });
        }

        $scope.sortablePageOptions = {
            'ui-floating': false,
            cancel: '.unsortable',
            items: 'li:not(.unsortable)',
            stop: function(e, ui) {
                for (var index in $scope.surveyPages) {
                    $scope.surveyPages[index].pageNumber = index;
                }
            }
        };

        $scope.sortableQuestionOptions = {
            'ui-floating': false,
            cancel: '.unsortable',
            items: 'li:not(.unsortable)',
            stop: function(e, ui) {
                // Update the display order
                var questions = ui.item.scope().$parent.page.questions;
                for (var index in questions) {
                    questions[index].displayOrder = index;
                }
            }
        };

        $scope.addAlert = function addAlert() {
            $scope.alerts.push({msg: 'Another alert!'});
        };

        $scope.closeAlert = function closeAlert(index) {
            $scope.alerts.splice(index, 1);
        };

        $scope.addPage = function addPage() {
            var page = SurveyService.one($scope.survey.id).one('pages');
            page.title = $scope.survey.surveySection.name | $scope.survey.name;
            page.description = $scope.survey.name + ' page';
            page.pageNumber = $scope.surveyPages.length + 1;
            $scope.surveyPages.push(page);
        };

        $scope.deletePage = function deletePage(index) {
            $scope.surveyPages.splice(index, 1);
        };

        $scope.addQuestion = function addQuestion(page) {
            $scope.question = Question.extend({displayOrder: page.questions.length});
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
        };

        $scope.save = function () {

            // Remove any existing alerts
            $scope.alerts = [];

            $scope.survey.save().then(function(survey) {
                $scope.alerts.push({type: 'success', msg: 'Module saved successfully'});

                _.each($scope.surveyPages, function(page) {
                    page.parentResource.id = survey.id;

                    // Only save pages with at least one question
                    if (page.questions.length) {
                        // Deselect all questions: Server threw an error saying "selected" is not marked as ignorable
                        _.each(page.questions, function (question) {
                            delete question.selected;
                        });

                        page.save().then(function (page) {}, function (response) {
                            $scope.alerts.push({type: 'danger', msg: 'There was an error saving module items.'});
                        });
                    } else {
                        $scope.alerts.push({type: 'danger', msg: 'Page items require a minimum of one question.'});
                    }
                });

                if (!$stateParams.surveyId) {
                    $state.transitionTo('modules.detail', {surveyId: survey.id}, {
                        reload: true, inherit: false, notify: false
                    });
                }

            }, function(response) {
                $scope.alerts.push({type: 'danger', msg: 'There was an error saving the module.'});
            });

            $scope.resetForm(false, {
                name: "modules.detail",
                params: {surveyId: $scope.survey.id},
                doTransition: true
            });
        };

        $scope.cancel = function cancel(moduleDetailsForm) {
            if (!moduleDetailsForm.$dirty || (moduleDetailsForm.$dirty && window.confirm("Would you like to discard all of your changes?"))) {
                $state.go('modules');
            }
        };

    }]);
})();

