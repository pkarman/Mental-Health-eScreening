/**
 * Created by pouncilt on 8/6/14.
 */
Editors.controller('questionsController', ['$rootScope', '$scope', '$state', 'questionTypeDropDownMenuOptions', function($rootScope, $scope, $state, questionTypeDropDownMenuOptions){
    var getDefaultQuestionType = function (selectQuestionUIObject, dropDownMenuOptions) {
            var defaultDropDownMenuOptionIndex = -1;

            dropDownMenuOptions.some(function(dropDownMenuOption, index) {
                if(dropDownMenuOption.name === selectQuestionUIObject.type){
                    defaultDropDownMenuOptionIndex = index;
                    return true;
                }
            });

            return defaultDropDownMenuOptionIndex;
        },
        getSelectedQuestionDomainObject = function () {
            var firstChildQuestionAnswers = (Object.isDefined($scope.selectedQuestionUIObject))?
                $scope.getFirstChildMeasureAnswers($scope.selectedQuestionUIObject.childQuestions): [],
                selectedQuestionDomainObject = null;

            if(Object.isDefined($scope.selectedQuestionUIObject)){
                $scope.selectedQuestionUIObject.childQuestions.forEach(function (childMeasure, index) {
                    if(index > 0) {
                        childMeasure.answers = firstChildQuestionAnswers;
                    }
                });

                selectedQuestionDomainObject = new EScreeningDashboardApp.models.Question($scope.selectedQuestionUIObject);
            }

            return selectedQuestionDomainObject;
        };

    var dropDownMenuOptionIndex = getDefaultQuestionType($scope.selectedQuestionUIObject, questionTypeDropDownMenuOptions);
    $scope.questionTypeDropDownMenu = (dropDownMenuOptionIndex >= 0)? questionTypeDropDownMenuOptions[dropDownMenuOptionIndex]: null;
    $scope.questionTypeDropDownMenuOptions = questionTypeDropDownMenuOptions;
    $scope.partentAddToPageQuestion = $scope.addToPageQuestion;
    $scope.parentResetForm = $scope.resetForm;

    $scope.$watch('selectedPageQuestionItem', function (currentlySelectedPageQuestionItem, previouslySelectedPageQuestionItem) {
        if (currentlySelectedPageQuestionItem === previouslySelectedPageQuestionItem) {
            return;
        } else {
            if(Object.isDefined(currentlySelectedPageQuestionItem)) {
                var dropDownMenuOptionIndex = getDefaultQuestionType(currentlySelectedPageQuestionItem.getItem().toUIObject(), questionTypeDropDownMenuOptions);
                $scope.questionTypeDropDownMenu = (dropDownMenuOptionIndex >= 0)? questionTypeDropDownMenuOptions[dropDownMenuOptionIndex]: null;
            }
        }
    }, true);
    $scope.$watch('formReset', function(newFormResetFlag, oldFormResetFlag) {
        if (newFormResetFlag === oldFormResetFlag) {
            return;
        } else {
            if(newFormResetFlag) {
                $scope.questionTypeDropDownMenu = null;
            }
        }
    });

    $scope.$watch( 'questionTypeDropDownMenu', function( currentlySelectedQuestionType, previouslySelectedQuestionType ) {
        if (currentlySelectedQuestionType === previouslySelectedQuestionType) {
            return;
        } else {
            var url = 'modules.detail.questions.editReadOnly',
                goToState = false;

            $scope.setFormReset(false);

            if(Object.isDefined(currentlySelectedQuestionType) && Object.isDefined(currentlySelectedQuestionType.displayName)){
                switch(currentlySelectedQuestionType.displayName){
                    case "Free/Read-Only Text":
                        url = 'modules.detail.questions.editReadOnly';
                        goToState = true;
                        break;
                    case 'Select Single':
                        url = 'modules.detail.questions.editSelectOne';
                        goToState = true;
                        break;
                    case 'Select Multiple':
                        url = 'modules.detail.questions.editSelectMultiple';
                        goToState = true;
                        break;
                    case 'Select Single Matrix':
                        url = 'modules.detail.questions.editSelectOneMatrix';
                        goToState = true;
                        break;
                    case 'Select Multiple Matrix':
                        url = 'modules.detail.questions.editSelectMultipleMatrix';
                        goToState = true;
                        break;
                    case 'Table Question':
                        url = 'modules.detail.questions.editTable';
                        goToState = true;
                        break;
                    case 'Instructions':
                        url = 'modules.detail.questions.editInstruction';
                        goToState = true;
                        break;
                }

                if(goToState) {
                    $state.go(url, {selectedQuestionId: $scope.selectedQuestionUIObject.id});
                }
            }
        }

    }, true);

    $scope.disableDropDownMenu = function () {
        var disableDropDownMenu = false;

        if(Object.isDefined($scope.selectedQuestionUIObject) && Object.isDefined($scope.selectedQuestionUIObject.type)){
            if(Object.isDefined($scope.questionTypeDropDownMenu)) {
                disableDropDownMenu = true;
            }
        }

        return disableDropDownMenu;
    };

    $scope.addToPageQuestion = function (resetFormFunction, softReset, state) {
        var selectedQuestionDomainObject;

        resetFormFunction = (Object.isDefined(resetFormFunction))? resetFormFunction: $scope.resetForm;
        softReset = (Object.isBoolean(softReset))? softReset : true;
        state = (Object.isDefined(state))? state: {
            name: "modules.detail.questions.blank",
            params: {selectedQuestionId: -1},
            doTransition: false
        };

        $scope.selectedQuestionUIObject.type = $scope.questionTypeDropDownMenu.name;
        selectedQuestionDomainObject = getSelectedQuestionDomainObject();

        if(Object.isDefined(selectedQuestionDomainObject)){
            if(Object.isDefined($scope.selectedPageQuestionItem)) {
                angular.copy(selectedQuestionDomainObject, $scope.selectedPageQuestionItem.getItem());
            } else {
                $scope.addQuestion(selectedQuestionDomainObject);
            }
        }

        resetFormFunction(softReset, state);
    };

    $scope.resetForm = function (softReset, state){
        softReset = (Object.isBoolean(softReset))? softReset : false;
        state = (Object.isDefined(state))? state: {
            name: "modules.detail.questions.blank",
            params: {selectedQuestionId: -1},
            doTransition: false
        };

        if(!softReset) {
            $scope.questionTypeDropDownMenu = null;
        }


        $scope.parentResetForm(softReset, state);
    };

    $scope.goToAddEdit = function(){
        $state.go('modules.detail.questions.blank');
    };
}]);