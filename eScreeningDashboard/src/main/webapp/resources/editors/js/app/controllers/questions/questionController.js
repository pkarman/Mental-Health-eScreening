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
        };

    var dropDownMenuOptionIndex = getDefaultQuestionType($scope.selectedQuestionUIObject, questionTypeDropDownMenuOptions);
    $scope.questionTypeDropDownMenu = (dropDownMenuOptionIndex >= 0)? questionTypeDropDownMenuOptions[dropDownMenuOptionIndex]: null;
    $scope.questionTypeDropDownMenuOptions = questionTypeDropDownMenuOptions;


    $scope.$watch('formReset', function(newFormResetFlag, oldFormResetFlag) {
        if (newFormResetFlag === oldFormResetFlag) {
            return;
        } else {
            if(newFormResetFlag) {
                var dropDownMenuOptionIndex = getDefaultQuestionType($scope.selectedQuestionUIObject, questionTypeDropDownMenuOptions);
                $scope.questionTypeDropDownMenu = (dropDownMenuOptionIndex >= 0)? questionTypeDropDownMenuOptions[dropDownMenuOptionIndex]: null;
            }
        }
    });

    $scope.$watch( 'questionTypeDropDownMenu', function( currentlySelectedQuestionType, previouslySelectedQuestionType ) {
        if (currentlySelectedQuestionType === previouslySelectedQuestionType) {
            return;
        } else {
            var url = 'modules.detail.questions.editReadOnly';

            $scope.formReset = false;

            switch(currentlySelectedQuestionType.displayName){
                case "Free/Read-Only Text":
                    url = 'modules.detail.questions.editReadOnly';
                    break;
                case 'Select Single':
                    url = 'modules.detail.questions.editSelectOne';
                    break;
                case 'Select Multiple':
                    url = 'modules.detail.questions.editSelectMultiple';
                    break;
                case 'Select Single Matrix':
                    url = 'modules.detail.questions.editSelectOneMatrix';
                    break;
                case 'Select Multiple Matrix':
                    url = 'modules.detail.questions.editSelectMultipleMatrix';
                    break;
                case 'Table Question':
                    url = 'modules.detail.questions.editTable';
                    break;
                case 'Instructions':
                    url = 'modules.detail.questions.editInstruction';
                    break;
            }
            $scope.selectedQuestionUIObject.type = currentlySelectedQuestionType.name;
            $state.go(url, {selectedQuestionId: $scope.selectedQuestionUIObject.id});
        }

    }, true);

    $scope.disableDropDownMenu = function () {
        var disableDropDownMenu = false,
            dropDownMenuOptionIndex = -1;

        if(Object.isDefined($scope.selectedQuestionUIObject) && Object.isDefined($scope.selectedQuestionUIObject.type)){
            dropDownMenuOptionIndex = getDefaultQuestionType($scope.selectedQuestionUIObject, $scope.questionTypeDropDownMenuOptions);
            $scope.questionTypeDropDownMenu = (dropDownMenuOptionIndex >= 0)? questionTypeDropDownMenuOptions[dropDownMenuOptionIndex]: null;
            disableDropDownMenu = true;
        }

        return disableDropDownMenu;
    };

    $scope.goToAddEdit = function(){
        $state.go('modules.detail.questions.blank');
    };
}]);