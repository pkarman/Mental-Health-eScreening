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
            var firstChildQuestionAnswers = (Object.isDefined($rootScope.selectedQuestionUIObject))?
                $scope.getFirstChildMeasureAnswers($rootScope.selectedQuestionUIObject.childQuestions): [],
                selectedQuestionDomainObject = null;

            if(Object.isDefined($rootScope.selectedQuestionUIObject)){
                $rootScope.selectedQuestionUIObject.childQuestions.forEach(function (childMeasure, index) {
                    if(index > 0) {
                        childMeasure.answers = firstChildQuestionAnswers;
                    }
                });

                selectedQuestionDomainObject = new EScreeningDashboardApp.models.Question($rootScope.selectedQuestionUIObject);
            }

            return selectedQuestionDomainObject;
        };

    var dropDownMenuOptionIndex = getDefaultQuestionType($rootScope.selectedQuestionUIObject, questionTypeDropDownMenuOptions);
    $scope.questionTypeDropDownMenu = (dropDownMenuOptionIndex >= 0)? questionTypeDropDownMenuOptions[dropDownMenuOptionIndex]: null;
    $scope.questionTypeDropDownMenuOptions = questionTypeDropDownMenuOptions;
    $scope.partentAddToPageQuestion = $scope.addToPageQuestion;
    $scope.parentResetForm = $scope.resetForm;

    $scope.$watch('formReset', function(newFormResetFlag, oldFormResetFlag) {
        if (newFormResetFlag === oldFormResetFlag) {
            return;
        } else {
            if(newFormResetFlag) {
                var dropDownMenuOptionIndex = getDefaultQuestionType($rootScope.selectedQuestionUIObject, questionTypeDropDownMenuOptions);
                $scope.questionTypeDropDownMenu = (dropDownMenuOptionIndex >= 0)? questionTypeDropDownMenuOptions[dropDownMenuOptionIndex]: null;
            }
        }
    });

    $scope.$watch( 'questionTypeDropDownMenu', function( currentlySelectedQuestionType, previouslySelectedQuestionType ) {
        if (currentlySelectedQuestionType === previouslySelectedQuestionType) {
            return;
        } else {
            var url = 'modules.detail.questions.editReadOnly',
                goToState = false;

            $scope.formReset = false;

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
                    //$rootScope.selectedQuestionUIObject.type = currentlySelectedQuestionType.name;
                    $state.go(url, {selectedQuestionId: $rootScope.selectedQuestionUIObject.id});
                }
            }
        }

    }, true);

    $scope.disableDropDownMenu = function () {
        var disableDropDownMenu = false,
            dropDownMenuOptionIndex = -1;

        if(Object.isDefined($rootScope.selectedQuestionUIObject) && Object.isDefined($rootScope.selectedQuestionUIObject.type)){
            //dropDownMenuOptionIndex = getDefaultQuestionType($rootScope.selectedQuestionUIObject, $scope.questionTypeDropDownMenuOptions);
            //$scope.questionTypeDropDownMenu = (dropDownMenuOptionIndex >= 0)? questionTypeDropDownMenuOptions[dropDownMenuOptionIndex]: null;
            if(Object.isDefined($scope.questionTypeDropDownMenu)) {
                disableDropDownMenu = true;
            }
        }

        return disableDropDownMenu;
    };

    $scope.addToPageQuestion = function (resetFormFunction, softReset, state) {
        var selectedQuestionDomainObject = null;

        resetFormFunction = (Object.isDefined(resetFormFunction))? resetFormFunction: $scope.resetForm;
        softReset = (Object.isBoolean(softReset))? softReset : false;
        state = (Object.isDefined(state))? state: {
            name: "modules.detail.questions.blank",
            params: {selectedQuestionId: -1}
        };

        $rootScope.selectedQuestionUIObject.type = $scope.questionTypeDropDownMenu.name;
        selectedQuestionDomainObject = getSelectedQuestionDomainObject();

        if(Object.isDefined(selectedQuestionDomainObject)){
            $scope.addQuestion(selectedQuestionDomainObject);
        }

        resetFormFunction(softReset, state.name, state.params);
    };

    $scope.resetForm = function (softReset, stateName, stateParams){
        softReset = (Object.isBoolean(softReset))? softReset : false;
        stateName = (Object.isString(stateName))? stateName: "modules.detail.questions.blank";
        stateParams = (Object.isDefined(stateParams))? stateParams : {selectedQuestionId: -1};

        if(!softReset) {
            $scope.questionTypeDropDownMenu = null;
        }

        $scope.parentResetForm(stateName, stateParams, softReset);
    };

    $scope.goToAddEdit = function(){
        $state.go('modules.detail.questions.blank');
    };
}]);