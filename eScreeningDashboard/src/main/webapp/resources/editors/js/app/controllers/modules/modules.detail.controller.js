(function() {
    'use strict';

    angular.module('Editors').controller('ModulesDetailController', ['$scope', '$state', '$stateParams', 'surveys', 'SurveyPage', 'Question', 'surveyPages', 'surveySections', function($scope, $state, $stateParams, surveys, SurveyPage, Question, surveyPages, surveySections){

        $scope.surveyPages = surveyPages;
        $scope.surveySections = surveySections;
        $scope.alerts = [];

        surveys.get($stateParams.surveyId).then(function(survey) {
            $scope.survey = survey;
        });

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

        $scope.addAlert = function addAlert() {
            $scope.alerts.push({msg: 'Another alert!'});
        };

        $scope.closeAlert = function closeAlert(index) {
            $scope.alerts.splice(index, 1);
        };

        $scope.addPage = function addPage() {
            var page = SurveyPage;
            page.pageNumber = $scope.surveyPages.length + 1;
            $scope.surveyPages.push(page);
        };

        $scope.deletePage = function deletePage(index) {
            surveyPages.splice(index, 1);
        };

        $scope.addQuestion = function addQuestion(page) {
            $scope.question = Question.extend({});
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

            $scope.survey.save().then(function(response) {
                console.log('response', response);
                $scope.alerts.push({type: 'success', msg: 'Module saved successfully'});
            });

            angular.forEach($scope.surveyPages, function(page) {
                page.save();
            });

            $scope.resetForm(false, {
                name: "modules.detail",
                params: {surveyId: $scope.survey.id},
                doTransition: true
            });
        };

        $scope.cancel = function cancel() {
            $state.go('modules');
        };

    }]);
})();

