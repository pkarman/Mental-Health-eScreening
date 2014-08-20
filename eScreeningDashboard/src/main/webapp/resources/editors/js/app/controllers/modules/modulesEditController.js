/**
 * Created by pouncilt on 8/4/14.
 */
Editors.controller('addEditModuleController', ['$rootScope', '$scope', '$state', 'SurveyService', 'QuestionService', 'questions', function($rootScope, $scope, $state, SurveyService, QuestionService, questions){
    var tmpList = [];
    $scope.questions = EScreeningDashboardApp.models.Question.toUIObjects(questions);

    $scope.getFirstChildMeasureAnswers = function(childQuestions) {
        return EScreeningDashboardApp.models.Question.getFirstChildMeasureAnswers(childQuestions);
    };

    $scope.editQuestion = function(question){
        $scope.selectedQuestion = question;

        switch (question.type){
            case 'freeText':
            case 'readOnly':
                $state.go('modules.detail.editReadOnlyQuestion');
                break;
            case 'selectOne':
                $state.go('modules.detail.editSelectOneQuestion', {selectedQuestionId: question.id});
                break;
            case'selectMulti':
                $state.go('modules.detail.editSelectMultipleQuestion');
                break;
            case 'selectOneMatrix':
                $state.go('modules.detail.editSelectOneMatrixQuestion', {selectedQuestionId: question.id});
                break;
            case 'selectMultiMatrix':
                $state.go('modules.detail.editSelectMultipleMatrixQuestion');
                break;
            case 'tableQuestion':
                $state.go('modules.detail.editTableQuestion');
                break;
            case 'instructions':
                $state.go('modules.detail.editInstructionQuestion');
                break;
            default:
                $state.go('modules.detail.editReadOnlyQuestion');
        }

    };

    $scope.save = function () {
        var selectedModuleDomainObject = new EScreeningDashboardApp.models.Survey($scope.module),
            selectedQuestionDomainObject = new EScreeningDashboardApp.models.Question($scope.selectedQuestion);

        SurveyService.update(SurveyService.setUpdateSurveyRequestParameter(selectedModuleDomainObject)).then(function (existingSurvey){
            $scope.module = existingSurvey;
        }, function(responseError) {
            $rootScope.errors.push(responseError.getMessage());
            console.log('Update Module Restful WebService Call Error:: ' + JSON.stringify($rootScope.errors));
        });
        QuestionService.update(QuestionService.setUpdateQuestionRequestParameter(selectedQuestionDomainObject)).then(function(existingQuestion){
            $scope.selectedQuestion = existingQuestion;
        }, function(responseError) {
            $rootScope.errors.push(responseError.getMessage());
            console.log('Update Question Restful WebService Call Error:: ' + JSON.stringify($rootScope.errors));
        });

        $state.go('modules.detail.question');
    };

    $scope.cancel = function () {
        $state.go('modules.detail.question');
    };

    $scope.addQuestion = function(){
        $scope.selectedQuestion = $rootScope.createQuestion();
        $state.go('modules.detail.editReadOnlyQuestion');
    };

    $scope.deleteQuestion = function(question){
        alert('Will delete the Question with VISTA Variable: ' + question.vistaVariable);
    };

    $scope.sortableOptions = {
        update: function(e, ui) {
            var logEntry = tmpList.map(function(i){
                return i.value;
            }).join(', ');
            // $scope.sortingLog.push('Update: ' + logEntry);
        },
        stop: function(e, ui) {
            // this callback has the changed model
            var logEntry = tmpList.map(function(i){
                return i.value;
            }).join(', ');
            //$scope.sortingLog.push('Stop: ' + logEntry);
        }
    };
}]);