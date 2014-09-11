/**
 * Created by pouncilt on 8/4/14.
 */
Editors.controller('addEditModuleController', ['$rootScope', '$scope', '$state', 'SurveyService', 'QuestionService', /*'questions', */'surveyUIObject', 'pageQuestionItems', function($rootScope, $scope, $state, SurveyService, QuestionService, /*questions, */surveyUIObject, pageQuestionItems){
    var tmpList = [],
        createSurvey = function(selectedModuleDomainObject) {
            SurveyService.create(SurveyService.setUpdateSurveyRequestParameter(selectedModuleDomainObject)).then(function (response){
                if(Object.isDefined(response)) {
                    if (response.isSuccessful()) {
                        $scope.selectedSurveyUIObject = response.getPayload().toUIObject();
                        $rootScope.addMessage($rootScope.createSuccessSaveMessage(response.getMessage()));
                    } else {
                        $rootScope.addMessage($rootScope.createErrorMessage(response.getMessage()));
                        console.error("modulesEditController.save() method. Expected successful response object from SurveyService.update() method to be successful.");
                    }
                }
            }, function(responseError) {
                $rootScope.addMessage($rootScope.createErrorMessage(responseError.getMessage()));
            });
        },
        updateSurvey = function (selectedModuleDomainObject) {
            SurveyService.update(SurveyService.setUpdateSurveyRequestParameter(selectedModuleDomainObject)).then(function (response){
                if(Object.isDefined(response)) {
                    if (response.isSuccessful()) {
                        $scope.selectedSurveyUIObject = response.getPayload().toUIObject();
                        $rootScope.addMessage($rootScope.createSuccessSaveMessage(response.getMessage()));
                    } else {
                        $rootScope.addMessage($rootScope.createErrorMessage(response.getMessage()));
                        console.error("modulesEditController.save() method. Expected successful response object from SurveyService.update() method to be successful.");
                    }
                }


            }, function(responseError) {
                $rootScope.addMessage($rootScope.createErrorMessage(responseError.getMessage()));
            });
        },
        createQuestion = function(selectedQuestionDomainObject) {
            QuestionService.create(QuestionService.setUpdateQuestionRequestParameter(selectedQuestionDomainObject)).then(function(response){
                if(Object.isDefined(response)) {
                    if (response.isSuccessful()) {
                        $scope.selectedQuestionUIObject = response.getPayload().toUIObject();
                        $rootScope.addMessage($rootScope.createSuccessSaveMessage(response.getMessage()));
                    } else {
                        $rootScope.addMessage($rootScope.createErrorMessage(response.getMessage()));
                        console.error("modulesEditController.save() method. Expected successful response object from QuestionService.update() method to be successful.");
                    }
                }
            }, function(responseError) {
                $rootScope.addMessage($rootScope.createErrorMessage(responseError.getMessage()));
            });
        },
        updateQuestion = function (selectedQuestionDomainObject){
            QuestionService.update(QuestionService.setUpdateQuestionRequestParameter(selectedQuestionDomainObject)).then(function(response){
                if(Object.isDefined(response)) {
                    if (response.isSuccessful()) {
                        $scope.selectedQuestionUIObject = response.getPayload().toUIObject();
                        $rootScope.addMessage($rootScope.createSuccessSaveMessage(response.getMessage()));
                    } else {
                        $rootScope.addMessage($rootScope.createErrorMessage(response.getMessage()));
                        console.error("modulesEditController.save() method. Expected successful response object from QuestionService.update() method to be successful.");
                    }
                }
            }, function(responseError) {
                $rootScope.addMessage($rootScope.createErrorMessage(responseError.getMessage()));
            });
        },
        setQuestionUIObjects = function () {
            QuestionService.queryBySurveyId(QuestionService.setQueryBySurveyIdSearchCriteria(surveyUIObject.id)).then(function (existingQuestions){
                $scope.sections = EScreeningDashboardApp.models.Question.toUIObjects(existingQuestions);
            }, function(responseError) {
                $rootScope.addMessage($rootScope.createErrorMessage(responseError.getMessage()));
            });
        },
        getSelectedQuestionDomainObject = function () {
            var firstChildQuestionAnswers = $scope.getFirstChildMeasureAnswers($scope.selectedQuestionUIObject.childQuestions);

            $scope.selectedQuestionUIObject.childQuestions.forEach(function (childMeasure, index) {
                if(index > 0) {
                    childMeasure.answers = firstChildQuestionAnswers;
                }
            });

            return new EScreeningDashboardApp.models.Question($scope.selectedQuestionUIObject);
        };

    $scope.resetFormStatus = false;
    $scope.selectedSurveyUIObject = (Object.isDefined(surveyUIObject)) ? surveyUIObject: $scope.createModule().toUIObject();
    $scope.questions = []; //(Object.isArray(questions))? EScreeningDashboardApp.models.Question.toUIObjects(questions): [];
    $scope.pageQuestionItems = (Object.isArray(pageQuestionItems) && pageQuestionItems.length > 0)? pageQuestionItems : $scope.pageQuestionItems;

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
                $state.go('modules.detail.questions.editReadOnly', {selectedQuestionId: $scope.selectedQuestionUIObject.id});
                break;
            case 'selectOne':
                $state.go('modules.detail.questions.editSelectOne', {selectedQuestionId: $scope.selectedQuestionUIObject.id});
                break;
            case'selectMulti':
                $state.go('modules.detail.questions.editSelectMultiple', {selectedQuestionId: $scope.selectedQuestionUIObject.id});
                break;
            case 'selectOneMatrix':
                $state.go('modules.detail.questions.editSelectOneMatrix', {selectedQuestionId: $scope.selectedQuestionUIObject.id});
                break;
            case 'selectMultiMatrix':
                $state.go('modules.detail.questions.editSelectMultipleMatrix', {selectedQuestionId: $scope.selectedQuestionUIObject.id});
                break;
            case 'tableQuestion':
                $state.go('modules.detail.questions.editTable');
                break;
            case 'instruction':
                $state.go('modules.detail.questions.editInstruction', {selectedQuestionId: $scope.selectedQuestionUIObject.id});
                break;
            default:
                $state.go('modules.detail.questions.editReadOnly', {selectedQuestionId: $scope.selectedQuestionUIObject.id});
        }

    };

    $scope.save = function () {
        var selectedModuleDomainObject = new EScreeningDashboardApp.models.Survey($scope.selectedSurveyUIObject),
            selectedQuestionDomainObject = getSelectedQuestionDomainObject();

        if(selectedModuleDomainObject.getId() > -1) {
            updateSurvey(selectedModuleDomainObject);
        } else {
            createSurvey(selectedModuleDomainObject);
        }

        if(selectedQuestionDomainObject.getId() > -1) {
            updateQuestion(selectedQuestionDomainObject);
        } else {
            createQuestion(selectedQuestionDomainObject);
        }

        $state.go('modules.detail.questions.blank');
    };

    $scope.addToPageQuestion = function (resetFormFunction) {
        var selectedQuestionDomainObject = getSelectedQuestionDomainObject();

        $scope.addQuestion(selectedQuestionDomainObject);
        resetFormFunction('modules.detail.questions.blank', {selectedQuestionId: -1});
    };

    $scope.cancel = function () {
        $state.go('modules.detail.questions.blank');
    };

    /*$scope.addQuestion = function(){
        $scope.selectedQuestionUIObject = $rootScope.createQuestion();
        $state.go('modules.detail.questions.editReadOnly');
    };*/

    $scope.deleteQuestion = function(question){
        $rootScope.messageHandler.clearMessages();
        QuestionService.remove(QuestionService.setRemoveQuestionRequestParameter($scope.selectedSurveyUIObject.id, question.id)).then(function(response){
            setQuestionUIObjects();
            $rootScope.addMessage($rootScope.createSuccessDeleteMessage(response.getMessage()));
        }, function(responseError) {
            $rootScope.addMessage($rootScope.createErrorMessage(responseError.getMessage()));
        });

        $state.go('modules.detail.questions.blank');
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