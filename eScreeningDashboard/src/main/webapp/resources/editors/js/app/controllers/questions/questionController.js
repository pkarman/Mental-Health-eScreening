/**
 * Created by pouncilt on 8/6/14.
 */
Editors.controller('questionsController', ['$rootScope', '$scope', '$state', function($rootScope, $scope, $state){
    $scope.qTypes = [
        {name:"Free/Read-Only Text", value:1},
        {name:"Select Single", value:2},
        {name:"Select Multiple", value:3},
        {name:"Select Single Matrix", value:4},
        {name:"Select Multiple Matrix", value:5},
        {name:"Table Question", value:6},
        {name:"Instructions", value:7}];

    $scope.qType = $scope.qTypes[0];

    $scope.$watch( 'qType', function( qType ) {
        var url = 'modules.detail.questions.editReadOnly';
        switch(qType.name){
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
    }, true);

    $scope.goToAddEdit = function(){
        $state.go('modules.detail.questions.blank');
    };

    $scope.showQuestionTypeForm = function(questionType) {

    };
}]);