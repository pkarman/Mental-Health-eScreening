/**
 * Created by pouncilt on 8/6/14.
 */
Editors.controller('questionsController', ['$rootScope', '$scope', '$state', 'questionTypeDropDownMenuOptions', function($rootScope, $scope, $state, questionTypeDropDownMenuOptions){
    var getDefaultQuestionType = function (selectQuestionUIObject, dropDownMenuOptions) {
            var defaultDropDownMenuOption = null;

            dropDownMenuOptions.some(function(dropDownMenuOption) {
                if(dropDownMenuOption.name === selectQuestionUIObject.type){
                    defaultDropDownMenuOption = dropDownMenuOption.name;
                    return true;
                }
            });

            return defaultDropDownMenuOption;
        },
        getArrayOfNames = function(dropDownMenuOptions) {
            var names = [];
            if(Object.isArray(dropDownMenuOptions)) {
                dropDownMenuOptions.forEach(function(option){
                    names.push(option.name);
                });
            }

            return names;
        };

    $scope.questionTypeDropDownMenuOptions = questionTypeDropDownMenuOptions;
    $scope.selectedQuestionUIObject.type = getDefaultQuestionType($scope.selectedQuestionUIObject, $scope.questionTypeDropDownMenuOptions);

    $scope.$watch( 'selectedQuestionUIObject.type', function( newType, oldType ) {
        if (newType === oldType) {
            return;
        } else {
            var url = 'modules.detail.questions.editReadOnly';
            switch(newType.name){
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
            $state.go(url);
        }

    }, true);

    $scope.goToAddEdit = function(){
        $state.go('modules.detail.questions.blank');
    };
}]);