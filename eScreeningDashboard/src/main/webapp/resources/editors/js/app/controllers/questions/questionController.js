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
        var url = 'modules.detail.editReadOnlyQuestion';
        switch(qType.name){
            case 'Free/Read-Only Text':
                url = 'modules.detail.editReadOnlyQuestion';
                break;
            case 'Select Single':
                url = 'modules.detail.editSelectMultipleQuestion';
                break;
            case 'Select Multiple':
                url = 'modules.detail.editSelectMultipleQuestion';
                break;
            case 'Select Single Matrix':
                url = 'modules.detail.editSelectMultipleMatrixQuestion';
                break;
            case 'Select Multiple Matrix':
                url = 'modules.detail.editSelectMultipleMatrixQuestion';
                break;
            case 'Table Question':
                url = 'modules.detail.editTableQuestion';
                break;
            case 'Instructions':
                url = 'modules.detail.editInstructionQuestion';
                break;
        }
        $state.go(url);
    });

    $scope.goToAddEdit = function(){
        $state.go('modules.detail.question');
    }
}]);