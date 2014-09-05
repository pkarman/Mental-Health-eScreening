/**
 * Created by pouncilt on 8/4/14.
 */
Editors.controller('addEditModuleController', ['$rootScope', '$scope', '$state', 'SurveyService', 'QuestionService', 'questions', 'surveyUIObject', function($rootScope, $scope, $state, SurveyService, QuestionService, questions, surveyUIObject){
    var tmpList = [];
    $scope.selectedSurveyUIObject = surveyUIObject;
    $scope.questions = EScreeningDashboardApp.models.Question.toUIObjects(questions);

    $scope.getFirstChildMeasureAnswers = function(childQuestions) {
        return EScreeningDashboardApp.models.Question.getFirstChildMeasureAnswers(childQuestions);
    };
    $scope.getDefaultTextFormatType = function (targetQuestionUIObject, dropDownMenuOptions) {
        var defaultTextFormatTypeValidation = new EScreeningDashboardApp.models.Validation();

        if(Object.isDefined(targetQuestionUIObject)) {
           if(Object.isArray(targetQuestionUIObject.validations)) {
               targetQuestionUIObject.validations.some(function(validation, index) {
                   if(validation.name === "dataType") {
                       return dropDownMenuOptions.some(function(dropDownMenuOption) {
                            if(dropDownMenuOption.name === validation.name && dropDownMenuOption.value === validation.value){
                                defaultTextFormatTypeValidation = dropDownMenuOption;
                                return true;
                            }
                       });
                   }
               });
           }
        }

        return defaultTextFormatTypeValidation;
    };

    $scope.editQuestion = function(questionUIObject){
        $scope.selectedQuestionUIObject = questionUIObject;

        switch ($scope.selectedQuestionUIObject.type){
            case 'freeText':
            case 'readOnly':
                $state.go('modules.detail.editReadOnlyQuestion', {selectedQuestionId: $scope.selectedQuestionUIObject.id});
                break;
            case 'selectOne':
                $state.go('modules.detail.editSelectOneQuestion', {selectedQuestionId: $scope.selectedQuestionUIObject.id});
                break;
            case'selectMulti':
                $state.go('modules.detail.editSelectMultipleQuestion', {selectedQuestionId: $scope.selectedQuestionUIObject.id});
                break;
            case 'selectOneMatrix':
                $state.go('modules.detail.editSelectOneMatrixQuestion', {selectedQuestionId: $scope.selectedQuestionUIObject.id});
                break;
            case 'selectMultiMatrix':
                $state.go('modules.detail.editSelectMultipleMatrixQuestion', {selectedQuestionId: $scope.selectedQuestionUIObject.id});
                break;
            case 'tableQuestion':
                $state.go('modules.detail.editTableQuestion');
                break;
            case 'instruction':
                $state.go('modules.detail.editInstructionQuestion', {selectedQuestionId: $scope.selectedQuestionUIObject.id});
                break;
            default:
                $state.go('modules.detail.editReadOnlyQuestion', {selectedQuestionId: $scope.selectedQuestionUIObject.id});
        }

    };

    $scope.save = function () {
        var firstChildQuestionAnswers = $scope.getFirstChildMeasureAnswers($scope.selectedQuestionUIObject.childQuestions),
            selectedModuleDomainObject = new EScreeningDashboardApp.models.Survey($scope.selectedSurveyUIObject),
            selectedQuestionDomainObject;

        $scope.selectedQuestionUIObject.childQuestions.forEach(function (childMeasure, index) {
            if(index > 0) {
                childMeasure.answers = firstChildQuestionAnswers;
            }
        });

        selectedQuestionDomainObject = new EScreeningDashboardApp.models.Question($scope.selectedQuestionUIObject),

        console.info("modulesEditController.save() method:\n" + selectedQuestionDomainObject + "\n\n");
        SurveyService.update(SurveyService.setUpdateSurveyRequestParameter(selectedModuleDomainObject)).then(function (existingSurvey){
            $scope.selectedSurveyUIObject = existingSurvey.toUIObject();
        }, function(responseError) {
            $rootScope.errors.push(responseError.getMessage());
            console.log('Update Module Restful WebService Call Error:: ' + JSON.stringify($rootScope.errors));
        });
        QuestionService.update(QuestionService.setUpdateQuestionRequestParameter(selectedQuestionDomainObject)).then(function(existingQuestion){
            $scope.selectedQuestionUIObject = existingQuestion;
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
        $scope.selectedQuestionUIObject = $rootScope.createQuestion();
        $state.go('modules.detail.editReadOnlyQuestion');
    };

    $scope.deleteQuestion = function(question){
        QuestionService.remove(QuestionService.setRemoveQuestionRequestParameter($scope.selectedSurveyUIObject.id, question.id)).then(function(response){
            $rootScope.messages.push(response.getMessage()); //Your data has been saved successfully
            //$state.go('modules.detail.question');
        }, function(responseError) {
            $rootScope.errors.push(responseError.getMessage());
        });
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