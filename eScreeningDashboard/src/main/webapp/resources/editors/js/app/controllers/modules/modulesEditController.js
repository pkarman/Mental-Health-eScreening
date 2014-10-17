/**
 * Created by pouncilt on 8/4/14.
 */
Editors.controller('addEditModuleController', ['$rootScope', '$scope', '$state', 'SurveyService', 'QuestionService', 'SurveyPageService', 'pageQuestionItems', 'surveySectionDropDownMenuOptions', function($rootScope, $scope, $state, SurveyService, QuestionService, SurveyPageService, pageQuestionItems, surveySectionDropDownMenuOptions){
    var tmpList = [],
        getSurveyUIObject = function () {
            var selectedSurveyUIObject = null;

            if(Object.isArray($scope.surveyUIObjects)) {
                $scope.surveyUIObjects.forEach(function (surveyUIObject) {
                    if(surveyUIObject.id === parseInt($state.params.selectedSurveyId)) {
                        selectedSurveyUIObject = surveyUIObject;
                    }
                });
            }

            return selectedSurveyUIObject;
        },
        updateSurveyPages = function (selectedSurveyId, surveyPages) {
            if(Object.isNumber(selectedSurveyId) && Object.isArray(surveyPages) && surveyPages.length > 0) {
                SurveyPageService.update(SurveyPageService.setUpdateSurveyPageRequestParameter($scope.selectedSurveyUIObject.id, surveyPages)).then(function (response) {
                    if (Object.isDefined(response)) {
                        if (response.isSuccessful()) {
                            $rootScope.addMessage($rootScope.createSuccessSaveMessage(response.getMessage()));
                        } else {
                            $rootScope.addMessage($rootScope.createErrorMessage(response.getMessage()));
                            console.error("modulesEditController.save() method. Expected successful response object from SurveyService.update() method to be successful.");
                        }
                    }
                }, function (responseError) {
                    $scope.addMessage($rootScope.createErrorMessage(responseError.getMessage()));
                });
            }
        },
        createQuestion = function(selectedQuestionDomainObject) {
            QuestionService.create(QuestionService.setUpdateQuestionRequestParameter(selectedQuestionDomainObject)).then(function(response){
                if(Object.isDefined(response)) {
                    if (response.isSuccessful()) {
                        //TODO: Need to update surveyPageQuestion list.
                        //$scope.setSelectedQuestionUIObject(response.getPayload().toUIObject());
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
                        //TODO: Need to update surveyPageQuestion list.
                        //$scope.setSelectedQuestionUIObject(response.getPayload().toUIObject());
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
        },
        selectedMenuItemIndex = function (menuItem, menuItemOptions) {
            var selectedMenuOptionIndex = -1;

            if(Object.isArray(menuItemOptions)) {
                menuItemOptions.some(function (menuOption, index) {
                    if (menuOption.name === menuItem.name) {
                        selectedMenuOptionIndex = index;
                        return true;
                    }
                });
            }

            return selectedMenuOptionIndex;
        },
        surveyUIObject = getSurveyUIObject();

    $scope.textFormatDropDownMenuOptions = [];
    $scope.setTextFormatDropDownMenuOptions = function(textFormatTypeMenuOptions) {
        $scope.textFormatDropDownMenuOptions = textFormatTypeMenuOptions;
    };


    $scope.setSelectedSurveyUIObject((Object.isDefined(surveyUIObject)) ? surveyUIObject: $scope.createModule().toUIObject());

    if (Object.isArray(pageQuestionItems) && pageQuestionItems.length > 0) {
        $scope.setPageQuestionItems(pageQuestionItems);
    } else {
        $scope.addPageBreak({pageNumber: 1});
    }

    $scope.surveySectionDropDownMenuOptions = surveySectionDropDownMenuOptions;
    var surveySectionDropDownMenuOptionIndex = (Object.isDefined($scope.selectedSurveyUIObject.surveySection))? selectedMenuItemIndex(new EScreeningDashboardApp.models.MenuItemSurveySectionUIObjectWrapper($scope.selectedSurveyUIObject.surveySection), surveySectionDropDownMenuOptions) : -1;
    $scope.selectedSurveyUIObject.surveySection = (surveySectionDropDownMenuOptionIndex >= 0)? surveySectionDropDownMenuOptions[surveySectionDropDownMenuOptionIndex].item: null;

    $scope.$watch('selectedSurveyUIObject.surveySection', function (currentlySelectedSurveySectionItem, previouslySelectedSurveySectionItem) {
        if (currentlySelectedSurveySectionItem === previouslySelectedSurveySectionItem) {
            return;
        } else {
            if(Object.isDefined(currentlySelectedSurveySectionItem)) {
                var dropDownMenuOptionIndex = selectedMenuItemIndex(currentlySelectedSurveySectionItem, $scope.surveySectionDropDownMenuOptions);
                $scope.selectedSurveyUIObject.surveySection = (dropDownMenuOptionIndex >= 0)? $scope.surveySectionDropDownMenuOptions[dropDownMenuOptionIndex].item: null;
            }
        }
    }, true);

    $scope.getFirstChildMeasureAnswers = function(childQuestions) {
        return EScreeningDashboardApp.models.Question.getFirstChildMeasureAnswers(childQuestions);
    };
    $scope.getDefaultTextFormatType = function (targetQuestion, dropDownMenuOptions) {
        var defaultTextFormatTypeValidation = null; //new EScreeningDashboardApp.models.Validation().toUIObject();

        if(Object.isDefined(targetQuestion)) {
           if(Object.isArray(targetQuestion.validations)) {
               targetQuestion.validations.some(function(validation, index) {
                   if(validation.name === "dataType") {
                       dropDownMenuOptions.some(function(dropDownMenuOption) {
                            if(dropDownMenuOption.name === validation.name && dropDownMenuOption.value === validation.value){
                                defaultTextFormatTypeValidation = dropDownMenuOption;
                                return true;
                            }
                       });

                       if(Object.isDefined(defaultTextFormatTypeValidation)) {
                           return true;
                       }
                   }
               });
           }
        }

        return defaultTextFormatTypeValidation;
    };

    $scope.save = function () {
        var selectedModuleDomainObject = new EScreeningDashboardApp.models.Survey($scope.selectedSurveyUIObject),
            organizedPages = $scope.organizePages();


        //console.info("Restful Payload Request: \n" + EScreeningDashboardApp.models.SurveyPage.toJSON(organizedPages) + "\n\n");

        if(selectedModuleDomainObject.getId() > -1) {
            SurveyService.update(SurveyService.setUpdateSurveyRequestParameter(selectedModuleDomainObject)).then(function (response){
                if(Object.isDefined(response)) {
                    if (response.isSuccessful()) {
                        $scope.setSelectedSurveyUIObject(response.getPayload().toUIObject());
                        $rootScope.addMessage($rootScope.createSuccessSaveMessage(response.getMessage()));

                        updateSurveyPages($scope.selectedSurveyUIObject.id, organizedPages);
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

                        updateSurveyPages($scope.selectedSurveyUIObject.id, organizedPages);
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
            params: {selectedQuestionId: $scope.selectedSurveyUIObject.id},
            doTransition: true
        });
    };

    /*$scope.addToPageQuestion = function (resetFormFunction, state, softReset) {
        var selectedQuestionDomainObject = getSelectedQuestionDomainObject();
        softReset = (Object.isBoolean(softReset))? softReset: false;

        $scope.addQuestion(selectedQuestionDomainObject);
        resetFormFunction(state.name, state.params, softReset);
    };*/

    $scope.cancel = function () {
        $scope.setSelectedPageQuestionItem(null);
        $scope.setSelectedSurveyUIObject(null);
        $state.go('modules.list');
    };

    /*$scope.addQuestion = function(){
        $scope.setSelectedQuestionUIObject($rootScope.createQuestion());
        $state.go('modules.detail.editReadOnlyQuestionType');
    };*/

    $scope.editQuestion = function(selectedPageQuestionItem){
        var stateName = $scope.getStateName(selectedPageQuestionItem.getItem().type),
            softReset = false,
            state = {
                name: undefined,
                params: {selectedQuestionId: selectedPageQuestionItem.getItem().id},
                doTransition: false
            };

        if(Object.isDefined(stateName)) {
            $scope.setSelectedPageQuestionItem(selectedPageQuestionItem);
            $state.go(stateName, {selectedQuestionId: selectedPageQuestionItem.getItem().id});
        }
    };

    $scope.getStateName = function(selectedStateName){
        var stateName;

        switch (selectedStateName){
            case 'freeText':
                stateName = "modules.detail.editFreeTextQuestionType";
                break;
            case 'readOnly':
                stateName = "modules.detail.editReadOnlyQuestionType";
                break;
            case 'selectOne':
                stateName = "modules.detail.editSelectOneQuestionType";
                break;
            case'selectMulti':
                stateName = "modules.detail.editSelectMultipleQuestionType";
                break;
            case 'selectOneMatrix':
                stateName = "modules.detail.editSelectOneMatrixQuestionType";
                break;
            case 'selectMultiMatrix':
                stateName = "modules.detail.editSelectMultipleMatrixQuestionType";
                break;
            case 'tableQuestion':
                stateName = "modules.detail.editTableQuestionType";
                break;
            case 'instruction':
                stateName = "modules.detail.editInstructionQuestionType";
                break;
            default:
                stateName = "modules.detail.editFreeTextQuestionType";
        }

        return stateName;
    };

    /*$scope.deleteQuestion = function(question){
        $rootScope.messageHandler.clearMessages();
        QuestionService.remove(QuestionService.setRemoveQuestionRequestParameter($scope.selectedSurveyUIObject.id, question.id)).then(function(response){
            setQuestionUIObjects();
            $rootScope.addMessage($rootScope.createSuccessDeleteMessage(response.getMessage()));
        }, function(responseError) {
            $rootScope.addMessage($rootScope.createErrorMessage(responseError.getMessage()));
        });

        $state.go('modules.detail.selectQuestionType');
    };*/

    $scope.sortableOptions = {
        cancel: ".unsortable",
        items: "li:not(.unsortable)",

        update: function(e, ui) {
            var logEntry = tmpList.map(function(i){
                return i.value;
            }).join(', ');
            // $scope.sortingLog.push('Update: ' + logEntry);
            if (ui.item.scope().item.isEnabled() === true) {

            }
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