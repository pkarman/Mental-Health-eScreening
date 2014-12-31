(function() {
    'use strict';

    angular.module('Editors').controller('ModulesDetailController', ['$scope', '$state', '$stateParams', 'SurveyService', 'QuestionService', 'SurveyPageService', 'SurveyPage', 'Question', 'surveys', 'surveyPages', 'surveySections', function($scope, $state, $stateParams, SurveyService, QuestionService, SurveyPageService, SurveyPage, Question, surveys, surveyPages, surveySections){

        $scope.surveySections = surveySections;
        $scope.surveyPages = surveyPages;

        if (!$scope.survey) {
            // Look up the selected survey by the id passed into the parameter
            /*
             TODO create GET /services/survey/:id endpoint
             SurveyService.one($stateParams.surveyId).get().then(function(survey) {
                $scope.survey = survey;
             });
             */
            // The above endpoint doesn't exist, therefore loop through the surveys list
            $scope.survey =_.find(surveys, function(survey) {
                return survey.id === +$stateParams.surveyId;
            });
        }

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
                    stateName = "modules.detail.one";
                    break;
                case'selectMulti':
                    stateName = "modules.detail.multi";
                    break;
                case 'selectOneMatrix':
                    stateName = "modules.detail.onematrix";
                    break;
                case 'selectMultiMatrix':
                    stateName = "modules.detail.multimatrix";
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
                $state.go(stateName, {selectedQuestionId: question.id});
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
                params: {selectedQuestionId: $scope.survey.id},
                doTransition: true
            });
        };

        $scope.cancel = function cancel() {
            $state.go('modules.list');
        };

    }]);
})();

