(function() {
    'use strict';

    angular.module('Editors').controller('ModulesDetailController', ['$scope', '$state', '$stateParams', 'SurveyService', 'QuestionService', 'SurveyPageService', 'SurveyPage', 'Question', 'surveys', 'surveyPages', 'surveySections', function($scope, $state, $stateParams, SurveyService, QuestionService, SurveyPageService, SurveyPage, Question, surveys, surveyPages, surveySections){

        $scope.surveySections = surveySections;
        $scope.surveyPages = surveyPages;

        if (!$scope.survey) {
            // Look up the selected survey by the id passed into the parameter
            SurveyService.one($stateParams.surveyId).get().then(function(survey) {
                $scope.survey = survey;
            });
        }

        $scope.alerts = [];

        $scope.addAlert = function() {
            $scope.alerts.push({msg: 'Another alert!'});
        };

        $scope.closeAlert = function(index) {
            $scope.alerts.splice(index, 1);
        };

        $scope.sortablePageOptions = {
            'ui-floating': false,
            cancel: '.unsortable',
            items: 'li:not(.unsortable)',
            stop: function(e, ui) {
                for (var index in $scope.surveyPages) {
                    //$scope.surveyPages[index].displayOrder = index;
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

        $scope.getStateName = function getStateName(selectedStateName){
            var stateName;

            switch (selectedStateName){
                case 'freeText':
                    stateName = "modules.detail.freetext";
                    break;
                case 'selectOne':
                    stateName = "modules.detail.simple";
                    break;
                case'selectMulti':
                    stateName = "modules.detail.simple";
                    break;
                case 'selectOneMatrix':
                    stateName = "modules.detail.matrix";
                    break;
                case 'selectMultiMatrix':
                    stateName = "modules.detail.matrix";
                    break;
                case 'tableQuestion':
                    stateName = "modules.detail.table";
                    break;
                case 'instruction':
                    stateName = "modules.detail.instructions";
                    break;
                default:
                    stateName = "modules.detail.freetext";
            }

            return stateName;
        };

        $scope.addPage = function addPage() {
            $scope.surveyPages.push(SurveyPage.create({pageNumber: $scope.surveyPages.length+1}));
        };

        $scope.deletePage = function deletePage(index) {
            surveyPages.splice(index, 1);
        };

        $scope.addQuestion = function addQuestion(page) {
            $scope.question = Question.create();
            page.questions.push($scope.question);
            $state.go('modules.detail.list');
        };

        $scope.editQuestion = function editQuestion(question){
            var stateName = $scope.getStateName(question.type);

            if(Object.isDefined(stateName)) {
                $scope.question = question;
                $state.go(stateName, {questionId: question.id});
            }
        };

        $scope.deleteQuestion = function deleteQuestion(page, index){
            page.questions.splice(index, 1);
        };

        $scope.save = function () {
            var selectedModuleDomainObject = new EScreeningDashboardApp.models.Survey($scope.survey),
                organizedPages = $scope.organizePages();

            if(selectedModuleDomainObject.getId() > -1) {
                SurveyService.update(SurveyService.setUpdateSurveyRequestParameter(selectedModuleDomainObject)).then(function (response){
                    if(Object.isDefined(response)) {
                        if (response.isSuccessful()) {
                            $scope.setSelectedSurveyUIObject(response.getPayload().toUIObject());

                            if (angular.isFunction($rootScope.addMessage)) {
                                $rootScope.addMessage($rootScope.createSuccessSaveMessage(response.getMessage()));
                            }

                            updateSurveyPages($scope.survey.id, organizedPages);
                        } else {
                            $rootScope.addMessage($rootScope.createErrorMessage(response.getMessage()));
                            console.error("modulesEditController.save() method. Expected successful response object from SurveyService.update() method to be successful.");
                        }
                    }
                }, function(responseError) {
                    $rootScope.addMessage($rootScope.createErrorMessage(responseError.getMessage()));
                });
            } else {
                SurveyService.create(SurveyService.setCreateSurveyRequestParameter(selectedModuleDomainObject)).then(function (response){
                    if(Object.isDefined(response)) {
                        if (response.isSuccessful()) {
                            $scope.setSelectedSurveyUIObject(response.getPayload().toUIObject());
                            $rootScope.addMessage($rootScope.createSuccessSaveMessage(response.getMessage()));

                            updateSurveyPages($scope.survey.id, organizedPages);
                        } else {
                            $rootScope.addMessage($rootScope.createErrorMessage(response.getMessage()));
                            console.error("modulesEditController.save() method. Expected successful response object from SurveyService.update() method to be successful.");
                        }
                    }
                }, function(responseError) {
                    $rootScope.addMessage($rootScope.createErrorMessage(responseError.getMessage()));
                });
            }

            $scope.resetForm(false, {
                name: "modules.detail.empty",
                params: {questionId: $scope.survey.id},
                doTransition: true
            });
        };

        $scope.cancel = function cancel() {
            $state.go('modules.list');
        };

    }]);
})();

